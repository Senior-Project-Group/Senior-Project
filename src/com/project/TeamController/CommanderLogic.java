package com.project.TeamController;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.ChessPieces.BishopPiece;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;
import com.project.ChessPieces.KnightPiece;
import com.project.ChessPieces.PawnPiece;
import com.project.ChessPieces.QueenPiece;
import com.project.ChessPieces.RookPiece;
import com.project.Main.Main;

public class CommanderLogic {

	private Team team;
	
	private int moveCount;
	
	private ArrayList<IChessPiece> commandersAlreadyUsedMove;
	
	private boolean hasKingMoved;
	
	private ArrayList<IChessPiece> hasMoveThisTurn = new ArrayList<IChessPiece>();
	
	public CommanderLogic(Team team) {
		hasKingMoved = false;
		this.team = team;
		moveCount = 1;
		commandersAlreadyUsedMove = new ArrayList<IChessPiece>();
	}
	
	public Team getTeam() {
		return this.team;
	}
	
	public int getMaxMoves() {
		int amount = 0;
		for(IChessPiece piece : team.getChessPieces()) {
			if(piece instanceof KingPiece || piece instanceof BishopPiece) {
				amount++;
			}
			
		}
		
		return amount;
	}
	
	public ArrayList<IChessPiece> getCommandersWhoHaveMoved() {
		return commandersAlreadyUsedMove;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public boolean hasDoneAllMoves() {
		if(getMoveCount() >= getMaxMoves()) {
			return true;
		}
		
		return false;
	}
	
	// Get the gets the commander for that person
	public boolean hasCommanderPerformedMove(IChessPiece commander) {
		// Check if the king has moved yet		
		if(commandersAlreadyUsedMove.contains(commander)) {
			return true;
		}
		
		return false;
	}
	
	public void move(IChessPiece commander) {
		moveCount++;
		if(commander instanceof KingPiece) {
			hasKingMoved = true;
		}
		commandersAlreadyUsedMove.add(commander);
	}
	
	public void reset() {
		moveCount = 1;
		hasKingMoved = false;
		commandersAlreadyUsedMove.clear();
		hasMoveThisTurn.clear();
	}
	
	public void addMovedThisTurn(IChessPiece piece) {
		hasMoveThisTurn.add(piece);
	}
	
	public boolean hasAlreadyMoved(IChessPiece piece) {
		if(hasMoveThisTurn.contains(piece)) {
			return true;
		}
		
		return false;
	}
	
	
	// Return null if it's it's own commander
	// This method will find the commander for the piece at hand
	
	// This does not check if commander has moved yet. 
	// But will assign the king to the piece if it's bishop is dead
	
	// TODO Will also check if the king has moved already, and give the piece to a corresponding bishop

	public IChessPiece getCommanderForPiece(IChessPiece piece) {			
		if(piece instanceof BishopPiece || piece instanceof KingPiece) {
			if(hasKingMoved && piece instanceof KingPiece) {
				return null;
			}
			return piece;
		}
		
		// Check for extra delegation
		
		for(IChessPiece pieces : team.getChessPieces()) {
			if(pieces instanceof BishopPiece) {
				ArrayList<IChessPiece> delegated = ((BishopPiece)pieces).getDelegatedPieces();
				if(delegated.contains(piece)) {
					// We have a delegation here
					if(!hasCommanderPerformedMove(pieces)) {
						return pieces;
					}else {
						return null;
					}
					
				}
				
			}
		}
		
		
		if(piece instanceof QueenPiece || piece instanceof RookPiece || piece instanceof PawnPiece) {
			for(IChessPiece pieces : team.getChessPieces()) {
				if(pieces instanceof KingPiece) {
					if(piece instanceof PawnPiece && (piece.getStartLocation().getX() == 3 || piece.getStartLocation().getX() == 4)) {
						if(!hasKingMoved) {
							return pieces;
						}	
					}else {
						if(!hasKingMoved && !(piece instanceof PawnPiece)) {
							return pieces;
						}	
					}

				}
			}
			
		}
		
		IChessPiece blackLeftBishop = Main.getBoardController().getPieceAtLocation(new Location(2, 7));
		IChessPiece blackRightBishop = Main.getBoardController().getPieceAtLocation(new Location(5, 7));
		
		IChessPiece whiteLeftBishop = Main.getBoardController().getPieceAtLocation(new Location(2, 0));
		IChessPiece whiteRightBishop = Main.getBoardController().getPieceAtLocation(new Location(5, 0));
		
		// Check if all commanders have performed move yet
		
		if(blackLeftBishop != null) {
			if(hasCommanderPerformedMove(blackLeftBishop)) {
				blackLeftBishop = null;
			}
		}
		
		if(blackRightBishop != null) {
			if(hasCommanderPerformedMove(blackRightBishop)) {
				blackRightBishop = null;
			}
		}
		
		if(whiteLeftBishop != null) {
			if(hasCommanderPerformedMove(whiteLeftBishop)) {
				whiteLeftBishop = null;
			}
		}
		
		if(whiteRightBishop != null) {
			if(hasCommanderPerformedMove(whiteRightBishop)) {
				whiteRightBishop = null;
			}
		}

		// This is the normal way, the piece has a bishop as a commander
		if(piece instanceof PawnPiece || piece instanceof KnightPiece) {
			for(IChessPiece pieces : team.getChessPieces()) {
				if(pieces instanceof BishopPiece) {
					if(piece.getStartLocation().getX() <= 2) { // Left side
						if(team.getTeamType().equals(TeamType.BLACK)) {
							if(blackLeftBishop != null) {
								return blackLeftBishop;
							}
						}else {
							if(whiteLeftBishop != null) {
								return whiteLeftBishop;
							}
						}
						
					}else if (piece.getStartLocation().getX() >= 5){ // Right side
						if(team.getTeamType().equals(TeamType.BLACK)) {
							if(blackRightBishop != null) {
								return blackRightBishop;
							}
						}else {
							if(whiteRightBishop != null) {
								return whiteRightBishop;
							}
						}
						
					}else { // These pieces are automatically the kings
						for(IChessPiece findKing : team.getChessPieces()) {
							if(pieces instanceof KingPiece) {
								return findKing;
							}
						}
					}
					
				}
			}
		}
		

		return null;
		
	}

	
	
	
}