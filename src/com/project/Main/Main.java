package com.project.Main;

import com.project.BoardController.BoardController;

public class Main {

	private static BoardController boardController;
	
	public static void main(String args[]) {
		System.out.println("Creating And Setting Up Board...");
		boardController = new BoardController();
	}
	
	public static BoardController getBoardController() {
		return boardController;
	}
}