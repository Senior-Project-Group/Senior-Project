package com.project.AiController;

public class NormalAI {

	private AIController controller;
	private CommonAIFunctions commonFunctionsController;
	
	public NormalAI(AIController controller) {
		this.controller = controller;
		this.commonFunctionsController = new CommonAIFunctions(controller);
	}
}