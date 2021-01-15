package com.project.TeamController;

import java.util.ArrayList;

import com.project.ChessPieces.IChessPiece;

public class Team {

	private ArrayList<IChessPiece> pieces;
	
	public Team() {
		pieces = new ArrayList<IChessPiece>();
	}
	
	public ArrayList<IChessPiece> getChessPieces(){
		return pieces;
	}
	
}