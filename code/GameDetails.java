
import java.util.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;  

public class GameDetails implements Serializable {
	private String date;
	private String members;
	private HashMap Scores;
	private boolean[] pins;
	private boolean foul;
	private int ball;
	private int bowlIndex;
	private int frameNumber;
	private boolean tenthFrameStrike;
	private int[] curScores;
	private int[][] cumulScores;
	private boolean canThrowAgain;
	private int[][] finalScores;
	private int gameNumber;
	private Bowler currentThrower;
	

	public GameDetails(Lane lane) {
		Scores = lane.getScores();
		ball = lane.getBall();
		bowlIndex = lane.getBowlIndex();
		frameNumber = lane.getFrameNumber();
		tenthFrameStrike = lane.isTenthFrameStrike();
		curScores = lane.getCurScores();
		cumulScores = lane.getCumulScores();
		canThrowAgain = lane.isCanThrowAgain();
		finalScores = lane.getFinalScores();
		gameNumber = lane.getGameNumber();
		pins = lane.getPinsetter().getPins();
		foul = lane.getPinsetter().isFoul();
		currentThrower = lane.getCurrentThrower();
		date = new SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss").format(new Date());
		members = getmembers();
	}

	@Override
	public String toString() {
		return "GameDetails [date=" + date + ", Scores=" + Scores + ", pins=" + Arrays.toString(pins) + ", foul=" + foul
				+ ", ball=" + ball + ", bowlIndex=" + bowlIndex + ", frameNumber=" + frameNumber + ", tenthFrameStrike="
				+ tenthFrameStrike + ", curScores=" + Arrays.toString(curScores) + ", cumulScores="
				+ Arrays.toString(cumulScores) + ", canThrowAgain=" + canThrowAgain + ", finalScores="
				+ Arrays.toString(finalScores) + ", gameNumber=" + gameNumber + "]";
	}
	
	public String toDisplay() {
		return "Date: " + date + ", Members: " + members;
	}
	
	private String getmembers() {
		String ret = "";
		for(Object i: Scores.keySet()) {
			ret += ((Bowler) i).getNick() + " ";
		}
		return ret;
	}

	public String getDate() {
		return date;
	}

	public HashMap getScores() {
		return Scores;
	}

	public boolean[] getPins() {
		return pins;
	}

	public boolean isFoul() {
		return foul;
	}

	public int getBall() {
		return ball;
	}

	public int getBowlIndex() {
		return bowlIndex;
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public boolean isTenthFrameStrike() {
		return tenthFrameStrike;
	}

	public int[] getCurScores() {
		return curScores;
	}

	public int[][] getCumulScores() {
		return cumulScores;
	}

	public boolean isCanThrowAgain() {
		return canThrowAgain;
	}

	public int[][] getFinalScores() {
		return finalScores;
	}

	public int getGameNumber() {
		return gameNumber;
	}
	
	public Bowler getCurrentThrower() {
		return currentThrower;
	}
}
