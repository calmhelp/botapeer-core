package com.botapeer.domain.service;

import java.util.Optional;

import com.botapeer.domain.service.interfaces.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.botapeer.infrastructure.entity.UserDetailsImpl;
import com.botapeer.domain.model.user.User;
import com.botapeer.domain.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		Optional<User> user = userService.findByUserNameOrEmail(usernameOrEmail);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException(usernameOrEmail + "が存在しません");
		}
		return new UserDetailsImpl(user.get());
	}

	public UserDetails loadUserById(Long userId) {
		Optional<User> user = userService.findById(userId);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException(userId + "が存在しません");
		}
		return new UserDetailsImpl(user.get());
	}
}
