package com.ryokujun.domain.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.ryokujun.domain.entity.User;
import com.ryokujun.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSeviceImpl implements IUserService {

	private final UserMapper userMapper;

	@Override
	public User findById(String userId) {
		return this.userMapper.findById(userId);
	}

	@Override
	public Collection<User> findAll() {
		return this.userMapper.findAll();
	}

	@Override
	public boolean update(User user) {
		return this.userMapper.update(user);
	}

	@Override
	public boolean delete(int userId) {
		return this.userMapper.delete(userId);
	}

}