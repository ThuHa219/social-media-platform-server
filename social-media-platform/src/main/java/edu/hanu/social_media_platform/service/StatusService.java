package edu.hanu.social_media_platform.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.hanu.social_media_platform.dao.StatusDAO;
import edu.hanu.social_media_platform.exception.DataNotFoundException;
import edu.hanu.social_media_platform.model.Status;

public class StatusService {
	private StatusDAO dao = new StatusDAO();

	public StatusService() {
		// do nothing
	}

	public List<Status> getAll() {
		return dao.getAll();
	}

	public Status get(long id) throws DataNotFoundException {
		Status status = dao.get(id);
		if (status == null) {
			throw new DataNotFoundException("Can not found status with status id: " + id);
		}
		return status;
	}

	public List<Status> getStatusForYear(int year) {
		List<Status> statusYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Status s : dao.getAll()) {
			Date created = Date.valueOf(s.getCreated());
			cal.setTime(created);
			if (cal.get(Calendar.YEAR) == year) {
				statusYear.add(s);
			}
		}
		return statusYear;
	}

	public List<Status> getStatusPaginated(int start, int size) {
		List<Status> list = dao.getAll();
		if (start + size > list.size()) {
			return new ArrayList<>();
		}
		return list.subList(start, start + size);
	}

	public Status add(Status status) {
		dao.save(status);
		return status;
	}

	public Status update(Status status) {
		if (status.getId() <= 0) {
			return null;
		}
		dao.update(status);
		return status;
	}

	public void remove(long id) throws DataNotFoundException {
		Status status = dao.get(id);
		if (status == null) {
			throw new DataNotFoundException("Can not found profile with profile id: " + id);
		}
		dao.delete(id);
	}
}
