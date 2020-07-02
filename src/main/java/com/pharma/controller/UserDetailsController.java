package com.pharma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.auth.JwtTokenUtil;
import com.pharma.model.JwtResponse;
import com.pharma.model.UserModel;
import com.pharma.service.UserDetailsService;
import com.pharma.util.PharmaException;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserDetailsController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping(value = "/signin")
	public JwtResponse createAuthenticationToken(@RequestBody UserModel authenticationRequest) throws PharmaException {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());		
					
		final String token = jwtTokenUtil.generateToken(userDetails);
		return new JwtResponse(token);
	}

	private void authenticate(String username, String password) throws PharmaException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new PharmaException("USER_DISABLED");
		} catch (BadCredentialsException e) {
			throw new PharmaException("Invalid username and password");
		}
		catch (InternalAuthenticationServiceException e) {
			throw new PharmaException("Invalid username and password");
		}
	}

	@PostMapping(value = "/signup")
	public UserModel registerUser(@RequestBody UserModel userModel) throws PharmaException {

		return userDetailsService.saveUser(userModel);

	}

	@GetMapping(value = "/generateOTP/{username}/{mobile}")
	public String generateOTP(@PathVariable("username") String username, @PathVariable("mobile") long mobile)
			throws PharmaException {

		return userDetailsService.generateOTP(username, mobile);
	}

	@GetMapping(value = "/verifyOTP/{mobile}/{OTP}")
	public String verifyOTP(@PathVariable("mobile") long mobile, @PathVariable("OTP") String OTP)
			throws PharmaException {

		return userDetailsService.verifyOTP(mobile, OTP);
	}

	@PostMapping(value = "/resetPasswordLink")
	public String resetPasswordLink(@RequestBody UserModel userModel) throws PharmaException {

		return userDetailsService.resetPasswordLink(userModel);
	}

	@PostMapping(value = "/resetPassword")
	public String resetPassword(@RequestBody UserModel userModel) throws PharmaException {

		return userDetailsService.resetPassword(userModel);
	}

	@GetMapping(value = "/userDetails")
	public UserModel getUserDetails() throws PharmaException {

		return userDetailsService.getCurrentUserDetails();
	}

}