package com.project.AiController;

import java.util.ArrayList;


// Object that handles the controllers for the different AI's
public class AIControllerHandler {

	private ArrayList<AIController> AIControllers;
	
	private int speed;
	
	private AIDifficulty difficulty;
	
	// Default contructor that sets the default information on system start
	public AIControllerHandler(int speed, AIDifficulty difficuly) {
		this.difficulty = difficuly;
		if(speed >= 1) {
			this.speed = speed * 1000;
		}else {
			this.speed = 300; // 0.3 seconds to run
		}
		
		AIControllers = new ArrayList<AIController>();
	}
	
	// Gets the difficulty of the AI: EASY, NORMAL, HARD
	public AIDifficulty getDifficulty() {
		return difficulty;
	}
	
	// Sets the difficulty of the AI: EASY, NORMAL, HARD
	public void setDifficulty(AIDifficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	// How fast each AI will move/process information
	public int getSpeed() {
		return speed;
	}
	
	// Sets new speed the AI will move/process information
	public void setNewSpeed(int speed) {
		this.speed = speed;
	}
	
	// Get all currently active AI controllers
	public ArrayList<AIController> getAIControllers() {
		return AIControllers;
	}
	
	// Clears and resets all AI controllers
	public void clear() {
		for(AIController controller : getAIControllers()) {
			controller.cancelAI();
		}
		AIControllers.clear();
	}
	
	// Added a new AI controller to the system
	public void addController(AIController controller) {
		AIControllers.add(controller);
	}
}