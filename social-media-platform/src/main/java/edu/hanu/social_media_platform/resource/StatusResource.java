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
import edu.hanu.social_media_platform.model.Status;
import edu.hanu.social_media_platform.service.StatusService;

@Path("/statuses")
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class StatusResource {
		private StatusService statusService = new StatusService();

		@GET
		public List<Status> getAll(@BeanParam FilterBean filterBean) {
			if (filterBean.getYear() > 0) {
				return statusService.getStatusForYear(filterBean.getYear());
			}
			if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
				return statusService.getStatusPaginated(filterBean.getStart(), filterBean.getSize());
			}
			return statusService.getAll();
		}

		@GET
		@Path("/{statusId}")
		public Response get(@PathParam("statusId") long statusId, @Context UriInfo uriInfo) {
			Status entity = statusService.get(statusId);
			entity.addLink(getUriForSelf(uriInfo), "self");
			entity.addLink(getUriForProfile(uriInfo, entity), "profile");
			return Response.ok()
							.entity(entity)
							.build();
		}

		private String getUriForProfile(UriInfo uriInfo, Status status) {
			String uri = uriInfo.getBaseUriBuilder()
					.path(ProfileResource.class)
					.path(status.getProfile().getProfileName())
					.build()
					.toString();
			return uri;
		}

		private String getUriForSelf(UriInfo uriInfo) {
			String uri = uriInfo.getAbsolutePathBuilder().build().toString();
			return uri;
		}
		
//		private String getUriForComment(UriInfo uriInfo, Message message) {
//			String uri = uriInfo.getBaseUriBuilder()
//							.path(MessageResource.class)
//							.path(MessageResource.class, "getCommentResource")
//							.path(CommentResource.class)
//							.resolveTemplate("messageId", message.getId())
//							.build()
//							.toString();
//			return uri;
//		}
		
//		private String getUriForLike(UriInfo uriInfo, Message message) {
//			String uri = uriInfo.getBaseUriBuilder()
//								.path(MessageResource.class)
//								.path(MessageResource.class, "getLikeResource")
//								.resolveTemplate("messageId", message.getId())
//								.build()
//								.toString();
//			return uri;
//		}

		@POST
		public Response add(Status status, @Context UriInfo uriInfo) {
			Status entity = statusService.add(status);
			System.out.println("hello");
			entity.addLink(getUriForSelf(uriInfo), "self");
			entity.addLink(getUriForProfile(uriInfo, entity), "profile");
			String newId = String.valueOf(status.getId());
			URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
			return Response.created(uri)
						.entity(entity)
						.build();
		}

		@PUT
		@Path("/{statusId}")
		public Status update(@PathParam("statusId") long id, Status status) {
			status.setId(id);
			return statusService.update(status);
		}

		@DELETE
		@Path("/{messageId}")
		public void deleteMessage(@PathParam("messageId") long id) {
			statusService.remove(id);
		}
}
