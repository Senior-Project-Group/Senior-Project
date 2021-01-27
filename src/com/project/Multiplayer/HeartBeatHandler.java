package com.project.Multiplayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.project.TeamController.TeamType;

public class HeartBeatHandler {

	private SQLHandler sqlHandler;
	
	private int currentPlayersBeat;
	
	private int secondPlayersBeat;
	
	public HeartBeatHandler(SQLHandler sqlHandler) {
		secondPlayersBeat = -1;
		currentPlayersBeat = 0;
		this.sqlHandler = sqlHandler;
	}
	
	public void sendHeartBeat() {
		if(sqlHandler.isActive()) {
			// Send beat
			
			currentPlayersBeat++;
			String run = "UPDATE david.CHESS_DATABASE SET " + sqlHandler.getTeamType().toString() + 
					"_STATUS = '" + (currentPlayersBeat) + 
					"' WHERE SESSION_ID = '" + sqlHandler.getSessionUUID() + "'";
			Statement stmt;
			try {
				stmt = sqlHandler.getSQLConnection().createStatement();
				stmt.executeUpdate(run);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			// After 1 second, send beat again
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	try {
			            		sendHeartBeat();
			            	}catch(Exception ex) {}
			            }
			        }, 1000
			);
		}
	}
	
	public void checkOtherBeat() {
		if(sqlHandler.isActive()) {
			// Check for the other player's heart beat
			String selectDatabase = "SELECT * FROM david.CHESS_DATABASE where AdminMode='true'";
		    Statement stmt;
		    try {
				stmt = sqlHandler.getSQLConnection().createStatement();
				ResultSet rs = stmt.executeQuery(selectDatabase);
				int testBeat = 0;
			      while(rs.next()) {
			    	  if(sqlHandler.getTeamType().equals(TeamType.BLACK)) {
			    		  testBeat = rs.getInt("WHITE_STATUS");
			    	  }else {
			    		  testBeat = rs.getInt("BLACK_STATUS");
			    	  }
					  break;
			      }
			      
			      // Other player hasn't joined yet
			      if(testBeat < 0) {
			    	  
			      }else {
			    	  // Player has joined
			    	  if(testBeat > secondPlayersBeat) {
				    	  // We're good here
				    	  secondPlayersBeat = testBeat;
				      }else {
				    	  
				      }
			      }
			      
			      
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			// After 3 seconds, check heart beat of other player
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	try {
			            		checkOtherBeat(); 
			            	}catch(Exception ex) {}
			            }
			        }, 3000
			);
			
			
		}
	}
	
	public SQLHandler getSQLHandler() {
		return sqlHandler;
	}
}