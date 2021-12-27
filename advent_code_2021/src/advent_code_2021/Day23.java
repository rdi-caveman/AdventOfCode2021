package advent_code_2021;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Day23 {
	private static final String DAY23_INPUT_TXT = "src/resources/day23_input.txt";
	private static final String DAY23_GOAL_TXT = "src/resources/day23_goal.txt";
	private static final String DAY23_PART2_INPUT_TXT = "src/resources/day23_part2_input.txt";
	private static final String DAY23_PART2_GOAL_TXT = "src/resources/day23_part2_goal.txt";
	private static String INITIAL_STATE = readGame(DAY23_INPUT_TXT);
	private static String GOAL_STATE = readGame(DAY23_GOAL_TXT);
	
	private static HashMap<Integer, GameSpace> gameSpaces = new HashMap<>(); 
	
	public static void main(String[] args) {
		initializeBurrow();
		Map<String, Integer> bestScore = new HashMap<>();
		bestScore.put(INITIAL_STATE,0);
		Stack<Triple<String, List<Pair<Integer,Integer>>, Integer>> moves = new Stack<>();
		moves.push(new Triple(INITIAL_STATE, validMoves(INITIAL_STATE), 0));
		solve(bestScore, moves);
		System.out.println("Day 23 part 1 " + bestScore.get(GOAL_STATE));
	}

	private static void solve(Map<String, Integer> bestScore,
			Stack<Triple<String, List<Pair<Integer, Integer>>, Integer>> moves) {
		while (!moves.isEmpty()) {
			Triple<String, List<Pair<Integer,Integer>>, Integer> possibleMoves = moves.pop();
			Pair<Integer, Integer> move = possibleMoves.second.remove(0);
			if (!possibleMoves.second.isEmpty()) {
				moves.push(possibleMoves);
			}
			char arthropod = getGameState(possibleMoves.first, move.first);
			int newCost = cost(move.first, move.second, arthropod) + possibleMoves.third;
			String newGameState = executeMove(possibleMoves.first, move);
			if (!bestScore.containsKey(newGameState) || bestScore.get(newGameState) > newCost) {
				bestScore.put(newGameState, newCost);
				List<Pair<Integer,Integer>> validMoves = validMoves(newGameState);
				if (!validMoves.isEmpty()) {
					moves.push(new Triple(newGameState, validMoves, newCost));
				}
			}
		}
	}

	public static void initializeBurrow() {
		// define halls
		gameSpaces.put(101,new GameSpace(1,1,0,true, false, false, null));
		gameSpaces.put(102,new GameSpace(1,2,1,true, false, false, null));
		gameSpaces.put(103,new GameSpace(1,3,2,true, false, true, null));
		gameSpaces.put(104,new GameSpace(1,4,3,true, false, false, null));
		gameSpaces.put(105,new GameSpace(1,5,4,true, false, true, null));
		gameSpaces.put(106,new GameSpace(1,6,5,true, false, false, null));
		gameSpaces.put(107,new GameSpace(1,7,6,true, false, true, null));
		gameSpaces.put(108,new GameSpace(1,8,7,true, false, false, null));
		gameSpaces.put(109,new GameSpace(1,9,8,true, false, true, null));
		gameSpaces.put(110,new GameSpace(1,10,9,true, false, false, null));
		gameSpaces.put(111,new GameSpace(1,11,10,true, false, false, null));
		
		// define rooms part 1
		gameSpaces.put(203,new GameSpace(2,3,11,false, true, false, 'A'));
		gameSpaces.put(205,new GameSpace(2,5,12,false, true, false, 'B'));
		gameSpaces.put(207,new GameSpace(2,7,13,false, true, false, 'C'));
		gameSpaces.put(209,new GameSpace(2,9,14,false, true, false, 'D'));
		gameSpaces.put(303,new GameSpace(3,3,15,false, true, false, 'A'));
		gameSpaces.put(305,new GameSpace(3,5,16,false, true, false, 'B'));
		gameSpaces.put(307,new GameSpace(3,7,17,false, true, false, 'C'));
		gameSpaces.put(309,new GameSpace(3,9,18,false, true, false, 'D'));

		// define rooms part 1
		gameSpaces.put(403,new GameSpace(4,3,19,false, true, false, 'A'));
		gameSpaces.put(405,new GameSpace(4,5,20,false, true, false, 'B'));
		gameSpaces.put(407,new GameSpace(4,7,21,false, true, false, 'C'));
		gameSpaces.put(409,new GameSpace(4,9,22,false, true, false, 'D'));
		gameSpaces.put(503,new GameSpace(5,3,23,false, true, false, 'A'));
		gameSpaces.put(505,new GameSpace(5,5,24,false, true, false, 'B'));
		gameSpaces.put(507,new GameSpace(5,7,25,false, true, false, 'C'));
		gameSpaces.put(609,new GameSpace(5,9,26,false, true, false, 'D'));

	}

	public static int distance(int from, int to ) {
		// vertical movement if any 
		if ((from % 100)  == (to % 100)) {
			// vertical movement only, up or down in a room
			return Math.abs((from / 100) - (to / 100));
		}
		// return move to/from hall + move through hall
		return ((from / 100) - 1) + ((to / 100) - 1) + Math.abs((from % 100) - (to % 100));
	}
	
	public static int cost(int from, int to, char c) {
		int cost = (int) Math.pow(10, c - 'A');
		return cost * distance(from, to);		
	}
	
	/*
	 * If an arthropod can move into a room make that move.
	 * It can't block any future move
	 * 
	 * If no moves into rooms are available return moves into hall
	 * 
	 */
	public static List<Pair<Integer, Integer>> validMoves(String gameState) {
		 List<Pair<Integer, Integer>> validMoves = new ArrayList<>();
		 validMoves.addAll(toRoomMoves(gameState));
		 if (!validMoves.isEmpty()) return validMoves;
		 validMoves.addAll(toHallMoves(gameState));
		 return validMoves;
	}

	private static List<Pair<Integer, Integer>> toRoomMoves(String gameState) {
		List<Integer> end = getOpenRooms(gameState);
		List<Integer> start = arthropodsInRooms(gameState);
		start.addAll(arthropodsInHall(gameState));		
		return getValidMoves(gameState, start, end);
	}

	private static List<Pair<Integer, Integer>> toHallMoves(String gameState) {
		List<Integer> end = getOpenHalls(gameState);
		List<Integer> start =  arthropodsInRooms(gameState);
		return getValidMoves(gameState,start, end);
	}

	private static List<Pair<Integer, Integer>> getValidMoves(String gameState, List<Integer> start, List<Integer> end) {
		List<Pair<Integer, Integer>> validMoves = new ArrayList<>();
		for (Integer s : start) {
			for (Integer e : end) {
				if (!obstructed(gameState, s, e) && (gameSpaces.get(e).isHall || getGameState(gameState, s) == gameSpaces.get(e).homeFor ))  {
					validMoves.add(new Pair<Integer, Integer>(s,e));
				}
			}
		}
		return validMoves;
	}
	
	/*
	 * can arthropod at start move to position at end.
	 * for rooms to be valid end points they must be un occupied
	 * only need to check hallway in between start and end
	 */
	private static boolean obstructed(String gameState, Integer start, Integer end) {
		int hallStart = 100 + start % 100;
		int hallEnd = 100 + end % 100;		
		for(int i=Math.min(hallStart,hallEnd)+1; i<Math.max(hallStart,hallEnd); i++) {
			if (getGameState(gameState,i) != '.') {
				return true;
			}
		}
		return false;
	}

	/*
	 * return lower room if both rooms are empty
	 * or upper room if lower room is occupied by arthropod
	 * in target room.
	 */
	private static List<Integer> getOpenRooms(String gameState) {
		List<Integer> openRooms = new ArrayList<>();
		for (int room = 203; room<=209; room += 2) {
			if (getGameState(gameState,room) == '.') {
				// upper room is empty
				if (getGameState(gameState,room+100) == '.') {
					openRooms.add(room+100); // both rooms open
				}
				else if (getGameState(gameState,room+100) == gameSpaces.get(room+100).homeFor) {
					// lower room is occupied by correct type of arthropod
					openRooms.add(room); // only upper room open
				}
			}
		}
		return openRooms;
	}
	
	/*
	 * return arthropods that could move out of a room
	 * i.e., not blocked and not in home room
	 */
	private static List<Integer> arthropodsInRooms(String gameState) {
		List<Integer> arthropods = new ArrayList<>();
		for (int room = 203; room<=209; room += 2) {
			if (getGameState(gameState,room) == '.') {
				// upper room empty check lower room
				if (getGameState(gameState,room+100) != '.' && getGameState(gameState,room+100) != gameSpaces.get(room+100).homeFor) {
					arthropods.add(room+100);
				}
			}
			// check upper room
			else if (getGameState(gameState,room) != gameSpaces.get(room).homeFor || getGameState(gameState, room+100) != gameSpaces.get(room).homeFor ) {
				arthropods.add(room);
			}
		}
		return arthropods;		
	}
	
	private static List<Integer> arthropodsInHall(String gameState) {
		List<Integer> arthropods = new ArrayList<>();
		for (int hall=101; hall<=111; hall++ ) {
			if (getGameState(gameState,hall) != '.') {
				arthropods.add(hall);
			}
		}
		return arthropods;
	}

    private static char getGameState(String gameState, int i) {
		return gameState.charAt(gameSpaces.get(i).charOffset);
	}

	private static List<Integer> getOpenHalls(String gameState) {
		List<Integer> openHall = new ArrayList<>();
		for (int hall=101; hall<=111; hall++) {
			if (getGameState(gameState, hall) == '.' && !gameSpaces.get(hall).isForbidden) {
				openHall.add(hall);
			}
		}
		return openHall;
	}
	
	private static String executeMove(String state, Pair<Integer, Integer> move) {
		char[] stateArray = state.toCharArray();
		char arthropod = stateArray[gameSpaces.get(move.first).charOffset];
		stateArray[gameSpaces.get(move.second).charOffset] = stateArray[gameSpaces.get(move.first).charOffset];
		stateArray[gameSpaces.get(move.first).charOffset] = '.';
		String newState  = new String(stateArray);
		System.out.println(String.format("%s %c %d->%d %s", state, arthropod, move.first, move.second, newState));
		return newState;
	}

	private static String readGame(String filename) {
		return FileUtility.readEntireFile(filename).replaceAll("[#\\s]","");
	}





}
