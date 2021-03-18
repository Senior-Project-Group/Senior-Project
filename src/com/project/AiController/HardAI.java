package com.project.AiController;

import java.util.ArrayList;
import java.util.Random;

import com.project.BoardController.Location;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;

public class HardAI {
	
	private AIController controller;
	private CommonAIFunctions commonFunctionsController;
	
	public HardAI(AIController controller) {
		this.controller = controller;
		this.commonFunctionsController = new CommonAIFunctions(controller);
		run();
	}
	
	private void run() {
		ArrayList<PieceInformation> information = commonFunctionsController.getAllPossibleMoves();
		
		boolean hasKilled = false;
		// Run it once to check for killing a king.
		for(PieceInformation info : information) {
			if(info.canKillPiece()) {
				Location location = commonFunctionsController.canPieceTakeKing(info.getPiece());
				if(location != null) {
					// Can kill the king
					controller.getBoardController().removePieceFromBoard(controller.getBoardController().getPieceAtLocation(location));
					controller.getBoardController().movePieceOnBoard(info.getPiece(), location);
					controller.getBoardController().setNextPlayerToMove();
					hasKilled = true;
					break;
				}
			}
		}
		// The king has been killed already.
		if(hasKilled) return;

}
