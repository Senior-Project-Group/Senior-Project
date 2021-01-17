package com.project.Render;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;

import com.project.BoardController.Location;
import com.project.ChessPieces.IChessPiece;
import com.project.Main.Main;

public class NextMoveRenderer {
	
	private ArrayList<NextMoveObject> labels;
	public NextMoveRenderer() {
		labels = new ArrayList<NextMoveObject>();
	}
	
	public ArrayList<NextMoveObject> getRenders(){
		return labels;
	}

	public void clearCurrentRender() {
		for(NextMoveObject obj : labels) {
			Main.getBoardController().getBoardObject().getFrame().remove(obj.getLabel());
		}
	}
	
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
			    	System.out.println("Moving Piece");
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