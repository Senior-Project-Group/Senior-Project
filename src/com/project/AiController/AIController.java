package com.project.AiController;

import javax.swing.JFrame;

import com.project.TeamController.Team;

// Object that handles the AI for the specified team
public class AIController {

	private Team team;
	
	private JFrame frame;
	
	// Create a new AI controller for the specified team
	public AIController(Team team, JFrame frame) {
		this.team = team;
		this.frame = frame;
		startAI();
	}
	
	public Team getTeam() {
		return team;
	}
	
	// TODO write code here to manage AI movements
	private void startAI() {
		
	}
	
	// Cancels the AI from working
	public void cancelAI() {
		
	}
	
}