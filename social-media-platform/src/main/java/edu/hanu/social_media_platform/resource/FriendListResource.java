package edu.hanu.social_media_platform.resource;

import java.net.URI;
import java.util.List;

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

import edu.hanu.social_media_platform.model.FriendList;
import edu.hanu.social_media_platform.service.FriendListService;

@Path("/friendlist")
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public class FriendListResource {
	FriendListService friendListService = new FriendListService();

	@GET
	public List<FriendList> getAll() {
		return friendListService.getAll();
	}

	@GET
	@Path("/{profilename}")
	public Response getProfile(@PathParam("profilename") String profilename, @Context UriInfo uriInfo) {
		FriendList entity = friendListService.get(profilename);
		return Response.ok()
				.entity(entity)
				.build();
	}

	@POST
	public Response add(FriendList f, @Context UriInfo uriInfo) {
		friendListService.add(f);
		String profilename = f.getProfile().getProfileName();
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(profilename)).build();
		return Response.created(uri).entity(f).build();
	}

	@PUT
	@Path("/{profilename}")
	public FriendList updateProfile(@PathParam("profilename") String profilename, FriendList f) {
		f.getProfile().setProfileName(profilename);
		return friendListService.update(f);
	}

	@DELETE
	@Path("/{profilename}/{friendname}")
	public void deleteProfile(@PathParam("profilename") String profilename, @PathParam("friendname") String friendname) {
		friendListService.remove(profilename, friendname);
	}

	public String getUriForSelf(UriInfo uriInfo) {
		String uri = uriInfo.getAbsolutePathBuilder().build().toString();
		return uri;
	}
}
