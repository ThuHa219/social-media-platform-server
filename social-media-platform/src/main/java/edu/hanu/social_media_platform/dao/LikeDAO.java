package edu.hanu.social_media_platform.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.hanu.social_media_platform.model.Like;
import edu.hanu.social_media_platform.model.Status;
import edu.hanu.social_media_platform.utils.DbUtils;

public class LikeDAO implements DAO<Like>{
	private static final String INSERT_SQL_QUERY = "INSERT INTO likes(time_created, profilename, status_id) VALUES(now(), ?, ?)";
	private static final String UPDATE_SQL_QUERY = "UPDATE likes SET profilename = ?," + " status_id = ? WHERE likes.id = ?";
	private static final String SELECT_SQL_QUERY = "SELECT * FROM likes WHERE likes.id = ?";
	private static final String SELECT_ALL_SQL_QUERY = "SELECT * FROM likes";
	private static final String DELETE_SQL_QUERY = "DELETE FROM likes WHERE likes.id = ?";
	private static final String DELETE_ALL_SQL_QUERY = "DELETE FROM likes";
	private ProfileDAO profileDAO = new ProfileDAO();
	private StatusDAO statusDAO = new StatusDAO();
	
	@Override
	public Like get(long id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Like like = new Like();
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("LikeDAO.get: connection is null");
			}
			ps = conn.prepareStatement(SELECT_SQL_QUERY);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			System.out.println(ps.toString());
			while (rs.next()) {
				like.setId(rs.getLong("id"));
				like.setProfile(profileDAO.get(rs.getString("profilename")));
				like.setStatus(statusDAO.get(rs.getLong("status_id")));
				like.setCreated(rs.getDate("time_created"));
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
		return like;
	}

	@Override
	public List<Like> getAll() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Like> likes= new ArrayList<>();
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("LikeDAO.getAll: connection is null");
			}
			ps = conn.prepareStatement(SELECT_ALL_SQL_QUERY);
			rs = ps.executeQuery();
			System.out.println(ps.toString());
			while (rs.next()) {
				Like like = new Like();
				like.setId(rs.getLong("id"));
				like.setProfile(profileDAO.get(rs.getString("profilename")));
				like.setStatus(statusDAO.get(rs.getLong("status_id")));
				like.setCreated(rs.getDate("time_created"));
				likes.add(like);
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
		return likes;
	}

	@Override
	public long save(Like l) {
		Connection conn = null;
		PreparedStatement ps = null;
		long id = 0;
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("LikeDAO.save: connection is null");
			}
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(INSERT_SQL_QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, l.getProfile().getProfileName());
			ps.setLong(2, l.getStatus().getId());

			ps.execute();
			System.out.println(ps.toString());
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
				l.setId(id);
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
	public void update(Like l) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("LikeDAO.update: connection is null");
			}
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(UPDATE_SQL_QUERY);
			ps.setString(1, l.getProfile().getProfileName());
			ps.setLong(2, l.getStatus().getId());
			ps.setLong(3, l.getId());
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
				throw new NullPointerException("LikeDAO.delete: connection is null");
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
				throw new NullPointerException("StatusDAO.deleteAll: connection is null");
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
		ProfileDAO profileDAO = new ProfileDAO();
		StatusDAO statusDAO = new StatusDAO();
		LikeDAO likeDAO = new LikeDAO();
		
		Like like = new Like();
		like.setProfile(profileDAO.get("ThuHa219"));
		like.setStatus(statusDAO.get(1));
		
//		likeDAO.delete(1);
		long id = likeDAO.save(like);
		
		System.out.println(likeDAO.get(id).toString());
//		likeDAO.delete(2);
	}
}
