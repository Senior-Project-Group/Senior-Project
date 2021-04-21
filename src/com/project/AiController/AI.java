package com.project.AiController;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	// Look for an attack with no movement first 
	
	// (Only if 2 or more moves left) Look to move to the highest value with the highest probability for success and do hierarchy for who will take the piece (archer, pawn, queen, bishop, king)
	// KNIGHT Look to move to the highest value with the highest probability for success and do hierarchy for who will take the piece
	// In danger check if == 1 move left (move the piece out of the way, if it can't move to safety, attack the enemy piece)
	// Random move
	
	private void run() {
		int randomNumber = new ProbabilityController().getRandomNumber();
		
		ArrayList<PieceInformation> information = commonFunctionsController.getAllPossibleMoves();
		
		if (attackWithNoMovesAI(information, randomNumber)) return;
		
		if (attackLookForHighestValueAI(information, randomNumber)) return;
		
		if (attackLookForHighestValueKnightOnlyAI(information, randomNumber)) return;
		
		if(inDangerCheckAI(information, randomNumber)) return;
		
		if (randomMoveAI(information, randomNumber)) return;
		
		Main.getBoardController().setNextPlayerToMove();

		Main.getBoardController().getTeam1().getCommanderLogic().reset();
		Main.getBoardController().getTeam2().getCommanderLogic().reset();
		
		System.out.println("How did we get here?");
	}
	
	private boolean attackWithNoMovesAI(ArrayList<PieceInformation> information, int randomNumber) {
		HashMap<IChessPiece, ArrayList<PieceInformation>> canAttackWithNoMovement = commonFunctionsController.generateMoveHashes(information).get(0);
		
		if(canAttackWithNoMovement == null || canAttackWithNoMovement.isEmpty()) return false;
		
		PieceInformation highestMove = null;
		
		int highestProb = 0;
		
		for(IChessPiece piece : canAttackWithNoMovement.keySet()) {
			ArrayList<PieceInformation> movements = canAttackWithNoMovement.get(piece);
			for(PieceInformation info : movements) {
				if(info.getKillProbablitySize() > highestProb) {
					highestProb = info.getKillProbablitySize();
					highestMove = info;
				}
			}
			
		}
		
		if(highestMove == null) return false;
		
		movePiece(highestMove, highestMove.getLocation(), randomNumber);
		
		return true;
	}
	
	private boolean attackLookForHighestValueAI(ArrayList<PieceInformation> information, int randomNumber) {
		
		return false;
	}
	
	private boolean attackLookForHighestValueKnightOnlyAI(ArrayList<PieceInformation> information, int randomNumber) {
		
		return false;
	}
	
	private boolean inDangerCheckAI(ArrayList<PieceInformation> information, int randomNumber) {
		return false;
	}
	
	
	private boolean randomMoveAI(ArrayList<PieceInformation> information, int randomNumber) {
		return false;
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
