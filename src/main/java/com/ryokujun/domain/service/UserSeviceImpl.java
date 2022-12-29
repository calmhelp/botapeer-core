package com.ryokujun.domain.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.ryokujun.domain.entity.User;
import com.ryokujun.domain.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSeviceImpl implements IUserService {

	private final IUserRepository userRepository;

	@Override
	public User findById(int userId) {
		return this.userRepository.findById(userId);
	}

	@Override
	public Collection<User> findAll() {
		return this.userRepository.findAll();
	}

	@Override
	public boolean update(User user) {
		return this.userRepository.update(user);
	}

	@Override
	public boolean delete(int userId) {
		return this.userRepository.delete(userId);
	}

	@Override
	public boolean create(User user) {
		return this.userRepository.create(user);
	}

	@Override
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

}
