package edu.hanu.social_media_platform.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.hanu.social_media_platform.dao.CommentDAO;
import edu.hanu.social_media_platform.exception.DataNotFoundException;
import edu.hanu.social_media_platform.model.Comment;

public class CommentService {
	private CommentDAO dao = new CommentDAO();

	public CommentService() {
		// do nothing
	}

	public List<Comment> getAll() {
		return dao.getAll();
	}

	public Comment get(long id) throws DataNotFoundException {
		Comment comment = dao.get(id);
		if (comment == null) {
			throw new DataNotFoundException("Can not found comment with comment id: " + id);
		}
		return comment;
	}

	public List<Comment> getCommentForYear(int year) {
		List<Comment> commentYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Comment c : dao.getAll()) {
			Date created = Date.valueOf(c.getCreated());
			cal.setTime(created);
			if (cal.get(Calendar.YEAR) == year) {
				commentYear.add(c);
			}
		}
		return commentYear;
	}

	public List<Comment> getCommentPaginated(int start, int size) {
		List<Comment> list = dao.getAll();
		if (start + size > list.size()) {
			return new ArrayList<>();
		}
		return list.subList(start, start + size);
	}

	public Comment add(Comment comment) {
		dao.save(comment);
		return comment;
	}

	public Comment update(Comment comment) {
		if (comment.getId() <= 0) {
			return null;
		}
		dao.update(comment);
		return comment;
	}

	public void remove(long id) throws DataNotFoundException {
		Comment comment = dao.get(id);
		if (comment == null) {
			throw new DataNotFoundException("Can not found comment with comment id: " + id);
		}
		dao.delete(id);
	}
}
