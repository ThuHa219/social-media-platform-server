package edu.hanu.social_media_platform.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.hanu.social_media_platform.model.Comment;
import edu.hanu.social_media_platform.utils.DbUtils;

public class CommentDAO implements DAO<Comment> {
	private static final String INSERT_SQL_QUERY = "INSERT INTO comment(comment, time_created, profilename, status_id) VALUES(?, now(), ?, ?)";
	private static final String UPDATE_SQL_QUERY = "UPDATE comment SET comment = ?," + "	profilename = ?,"
			+ "	status_id = ? WHERE comment.id = ?";
	private static final String SELECT_SQL_QUERY = "SELECT * FROM comment WHERE comment.id = ?";
	private static final String SELECT_ALL_SQL_QUERY = "SELECT * FROM comment";
	private static final String DELETE_SQL_QUERY = "DELETE FROM comment WHERE comment.id = ?";
	private static final String DELETE_ALL_SQL_QUERY = "DELETE FROM comment";
	private StatusDAO statusDAO = new StatusDAO();
	private ProfileDAO profileDAO = new ProfileDAO();

	@Override
	public Comment get(long id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Comment comment = new Comment();
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("CommentDAO.get: connection is null");
			}
			ps = conn.prepareStatement(SELECT_SQL_QUERY);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			System.out.println(ps.toString());
			while (rs.next()) {
				comment.setId(rs.getLong("id"));
				comment.setCreated(rs.getDate("time_created").toString());
				comment.setComment(rs.getString("comment"));
				comment.setStatus(statusDAO.get(rs.getLong("status_id")));
				comment.setProfile(profileDAO.get(rs.getString("profilename")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtils.closeResultSet(rs);
				DbUtils.closePreparedStatement(ps);
				DbUtils.closeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return comment;
	}

	@Override
	public List<Comment> getAll() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Comment> comments = new ArrayList<>();
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("CommentDAO.getAll: connection is null");
			}
			ps = conn.prepareStatement(SELECT_ALL_SQL_QUERY);
			rs = ps.executeQuery();
			System.out.println(ps.toString());
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getLong("id"));
				comment.setCreated(rs.getDate("time_created").toString());
				comment.setComment(rs.getString("comment"));
				comment.setStatus(statusDAO.get(rs.getLong("status_id")));
				comment.setProfile(profileDAO.get(rs.getString("profilename")));
				comments.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtils.closeResultSet(rs);
				DbUtils.closePreparedStatement(ps);
				DbUtils.closeConnection(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return comments;
	}

	@Override
	public long save(Comment c) {
		Connection conn = null;
		PreparedStatement ps = null;
		long id = 0;
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("CommentDAO.save: connection is null");
			}
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(INSERT_SQL_QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, c.getComment());
			ps.setString(2, c.getProfile().getProfileName());
			ps.setLong(3, c.getStatus().getId());

			ps.execute();
			System.out.println(ps.toString());
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
				c.setId(id);
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				DbUtils.closePreparedStatement(ps);
				DbUtils.closeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	@Override
	public void update(Comment c) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("CommentDAO.update: connection is null");
			}
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(UPDATE_SQL_QUERY);
			ps.setString(1, c.getComment());
			ps.setString(2, c.getProfile().getProfileName());
			ps.setLong(3, c.getStatus().getId());
			ps.setLong(4, c.getId());
			ps.execute();
			System.out.println(ps.toString());
			conn.commit();
		} catch (SQLException e) {
			try {
				if (conn != null) {
					conn.rollback();
					e.printStackTrace();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				DbUtils.closePreparedStatement(ps);
				DbUtils.closeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(long id) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("CommentDAO.delete: connection is null");
			}
			ps = conn.prepareStatement(DELETE_SQL_QUERY);
			ps.setLong(1, id);
			ps.execute();
			System.out.println(ps.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtils.closePreparedStatement(ps);
				DbUtils.closeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAll() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("CommentDAO.deleteAll: connection is null");
			}
			ps = conn.prepareStatement(DELETE_ALL_SQL_QUERY);
			ps.execute();
			System.out.println(ps.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtils.closePreparedStatement(ps);
				DbUtils.closeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		StatusDAO statusDAO = new StatusDAO();
		ProfileDAO profileDAO = new ProfileDAO();
		CommentDAO commentDAO = new CommentDAO();
		List<Comment> comments = commentDAO.getAll();
		for (Comment comment : comments) {
			System.out.println(comment.toString());
		}
//		Comment comment = new Comment();
//		comment.setComment("ha ha");
//		comment.setStatus(statusDAO.get(1));
//		comment.setProfile(profileDAO.get("ThuHa219"));
//		System.out.println(profileDAO.get("ThuHa219"));
//		commentDAO.save(comment);
//		
		//System.out.println(commentDAO.get(1).toString());
	}
}
