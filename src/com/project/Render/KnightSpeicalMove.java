package com.project.Render;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.project.BoardController.Location;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KnightPiece;
import com.project.Main.Main;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class KnightSpeicalMove {

	private JFrame frmKnightSpecialMove;
	
	private IChessPiece knight;
	
	public KnightSpeicalMove(IChessPiece knight) {
		this.knight = knight;
		
		initialize();
		frmKnightSpecialMove.setVisible(true);
	}

	private void initialize() {
		frmKnightSpecialMove = new JFrame();
		frmKnightSpecialMove.setTitle("Knight Special Move");
		frmKnightSpecialMove.setBounds(100, 100, 380, 227);
		frmKnightSpecialMove.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmKnightSpecialMove.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Do Knight Special Move?");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(87, 11, 249, 34);
		frmKnightSpecialMove.getContentPane().add(lblNewLabel);
		
		JButton yesButton = new JButton("Yes");
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Attempting special move");
				
				KnightPiece n = (KnightPiece)knight;
				ArrayList<Location> attacks = n.getAttackLocations();
				
				for(Location a : attacks) {
					
					
				}
				
				Main.getBoardController().resetKnightSpecialMoveGUI();
			}
		});
		yesButton.setBounds(25, 122, 138, 45);
		frmKnightSpecialMove.getContentPane().add(yesButton);
		
		JButton noButton = new JButton("No");
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmKnightSpecialMove.dispose();
				Main.getBoardController().resetKnightSpecialMoveGUI();
			}
		});
		noButton.setBounds(204, 122, 138, 45);
		frmKnightSpecialMove.getContentPane().add(noButton);
		
		
		Main.getBoardController().setKnightSpecialMoveGUI(this);
	}
}
