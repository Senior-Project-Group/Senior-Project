package com.project.Render;

import java.awt.event.MouseAdapter;

import javax.swing.JLabel;

import com.project.BoardController.Location;
import com.project.ChessPieces.IChessPiece;

// Object that holds information of the label/texture of the next move.
// When you press on a piece, points of touching appear on the board. You press those to interact and move the piece.
public class NextMoveObject {

	private JLabel label;
	
	private Location coords;
	
	private IChessPiece piece;
	
	private boolean isPieceRendered;
	
	private MouseAdapter adapter;
	
	public NextMoveObject(IChessPiece piece, JLabel label, Location location, boolean isPieceRendered, MouseAdapter adapter) {
		this.isPieceRendered = isPieceRendered;
		this.piece = piece;
		this.label = label;
		this.coords = location;
		this.adapter = adapter;
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	public Location getLocation() {
		return coords;
	}
	
	public IChessPiece getPiece() {
		return piece;
	}
	
	public boolean isPieceRendered() {
		return isPieceRendered;
	}
	
	public MouseAdapter getMouseAdapter() {
		return adapter;
	}
}
