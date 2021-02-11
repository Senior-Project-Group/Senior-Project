package com.project.AiController;

public class EasyAI {

	private AIController controller;
	private CommonAIFunctions commonFunctionsController;
	
	public EasyAI(AIController controller) {
		this.controller = controller;
		this.commonFunctionsController = new CommonAIFunctions(controller);
	}
	
}
