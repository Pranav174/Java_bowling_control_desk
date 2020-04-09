import java.util.*;
import java.io.*;

public class PausedGameFile {
	private static String PAUSED_DAT = "PAUSED.DAT";
	
	public static Vector getPausedGameList() {
		Vector<String> games = new Vector<String>();
		try {
			FileInputStream fis = new FileInputStream(PAUSED_DAT);
			ObjectInputStream ois = new ObjectInputStream(fis);	
			Vector<GameDetails> gd = (Vector<GameDetails>) ois.readObject();
			ois.close();
			fis.close();
			for(GameDetails i: gd) {
				games.add(i.toDisplay());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return games;
	}
	
	public static GameDetails getGameDetails(String abbv) {
		try {
			FileInputStream fis = new FileInputStream(PAUSED_DAT);
			ObjectInputStream ois = new ObjectInputStream(fis);	
			Vector<GameDetails> gd = (Vector<GameDetails>) ois.readObject();
			ois.close();
			fis.close();
			for(GameDetails i: gd) {
				if(i.toDisplay().equals(abbv)) {
					gd.remove(i);
					FileOutputStream fileOut = new FileOutputStream(PAUSED_DAT);
		            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
		            objectOut.writeObject(gd);
		            objectOut.close();
		            fileOut.close();
					return i;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
//	public static GameDetails viewGames() throws IOException, ClassNotFoundException {
////		try {
//        	FileInputStream fis = new FileInputStream(PAUSED_DAT);
//			ObjectInputStream ois = new ObjectInputStream(fis);	
//			Vector<GameDetails> gd = (Vector<GameDetails>) ois.readObject();
//			ois.close();
//			fis.close();
//			System.out.println(gd);
//			return gd.get(0);
////            objectOut.writeObject(paused);
////            objectOut.close();
////            System.out.println("The Object  was succesfully written to a file");
//// 
////        } catch (Exception ex) {
////            ex.printStackTrace();
////        }
//	}
//	
//	public static void pauseGame(GameDetails paused) {
//		try {
//            FileOutputStream fileOut = new FileOutputStream(PAUSED_DAT);
//            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//            objectOut.writeObject(paused);
//            objectOut.close();
//            System.out.println("The Object  was succesfully written to a file");
// 
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//	}
//	
//	public static void pauseGameList(Vector<GameDetails> paused) {
//		try {
//            FileOutputStream fileOut = new FileOutputStream(PAUSED_DAT);
//            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//            objectOut.writeObject(paused);
//            objectOut.close();
//            System.out.println("The Objects were succesfully written to a file");
// 
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//	}
	
	public static void putPauseGame(GameDetails paused) {
		try {
			FileInputStream fis = new FileInputStream(PAUSED_DAT);
			ObjectInputStream ois = new ObjectInputStream(fis);	
			Vector<GameDetails> gd = (Vector<GameDetails>) ois.readObject();
			ois.close();
			fis.close();
			gd.add(paused);
            FileOutputStream fileOut = new FileOutputStream(PAUSED_DAT);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(gd);
            objectOut.close();
            System.out.println("The Objects were succesfully written to a file");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
//	public static void main(String[] args) throws ClassNotFoundException, IOException {
//		GameDetails gd = new GameDetails(new Lane());
//		System.out.println(gd);
//		Vector<GameDetails> test = new Vector<GameDetails>();
//		test.add(gd);
//		pauseGame(gd);
//		pauseGameList(test);
//		viewGames();
//	}
}
