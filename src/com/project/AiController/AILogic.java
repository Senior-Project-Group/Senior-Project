package com.project.AiController;

import java.util.ArrayList;

import com.project.ChessPieces.IChessPiece;

public class AILogic {

	private AIController controller;
	private CommonAIFunctions commonFunctionsController;
	
	public AILogic(AIController controller) {
		this.controller = controller;
		this.commonFunctionsController = new CommonAIFunctions(controller);
	}
	
	
	// Returns null if no piece can kill the king
	public PieceInformation getBestProbablity(ArrayList<PieceInformation> information, IChessPiece movingPiece) {
		
		PieceInformation bestRun = null;
		for(PieceInformation info : information) {
			
			
		}
		
		return bestRun;
	}
	
}