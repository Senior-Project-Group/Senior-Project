package com.project.Multiplayer;

import java.sql.SQLException;
import java.sql.Statement;

import com.project.BoardController.GameType;
import com.project.BoardController.Location;
import com.project.Main.Main;
import com.project.TeamController.TeamType;


public class NextMoveParser {

	private String input;
	
	// EX: WHITE=1,2=3,4
	@SuppressWarnings("unused")
	private String inputFormat = "[COLOR_MOVING]=[LOCATION_FROM]=[LOCATION_TO]";
	
	
	// If the format is already know
	public NextMoveParser(String input) {
		this.input = input;
	}
	
	// Create an input based on coordinates
	public NextMoveParser(TeamType team, Location from, Location to) {
		// Create format
		input = team.toString() + "=" + from.getX() + "," + from.getZ() + "=" + to.getX() + "," + to.getZ();
		
	}
	
	// Use this for the castle
	public NextMoveParser(TeamType team, String castleDone) {
		input = team.toString() + "=" + castleDone;
	}
	
	public String getTeamAttemptingMove() {
		String[] initalSplit = input.split("=");
		return initalSplit[0];
	}
	
	public Location getNextMoveLocation() {
		String[] initalSplit = input.split("=");
		String[] locationSplit = initalSplit[2].split(",");
		
		return new Location(Integer.parseInt(locationSplit[0]), Integer.parseInt(locationSplit[1]));
	}
	
	public Location getMovedFromLocation() {
		String[] initalSplit = input.split("=");
		String[] locationSplit = initalSplit[1].split(",");
		return new Location(Integer.parseInt(locationSplit[0]), Integer.parseInt(locationSplit[1]));
	}
	
	public String getFormattedString() {
		return input;
	}
	
	public boolean isMoveCastle() {		
		return input.split("=")[1].contains("CASTLE");
	}
	
	public String getCastleMove() {
		if(isMoveCastle()) {
			String[] initalSplit = input.split("=");
			return initalSplit[1];
		}
		
		return null;
	}
	
	// Send the data to the database
	public void sendToDatabase(SQLHandler sqlHandler) {
		String run = "UPDATE david.CHESS_DATABASE SET NEXT_MOVE = '" + input + 
				"' WHERE SESSION_ID = '" + sqlHandler.getSessionUUID() + "'";
		Statement stmt;
		try {
			stmt = sqlHandler.getSQLConnection().createStatement();
			stmt.executeUpdate(run);
		} catch (SQLException e) {
			Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Connection Issue. Abopting Game.");
			sqlHandler.destroy();
			Main.createNewGame(GameType.PLAYER_VS_PLAYER);
			return;
		}
		
	}
}