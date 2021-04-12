package com.project.AiController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import com.project.BoardController.Location;
import com.project.ChessPieces.BishopPiece;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;
import com.project.ChessPieces.KnightPiece;
import com.project.ChessPieces.ProbabilityController;
import com.project.Main.Main;

public class EasyAI {

	private AIController controller;
	private CommonAIFunctions commonFunctionsController;
	
	public EasyAI(AIController controller) {
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
		
		if(controller.getTeam().getCommanderLogic().getMaxMoves() - controller.getTeam().getAmountOfMovesDone() <= 2) {
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
		
		IChessPiece bestChance = null;
		for(IChessPiece danger : dangers) {
			ArrayList<IChessPiece> canStop = commonFunctionsController.getPieceWhoCanKill(danger);
			for(IChessPiece stop : canStop) {
				
			}
			
		}
		
		
		return false;
	}
	
	
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
				
				if(canDoMove(randomNumber, bestMove.getKillProbality())) {					
					
					IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(bestMove.getPiece());
					controller.getTeam().getCommanderLogic().move(commander);
					
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
			
			if(canDoMove(randomNumber, bestMove.getKillProbality())) {
				
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
		System.out.println("Blah:  " + bestMove.getPiece());
		if(canDoMove(randomNumber, bestMove.getKillProbality())) {
			
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
					
					IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(bestMove.getPiece());
					controller.getTeam().getCommanderLogic().move(commander);
					
					if(canDoMove(randomNumber, bestMove.getKillProbality())) {
						
						controller.getBoardController().movePieceOnBoard(bestMove.getPiece(), moveLocation);
						System.out.println("Moved piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
					}else {
						System.out.println("Unable to move piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
					}
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
					IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(bestMove.getPiece());
					controller.getTeam().getCommanderLogic().move(commander);
					
					if(canDoMove(randomNumber, bestMove.getKillProbality())) {
						controller.getBoardController().movePieceOnBoard(bestMove.getPiece(), moveLocation);
						System.out.println("Moved piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
					}else {
						System.out.println("Unable to move piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
					}
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
						
						IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(bestMove.getPiece());
						controller.getTeam().getCommanderLogic().move(commander);
						
						if(canDoMove(randomNumber, bestMove.getKillProbality())) {
							
							controller.getBoardController().movePieceOnBoard(bestMove.getPiece(), moveLocation);
							System.out.println("Moved piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
						}else {
							System.out.println("Unable to move piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
						}
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
						
						IChessPiece commander = controller.getTeam().getCommanderLogic().getCommanderForPiece(bestMove.getPiece());
						controller.getTeam().getCommanderLogic().move(commander);
						
						if(canDoMove(randomNumber, bestMove.getKillProbality())) {
							
							controller.getBoardController().movePieceOnBoard(bestMove.getPiece(), moveLocation);
							System.out.println("Moved piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
						}else {
							System.out.println("Unable to move piece: " + bestMove.getPiece().getTexture().getTextureLocation() + ", random number: " + randomNumber);
						}
						return true;
					}
			
			
			return false;
		}
	
	
	private boolean canDoMove(int randomNumber, int[] probablity) {
		if(probablity == null) {
			return true;
		}
		if(probablity.length < 3) { // TODO Maybe add this because it's unlikely
			return false;
		}
		
		for(int x : probablity) {
			if(x == randomNumber) {
			return true;	
			}
		}
		return false;
	}
	
}
