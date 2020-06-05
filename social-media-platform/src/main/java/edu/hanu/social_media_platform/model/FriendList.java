package edu.hanu.social_media_platform.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FriendList {
	private Profile profile;
	private List<Profile> friend;
	
	public FriendList(Profile profile, List<Profile> friend) {
		super();
		this.profile = profile;
		this.friend = friend;
	}
	/**
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}
	/**
	 * @param profile the profile to set
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	/**
	 * @return the friend
	 */
	public List<Profile> getFriend() {
		return friend;
	}
	/**
	 * @param friend the friend to set
	 */
	public void setFriend(List<Profile> friend) {
		this.friend = friend;
	}
	public FriendList() {
		super();
	}
	
	@Override
	public String toString() {
		return "FriendList [profile=" + profile + ", friend=" + friend + "]";
	}
}
