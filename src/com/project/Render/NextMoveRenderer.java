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
import com.project.Main.Main;


// Object that is responsible for determining the next locations the currently selected piece can or can't move.
public class NextMoveRenderer {
	
	private ArrayList<NextMoveObject> labels;
	public NextMoveRenderer() {
		labels = new ArrayList<NextMoveObject>();
	}
	
	public ArrayList<NextMoveObject> getRenders(){
		return labels;
	}

	// Remove all current renders
	public void clearCurrentRender() {
		for(NextMoveObject obj : labels) {
			if(!obj.isPieceRendered()) {
				Main.getBoardController().getBoardObject().getFrame().remove(obj.getLabel());
			}else {
				// It's a rendered piece, remove the border
				obj.getLabel().setBorder(null);
			}
		}
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
			
			pieceLabel.addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent e) {  
			    	// Move the object to here
			    	for(NextMoveObject obj : labels) {
			    		if(obj.getLabel().equals(e.getComponent())) {
			    			// Check if there is a piece there
			    			IChessPiece piece1 = Main.getBoardController().getPieceAtLocation(obj.getLocation());
			    			if(piece1 != null) {
			    				System.out.println(piece1.getLocation().getX() + "," + piece1.getLocation().getZ());
			    				System.out.println("Destroying piece");
			    				piece1.destroyPiece();
			    			}
			    			
			    			Main.getBoardController().movePieceOnBoard(piece, obj.getLocation());
			    			Main.getBoardController().setNextPlayerToMove();
			    			Main.getBoardController().getNextMoveRenderer().clearCurrentRender();
			    			break;
			    		}
			    	}
			    }  
			}); 
			labels.add(new NextMoveObject(piece, pieceLabel, location, isPieceRendered));
			if(!isPieceRendered) {
				Main.getBoardController().getBoardObject().getFrame().add(pieceLabel);
			}
			Main.getBoardController().getBoardObject().getFrame().repaint();
		}
		
	}
	
}