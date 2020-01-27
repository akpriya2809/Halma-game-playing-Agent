package assignment2;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AgentGame {

	private final static String GAME_PLAY = "GAME";
	private final static int BOARD_SIZE = 16;
	private static final String INPUT_PATH = "/Users/akankshapriya/AI_Assignments/Homework2/src/input2.txt";
	private static final String OUTPUT_PATH = "/Users/akankshapriya/AI_Assignments/Homework2/src/output.txt";
	private static final double PLAY_TIME = 300.0;
	
	public static void main(String[] args) throws IOException {
//		List<String> lines = Files.readAllLines(Paths.get(INPUT_PATH));
//		Node[][] board = initBoard(lines, 3);
		
		File file = new File(INPUT_PATH);
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
		char[][] board1 = new char [BOARD_SIZE][ BOARD_SIZE];
		for(int i =0;i<BOARD_SIZE;i++) {
			String line = sc.next();
			for(int j =0; j<BOARD_SIZE; j++) {
				board1[j][i]= line.charAt(j);
			}
		}
		int count = 1;
		long start, end;
		long startTime, endTime;
		double seconds;
		double total1 = PLAY_TIME, total2 = PLAY_TIME;
		//while(!reachedGameEnd(board)) {
		start = System.nanoTime();
		while(!Minimax.isTerminalState(board1,hw)) {
			for(int i =0;i<BOARD_SIZE;i++) {
				for(int j =0; j<BOARD_SIZE; j++) {
					System.out.print(board1[j][i]);
				}
				System.out.println("");
			}
			startTime = System.nanoTime();
			if(count % 2 != 0) {
				homework.main(new String[] {});
			}
			else {
				homework.main(new String[] {});
			}
			endTime = System.nanoTime();
			seconds = (double) (endTime - startTime) / 1_000_000_000.0;
			if(count % 2 != 0) {
				total1 -= seconds;
				//System.out.println("total1"+total1);
			}
			else {
				total2 -= seconds;
				//System.out.println("total2"+total2);
			}
			System.out.println(count);
			count++;
			//lines = Files.readAllLines(Paths.get(OUTPUT_PATH));
			//modifyBoard(lines.get(0), board, count, total1, total2);
			modifyBoard( board1, count, total1, total2, sc);
		}
		end = System.nanoTime();
		double diff = (double) (end - start) / 1_000_000_000.0;
		System.out.println("diff"+diff);
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}

	private static void modifyBoard(char[][] board1, int count, double total1, double total2, Scanner sc) throws IOException {
		File file = new File(OUTPUT_PATH);
		List<String> lines = Files.readAllLines(Paths.get(OUTPUT_PATH));
		System.out.println(lines);
		
		String[] splitStr = lines.get(0).split("\\s+");
		String[] from = splitStr[1].split(","); 
		splitStr = lines.get(lines.size() - 1).split("\\s+");
		String[] to = splitStr[2].split(",");
		int[] s = new int[] {Integer.valueOf(from[0]), Integer.valueOf(from[1])};
		int[] t = new int[] {Integer.valueOf(to[0]), Integer.valueOf(to[1])};
		char start = board1[t[0]][t[1]];
		board1[t[0]][t[1]] =board1[s[0]][s[1]];
		board1[s[0]][s[1]]=start;
		
//		NodeType currType = start.nodeType;
//		start.nodeType = NodeType.BLANK;
//		target.nodeType = currType;
		BufferedWriter bw = new BufferedWriter(new FileWriter(INPUT_PATH, false));
		PrintWriter pw = new PrintWriter(bw);
		pw.println(GAME_PLAY);
		if(count % 2 != 0) {
			pw.println("WHITE");
			pw.println(total1);
		} else {
			pw.println("BLACK");
			pw.println(total2);
		}
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				pw.print(board1[j][i]);
			}
			pw.println();
		}
		pw.close();
	}
}
