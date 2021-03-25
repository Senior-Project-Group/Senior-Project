//	Easy difficulty will make random movements until a piece is available to be captured
package com.project.AiController;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.ChessPieces.ProbabilityController;

public class EasyAI {

	private AIController controller;
	private CommonAIFunctions commonFunctionsController;
	
	public EasyAI(AIController controller) {
		this.controller = controller;
		this.commonFunctionsController = new CommonAIFunctions(controller);
		run();
	}
	
	private void run() {
		ArrayList<PieceInformation> information = commonFunctionsController.getAllPossibleMoves();
		boolean hasPerformedAction = false;
		ProbabilityController prod = new ProbabilityController();
		int randomNumber = prod.getRandomNumber();
		
		
		int[] neededProbablity = null;
		
		
		// Check if any piece can attempt to take the king
		for(PieceInformation info : information) {
			if(info.canKillPiece()) {
				Location location = commonFunctionsController.canPieceTakeKing(info.getPiece());
				if(location != null) {
					// Can kill the king
					break;
				}
			}
		}
		
		// Check to make sure an action hasn't been done yet
		if(hasPerformedAction) return;
		
		// Check if any piece can attempt to take a bishop
		for(PieceInformation info : information) {
			if(info.canKillPiece()) {
				Location location = commonFunctionsController.canPieceTakeBishop(info.getPiece());
				if(location != null) {
					// Can kill a bishop
					
					break;
				}
			}
		}
		
		// Check to make sure an action hasn't been done yet
		if(hasPerformedAction) return;
		
		
		
	}
}
