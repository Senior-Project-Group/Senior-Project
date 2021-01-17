package com.project.BoardController;

import java.awt.EventQueue;

import com.project.ChessPieces.IChessPiece;
import com.project.Render.Board;
import com.project.TeamController.Team;
import com.project.TeamController.TeamType;

public class BoardController {

	private Team team1; // Assume team 1 is black and on top
	private Team team2; // Assume team 2 is white and on bottom
	
	private Board board;
	
	public BoardController() {
		board = new Board();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				team1 = new Team(TeamType.BLACK);
				team2 = new Team(TeamType.WHITE);
			}
		});
	}
	
	public Team getTeam1() {
		return team1;
	}
	
	public Team getTeam2() {
		return team2;
	}
	
	// Returns null if there is no piece at the location
	public IChessPiece getPieceAtLocation(Location location) {
		for(IChessPiece piece : getTeam1().getChessPieces()) {
			if(piece.getLocation().getX() == location.getX() && piece.getLocation().getZ() == location.getZ()) {
				return piece;
			}
		}
		
		for(IChessPiece piece : getTeam2().getChessPieces()) {
			if(piece.getLocation().getX() == location.getX() && piece.getLocation().getZ() == location.getZ()) {
				return piece;
			}
		}
		return null;
	}
	
	// Returns the team the piece belongs to
	// Will return null if the piece somehow doesn't belong to a specific team
	
	public Team getTeamPieceBelongsTo(IChessPiece piece) {
		
		if(team1.getChessPieces().contains(piece)) {
			return team1;
		}
		
		if(team2.getChessPieces().contains(piece)) {
			return team2;
		}
		
		return null;
	}
	
	public boolean isLocationOnBoard(Location location) {
		if(location.getX() >= 0 && location.getZ() >= 0 && location.getX() <= 7 && location.getZ() <= 7) {
			return true;
		}
		return false;
	}
	
	public void removePieceFromBoard(IChessPiece piece) {
		Team team = getTeamPieceBelongsTo(piece);
		if(team != null) {
			team.removePiece(piece);
		}
	}
	
	public Board getBoardObject() {
		return board;
	}
	
}