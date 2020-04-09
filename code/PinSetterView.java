/*
 * PinSetterView/.java
 *
 * Version:
 *   $Id$
 *
 * Revision:
 *   $Log$
 */

/**
 *  constructs a prototype PinSetter GUI
 *
 */

import java.awt.*;
import javax.swing.*;
import java.util.Vector;


public class PinSetterView implements PinsetterObserver {


    private Vector pinVect = new Vector ( );
    private JPanel firstRoll;
    private JPanel secondRoll;

    /**
     * Constructs a Pin Setter GUI displaying which roll it is with
     * yellow boxes along the top (1 box for first roll, 2 boxes for second)
     * and displays the pins as numbers in this format:
     *
     *                7   8   9   10
     *                  4   5   6
     *                    2   3
     *                      1
     *
     */


	private JFrame frame;
    
    public PinSetterView ( int laneNum ) {

	frame = new JFrame ( "Lane " + laneNum + ":" );
	
	Container cpanel = frame.getContentPane ( );
	
	JPanel pins = new JPanel ( );
	
	pins.setLayout ( new GridLayout ( 4, 7 ) );
	
	//********************Top of GUI indicates first or second roll
	
	JPanel top = new JPanel ( );
	
	firstRoll = new JPanel ( );
	firstRoll.setBackground( Color.yellow );
	
	secondRoll = new JPanel ( );
	secondRoll.setBackground ( Color.black );
	
	top.add ( firstRoll, BorderLayout.WEST );
	
	top.add ( secondRoll, BorderLayout.EAST );
	
	//******************************************************************
	
	//**********************Grid of the pins**************************


	JPanel one = getPinPanel("1");
	JPanel two = getPinPanel("2");
	JPanel three = getPinPanel("3");
	JPanel four = getPinPanel("4");
	JPanel five = getPinPanel("5");
	JPanel six = getPinPanel("6");
	JPanel seven = getPinPanel("7");
	JPanel eight = getPinPanel("8");
	JPanel nine = getPinPanel("9");
	JPanel ten = getPinPanel("10");

		//This Vector will keep references to the pin labels to show
	//which ones have fallen.

	
	//******************************Fourth Row**************

	formFourthRow(pins, seven, eight, nine, ten);

		//*****************************Third Row***********

	formThirdRow(pins, four, five, six);

		//*****************************Second Row**************

	formSecondRow(pins, two, three);

		//******************************First Row*****************

	formFirstRow(pins, one);

		//*********************************************************
	
	top.setBackground ( Color.black );
	
	cpanel.add ( top, BorderLayout.NORTH );
	
	pins.setBackground ( Color.black );
	pins.setForeground ( Color.yellow );
	
	cpanel.add ( pins, BorderLayout.CENTER );
	
	frame.pack();
	
	
//	frame.show();
    }

	private void formFirstRow(JPanel pins, JPanel one) {
		pins.add ( new JPanel ( ) );
		pins.add ( new JPanel ( ) );
		pins.add ( new JPanel ( ) );
		pins.add ( one );
		pins.add ( new JPanel ( ) );
		pins.add ( new JPanel ( ) );
		pins.add ( new JPanel ( ) );
	}

	private void formSecondRow(JPanel pins, JPanel two, JPanel three) {
		pins.add ( new JPanel ( ) );
		pins.add ( new JPanel ( ) );
		pins.add ( new JPanel ( ) );
		pins.add ( two );
		pins.add ( new JPanel ( ) );
		pins.add ( three );
		pins.add ( new JPanel ( ) );
		pins.add ( new JPanel ( ) );
	}

	private void formThirdRow(JPanel pins, JPanel four, JPanel five, JPanel six) {
		pins.add(new JPanel());
		pins.add(four);
		pins.add(new JPanel());
		pins.add(five);
		pins.add(new JPanel());
		pins.add(six);
	}

	private void formFourthRow(JPanel pins, JPanel seven, JPanel eight, JPanel nine, JPanel ten) {
		pins.add(seven);
		pins.add(new JPanel());
		pins.add(eight);
		pins.add(new JPanel());
		pins.add(nine);
		pins.add(new JPanel());
		pins.add(ten);
	}

	private JPanel getPinPanel(String s) {
		JPanel pin = new JPanel();
		JLabel pinL = new JLabel(s);
		pin.add(pinL);
		pinVect.add(pinL);
		return pin;
	}


	/**
     * This method receives a pinsetter event.  The event is the current
     * state of the PinSetter and the method changes how the GUI looks
     * accordingly.  When pins are "knocked down" the corresponding label
     * is grayed out.  When it is the second roll, it is indicated by the
     * appearance of a second yellow box at the top.
     *
     * @param pe    The state of the pinsetter is sent in this event.
     */
    

    public void receivePinsetterEvent(PinsetterEvent pe){
	if ( !(pe.isFoulCommited()) ) {
	    	JLabel tempPin = new JLabel ( );
	    	for ( int c = 0; c < 10; c++ ) {
				boolean pin = pe.pinKnockedDown ( c );
				tempPin = (JLabel)pinVect.get ( c );
				if ( pin ) {
		    		tempPin.setForeground ( Color.lightGray );
				}
	    	}
    	}
		if ( pe.getThrowNumber() == 1 ) {
	   		 secondRoll.setBackground ( Color.yellow );
		}
	if ( pe.pinsDownOnThisThrow() == -1) {
		for ( int i = 0; i != 10; i++){
			((JLabel)pinVect.get(i)).setForeground(Color.black);
		}
		secondRoll.setBackground( Color.black);
	}
    }
    
    public void show() {
    	frame.show();
    }

    public void hide() {
    	frame.hide();
    }
    
    public static void main ( String args [ ] ) {
		PinSetterView pg = new PinSetterView ( 1 );
    }
    
}
