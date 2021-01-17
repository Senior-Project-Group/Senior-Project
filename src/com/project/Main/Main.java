package com.project.Main;

import com.project.AiController.AIControllerHandler;
import com.project.BoardController.BoardController;

public class Main {

	private static BoardController boardController;
	
	private static AIControllerHandler AIController;
	
	public static void main(String args[]) {
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
			break;
		case AI_VS_AI:
			break;
		}
	}
	
	public static BoardController getBoardController() {
		return boardController;
	}
	
	public static AIControllerHandler getAIController() {
		return AIController;
	}
	
}