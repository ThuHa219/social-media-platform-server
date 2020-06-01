package edu.hanu.social_media_platform.service;

import java.util.List;

import edu.hanu.social_media_platform.dao.FriendListDAO;
import edu.hanu.social_media_platform.exception.DataNotFoundException;
import edu.hanu.social_media_platform.model.FriendList;

public class FriendListService {
	private FriendListDAO dao = new FriendListDAO();

	public FriendListService() {
		// do nothing
	}

	public List<FriendList> getAll() {
		return dao.getAll();
	}

	public FriendList get(String profilename) throws DataNotFoundException {
		FriendList friendlist = dao.get(profilename);
		if (friendlist == null) {
			throw new DataNotFoundException("Can not found friendname with profile name: " + profilename);
		}
		return friendlist;
	}

	public FriendList add(FriendList f) {
		dao.save(f);
		return f;
	}

	public FriendList update(FriendList f) {
		dao.update(f);
		return f;
	}

	public FriendList remove(FriendList f) {
		dao.delete(f);
		return f;
	}
	
	public void remove(String profilename, String friendname) {
		dao.delete(profilename, friendname);
	}
}
