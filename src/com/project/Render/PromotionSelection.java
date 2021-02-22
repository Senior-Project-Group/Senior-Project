package com.project.Render;

import javax.swing.JLabel;

import com.project.BoardController.GameType;
import com.project.BoardController.Location;
import com.project.ChessPieces.BishopPiece;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KnightPiece;
import com.project.ChessPieces.QueenPiece;
import com.project.ChessPieces.RookPiece;
import com.project.Main.Main;
import com.project.Multiplayer.NextMoveParser;
import com.project.TeamController.TeamType;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PromotionSelection {
private JDialog frmPromotion;
	
	private IChessPiece piece;
	private Location fromLocation;
	
	public PromotionSelection(IChessPiece pieceMoved, Location fromLocation) {
		this.piece = pieceMoved;
		this.fromLocation = fromLocation;
		initialize();
		frmPromotion.setVisible(true);
	}

	
	private void initialize() {
		Main.getNotificationHandler().sendNotificationMessage("Update Piece Selector", "Please select piece to upgrade now");
		frmPromotion = new JDialog();
		frmPromotion.setTitle("Select a Piece");
		frmPromotion.setBounds(100, 100, 303, 269);
		frmPromotion.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		frmPromotion.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Select a promotion piece for the pawn");
		lblNewLabel.setBounds(51, 11, 239, 14);
		frmPromotion.getContentPane().add(lblNewLabel);
		
		JButton queenButton = new JButton(new ImageIcon(getClass().getResource("resources/white_queen.png")));
		queenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				piece.destroyPiece();
				if(piece.getTeamType().equals(TeamType.BLACK)) {
					Main.getBoardController().getTeam1().addNewPiece(new QueenPiece(piece.getLocation(), piece.getTeamType()));
				}else {
					Main.getBoardController().getTeam2().addNewPiece(new QueenPiece(piece.getLocation(), piece.getTeamType()));
				}
				if(Main.getBoardController().getCurrentGameType().equals(GameType.SQL_MULTIPLAYER)) {
					new NextMoveParser(piece.getTeamType(), fromLocation, piece.getLocation(), "QUEEN").sendToDatabase(Main.getSQLHandler());
				}
				frmPromotion.dispose();
			}
		});
		queenButton.setBounds(27, 78, 110, 54);
		frmPromotion.getContentPane().add(queenButton);
		
		JButton rookButton = new JButton(new ImageIcon(getClass().getResource("resources/white_rook.png")));
		rookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				piece.destroyPiece();
				if(piece.getTeamType().equals(TeamType.BLACK)) {
					Main.getBoardController().getTeam1().addNewPiece(new RookPiece(piece.getLocation(), piece.getTeamType()));
				}else {
					Main.getBoardController().getTeam2().addNewPiece(new RookPiece(piece.getLocation(), piece.getTeamType()));
				}
				if(Main.getBoardController().getCurrentGameType().equals(GameType.SQL_MULTIPLAYER)) {
					new NextMoveParser(piece.getTeamType(), fromLocation, piece.getLocation(), "ROOK").sendToDatabase(Main.getSQLHandler());
				}
				frmPromotion.dispose();
			}
		});
		rookButton.setForeground(Color.BLACK);
		rookButton.setBackground(Color.RED);
		rookButton.setBounds(147, 78, 110, 54);
		frmPromotion.getContentPane().add(rookButton);
		
		JButton bishopButton = new JButton(new ImageIcon(getClass().getResource("resources/white_bishop.png")));
		bishopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				piece.destroyPiece();
				if(piece.getTeamType().equals(TeamType.BLACK)) {
					Main.getBoardController().getTeam1().addNewPiece(new BishopPiece(piece.getLocation(), piece.getTeamType()));
				}else {
					Main.getBoardController().getTeam2().addNewPiece(new BishopPiece(piece.getLocation(), piece.getTeamType()));
				}
				if(Main.getBoardController().getCurrentGameType().equals(GameType.SQL_MULTIPLAYER)) {
					new NextMoveParser(piece.getTeamType(), fromLocation, piece.getLocation(), "BISHOP").sendToDatabase(Main.getSQLHandler());
				}
				frmPromotion.dispose();
			}
		});
		bishopButton.setForeground(Color.BLACK);
		bishopButton.setBackground(Color.ORANGE);
		bishopButton.setBounds(27, 143, 110, 54);
		frmPromotion.getContentPane().add(bishopButton);
		
		JButton knightButton = new JButton(new ImageIcon(getClass().getResource("resources/white_knight.png")));
		knightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				piece.destroyPiece();
				if(piece.getTeamType().equals(TeamType.BLACK)) {
					Main.getBoardController().getTeam1().addNewPiece(new KnightPiece(piece.getLocation(), piece.getTeamType()));
				}else {
					Main.getBoardController().getTeam2().addNewPiece(new KnightPiece(piece.getLocation(), piece.getTeamType()));
				}
				if(Main.getBoardController().getCurrentGameType().equals(GameType.SQL_MULTIPLAYER)) {
					new NextMoveParser(piece.getTeamType(), fromLocation, piece.getLocation(), "KNIGHT").sendToDatabase(Main.getSQLHandler());
				}
				frmPromotion.dispose();
			}
		});
		knightButton.setForeground(Color.BLACK);
		knightButton.setBackground(Color.ORANGE);
		knightButton.setBounds(147, 143, 110, 54);
		frmPromotion.getContentPane().add(knightButton);
	}
}
