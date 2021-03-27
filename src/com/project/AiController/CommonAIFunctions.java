package com.project.AiController;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;
import com.project.Main.Main;
import com.project.TeamController.Team;
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
	
	// Return the location a piece can move to inorder to take out a bishop
	// Returns null if the piece can't take out a bishop
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
	
	// Return all possible moves for all the teams pieces
	// Will never return null. Impossible
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
	
	// Function that will return an arraylist of all pieces who can move to a specific location
	public ArrayList<IChessPiece> findPiecesToMoveToLocation(Location location) {
		ArrayList<IChessPiece> pieces = new ArrayList<IChessPiece>();
		
		for(IChessPiece onBoard : controller.getTeam().getChessPieces()) {
			if(location.equals(onBoard.getLocation())) {
				pieces.add(onBoard);
			}
			
		}
		
		return null;	
	}
	
	// Returns all threatened pieces of the team
	// A threatened piece means that it's possible for it to be taken out/killed
	public ArrayList<ThreatenedPiece> getThreatenedPieces() {
		ArrayList<ThreatenedPiece> threatenedPieces = new ArrayList<ThreatenedPiece>();
		
		// Get all enemy pieces
		for(IChessPiece enemyPieces : controller.getEnemyTeam().getChessPieces()) {
			ArrayList<Location> moves = enemyPieces.getPossibleMoves();
			for(Location loc : moves) {
				if(Main.getBoardController().getPieceAtLocation(loc) != null) { // It take take out a piece
					boolean found = false;
					ThreatenedPiece piece = new ThreatenedPiece(Main.getBoardController().getPieceAtLocation(loc));
					for(ThreatenedPiece threat : threatenedPieces) {
						if(piece.equals(threat)) {
							found = true;
							threat.addThreat(enemyPieces);
							break;
						}
					}
					if(!found) {
						threatenedPieces.add(piece);
					}
					
				}
			}
		}
		
		if(threatenedPieces.size() == 0) {
			return null;
		}
		
		return threatenedPieces;
		
	}
	
	
}