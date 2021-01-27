package com.project.Multiplayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.project.BoardController.BoardController;
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
				Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Connection Issue. Abopting Game.");
				getSQLHandler().destroy();
				Main.createNewGame(GameType.PLAYER_VS_PLAYER);
				return;
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
			    		  if(Main.multiplayerGUI != null) {
			    			  Main.multiplayerGUI.getFrame().dispose();
			    		  }
			    		  
			    		  // Start the game here
			    		  
			    		  System.out.println("Creating And Setting Up Board for gamne type: " + GameType.SQL_MULTIPLAYER.toString());
			    			// Delete the old game and reset the AI controller
			    			Main.getBoardController().getNextMoveRenderer().clearCurrentRender();
			    			Main.getBoardController().getBoardObject().getFrame().dispose();
			    			Main.getAIController().clear();
			    			
			    			// Create a new board
			    			Main.setNewBoardController(new BoardController(GameType.SQL_MULTIPLAYER));
			    			  
			    	  }else {
			    		  // Player has joined
			    		  if(testBeat > secondPlayersBeat) {
					    	  // We're good here
					    	  secondPlayersBeat = testBeat;
					      }else {
					    	  // CONNECTION WITH OTHER PLAYER ISSUE!
					    	  Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Second Player Connection Lost. Aborting Game.");
					    	  Main.createNewGame(GameType.PLAYER_VS_PLAYER);
					    	  sqlHandler.destroy();
					    	  // Delete the game from SQL
					    	  String run1 = "DELETE FROM david.CHESS_DATABASE WHERE SESSION_ID = '" + sqlHandler.getSessionUUID() + "'";
								Statement stmt1;
								try {
									stmt1 = sqlHandler.getSQLConnection().createStatement();
									stmt1.executeUpdate(run1);
								} catch (SQLException e) {
									Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Connection Issue. Abopting Game.");
									getSQLHandler().destroy();
									Main.createNewGame(GameType.PLAYER_VS_PLAYER);
									return;
								}
					    	  
					      }  
			    	  }
			      }
			      
			} catch (SQLException e) {
				Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Connection Issue. Abopting Game.");
				getSQLHandler().destroy();
				Main.createNewGame(GameType.PLAYER_VS_PLAYER);
				return;
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