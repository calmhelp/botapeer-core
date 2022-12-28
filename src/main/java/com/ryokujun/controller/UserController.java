package com.ryokujun.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ryokujun.constants.ResponseConstants;
import com.ryokujun.controller.error.ErrorMessages;
import com.ryokujun.controller.exception.validation.ValidationException;
import com.ryokujun.entity.User;
import com.ryokujun.service.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final IUserService userService;

	@GetMapping("/user/{userId}")
	public User getUser(@PathVariable String userId) {
		User u = userService.findById(userId);
		return u;
	}

	@PostMapping("/user/{userId}")
	public void updateUser(@PathVariable String userId, @Validated @RequestBody User user,
			BindingResult result) {
		if (result.hasErrors()) {
			List<HashMap<String, String>> list = new ArrayList<>();
			for (int i = 0; i < result.getErrorCount(); i++) {
				HashMap<String, String> errorsList = new HashMap<>();
				errorsList.put(ResponseConstants.ERRORS_CODE_KEY, result.getAllErrors().get(i).getCode());
				errorsList.put(ResponseConstants.ERRORS_MESSAGE_KEY, result.getAllErrors().get(i).getDefaultMessage());
				list.add(errorsList);
			}
			ErrorMessages errorMessages = new ErrorMessages();
			errorMessages.setMessages(list);
			throw new ValidationException(errorMessages);
		}
		int userIdforUpdate = Integer.parseInt(userId);
		user.setId(userIdforUpdate);
		userService.update(user);
	}

}
