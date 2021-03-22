package com.project.Multiplayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.project.BoardController.BoardController;
import com.project.BoardController.GameType;
import com.project.BoardController.Location;
import com.project.ChessPieces.BishopPiece;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KnightPiece;
import com.project.ChessPieces.QueenPiece;
import com.project.ChessPieces.RookPiece;
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
	    	  System.out.println("Attempting to connect to data servers...");
	    	 connection = DriverManager.getConnection(connectionURL, "david", password);
	    	 System.out.println("Connected...");
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
			// Check if the current player to move it the one before trying data check
			try {
				
				if(!Main.getBoardController().getCurrentPlayerToMove().equals(getTeamType())) {
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
						      if(!nextMove.equals("SETUP") && !Main.getBoardController().hasGameEnded()) {
						    	  NextMoveParser nextMoveData = new NextMoveParser(nextMove);
						    	  
						    	  // Check if the next move is the other player
						    	  
						    	  if(Main.getBoardController().getCurrentPlayerToMove().toString().equals(nextMoveData.getTeamAttemptingMove())) {
						    		  
						    		  // Do castle check first to make sure there is no issues
						    		  if(nextMoveData.isMoveCastle()) {
						    			  // We have a castle move
						    			  String getCastleType = nextMoveData.getCastleMove();
						    			  if(getCastleType != null) {
							    			  if(getCastleType.equals("RIGHT_CASTLE")) { // Right castle
							    				  // Get the king and rook involved
							    				  if(nextMoveData.getTeamAttemptingMove().equals("BLACK")) {
							    					  IChessPiece king = Main.getBoardController().getPieceAtLocation(new Location(4, 7)); // Black king
							    					  IChessPiece rook = Main.getBoardController().getPieceAtLocation(new Location(7, 7)); // Black right castle
							    					  
							    					  Main.getBoardController().movePieceOnBoard(king, new Location(6, 7)); // Move king
							    					  Main.getBoardController().movePieceOnBoard(rook, new Location(5, 7)); // Move rook
							    				  }else { // White is doing it
							    					  IChessPiece king = Main.getBoardController().getPieceAtLocation(new Location(4, 0)); // White king
							    					  IChessPiece rook = Main.getBoardController().getPieceAtLocation(new Location(7, 0)); // White right castle
							    					  
							    					  Main.getBoardController().movePieceOnBoard(king, new Location(6, 0)); // Move king
							    					  Main.getBoardController().movePieceOnBoard(rook, new Location(5, 0)); // Move rook
							    				  }
							    				  
							    			  }else { // Left castle
							    				  // Get the king and rook involved
							    				  if(nextMoveData.getTeamAttemptingMove().equals("BLACK")) {
							    					  IChessPiece king = Main.getBoardController().getPieceAtLocation(new Location(4, 7)); // Black king
							    					  IChessPiece rook = Main.getBoardController().getPieceAtLocation(new Location(0, 7)); // Black left castle
							    					  
							    					  Main.getBoardController().movePieceOnBoard(king, new Location(2, 7)); // Move king
							    					  Main.getBoardController().movePieceOnBoard(rook, new Location(3, 7)); // Move rook
							    					  
							    				  }else { // White is doing it
							    					  IChessPiece king = Main.getBoardController().getPieceAtLocation(new Location(4, 0)); // White king
							    					  IChessPiece rook = Main.getBoardController().getPieceAtLocation(new Location(0, 0)); // White left castle
							    					  
							    					  Main.getBoardController().movePieceOnBoard(king, new Location(2, 0)); // Move king
							    					  Main.getBoardController().movePieceOnBoard(rook, new Location(3, 0)); // Move rook
							    				  }
							    				  
							    			  }
						    			  }
						    			  
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
							  			
						    		  }else {
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
								  			
								  			String promotion = nextMoveData.getPromotionType(piece);
								  			if(promotion != null) {
								  				// Promote the piece
								  				piece.destroyPiece();
								  				if(piece.getTeamType().equals(TeamType.BLACK)) {
								  					switch(promotion) {
								  						case "KNIGHT":
								  							Main.getBoardController().getTeam1().addNewPiece(new KnightPiece(piece.getLocation(), piece.getTeamType()));
								  							break;
								  						case "QUEEN":
								  							Main.getBoardController().getTeam1().addNewPiece(new QueenPiece(piece.getLocation(), piece.getTeamType()));
								  							break;
								  						case "BISHOP":
								  							Main.getBoardController().getTeam1().addNewPiece(new BishopPiece(piece.getLocation(), piece.getTeamType()));
								  							break;
								  						case "ROOK":
								  							Main.getBoardController().getTeam1().addNewPiece(new RookPiece(piece.getLocation(), piece.getTeamType()));
								  							break;
								  					}
								  				}else { // White
								  					switch(promotion) {
							  						case "KNIGHT":
							  							Main.getBoardController().getTeam2().addNewPiece(new KnightPiece(piece.getLocation(), piece.getTeamType()));
							  							break;
							  						case "QUEEN":
							  							Main.getBoardController().getTeam2().addNewPiece(new QueenPiece(piece.getLocation(), piece.getTeamType()));
							  							break;
							  						case "BISHOP":
							  							Main.getBoardController().getTeam2().addNewPiece(new BishopPiece(piece.getLocation(), piece.getTeamType()));
							  							break;
							  						case "ROOK":
							  							Main.getBoardController().getTeam2().addNewPiece(new RookPiece(piece.getLocation(), piece.getTeamType()));
							  							break;
							  					}
								  				}
								  				
								  			}
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
					
				}
				
				
			}catch(NullPointerException e) {}
			
			
			// Check for next move in 0.5 seconds
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	try {
			            		checkForPlayerMove();
			            	}catch(Exception ex) {}
			            }
			        }, 500
			);
		}
	}
	
	public void sendRestartRequest() {
		// Make sure the session is still open
		if(isActive()) {			
			// Check to see if other player has already submitted a request to restart
			String currentSelection = getCurrentNextMoveRequest();
			
			// There was an error processing
			if(currentSelection.equals("ERROR")) {
				Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Connection Issue. Abopting Game.");
  				destroy();
  				Main.createNewGame(GameType.PLAYER_VS_PLAYER);
				return;
			}
			
			// There is already a restart request initialized
			if(currentSelection.contains("RESTART_REQUEST")) {
				// Send complete update request
				String run = "UPDATE david.CHESS_DATABASE SET NEXT_MOVE = 'RESTART_REQUEST_WHITE_BLACK' WHERE SESSION_ID = '" + getSessionUUID() + "'";
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
	  			
	  			System.out.println("Creating And Setting Up Board for game type: " + GameType.SQL_MULTIPLAYER.toString());
		    	// Delete the old game and reset the AI controller
		    	Main.getBoardController().getNextMoveRenderer().clearCurrentRender();
		    	Main.getBoardController().getBoardObject().getFrame().dispose();
		    	Main.getAIController().clear();
		    			
		    	// Create a new board
		    	Main.setNewBoardController(new BoardController(GameType.SQL_MULTIPLAYER));
	  			
				
			}else { // There isn't a request there currently
				// Send request for current team
				String run = "UPDATE david.CHESS_DATABASE SET NEXT_MOVE = 'RESTART_REQUEST_" + getTeamType().toString() + "' WHERE SESSION_ID = '" + getSessionUUID() + "'";
	  			Statement stmt2;
	  			try {
	  				stmt2 = getSQLConnection().createStatement();
	  				stmt2.executeUpdate(run);
	  				restartRequestTimer();
	  			} catch (SQLException e) {
	  				Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Connection Issue. Abopting Game.");
	  				destroy();
	  				Main.createNewGame(GameType.PLAYER_VS_PLAYER);
	  				return;
	  			}
			}
				
			}
			
		}
	
	// Check to see if the other player has requested a restart every 2 seconds
	private void restartRequestTimer() {
		
		// Found request
		if(getCurrentNextMoveRequest().contains("WHITE_BLACK")) {
			Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Restarting game...");
  		  if(Main.multiplayerGUI != null) {
  			  Main.multiplayerGUI.getFrame().dispose();
  		  }
  		  
  		    // Start the game here
  		    System.out.println("Creating And Setting Up Board for game type: " + GameType.SQL_MULTIPLAYER.toString());
  		    
  			// Delete the old game and reset the AI controller
  			Main.getBoardController().getNextMoveRenderer().clearCurrentRender();
  			Main.getBoardController().getBoardObject().getFrame().dispose();
  			Main.getAIController().clear();
  			
  			// Create a new board
  			Main.setNewBoardController(new BoardController(GameType.SQL_MULTIPLAYER));
  			
  			// Restart database
			String run = "UPDATE david.CHESS_DATABASE SET NEXT_MOVE = 'SETUP' WHERE SESSION_ID = '" + getSessionUUID() + "'";
  			Statement stmt2;
  			try {
  				stmt2 = getSQLConnection().createStatement();
  				stmt2.executeUpdate(run);
  			} catch (SQLException e) {
  				Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Connection Issue. Abopting Game.");
  				destroy();
  				Main.createNewGame(GameType.PLAYER_VS_PLAYER);
  				
  			}
			return;
		}
		
		
		
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	try {
		            		restartRequestTimer();
		            	}catch(Exception ex) {}
		            }
		        }, 2000
		);
		
	}
	
	// Returns what's currently in the next move area of the database
	private String getCurrentNextMoveRequest() {
		String selectDatabase = "SELECT * FROM david.CHESS_DATABASE where SESSION_ID='" + getSessionUUID() + "'";
	    Statement stmt;
			try {
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(selectDatabase);
			      while(rs.next()) {
			    	  return rs.getString("NEXT_MOVE");
			      }
				
			} catch (SQLException e) {
				// Error with connection
				e.printStackTrace();
			}
			
			return "ERROR";
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