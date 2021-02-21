package com.project.Render;

import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PromotionSelection {
private JDialog frmPromotion;
	
	public PromotionSelection() {
		initialize();
		frmPromotion.setVisible(true);
	}

	
	private void initialize() {
		frmPromotion = new JDialog();
		frmPromotion.setTitle("Select a Piece");
		frmPromotion.setBounds(100, 100, 303, 269);
		frmPromotion.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frmPromotion.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Select a promotion piece for the pawn");
		lblNewLabel.setBounds(51, 11, 239, 14);
		frmPromotion.getContentPane().add(lblNewLabel);
		
		JButton queenButton = new JButton(new ImageIcon("resources/white_queen.png"));
		queenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NextMoveRenderer.promoType = "Queen";
				frmPromotion.dispose();
			}
		});
		queenButton.setBounds(27, 78, 110, 54);
		frmPromotion.getContentPane().add(queenButton);
		
		JButton rookButton = new JButton(new ImageIcon("resources/white_rook.png"));
		rookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NextMoveRenderer.promoType = "Rook";
				frmPromotion.dispose();
			}
		});
		rookButton.setForeground(Color.BLACK);
		rookButton.setBackground(Color.RED);
		rookButton.setBounds(147, 78, 110, 54);
		frmPromotion.getContentPane().add(rookButton);
		
		JButton bishopButton = new JButton(new ImageIcon("resources/white_bishop.png"));
		bishopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NextMoveRenderer.promoType = "Bishop";
				frmPromotion.dispose();
			}
		});
		bishopButton.setForeground(Color.BLACK);
		bishopButton.setBackground(Color.ORANGE);
		bishopButton.setBounds(27, 143, 110, 54);
		frmPromotion.getContentPane().add(bishopButton);
		
		JButton knightButton = new JButton(new ImageIcon("resources/white_knight.png"));
		knightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NextMoveRenderer.promoType = "Knight";
				frmPromotion.dispose();
			}
		});
		knightButton.setForeground(Color.BLACK);
		knightButton.setBackground(Color.ORANGE);
		knightButton.setBounds(147, 143, 110, 54);
		frmPromotion.getContentPane().add(knightButton);
	}
}
