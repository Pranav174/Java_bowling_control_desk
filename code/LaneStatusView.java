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

public class LaneStatusView implements ActionListener, LaneObserver, PinsetterObserver {

	private JPanel jp;

	private JLabel curBowler, pinsDown;
	private JButton viewLane;
	private JButton viewPinSetter, maintenance;

	private PinSetterView psv;
	private LaneView lv;
	private Lane lane;
	private int laneNum;

	private boolean laneShowing;
	private boolean psShowing;

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
		JLabel pdLabel = new JLabel( "Pins Down: " );
		pinsDown = new JLabel( "0" );

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());


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

		maintenance = new JButton("     ");
		maintenance.setBackground( Color.GREEN );
		JPanel maintenancePanel = new JPanel();
		maintenancePanel.setLayout(new FlowLayout());
		maintenance.addActionListener(this);
		maintenancePanel.add(maintenance);

		setLaneAndPinSetterStatus(false);

		buttonPanel.add(viewLanePanel);
		buttonPanel.add(viewPinSetterPanel);
		buttonPanel.add(maintenancePanel);

//		JLabel cLabel = addLabeltojp( "Now Bowling: " );
		jp.add( cLabel );
		jp.add( curBowler );
//		jp.add( fLabel );
//		jp.add( foul );
		jp.add( pdLabel );
		jp.add( pinsDown );

		jp.add(buttonPanel);

	}

	public JPanel showLane(int laneCount) {
		jp.setBorder(new TitledBorder("Lane" + ++laneCount ));
		return jp;
	}

	public void actionPerformed( ActionEvent e ) {
		if ( lane.isPartyAssigned() ) {
			if (e.getSource().equals(viewPinSetter)) {
				viewPinSetterClicked();
			}
			if (e.getSource().equals(viewLane)) {
				viewLaneClicked();
			}
			if (e.getSource().equals(maintenance)) {
				lane.unPauseGame();
				maintenance.setBackground( Color.GREEN );

			}

		}


	}

	public void  viewLaneClicked(){
		if (laneShowing) {
			lv.hide();
			laneShowing = false;
		} else {
			lv.show();
			laneShowing=true;
		}
	}

	public void viewPinSetterClicked(){
		if (psShowing) {
			psv.hide();
			psShowing=false;
		} else {
			psv.show();
			psShowing=true;
		}
	}
	public void receiveLaneEvent(LaneEvent le) {
		curBowler.setText( ( (Bowler)le.getBowler()).getNickName() );
		if ( le.isMechanicalProblem() ) {
			maintenance.setBackground( Color.RED );
		}
		if ( !lane.isPartyAssigned() ) {
			setLaneAndPinSetterStatus(false);
		} else {
			setLaneAndPinSetterStatus(true);
		}
	}
	public void setLaneAndPinSetterStatus(boolean value){
		viewLane.setEnabled( value );
		viewPinSetter.setEnabled( value );
	}

	public JLabel addLabeltojp( String text ){
		JLabel newLabel = new JLabel( "Now Bowling: " );
		jp.add(newLabel);
		return newLabel;
	}
	public void receivePinsetterEvent(PinsetterEvent pe) {
		pinsDown.setText( ( new Integer(pe.totalPinsDown()) ).toString() );
//		foul.setText( ( new Boolean(pe.isFoulCommited()) ).toString() );
		
	}

}
