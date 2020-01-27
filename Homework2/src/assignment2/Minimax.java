package assignment2;

import java.util.*;


public class Minimax {

	public static int[]dy = {-1,-1,-1,0,0,1,1,1};
	public static int[]dx = {-1,0,1,-1,1,-1,0,1};
	
	public static int[]bdy = {0,1,1};
	public static int[]bdx = {1,1,0};
	//public static int[]bdy = {-1,0,1,1,1};
	//public static int[]bdx = {1,1,1,0,-1};
	public static int[]wdy = {0,-1,-1};
	public static int[]wdx = {-1,0,-1};
//	public static int[]wdy = {-1,0,-1,-1,1};
//	public static int[]wdx = {1,-1,0,-1,-1};
	public static int goalLocx;
	public static int goalLocy;
	
	public State alphaBetaSearch(State state, homework hw, int depth) {
		
		 return maxValue(state, hw, depth);
		
	}
	
	
	private State maxValue(State currState, homework hw, int depth) {
//		long currTime = System.nanoTime();
//		double timeused = (currTime-homework.start)/1000000000;

		//check if it is the terminal state or if the time is remaining return utiltyFunction(board)
		 if(isTerminalState(currState.board, hw)|| depth == 0 ) {	
			 State st = utilityFunction(homework.blackCamp, homework.whiteCamp, currState, hw);
			return st;	
		}
		ArrayList<ArrayList<State>> moves = generateAllPossibleStates(currState, 1, hw);
		
		//generate all possible states 
		State bestState = currState;
		int max = Integer.MIN_VALUE;
		for(ArrayList<State> stateList: moves) {
			String src = stateList.get(0).fromLoc;
			State s = stateList.get(stateList.size()-1);
			//for(State s: stateList) {
					State newstate = new State();
					newstate.setAlpha(currState.getAlpha());
					newstate.setBeta(currState.getBeta());
					newstate.setBoard(s.board);
					newstate.setfromLoc(src);
					newstate.setTo(s.to);
					//newstate.setValue(s.getValue());
					newstate.setMoveType(s.getMoveType());
					newstate.setPath(s.getPath());
					State st = minValue(newstate,hw, depth-1);
					if(st.getValue()>max) {
						bestState = newstate;
						max = st.getValue();
						bestState.setAlpha(max);
						bestState.setValue(max);
						
					}
					if(max >=currState.getBeta()) {
						return bestState;
					}
					currState.setAlpha(max);
			//}
		}
		
		return bestState;
	}
	
	

	private  State minValue(State currState, homework hw, int depth ) {
		long currTime = System.nanoTime();
		double timeused = (currTime-homework.start)/1000000000;
		
		if(isTerminalState(currState.board, hw)|| depth == 0 ) {
			State st = utilityFunction(homework.blackCamp, homework.whiteCamp, currState, hw);
			return st;	
		}
		ArrayList<ArrayList<State>> moves = generateAllPossibleStates(currState, 0, hw);
		//generate all possible states 
		int min = Integer.MAX_VALUE;
		State bestState = currState;
		for(ArrayList<State> stateList: moves) {
			String src = stateList.get(0).fromLoc;
			State s = stateList.get(stateList.size()-1);
			//for(State s: stateList) {
					State newstate = new State();
					newstate.setAlpha(currState.getAlpha());
					newstate.setBeta(currState.getBeta());
					newstate.setBoard(s.board);
					newstate.setfromLoc(src);
					newstate.setTo(s.to);
					newstate.setMoveType(s.getMoveType());
					newstate.setPath(s.getPath());
					State st = maxValue(newstate,hw, depth-1);
					if(st.getValue()<min) {
						bestState = newstate;
						min = st.getValue();
						bestState.setBeta(min);
						bestState.setValue(min);
						
					}
					if(min <= currState.getAlpha()) {
						return bestState;
					}
					currState.setBeta(min);
			//}
			
		}
		
		return bestState;
	}
	public ArrayList<ArrayList<State>> generateAllPossibleStates(State state, int max, homework hw) {
		ArrayList<ArrayList<State>> listofStates= new ArrayList<>();
		char player;
		if(max==1) {
			if(hw.color.equals("WHITE")){
				player = 'W';
			}else {
				player ='B';
			}
			
		}else {
			if(hw.color.equals("WHITE")){
				player = 'B';
			}else {
				player ='W';
			}
		}
		
		
		char [][] board = state.board;
		
		int n = board.length;
		//iterate over camp to make a move

		switch(player) {
			case'W':
				for (String loc :homework.whiteCamp) {
					String[] arr = loc.split(",");
					int j = Integer.parseInt(arr[0]);
					int i = Integer.parseInt(arr[1]);
					if(board[j][i]=='W') {
						HashSet<String> uniqueState = new HashSet<>();
						
						for(int k = 0 ; k<wdy.length; k++) {
							int y =j+wdy[k];
							int x = i+wdx[k];
							
							if(isValid(y,x,n)) {
								
								if(board[y][x]=='.') {
									char[][] newBoard = new char[n][n];
									
									for(int a = 0; a<n;a++) {
										for(int b= 0; b<n;b++) {
											newBoard[b][a]=board[b][a];
										}
									}
									newBoard[y][x]=player;
									newBoard[j][i]= '.';
									State s = new State();
									s.board = newBoard;
									s.fromLoc = j+","+i;
									s.to = y+","+x;
									s.setMoveType("SINGLE");
									
									if(!uniqueState.contains(s.to)) {
										ArrayList<State> list = new ArrayList<>(Arrays.asList(s));
										State freshState = list.get(list.size()-1);
											uniqueState.add(freshState.to);
											listofStates.add(list);
									}
									
								}else {
									if(isValid(y+wdy[k],x+wdx[k],n) && board[y+wdy[k]][x+wdx[k]]=='.') {
										HashSet<String> set = new HashSet<>();
										char[][] newBoard = new char[n][n];
										
										for(int a = 0; a<n;a++) {
											for(int b= 0; b<n;b++) {
												newBoard[b][a]=board[b][a];
											}
										}
										newBoard[y+wdy[k]][x+wdx[k]]=player;
										newBoard[j][i]= '.';
									
										State s = new State();
										s.board = newBoard;
										s.fromLoc = j+","+i;
										s.to = (y+wdy[k])+","+(x+wdx[k]);
										s.path.add(new ArrayList<>(Arrays.asList(s.fromLoc, s.to)));
										s.setMoveType("JUMP");
										set.add(s.to);
										set.add(s.fromLoc);
										ArrayList<State> list = new ArrayList<>(Arrays.asList(s));
										
										findSkipStates(state,newBoard,y+wdy[k],x+wdx[k],j,i,list,player,set, uniqueState, listofStates);
										
										State freshState = list.get(list.size()-1);
										
										if(!uniqueState.contains(freshState.to)) {
											uniqueState.add(freshState.to);
											listofStates.add(list);
										}else {
											list.remove(list.size()-1);
										}
									}
								}
							}
						}
					}
				}
				//iterate over places outside the camp and generate states if no piece inside the camp
					if(listofStates.size()==0) { 
						for(int q = 0;q<n;q++) {
							for(int p = 0; p<n;p++) {
								if(!homework.whiteCamp.contains(q+","+p) && board[q][p]=='W') {
									HashSet<String> uniqueState = new HashSet<>();
									
									for(int k = 0 ; k<dy.length; k++) {
										int y =q+dy[k];
										int x = p+dx[k];
										if(isValid(y,x,n)) {
											if(board[y][x]=='.' ) {
												if(!homework.whiteCamp.contains(y+","+x)) {
														char[][] newBoard = new char[n][n];
														for(int a = 0; a<n;a++) {
															for(int b= 0; b<n;b++) {
																newBoard[b][a]=board[b][a];
															}
														}
														newBoard[y][x]=player;
														newBoard[q][p]= '.';
														State s = new State();
														s.board = newBoard;
														s.fromLoc = q+","+p;
														s.to = y+","+x;
														s.setMoveType("SINGLE");
														if(!uniqueState.contains(s.to)) {
															ArrayList<State> list = new ArrayList<>(Arrays.asList(s));
															State freshState = list.get(list.size()-1);
															uniqueState.add(freshState.to);
															listofStates.add(list);
														
															
														}
														// single move so need to check end state here already taken care
														
														
												}
											}else {
												if(isValid(y+dy[k],x+dx[k],n) && board[y+dy[k]][x+dx[k]]=='.') {
													HashSet<String> set = new HashSet<>();
													char[][] newBoard = new char[n][n];
													for(int a = 0; a<n;a++) {
														for(int b= 0; b<n;b++) {
															newBoard[b][a]=board[b][a];
														}
													}
													newBoard[y+dy[k]][x+dx[k]]=player;
													newBoard[q][p]= '.';
												
													State s = new State();
													s.board = newBoard;
													s.fromLoc = q+","+p;
													s.to = (y+dy[k])+","+(x+dx[k]);
													set.add(s.to);
													set.add(s.fromLoc);
													s.setMoveType("JUMP");
													ArrayList<State> list = new ArrayList<>(Arrays.asList(s));
													
													findSkipStates(state,newBoard,y+dy[k],x+dx[k],q,p,list,player,set, uniqueState, listofStates);
													if(list.size()>0) {
														State freshState = list.get(list.size()-1);
														
														if(!uniqueState.contains(freshState.to )) {
															uniqueState.add(freshState.to);
															
															listofStates.add(list);
														}else {
															list.remove(list.size()-1);
														}
														
													}
													
													
												}
											}
										}
									}
								}
							}
						}
					}

					
					
					
				break;
			case'B':
				for (String loc :homework.blackCamp) {
					String[] arr = loc.split(",");
					int j = Integer.parseInt(arr[0]);
					int i = Integer.parseInt(arr[1]);
					if(board[j][i]=='B') {
						HashSet<String> uniqueState = new HashSet<>();
							
							for(int k = 0 ; k<bdy.length; k++) {
								int y =j+bdy[k];
								int x = i+bdx[k];
								if(isValid(y,x,n)) {
									if(board[y][x]=='.') {
										char[][] newBoard = new char[n][n];
										for(int a = 0; a<n;a++) {
											for(int b= 0; b<n;b++) {
												newBoard[b][a]=board[b][a];
											}
										}
										newBoard[y][x]=player;
										newBoard[j][i]= '.';
										State s = new State();
										s.board = newBoard;
										s.fromLoc = j+","+i;
										s.to = y+","+x;
										s.setMoveType("SINGLE");
										
										if(!uniqueState.contains(s.to)) {
											ArrayList<State> list = new ArrayList<>(Arrays.asList(s));
											State freshState = list.get(list.size()-1);
											uniqueState.add(freshState.to);
											listofStates.add(list);
											
										}
										
									}else {
										if(isValid(y+bdy[k],x+bdx[k],n) && board[y+bdy[k]][x+bdx[k]]=='.') {
											HashSet<String> set = new HashSet<>();
											char[][] newBoard = new char[n][n];
											
											for(int a = 0; a<n;a++) {
												for(int b= 0; b<n;b++) {
													newBoard[b][a]=board[b][a];
												}
											}
											newBoard[y+bdy[k]][x+bdx[k]]=player;
											newBoard[j][i]= '.';
										
											State s = new State();
											s.board = newBoard;
											s.fromLoc = j+","+i;
											s.to = (y+bdy[k])+","+(x+bdx[k]);
											s.setMoveType("JUMP");
											set.add(s.to);
											set.add(s.fromLoc);
											
											
											ArrayList<State> list = new ArrayList<>(Arrays.asList(s));
											findSkipStates(state, newBoard,y+bdy[k],x+bdx[k],j,i,list,player,set, uniqueState, listofStates);
											State freshState = list.get(list.size()-1);
											
											if(!uniqueState.contains(freshState.to) ) {
												uniqueState.add(freshState.to);
												listofStates.add(list);
											}else {
												list.remove(list.size()-1);
											}
										}
									}
								}
							}
						}
					}
		if(listofStates.size()==0) {
				for(int i = 0;i<n;i++) {
					for(int j = 0; j<n;j++) {
						if(!homework.blackCamp.contains(j+","+i) && board[j][i]=='B') { 
							HashSet<String> uniqueState = new HashSet<>();
							for(int k = 0 ; k<dy.length; k++) {
								int y =j+dy[k];
								int x = i+dx[k];
								if(isValid(y,x,n)) {
									if(board[y][x]=='.') {
										if(!homework.blackCamp.contains(y+","+x)) {
											char[][] newBoard = new char[n][n];
											for(int a = 0; a<n;a++) {
												for(int b= 0; b<n;b++) {
													newBoard[b][a]=board[b][a];
												}
											}
											newBoard[y][x]=player;
											newBoard[j][i]= '.';
											State s = new State();
											s.board = newBoard;
											s.fromLoc = j+","+i;
											s.to = y+","+x;
											s.setMoveType("SINGLE");
											
											if(!uniqueState.contains(s.to)) {
												ArrayList<State> list = new ArrayList<>(Arrays.asList(s));
												State freshState = list.get(list.size()-1);
												uniqueState.add(freshState.to);
												listofStates.add(list);
											}
											
										}
										
									}else {
										if(isValid(y+dy[k],x+dx[k],n) && board[y+dy[k]][x+dx[k]]=='.') {
											HashSet<String> set = new HashSet<>();
											char[][] newBoard = new char[n][n];
											for(int a = 0; a<n;a++) {
												for(int b= 0; b<n;b++) {
													newBoard[b][a]=board[b][a];
												}
											}
											newBoard[y+dy[k]][x+dx[k]]=player;
											newBoard[j][i]= '.';
										
											State s = new State();
											s.board = newBoard;
											s.fromLoc = j+","+i;
											s.to = (y+dy[k])+","+(x+dx[k]);
											s.setMoveType("JUMP");
											
											set.add(s.to);
											set.add(s.fromLoc);
											ArrayList<State> list = new ArrayList<>(Arrays.asList(s));
											
											findSkipStates(state, newBoard,y+dy[k],x+dx[k],j,i,list,player,set, uniqueState, listofStates);
											if(list.size()>0) {
												State freshState = list.get(list.size()-1);
												if(!uniqueState.contains(freshState.to )) {
													uniqueState.add(freshState.to);
													listofStates.add(list);
												}else {
													list.remove(list.size()-1);
												}
												
											}
										}
									}
								}
							}
						}
					}
				}
			}

				break;
				default:
					
					
		}
		
		
		return listofStates;
	}

	public boolean isValid(int y , int x, int n) {
		if(x>=0 && x< n && y>=0 && y<n) {
			return true;
		}
		return false;
	}
	public void findSkipStates(State state,char[][]board, int destj, int desti, int i, int j,
			ArrayList<State> list,char player, HashSet<String> set, HashSet<String> uniqueState,
			ArrayList<ArrayList<State>> listofStates) {
		int n = board.length;
		char[][] newBoard = new char[n][n];
		for(int a = 0; a<n;a++) {
			for(int b = 0; b<n;b++) {
				newBoard[b][a]=board[b][a];
			}
		}
		if(homework.whiteCamp.contains(destj+","+desti)) {
			for(int k =0; k<wdy.length;k++) {
				int y = destj+wdy[k];
				int x = desti+wdx[k];
				if(isValid(y, x, n)) {
					if(board[y][x]!='.') {
						if(isValid(y+wdy[k],x+wdx[k], n)&&board[y+wdy[k]][x+wdx[k]]=='.' && !set.contains((y+wdy[k]+","+(x+wdx[k])))) {
							newBoard[y+wdy[k]][x+wdx[k]]=player;
							newBoard[destj][desti]= '.';
							State s = new State();
							s.board = newBoard;
							s.fromLoc = destj+","+desti;
							s.to = (y+wdy[k])+","+(x+wdx[k]);
							set.add(s.to);
							list.add(s);
							populatePath(list,s);
							
							findSkipStates(s, newBoard, y+wdy[k], x+wdx[k], i,j,list, player, set, uniqueState, listofStates);
							if(list.size()>0) {
								State freshState = list.get(list.size()-1);
								if(!uniqueState.contains(freshState.to)) {
									uniqueState.add(freshState.to);
									ArrayList<State> temp = new ArrayList<>(list);
									freshState.setMoveType("JUMP");
									listofStates.add(temp);
									list.remove(list.size()-1);
								}else {
									list.remove(list.size()-1);
								}
								
							}
							
						}
					}
				}
			}
		}else if(homework.blackCamp.contains(destj+","+desti)) {
			for(int k =0; k<bdy.length;k++) {
				int y = destj+bdy[k];
				int x = desti+bdx[k];
				if(isValid(y, x, n)) {
					if(board[y][x]!='.') {
						if(isValid(y+bdy[k],x+bdx[k], n)&&board[y+bdy[k]][x+bdx[k]]=='.' && !set.contains((y+bdy[k]+","+(x+bdx[k])))) {
							newBoard[y+bdy[k]][x+bdx[k]]=player;
							newBoard[destj][desti]= '.';
							State s = new State();
							s.board = newBoard;
							s.fromLoc = destj+","+desti;
							s.to = (y+bdy[k])+","+(x+bdx[k]);
							set.add(s.to);
							set.add(s.fromLoc);
							list.add(s);
							populatePath(list,s);
							findSkipStates(s, newBoard, y+bdy[k], x+bdx[k], i,j,list, player, set, uniqueState, listofStates);
							if(list.size()>0) {
								State freshState = list.get(list.size()-1);
								if(!uniqueState.contains(freshState.to)) {
									uniqueState.add(freshState.to);
									ArrayList<State> temp = new ArrayList<>(list);
									freshState.setMoveType("JUMP");
									listofStates.add(temp);
									list.remove(list.size()-1);
								}else {
									list.remove(list.size()-1);
								}
								
							}
						}
					}
				}
			}
		}else {
			for(int k =0; k<8;k++) {
				int y = destj+dy[k];
				int x = desti+dx[k];
				if(isValid(y, x, n)) {
					if(board[y][x]!='.') {
						if(isValid(y+dy[k],x+dx[k], n)&&board[y+dy[k]][x+dx[k]]=='.' && !set.contains((y+dy[k]+","+(x+dx[k])))) {
							newBoard[y+dy[k]][x+dx[k]]=player;
							newBoard[destj][desti]= '.';
							State s = new State();
							s.board = newBoard;
							s.fromLoc = destj+","+desti;
							s.to = (y+dy[k])+","+(x+dx[k]);
							set.add(s.to);
							list.add(s);
							populatePath(list,s);
							findSkipStates(s, newBoard, y+dy[k], x+dx[k], i,j,list, player, set, uniqueState, listofStates);
							if(list.size()>0) {
								State freshState = list.get(list.size()-1);
								if(!uniqueState.contains(freshState.to) ) {
										uniqueState.add(freshState.to);
										ArrayList<State> temp = new ArrayList<>(list);
										freshState.setMoveType("JUMP");
										listofStates.add(temp);
										list.remove(list.size()-1);
								}else {
									list.remove(list.size()-1);
								}
								}
								
							}
						}
					}
				}
			}
		}
		
		

		
	
	
	private void populatePath(ArrayList<State> list, State s) {
		List<List<String>> res = new ArrayList<>();
		for(State st: list) {
			List<String> oneJump = new ArrayList<>();
			oneJump.add(st.fromLoc);
			oneJump.add(st.to);
			res.add(oneJump);
		}
		s.path = res;
		
	}


	public static boolean isTerminalState(char[][]board, homework hw) {
		
		
		int countWhite = 0;
		int countBlack = 0;
		boolean blackflag = false;
		boolean whiteflag = false;
			for(String location : homework.blackCamp){
				String [] arr = location.split(",");
				   int num1 = Integer.parseInt(arr[0]);//j
				   int num2 = Integer.parseInt(arr[1]);//i
				   if(board[num1][num2]== '.') {
					   blackflag = false;
				   }else if (board[num1][num2]== 'W') {
					   countWhite++;
				   }else if (board[num1][num2]== 'B') {
					   countBlack++;
				   }
				   if(countWhite+countBlack== homework.totalPieces && countWhite>=1) {
					   blackflag =  true;
				   }
				}
			countWhite = 0;
			countBlack = 0;
			for(String location : homework.whiteCamp){
					String [] arr = location.split(",");
					int num1 = Integer.parseInt(arr[0]);//j
				   int num2 = Integer.parseInt(arr[1]);//i
				   if(board[num1][num2]== '.') {
					   whiteflag = false;
				   }else if (board[num1][num2]== 'B') {
					   countBlack++;
				   }else if (board[num1][num2]== 'W') {
					   countWhite++;
				   }
				   if(countBlack+countWhite== homework.totalPieces && countBlack>=1) {
					   whiteflag = true;
				   }
				   
				}
			return whiteflag|| blackflag;

	}
	@SuppressWarnings("rawtypes")
	public static State utilityFunction(LinkedHashSet blackCamp,  LinkedHashSet whiteCamp, 
			State state, homework hw) {
		String player = hw.color;
		int white = 0;
		int black = 0;
		char[][] board= state.board;
		
			for(int i =0; i <board.length;i++ ) {
				for(int j =0; j<board[0].length;j++) {
						if(board[j][i]=='B') {
							double dist = Math.pow(i - homework.DEST_B.y , 2)
									+ Math.pow(j - homework.DEST_B.x, 2);
							black+=dist;
						}else if(board[j][i]=='W') {
							double dist = Math.pow(i - homework.DEST_W.y , 2)
									+ Math.pow(j - homework.DEST_W.x, 2);
							white+=dist;
						}
					}
					
					
				}

			int value=0;
		if(player.equals("WHITE")) {
			int num = numberOfPiecesinGoal(blackCamp,board, player);
			
			if(num >= 18 && state.to!="" && !blackCamp.contains(state.fromLoc) ) {
				String [] arr = state.to.split(",");
				int num1 = Integer.parseInt(arr[0]);//j
			   int num2 = Integer.parseInt(arr[1]);//i
				if(goalLocx>goalLocy) {
					value += (16-num1);
				}else if(goalLocx<goalLocy) {
					value += (16-num2);
				}else {
					if(num1>num2) {
						value+=(16-num1);
					}else {
						value+=(16-num2);
					}
				}
				
			}
//			if(homework.whiteCamp.contains(state.fromLoc)&& !homework.whiteCamp.contains(state.to)) {
//				value+=10;
//			}

			state.setValue(black-white+value);
			
		
		}else if(player.equals("BLACK")) {
			int num = numberOfPiecesinGoal(whiteCamp,board, player);
			if(num >= 18 && state.to!="" && !whiteCamp.contains(state.fromLoc) ) {
				String [] arr = state.to.split(",");
				int num1 = Integer.parseInt(arr[0]);//j
			   int num2 = Integer.parseInt(arr[1]);//i
				if(goalLocx>goalLocy) {
					value += (16-num1);
				}else if(goalLocx<goalLocy) {
					value += (16-num2);
				}else {
					if(num1>num2) {
						value+=(16-num1);
					}else {
						value+=(16-num2);
					}
				}
				
			}
//			if(homework.blackCamp.contains(state.fromLoc)&& !homework.blackCamp.contains(state.to)) {
//				value+=10;
//			}
			state.setValue( white-black+value);
			
			
		}
		
		return state;
		
	}


	private static int numberOfPiecesinGoal(LinkedHashSet<String> camp, char[][] board, String player) {
		int num=0;
		char ch = player.charAt(0);
		for(String location : camp) {
			String [] arr = location.split(",");
			   int num1 = Integer.parseInt(arr[0]);//j
			   int num2 = Integer.parseInt(arr[1]);//i
			   if(board[num1][num2]== ch) {
				   num++;
			   }else if(board[num1][num2]== '.') {
				   goalLocx=num2;
				   goalLocy = num1;
			   }
			   
		}
		return num;
	}
	
}
