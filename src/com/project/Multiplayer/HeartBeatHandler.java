package com.project.Multiplayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.project.BoardController.GameType;
import com.project.Main.Main;
import com.project.TeamController.TeamType;

public class HeartBeatHandler {

	private SQLHandler sqlHandler;
	
	private int currentPlayersBeat;
	
	private int secondPlayersBeat;
	
	private boolean firstConnected;
	
	public HeartBeatHandler(SQLHandler sqlHandler) {
		secondPlayersBeat = -1;
		currentPlayersBeat = 0;
		firstConnected = false;
		this.sqlHandler = sqlHandler;
		sendHeartBeat();
		checkOtherBeat();
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
			String selectDatabase = "SELECT * FROM david.CHESS_DATABASE where SESSION_ID='" + sqlHandler.getSessionUUID() + "'";
		    Statement stmt;
		    try {
				stmt = sqlHandler.getSQLConnection().createStatement();
				ResultSet rs = stmt.executeQuery(selectDatabase);
				int testBeat = -1;
			      while(rs.next()) {
			    	  if(sqlHandler.getTeamType().equals(TeamType.BLACK)) {
			    		  testBeat = rs.getInt("WHITE_STATUS");
			    	  }else {
			    		  testBeat = rs.getInt("BLACK_STATUS");
			    	  }
					  break;
			      }
			      
			      // Check when the player has or hasn't joined.
			      // -1 is when still waiting for a player to join
			      if(testBeat >= 0) {
			    	  if(firstConnected == false) {
			    		  firstConnected = true;
			    		  Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Second Player Connected. Starting game");
			    	  }else {
			    		  // Player has joined
			    		  if(testBeat > secondPlayersBeat) {
					    	  // We're good here
					    	  secondPlayersBeat = testBeat;
					      }else {
					    	  // CONNECTION WITH OTHER PLAYER ISSUE!
					    	  Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Second Player Connection Lost. Aborting Game.");
					    	  Main.createNewGame(GameType.PLAYER_VS_PLAYER);
					      }  
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