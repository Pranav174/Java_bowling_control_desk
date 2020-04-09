import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ScoreView implements ActionListener{
    private JFrame win;
    private Vector<Score> all_scores;
    private JTextField name,topScore,LowScore;
    JTextArea LastFive;
    public ScoreView() {
        win = new JFrame();
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);
		
		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(1, 3));
		
		
		//Score panel
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new FlowLayout());
		scorePanel.setBorder(new TitledBorder("All Scores"));
		// Set Scores
        String data[][] = {};
		try {
			all_scores = ScoreHistoryFile.getAllScores();
			data = generateTableData();
		} catch (IOException e) {
			e.printStackTrace();
		}
        String column[] = { "NAME", "DATETIME", "SCORE" };
        JTable scoresTable = new JTable(data, column);
        JScrollPane sp = new JScrollPane(scoresTable);
        scorePanel.add(sp);
        
        //Query Panels
        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new GridLayout(3, 1));
		queryPanel.setBorder(new TitledBorder("Details"));
		
		JLabel topPlayer = new JLabel( "Top Player: " +  getTopPlayer()) ;
		JLabel highestScore = new JLabel( "Highest Score: " +  getHighestScore() ) ;
		JLabel lowestScore = new JLabel( "Lowest Score: " +  getLowestScore() ) ;
		
		queryPanel.add(topPlayer);
		queryPanel.add(highestScore);
		queryPanel.add(lowestScore);
		
		//Query Panels
        JPanel playerqueryPanel = new JPanel();
        playerqueryPanel.setLayout(new GridLayout(4, 2));
        playerqueryPanel.setBorder(new TitledBorder("Queries"));
		
		name = new JTextField();
		name.setBounds(50,150,150,20);  
		topScore = new JTextField();
		topScore.setEditable(false);
		LowScore = new JTextField();
		LowScore.setEditable(false);
		LastFive = new JTextArea();
		LastFive.setEditable(false);
		
		JButton find = new JButton("find");
		find.addActionListener(this);
		playerqueryPanel.add(name);
		playerqueryPanel.add(find);
		playerqueryPanel.add(new JLabel( "Highest Score: "));
		playerqueryPanel.add(topScore);
		playerqueryPanel.add(new JLabel( "Lowest Score: "));
		playerqueryPanel.add(LowScore);
		playerqueryPanel.add(new JLabel( "Recent Scores: "));
		playerqueryPanel.add(LastFive);
		
        
        //Finalize window
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();        
		colPanel.add(scorePanel, "East");
		colPanel.add(queryPanel, "Center");
		colPanel.add(playerqueryPanel, "West");

		win.getContentPane().add("Center", colPanel);
		win.pack();
        win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.setVisible(true);
    }
    
    private String[][] generateTableData(){
    	String data[][] = new String[all_scores.size()][3];
    	
    	for(int i=0; i<all_scores.size(); i++) {
    		data[i] = all_scores.get(i).toString().split("\t");
    	}
    	
    	return data;
    }
    
    private String getTopPlayer() {
    	try {
			Vector bowlers = BowlerFile.getBowlers();
			String bestBowler = "(no One)";
			Double bestAvg = (double) -1;
			Iterator i = bowlers.iterator();
			while (i.hasNext()) {
				   String bowler = (String) i.next();
				   Vector<Score> BowlerScores = ScoreHistoryFile.getScores(bowler);
				   double ScoreAvg = 0;
				   if (BowlerScores.size()!=0) {
					   int scoreCount=0;
					   Iterator<Score> j = BowlerScores.iterator();
					   while(j.hasNext()) {
						   ScoreAvg += j.next().getScoreInt();
						   scoreCount++;
					   }
					   ScoreAvg = ScoreAvg/scoreCount;
				   }
				   if (ScoreAvg>bestAvg) {
					   bestAvg = ScoreAvg;
					   bestBowler = bowler;
				   }
				   
				}
			return bestBowler + " with Avg Score " + bestAvg;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
    }
    private String getHighestScore() {
    	if (all_scores.size()==0) {
    		return "(no score data available)";
    	}
    	Score bestScore = all_scores.get(0);
    	for(int i=1; i<all_scores.size(); i++) {
    		if (bestScore.getScoreInt() < all_scores.get(i).getScoreInt()) {
    			bestScore = all_scores.get(i);
    		}
    	}
    	return bestScore.toStringPretty();
    }
    private String getLowestScore() {
    	if (all_scores.size()==0) {
    		return "(no score data available)";
    	}
    	Score worstScore = all_scores.get(0);
    	for(int i=1; i<all_scores.size(); i++) {
    		if (worstScore.getScoreInt() >= all_scores.get(i).getScoreInt()) {
    			worstScore = all_scores.get(i);
    		}
    	}
    	return worstScore.toStringPretty();
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String nick = name.getText();
		topScore.setText(playerHighest(nick));
		LowScore.setText(playerLowest(nick));
		LastFive.setText(playerRecent(nick));
	}
	
	private String playerHighest(String nick) {
		try {
			Vector<Score> Scores = ScoreHistoryFile.getScores(nick);
			if (Scores.size()==0) {
				return "No scores";
			}
			else {
				Score bestScore = Scores.get(0);
		    	for(int i=1; i<Scores.size(); i++) {
		    		if (bestScore.getScoreInt() < Scores.get(i).getScoreInt()) {
		    			bestScore = Scores.get(i);
		    		}
		    	}
		    	return bestScore.toStringPretty();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
	private String playerLowest(String nick) {
		try {
			Vector<Score> Scores = ScoreHistoryFile.getScores(nick);
			if (Scores.size()==0) {
				return "No scores";
			}
			else {
				Score worstScore = Scores.get(0);
		    	for(int i=1; i<Scores.size(); i++) {
		    		if (worstScore.getScoreInt() >= Scores.get(i).getScoreInt()) {
		    			worstScore = Scores.get(i);
		    		}
		    	}
		    	return worstScore.toStringPretty();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
	private String playerRecent(String nick) {
		try {
			Vector<Score> Scores = ScoreHistoryFile.getScores(nick);
			if (Scores.size()==0) {
				return "No scores";
			}
			else {
				String ret = "";
		    	for(int i=Scores.size()-1; i>=(Scores.size()-5) && i>=0; i--) {
		    		ret += Scores.get(i).toStringPretty() + '\n';
		    	}
		    	return ret;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		
	}
}
