package com.project.BoardController;

import com.project.TeamController.Team;
import com.project.TeamController.TeamType;

public class BoardController {

	private Team team1; // Assume team 1 is black and on top
	private Team team2; // Assume team 2 is white and on bottom
	
	public BoardController() {
		team1 = new Team(TeamType.BLACK);
		team2 = new Team(TeamType.WHITE);
	}
	
	public Team getTeam1() {
		return team1;
	}
	
	public Team getTeam2() {
		return team2;
	}
}