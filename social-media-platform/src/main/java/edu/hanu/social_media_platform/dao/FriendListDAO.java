package edu.hanu.social_media_platform.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.hanu.social_media_platform.model.FriendList;
import edu.hanu.social_media_platform.model.Like;
import edu.hanu.social_media_platform.model.Profile;
import edu.hanu.social_media_platform.utils.DbUtils;

public class FriendListDAO {
	private static final String INSERT_SQL_QUERY = "INSERT INTO friendlist(profilename, friendname) VALUES(?, ?)";
	private static final String UPDATE_SQL_QUERY = "UPDATE friendlist SET friendname = ? WHERE friendlist.profilename = ?";
	private static final String SELECT_SQL_QUERY = "SELECT * FROM friendlist WHERE friendlist.profilename = ?";
	private static final String SELECT_ALL_SQL_QUERY = "SELECT * FROM friendlist";
	private static final String DELETE_SQL_QUERY = "DELETE FROM friendlist WHERE friendlist.profilename = ? AND friendlist.friendname = ?";
	private static final String DELETE_ALL_SQL_QUERY = "DELETE FROM friendlist";
	private ProfileDAO dao = new ProfileDAO();
	
	public FriendList get(String profilename) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		FriendList friendList = new FriendList();
		List<Profile> friend = new ArrayList<>();
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("FriendListDAO.get: connection is null");
			}
			ps = conn.prepareStatement(SELECT_SQL_QUERY);
			friendList.setProfile(dao.get(profilename));
			ps.setString(1, profilename);
			rs = ps.executeQuery();
			System.out.println(ps.toString());
			while (rs.next()) {
				friend.add(dao.get(rs.getString("friendname")));
			}
			friendList.setFriend(friend);
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
		return friendList;
	}
	
	public List<FriendList> getAll() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<FriendList> friendlist = new ArrayList<>();
		Set<String> friendname = new HashSet<>();
		
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("FriendListDAO.getAll: connection is null");
			}
			ps = conn.prepareStatement(SELECT_ALL_SQL_QUERY);
			rs = ps.executeQuery();
			System.out.println(ps.toString());
			while (rs.next()) {
				friendname.add(rs.getString("profilename"));
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
		for(String s : friendname) {
			FriendList friendList = get(s);
			friendlist.add(friendList);
		}
		return friendlist;
	}
	
	public long save(String profilename, String friendname) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("FriendListDAO.save: connection is null");
			}
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(INSERT_SQL_QUERY);
			ps.setString(1, profilename);
			ps.setString(2, friendname);

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
	
	public void save(FriendList f) {
		delete(f);
		for(Profile p : f.getFriend()) {
			save(f.getProfile().getProfileName(), p.getProfileName());
		}
	}

	public void update(String profilename, String friendname) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("FriendListDAO.update: connection is null");
			}
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(UPDATE_SQL_QUERY);
			ps.setString(1, friendname);
			ps.setString(2, profilename);
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
	
	public void update(FriendList f) {
		for(Profile p : f.getFriend()) {
			update(f.getProfile().getProfileName(), p.getProfileName());
		}
	}

	public void delete(String profilename, String friendname) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DbUtils.initialise();
			if (conn == null) {
				throw new NullPointerException("LikeDAO.delete: connection is null");
			}
			ps = conn.prepareStatement(DELETE_SQL_QUERY);
			ps.setString(1, profilename);
			ps.setString(2, friendname);
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
	
	public void delete(FriendList f) {
		for(Profile p : f.getFriend()) {
			delete(f.getProfile().getProfileName(), p.getProfileName());
		}
	}

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
		FriendListDAO dao = new FriendListDAO();
//		dao.delete("ThuHa219", "QuangChien19");
		
	}
}
