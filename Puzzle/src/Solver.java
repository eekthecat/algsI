import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {
	private MinPQ<SearchNode> stepsQueue;
	
	private Comparator<SearchNode> hammingComparator = new Comparator<SearchNode>(){
		@Override
		public int compare(SearchNode s1, SearchNode s2){
			return s1.board.hamming().compareTo(s2.board.hamming());
		}
	};
	
	private class SearchNode{
		SearchNode previous;
		Board board;
	}
	
	private Board initialBoard;
	
	public Solver(Board initialBoard) {
		super();
		this.stepsQueue = new MinPQ<SearchNode>(hammingComparator);
		this.initialBoard = initialBoard;
		SearchNode initialSearchNode = new SearchNode();
		initialSearchNode.board = initialBoard;
		initialSearchNode.previous = null;
		stepsQueue.insert(initialSearchNode);
	} // find a solution to the initial board (using the A* algorithm){}

	public boolean isSolvable() {
		Solver twinSolver = new Solver(initialBoard.twin());
		
		return twinSolver.solution() != null || this.solution() != null;
	} // is the initial board solvable?

//	public int moves() {
//	} // min number of moves to solve initial board; -1 if no solution

	public Iterable<Board> solution() {
		List<Board> solution = new ArrayList<Board>();
		
		Boolean foundGoal = Boolean.FALSE;
		
		while(!stepsQueue.isEmpty() && !foundGoal){
			SearchNode sn = stepsQueue.delMin();
			Board board = sn.board;
			
			solution.add(board);
			
			if(board.isGoal()){
				foundGoal = Boolean.TRUE;
			}

			for(Board neighborBoard:board.neighbors()){
				if(sn.previous == null || !sn.previous.board.equals(neighborBoard)){
					SearchNode neighborSearchNode = new SearchNode();
					neighborSearchNode.previous = sn;
					neighborSearchNode.board = neighborBoard;
					stepsQueue.insert(neighborSearchNode);
				}
			}
		}
		
		if(!foundGoal){
			return null;
		}
		
		return solution;
	} // sequence of boards in a shortest solution; null if no solution

	public static void main(String[] args) {
		int[][] blocks = new int[][] {
				new int[]{0, 1, 3},
				new int[]{4, 2, 5},
				new int[]{7, 8, 6}
			};
		Board initial = new Board(blocks);
		
		Solver solver = new Solver(initial);
		
		for(Board b:solver.solution()){
			System.out.println(b);	
		}
		
	} // solve a slider puzzle (given below){}
}