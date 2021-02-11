package com.project.AiController;

import com.project.BoardController.Location;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;

public class CommonAIFunctions {

	private AIController controller;
	public CommonAIFunctions(AIController controller) {
		this.controller = controller;
	}
	
	// Return location the piece can move too inorder to take the king
	// Returns null if the piece cannot take the king
	public Location canPieceTakeKing(IChessPiece piece) {
		// King piece can't take out another king
		if(piece instanceof KingPiece) return null;
		
		// Get the kings location of the other team
		IChessPiece enemyKing = null;
		for(IChessPiece king : controller.getEnemyTeam().getChessPieces()) {
			if(king instanceof KingPiece) {
				enemyKing = king;
				break;
			}
		}
		// Error check
		if(enemyKing == null) return null;
		
		
		for(Location possibleMoves : piece.getPossibleMoves()) {
			if(enemyKing.getLocation().equals(possibleMoves)) {
				return possibleMoves;
			}
		}
		
		return null;
	}
}