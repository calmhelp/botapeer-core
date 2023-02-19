package com.botapeer.domain.service.interfaces;

import java.util.Collection;
import java.util.Optional;

import com.botapeer.domain.model.user.User;

public interface IUserService {

	public Optional<User> findById(Long userId);

	public Collection<User> findUsers(String username);

	public Integer create(User user, String encryptedPassword);

	public boolean update(User user);

	//	public boolean updatePassword(UpdatePasswordRequest request);

	public boolean delete(Long userId);

	public Optional<User> findByUserNameOrEmail(String usernameOrEmail);

	public Optional<User> findByEmail(String email);

	public Optional<User> findByName(String name);
}
