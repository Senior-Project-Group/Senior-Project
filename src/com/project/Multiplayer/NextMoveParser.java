package com.project.Multiplayer;

import com.project.BoardController.Location;
import com.project.TeamController.TeamType;

public class NextMoveParser {
	
	private String input;
	
	// EX: WHITE=1,2=3,4
	private String inputFormat = "[COLOR_MOVING]=[LOCATION_FROM]=[LOCATION_TO]";
	
	
	public NextMoveParser(String input) {
		this.input = input;
	}
	
	public TeamType getTeamAttemptingMove() {
		return null;
	}
	
	public Location getNextMoveLocation() {
		return null;
	}
	
	public Location getMovedFromLocation() {
		return null;
	}
}