
public class Percolation {
	Integer N;
	private WeightedQuickUnionUF wqu;
	private int [][] openSites;
	
	private int[][] directions = new int[][]{
			new int[]{0, +1}, new int[]{0, -1}, new int[]{+1, 0}, new int[]{-1, 0} 
	};
	
	// create N-by-N grid, with all sites blocked
	public Percolation(int N) {
		this.N = N;
		this.wqu = new WeightedQuickUnionUF((N * N) + 2);
		this.openSites = new int[N][N];
	}

	// open site (row i, column j) if it is
	// not already
	public void open(int x, int y){
		int [] ij = xyToij(x, y);
		
		openSites[ij[0]][ij[1]] = 1;
		
		int id = ijTo1D(ij);
		
		if(ij[0] == 0){
			wqu.union(id, 0);
		}else if(ij[0] == (N - 1)){
			wqu.union(id, (N * N) + 1);
		}
		
		for(int d = 0; d < directions.length; d++){
			int[] neighbor = getNeighbor(directions[d], ij);
			
			if(neighbor != null && isOpen(neighbor)){
				wqu.union(id, ijTo1D(neighbor));
			}
		}
	}
	
	protected int[] getNeighbor(int[] operation, int [] ij){
		int [] neighbor = new int[2];
		
		neighbor[0] = ij[0] + operation[0];
		neighbor[1] = ij[1] + operation[1];
		
		if(neighbor[0] < 0 || neighbor[1] < 0 || neighbor[0] >= N || neighbor[1] >=N){
			return null;
		}
		
		return neighbor;
	}
	
	// is site (row i, column j) open?
	public boolean isOpen(int x, int y) {
		return isOpen(xyToij(x, y));
	}
	
	protected boolean isOpen(int [] ij){
		return openSites[ij[0]] [ij[1]] == 1;
	}
	
	protected int[] xyToij(int x, int y){
		if(x < 1 || y < 1 || x > N || y > N){
			throw new ArrayIndexOutOfBoundsException(x + ", " + y);
		}
		return new int[]{--x, --y};
	}
	
	// is site (row i, column j) full?
	public boolean isFull(int x, int y){
		int id = ijTo1D(xyToij(x, y));
		return wqu.connected(0, id);
	}
	
	// does the system percolate?
	public boolean percolates() {
		return wqu.connected(0, (N * N) + 1);
	}
	
	protected int ijTo1D(int[] ij){
		return ( ij[0] * N) + ij[1] + 1;
	}
}
