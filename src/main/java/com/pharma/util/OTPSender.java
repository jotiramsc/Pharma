package com.pharma.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class OTPSender {


	@Value("${twillio.account.sid}")
	public String AccountSID;
	
	@Value("${twillio.account.auth.token}")
	private String authToken;	

/*	public boolean sendPasswordResetMail(String resetToken) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo("chavanjotiram24@gmail.com");
		mailMessage.setSubject("Password Reset Link");
		mailMessage.setText("Please click below link to reset password:" + resetToken);

		mailMessage.setFrom("johndoe@example.com");

		javaMailSender.send(mailMessage);
		return false;

	} */
	
	public boolean sendMobileVerificationOTP(String OTP, String mobileNumber) {

		Twilio.init(AccountSID, authToken);
		mobileNumber = "+" + mobileNumber;

        Message message = Message
                .creator(new PhoneNumber(mobileNumber), new PhoneNumber("+12029337470"), OTP)
                .create();
        
        System.out.println(message.getSid());
        
        return true;
	}
}
