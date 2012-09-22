import java.util.ArrayList;

import junit.framework.TestCase;

public class DequeTest extends TestCase{
	public void test(){
		Deque<String> dq = new Deque<String>();
		
		dq.addFirst("a1");
		dq.addFirst("a2");
		dq.addLast("a3");
		dq.addLast("a4");
		dq.addFirst("a6");
		dq.addLast("a7");
		
		assertElements(dq, "a6", "a2", "a1", "a3", "a4", "a7");
		
		assertEquals("a6", dq.removeFirst());
		assertEquals("a7", dq.removeLast());
		assertEquals("a4", dq.removeLast());
		
		assertElements(dq, "a2", "a1", "a3");
	}
	
	public void test2(){
		Deque<Integer> dq = new Deque<Integer>();
		
		for(int i = 0; i < 100000; i++){
			if(i % 2 == 0){
				dq.addFirst(i);	
			}else{
				dq.addLast(i);
			}
			
			assertEquals (i + 1, dq.size());
		}
	}
	
	private void assertElements(Deque<String> dq, String... expected){
		ArrayList<String> dqElements = new ArrayList<String>();
		for(String s:dq){
			dqElements.add(s);
		}
		
		assertEquals(expected.length, dqElements.size());
		
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], dqElements.get(i));
		}
	}
}
