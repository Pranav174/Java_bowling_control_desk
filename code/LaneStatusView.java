/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class LaneStatusView implements ActionListener, LaneObserver, PinsetterObserver {

	private JPanel jp;

	private JLabel curBowler, foul, pinsDown;
	private JButton viewLane;
	private JButton viewPinSetter, maintenance, pause, quit, contin;

	private PinSetterView psv;
	private LaneView lv;
	private Lane lane;
	int laneNum;

	boolean laneShowing;
	boolean psShowing;

	public LaneStatusView(Lane lane, int laneNum ) {

		this.lane = lane;
		this.laneNum = laneNum;

		laneShowing=false;
		psShowing=false;

		psv = new PinSetterView( laneNum );
		Pinsetter ps = lane.getPinsetter();
		ps.subscribe(psv);

		lv = new LaneView( lane, laneNum );
		lane.subscribe(lv);


		jp = new JPanel();
		jp.setLayout(new FlowLayout());
		JLabel cLabel = new JLabel( "Now Bowling: " );
		curBowler = new JLabel( "(no one)" );
		JLabel fLabel = new JLabel( "Foul: " );
		foul = new JLabel( " " );
		JLabel pdLabel = new JLabel( "Pins Down: " );
		pinsDown = new JLabel( "0" );

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		Insets buttonMargin = new Insets(4, 4, 4, 4);

		viewLane = new JButton("View Lane");
		JPanel viewLanePanel = new JPanel();
		viewLanePanel.setLayout(new FlowLayout());
		viewLane.addActionListener(this);
		viewLanePanel.add(viewLane);

		viewPinSetter = new JButton("Pinsetter");
		JPanel viewPinSetterPanel = new JPanel();
		viewPinSetterPanel.setLayout(new FlowLayout());
		viewPinSetter.addActionListener(this);
		viewPinSetterPanel.add(viewPinSetter);
		
		pause = new JButton("Pause");
		JPanel pausePanel = new JPanel();
		pausePanel.setLayout(new FlowLayout());
		pause.addActionListener(this);
		pausePanel.add(pause);
		
		quit = new JButton("Save & Quit");
		JPanel quitPanel = new JPanel();
		quitPanel.setLayout(new FlowLayout());
		quit.addActionListener(this);
		quitPanel.add(quit);
		
		contin = new JButton("Continue");
		JPanel continuePanel = new JPanel();
		continuePanel.setLayout(new FlowLayout());
		contin.addActionListener(this);
		continuePanel.add(contin);

		maintenance = new JButton("     ");
		maintenance.setBackground( Color.GREEN );
		JPanel maintenancePanel = new JPanel();
		maintenancePanel.setLayout(new FlowLayout());
		maintenance.addActionListener(this);
		maintenancePanel.add(maintenance);

		viewLane.setEnabled( false );
		viewPinSetter.setEnabled( false );
		pause.setEnabled( false );
		quit.setEnabled(false);

		buttonPanel.add(continuePanel);
		buttonPanel.add(viewLanePanel);
		buttonPanel.add(viewPinSetterPanel);
		buttonPanel.add(pausePanel);
		buttonPanel.add(quitPanel);
		buttonPanel.add(maintenancePanel);

		jp.add( cLabel );
		jp.add( curBowler );
//		jp.add( fLabel );
//		jp.add( foul );
		jp.add( pdLabel );
		jp.add( pinsDown );
		
		jp.add(buttonPanel);

	}

	public JPanel showLane() {
		return jp;
	}

	public void actionPerformed( ActionEvent e ) {
		if ( lane.isPartyAssigned() ) { 
			if (e.getSource().equals(viewPinSetter)) {
				if ( psShowing == false ) {
					psv.show();
					psShowing=true;
				} else if ( psShowing == true ) {
					psv.hide();
					psShowing=false;
				}
			}
		}
		if (e.getSource().equals(viewLane)) {
			if ( lane.isPartyAssigned() ) { 
				if ( laneShowing == false ) {
					lv.show();
					laneShowing=true;
				} else if ( laneShowing == true ) {
					lv.hide();
					laneShowing=false;
				}
			}
		}
		if (e.getSource().equals(maintenance)) {
			if ( lane.isPartyAssigned() ) {
				lane.unPauseGame();
				maintenance.setBackground( Color.GREEN );
				quit.setEnabled(false);
			}
		}
		if (e.getSource().equals(pause)) {
//			System.out.println("Pause now");
			if ( lane.isPartyAssigned() ) {
				lane.pauseGame();
				maintenance.setBackground( Color.RED );
				quit.setEnabled(true);
			}
		}
		if (e.getSource().equals(quit)) {
//			System.out.println("Quit now");
			if ( lane.isPartyAssigned() ) {
				lane.saveQuit();
			}
		}
		if (e.getSource().equals(contin)) {
			new PausedGameView(lane);
		}
	}

	public void receiveLaneEvent(LaneEvent le) {
		curBowler.setText( ( (Bowler)le.getBowler()).getNickName() );
		if ( le.isMechanicalProblem() ) {
			maintenance.setBackground( Color.RED );
			quit.setEnabled(true);
		}
		else {
			maintenance.setBackground( Color.GREEN );
			quit.setEnabled(false);
		}
		if ( lane.isPartyAssigned() == false ) {
			viewLane.setEnabled( false );
			viewPinSetter.setEnabled( false );
			pause.setEnabled( false );
			contin.setEnabled(true);
		} else {
			viewLane.setEnabled( true );
			viewPinSetter.setEnabled( true );
			pause.setEnabled( true );
			contin.setEnabled(false);
		}
	}

	public void receivePinsetterEvent(PinsetterEvent pe) {
		pinsDown.setText( ( new Integer(pe.totalPinsDown()) ).toString() );
//		foul.setText( ( new Boolean(pe.isFoulCommited()) ).toString() );
		
	}

}
