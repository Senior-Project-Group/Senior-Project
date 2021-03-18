//Alpha-beta pruning in the hardest difficulty will reduce noncritical moves in the search tree, making the AI more strategic.
package com.project.AiController;

import java.util.ArrayList;
import java.util.Random;

import com.project.BoardController.Location;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;

public class EasyAI {

	private AIController controller;
	private CommonAIFunctions commonFunctionsController;
	
	public EasyAI(AIController controller) {
		this.controller = controller;
		this.commonFunctionsController = new CommonAIFunctions(controller);
		run();
	}
	
	private void run() {
		ArrayList<PieceInformation> information = commonFunctionsController.getAllPossibleMoves();
		
		boolean hasKilled = false;
		// Run it once to check for killing a king.
		for(PieceInformation info : information) {
			if(info.canKillPiece()) {
				Location location = commonFunctionsController.canPieceTakeKing(info.getPiece());
				if(location != null) {
					// Can kill the king
					controller.getBoardController().removePieceFromBoard(controller.getBoardController().getPieceAtLocation(location));
					controller.getBoardController().movePieceOnBoard(info.getPiece(), location);
					controller.getBoardController().setNextPlayerToMove();
					hasKilled = true;
					break;
				}
			}
		}
		// The king has been killed already.
		if(hasKilled) return;
		
		int maxCanKill = 0;
		ArrayList<PieceInformation> canKill = new ArrayList<PieceInformation>();
		
		// Run it again and check the best pieces to take
		for(PieceInformation info : information) {
			if(info.canKillPiece()) { // It can kill a piece
				System.out.println("Can Kill King");
				if(maxCanKill == info.getRankOfMove()) {
					canKill.add(info);
				}else if (maxCanKill < info.getRankOfMove()) { // It's actually a higher rank
					maxCanKill = info.getRankOfMove();
					canKill.clear();
					canKill.add(info);
				}
				
			}
		}
		
		IChessPiece movedPiece = null;
		boolean isFirstMove = false;
		
		
		if(canKill.isEmpty()) { // We can't take any pieces yet, random move
			PieceInformation p = information.get(new Random().nextInt(information.size()));
			  IChessPiece atMoveLocation = controller.getBoardController().getPieceAtLocation(p.getLocation());
			  if(atMoveLocation != null) {
				  controller.getBoardController().removePieceFromBoard(atMoveLocation);
			  }
			  isFirstMove = p.getPiece().hasMovedAlready();
			  controller.getBoardController().movePieceOnBoard(p.getPiece(), p.getLocation());
			  controller.getBoardController().setNextPlayerToMove();
			  movedPiece = p.getPiece();
		}else { // It's not empty, we can take pieces, take highest piece
			
			Random rand = new Random();
			int number = rand.nextInt(10) + 1;
			if(number > 6) { // Do a logical move if it is
				PieceInformation p;
				if(canKill.size() == 1) {
					p = canKill.get(0);
				}else {
					p = canKill.get(new Random().nextInt(canKill.size()));
				}
				
				IChessPiece atMoveLocation = controller.getBoardController().getPieceAtLocation(p.getLocation());
				  if(atMoveLocation != null) {
					  controller.getBoardController().removePieceFromBoard(atMoveLocation);
				  }
				  isFirstMove = p.getPiece().hasMovedAlready();
				  controller.getBoardController().movePieceOnBoard(p.getPiece(), p.getLocation());
				  controller.getBoardController().setNextPlayerToMove();
				  movedPiece = p.getPiece();
			}else {
				// Do a random move, doesn't matter
				PieceInformation p = information.get(new Random().nextInt(information.size()));
				  IChessPiece atMoveLocation = controller.getBoardController().getPieceAtLocation(p.getLocation());
				  if(atMoveLocation != null) {
					  controller.getBoardController().removePieceFromBoard(atMoveLocation);
				  }
				  isFirstMove = p.getPiece().hasMovedAlready();
				  controller.getBoardController().movePieceOnBoard(p.getPiece(), p.getLocation());
				  controller.getBoardController().setNextPlayerToMove();
				  movedPiece = p.getPiece();
			}
			

		}
		// Check information
		if(movedPiece != null) {
			commonFunctionsController.promotePawnMove(movedPiece);
		}
		
		if(movedPiece instanceof KingPiece && !isFirstMove) {
			commonFunctionsController.checkCastleMove(movedPiece);
		}
		
	}
}
