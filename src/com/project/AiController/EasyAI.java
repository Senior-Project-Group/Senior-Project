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
		// How this AI will work:
		
		// Most dangerous pieces: King, queen (has the second most power), Knight (knight has the most power)
		// Goal: Kill king, if can't kill king, then kill bishop next, then knight then queen
		
		// Check if any piece can take out the king (if the chance is 50% or higher perform action)
		// Check if any bishops can be taken out next (if the chance is 50% or higher perform action)
		
		// Check if king/bishops on our side are in danger.  If there is over a 33% chance, attempt to move piece to counter it
		// If they aren't in danger, find all pieces that can die on the other team and try and kill it, but check if moving the piece will make the king/bishops exposed and at risk
		
		// If no pieces can kill a piece, then randomly move a piece, but check if moving the piece will make the king/bishops exposed and at risk
		
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
