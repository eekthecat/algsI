import junit.framework.TestCase;


public class PercolationTest extends TestCase{
	private static final int N = 20;

	Percolation percolation;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		percolation = new Percolation(N);
	}
	
	public void testXYToId(){
		int id = 1;
		
		for(int i = 0; i < N; i++ ){
			for(int j = 0; j < N; j++){
				int generatedId = percolation.ijTo1D(new int[]{i, j});
				assertEquals (id, generatedId);
				id ++;
			}
		}
		
		assertEquals (N * N, id - 1);
	}
	
	public void testIsOpen(){
		assertFalse(percolation.isOpen(N/2, N));
		
		percolation.open(N/2, N);
		
		assertTrue(percolation.isOpen(N/2, N));
	}
	
	public void testPercolation() throws InterruptedException{
		percolation.open(1, 15);
		percolation.open(2, 15);
		
		assertIsFull(1, 15);
		assertIsFull(2, 15);
		
		percolation.open(5, 15);
		
		assertIsNotFull(3, 15);
		assertIsNotFull(4, 15);
		assertIsNotFull(5, 15);

		percolation.open(3, 15);
		percolation.open(4, 15);
		
		assertIsFull(3, 15);
		assertIsFull(4, 15);
		assertIsFull(5, 15);
		
		percolation.open(5, 16);
		assertIsFull(5, 16);
		
		for(int i = 6, j = 1; i < N; i++, j=(j==1?-1:1)){
			percolation.open(i, 15);
			assertIsFull(i, 15);
			percolation.open(i, 15 + j);
			assertIsFull(i, 15 + j);
		}
		
		assertFalse(percolation.percolates());
		
		percolation.open(N, 15);
		
		assertTrue(percolation.percolates());
		
		PercolationVisualizer.draw(percolation, N);
	}
	
	public void testIndexOutOfBounds(){
		assertOutOfBounds(N + 1, 1);
		assertOutOfBounds(1, N + 1);
		assertOutOfBounds(1, 0);
		assertOutOfBounds(0, 0);
		assertOutOfBounds(N + 1, N + 1);
	}
	
	private void assertOutOfBounds(int x, int y){
		try{
			percolation.open(x, y);
			percolation.isFull(x, y);
			percolation.isOpen(x, y);
			fail();
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
	}
	
	private void assertIsFull(int x, int y){
		assertTrue(percolation.isFull(x, y));
	}
	
	private void assertIsNotFull(int x, int y){
		assertFalse(percolation.isFull(x, y));
	}
}
