package com.project.Main;

import com.project.AiController.AIControllerHandler;
import com.project.BoardController.BoardController;
import com.project.Notifications.NotificationHandler;

public class Main {

	private static BoardController boardController;
	
	private static AIControllerHandler AIController;
	
	private static NotificationHandler notificationHandler;
	
	public static void main(String args[]) {
		notificationHandler = new NotificationHandler();
		AIController = new AIControllerHandler();
		System.out.println("Creating And Setting Up Board...");
		boardController = new BoardController(GameType.PLAYER_VS_PLAYER);
		
	}
	
	public static void createNewGame(GameType newGameType) {
		System.out.println("Creating And Setting Up Board for gamne type: " + newGameType.toString());
		// Delete the old game and reset the AI controller
		getBoardController().getBoardObject().getFrame().dispose();
		getAIController().clear();
		// Create a new board
		boardController = new BoardController(newGameType);
		switch(newGameType) {
		case PLAYER_VS_PLAYER:
			break;
		case PLAYER_VS_AI:
			getAIController().addController(new com.project.AiController.AIController(Main.getBoardController().getTeam1(), getBoardController().getBoardObject().getFrame()));
			break;
		case AI_VS_AI:
			getAIController().addController(new com.project.AiController.AIController(Main.getBoardController().getTeam1(), getBoardController().getBoardObject().getFrame()));
			getAIController().addController(new com.project.AiController.AIController(Main.getBoardController().getTeam2(), getBoardController().getBoardObject().getFrame()));
			break;
		}
	}
	
	public static BoardController getBoardController() {
		return boardController;
	}
	
	public static AIControllerHandler getAIController() {
		return AIController;
	}
	
	public static NotificationHandler getNotificationHandler() {
		return notificationHandler;
	}
	
}