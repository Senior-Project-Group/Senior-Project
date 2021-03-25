package com.project.Render;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class DiceRollerGUI {

	private JFrame frmDiceRoller;
	
	private JLabel rollingLabel;
	
	public DiceRollerGUI() {
		initialize();
		frmDiceRoller.setVisible(true);
	}
	
	public JLabel getRollingLabel() {
		return rollingLabel;
	}
	
	public JFrame getFrame() {
		return frmDiceRoller;
	}

	
	private void initialize() {
		frmDiceRoller = new JFrame();
		frmDiceRoller.setTitle("Dice Roller");
		frmDiceRoller.setBounds(100, 100, 599, 260);
		frmDiceRoller.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frmDiceRoller.getContentPane().setLayout(null);
		
		rollingLabel = new JLabel("Rolling");
		rollingLabel.setForeground(Color.GREEN);
		rollingLabel.setFont(new Font("Tahoma", Font.PLAIN, 71));
		rollingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rollingLabel.setBounds(10, 11, 545, 199);
		frmDiceRoller.getContentPane().add(rollingLabel);
	}
}