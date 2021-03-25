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

// Object that manages the pieces for the specified team
public class Team {

	private ArrayList<IChessPiece> pieces; // The pieces for the team
	
	private TeamType teamType; // The team type, either WHITE or BLACK
	
	private int moveCounter;
	
	private CommanderLogic commanderLogic;
	
	// Default contructor that creates a new team, either WHITE or BLACK
	public Team(TeamType type) {
		moveCounter = 0;
		teamType = type;
		pieces = new ArrayList<IChessPiece>();
		setPieces();
		commanderLogic = new CommanderLogic(this);
	}
	
	// Sets the piece locations on the board
	private void setPieces() {
		
		if(getTeamType().equals(TeamType.BLACK)) {
			// Set the location pieces for black
			
			// Set bottom row for black
			pieces.add(new RookPiece(new Location(0, 7), TeamType.BLACK));
			pieces.add(new KnightPiece(new Location(1, 7), TeamType.BLACK));
			pieces.add(new BishopPiece(new Location(2, 7), TeamType.BLACK));
			pieces.add(new QueenPiece(new Location(3, 7), TeamType.BLACK));
			pieces.add(new KingPiece(new Location(4, 7), TeamType.BLACK));
			pieces.add(new BishopPiece(new Location(5, 7), TeamType.BLACK));
			pieces.add(new KnightPiece(new Location(6, 7), TeamType.BLACK));		
			pieces.add(new RookPiece(new Location(7, 7), TeamType.BLACK));
			
			// Set top row for black
			for(int x = 0; x != 8; x++) {
				pieces.add(new PawnPiece(new Location(x, 6), TeamType.BLACK));
			}

		}else {
			// Set the location pieces for white
			
			// Bottom row for white
			pieces.add(new RookPiece(new Location(0, 0), TeamType.WHITE));
			pieces.add(new KnightPiece(new Location(1, 0), TeamType.WHITE));
			pieces.add(new BishopPiece(new Location(2, 0), TeamType.WHITE));
			pieces.add(new QueenPiece(new Location(3, 0), TeamType.WHITE));
			pieces.add(new KingPiece(new Location(4, 0), TeamType.WHITE));
			pieces.add(new BishopPiece(new Location(5, 0), TeamType.WHITE));
			pieces.add(new KnightPiece(new Location(6, 0), TeamType.WHITE));
			pieces.add(new RookPiece(new Location(7, 0), TeamType.WHITE));
			
			// Top row for white (pawns)
			for(int x = 0; x != 8; x++) {
				 pieces.add(new PawnPiece(new Location(x, 1), TeamType.WHITE));
			}
			
		}
		
	}
	
	// Get the chess pieces for the team
	public ArrayList<IChessPiece> getChessPieces(){
		return pieces;
	}
	
	// Removes the chess piece from the team (done when the piece is killed/destroyed)
	public void removePiece(IChessPiece piece) {
		if(pieces.contains(piece)) {
			pieces.remove(piece);
			piece.getTexture().removeTextureFromBoard();
		}
	}
	
	// Get the team type, either WHITE or BLACK
	public TeamType getTeamType() {
		return teamType;
	}
	
	public int getAmountOfMovesDone() {
		return moveCounter;
	}
	
	public void addMove() {
		moveCounter++;
	}
	
	public void addNewPiece(IChessPiece piece) {
		pieces.add(piece);
	}
	
	public CommanderLogic getCommanderLogic() {
		return commanderLogic;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Team) {
			Team temp = (Team)o;
			if(temp.getTeamType().equals(((Team) o).getTeamType())) {
				return true;
				
			}
		}
		
		return false;
	}
	
}