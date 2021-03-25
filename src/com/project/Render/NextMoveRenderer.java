package com.project.Render;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import com.project.BoardController.Location;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.ProbabilityController;
import com.project.Main.Main;
import com.project.TeamController.Team;
import com.project.TeamController.TeamType;


// Object that is responsible for determining the next locations the currently selected piece can or can't move.
public class NextMoveRenderer {
	
	private ArrayList<NextMoveObject> labels; // Stores all the next moves objects into the manager
	public static String promoType;
	
	
	// Default contructor to create the next move handler
	public NextMoveRenderer() {
		labels = new ArrayList<NextMoveObject>();
	}
	
	// Get all the currently rendered next moves
	public ArrayList<NextMoveObject> getRenders(){
		return labels;
	}

	// Remove all current renders
	public void clearCurrentRender() {
		for(NextMoveObject obj : labels) {
			if(!obj.isPieceRendered()) {
				obj.getLabel().setBorder(null);
				Main.getBoardController().getBoardObject().getFrame().remove(obj.getLabel());
			}else {
				// It's a rendered piece, remove the border
				obj.getLabel().setBorder(null);
				obj.getLabel().removeMouseListener(obj.getMouseAdapter());
			}
		}
		labels.clear();
		Main.getBoardController().getBoardObject().getFrame().repaint();
	}
	
	// This will render all the possible locations of the piece
	// In the end, I am hoping to make this work well and physically show the different moves the piece can do
	
	public void renderForPiece(IChessPiece piece) {
		clearCurrentRender();
		CenterPointManager center = new CenterPointManager();
		
		for(Location location : piece.getPossibleMoves()) {
			boolean isPieceRendered = false;
			Location point = center.centerPointAlgorithm(location.getX(), location.getZ());
			JLabel pieceLabel = null;
			// Check if location has a piece at it currently
			IChessPiece pieceAt = Main.getBoardController().getPieceAtLocation(location);
			if(pieceAt != null) {
				isPieceRendered = true;
				pieceLabel = pieceAt.getTexture().getPieceLabel();
				
				Border border = BorderFactory.createLineBorder(Color.GREEN, 3);
				
				// ADD THE ROLL INFORMATION
				
				
				ProbabilityController probability = new ProbabilityController();
				int[] values = probability.getProbablity(pieceAt, piece);
				
				String text = "";
				for(int x = 0; x != values.length; x++) {
					if(x != 0) {
						text = text + ", " + values[x];
					}else {
						text = text + values[x];
					}
				}
				
				pieceLabel.setBorder(border);
				
			}else {
				pieceLabel = new JLabel("");
				pieceLabel.setBounds(point.getX(), point.getZ(), center.getDimensionOfPiece(), center.getDimensionOfPiece());
				
				Border border = BorderFactory.createLineBorder(Color.BLUE, 3);
				pieceLabel.setBorder(border);
			}
			Main.getBoardController().getBoardObject().getFrame().repaint();
			
			MouseAdapter mouseListener = new MouseAdapter() {
			    public void mouseClicked(MouseEvent e) {
			    	if(Main.getBoardController().hasGameEnded()) {
			    		Main.getBoardController().getNextMoveRenderer().clearCurrentRender();
			    		return;
			    	}
			    	// Move the object to here
			    	for(NextMoveObject obj : labels) {
			    		if(obj.getLabel().equals(e.getComponent())) {
			    			// Check if there is a piece there
			    			Main.getBoardController().getNextMoveRenderer().clearCurrentRender();
			    			IChessPiece piece1 = Main.getBoardController().getPieceAtLocation(obj.getLocation());
			    			
			    			
			    			// TODO ADD FUZZY LOGIC HERE!
			    			if(piece1 != null) { // A piece is here
			    				DiceRollerGUI gui = new DiceRollerGUI();
			    				ProbabilityController prob = new ProbabilityController();
			    				int rand = prob.getRandomNumber();
			    				int[] valuesNeeded = prob.getProbablity(piece, piece1);
			    				
			    				try {
									Thread.sleep(2000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
			    				
			    				boolean success = false;
			    				for(int x = 0; x != valuesNeeded.length; x++) {
			    					if(rand == valuesNeeded[x]) {
			    						success = true;
			    					}
			    				}
			    				
			    				if(success) {
			    					gui.getRollingLabel().setText(rand + " (Success!)");
			    				}else {
			    					gui.getRollingLabel().setText(rand + " (Missed!)");
			    				}
			    				
			    				try {
									Thread.sleep(1300);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
			    				
			    				gui.getFrame().dispose();
			    				Main.getBoardController().movePieceOnBoard(piece, obj.getLocation());
			    				Main.getBoardController().getNextMoveRenderer().clearCurrentRender();
			    				
			    				piece1.destroyPiece();
		    					Main.getBoardController().checkForGameFinished();
			    				
			    				
			    			}else {
			    				// NO PIECE HERE, JUST MOVE THE PIECE
			    				Main.getBoardController().movePieceOnBoard(piece, obj.getLocation());
			    				Main.getBoardController().getNextMoveRenderer().clearCurrentRender();
			    			}
			    			
			    			// Determine who's going to move next
			    			
			    			
			    			// TODO FLUZZY LOGIC! CHECK IF NEXT TEAM TO MOVE
			    			Team team = null;
			    			if(Main.getBoardController().getCurrentPlayerToMove().equals(TeamType.BLACK)) {
			    				// Team 1
			    				team = Main.getBoardController().getTeam1();
			    			}else {
			    				team = Main.getBoardController().getTeam2();
			    			}
			    			
			    			if(team.getCommanderLogic().hasDoneAllMoves()) {
			    				Main.getBoardController().setNextPlayerToMove();
			    				team.getCommanderLogic().reset();
			    			}else {
			    				team.getCommanderLogic().move(team.getCommanderLogic().getCommanderForPiece(piece));
			    			}
			    			
			    			break;
			    		}
			    	}
			    }
			}; 
			
			pieceLabel.addMouseListener(mouseListener);
			
			labels.add(new NextMoveObject(piece, pieceLabel, location, isPieceRendered, mouseListener));
			if(!isPieceRendered) {
				Main.getBoardController().getBoardObject().getFrame().add(pieceLabel);
			}
			    Main.getBoardController().getBoardObject().getFrame().repaint();
		}
		
	}
	
}