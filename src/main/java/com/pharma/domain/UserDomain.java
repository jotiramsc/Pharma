package com.pharma.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "user")
public class UserDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@Column(length = 50)
	String firstName;
	@Column(length = 50)
	String lastName;
	@Column(length = 50)
	String email;
	@Column(length = 10)
	long mobile;
	@Column
	boolean validate;
	@Column(length = 20)
	String loginType;
	@Column(length = 20)
	String userType;
	@Column(length = 10)
	String role;
	@Column
	LocalDateTime createdTs;
	@Column
	LocalDateTime modifiedTs;

	@Column(length = 250)
	String username;

	@Column(length = 250)
	String password;

	@Column(length = 36)
	String resetToken;

	@Column
	LocalDateTime resetTokenExpiry;

	@Column(length = 36)
	String mobileVerifiedOTP;

	@Column
	boolean mobileVerified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDateTime getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(LocalDateTime createdTs) {
		this.createdTs = createdTs;
	}

	public LocalDateTime getModifiedTs() {
		return modifiedTs;
	}

	public void setModifiedTs(LocalDateTime modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public LocalDateTime getResetTokenExpiry() {
		return resetTokenExpiry;
	}

	public void setResetTokenExpiry(LocalDateTime resetTokenExpiry) {
		this.resetTokenExpiry = resetTokenExpiry;
	}

	public String getMobileVerifiedOTP() {
		return mobileVerifiedOTP;
	}

	public void setMobileVerifiedOTP(String mobileVerifiedOTP) {
		this.mobileVerifiedOTP = mobileVerifiedOTP;
	}

	public boolean isMobileVerified() {
		return mobileVerified;
	}

	public void setMobileVerified(boolean mobileVerified) {
		this.mobileVerified = mobileVerified;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdTs == null) ? 0 : createdTs.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (mobileVerified ? 1231 : 1237);
		result = prime * result + ((mobileVerifiedOTP == null) ? 0 : mobileVerifiedOTP.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((loginType == null) ? 0 : loginType.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		result = prime * result + (int) (mobile ^ (mobile >>> 32));
		result = prime * result + ((modifiedTs == null) ? 0 : modifiedTs.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((resetToken == null) ? 0 : resetToken.hashCode());
		result = prime * result + ((resetTokenExpiry == null) ? 0 : resetTokenExpiry.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + (validate ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDomain other = (UserDomain) obj;
		if (createdTs == null) {
			if (other.createdTs != null)
				return false;
		} else if (!createdTs.equals(other.createdTs))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (mobileVerified != other.mobileVerified)
			return false;
		if (mobileVerifiedOTP == null) {
			if (other.mobileVerifiedOTP != null)
				return false;
		} else if (!mobileVerifiedOTP.equals(other.mobileVerifiedOTP))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (loginType == null) {
			if (other.loginType != null)
				return false;
		} else if (!loginType.equals(other.loginType))
			return false;
		if (userType == null) {
			if (other.userType != null)
				return false;
		} else if (!userType.equals(other.userType))
			return false;
		if (mobile != other.mobile)
			return false;
		if (modifiedTs == null) {
			if (other.modifiedTs != null)
				return false;
		} else if (!modifiedTs.equals(other.modifiedTs))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (resetToken == null) {
			if (other.resetToken != null)
				return false;
		} else if (!resetToken.equals(other.resetToken))
			return false;
		if (resetTokenExpiry == null) {
			if (other.resetTokenExpiry != null)
				return false;
		} else if (!resetTokenExpiry.equals(other.resetTokenExpiry))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (validate != other.validate)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDomain [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobile=" + mobile + ", validate=" + validate + ", loginType=" + loginType + ", role=" + role
				+ ", createdTs=" + createdTs + ", modifiedTs=" + modifiedTs + ", username=" + username + ", password="
				+ password + ", resetToken=" + resetToken + ", resetTokenExpiry=" + resetTokenExpiry
				+ ", mobileVerifiedOTP=" + mobileVerifiedOTP + ", mobileVerified=" + mobileVerified + "]";
	}

}
