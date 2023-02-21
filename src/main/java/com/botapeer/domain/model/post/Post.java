package com.botapeer.domain.model.post;

import java.time.LocalDateTime;

import com.botapeer.domain.model.text.Article;
import com.botapeer.domain.model.text.Title;

import lombok.Data;

@Data
public class Post {

	private Long id;

	private Long plantRecordId;

	Title title;

	Article article;

	String imageUrl;

	Integer status;

	LocalDateTime createdAt;

	LocalDateTime updatedAt;

}
