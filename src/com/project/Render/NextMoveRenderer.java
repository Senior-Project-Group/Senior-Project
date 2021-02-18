package com.project.Render;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import com.project.BoardController.GameType;
import com.project.BoardController.Location;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;
import com.project.Main.Main;
import com.project.Multiplayer.NextMoveParser;


// Object that is responsible for determining the next locations the currently selected piece can or can't move.
public class NextMoveRenderer {
	
	private ArrayList<NextMoveObject> labels; // Stores all the next moves objects into the manager
	
	
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
				pieceLabel.setBorder(border);
			}else {
				pieceLabel = new JLabel("");
				pieceLabel.setBounds(point.getX(), point.getZ(), center.getDimensionOfPiece(), center.getDimensionOfPiece());
				
				Border border = BorderFactory.createLineBorder(Color.BLUE, 3);
				pieceLabel.setBorder(border);
			}
			
			
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
			    			
			    			Location pieceLocation = piece.getLocation();
			    			
			    			// Check if this was a castling move
			    			if(piece instanceof KingPiece) {
			    				if(!piece.hasMovedAlready()) { // This is the kings first move
			    					// Check the location of the move
			    					if(obj.getLocation().equals(new Location(6, piece.getLocation().getZ()))) { // Check right castle
			    						// It was a castle move
			    						IChessPiece rightRook = Main.getBoardController().getPieceAtLocation(new Location(7, piece.getLocation().getZ()));
			    						if(rightRook != null) {
			    							Main.getBoardController().movePieceOnBoard(rightRook, new Location(5, piece.getLocation().getZ()));
			    							System.out.println("Right Castle Done");
			    						}
			    					}
			    					
			    					if(obj.getLocation().equals(new Location(2, piece.getLocation().getZ()))) { // Check left castle
			    						// It was a castle move
			    						IChessPiece leftRook = Main.getBoardController().getPieceAtLocation(new Location(0, piece.getLocation().getZ()));
			    						if(leftRook != null) {
			    							Main.getBoardController().movePieceOnBoard(leftRook, new Location(3, piece.getLocation().getZ()));
			    							System.out.println("Left Castle Done");
			    						}
			    					}
			    					
			    				}
			    				
			    			}
			    			
			    			Main.getBoardController().movePieceOnBoard(piece, obj.getLocation());
			    			Main.getBoardController().setNextPlayerToMove();
			    			Main.getBoardController().getNextMoveRenderer().clearCurrentRender();
			    		
			    			
			    			if(Main.getBoardController().getCurrentGameType().equals(GameType.SQL_MULTIPLAYER)) {
			    				// Send the move to the SQL database
			    				new NextMoveParser(piece.getTeamType(), pieceLocation, obj.getLocation()).sendToDatabase(Main.getSQLHandler());
			    			}
			    			
			    			if(piece1 != null) {
		    					System.out.println("Destroying piece: " + piece1.getTexture().getTextureLocation());
		    					piece1.destroyPiece();
		    					Main.getBoardController().checkForGameFinished();
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