package com.project.AiController;

public class HardAI {
	
	private AIController controller;
	private CommonAIFunctions commonFunctionsController;
	
	public HardAI(AIController controller) {
		this.controller = controller;
		this.commonFunctionsController = new CommonAIFunctions(controller);
	}
}
