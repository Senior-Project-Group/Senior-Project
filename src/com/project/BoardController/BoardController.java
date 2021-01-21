package com.project.BoardController;

import java.awt.EventQueue;

import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;
import com.project.Main.Main;
import com.project.Render.Board;
import com.project.Render.NextMoveRenderer;
import com.project.TeamController.Team;
import com.project.TeamController.TeamType;

// Object that controllers the board and keeps the information like current player, current game type, the board object, and team information.
// Also has information related getting the location of a piece back on the grid and other game related functions.
public class BoardController {

	private GameType currentGameType;
	
	private Team team1; // Assume team 1 is black and on top
	private Team team2; // Assume team 2 is white and on bottom
	
	private TeamType currentPlayer;
	
	private Board board;
	
	private NextMoveRenderer nextMoveRenderer;
	
	private boolean gameEnded;
	
	public BoardController(GameType gameType) {
		this.gameEnded = false;
		this.currentGameType = gameType;
		nextMoveRenderer = new NextMoveRenderer();
		currentPlayer = TeamType.WHITE; // White always goes first. It's in the rules.
		board = new Board();
		
		// Needed to prevent error of board not being created yet
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
			piece.getTexture().removeTextureFromBoard();
			team.removePiece(piece);
		}else {
			System.out.println("Error: Unable to remove piece");
		}
	}
	
	public void movePieceOnBoard(IChessPiece piece, Location newLocation) {
		piece.getTexture().moveTextureTo(newLocation);
		piece.setLocation(newLocation);
		getNextMoveRenderer().clearCurrentRender();
		getBoardObject().getFrame().repaint();
	}
	
	public Board getBoardObject() {
		return board;
	}
	
	public NextMoveRenderer getNextMoveRenderer() {
		return nextMoveRenderer;
	}
	
	public TeamType getCurrentPlayerToMove() {
		return currentPlayer;
	}
	
	public GameType getCurrentGameType() {
		return currentGameType;
	}
	
	public void setNextPlayerToMove() {
		if(currentPlayer.equals(TeamType.WHITE)) {
			currentPlayer = TeamType.BLACK;
		}else {
			currentPlayer = TeamType.WHITE;
		}
	}
	
	public void checkForGameFinished() {
		// Locate the king piece for each team
		
		boolean team1HasKing = false;
		boolean team2HasKing = false;
		
		for (IChessPiece piece : getTeam1().getChessPieces()) {
			if(piece instanceof KingPiece) {
				// We found a king piece for this team, so this team is still alive
				team1HasKing = true;
			}
		}
		
		
		for (IChessPiece piece : getTeam2().getChessPieces()) {
			if(piece instanceof KingPiece) {
				// We found a king piece for this team, so this team is still alive
				team2HasKing = true;
			}
		}
		
		if(team2HasKing && team1HasKing) {
			// Both teams have just the king left somehow
			if(getTeam1().getChessPieces().size() == 1 && getTeam2().getChessPieces().size() == 1) {
				endGame(EndGameStatus.TIE);
				return;
			}
		}
		
		if(team1HasKing && !team2HasKing) {
			// Team 1 won
			endGame(EndGameStatus.TEAM1_WON);
			return;
		}
		
		if(team2HasKing && !team1HasKing) {
			// Team 2 won
			endGame(EndGameStatus.TEAM2_WON);
			return;
		}
		
		// Check if a certain team only has the king left
		
		if(team1HasKing && getTeam1().getChessPieces().size() == 1 && getTeam2().getChessPieces().size() >= 2) {
			endGame(EndGameStatus.TEAM2_WON);
			return;
		}
		
		if(team2HasKing && getTeam2().getChessPieces().size() == 1 && getTeam1().getChessPieces().size() >= 2) {
			endGame(EndGameStatus.TEAM1_WON);
			return;
		}
		
	}
	
	public void endGame(EndGameStatus status) {
		gameEnded = true;
		if(status.equals(EndGameStatus.TIE)) {
			Main.getNotificationHandler().sendNotificationMessage("Game Over","It was a tie! No one won the game!");
		}else {
			if(status.equals(EndGameStatus.TEAM1_WON)) {
				Main.getNotificationHandler().sendNotificationMessage("Game Over", "Black Team won the game!");
			}else {
				Main.getNotificationHandler().sendNotificationMessage("Game Over", "White Team won the game!");
			}
		}
	}
	
	public boolean hasGameEnded() {
		return gameEnded;
	}
	
	
}