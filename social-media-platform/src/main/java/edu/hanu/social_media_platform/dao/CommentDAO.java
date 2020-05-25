package edu.hanu.social_media_platform.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.hanu.social_media_platform.utils.DbUtils;

public class CommentDAO {
//	private static final String INSERT_SQL_QUERY = "INSERT INTO comment(message, time_created, profile_id, message_id) VALUES(?, ?, ?, ?)";
//	private static final String UPDATE_SQL_QUERY = "UPDATE cases SET new_cases = ?," + "	active_case = ?,"
//			+ "	critical = ?," + "	recovered = ?," + "	total = ? WHERE cases.cases_id = ?";
//	private static final String SELECT_SQL_QUERY = "SELECT * FROM cases WHERE cases.cases_id = ?";
//	private static final String SELECT_ALL_SQL_QUERY = "SELECT * FROM cases";
//	private static final String DELETE_SQL_QUERY = "DELETE FROM cases WHERE cases.cases_id = ?";
//	private static final String DELETE_ALL_SQL_QUERY = "DELETE FROM cases";
//
//	@Override
//	public Cases get(long id) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Cases cases = new Cases();
//		try {
//			conn = DbUtils.initialise();
//			if (conn == null) {
//				throw new NullPointerException("CasesDAO.get: connection is null");
//			}
//			ps = conn.prepareStatement(SELECT_SQL_QUERY);
//			ps.setLong(1, id);
//			rs = ps.executeQuery();
//			System.out.println(ps.toString());
//			while (rs.next()) {
//				cases.setId(rs.getLong("cases_id"));
//				cases.setNewCase(rs.getString("new_cases"));
//				cases.setActive(rs.getInt("active_case"));
//				cases.setCritical(rs.getInt("critical"));
//				cases.setRecovered(rs.getInt("recovered"));
//				cases.setTotal(rs.getInt("total"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				DbUtils.closeResultSet(rs);
//				DbUtils.closePreparedStatement(ps);
//				DbUtils.closeConnection(conn);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return cases;
//	}
//
//	@Override
//	public List<Cases> getAll() {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		List<Cases> allCases = new ArrayList<>();
//		try {
//			conn = DbUtils.initialise();
//			if (conn == null) {
//				throw new NullPointerException("CasesDAO.getAll: connection is null");
//			}
//			ps = conn.prepareStatement(SELECT_ALL_SQL_QUERY);
//			rs = ps.executeQuery();
//			System.out.println(ps.toString());
//			while (rs.next()) {
//				Cases cases = new Cases();
//				cases.setId(rs.getLong("cases_id"));
//				cases.setNewCase(rs.getString("new_cases"));
//				cases.setActive(rs.getInt("active_case"));
//				cases.setCritical(rs.getInt("critical"));
//				cases.setRecovered(rs.getInt("recovered"));
//				cases.setTotal(rs.getInt("total"));
//				allCases.add(cases);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				DbUtils.closeResultSet(rs);
//				DbUtils.closePreparedStatement(ps);
//				DbUtils.closeConnection(conn);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return allCases;
//	}
//
//	@Override
//	public long save(Cases c) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		long id = 0;
//		try {
//			conn = DbUtils.initialise();
//			if (conn == null) {
//				throw new NullPointerException("CasesDAO.save: connection is null");
//			}
//			conn.setAutoCommit(false);
//			ps = conn.prepareStatement(INSERT_SQL_QUERY, Statement.RETURN_GENERATED_KEYS);
//			ps.setString(1, c.getNewCase());
//			ps.setInt(2, c.getActive());
//			ps.setInt(3, c.getCritical());
//			ps.setInt(4, c.getRecovered());
//			ps.setInt(5, c.getTotal());
//
//			ps.execute();
//			System.out.println(ps.toString());
//			ResultSet rs = ps.getGeneratedKeys();
//			if (rs.next()) {
//				id = rs.getLong(1);
//				c.setId(id);
//			}
//			conn.commit();
//		} catch (SQLException e) {
//			try {
//				if (conn != null) {
//					conn.rollback();
//				}
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//		} finally {
//			try {
//				DbUtils.closePreparedStatement(ps);
//				DbUtils.closeConnection(conn);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return id;
//	}
//
//	@Override
//	public void update(Cases c) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//
//		try {
//			conn = DbUtils.initialise();
//			if (conn == null) {
//				throw new NullPointerException("CasesDAO.update: connection is null");
//			}
//			conn.setAutoCommit(false);
//			ps = conn.prepareStatement(UPDATE_SQL_QUERY);
//			ps.setString(1, c.getNewCase());
//			ps.setInt(2, c.getActive());
//			ps.setInt(3, c.getCritical());
//			ps.setInt(4, c.getRecovered());
//			ps.setInt(5, c.getTotal());
//			ps.setLong(6, c.getId());
//			ps.execute();
//			System.out.println(ps.toString());
//			conn.commit();
//		} catch (SQLException e) {
//			try {
//				if (conn != null) {
//					conn.rollback();
//					e.printStackTrace();
//				}
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		} finally {
//			try {
//				DbUtils.closePreparedStatement(ps);
//				DbUtils.closeConnection(conn);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@Override
//	public void delete(long id) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		try {
//			conn = DbUtils.initialise();
//			if (conn == null) {
//				throw new NullPointerException("CasesDAO.delete: connection is null");
//			}
//			ps = conn.prepareStatement(DELETE_SQL_QUERY);
//			ps.setLong(1, id);
//			ps.execute();
//			System.out.println(ps.toString());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				DbUtils.closePreparedStatement(ps);
//				DbUtils.closeConnection(conn);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@Override
//	public void deleteAll() {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		try {
//			conn = DbUtils.initialise();
//			if (conn == null) {
//				throw new NullPointerException("CasesDAO.deleteAll: connection is null");
//			}
//			ps = conn.prepareStatement(DELETE_ALL_SQL_QUERY);
//			ps.execute();
//			System.out.println(ps.toString());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				DbUtils.closePreparedStatement(ps);
//				DbUtils.closeConnection(conn);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
