package assignment2;

import java.util.*;

public class State {
	char[][] board;
	int alpha;
	int beta;
	String fromLoc;
	String to;
	String moveType;
	List<List<String>> path;
	int value;
	public State() {
		this.fromLoc="";
		this.to = "";
		this.moveType = "";
		this.path = new ArrayList<>();
	}
	
	public char[][] getBoard() {
		return board;
	}
	public void setBoard(char[][] board) {
		this.board = board;
	}
	public int getAlpha() {
		return alpha;
	}
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	public int getBeta() {
		return beta;
	}
	public void setBeta(int beta) {
		this.beta = beta;
	}
	public String getfromLoc() {
		return fromLoc;
	}
	public void setfromLoc(String loc) {
		this.fromLoc = loc;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int val) {
		this.value = val;
	}
	
	public String getMoveType() {
		return moveType;
	}
	public void setMoveType(String type) {
		this.moveType = type;
	}
	public List<List<String>> getPath() {
		return path;
	}
	public void setPath(List<List<String>>  list) {
		this.path = list;
	}
	

}
