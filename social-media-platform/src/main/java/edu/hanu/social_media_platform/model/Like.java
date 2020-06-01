package edu.hanu.social_media_platform.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Like {
	private long id;
	private Profile profile;
	private Status status;
	private String created;
	private List<Link> links = new ArrayList<>();
	
	public Like(int id, String author, Date created) {
		super();
		this.id = id;
	}

	public Like() {
		super();
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
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the links
	 */
	public List<Link> getLinks() {
		return links;
	}

	/**
	 * @param links the links to set
	 */
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public void addLink(String uri, String rel) {
		Link link = new Link();
		link.setUri(uri);
		link.setRel(rel);
		links.add(link);
	}

	@Override
	public String toString() {
		return "Like [id=" + id + ", profile=" + profile + ", status=" + status + ", created=" + created + ", links="
				+ links + "]";
	}
}
