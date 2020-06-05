package edu.hanu.social_media_platform.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import edu.hanu.social_media_platform.dao.ProfileDAO;
import edu.hanu.social_media_platform.exception.DataNotFoundException;
import edu.hanu.social_media_platform.model.Profile;
import edu.hanu.social_media_platform.utils.PasswordAuthentication;

public class ProfileService {
	private ProfileDAO dao = new ProfileDAO();
	private PasswordAuthentication authentication = new PasswordAuthentication();

	public ProfileService() {
		// do nothing
	}

	public List<Profile> getAll() {
		return dao.getAll();
	}

	public Profile get(String profilename) throws DataNotFoundException {
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
			Date created = Date.valueOf(p.getCreated());
			cal.setTime(created);
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

	@SuppressWarnings("deprecation")
	public Profile add(Profile profile) {
		System.out.println(profile.getPassword());
		String pass = profile.getPassword();
		profile.setPassword(authentication.hash(pass));
		dao.save(profile);
		return profile;
	}

	@SuppressWarnings("deprecation")
	public Profile update(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}
		System.out.println(profile.getPassword());
		String pass = profile.getPassword();
		profile.setPassword(authentication.hash(pass));
		dao.update(profile);
		return profile;
	}
	
	public Profile edit(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}
		System.out.println(profile.getPassword());
		String pass = profile.getPassword();
		profile.setPassword(authentication.hash(pass));
		dao.updateNotQA(profile);
		return profile;
	}

	public void remove(String profileName) throws DataNotFoundException {
		Profile profile = dao.get(profileName);
		if (profile == null) {
			throw new DataNotFoundException("Can not found profile with profile name: " + profileName);
		}
		dao.delete(profileName);
	}
	public static void main(String[] args) {
		Profile p = new Profile();
		p.setFirstName("Chien");
		p.setLastName("Pham");
		p.setProfileName("QuangChien20");
		p.setPassword("12345678910");
		p.setQuestion("what is favorite book ?");
		p.setAnswer("harry potter");
		
		ProfileService profileService = new ProfileService();
		System.out.println(profileService.add(p));
	}
}
