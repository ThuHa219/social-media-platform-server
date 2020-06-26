package edu.hanu.social_media_platform.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.hanu.social_media_platform.model.Comment;

class CommentServiceTest {
	
	CommentService commentService;
	ProfileService profileService;
	StatusService statusService;
	
	@BeforeEach
	void init() {
		commentService = new CommentService();
		profileService = new ProfileService();
		statusService = new StatusService();
	}

	@Test
	public void testGet() {
		Comment expected = new Comment();
		expected.setId(1);
		expected.setComment("ha ha");
		expected.setCreated("2020-05-28");
		expected.setProfile(profileService.get("ThuHa219"));
		expected.setStatus(statusService.get(1));
		
		assertEquals(expected.toString(), commentService.get(1).toString());
	}
}
