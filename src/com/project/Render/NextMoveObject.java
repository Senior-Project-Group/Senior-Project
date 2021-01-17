package com.project.Render;

import javax.swing.JLabel;

import com.project.BoardController.Location;

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
