package assignment2;

import java.awt.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class homework {
	
	public final static Point DEST_B = new Point(15, 15);
	public final static Point DEST_W = new Point(0, 0);
	
	public final static int totalPieces = 19;
	
	public static LinkedHashSet<String> blackCamp = new LinkedHashSet<>(Arrays.asList("0,4","1,4","2,3","3,2",
			"4,1","4,0","3,1", "2,2", "1,3", "0,3", "0,2","1,2","2,1", "3,0","2,0", "1,1","0,1", "1,0","0,0" ));
	
	public static LinkedHashSet<String> whiteCamp = new LinkedHashSet<>(Arrays.asList("15,11","14,11","13,12",
			"12,13","11,14","11,15","12,14", "13,13", "14,12", "15,12", "14,13",   "13,14", "12,15",
			"13,15",  "14,14","15,13", "15,14", "14,15", "15,15"));
	
	
	final static int BOARD_SIZE = 16;
	static long  start, end;
	public static int depth = 3;
	String color;
	String move;
	double timeRemaining;
	public homework() {
		this.move="";
		this.color="";
		this.timeRemaining=0.0;
	}

	
	
	

	public static void main(String[] args) {
		start = System.nanoTime();
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
		TreeMap<Integer, Long> depthMap= new TreeMap<>(Collections.reverseOrder());
		generateDepthMap(depthMap,sc);
		homework.depth = pickDepthValue(depthMap, hw, board);
		int numOfPieces =calculatePiecesInGoalCamp(hw, board);
		if(hw.timeRemaining<10 || numOfPieces>=17) {
			homework.depth = 1;
		}
		State state = new State();
			Minimax mm = new Minimax();
			state.setAlpha(Integer.MIN_VALUE);
			state.setBeta(Integer.MAX_VALUE);
			state.setBoard(board);
			state.setfromLoc("");
			State st  = mm.alphaBetaSearch(state, hw, 1);
			
			writeToOputputFile(st);
			long end = System.nanoTime();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}





	private static int calculatePiecesInGoalCamp(homework hw, char [][]board) {
		int count=0;
		if(hw.color.equals("WHITE")) {
			for(String loc:blackCamp) {
				String [] arr = loc.split(",");
				   int num1 = Integer.parseInt(arr[0]);//j
				   int num2 = Integer.parseInt(arr[1]);//i
				   if(board[num1][num2]=='W') {
					   count++;
				   }
				   
			}
		}else if(hw.color.equals("BLACK")) {
			for(String loc:whiteCamp) {
				String [] arr = loc.split(",");
				   int num1 = Integer.parseInt(arr[0]);//j
				   int num2 = Integer.parseInt(arr[1]);//i
				   if(board[num1][num2]=='B') {
					   count++;
				   }
				   
			}
		}
		return count;
	}





	private static int pickDepthValue(TreeMap<Integer, Long> map, homework hw, char[][] board) {
			int num = numberOfPieceInsideCamp(hw, board);
			if(num<19) {
				return 1;
			}
			for(Map.Entry<Integer, Long> entry: map.entrySet()) {
				if(hw.timeRemaining*1000000000 > (double)(entry.getValue())) {
					return (entry.getKey());
				}
			}
		return homework.depth;
		
	}





	private static int numberOfPieceInsideCamp(homework hw, char[][]board) {
		int count=0;
		for( String location:homework.blackCamp) {
			String[] arr = location.split(",");
			int j = Integer.parseInt(arr[0]);
			int i = Integer.parseInt(arr[1]);
			if(board[j][i]=='B') {
				count++;
			}
		}
		for( String location:homework.whiteCamp) {
			String[] arr = location.split(",");
			int j = Integer.parseInt(arr[0]);
			int i = Integer.parseInt(arr[1]);
			if(board[j][i]=='W') {
				count++;
			}
		}
		return count;
	}





	private static void generateDepthMap(TreeMap<Integer, Long> depthMap, Scanner sc) {
		File f = new File("/Users/akankshapriya/AI_Assignments/Homework2/src/calibrate.txt");
		 boolean exists = f.exists();
		 if(exists) {
		try {
			sc = new Scanner(f);
		
		while(sc.hasNextLine()) {
			String str = sc.next();
			
			String[] arr = str.split(":");
			depthMap.put(Integer.parseInt(arr[0]), Long.parseLong(arr[1]));
		}
	
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		 }
	}





	private static void writeToOputputFile(State st) {
		
		FileWriter writer;
		try {
			writer = new FileWriter("/Users/akankshapriya/AI_Assignments/Homework2/src/output.txt");
			List<List<String>> paths = st.path;
			int len = paths.size();
			if(len!= 0) {
				for(int i =0; i<len;i++) {
					writer.write("J ");
					int innerLen = paths.get(i).size();
					for(int j =0; j<innerLen; j++) {
						writer.write(paths.get(i).get(j));
						if(j != innerLen-1) {
							writer.write(" ");
						}
						
					}
					if(i != len -1) {
						writer.write("\n");
					}
				}
			}else {
				if(st.getMoveType().equals("SINGLE")) {
					writer.write("E ");
				}else {
					writer.write("J ");
				}
				
				writer.write(st.fromLoc+" ");
				writer.write(st.to);
			}
			
			
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	

	
	
	

	

}
