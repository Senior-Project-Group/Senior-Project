package com.project.Render;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.project.AiController.AIDifficulty;
import com.project.BoardController.GameType;
import com.project.Main.Main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


// Board object, has all the information about the frame 

// PLEASE DO NOT EDIT THIS FILE. IF YOU NEED TO, PLEASE BE CAREFUL!

public class Board {

	private JFrame frmChess;

	public Board() {
		initialize();
		frmChess.setVisible(true);
	}

	// Information:
	// 
	
	// Auto render piece sizes to bounds: x, y, 60, 60
	private void initialize() {		
		JLabel lblNewLabel = new JLabel("");
		
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("resources/standard-walnut-chess-board-21184254145_1024x1024.jpg")));
		lblNewLabel.setBounds(0, 0, 716, 723);
		
		frmChess = new JFrame();
		
		frmChess.setContentPane(lblNewLabel);
		
		frmChess.setResizable(false);
		frmChess.setTitle("Chess");
		frmChess.setBounds(100, 100, 706, 755);
		frmChess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChess.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		frmChess.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New Game");
		menuBar.add(mnNewMenu);
		
		JMenuItem playerVPlayerGame = new JMenuItem("Play Player vs Player Game");
		playerVPlayerGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.createNewGame(GameType.PLAYER_VS_PLAYER);
			}
		});
		mnNewMenu.add(playerVPlayerGame);
		
		JMenuItem playerVAIGame = new JMenuItem("Play Player vs AI Game");
		playerVAIGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.createNewGame(GameType.PLAYER_VS_AI);
			}
		});
		mnNewMenu.add(playerVAIGame);
		
		JMenuItem AI_V_AI_Game = new JMenuItem("Play AI vs AI Game");
		AI_V_AI_Game.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.createNewGame(GameType.PLAYER_VS_AI);
			}
		});
		mnNewMenu.add(AI_V_AI_Game);
		
		JMenu mnNewMenu_2 = new JMenu("Select AI Difficulty");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Easy");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.getNotificationHandler().sendNotificationMessage("Chess Game", "Changed difficulty AI difficulty to easy.");
				Main.getAIController().setDifficulty(AIDifficulty.EASY);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Normal");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getNotificationHandler().sendNotificationMessage("Chess Game", "Changed difficulty AI difficulty to normal.");
				Main.getAIController().setDifficulty(AIDifficulty.NORMAL);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Hard");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getNotificationHandler().sendNotificationMessage("Chess Game", "Changed difficulty AI difficulty to hard.");
				Main.getAIController().setDifficulty(AIDifficulty.HARD);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_4);
		
		JMenu mnNewMenu_3 = new JMenu("AI Process Speed");
		menuBar.add(mnNewMenu_3);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("5 seconds");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getNotificationHandler().sendNotificationMessage("Chess Game", "Changed AI speed to 5 seconds.");
				Main.getAIController().setNewSpeed(5);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_5);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("4 seconds");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getNotificationHandler().sendNotificationMessage("Chess Game", "Changed AI speed to 4 seconds.");
				Main.getAIController().setNewSpeed(4);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("3 seconds");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getNotificationHandler().sendNotificationMessage("Chess Game", "Changed AI speed to 3 seconds.");
				Main.getAIController().setNewSpeed(3);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_7);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("2 seconds");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getNotificationHandler().sendNotificationMessage("Chess Game", "Changed AI speed to 2 seconds.");
				Main.getAIController().setNewSpeed(2);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_8);
		
		JMenuItem mntmNewMenuItem_9 = new JMenuItem("1 second");
		mntmNewMenuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getNotificationHandler().sendNotificationMessage("Chess Game", "Changed AI speed to 1 second.");
				Main.getAIController().setNewSpeed(1);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_9);
		
		JMenuItem mntmNewMenuItem_10 = new JMenuItem("Instant");
		mntmNewMenuItem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getNotificationHandler().sendNotificationMessage("Chess Game", "Changed AI speed to instant.");
				Main.getAIController().setNewSpeed(0);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_10);
	}
	
	public JFrame getFrame() {
		return frmChess;
	}
}
