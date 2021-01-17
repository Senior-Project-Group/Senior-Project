package com.project.Render;

import javax.swing.JLabel;

import com.project.BoardController.Location;

// Object that holds information of the label/texture of the next move.
// When you press on a piece, points of touching appear on the board. You press those to interact and move the piece.
public class NextMoveObject {

	private JLabel label;
	
	private Location coords;
	
	public NextMoveObject(JLabel label, Location location) {
		this.label = label;
		this.coords = location;
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	public Location getLocation() {
		return coords;
	}
}
