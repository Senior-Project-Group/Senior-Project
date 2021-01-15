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

public class Team {

	private ArrayList<IChessPiece> pieces;
	
	private TeamType teamType;
	
	public Team(TeamType type) {
		teamType = type;
		pieces = new ArrayList<IChessPiece>();
		setPieces();
	}
	
	private void setPieces() {
		// TODO Create way to set pieces
		
		if(getTeamType().equals(TeamType.BLACK)) {
			// Set the location pieces for black
			
			// Set bottom row for black
			pieces.add(new RookPiece(new Location(7, 0)));
			pieces.add(new KnightPiece(new Location(7, 1)));
			pieces.add(new BishopPiece(new Location(7, 2)));
			pieces.add(new QueenPiece(new Location(7, 3)));
			pieces.add(new KingPiece(new Location(7, 4)));
			pieces.add(new BishopPiece(new Location(7, 5)));
			pieces.add(new KnightPiece(new Location(7, 6)));		
			pieces.add(new RookPiece(new Location(7, 7)));
			
			// Set top row for black
			for(int x = 0; x != 7; x++) {
				pieces.add(new PawnPiece(new Location(6, x)));
			}
			
			
		}else {
			// Set the location pieces for white
			
			// Bottom row for white
			pieces.add(new RookPiece(new Location(0, 0)));
			pieces.add(new KnightPiece(new Location(0, 1)));
			pieces.add(new BishopPiece(new Location(0, 2)));
			pieces.add(new QueenPiece(new Location(0, 3)));
			pieces.add(new KingPiece(new Location(0, 4)));
			pieces.add(new BishopPiece(new Location(0, 5)));
			pieces.add(new KnightPiece(new Location(0, 6)));
			pieces.add(new RookPiece(new Location(0, 7)));
			
			// Top row for white (pawns)
			for(int x = 0; x != 7; x++) {
				pieces.add(new PawnPiece(new Location(1, x)));
			}
			
		}
		
	}
	
	public ArrayList<IChessPiece> getChessPieces(){
		return pieces;
	}
	
	public TeamType getTeamType() {
		return teamType;
	}
	
}