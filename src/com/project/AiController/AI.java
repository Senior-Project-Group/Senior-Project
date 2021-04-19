package com.project.AiController;

import com.project.BoardController.Location;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.ProbabilityController;
import com.project.Main.Main;

public class AI {

	private AIController controller;
	private CommonAIFunctions commonFunctionsController;
	
	public AI(AIController controller) {
		this.controller = controller;
		this.commonFunctionsController = new CommonAIFunctions(controller);
		run();
	}
	
	private void run() {
		int randomNumber = new ProbabilityController().getRandomNumber();
		
		
		Main.getBoardController().setNextPlayerToMove();
		//Main.getNotificationHandler().sendNotificationMessage("Chess Game", "AI Skipped Turn");
		Main.getBoardController().getTeam1().getCommanderLogic().reset();
		Main.getBoardController().getTeam2().getCommanderLogic().reset();
		
		System.out.println("How did we get here?");
	}
	
	
	
	private void movePiece(PieceInformation bestMove, Location moveLocation, int randomNumber) {
		IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(bestMove.getPiece());
		controller.getTeam().getCommanderLogic().move(commander);
		
		IChessPiece pieceAtMoveLocation = controller.getBoardController().getPieceAtLocation(bestMove.getLocation());
		
		if(pieceAtMoveLocation == null) {
			controller.getBoardController().movePieceOnBoard(bestMove.getPiece(), bestMove.getLocation());
			System.out.println("Moved piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
			return;
		}
		
		if(canDoMove(randomNumber, bestMove.getKillProbality())) {
			controller.getBoardController().removePieceFromBoard(pieceAtMoveLocation);
			controller.getBoardController().movePieceOnBoard(bestMove.getPiece(), moveLocation);
			System.out.println("Moved piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
		}else {
			System.out.println("Unable to move piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber + ", (" + moveLocation.getX() + ", " + moveLocation.getZ() + ")");
		}
	}
	
	private void movePiece(IChessPiece bestMove, Location moveLocation, int randomNumber) {
		IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(bestMove);
		controller.getTeam().getCommanderLogic().move(commander);
		
		IChessPiece pieceAtMoveLocation = controller.getBoardController().getPieceAtLocation(moveLocation);
		
		if(pieceAtMoveLocation == null) {
			controller.getBoardController().movePieceOnBoard(bestMove, moveLocation);
			System.out.println("Moved piece: " + bestMove.getTexture().getTextureLocation() + ", random number: " + randomNumber);
			return;
		}
		
		ProbabilityController prod = new ProbabilityController();
		int[] chances = prod.getProbablity(bestMove, Main.getBoardController().getPieceAtLocation(moveLocation));
		
		if(canDoMove(randomNumber, chances)) {
			controller.getBoardController().removePieceFromBoard(pieceAtMoveLocation);
			controller.getBoardController().movePieceOnBoard(bestMove, moveLocation);
			System.out.println("Moved piece: " + bestMove.getTexture().getTextureLocation() + ", random number: " + randomNumber);
		}else {
			System.out.println("Unable to move piece: " + bestMove.getTexture().getTextureLocation() + ", random number: " + randomNumber + ", (" + moveLocation.getX() + ", " + moveLocation.getZ() + ")");
		}
	}
	
	
	private boolean canDoMove(int randomNumber, int[] probablity) {		
		if(probablity == null) {
			System.out.println("Null Probability");
			return true;
		}
		
		System.out.print("Needed Probablity : {");
		for(int x : probablity) {
			System.out.print(x + ",");
		}
		
		System.out.println("}");
		
		
		if(probablity.length < 3) { // TODO Maybe add this because it's unlikely
			//return false;
		}
		
		for(int x : probablity) {
			if(x == randomNumber) {
			return true;	
			}
		}
		return false;
	}
	
}
