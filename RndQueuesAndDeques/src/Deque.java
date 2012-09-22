import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Object[] items;
	private static int DEFAULT_SIZE = 10;
	
	private int f;
	private int l;
	
	public Deque() {
		items = new Object[DEFAULT_SIZE];
		
		f = (items.length/2);
		l = f;
	}

	private void ensureCapacity(){
		if(f == 0 || l == items.length - 1){
			Integer size = size();
			Integer newF = size/2;
			Integer newL = newF + size - 1; 
			Object[] newItems = new Object[size * 2];

			System.arraycopy(items, f, newItems, newF, size);
			
			items = newItems;
			f = newF;
			l = newL;
		}
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}

	public int size() {
		if(items[f] == null){
			return 0;
		}
		return (l - f) + 1;
	}

	public void addFirst(Item item) {
		ensureCapacity();
		
		if(items[f] == null){
			items[f] = item;
		}else{
			items[--f] = item;
		}
	}

	public void addLast(Item item) {
		ensureCapacity();
		
		if(items[l] == null){
			items[l] = item;
		}else{
			items[++l] = item;
		}
	}

	public Item removeFirst() {
		Item i = removeAt(f);
		if(i != null)
			f++;
		return i;
	}

	public Item removeLast() {
		Item i = removeAt(l);
		if(i != null)
			l--;
		return i;
	}
	
	@SuppressWarnings("unchecked")	
	private Item removeAt(Integer index){
		Item i = (Item) items[index];
		if(i == null)
			return null;
		items[index] = null;
		return i;
	}

	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			Integer i = f;
			
			@Override
			public boolean hasNext() {
				if(i >= items.length){
					return false;
				}
				
				return items[i] != null;
			}

			@SuppressWarnings("unchecked")
			@Override
			public Item next() {
				if(!this.hasNext()){
					throw new NoSuchElementException();
				}
				
				return (Item) items[i++];
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
}