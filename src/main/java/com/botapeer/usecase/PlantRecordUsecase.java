package com.botapeer.usecase;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.botapeer.domain.model.place.Place;
import com.botapeer.domain.model.plantRecord.PlantRecord;
import com.botapeer.domain.model.post.Post;
import com.botapeer.domain.model.user.User;
import com.botapeer.domain.service.interfaces.IPlaceService;
import com.botapeer.domain.service.interfaces.IPlantRecordService;
import com.botapeer.domain.service.interfaces.IPostService;
import com.botapeer.domain.service.interfaces.IUserService;
import com.botapeer.usecase.dto.plantRecord.CreatePlantRecordRequestDto;
import com.botapeer.usecase.dto.plantRecord.PlantRecordResponseDto;
import com.botapeer.usecase.interfaces.IPlantRecordUsecase;

import lombok.RequiredArgsConstructor;
import model.CreatePlantRecordRequest;
import model.PlantRecordResponse;

@Component
@RequiredArgsConstructor
public class PlantRecordUsecase implements IPlantRecordUsecase {

	private final IPlantRecordService plantRecordService;
	private final IUserService userService;
	private final IPlaceService placeService;
	private final IPostService postService;

	Logger logger = LoggerFactory.getLogger(PlantRecordUsecase.class);

	@Value(value = "${imagePath}")
	private String imagePath;

	@Override
	public Optional<PlantRecordResponse> findById(String plantRecordId) {
		try {
			Long id = Long.parseLong(plantRecordId);
			Optional<PlantRecord> record = plantRecordService.findById(id);

			Collection<Post> posts = postService.findByPlantRecordId(record.get().getId());

			record.get().setPosts(posts);

			Optional<Place> place = placeService.findById(record.get().getPlace().getId());
			record.get().setPlace(place.get());

			Optional<PlantRecordResponse> r = PlantRecordResponseDto.toResponse(record);
			return r;
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
		}
		return Optional.empty();
	}

	@Override
	public Optional<PlantRecordResponse> create(CreatePlantRecordRequest request) {

		PlantRecord plantRecord = CreatePlantRecordRequestDto.toModel(request);

		plantRecord.setAlive(1);
		plantRecord.setStatus(1);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String userName = auth.getName();
		Optional<User> user = userService.findByName(userName);

		plantRecord.setUserId(user.get().getId());

		Optional<Place> place = placeService.findById(request.getPlaceId());
		plantRecord.setPlace(place.get());

		Optional<PlantRecord> resultRecord = plantRecordService.create(plantRecord);

		Collection<Post> posts = postService.findByPlantRecordId(resultRecord.get().getId());

		resultRecord.get().setPosts(posts);
		resultRecord.get().setPlace(place.get());

		return PlantRecordResponseDto.toResponse(resultRecord);
	}

	@Override
	public Collection<PlantRecordResponse> findByUserId(String userId) {
		try {
			Integer numUserId = Integer.parseInt(userId);
			Collection<PlantRecord> plantRecords = plantRecordService.findByUserId((long) numUserId);
			for (PlantRecord p : plantRecords) {
				Collection<Post> posts = postService.findByPlantRecordId(p.getId());
				p.setPosts(posts);

				Optional<Place> place = placeService.findById((long) p.getPlace().getId());
				p.setPlace(place.get());
			}

			Collection<PlantRecordResponse> response = PlantRecordResponseDto.toResponse(plantRecords);

			return response;
		} catch (NumberFormatException ex) {
			logger.error(ex.getMessage());
		}
		return null;
	}

}
