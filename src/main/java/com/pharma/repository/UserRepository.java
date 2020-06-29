package com.pharma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.domain.UserDomain;

public interface UserRepository extends JpaRepository<UserDomain, Long> {

	public UserDomain findByUsername(String username);

	public UserDomain findByUsernameOrEmail(String username, String email);

	public UserDomain findByResetToken(String resetToken);
	
	public UserDomain findByMobile(long mobile);
}
