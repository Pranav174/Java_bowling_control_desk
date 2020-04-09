import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;
import java.text.*;


public class PausedGameView  implements ListSelectionListener {

	private JFrame win;
	private JButton start, abort;
	private JList games;
	private Vector<String> gameList;
	private Lane lane;
	public PausedGameView(Lane lane) {
		this.lane = lane;
		win = new JFrame("Continue Game");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(1, 1));

		// Bowler Database
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new FlowLayout());
		gamePanel.setBorder(new TitledBorder("Click the game to resume"));
		gameList = PausedGameFile.getPausedGameList();
				
		games = new JList(gameList);
		games.setVisibleRowCount(8);
		games.setFixedCellWidth(500);
		JScrollPane gamePane = new JScrollPane(games);
		gamePane.setVerticalScrollBarPolicy(
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		games.addListSelectionListener(this);
		gamePanel.add(gamePane);

		// Clean up main panel
		colPanel.add(gamePanel);

		win.getContentPane().add("Center", colPanel);

		win.pack();

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.setVisible(true);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		System.out.println("Check1");
		if(!lane.isPartyAssigned()) {
			System.out.println("Check2");
			String selectedGame = ((String) ((JList) arg0.getSource()).getSelectedValue());
			GameDetails gd = PausedGameFile.getGameDetails(selectedGame);
			System.out.println(selectedGame);
			System.out.println(PausedGameFile.getPausedGameList());
			System.out.println(gd);
			if(gd!=null) {
				System.out.println("Check3");
				lane.resumeGame(gd);
				win.setVisible(false);
			}
		}
		
	}

}
