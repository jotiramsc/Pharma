package com.pharma.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pharma.domain.UserDomain;
import com.pharma.model.UserModel;
import com.pharma.repository.UserRepository;
import com.pharma.util.OTPSender;
import com.pharma.util.PharmaException;

@Service
public class UserDetailsService
implements org.springframework.security.core.userdetails.UserDetailsService, Mappable<UserDomain, UserModel> {

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private OTPSender otpSender;

	@Override
	public UserModel loadUserByUsername(String username) {

		UserDomain domain = userRepository.findByUsername(username);
		
		return convertToModel(domain);

	}

	public UserModel saveUser(UserModel userModel) throws PharmaException {

		final UserDomain userDetails = userRepository.findByUsernameOrEmail(userModel.getUsername(),
				userModel.getEmail());

		// user type : customer , admin
		// login type : normal, facebook, google, pharma
		if (null != userDetails && userDetails.getLoginType().equalsIgnoreCase("normal")) {
			throw new PharmaException("User Already Exists");
		} else if (null != userDetails && userDetails.getLoginType().equalsIgnoreCase("facebook")) {
			throw new PharmaException("User Already Registered with Facebook");
		} else if (null != userDetails && userDetails.getLoginType().equalsIgnoreCase("google")) {
			throw new PharmaException("User Already Registered with Google");
		} else if (null != userDetails && userDetails.getLoginType().equalsIgnoreCase("pharma")) {
			throw new PharmaException("User Alredy Exists with Pharma");
		} else {
			UserDomain detail = modelMapper.map(userModel, UserDomain.class);
			detail.setPassword(bcryptEncoder.encode(detail.getPassword()));
			//detail.setMobileVerifiedOTP(UUID.randomUUID().toString());
			detail.setMobileVerified(false);
			detail.setCreatedTs(LocalDateTime.now());
			detail.setModifiedTs(LocalDateTime.now());

			UserDomain userDomain = userRepository.save(detail);
			setUpAccount(detail);
			// emailSender.sendEmailVerificationMail(detail.getEmailVerifyToken(),
			// detail.getEmail());
			return modelMapper.map(userDomain, UserModel.class);

		}
	}

	public String generateOTP(String userName, long mobile) throws PharmaException {

		final UserDomain userDetails = userRepository.findByUsernameOrEmail(userName, userName);
		if (null == userDetails) {
			throw new PharmaException("User does not exists");
		}else {
			userDetails.setMobile(mobile);
			int randomPin = (int) (Math.random() * 9000) + 1000;
			userDetails.setMobileVerifiedOTP(String.valueOf(randomPin));
			UserDomain userDomain = userRepository.save(userDetails);
			//setUpAccount(detail);
			//otpSender.sendMobileVerificationOTP(userDomain.getMobileVerifiedOTP(), String.valueOf(userDomain.getMobile()));
			return userDetails.getMobileVerifiedOTP();
		}
	}

	private void setUpAccount(UserDomain userDomain) {

	}

	public String resetPasswordLink(UserModel userModel) throws PharmaException {

		String response = null;

		final UserDomain userDetails = userRepository.findByUsernameOrEmail(userModel.getUsername(),
				userModel.getEmail());
		if (null == userDetails) {
			response = "We could not find an account for that e-mail address";
		} else {

			userDetails.setResetToken(UUID.randomUUID().toString());
			userDetails.setResetTokenExpiry(LocalDateTime.now().plusDays(5));
			userDetails.setModifiedTs(LocalDateTime.now());
			userRepository.save(userDetails);
			//otpSender.sendPasswordResetMail(userDetails.getResetToken());
			response = "Reset password link has been sent on " + userModel.getEmail();
		}

		return response;

	}

	public String resetPassword(UserModel userModel) throws PharmaException {

		String response = null;

		final UserDomain userDetails = userRepository.findByResetToken(userModel.getResetToken());
		if (null == userDetails) {
			response = "We could not find an account for that e-mail address";
		} else {

			if (validateResetToken(userDetails, userModel)) {

				userDetails.setResetToken(null);
				userDetails.setResetTokenExpiry(null);
				userDetails.setPassword(bcryptEncoder.encode(userModel.getPassword()));
				userDetails.setModifiedTs(LocalDateTime.now());
				userRepository.save(userDetails);

				response = "Password changed";

			} else {

			}

		}

		return response;

	}

	public String verifyOTP(long mobile, String OTP) throws PharmaException {
		String response = null;
		final UserDomain userDetails = userRepository.findByMobile(mobile);
		if (null == userDetails) { 
			throw new PharmaException("We could not find an account for that e-mail address");
		}else {
			if (userDetails.getMobileVerifiedOTP().equalsIgnoreCase(OTP)) {
				userDetails.setMobileVerified(true); 
				userDetails.setModifiedTs(LocalDateTime.now());
				userRepository.save(userDetails);
				response = "Mobile Verification Completed";
			}else
			{
				throw new PharmaException("Please enter valid OTP");
			}
		}

		return response;
	}

	private boolean validateResetToken(UserDomain userDetails, UserModel userModel) {
		if (userDetails.getResetToken().equalsIgnoreCase(userModel.getResetToken())
				&& (!userDetails.getResetTokenExpiry().isBefore(LocalDateTime.now()))) {
			return true;
		}
		return false;

	}

	@Override
	public UserModel convertToModel(UserDomain domainObject) {
		return modelMapper.map(domainObject, UserModel.class);
	}

	@Override
	public UserDomain convertToDomain(UserModel modelObject) {
		if (modelObject.getId() != null) {
			UserDomain destination = userRepository.getOne(modelObject.getId());
			modelMapper.map(modelObject, destination);
			return destination;
		}
		return modelMapper.map(modelObject, UserDomain.class);
	}

	@Override
	public List<UserModel> convertToModelList(List<UserDomain> domainlist) {
		return domainlist.parallelStream().map(this::convertToModel).collect(Collectors.toList());
	}

	@Override
	public List<UserDomain> convertToDomainList(List<UserModel> modelList) {
		return modelList.parallelStream().map(this::convertToDomain).collect(Collectors.toList());
	}

	public UserModel getCurrentUserDetails() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return this.loadUserByUsername(username);
	}
}