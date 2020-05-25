package edu.hanu.social_media_platform.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.hanu.social_media_platform.dao.ProfileDAO;
import edu.hanu.social_media_platform.exception.DataNotFoundException;
import edu.hanu.social_media_platform.model.Profile;

public class ProfileService {
	private ProfileDAO dao = new ProfileDAO();

	public ProfileService() {
		// do nothing
	}

	public List<Profile> getAllProfiles() {
		return dao.getAll();
	}

	public Profile getProfile(String profilename) throws DataNotFoundException {
		Profile profile = dao.get(profilename);
		if (profile == null) {
			throw new DataNotFoundException("Can not found profile with profile name: " + profilename);
		}
		return profile;
	}

	public List<Profile> getProfilesForYear(int year) {
		List<Profile> profilesYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Profile p : dao.getAll()) {
			cal.setTime(p.getCreated());
			if (cal.get(Calendar.YEAR) == year) {
				profilesYear.add(p);
			}
		}
		return profilesYear;
	}

	public List<Profile> getProfilesPaginated(int start, int size) {
		List<Profile> list = dao.getAll();
		if (start + size > list.size()) {
			return new ArrayList<>();
		}
		return list.subList(start, start + size);
	}

	public Profile addProfile(Profile profile) {
		dao.save(profile);
		return profile;
	}

	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}
		dao.update(profile);
		return profile;
	}

	public void removeProfile(String profileName) throws DataNotFoundException {
		Profile profile = dao.get(profileName);
		if (profile == null) {
			throw new DataNotFoundException("Can not found profile with profile name: " + profileName);
		}
		dao.delete(profileName);
	}
}
