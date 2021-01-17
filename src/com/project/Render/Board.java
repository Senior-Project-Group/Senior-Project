package com.project.Render;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.project.BoardController.Location;


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
		CenterPointManager point = new CenterPointManager();
		
		JLabel lblNewLabel = new JLabel("");
		
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("standard-walnut-chess-board-21184254145_1024x1024.jpg")));
		lblNewLabel.setBounds(0, 0, 716, 723);
		
		frmChess = new JFrame();
		
		// Remove this for check
		frmChess.setContentPane(lblNewLabel);
		
		frmChess.setResizable(false);
		frmChess.setTitle("Chess");
		frmChess.setBounds(100, 100, 706, 755);
		frmChess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChess.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\djcli\\ngplusworkspace\\Senior-Project\\pieces\\black_bishop.png"));
		
		Location loc = point.centerPointAlgorithm(1, 1);
		
		lblNewLabel_1.setBounds(loc.getX(), loc.getZ(), 60, 60);
		frmChess.getContentPane().add(lblNewLabel_1);
		
		
		// Add this for check
		//frmChess.getContentPane().add(lblNewLabel);
				
		JMenuBar menuBar = new JMenuBar();
		frmChess.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Game Manager");
		menuBar.add(mnNewMenu);
		
		JMenuItem playerVPlayerGame = new JMenuItem("Play Player vs Player Game");
		mnNewMenu.add(playerVPlayerGame);
		
		JMenuItem playerVAIGame = new JMenuItem("Play Player vs AI Game");
		mnNewMenu.add(playerVAIGame);
		
		JMenuItem AI_V_AI_Game = new JMenuItem("Play AI vs AI Game");
		mnNewMenu.add(AI_V_AI_Game);
	}
	
	public JFrame getFrame() {
		return frmChess;
	}
}
