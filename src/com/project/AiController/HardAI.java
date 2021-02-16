package com.project.AiController;

import java.util.ArrayList;

public class HardAI {
	
	private AIController controller;
	private CommonAIFunctions commonFunctionsController;
	
	public HardAI(AIController controller) {
		this.controller = controller;
		this.commonFunctionsController = new CommonAIFunctions(controller);
		run();
	}
	
	private void run() {
		ArrayList<PieceInformation> information = commonFunctionsController.getAllPossibleMoves();
		
		
		
	}

}
