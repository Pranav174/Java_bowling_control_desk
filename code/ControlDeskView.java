/* ControlDeskView.java
 *
 *  Version:
 *			$Id$
 * 
 *  Revisions:
 * 		$Log$
 * 
 */

/**
 * Class for representation of the control desk
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
//import javax.swing.event.*;

import java.util.*;

public class ControlDeskView implements ActionListener, ControlDeskObserver {

	private JFrame win;
	private JList partyList;

	/** The maximum  number of members in a party */
	private int maxMembers;

	private ControlDesk controlDesk;
	ButtonPanel addParty,finished,viewScores;
	
	/**
	 * Displays a GUI representation of the ControlDesk
	 *
	 */


	public ControlDeskView(ControlDesk controlDesk, int maxMembers) {

		this.controlDesk = controlDesk;
		this.maxMembers = maxMembers;
		int numLanes = controlDesk.getNumLanes();

		win = new JFrame("Control Desk");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new BorderLayout());

		// Controls Panel
		JPanel controlsPanel = initializePanel(3, 1, "Controls");

<<<<<<< HEAD
		addParty = new JButton("Add Party");
		JPanel addPartyPanel = new JPanel();
		addPartyPanel.setLayout(new FlowLayout());
		addParty.addActionListener(this);
		addPartyPanel.add(addParty);
		controlsPanel.add(addPartyPanel);


		finished = new JButton("Finished");
		JPanel finishedPanel = new JPanel();
		finishedPanel.setLayout(new FlowLayout());
		finished.addActionListener(this);
		finishedPanel.add(finished);
		controlsPanel.add(finishedPanel);
=======
		addParty = new ButtonPanel("Add Party", this);
		controlsPanel.add(addParty.getPanel());

//		continueGame = new ButtonPanel("Continue Game", this);
//		controlsPanel.add(continueGame.getPanel());

		viewScores = new ButtonPanel("View Scores", this);
		controlsPanel.add(viewScores.getPanel());
		
		finished = new ButtonPanel("Finished", this);
		controlsPanel.add(finished.getPanel());
>>>>>>> newFeatures

		// Lane Status Panel
		JPanel laneStatusPanel = initializePanel(numLanes, 1, "Lane Status");

		HashSet lanes=controlDesk.getLanes();
		Iterator it = lanes.iterator();
		int laneCount=0;
		while (it.hasNext()) {
			Lane curLane = (Lane) it.next();
			LaneStatusView laneStat = new LaneStatusView(curLane,(laneCount+1));
			curLane.subscribe(laneStat);
			((Pinsetter)curLane.getPinsetter()).subscribe(laneStat);
			JPanel lanePanel = laneStat.showLane(laneCount);
			laneStatusPanel.add(lanePanel);
		}

		// Party Queue Panel
		JPanel partyPanel = new JPanel();
		partyPanel.setLayout(new FlowLayout());
		partyPanel.setBorder(new TitledBorder("Party Queue"));

		Vector empty = new Vector();
		empty.add("(Empty)");

		partyList = new JList(empty);
		partyList.setFixedCellWidth(120);
		partyList.setVisibleRowCount(10);
		JScrollPane partyPane = new JScrollPane(partyList);
		partyPane.setVerticalScrollBarPolicy(
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		partyPanel.add(partyPane);
		//		partyPanel.add(partyList);

		// Clean up main panel
		colPanel.add(controlsPanel, "East");
		colPanel.add(laneStatusPanel, "Center");
		colPanel.add(partyPanel, "West");
		new DisplayWindow(win,colPanel,true);

	}

	public JPanel initializePanel(int rows,int cols, String title ){
		JPanel newPanel=new JPanel();
		newPanel.setLayout(new GridLayout(rows, cols));
		newPanel.setBorder(new TitledBorder(title));
		return  newPanel;
	}
	/**
	 * Handler for actionEvents
	 *
	 * @param e	the ActionEvent that triggered the handler
	 *
	 */

	public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
		if (e.getSource().equals(addParty)) {
			new AddPartyView(this, maxMembers);
		}
		if (e.getSource().equals(assign)) {
			controlDesk.assignLane();
=======
		if (e.getSource().equals(addParty.getButton())) {
			new AddPartyView(this, maxMembers);
>>>>>>> newFeatures
		}
		if (e.getSource().equals(finished.getButton())) {
			win.hide();
			System.exit(0);
		}
		if (e.getSource().equals(viewScores.getButton())) {
			new ScoreView();
		}
//		if (e.getSource().equals(continueGame.getButton())) {
//			System.out.println("Continue Game");
//			new PausedGameView();
//		}
	}

	/**
	 * Receive a new party from andPartyView.
	 *
	 * @param addPartyView	the AddPartyView that is providing a new party
	 *
	 */

	public void updateAddParty(AddPartyView addPartyView) {
		controlDesk.addPartyQueue(addPartyView.getParty());
	}

	/**
	 * Receive a broadcast from a ControlDesk
	 *
	 * @param ce	the ControlDeskEvent that triggered the handler
	 *
	 */

	public void receiveControlDeskEvent(ControlDeskEvent ce) {
		partyList.setListData(((Vector) ce.getPartyQueue()));
	}
}
