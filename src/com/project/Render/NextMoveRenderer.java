package com.project.Render;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;

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
			Main.getBoardController().getBoardObject().getFrame().remove(obj.getLabel());
		}
	}
	
	// This will render all the possible locations of the piece
	// In the end, I am hoping to make this work well and physically show the different moves the piece can do
	public void renderForPiece(IChessPiece piece) {
		clearCurrentRender();
		CenterPointManager center = new CenterPointManager();
		for(Location location : piece.getPossibleMoves()) {
			Location point = center.centerPointAlgorithm(location.getX(), location.getZ());
			JLabel pieceLabel = new JLabel("");
			//pieceLabel.setIcon(new ImageIcon(getClass().getResource("green.png")));
			pieceLabel.setBounds(point.getX(), point.getZ(), center.getDimensionOfPiece(), center.getDimensionOfPiece());
			
			pieceLabel.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	// Move the object to here
			    	for(NextMoveObject obj : labels) {
			    		if(obj.getLabel().equals(e.getComponent())) {
			    			Main.getBoardController().movePieceOnBoard(piece, obj.getLocation());
			    			Main.getBoardController().setNextPlayerToMove();
			    			break;
			    		}
			    	}
			    }  
			}); 
			labels.add(new NextMoveObject(pieceLabel, location));
			Main.getBoardController().getBoardObject().getFrame().add(pieceLabel);
		}
		
	}
	
}