import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {
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
		this.initialBoard = initialBoard;
	} // find a solution to the initial board (using the A* algorithm){}

	public boolean isSolvable() {
		Solver twinSolver = new Solver(initialBoard.twin());
		
		return twinSolver.solution() != null || this.solution() != null;
	} // is the initial board solvable?

	public int moves() {
		List<Board> solution = getSolutionBoards();
		
		if(solution == null){
			return -1;
		}
		
		return solution.size() - 1;
	} // min number of moves to solve initial board; -1 if no solution

	public Iterable<Board> solution(){
		return getSolutionBoards();
	}
	
	public List<Board> getSolutionBoards() {
		MinPQ<SearchNode> stepsQueue = new MinPQ<SearchNode>(hammingComparator);
		SearchNode initialSearchNode = new SearchNode();
		initialSearchNode.board = initialBoard;
		initialSearchNode.previous = null;
		stepsQueue.insert(initialSearchNode);
		
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
		
		System.out.println("SOLUTION");
		for(Board b:solver.solution()){
			System.out.println(b);	
		}
		System.out.println("MOVES");
		System.out.println(solver.moves());
		
		System.out.println("UNFEASIBLE");
		Solver unfeasibleSolver = new Solver(new Board(new int[][] {
			new int[]{1, 2, 3},
			new int[]{4, 5, 6},
			new int[]{8, 7, 0}
		}));
		System.out.println(unfeasibleSolver.isSolvable());
	} // solve a slider puzzle (given below){}
}