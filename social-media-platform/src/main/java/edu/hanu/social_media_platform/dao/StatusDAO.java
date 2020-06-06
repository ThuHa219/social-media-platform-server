package edu.hanu.social_media_platform.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.hanu.social_media_platform.model.Status;
import edu.hanu.social_media_platform.utils.DbUtils;

public class StatusDAO implements DAO<Status> {
	private static final String INSERT_SQL_QUERY = "INSERT INTO status(status, profilename, time_created) VALUES(?, ?, now())";
	private static final String UPDATE_SQL_QUERY = "UPDATE status SET status = ?,"
			+ " profilename = ? WHERE status.id = ?";
	private static final String SELECT_SQL_QUERY = "SELECT * FROM status WHERE status.id = ?";
	private static final String SELECT_ALL_SQL_QUERY = "SELECT * FROM status";
	private static final String DELETE_SQL_QUERY = "DELETE FROM status WHERE status.id = ?";
	private static final String DELETE_SQL_QUERY_ALL_COMMENT = "DELETE FROM comment WHERE status_id = ?";
	private static final String DELETE_ALL_SQL_QUERY = "DELETE FROM status";
	ProfileDAO dao = new ProfileDAO();
//	CommentDAO commentDao = new CommentDAO();

	@Override
	public Status get(long id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Status status = new Status();
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("StatusDAO.get: connection is null");
			}
			ps = conn.prepareStatement(SELECT_SQL_QUERY);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			System.out.println(ps.toString());
			while (rs.next()) {
				status.setId(rs.getLong("id"));
				status.setStatus(rs.getString("status"));
				status.setProfile(dao.get(rs.getString("profilename")));
				status.setCreated(rs.getDate("time_created").toString());
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
		return status;
	}

	@Override
	public List<Status> getAll() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Status> statuses = new ArrayList<>();
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("StatusDAO.getAll: connection is null");
			}
			ps = conn.prepareStatement(SELECT_ALL_SQL_QUERY);
			rs = ps.executeQuery();
			System.out.println(ps.toString());
			while (rs.next()) {
				Status status = new Status();
				status.setId(rs.getLong("id"));
				status.setStatus(rs.getString("status"));
				status.setProfile(dao.get(rs.getString("profilename")));
				status.setCreated(rs.getDate("time_created").toString());
				statuses.add(status);
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
		return statuses;
	}

	@Override
	public long save(Status s) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("StatusDAO.save: connection is null");
			}
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(INSERT_SQL_QUERY);
			ps.setString(1, s.getStatus());
			ps.setString(2, s.getProfile().getProfileName());

			ps.execute();
			System.out.println(ps.toString());
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
			return 0;
		} finally {
			try {
				DbUtils.closePreparedStatement(ps);
				DbUtils.closeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}

	@Override
	public void update(Status s) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("StatusDAO.update: connection is null");
			}
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(UPDATE_SQL_QUERY);
			ps.setString(1, s.getStatus());
			ps.setString(2, s.getProfile().getProfileName());
			ps.setLong(3, s.getId());
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
				throw new NullPointerException("StatusDAO.delete: connection is null");
			}
			ps = conn.prepareStatement(DELETE_SQL_QUERY);
			deleteComment(id);
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

	public void deleteComment(long statusId) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("StatusDAO.delete: connection is null");
			}
			ps = conn.prepareStatement(DELETE_SQL_QUERY_ALL_COMMENT);
			ps.setLong(1, statusId);
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
		Status status = new Status();
		status.setStatus("test test");
		ProfileDAO profileDao = new ProfileDAO();
		status.setProfile(profileDao.get("ThuHa219"));
		System.out.println(profileDao.get("ThuHa219"));
		StatusDAO dao = new StatusDAO();
		dao.save(status);
		System.out.println(dao.get(1).toString());
	}
}
