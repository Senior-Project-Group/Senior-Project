package com.project.AiController;

import java.util.ArrayList;
import java.util.Random;

import com.project.BoardController.Location;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;
import com.project.ChessPieces.PawnPiece;
import com.project.ChessPieces.QueenPiece;
import com.project.TeamController.TeamType;

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
	
	public ArrayList<PieceInformation> getAllPossibleMoves() {
		ArrayList<PieceInformation> pieceInformation = new ArrayList<PieceInformation>();
		for(IChessPiece piece : controller.getTeam().getChessPieces()) {
				for(Location loc : piece.getPossibleMoves()) {
					IChessPiece pieceAtLocation = controller.getBoardController().getPieceAtLocation(loc);
					if(pieceAtLocation != null && !pieceAtLocation.getTeamType().equals(piece.getTeamType())) {
						pieceInformation.add(new PieceInformation(piece, loc, true, pieceAtLocation));
					}else {
						pieceInformation.add(new PieceInformation(piece, loc, false, pieceAtLocation));
					}
			}
			
		}
		return pieceInformation;
		
	}
	
	public void promotePawnMove(IChessPiece pawn) {
		if(pawn instanceof PawnPiece) { // Check to make sure it is a pawn
			if(pawn.getLocation().getZ() == 0 || pawn.getLocation().getZ() == 7) { // Proper promotion Z value
				pawn.destroyPiece();
				if(pawn.getTeamType().equals(TeamType.BLACK)) {
					controller.getBoardController().getTeam1().addNewPiece(new QueenPiece(pawn.getLocation(), pawn.getTeamType()));
				}else {
					controller.getBoardController().getTeam2().addNewPiece(new QueenPiece(pawn.getLocation(), pawn.getTeamType()));
				}
			}
			
		}
	}
	
	public void checkCastleMove(IChessPiece piece) {
		
		if(piece.getTeamType().equals(TeamType.BLACK)) {
			Location castleCheck1 = new Location(2, 7); // Left castle
			Location castleCheck2 = new Location(6, 7); // Right castle
			if(piece.getLocation().equals(castleCheck1)) {
				// Check the rook now
				IChessPiece rook = controller.getBoardController().getPieceAtLocation(new Location(0, 7)); 
				if(rook != null) {
					if(!rook.hasMovedAlready()) {
						controller.getBoardController().movePieceOnBoard(rook, new Location(3, 7));
						System.out.println("Left castle done.");
					}
				}
				
			}else if(piece.getLocation().equals(castleCheck2)) {
				IChessPiece rook = controller.getBoardController().getPieceAtLocation(new Location(7, 7));
				if(rook != null) {
					if(!rook.hasMovedAlready()) {
						controller.getBoardController().movePieceOnBoard(rook, new Location(5, 7));
						System.out.println("Right castle done.");
					}
				}
				
			}
			
		}else {
			Location castleCheck1 = new Location(2, 0); // Left castle
			Location castleCheck2 = new Location(6, 0); // Right castle
			if(piece.getLocation().equals(castleCheck1)) {
				// Check the rook now
				IChessPiece rook = controller.getBoardController().getPieceAtLocation(new Location(0, 0)); 
				if(rook != null) {
					if(!rook.hasMovedAlready()) {
						controller.getBoardController().movePieceOnBoard(rook, new Location(3, 0));
						System.out.println("Left castle done.");
					}
				}
				
			}else if(piece.getLocation().equals(castleCheck2)) {
				IChessPiece rook = controller.getBoardController().getPieceAtLocation(new Location(7, 0));
				if(rook != null) {
					if(!rook.hasMovedAlready()) {
						controller.getBoardController().movePieceOnBoard(rook, new Location(5, 0));
						System.out.println("Right castle done.");
					}
				}
				
			}
		}
		
		
	}
	
	
	public IChessPiece getRandomChessPiece() {
	    return controller.getTeam().getChessPieces().get(new Random().nextInt(controller.getTeam().getChessPieces().size()));
	}
}