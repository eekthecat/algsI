import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item>{
	Node head;
	Integer size;
	
	public RandomizedQueue() {
		head = new Node();
		size = 0;
	} 

	public boolean isEmpty() {
		return head.next == null;
	} 

	public int size() {
		return size;
	} 

	public void enqueue(Item item) {
		Node newNode = new Node();
		newNode.value = item;
		newNode.next = head.next;
		head.next = newNode;
		size++;
	}

	public Item dequeue() {
		SampleAndPrevious sampleAndPrevious = getSampleAndPrevious();
		sampleAndPrevious.previous.next = sampleAndPrevious.sample.next;
		size--;
		return sampleAndPrevious.sample.value;
	} 

	public Item sample() {
		return getSampleAndPrevious().sample.value;
	}

	public Iterator<Item> iterator() {
		return new Iterator<Item>(){
			Node current = head;
			
			@Override
			public boolean hasNext() {
				return current.next != null;
			}

			@Override
			public Item next() {
				if(!hasNext()){
					throw new NoSuchElementException();
				}
				
				Node next = current.next;
				current = next;
				return next.value;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	private SampleAndPrevious getSampleAndPrevious(){
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		
		Integer rnd = StdRandom.uniform(size);
		
		Node n = head.next;
		Node previous = head;
		
		for(int i = 0; i < rnd; i++){
			previous = n;
			n = n.next;
		}
		
		return new SampleAndPrevious(n, previous);
	}
	
	private class Node{
		Node next;
		Item value;
	}
	
	private class SampleAndPrevious{
		Node sample;
		Node previous;
		
		private SampleAndPrevious(Node sample, Node previous){
			super();
			this.sample = sample;
			this.previous = previous;
		}
	}
	
	public static void main(String [] args){
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		
		rq.enqueue(1);
		System.out.println(rq.dequeue());
		
		int sampleSize = 100;
		
		for(int i = 0 ; i < sampleSize; i++){
			rq.enqueue(i + 1);
		}

		for (Integer i : rq){
			System.out.println(i);
		}
		
		System.out.println("now random");
		
		for(int i = 0 ; i < sampleSize; i++){
			System.out.println(rq.dequeue());	
		}
		
		assert (0 == rq.size());
	}
}
