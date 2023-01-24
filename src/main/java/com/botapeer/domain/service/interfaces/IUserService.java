package com.botapeer.domain.service.interfaces;

import java.util.Collection;
import java.util.Optional;

import com.botapeer.controller.payload.user.UpdatePasswordRequest;
import com.botapeer.domain.model.plantRecord.PlantRecord;
import com.botapeer.domain.model.user.User;

public interface IUserService {

	public Optional<User> findById(Long userId);

	public Collection<User> findUsers(String username);

	public boolean update(User user);

	public boolean updatePassword(UpdatePasswordRequest request);

	public boolean delete(Long userId);

	public boolean create(User user);

	public Optional<User> findByUserNameOrEmail(String usernameOrEmail);

	public Optional<User> findByEmail(String email);

	public Optional<User> findByName(String name);

	public Collection<PlantRecord> findPlantRecords(Long userId);

}