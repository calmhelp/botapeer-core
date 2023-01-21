package com.botapeer.domain.service;

import java.util.Collection;
import java.util.Optional;

import com.botapeer.domain.entity.User;

public interface IUserService {

	public Optional<User> findById(Long userId);

	public Collection<User> findUsers(String username);

	public boolean update(User user);

	public boolean delete(Long userId);

	public boolean create(User user);

	public Optional<User> findByUserNameOrEmail(String usernameOrEmail);

	public Optional<User> findByEmail(String email);

	public Optional<User> findByName(String name);

}