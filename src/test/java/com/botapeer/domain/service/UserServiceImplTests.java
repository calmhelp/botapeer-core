package com.botapeer.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.botapeer.domain.model.user.Password;
import com.botapeer.domain.model.user.User;
import com.botapeer.domain.model.user.UserName;
import com.botapeer.domain.repository.IUserRepository;
import com.botapeer.exception.NotFoundException;

public class UserServiceImplTests {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private IUserRepository userRepository;

	@BeforeEach
	void setup() {
		Collection<User> users = new ArrayList<User>();
		users.add(new User(1, new UserName("taro"), "taro@taro.com", new Password("encryptedPassword"),
				Integer.valueOf(1), "説明1",
				"", ""));
		users.add(new User(2, new UserName("jiro"), "jiro@jiro.com", new Password("encryptedPassword"),
				Integer.valueOf(1), "説明2",
				"", ""));
		users.add(new User(3, new UserName("saburo"), "saburo@saburo.com", new Password("encryptedPassword"),
				Integer.valueOf(1), "説明3",
				"", ""));

		MockitoAnnotations.openMocks(this);

		Mockito.when(userRepository.findUsers(Mockito.anyString())).thenReturn(new ArrayList<User>());
		Mockito.when(userRepository.findUsers("")).thenReturn(users);
		Mockito.when(userRepository.delete(Mockito.anyLong())).thenReturn(false);
		for(User user: users) {
			if (user.getId().equals(1)) {
				Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
				Mockito.when(userRepository.delete((long)user.getId())).thenReturn(true);
			} else if (user.getId().equals(2)) {
				Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(user));
				Mockito.when(userRepository.delete((long)user.getId())).thenReturn(true);
			} else if(user.getId().equals(3)) {
				Mockito.when(userRepository.findById(3L)).thenReturn(Optional.of(user));
				Mockito.when(userRepository.delete((long)user.getId())).thenReturn(true);
			}
			if(user.getName().getName().equals("taro")) {
				Mockito.when(userRepository.findByName("taro")).thenReturn(Optional.of(user));
				Mockito.when(userRepository.findUsers("taro")).thenReturn(Collections.singletonList(user));
				Mockito.when(userRepository.findUserByNameOrEmail("taro")).thenReturn(Optional.of(user));
				Mockito.when(userRepository.findUserByNameOrEmail("taro@taro.com")).thenReturn(Optional.of(user));
				Mockito.when(userRepository.findByEmail("taro@taro.com")).thenReturn(Optional.of(user));
				Mockito.when(userRepository.findByName("taro")).thenReturn(Optional.of(user));
			}else if(user.getName().getName().equals("jiro")) {
				Mockito.when(userRepository.findByName("jiro")).thenReturn(Optional.of(user));
				Mockito.when(userRepository.findUsers("jiro")).thenReturn(Collections.singletonList(user));
				Mockito.when(userRepository.findUserByNameOrEmail("jiro")).thenReturn(Optional.of(user));
				Mockito.when(userRepository.findUserByNameOrEmail("jiro@jiro.com")).thenReturn(Optional.of(user));
				Mockito.when(userRepository.findByEmail("jiro@jiro.com")).thenReturn(Optional.of(user));
				Mockito.when(userRepository.findByName("jiro")).thenReturn(Optional.of(user));
			}else if(user.getName().getName().equals("saburo")) {
				Mockito.when(userRepository.findByName("saburo")).thenReturn(Optional.of(user));
				Mockito.when(userRepository.findUsers("saburo")).thenReturn(Collections.singletonList(user));
				Mockito.when(userRepository.findUserByNameOrEmail("saburo")).thenReturn(Optional.of(user));
				Mockito.when(userRepository.findUserByNameOrEmail("saburo@saburo.com")).thenReturn(Optional.of(user));
				Mockito.when(userRepository.findByEmail("saburo@saburo.com")).thenReturn(Optional.of(user));
				Mockito.when(userRepository.findByName("saburo")).thenReturn(Optional.of(user));
			} 
		}
		
		Mockito.when(userRepository.create(Mockito.isA(User.class), Mockito.anyString())).thenReturn(4);
		Mockito.when(userRepository.update(Mockito.isA(User.class))).thenReturn(true);
		
		
		userService = new UserServiceImpl(userRepository);
	}

	@Test
	void testFindById() {
		User user = userService.findById(1L).orElse(null);
		Assertions.assertNotNull(user);
		Assertions.assertEquals(1, user.getId());
		Assertions.assertEquals("taro", user.getName().getName());
		Assertions.assertEquals("taro@taro.com", user.getEmail());
		Assertions.assertEquals("encryptedPassword", user.getPassword().getPassword());
		Assertions.assertEquals(1, user.getStatus());
		Assertions.assertEquals("説明1", user.getDescription());
		Assertions.assertEquals("", user.getProfileImage());
		Assertions.assertEquals("", user.getCoverImage());
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			userService.findById(-1L);
		});
		Assertions.assertThrows(NullPointerException.class, () -> {
			userService.findById(null);
		});
		Optional<User> nullUser = userService.findById(100L);
		Assertions.assertEquals(Optional.empty(), nullUser);
	}

	@Test
	void testFindUsers() {
		Collection<User> u = userService.findUsers("taro");
		Assertions.assertEquals(1, u.stream().findFirst().get().getId());
		Assertions.assertEquals("taro", u.stream().findFirst().get().getName().getName());
		Assertions.assertEquals("taro@taro.com", u.stream().findFirst().get().getEmail());
		Assertions.assertEquals("encryptedPassword", u.stream().findFirst().get().getPassword().getPassword());
		Assertions.assertEquals(1, u.stream().findFirst().get().getStatus());
		Assertions.assertEquals("", u.stream().findFirst().get().getProfileImage());
		Assertions.assertEquals("", u.stream().findFirst().get().getCoverImage());
		Assertions.assertThrows(NullPointerException.class, () -> {
			userService.findUsers(null);
		});
		Collection<User> userEmpty = userService.findUsers("");
		Assertions.assertEquals(3, userEmpty.size());
		Collection<User> userTest = userService.findUsers("test");
		Assertions.assertEquals(0, userTest.size());
	}

	@Test
	void testCreateUser() {
		User user = new User(new UserName("shiro"), "shiro@shiro.com", "説明4",
				"", "");
		Integer createdId = userService.create(user, "encryptedPassword");
		Assertions.assertEquals(4, createdId);
		Assertions.assertEquals(2, user.getStatus());
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			userService.create(user, "");
		});
		Assertions.assertThrows(NullPointerException.class, () -> {
			userService.create(user, null);
		});
		Assertions.assertThrows(NullPointerException.class, () -> {
			userService.create(null, "encryptedPassword");
		});
	}

	@Test
	void testUpdateUser() {
		User user = new User(1, new UserName("goro"), "goro@goro.com", "説明5",
				"/image/imagePath1", "/image/imagePath2");
		boolean isSuccess = userService.update(user);
		Assertions.assertEquals(isSuccess, true);
		Assertions.assertThrows(NullPointerException.class, () -> {
			userService.update(null);
		});
		user.setPassword(new Password("password"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			userService.update(user);
		});
		user.setPassword(null);
		user.setStatus(2);
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			userService.update(user);
		});
		User user1000 = new User(1000, new UserName("taro"), "taro@taro.com", "説明1000",
				"/image/imagePath1", "/image/imagePath2");
		Assertions.assertThrows(NotFoundException.class, () -> {
			userService.update(user1000);
		});
	}

	@Test
	void testDelete() {
		boolean isSuccess = userService.delete(1L);
		Assertions.assertTrue(isSuccess);
		boolean isSuccessWithNoId = userService.delete(100L);
		Assertions.assertFalse(isSuccessWithNoId);
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			userService.delete(-3L);
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			userService.delete(0L);
		});
		Assertions.assertThrows(NullPointerException.class, () -> {
			userService.delete(null);
		});
	}

	@Test
	void testFindByUserNameOrEmail() {
		Assertions.assertThrows(NotFoundException.class, () -> {
			userService.findByUserNameOrEmail("test@test.com");
		});
		Optional<User> jiro = userService.findByUserNameOrEmail("jiro@jiro.com");
		Assertions.assertEquals(2, jiro.get().getId());
		Assertions.assertEquals("jiro@jiro.com", jiro.get().getEmail());
		Assertions.assertEquals("jiro", jiro.get().getName().getName());
		Assertions.assertEquals("encryptedPassword", jiro.get().getPassword().getPassword());
		Assertions.assertEquals(1, jiro.get().getStatus());
		Assertions.assertEquals("説明2", jiro.get().getDescription());

		Optional<User> saburo = userService.findByUserNameOrEmail("saburo@saburo.com");
		Assertions.assertEquals(3, saburo.get().getId());
		Assertions.assertEquals("saburo@saburo.com", saburo.get().getEmail());
		Assertions.assertEquals("saburo", saburo.get().getName().getName());
		Assertions.assertEquals("encryptedPassword", saburo.get().getPassword().getPassword());
		Assertions.assertEquals(1, saburo.get().getStatus());
		Assertions.assertEquals("説明3", saburo.get().getDescription());

		Assertions.assertThrows(NullPointerException.class, () -> {
			userService.findByUserNameOrEmail(null);
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			userService.findByUserNameOrEmail("");
		});
	}

	@Test
	void testFindByEmail() {
		Optional<User> user = userService.findByEmail("taro@taro.com");
		Assertions.assertEquals(1, user.get().getId());
		Assertions.assertEquals("taro@taro.com", user.get().getEmail());
		Assertions.assertEquals("taro", user.get().getName().getName());
		Assertions.assertEquals("encryptedPassword", user.get().getPassword().getPassword());
		Assertions.assertEquals(1, user.get().getStatus());
		Assertions.assertEquals("説明1", user.get().getDescription());

		Assertions.assertThrows(NullPointerException.class, () -> {
			userService.findByEmail(null);
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			userService.findByEmail("");
		});
	}

	@Test
	void testFindByName() {
		Optional<User> user = userService.findByName("taro");
		Assertions.assertEquals(1, user.get().getId());
		Assertions.assertEquals("taro@taro.com", user.get().getEmail());
		Assertions.assertEquals("taro", user.get().getName().getName());
		Assertions.assertEquals("encryptedPassword", user.get().getPassword().getPassword());
		Assertions.assertEquals(1, user.get().getStatus());
		Assertions.assertEquals("説明1", user.get().getDescription());

		Assertions.assertThrows(NullPointerException.class, () -> {
			userService.findByName(null);
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			userService.findByName("");
		});
	}

}
