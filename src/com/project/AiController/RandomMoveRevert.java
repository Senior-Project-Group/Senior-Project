package com.project.AiController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;

public class RandomMoveRevert {

/*
	// This can only attack: Pawn or Rook. If it can't attack either, do a random move
	private boolean randomMoveAI(int randomNumber, ArrayList<PieceInformation> information) {
		PieceInformation bestMove = null;
		
		// Key: A piece on friendly team
		// Values: ArrayList of all pieces this 
		
		
		HashMap<IChessPiece, ArrayList<PieceInformation>> piecesThatCanAttackHash = new HashMap<IChessPiece, ArrayList<PieceInformation>>();
		
		HashMap<IChessPiece, ArrayList<PieceInformation>> piecesThatCannotAttackHash = new HashMap<IChessPiece, ArrayList<PieceInformation>>();
		
		for(PieceInformation piece : information) {
			if(piece.canKillPiece()) {
				if(piecesThatCanAttackHash.containsKey(piece.getPiece())) {
					ArrayList<PieceInformation> temp = piecesThatCanAttackHash.get(piece.getPiece());
					if(!temp.contains(piece)) {
						temp.add(piece);
						piecesThatCanAttackHash.put(piece.getPiece(), temp);
					}
				}else {
					ArrayList<PieceInformation> temp = new ArrayList<PieceInformation>();
					temp.add(piece);
					piecesThatCanAttackHash.put(piece.getPiece(), temp);
					
				}
				
			}else {
				if(piecesThatCannotAttackHash.containsKey(piece.getPiece())) {
					ArrayList<PieceInformation> temp = piecesThatCannotAttackHash.get(piece.getPiece());
					if(!temp.contains(piece)) {
						temp.add(piece);
						piecesThatCannotAttackHash.put(piece.getPiece(), temp);
					}
				}else {
					ArrayList<PieceInformation> temp = new ArrayList<PieceInformation>();
					temp.add(piece);
					piecesThatCannotAttackHash.put(piece.getPiece(), temp);
				}
			}
			
		}
		
		
		for(IChessPiece piece : piecesThatCanAttackHash.keySet()) {
			ArrayList<PieceInformation> attackMoves = piecesThatCanAttackHash.get(piece);
			
			// Very special case. Only the king can move
			if(piecesThatCannotAttackHash.isEmpty() && piecesThatCanAttackHash.size() == 1 && piece instanceof KingPiece) {
				for(PieceInformation info : attackMoves) {
					if(bestMove == null) {
						bestMove = info;
					}else {
						if(info.getKillProbablitySize() > bestMove.getKillProbablitySize()) {
							bestMove = info;
						}
					}
				}
				
				IChessPiece pieceAtMoveLocation = controller.getBoardController().getPieceAtLocation(bestMove.getLocation());
				
				if(pieceAtMoveLocation == null) {
					IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(bestMove.getPiece());
					controller.getTeam().getCommanderLogic().move(commander);
					
					controller.getBoardController().movePieceOnBoard(bestMove.getPiece(), bestMove.getLocation());
					System.out.println("Moved piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
					return true;
				}
				
				if(canDoMove(randomNumber, bestMove.getKillProbality())) {
					IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(bestMove.getPiece());
					controller.getTeam().getCommanderLogic().move(commander);
					
					controller.getBoardController().removePieceFromBoard(pieceAtMoveLocation);
					
					controller.getBoardController().movePieceOnBoard(bestMove.getPiece(), bestMove.getLocation());
					System.out.println("Moved piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
					return true;
				}else {
					System.out.println("Unable to move piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
					return true;
				}
			}
			
		}
		
		// Remove the king piece from moving
		for(IChessPiece piece : piecesThatCanAttackHash.keySet()) {
			if(piece instanceof KingPiece) {
				piecesThatCanAttackHash.remove(piece);
				break;
			}
			
		}
		
		
		if(piecesThatCanAttackHash.size() >= 1) {
			
			int random = new Random().nextInt(piecesThatCanAttackHash.size());
			Object[] pieces = piecesThatCanAttackHash.keySet().toArray();
			Object piece = pieces[random];
			
			ArrayList<PieceInformation> movements = piecesThatCanAttackHash.get(piece);
			
			int random1 = new Random().nextInt(movements.size());
			
			bestMove = movements.get(random1);
			
			IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(bestMove.getPiece());
			controller.getTeam().getCommanderLogic().move(commander);
			
			
			IChessPiece pieceAtMoveLocation = controller.getBoardController().getPieceAtLocation(bestMove.getLocation());
			
			if(pieceAtMoveLocation == null) {
				controller.getTeam().getCommanderLogic().move(commander);
				
				controller.getBoardController().movePieceOnBoard(bestMove.getPiece(), bestMove.getLocation());
				System.out.println("Moved piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
				return true;
			}
			
			
			if(canDoMove(randomNumber, bestMove.getKillProbality())) {
				controller.getBoardController().removePieceFromBoard(pieceAtMoveLocation);
				controller.getBoardController().movePieceOnBoard(bestMove.getPiece(), bestMove.getLocation());
				System.out.println("Moved piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
				return true;
			}else {
				System.out.println("Unable to move piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
				return true;
			}
			
			
		}
		
		
		// This is a random move here
		
		if(piecesThatCanAttackHash.isEmpty() && piecesThatCannotAttackHash.isEmpty()) {
			return false;
		}
		
		int random = new Random().nextInt(piecesThatCannotAttackHash.size());
		Object[] p = piecesThatCannotAttackHash.keySet().toArray();
		//IChessPiece[] pieces = (IChessPiece[]) piecesThatCannotAttackHash.values().toArray();
		//IChessPiece piece = pieces[random];
		Object p1 = p[random];
		ArrayList<PieceInformation> movements = piecesThatCannotAttackHash.get(p1);
		
		int random1 = new Random().nextInt(movements.size());
		
		bestMove = movements.get(random1);
		System.out.println(bestMove);
		
		IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(bestMove.getPiece());
		controller.getTeam().getCommanderLogic().move(commander);
		
		IChessPiece pieceAtMoveLocation = controller.getBoardController().getPieceAtLocation(bestMove.getLocation());
		
		if(pieceAtMoveLocation == null) {
			controller.getTeam().getCommanderLogic().move(commander);
			
			controller.getBoardController().movePieceOnBoard(bestMove.getPiece(), bestMove.getLocation());
			System.out.println("Moved piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
			return true;
		}
		
		
		if(canDoMove(randomNumber, bestMove.getKillProbality())) {
			controller.getBoardController().removePieceFromBoard(pieceAtMoveLocation);
			controller.getBoardController().movePieceOnBoard(bestMove.getPiece(), bestMove.getLocation());
			System.out.println("Moved piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
			return true;
		}else {
			System.out.println("Unable to move piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
			return true;
		}
		
	}
	
	*/
}
