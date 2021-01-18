package com.project.AiController;

import javax.swing.JFrame;

import com.project.Main.Main;
import com.project.TeamController.Team;

// Object that handles the AI for the specified team
public class AIController {

	private Team team;
	
	private JFrame frame;
	
	private boolean cancel;
	
	private Team enemyTeam;
	
	// Create a new AI controller for the specified team
	public AIController(Team team, Team enemyTeam, JFrame frame) {
		this.team = team;
		this.frame = frame;
		this.enemyTeam = enemyTeam;
		cancel = false;
		runAIHandler();
	}
	
	public Team getTeam() {
		return team;
	}
	
	public Team getEnemyTeam() {
		return enemyTeam;
	}
	
	// Get the frame the AI is responsible for
	public JFrame getAIFrame() {
		return frame;
	}
	
	
	// TODO write code here to manage AI movements
	private void preformAIMove() {
		if(Main.getBoardController().getCurrentPlayerToMove().equals(team.getTeamType())) {
			// Run the code here
		}
	}
	
	private void runAIHandler() {
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	if(cancel) return; // If the AI was canceled, don't run AI code
		            	if(!getAIFrame().isActive() || !getAIFrame().isDisplayable()) return; // If the frame is over, cancel it
		            	if(Main.getBoardController().hasGameEnded()) return; // Cancel if the game is over
		            	
		            	
		            	// Run the AI
		            	preformAIMove();
		            	// Rerun the timer
		            	runAIHandler();
		            }
		            	
		        }, Main.getAIController().getSpeed() // Run this every 2.5 seconds
		);
	}
	
	
	// Cancels the AI from working
	public void cancelAI() {
		cancel = true;
	}
	
}