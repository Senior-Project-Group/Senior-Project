package com.project.AiController;

import java.util.ArrayList;


// Object that handlers the controllers for the different AI's
public class AIControllerHandler {

	private ArrayList<AIController> AIControllers;
	
	public AIControllerHandler() {
		AIControllers = new ArrayList<AIController>();
	}
	
	public ArrayList<AIController> getAIControllers() {
		return AIControllers;
	}
	
	public void clear() {
		AIControllers.clear();
	}
	
	public void addController(AIController controller) {
		AIControllers.add(controller);
	}
}