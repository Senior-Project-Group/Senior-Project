package com.project.Main;
import java.awt.EventQueue;

import com.project.AiController.AIController;
import com.project.AiController.AIControllerHandler;
import com.project.AiController.AIDifficulty;
import com.project.BoardController.BoardController;
import com.project.BoardController.GameType;
import com.project.Multiplayer.MultiplayerConnectionGUI;
import com.project.Multiplayer.SQLHandler;
import com.project.Notifications.NotificationHandler;

public class Main {

	private static BoardController boardController;
	
	private static AIControllerHandler AIController;
	
	private static NotificationHandler notificationHandler;
	
	private static SQLHandler sqlHandler;
	
	public static MultiplayerConnectionGUI multiplayerGUI;
	
	// Starts the program
	public static void main(String args[]) {
		multiplayerGUI = null;
		sqlHandler = null;
		notificationHandler = new NotificationHandler();
		// Default AI difficulty is EASY and the default process time is 2 seconds
		AIController = new AIControllerHandler(2, AIDifficulty.EASY);
		System.out.println("Creating And Setting Up Board...");
		boardController = new BoardController(GameType.PLAYER_VS_PLAYER);
	}
	
	// Method to create a new game with specified game type: PLAYER_VS_PLAYER, PLAYER_VS_AI, AI_VS_AI
	public static void createNewGame(GameType newGameType) {
		System.out.println("Creating And Setting Up Board for gamne type: " + newGameType.toString());
		// Delete the old game and reset the AI controller
		getBoardController().getNextMoveRenderer().clearCurrentRender();
		getBoardController().getBoardObject().getFrame().dispose();
		getAIController().clear();
		
		if(sqlHandler != null) {
			sqlHandler.destroy();
		}
		
		// Create a new board
		boardController = new BoardController(newGameType);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				switch(newGameType) {
				case PLAYER_VS_PLAYER:
					break;
				case PLAYER_VS_AI:
					getAIController().addController(new com.project.AiController.AIController(
							getBoardController().getTeam1(), // Team it manages
							getBoardController().getTeam2(), // Enemy team
							getBoardController().getBoardObject().getFrame())); // Current board frame
					break;
				case AI_VS_AI:
					getAIController().addController(new com.project.AiController.AIController(
							getBoardController().getTeam1(), // Team it manages
							getBoardController().getTeam2(), // Enemy team
							getBoardController().getBoardObject().getFrame())); // Current board frame
					
					getAIController().addController(new com.project.AiController.AIController(
							getBoardController().getTeam2(), // Team it manages
							getBoardController().getTeam1(), // Enemy team
							getBoardController().getBoardObject().getFrame())); // Current board frame
					
					new java.util.Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
					            	AIController controller = Main.getAIController().getAIControllerForTeam(getBoardController().getCurrentPlayerToMove());
					            	if(controller != null) {
					            		controller.runMove();
					            	}
					            }
					            	
					        }, 400 
					);
					
					
					break;
				case SQL_MULTIPLAYER:
					// Create SQL Controller
					break;
				}
			}
		});
	}
	
	// Returns the board controller object
	public static BoardController getBoardController() {
		return boardController;
	}
	
	// Returns the AI controller object
	public static AIControllerHandler getAIController() {
		return AIController;
	}
	
	// Returns the notification handler object
	public static NotificationHandler getNotificationHandler() {
		return notificationHandler;
	}
	
	// Gets the games SQL Handler, only active when multiplayer is used
	public static SQLHandler getSQLHandler() {
		return sqlHandler;
	}
	
	// Set the new handler when a new game starts
	public static void setSQLHandler(SQLHandler sql) {
		sqlHandler = sql;
	}
	
	// Set the game controller, only use for multiplayer
	public static void setNewBoardController(BoardController newController) {
		boardController = newController;
	}
	
}