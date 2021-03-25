package com.project.AiController;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;
public class CommonAIFunctions {

	private AIController controller;
	public CommonAIFunctions(AIController controller) {
		this.controller = controller;
	}
	
	// Return location the piece can move too in order to take the king
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
	
	public Location canPieceTakeBishop(IChessPiece piece) {
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
	
	
	public ArrayList<PieceInformation> getAllPossibleMoves() {
		ArrayList<PieceInformation> pieceInformation = new ArrayList<PieceInformation>();
		for(IChessPiece piece : controller.getTeam().getChessPieces()) {
				for(Location loc : piece.getPossibleMoves()) {
					// Check if commander is dead
					IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(piece);
					if(commander != null) {
						IChessPiece pieceAtLocation = controller.getBoardController().getPieceAtLocation(loc);
						if(pieceAtLocation != null && !pieceAtLocation.getTeamType().equals(piece.getTeamType())) {
							pieceInformation.add(new PieceInformation(piece, loc, true, pieceAtLocation));
						}else {
							pieceInformation.add(new PieceInformation(piece, loc, false, pieceAtLocation));
						}
					}
				}
			
		}
		return pieceInformation;
		
	}
	
	
}