package com.project.TeamController;

import java.util.ArrayList;

import com.project.ChessPieces.BishopPiece;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;
import com.project.ChessPieces.QueenPiece;

public class CommanderLogic {

	private Team team;
	
	private int moveCount;
	
	private ArrayList<IChessPiece> commandersAlreadyUsedMove;
	
	private boolean hasKingMoved;
	
	public CommanderLogic(Team team) {
		hasKingMoved = false;
		this.team = team;
		moveCount = 0;
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
		if(getMaxMoves() >= getMaxMoves()) {
			return true;
		}
		
		return false;
	}
	
	public boolean hasCommanderPerformedMove(IChessPiece commander) {
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
		moveCount = 0;
		hasKingMoved = true;
		commandersAlreadyUsedMove.clear();
	}
	
	// Return null if it's it's own commander
	// This method will find the commander for the piece at hand
	// It will check if it's real commander has used his move yet, and if so, check for king
	public IChessPiece getCommanderForPiece(IChessPiece piece) {
		if(piece instanceof BishopPiece || piece instanceof KingPiece) {
			return null;
		}
		
		
		

		return null;
		
	}
	
}