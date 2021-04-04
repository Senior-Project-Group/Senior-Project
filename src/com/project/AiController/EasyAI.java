package com.project.AiController;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.ChessPieces.BishopPiece;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;
import com.project.ChessPieces.ProbabilityController;

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
		
		if(inDangerAICheck(randomNumber, information, threats)) return;
		
		if(bishopAttackAI(randomNumber, information)) return;
		
		if(knightAttackAI(randomNumber, information)) return;
		
		if(queenAttackAI(randomNumber, information)) return;
		
		// Random move after this
		if(randomMoveAI(randomNumber, information)) return;
		
		// If we get to here. I have no idea what to do.
		System.out.println("How did we get here?");
	}
	
	private boolean inDangerAICheck(int randomNumber, ArrayList<PieceInformation> information, ArrayList<ThreatenedPiece> threats) {
		ThreatenedPiece highestThreatened = null;
		for(ThreatenedPiece piece : threats) {
			// King is threatened
			if(piece.getThreatenedPiece() instanceof KingPiece) {
				highestThreatened = piece;
				break;
			}
			
			// Bishop threatened
			if(piece.getThreatenedPiece() instanceof BishopPiece) {
				highestThreatened = piece;
			}
			
		}
		
		// We don't have any kings/bishops that can be killed on our team
		if(highestThreatened == null) return false;
		
		// Oh no. We do have kings/bishops that can be killed on our team
		ArrayList<IChessPiece> dangers = highestThreatened.getPiecesThreatening();
		
		IChessPiece bestChance = null;
		for(IChessPiece danger : dangers) {
			ArrayList<IChessPiece> canStop = commonFunctionsController.getPieceWhoCanKill(danger);
			for(IChessPiece stop : canStop) {
				
			}
			
		}
		
		
		return false;
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
	
	private boolean randomMoveAI(int randomNumber, ArrayList<PieceInformation> information) {
		
		PieceInformation bestMove = null;
		
		for(PieceInformation piece : information) {
			
			
		}
		
		if(bestMove == null) {
			
		}
		
		return false;
	}
	
	
	private boolean canDoMove(int randomNumber, int[] probablity) {
		if(probablity.length < 3) { // TODO Maybe add this because it's unlikely
			
		}
		for(int x : probablity) {
			if(x == randomNumber) {
			return true;	
			}
		}
		return false;
	}
}
