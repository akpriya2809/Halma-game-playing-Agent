package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.*;

public class Debug {
	final static int BOARD_SIZE = 16;

	public static void main(String[] args) {
		File file = new File("/Users/akankshapriya/AI_Assignments/Homework2/src/input2.txt");
		Scanner sc;
		try {
			sc = new Scanner(file);
			homework hw = new homework();
			
			if(sc.hasNextLine()) {
				hw.move = sc.next();
			}
			if(sc.hasNextLine()) {
				hw.color = sc.next();
			}
			if(sc.hasNextLine()) {
				hw.timeRemaining = Double.parseDouble(sc.next());
			}
			char[][] board = new char [BOARD_SIZE][ BOARD_SIZE];
			for(int i =0;i<BOARD_SIZE;i++) {
				String line = sc.next();
				for(int j =0; j<BOARD_SIZE; j++) {
					board[j][i]= line.charAt(j);
				}
			}
			State state = new State();
			Minimax mm = new Minimax();
			state.setAlpha(Integer.MIN_VALUE);
			state.setBeta(Integer.MAX_VALUE);
			state.setBoard(board);
			state.setfromLoc("");
		ArrayList<ArrayList<State>> moves = mm.generateAllPossibleStates(state, 1, hw);
		for(List<State> list :moves) {
			State st1 = list.get(list.size()-1);
			if(st1.fromLoc.equals("2,2")|| st1.fromLoc.equals("4,2")) {
				State st2 = mm.utilityFunction(homework.blackCamp, homework.whiteCamp, st1, hw);
				System.out.println(st2.fromLoc+"-"+st2.to+"="+st2.getValue());
				
			}
			
			
//			for(State s:list) {
//				System.out.println(s.fromLoc+","+s.to);
//			}
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
