package edu.hanu.social_media_platform.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.hanu.social_media_platform.dao.LikeDAO;
import edu.hanu.social_media_platform.exception.DataNotFoundException;
import edu.hanu.social_media_platform.model.Like;

public class LikeService {
	private LikeDAO dao = new LikeDAO();

	public LikeService() {
		// do nothing
	}

	public List<Like> getAll() {
		return dao.getAll();
	}

	public Like get(long id) throws DataNotFoundException {
		Like like = dao.get(id);
		if (like == null) {
			throw new DataNotFoundException("Can not found comment with comment id: " + id);
		}
		return like;
	}

	public List<Like> getLikeForYear(int year) {
		List<Like> likeYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Like l : dao.getAll()) {
			Date created = Date.valueOf(l.getCreated());
			cal.setTime(created);
			if (cal.get(Calendar.YEAR) == year) {
				likeYear.add(l);
			}
		}
		return likeYear;
	}

	public List<Like> getLikePaginated(int start, int size) {
		List<Like> list = dao.getAll();
		if (start + size > list.size()) {
			return new ArrayList<>();
		}
		return list.subList(start, start + size);
	}

	public Like add(Like like) {
		dao.save(like);
		return like;
	}

	public Like update(Like like) {
		if (like.getId() <= 0) {
			return null;
		}
		dao.update(like);
		return like;
	}

	public void remove(long id) throws DataNotFoundException {
		Like like = dao.get(id);
		if (like == null) {
			throw new DataNotFoundException("Can not found like with like id: " + id);
		}
		dao.delete(id);
	}
}
