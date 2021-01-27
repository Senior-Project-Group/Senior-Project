package com.project.Multiplayer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.project.Main.Main;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MultiplayerConnectionGUI {

	private JFrame frmMultiplayerSetup;
	private JTextField uuidField;
	private JTextField otherPlayerSessionID;
	
	private JLabel currentStatusLabel;

	public MultiplayerConnectionGUI() {
		initialize();
		frmMultiplayerSetup.setVisible(true);
	}
	
	private void initialize() {
		frmMultiplayerSetup = new JFrame();
		frmMultiplayerSetup.setTitle("Multiplayer Setup");
		frmMultiplayerSetup.setBounds(100, 100, 453, 286);
		frmMultiplayerSetup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmMultiplayerSetup.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Your Session ID:");
		lblNewLabel.setBounds(10, 47, 106, 14);
		frmMultiplayerSetup.getContentPane().add(lblNewLabel);
		
		uuidField = new JTextField();
		uuidField.setEditable(false);
		uuidField.setBounds(128, 44, 309, 20);
		frmMultiplayerSetup.getContentPane().add(uuidField);
		uuidField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Join Other Session:");
		lblNewLabel_1.setBounds(10, 85, 126, 14);
		frmMultiplayerSetup.getContentPane().add(lblNewLabel_1);
		
		otherPlayerSessionID = new JTextField();
		otherPlayerSessionID.setColumns(10);
		otherPlayerSessionID.setBounds(128, 82, 309, 20);
		frmMultiplayerSetup.getContentPane().add(otherPlayerSessionID);
		
		JLabel lblNewLabel_2 = new JLabel("Status:");
		lblNewLabel_2.setBounds(10, 11, 74, 14);
		frmMultiplayerSetup.getContentPane().add(lblNewLabel_2);
		
		JButton joinCurrentSessionButton = new JButton("Join Other User's Session");
		joinCurrentSessionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Join other person's session
				if(Main.getSQLHandler() != null) {
					if(Main.getSQLHandler().isActive()) {
						Main.getSQLHandler().destroy();
					}
				}
				Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Attempting to join game");
				Main.setSQLHandler(new SQLHandler(otherPlayerSessionID.getText()));
			}
		});
		joinCurrentSessionButton.setBounds(232, 170, 193, 23);
		frmMultiplayerSetup.getContentPane().add(joinCurrentSessionButton);
		
		JButton hostOwnSession = new JButton("Host Your Own Session");
		hostOwnSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Start hosting your own session
				if(Main.getSQLHandler() != null) {
					if(Main.getSQLHandler().isActive()) {
						Main.getSQLHandler().destroy();
					}
				}
					Main.getNotificationHandler().sendNotificationMessage("Multiplayer Handler", "Attempting to host game");
					SQLHandler handler = new SQLHandler();
					Main.setSQLHandler(handler);
					setCurrentStatusLabel(MutliplayerStatus.HOSTING);
					uuidField.setText(handler.getSessionUUID());
			}
		});
		hostOwnSession.setBounds(10, 170, 193, 23);
		frmMultiplayerSetup.getContentPane().add(hostOwnSession);
		
		setCurrentStatusLabel(MutliplayerStatus.IDLE);
		currentStatusLabel.setBounds(94, 11, 99, 14);
		frmMultiplayerSetup.getContentPane().add(currentStatusLabel);
	}
	
	public void setCurrentStatusLabel(MutliplayerStatus status) {
			//currentStatusLabel = new JLabel(status.toString());
	}
}
