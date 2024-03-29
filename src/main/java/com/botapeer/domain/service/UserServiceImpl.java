package com.botapeer.domain.service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.botapeer.domain.adapter.IUploader;
import com.botapeer.exception.InvalidImageException;
import com.botapeer.util.ImageUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.botapeer.constants.ResponseConstants;
import com.botapeer.domain.model.user.User;
import com.botapeer.domain.repository.IUserRepository;
import com.botapeer.domain.service.interfaces.IUserService;
import com.botapeer.exception.NotFoundException;
import com.botapeer.util.StringUtil;
import com.botapeer.util.ValidateUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final IUserRepository userRepository;
	private final Validator validator;
	private final IUploader uploader;

	@Value(value = "${IMAGE_PATH}")
	private String imagePath;

	@Override
	public Optional<User> findById(Long userId) {
		Optional<User> u = userRepository.findById(userId);
		if (u.isEmpty()) {
			throw new NotFoundException(ResponseConstants.NOTFOUND_USER_CODE);
		}

		return this.userRepository.findById(userId);
	}

	@Override
	public Collection<User> findUsers(String name) {
		return this.userRepository.findUsers(name);
	}

	@Override
	public Integer create(User user, String encryptedPassword) {
		Map<String, String> errorMessages = new HashMap<>();
		Optional<String> validationMessage;
		validationMessage = ValidateUtils.validateNullOrEmpty(encryptedPassword, "encryptedPassword is null or empty");
		validationMessage.ifPresent(msg -> errorMessages.put("encryptedPassword_nullOrEmpty", msg));

		if (!errorMessages.isEmpty()) {
			throw new IllegalArgumentException(errorMessages.toString());
		}

		user.setStatus(2);
		return this.userRepository.create(user, encryptedPassword);
	}

	@Override
	public Optional<User> update(User userForUpdate , MultipartFile profileImage, MultipartFile coverImage) {

		Map<String, String> errorMessages = new HashMap<>();
		Optional<String> validationMessage;

		userForUpdate.setDescription(StringUtil.null2Void(userForUpdate.getDescription()));
		userForUpdate.setProfileImage(StringUtil.null2Void(userForUpdate.getProfileImage()));
		userForUpdate.setCoverImage(StringUtil.null2Void(userForUpdate.getCoverImage()));

		validationMessage = ValidateUtils.validatePresent(userForUpdate.getPassword(), "password is present");
		validationMessage.ifPresent(msg -> errorMessages.put("password_present", msg));
		validationMessage = ValidateUtils.validatePresent(userForUpdate.getStatus(), "status is present");
		validationMessage.ifPresent(msg -> errorMessages.put("status_present", msg));

		if (!errorMessages.isEmpty()) {
			throw new IllegalArgumentException(errorMessages.toString());
		}

		Optional<User> savedUser = findById((long) userForUpdate.getId());
		if (savedUser.isEmpty()) {
			throw new NotFoundException(ResponseConstants.NOTFOUND_USER_CODE);
		}

		if(!isEmpty(profileImage)) {
			if(ImageUtils.isImage(profileImage)) {
				throw new InvalidImageException("The uploaded file is not an image.");
			}
		}
		if(!isEmpty(coverImage)) {
			if(ImageUtils.isImage(coverImage)) {
				throw new InvalidImageException("The uploaded file is not an image.");
			}
		}

		String profileImageName = null;
		try {
			profileImageName = uploader.uploadImage(profileImage);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (StringUtils.isEmpty(profileImageName)) {
			userForUpdate.setProfileImage(savedUser.get().getProfileImage());
		} else {
			userForUpdate.setProfileImage(imagePath + profileImageName);
		}

		String coverImageName = null;
		try {
			coverImageName = uploader.uploadImage(coverImage);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (StringUtils.isEmpty(coverImageName)) {
			userForUpdate.setCoverImage(savedUser.get().getCoverImage());
		} else {
			userForUpdate.setCoverImage(imagePath + coverImageName);
		}

		if(!this.userRepository.update(userForUpdate)) {
			logger.error("user cannot be updated");
			throw new RuntimeException();
		}

		return findById((long)userForUpdate.getId());
	}

	//	@Override
	//	public boolean updatePassword(UpdatePasswordRequest request) {
	//
	//		String currentPassword = request.getCurrentPassword();
	//		String newPassword = request.getNewPassword();
	//
	//		if (newPassword.length() < 8
	//				|| newPassword.length() < 20) {
	//			throw new Error();
	//		}
	//
	//		return userRepository.updatePassword(request);
	//	}

	@Override
	public boolean delete(Long userId) {
		Map<String, String> errorMessages = new HashMap<>();
		Optional<String> validationMessage;
		validationMessage = ValidateUtils.validateNullOrEmpty(userId, "userId is null or empty");
		validationMessage.ifPresent(msg -> errorMessages.put("userId_nullOrEmpty", msg));
		if (!errorMessages.isEmpty()) {
			throw new IllegalArgumentException(errorMessages.toString());
		}

		validationMessage = ValidateUtils.validateZeroOrNegative(userId, "userId is zero or negative");
		validationMessage.ifPresent(msg -> errorMessages.put("userId_ZeroOrNegative", msg));
		if (!errorMessages.isEmpty()) {
			throw new IllegalArgumentException(errorMessages.toString());
		}

		Optional<User> savedUser = findById(userId);
		if (savedUser.isEmpty()) {
			throw new NotFoundException(ResponseConstants.NOTFOUND_USER_CODE);
		}

		return userRepository.delete(userId);
	}

	@Override
	public Optional<User> findByUserNameOrEmail(String usernameOrEmail) {
		Map<String, String> errorMessages = new HashMap<>();
		Optional<String> validationMessage;
		validationMessage = ValidateUtils.validateNullOrEmpty(usernameOrEmail, "usernameOrEmail is null or empty");
		validationMessage.ifPresent(msg -> errorMessages.put("usernameOrEmail_nullOrEmpty", msg));

		if (!errorMessages.isEmpty()) {
			throw new IllegalArgumentException(errorMessages.toString());
		}

		Optional<User> user = userRepository.findUserByNameOrEmail(usernameOrEmail);
		if (user.isEmpty()) {
			throw new NotFoundException(ResponseConstants.NOTFOUND_USER_CODE);
		}
		return user;
	}

	@Override
	public Optional<User> findByEmail(String email) {
		Map<String, String> errorMessages = new HashMap<>();
		Optional<String> validationMessage;
		validationMessage = ValidateUtils.validateNullOrEmpty(email, "email is null or empty");
		validationMessage.ifPresent(msg -> errorMessages.put("email_nullOrEmpty", msg));

		if (!errorMessages.isEmpty()) {
			throw new IllegalArgumentException(errorMessages.toString());
		}

		Optional<User> user = userRepository.findByEmail(email);

		if (user.isEmpty()) {
			throw new NotFoundException(ResponseConstants.NOTFOUND_USER_CODE);
		}

		return user;
	}

	@Override
	public Optional<User> findByName(String name) {
		Map<String, String> errorMessages = new HashMap<>();
		Optional<String> validationMessage;
		validationMessage = ValidateUtils.validateNullOrEmpty(name, "name is null or empty");
		validationMessage.ifPresent(msg -> errorMessages.put("name_nullOrEmpty", msg));

		if (!errorMessages.isEmpty()) {
			throw new IllegalArgumentException(errorMessages.toString());
		}

		Optional<User> user = userRepository.findByName(name);

		if (user.isEmpty()) {
			throw new NotFoundException(ResponseConstants.NOTFOUND_USER_CODE);
		}

		return userRepository.findByName(name);
	}

}
