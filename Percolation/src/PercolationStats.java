
public class PercolationStats {
	Integer N;
	Integer T;
	double[] percThresholds;
	
	// perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		super();
		this.N = N;
		this.T = T;
		this.percThresholds = new double[T];
		 
		for(int i = 0; i < T; i ++){
			Percolation perc = new Percolation(N);
			
			int dim = N * N;
			
			for(int j = 0; j < dim; j++){
				int rndX = StdRandom.uniform(N) + 1;
				int rndY = StdRandom.uniform(N) + 1;
				
				perc.open(rndX, rndY);
				
				if(perc.percolates()){
					PercolationVisualizer.draw(perc, N);
					percThresholds[i] = ((j*1D)/dim);
				}
			}
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(percThresholds);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(percThresholds);
	}

	public static void main(String[] args) {
		PercolationStats ps = new PercolationStats(20, 100);
		System.out.println(ps.mean());
		System.out.println(ps.stddev());
	}
}
