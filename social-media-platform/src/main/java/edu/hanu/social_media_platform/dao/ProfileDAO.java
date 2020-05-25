package edu.hanu.social_media_platform.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.hanu.social_media_platform.model.Profile;
import edu.hanu.social_media_platform.utils.DbUtils;

public class ProfileDAO implements DAO<Profile>{
	private static final String INSERT_SQL_QUERY = "INSERT INTO profile(firstname, lastname, time_created, profilename, email, phoneNumber, address, password) VALUES(?, ?, now(), ?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL_QUERY = "UPDATE profile SET firstname = ?," + "	lastname = ?,"
			+ "	profilename = ?," + " email = ?," + " phoneNumber = ?," + " address = ?," + " password = ? WHERE profile.id = ?";
	private static final String SELECT_SQL_QUERY = "SELECT * FROM profile WHERE profile.id = ?";
	private static final String SELECT_SQL_BY_NAME_QUERY = "SELECT * FROM profile WHERE profile.profilename = ?";
	private static final String SELECT_ALL_SQL_QUERY = "SELECT * FROM profile";
	private static final String DELETE_SQL_QUERY = "DELETE FROM profile WHERE profile.id = ?";
	private static final String DELETE_SQL_BY_NAME_QUERY = "DELETE FROM profile WHERE profile.profilename = ?";
	private static final String DELETE_ALL_SQL_QUERY = "DELETE FROM profile";

	@Override
	public Profile get(long id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Profile profile = new Profile();
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("ProfileDAO.get: connection is null");
			}
			ps = conn.prepareStatement(SELECT_SQL_QUERY);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			System.out.println(ps.toString());
			while (rs.next()) {
				profile.setId(rs.getLong("id"));
				profile.setFirstName(rs.getString("firstname"));
				profile.setLastName(rs.getString("lastname"));
				profile.setCreated(rs.getDate("time_created"));
				profile.setProfileName(rs.getString("profilename"));
				profile.setEmail(rs.getString("email"));
				profile.setPhoneNumber(rs.getString("phoneNumber"));
				profile.setAddress(rs.getString("address"));
				profile.setPassword(rs.getString("password"));
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
		return profile;
	}

	@Override
	public List<Profile> getAll() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Profile> profiles = new ArrayList<>();
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("ProfileDAO.getAll: connection is null");
			}
			ps = conn.prepareStatement(SELECT_ALL_SQL_QUERY);
			rs = ps.executeQuery();
			System.out.println(ps.toString());
			while (rs.next()) {
				Profile profile = new Profile();
				profile.setId(rs.getLong("id"));
				profile.setFirstName(rs.getString("firstname"));
				profile.setLastName(rs.getString("lastname"));
				profile.setCreated(rs.getDate("time_created"));
				profile.setProfileName(rs.getString("profilename"));
				profile.setEmail(rs.getString("email"));
				profile.setPhoneNumber(rs.getString("phoneNumber"));
				profile.setAddress(rs.getString("address"));
				profile.setPassword(rs.getString("password"));
				profiles.add(profile);
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
		return profiles;
	}

	@Override
	public long save(Profile p) {
		Connection conn = null;
		PreparedStatement ps = null;
		long id = 0;
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("ProfileDAO.save: connection is null");
			}
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(INSERT_SQL_QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getFirstName());
			ps.setString(2, p.getLastName());
			ps.setString(3, p.getProfileName());
			ps.setString(4, p.getEmail());
			ps.setString(5, p.getPhoneNumber());
			ps.setString(6, p.getAddress());
			ps.setString(7, p.getPassword());

			ps.execute();
			System.out.println(ps.toString());
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
				p.setId(id);
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
	public void update(Profile p) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("ProfileDAO.update: connection is null");
			}
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(UPDATE_SQL_QUERY);
			ps.setString(1, p.getFirstName());
			ps.setString(2, p.getLastName());
			ps.setString(3, p.getProfileName());
			ps.setString(4, p.getEmail());
			ps.setString(5, p.getPhoneNumber());
			ps.setString(6, p.getAddress());
			ps.setString(7, p.getPassword());
			ps.setLong(8, p.getId());
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
				throw new NullPointerException("ProfileDAO.delete: connection is null");
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
				throw new NullPointerException("ProfileDAO.deleteAll: connection is null");
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
	
	public Profile get(String profilename) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Profile profile = new Profile();
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("ProfileDAO.get: connection is null");
			}
			ps = conn.prepareStatement(SELECT_SQL_BY_NAME_QUERY);
			ps.setString(1, profilename);
			rs = ps.executeQuery();
			System.out.println(ps.toString());
			while (rs.next()) {
				profile.setId(rs.getLong("id"));
				profile.setFirstName(rs.getString("firstname"));
				profile.setLastName(rs.getString("lastname"));
				profile.setCreated(rs.getDate("time_created"));
				profile.setProfileName(rs.getString("profilename"));
				profile.setEmail(rs.getString("email"));
				profile.setPhoneNumber(rs.getString("phoneNumber"));
				profile.setAddress(rs.getString("address"));
				profile.setPassword(rs.getString("password"));
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
		return profile;	
	}
	
	public void delete(String profilename) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("ProfileDAO.delete: connection is null");
			}
			ps = conn.prepareStatement(DELETE_SQL_BY_NAME_QUERY);
			ps.setString(1, profilename);
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
//		Profile p = new Profile();
//		p.setFirstName("Ha");
//		p.setLastName("Nguyen");
//		p.setProfileName("ThuHa219");
//		p.setPassword("123456");
//		
		ProfileDAO dao = new ProfileDAO();
//		dao.save(p);
		
		System.out.println(dao.get("ThuHa219").toString());
	}
}
