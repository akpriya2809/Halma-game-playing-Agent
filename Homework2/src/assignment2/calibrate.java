package assignment2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map;
import java.util.Scanner;

public class calibrate {
	


	public static void main(String[] args) {
		Minimax mm = new Minimax();
		char[][]board = {
				{'.','W','.','.','.','B','.','.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','B','.','B','.','.','.','.','.','.'},
				{'.','.','W','.','.','W','.','.','B','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
				{'W','.','.','.','B','B','B','.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','B','.','B','.','.','.','.','.'},
				{'.','.','.','.','B','B','.','B','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','.','W','.','.','W','.','.','.','.','.','.'},
				{'.','.','W','.','.','.','.','.','.','.','W','W','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','W','.','.','.','.','B','.','.'},
				{'.','.','.','.','.','W','W','.','.','W','W','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','W','.','.','.','.','.','.'},
				{'.','.','.','.','.','W','.','B','W','B','.','.','B','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','.','.','.','.','B','B','.'},
				{'.','.','W','.','.','.','.','.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','W','.','.','.','.','B','.'}
				};
		File file = new File("/Users/akankshapriya/AI_Assignments/Homework2/src/input2.txt");
		Scanner sc;
		//long start = System.currentTimeMillis();
		
		try {
				homework hw = new homework();

				hw.color = "BLACK";
				State state = new State();
				state.setAlpha(Integer.MIN_VALUE);
				state.setBeta(Integer.MAX_VALUE);
				state.setBoard(board);
				state.setfromLoc("");
				LinkedHashMap<Long, Integer> map = new LinkedHashMap<>();
				for(int depth = 1; depth<4; depth++) {
					long start = System.nanoTime();
					State st  = mm.alphaBetaSearch(state, hw, depth);
					long end = System.nanoTime();
					long duration = (end-start);
					
					map.put(duration, depth);
				}
				FileWriter writer;
				try {
					writer = new FileWriter("/Users/akankshapriya/AI_Assignments/Homework2/src/calibrate.txt");
					List<Map.Entry<Long,Integer>> entryList =
						    new ArrayList<Map.Entry<Long, Integer>>(map.entrySet());
					int size = entryList.size();
					for(int t=0; t<size;t++) {
						Map.Entry<Long,Integer> entry = entryList.get(t);
						writer.write(Integer.toString(entry.getValue()));
						writer.write(":");
						writer.write(Long.toString(entry.getKey()));
						if(t!=size-1) {
							writer.write("\n");
						}
					}
					
					
					writer.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
		            
				
				
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
