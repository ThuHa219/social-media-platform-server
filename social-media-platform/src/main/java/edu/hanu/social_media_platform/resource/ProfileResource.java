package edu.hanu.social_media_platform.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import edu.hanu.social_media_platform.model.FilterBean;
import edu.hanu.social_media_platform.model.Profile;
import edu.hanu.social_media_platform.service.ProfileService;

@Path("/profiles")
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class ProfileResource {
	ProfileService profileService = new ProfileService();

	@GET
	public List<Profile> getAll(@BeanParam FilterBean filterBean) {
		if (filterBean.getYear() > 0) {
			return profileService.getProfilesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return profileService.getProfilesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return profileService.getAll();
	}

	@GET
	@Path("/{profileName}")
	public Response get(@PathParam("profileName") String profileName, @Context UriInfo uriInfo) {
		 Profile entity = profileService.get(profileName);
		 entity.addLink(getUriForSelf(uriInfo), "self");
		 return Response.ok()
				 		.entity(entity)
				 		.build();
	}

	@POST
	public Response add(Profile profile, @Context UriInfo uriInfo) {
		Profile entity = profileService.add(profile);
		System.out.println("hello");
		String profileName = profile.getProfileName();
		URI uri = uriInfo.getAbsolutePathBuilder().path(profileName).build();
		return Response.created(uri)
						.entity(entity)
						.build();
	}

	@PUT
	@Path("/{profileName}")
	public Profile update(@PathParam("profileName") String profileName, Profile profile) {
		profile.setProfileName(profileName);
		return profileService.update(profile);
	}
	
	@PUT
	@Path("/edit/{profileName}")
	public Profile edit(@PathParam("profileName") String profileName, Profile profile) {
		profile.setProfileName(profileName);
		return profileService.edit(profile);
	}

	@DELETE
	@Path("/{profileName}")
	public void delete(@PathParam("profileName") String profileName) {
		profileService.remove(profileName);
	}
	
	public String getUriForSelf(UriInfo uriInfo) {
		String uri = uriInfo.getAbsolutePathBuilder().build().toString();
		return uri;
	}
}
