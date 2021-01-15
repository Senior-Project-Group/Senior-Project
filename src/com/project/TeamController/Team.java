package com.project.TeamController;

import java.util.ArrayList;

import com.project.ChessPieces.IChessPiece;

public class Team {

	private ArrayList<IChessPiece> pieces;
	
	private TeamType teamType;
	
	public Team(TeamType type) {
		teamType = type;
		pieces = new ArrayList<IChessPiece>();
		setPieces();
	}
	
	private void setPieces() {
		// Set the location pieces for black
		if(getTeamType().equals(TeamType.BLACK)) {
			// Set the location pieces for black
		}else {
			// Set the location pieces for white
		}
		
		// TODO Create way to set pieces
		
	}
	
	public ArrayList<IChessPiece> getChessPieces(){
		return pieces;
	}
	
	public TeamType getTeamType() {
		return teamType;
	}
	
}