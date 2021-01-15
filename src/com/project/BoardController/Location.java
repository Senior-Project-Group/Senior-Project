package com.project.BoardController;

public class Location {

	// Starting at the lowest corner (0,0)
	// Up to the top corner which will be (7,7)
	
	private int x, z;
	public Location() {}
	
	public Location(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public int getX() {
		return x;
	}
	
	public int getZ() {
		return z;
	}
	
	public void setNewLocation(Location location) {
		this.x = location.getX();
		this.z = location.getZ();
	}
	
}