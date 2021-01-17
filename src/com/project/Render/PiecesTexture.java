package com.project.Render;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.project.BoardController.Location;
import com.project.ChessPieces.BishopPiece;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;
import com.project.ChessPieces.KnightPiece;
import com.project.ChessPieces.PawnPiece;
import com.project.ChessPieces.QueenPiece;
import com.project.ChessPieces.RookPiece;
import com.project.Main.Main;
import com.project.TeamController.TeamType;

public class PiecesTexture {

	private String textureLocation;
	
	private JLabel pieceLabel;
	
	public PiecesTexture(IChessPiece piece, int x, int z, TeamType teamType) {
		pieceLabel = new JLabel("");
		
		CenterPointManager point = new CenterPointManager();
		Location location = point.centerPointAlgorithm(x, z);

		String link = "";
		if(teamType.equals(TeamType.WHITE)) {
		if(piece instanceof BishopPiece) {
			link = "white_bishop.png";
		}else if(piece instanceof KingPiece) {
			link = "white_king.png";
		}else if(piece instanceof KnightPiece) {
			link = "white_knight.png";
		}else if(piece instanceof PawnPiece) {
			link = "white_pawn.png";
		}else if(piece instanceof QueenPiece) {
			link = "white_queen.png";
		}else if(piece instanceof RookPiece) {
			link = "white_rook.png";
		}
			
		}else {
			if(piece instanceof BishopPiece) {
				link = "black_bishop.png";
			}else if(piece instanceof KingPiece) {
				link = "black_king.png";
			}else if(piece instanceof KnightPiece) {
				link = "black_knight.png";
			}else if(piece instanceof PawnPiece) {
				link = "black_pawn.png";
			}else if(piece instanceof QueenPiece) {
				link = "black_queen.png";
			}else if(piece instanceof RookPiece) {
				link = "black_rook.png";
			}
		}
		
		pieceLabel.setIcon(new ImageIcon(getClass().getResource(link)));
		pieceLabel.setBounds(location.getX(), location.getZ(), point.getDimensionOfPiece(), point.getDimensionOfPiece());
		
		Main.getBoardController().getBoardObject().getFrame().add(pieceLabel);
	}
	
	public String getTextureLocation() {
		return textureLocation;
	}
	
	public JLabel getPieceLabel() {
		return pieceLabel;
	}
	
	public void removeTextureFromBoard() {
		Main.getBoardController().getBoardObject().getFrame().remove(getPieceLabel());
	}
}