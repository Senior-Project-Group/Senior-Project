package com.project.AiController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.project.BoardController.Location;
import com.project.ChessPieces.BishopPiece;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;
import com.project.ChessPieces.KnightPiece;
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
		// How this AI will work:
		
		// Most dangerous pieces: King, queen (has the second most power), Knight (knight has the most power)
		// Goal: Kill king, if can't kill king, then kill bishop next, then knight then queen
		
		// Check if any piece can take out the king (if the chance is 50% or higher perform action)
		// Check if any bishops can be taken out next (if the chance is 50% or higher perform action)
		
		// Check if king/bishops on our side are in danger.  If there is over a 33% chance, attempt to move piece to counter it
		// If they aren't in danger, find all pieces that can die on the other team and try and kill it, but check if moving the piece will make the king/bishops exposed and at risk
		
		// If no pieces can kill a piece, then randomly move a piece, but check if moving the piece will make the king/bishops exposed and at risk
		
		ArrayList<PieceInformation> information = commonFunctionsController.getAllPossibleMoves();
		ArrayList<ThreatenedPiece> threats = commonFunctionsController.getThreatenedPieces();
		
		ProbabilityController prod = new ProbabilityController();
		int randomNumber = prod.getRandomNumber();
		
		if(kingAttackAI(randomNumber, information)) return;
		
		if(controller.getTeam().getCommanderLogic().getMaxMoves() - controller.getTeam().getAmountOfMovesDone() <= 1) {
			if(inDangerAICheck(randomNumber, information, threats)) return;
		}
		
		if(bishopAttackAI(randomNumber, information)) return;
		
		if(knightAttackAI(randomNumber, information)) return;
		
		if(queenAttackAI(randomNumber, information)) return;
		
		// Random move after this
		if(randomMoveAI(randomNumber, information)) return;
		
		// If we get to here. I have no idea what to do.
		
		Main.getBoardController().setNextPlayerToMove();
		//Main.getNotificationHandler().sendNotificationMessage("Chess Game", "AI Skipped Turn");
		Main.getBoardController().getTeam1().getCommanderLogic().reset();
		Main.getBoardController().getTeam2().getCommanderLogic().reset();
		
		System.out.println("How did we get here?");
	}
	
	private boolean inDangerAICheck(int randomNumber, ArrayList<PieceInformation> information, ArrayList<ThreatenedPiece> threats) {
		ThreatenedPiece highestThreatened = null;
		if(threats == null) {
			return false;
		}
		
		for(ThreatenedPiece piece : threats) {
			// King is threatened
			if(piece.getThreatenedPiece() instanceof KingPiece) {
				highestThreatened = piece;
				break;
			}
			
			// Bishop threatened
			if(piece.getThreatenedPiece() instanceof BishopPiece) {
				if(highestThreatened == null) {
					highestThreatened = piece;
				}else {
					if(highestThreatened.getThreatenedPiece() instanceof KnightPiece) {
						highestThreatened = piece;
					}
					
				}
			}
			
			if(piece.getThreatenedPiece() instanceof KnightPiece) {
				if(highestThreatened == null) {
					highestThreatened = piece;
				}else {
					if(!(highestThreatened.getThreatenedPiece() instanceof BishopPiece)) {
						highestThreatened = piece;
					}
				}
			}
			
		}
		
		// We don't have any kings/bishops that can be killed on our team
		if(highestThreatened == null) return false;
		
		// Oh no. We do have kings/bishops/knight that can be killed on our team
		ArrayList<IChessPiece> dangers = highestThreatened.getPiecesThreatening();
		
		if(dangers == null) return false;
		
			IChessPiece biggestThreat = null;
			
			for(IChessPiece danger : dangers) {
				if(biggestThreat == null) {
					biggestThreat = danger;
				}
				
				ProbabilityController prod = new ProbabilityController();
				
				int[] currentBiggest = prod.getProbablity(biggestThreat, highestThreatened.getThreatenedPiece());
				
				int[] chances = prod.getProbablity(danger, highestThreatened.getThreatenedPiece());
				
				if(chances.length > currentBiggest.length) {
					biggestThreat = danger;
				}
				
			}
			
			if(biggestThreat == null) return false;
			
			ArrayList<IChessPiece> canTakeOut = commonFunctionsController.findPiecesToMoveToLocation(biggestThreat.getLocation());
			
			if(canTakeOut != null && !canTakeOut.isEmpty()) {
				// There are pieces that can attack the piece
				
				IChessPiece bestToTakeOut = null;
				for(IChessPiece piece : canTakeOut) {
					ProbabilityController prod = new ProbabilityController();
					if(bestToTakeOut == null) {
						bestToTakeOut = piece;
					}
					
					int[] currentBiggest = prod.getProbablity(bestToTakeOut, biggestThreat);
					
					int[] chances = prod.getProbablity(piece, biggestThreat);
					
					if(chances.length > currentBiggest.length) {
						bestToTakeOut = piece;
					}
					
				}
				
				// Now perform the action
				movePiece(bestToTakeOut, biggestThreat.getLocation(), randomNumber);
				return true;
			}else {
				// There are NO pieces that can attack the attacker
				ArrayList<Location> moveMe = highestThreatened.getThreatenedPiece().getPossibleMoves();
				
				for(Location findNoneThreatendLocation : moveMe) {
					ArrayList<IChessPiece> dangerChecker = commonFunctionsController.findPiecesToMoveToLocationEnemy(findNoneThreatendLocation);
					if(dangerChecker == null) {
						// This is a safe spot to move too.
						movePiece(highestThreatened.getThreatenedPiece(), findNoneThreatendLocation, randomNumber);
						return true;
					}
					
				}
				
				// There is no safe movement location
				
				Random rand = new Random();
				if(moveMe.size() == 0) {
					Main.getBoardController().setNextPlayerToMove();
					return true;
				}
				int random = rand.nextInt(moveMe.size());
				Location move = moveMe.get(random);
				
				movePiece(highestThreatened.getThreatenedPiece(), move, randomNumber);
				return true;
				
			}
		}
	
	
	// This can only attack: Pawn or Rook. If it can't attack either, do a random move
	private boolean randomMoveAI(int randomNumber, ArrayList<PieceInformation> information) {
		System.out.println("Running random move.");
		PieceInformation bestMove = null;
		
		// Key: A piece on friendly team
		// Values: ArrayList of all pieces this
		
		ArrayList<HashMap<IChessPiece, ArrayList<PieceInformation>>> array = commonFunctionsController.generateMoveHashes(information);
		
		HashMap<IChessPiece, ArrayList<PieceInformation>> piecesThatCanAttackHash = array.get(0);
		
		HashMap<IChessPiece, ArrayList<PieceInformation>> piecesThatCannotAttackHash = array.get(1);
		
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
				
				IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(bestMove.getPiece());
				controller.getTeam().getCommanderLogic().move(commander);
				
				if(pieceAtMoveLocation == null) {
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
		
		IChessPiece pieceAtMoveLocation = controller.getBoardController().getPieceAtLocation(bestMove.getLocation());
		
		IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(bestMove.getPiece());
		controller.getTeam().getCommanderLogic().move(commander);
		
		if(pieceAtMoveLocation == null) {
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
	
	
	// Return true if action successfully done
	private boolean kingAttackAI(int randomNumber, ArrayList<PieceInformation> information) {
		PieceInformation bestMove = null;
		Location moveLocation = null;
		
		// Check if any piece can attempt to take the king
				for(PieceInformation info : information) {
					if(info.canKillPiece()) {
						Location location = commonFunctionsController.canPieceTakeKing(info.getPiece());
						if(location != null) {
							moveLocation = location;
							if(bestMove == null) {
								bestMove = info;
							}else {
								if(bestMove.getKillProbablitySize() > info.getKillProbablitySize()) {
									bestMove = info;
								}
							}
						}
					}
				}
				
				if(bestMove != null && moveLocation != null) {
					movePiece(bestMove, moveLocation, randomNumber);
					return true;
				}
		
		
		return false;
	}
	
	
	// Return true if action successfully done
	private boolean bishopAttackAI(int randomNumber, ArrayList<PieceInformation> information) {
		PieceInformation bestMove = null;
		Location moveLocation = null;
		
		// Check if any piece can attempt to take the king
				for(PieceInformation info : information) {
					if(info.canKillPiece()) {
						Location location = commonFunctionsController.canPieceTakeBishop(info.getPiece());
						if(location != null) {
							moveLocation = location;
							if(bestMove == null) {
								bestMove = info;
							}else {
								if(bestMove.getKillProbablitySize() > info.getKillProbablitySize()) {
									bestMove = info;
								}
							}
						}
					}
				}
				
				
				if(bestMove != null && moveLocation != null) {
					movePiece(bestMove, moveLocation, randomNumber);
					return true;
				}
		
		
		return false;
	}
	
	
	// Return true if action successfully done
	private boolean knightAttackAI(int randomNumber, ArrayList<PieceInformation> information) {
			PieceInformation bestMove = null;
			Location moveLocation = null;
			
			// Check if any piece can attempt to take the king
					for(PieceInformation info : information) {
						if(info.canKillPiece()) {
							Location location = commonFunctionsController.canPieceTakeKnight(info.getPiece());
							if(location != null) {
								moveLocation = location;
								if(bestMove == null) {
									bestMove = info;
								}else {
									if(bestMove.getKillProbablitySize() > info.getKillProbablitySize()) {
										bestMove = info;
									}
								}
							}
						}
					}
					
					if(bestMove != null && moveLocation != null) {
						movePiece(bestMove, moveLocation, randomNumber);
						return true;
					}
			
			
			return false;
		}
		
		// Return true if action successfully done
	private boolean queenAttackAI(int randomNumber, ArrayList<PieceInformation> information) {
			PieceInformation bestMove = null;
			Location moveLocation = null;
			
			// Check if any piece can attempt to take the king
					for(PieceInformation info : information) {
						if(info.canKillPiece()) {
							Location location = commonFunctionsController.canPieceTakeQueen(info.getPiece());
							if(location != null) {
								moveLocation = location;
								if(bestMove == null) {
									bestMove = info;
								}else {
									if(bestMove.getKillProbablitySize() > info.getKillProbablitySize()) {
										bestMove = info;
									}
								}
							}
						}
					}
					
					if(bestMove != null && moveLocation != null) {
						movePiece(bestMove, moveLocation, randomNumber);
						return true;
					}
			
			
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
