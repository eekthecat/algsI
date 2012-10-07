import java.util.ArrayList;
import java.util.List;

public class Board {
	private int[][] blocks;
	private int[][] goal = new int[][] {
		new int[]{1, 2, 3},
		new int[]{4, 5, 6},
		new int[]{7, 8, 0}
	};
	private int[][] directions = new int[][]{
			new int[]{0, +1}, new int[]{0, -1}, new int[]{+1, 0}, new int[]{-1, 0} 
	};
	
	public Board(int[][] blocks) {
		super();
		
		this.blocks = makeCopy(blocks);
	}

	public Integer dimension() {
		return this.blocks.length;
	}

	public Integer hamming() {
		return outOfPlace(this.blocks, this.goal);
	}

	public int manhattan() {
		int manhattanDistance = 0;
		
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				int n = blocks[i][j] - 1;
				if(n != -1){
					int row = n / blocks.length;
					int col = n % blocks.length;
					
					manhattanDistance += Math.abs(row - i) + Math.abs(col - j);					
				}
			}
		}
		
		return manhattanDistance;
	}

	public boolean isGoal() {
		return outOfPlace(this.blocks, this.goal) == 0;
	}
	
	public Board twin() {
		int i1 = 0, i2 = 0, j1 = 0, j2 = 0;
		
		Boolean foundOne = Boolean.FALSE;
		Boolean foundTwo = Boolean.FALSE;
		
		int[][] twinBlocks = makeCopy(blocks);
		
		for (int i = 0; i < twinBlocks.length && !foundTwo; i++) {
			foundOne = Boolean.FALSE;
			for (int j = 0; j < twinBlocks.length && !foundTwo; j++) {
				if(twinBlocks[i][j] != 0){
					if(!foundOne){
						foundOne = Boolean.TRUE;
						i1 = i;
						j1 = j;
					}else{
						i2 = i;
						j2 = j;
						foundTwo = Boolean.TRUE;
					}
				}
			}
		}
		
		int tmp = twinBlocks[i1][j1];
		twinBlocks[i1][j1] = twinBlocks[i2][j2];
		twinBlocks[i2][j2] = tmp;
		
		return new Board(twinBlocks);
	}

	public boolean equals(Object y) {
		if(y == null){
			return false;
		}
		
		if(!(y instanceof Board)){
			return false;
		}
		
		int[][]otherBlocks = ((Board) y).blocks;
		
		return outOfPlace(this.blocks, otherBlocks) == 0;
	}

	private static int outOfPlace(int[][]blocks1, int[][]blocks2){
		if(blocks1.length != blocks2.length){
			throw new ArrayIndexOutOfBoundsException();
		}
		
		int outOfPlace = 0;
		
		for(int i = 0; i < blocks1.length; i ++){
			for(int j = 0; j < blocks1.length; j ++){
				if(blocks1[i][j] != blocks2[i][j]){
					outOfPlace++;
				}
			}
		}
		
		return outOfPlace;
	}
	
	private static int[][] makeCopy(int[][] m){
		int[][]m2 = new int[m.length][m.length];
		for(int i = 0; i < m.length; i ++){
			for(int j = 0; j < m.length; j ++){
				m2[i][j] = m[i][j]; 
			}
		}
		return m2;
	}
	
	public Iterable<Board> neighbors() {
		int[] zeroPos = getZeroPosition();
		
		List<Board> neighbors = new ArrayList<Board>();

		for (int i = 0; i < directions.length; i++) {
			int [] neighborPos = new int[]{zeroPos[0] + directions[i][0], zeroPos[1] + directions[i][1]};
			
			if(neighborPos[0] < 0 || neighborPos[1] < 0 || neighborPos[0] >= blocks.length || neighborPos[1] >= blocks.length){
				continue;
			}
			
			int[][]neighborBlocks = makeCopy(this.blocks);
			
			int tmp = neighborBlocks[neighborPos[0]][neighborPos[1]];
			
			neighborBlocks[neighborPos[0]][neighborPos[1]] = 0;
			neighborBlocks[zeroPos[0]][zeroPos[1]] = tmp;
			
			neighbors.add(new Board(neighborBlocks));
		}
		
		return neighbors;
	}

	private int[] getZeroPosition() {
		int[]zeroPos =  new int[2];
		
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				if(blocks[i][j] == 0){
					zeroPos[0] = i;
					zeroPos[1] = j;
					
					break;
				}
			}
		}
		return zeroPos;
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				s+= blocks[i][j];
				s+= " ";
			}
			s+="\n";
		}
		
		return s;
	}
	
	public static void main(String [] args){
		int[][] blocks = new int[][] {
			new int[]{0,  1,  3},
			new int[]{4, 2, 5},
			new int[]{7, 8, 6}
		};
		     
		Board c = new Board(blocks);
		
		System.out.println(c);
		
		System.out.println("NEIGHBORS");
		for(Board n:c.neighbors()){
			System.out.println(n);
		}
		System.out.println("TWIN");
		System.out.println(c.twin());
		
		System.out.println("MANHATTAN");
		System.out.println(c.manhattan());
	}
}
