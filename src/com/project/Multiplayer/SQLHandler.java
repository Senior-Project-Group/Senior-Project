package com.project.Multiplayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.project.BoardController.GameType;
import com.project.ChessPieces.IChessPiece;
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
			Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Error: Can't connect to servers.");
			destroy();
			Main.createNewGame(GameType.PLAYER_VS_PLAYER);
			return;
		}
		checkForPlayerMove();
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
		checkForPlayerMove();
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
	
	
	public void destroy() {
		isActive = false;
		// Remove from database too if possible
		String run1 = "DELETE FROM david.CHESS_DATABASE WHERE SESSION_ID = '" + getSessionUUID() + "'";
		Statement stmt1;
		try {
			stmt1 = getSQLConnection().createStatement();
			stmt1.executeUpdate(run1);
		} catch (SQLException e) {
			// Don't send a notification of failure
			Main.createNewGame(GameType.PLAYER_VS_PLAYER);
			return;
		}
	}
	
	// Check database for attempted moves from the other player
	public void checkForPlayerMove() {
		if(isActive()) {
			// Run check
			
			String selectDatabase = "SELECT * FROM david.CHESS_DATABASE where SESSION_ID = '" + getSessionUUID() + "'";
		    Statement stmt;
				try {
					stmt = getSQLConnection().createStatement();
					ResultSet rs = stmt.executeQuery(selectDatabase);
					String nextMove = "SETUP";
				      while(rs.next()) {
				    	  nextMove = rs.getString("NEXT_MOVE");
				    	  
				      }
				      // Check if still in setup process
				      if(!nextMove.equals("SETUP")) {
				    	  NextMoveParser nextMoveData = new NextMoveParser(nextMove);
				    	  
				    	  // Check if the next move is the other player
				    	  System.out.println(Main.getBoardController().getCurrentPlayerToMove().toString() + " -- " + nextMoveData.getTeamAttemptingMove());
				    	  
				    	  if(Main.getBoardController().getCurrentPlayerToMove().toString().equals(nextMoveData.getTeamAttemptingMove())) {
				    		  IChessPiece piece = Main.getBoardController().getPieceAtLocation(nextMoveData.getMovedFromLocation());
				    		  // Check if the piece will destroy a piece before the move
				    		 IChessPiece movedTo = Main.getBoardController().getPieceAtLocation(nextMoveData.getNextMoveLocation());
				    		 if(movedTo != null) {
				    			 movedTo.destroyPiece();
				    		 }
				    		  
				    		  Main.getBoardController().movePieceOnBoard(piece, nextMoveData.getNextMoveLocation());
				    		  Main.getBoardController().setNextPlayerToMove();
				    		  Main.getBoardController().checkForGameFinished();
				    		  
				    		  // Reset SQL Next mover
				    		  String run = "UPDATE david.CHESS_DATABASE SET NEXT_MOVE = 'SETUP' WHERE SESSION_ID = '" + getSessionUUID() + "'";
				  			Statement stmt2;
				  			try {
				  				stmt2 = getSQLConnection().createStatement();
				  				stmt2.executeUpdate(run);
				  			} catch (SQLException e) {
				  				Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Connection Issue. Abopting Game.");
				  				destroy();
				  				Main.createNewGame(GameType.PLAYER_VS_PLAYER);
				  				return;
				  			}
				    	  }
				    	
				    	    
				      }
				      
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Error: Can't connect to servers.");
					destroy();
					Main.createNewGame(GameType.PLAYER_VS_PLAYER);
					return;
				}
			
			// Check for next move in 1.5 seconds
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	try {
			            		checkForPlayerMove();
			            	}catch(Exception ex) {}
			            }
			        }, 1500
			);
		}
	}
	
	
	
	
	public boolean isActive() {
		return isActive;
	}
	
	public String getSessionUUID() {
		return SESSION_UUID;
	}
	
	public TeamType getTeamType() {
		return playersTeam;
	}
	
	public HeartBeatHandler getHeartBeatHandler() {
		return heartBeat;
	}
	
	public Connection getSQLConnection() {
		return connection;
	}
}