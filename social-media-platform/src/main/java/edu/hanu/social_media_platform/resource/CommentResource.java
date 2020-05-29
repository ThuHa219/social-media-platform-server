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

import edu.hanu.social_media_platform.model.Comment;
import edu.hanu.social_media_platform.model.FilterBean;
import edu.hanu.social_media_platform.service.CommentService;

@Path("/comments")
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class CommentResource {
	private CommentService commentService = new CommentService();

	@GET
	public List<Comment> getAll(@BeanParam FilterBean filterBean) {
		if (filterBean.getYear() > 0) {
			return commentService.getCommentForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return commentService.getCommentPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return commentService.getAll();
	}

	@GET
	@Path("/{commentId}")
	public Response get(@PathParam("commentId") long commentId, @Context UriInfo uriInfo) {
		 Comment entity = commentService.get(commentId);
		 entity.addLink(getUriForSelf(uriInfo), "self");
		 return Response.ok()
				 		.entity(entity)
				 		.build();
	}

	@POST
	public Response add(Comment comment, @Context UriInfo uriInfo) {
		Comment entity = commentService.add(comment);
		long id = entity.getId();
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
		return Response.created(uri)
						.entity(entity)
						.build();
	}

	@PUT
	@Path("/{commentId}")
	public Comment update(@PathParam("commentId") long commentId, Comment comment) {
		comment.setId(commentId);
		return commentService.update(comment);
	}

	@DELETE
	@Path("/{commentId}")
	public void delete(@PathParam("commentId") long commentId) {
		commentService.remove(commentId);
	}
	
	public String getUriForSelf(UriInfo uriInfo) {
		String uri = uriInfo.getAbsolutePathBuilder().build().toString();
		return uri;
	}
}

