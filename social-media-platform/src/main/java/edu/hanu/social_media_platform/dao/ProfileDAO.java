package edu.hanu.social_media_platform.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import edu.hanu.social_media_platform.model.Profile;
import edu.hanu.social_media_platform.utils.DbUtils;

public class ProfileDAO implements DAO<Profile>{
	private static final String INSERT_SQL_QUERY = "INSERT INTO profile(firstname, lastname, time_created, profilename, email, phoneNumber, address, password, answer, question) VALUES(?, ?, now(), ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL_QUERY = "UPDATE profile SET firstname = ?," + "	lastname = ?,"
			+ "	profilename = ?," + " email = ?," + " phoneNumber = ?," + " address = ?," + "answer = ?, " + "question = ?, " + " password = ? WHERE profile.profilename = ?";
	private static final String SELECT_SQL_QUERY = "SELECT * FROM profile WHERE profile.profilename = ?";
	private static final String SELECT_ALL_SQL_QUERY = "SELECT * FROM profile";
	private static final String DELETE_SQL_QUERY = "DELETE FROM profile WHERE profile.profilename = ?";
	private static final String DELETE_ALL_SQL_QUERY = "DELETE FROM profile";

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
				profile.setFirstName(rs.getString("firstname"));
				profile.setLastName(rs.getString("lastname"));
				profile.setCreated(rs.getString("time_created"));
				profile.setProfileName(rs.getString("profilename"));
				profile.setEmail(rs.getString("email"));
				profile.setPhoneNumber(rs.getString("phoneNumber"));
				profile.setAddress(rs.getString("address"));
				profile.setPassword(rs.getString("password"));
				profile.setAnswer(rs.getString("answer"));
				profile.setQuestion(rs.getString("question"));
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
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("ProfileDAO.save: connection is null");
			}
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(INSERT_SQL_QUERY);
			ps.setString(1, p.getFirstName());
			ps.setString(2, p.getLastName());
			ps.setString(3, p.getProfileName());
			ps.setString(4, p.getEmail());
			ps.setString(5, p.getPhoneNumber());
			ps.setString(6, p.getAddress());
			ps.setString(7, p.getPassword());
			ps.setString(8, p.getAnswer());
			ps.setString(9, p.getQuestion());
			
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
			ps.setString(8, p.getAnswer());
			ps.setString(9, p.getQuestion());
			ps.setString(10, p.getProfileName());
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
			ps = conn.prepareStatement(SELECT_SQL_QUERY);
			ps.setString(1, profilename);
			rs = ps.executeQuery();
			System.out.println(ps.toString());
			while (rs.next()) {
				profile.setFirstName(rs.getString("firstname"));
				profile.setLastName(rs.getString("lastname"));
				profile.setCreated(rs.getString("time_created"));
				profile.setProfileName(rs.getString("profilename"));
				profile.setEmail(rs.getString("email"));
				profile.setPhoneNumber(rs.getString("phoneNumber"));
				profile.setAddress(rs.getString("address"));
				profile.setPassword(rs.getString("password"));
				profile.setAnswer(rs.getString("answer"));
				profile.setQuestion(rs.getString("question"));
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
			ps = conn.prepareStatement(DELETE_SQL_QUERY);
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
//		p.setQuestion("what is favorite book ?");
//		p.setAnswer("harry potter");
		ProfileDAO dao = new ProfileDAO();
//		dao.save(p);
//		
		System.out.println(dao.get("ThuHa219").toString());
		
		Client client = ClientBuilder.newClient();
		final WebTarget baseTarget = client.target("http://localhost:8080/social-media-platform-server/webapi");
		WebTarget resourceTarget = baseTarget.path("/{resourceName}");
		WebTarget resourceTargetId = resourceTarget.path("/{resourceId}");
		
		System.out.println("hello");
		Profile profile = resourceTargetId.resolveTemplate("resourceName", "profiles")
				.resolveTemplate("resourceId", "ThuHa219").request(MediaType.APPLICATION_JSON).get(Profile.class);
		System.out.println(profile);
		System.out.println("hello");
		System.out.println(profile.toString());
	}

	@Override
	public Profile get(long id) {
		// do nothing
		return null;
	}

	@Override
	public void delete(long id) {
		//do nothing
	}
}
