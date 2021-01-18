package com.project.AiController;

import java.util.ArrayList;


// Object that handlers the controllers for the different AI's
public class AIControllerHandler {

	private ArrayList<AIController> AIControllers;
	
	private int speed;
	
	private AIDifficulty difficulty;
	
	public AIControllerHandler(int speed, AIDifficulty difficuly) {
		this.difficulty = difficuly;
		if(speed >= 1) {
			this.speed = speed * 1000;
		}else {
			this.speed = 300; // 0.3 seconds to run
		}
		
		AIControllers = new ArrayList<AIController>();
	}
	
	public AIDifficulty getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(AIDifficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setNewSpeed(int speed) {
		this.speed = speed;
	}
	
	public ArrayList<AIController> getAIControllers() {
		return AIControllers;
	}
	
	public void clear() {
		for(AIController controller : getAIControllers()) {
			controller.cancelAI();
		}
		AIControllers.clear();
	}
	
	public void addController(AIController controller) {
		AIControllers.add(controller);
	}
}