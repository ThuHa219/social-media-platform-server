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
import edu.hanu.social_media_platform.model.Like;
import edu.hanu.social_media_platform.model.Profile;
import edu.hanu.social_media_platform.service.LikeService;

@Path("/likes")
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class LikeResource {
	LikeService likeService = new LikeService();

	@GET
	public List<Like> getAll(@BeanParam FilterBean filterBean) {
		if (filterBean.getYear() > 0) {
			return likeService.getLikeForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return likeService.getLikePaginated(filterBean.getStart(), filterBean.getSize());
		}
		return likeService.getAll();
	}

	@GET
	@Path("/{likeId}")
	public Response getProfile(@PathParam("likeId") long likeId, @Context UriInfo uriInfo) {
		 Like entity = likeService.get(likeId);
		 entity.addLink(getUriForSelf(uriInfo), "self");
		 return Response.ok()
				 		.entity(entity)
				 		.build();
	}

	@POST
	public Response add(Like like, @Context UriInfo uriInfo) {
		Like entity = likeService.add(like);
		long likeId = like.getId();
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(likeId)).build();
		return Response.created(uri)
						.entity(entity)
						.build();
	}

	@PUT
	@Path("/{likeId}")
	public Like updateProfile(@PathParam("likeId") long likeId, Like like) {
		like.setId(likeId);
		return likeService.update(like);
	}

	@DELETE
	@Path("/{likeId}")
	public void deleteProfile(@PathParam("likeId") long likeId) {
		likeService.remove(likeId);
	}
	
	public String getUriForSelf(UriInfo uriInfo) {
		String uri = uriInfo.getAbsolutePathBuilder().build().toString();
		return uri;
	}
}
