package com.project.BoardController;

import com.project.TeamController.Team;

public class BoardController {

	private Team team1; // Assume team 1 is black
	private Team team2; // Assume team 2 is white
	
	public BoardController() {
		team1 = new Team();
		team2 = new Team();
	}
	
	public Team getTeam1() {
		return team1;
	}
	
	public Team getTeam2() {
		return team2;
	}
}