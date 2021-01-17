package com.project.AiController;

import com.project.TeamController.Team;

// Object that handles the AI for the specified team
public class AIController {

	private Team team;
	
	// Create a new AI controller for the specified team
	public AIController(Team team) {
		this.team = team;
	}

	
	public Team getTeam() {
		return team;
	}
}