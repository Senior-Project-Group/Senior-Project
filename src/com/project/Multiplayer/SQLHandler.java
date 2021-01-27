package com.project.Multiplayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.project.Main.Main;
import com.project.TeamController.TeamType;

public class SQLHandler {

	private String SESSION_UUID;
	private Connection connection;
	private static String password = "ICZ1kVPei85PiObO"; // Encoded password
	
	private boolean isActive;
	
	private HeartBeatHandler heartBeat;
	
	private TeamType playersTeam;

	// Used if player is hosting his own game
	public SQLHandler() {
		isActive = true;
		SESSION_UUID = new UUIDUtility().generateTypeUUID().toString();
		connect(password);
		playersTeam = TeamType.WHITE;
		heartBeat = new HeartBeatHandler(this);
		// Send game information to SQL
		
		String run = "INSERT INTO david.CHESS_DATABASE (SESSION_ID, NEXT_MOVE, WHITE_STATUS, BLACK_STATUS) "
				 + "VALUES ("
				 + "'" + getSessionUUID() + "', "
				 + "'SETUP', "
				 + "'0', "
				 + "'-1'"
				 + ")";
		
		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(run);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// Used if player joining another person's game
	public SQLHandler(String token) {
		isActive = true;
		SESSION_UUID = token;
		connect(password);
		
		if(!checkIfAllowedToJoin(token)) {
			destroy();
			Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Error: You aren't allowed to join that game.");
			return;
		}
		playersTeam = TeamType.BLACK;
		heartBeat = new HeartBeatHandler(this);
	}
	
	
	public String getSessionUUID() {
		return SESSION_UUID;
	}
	
	// Check if the session exists in the database
	public boolean checkIfAllowedToJoin(String id) {
		String selectDatabase = "SELECT * FROM david.CHESS_DATABASE where SESSION_ID='" + getSessionUUID() + "'";
	    Statement stmt;
			try {
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(selectDatabase);
				int testBeat = -1;
			      while(rs.next()) {
			    	  testBeat = rs.getInt("BLACK_STATUS");
			    	  if(testBeat <= 0) {
			    		  return true;
			    	  }
					  break;
			      }
				
				
			} catch (SQLException e) {
				// Error with connection
				e.printStackTrace();
				return false;
			}
		
		return false;
	}
	
	private void connect(String password) {
	      String connectionURL = "jdbc:mysql://jump.miovo.me";
	      try {
	    	 connection = DriverManager.getConnection(connectionURL, "david", password);
		} catch (SQLException e) {
			// TODO Add SQL Updater to attempt reconnect.
			// ERROR: Unable to connect to database
		}
	}
	
	public TeamType getTeamType() {
		return playersTeam;
	}
	
	public void destroy() {
		isActive = false;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public HeartBeatHandler getHeartBeatHandler() {
		return heartBeat;
	}
	
	public Connection getSQLConnection() {
		return connection;
	}
}