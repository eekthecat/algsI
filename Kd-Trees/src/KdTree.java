import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KdTree {
	private Integer size;
	private Node root;
	private Double minX = 0D;
	private Double minY = 0D;
	private Double maxX = 100D;
	private Double maxY = 100D;
	
	static private class Node{
		Point2D value;
		Boolean usesY;
		Node left;
		Node right;
		Comparator<Point2D> comparator;
		
		private Node(Point2D value, Comparator<Point2D> comparator, Boolean usesY){
			super();
			this.value = value;
			this.comparator = comparator;
			this.usesY = usesY;
		}
		
		public static Node xNode(Point2D value){
			return new Node(value, Point2D.X_ORDER, Boolean.FALSE);
		}

		public static Node yNode(Point2D value){
			return new Node(value, Point2D.Y_ORDER, Boolean.TRUE);
		}
		
		private Integer compareTo(Point2D p){
			return comparator.compare(value, p);
		}
		
		private Node createNext(Point2D value){
			if(usesY){
				return xNode(value);
			}else{
				return yNode(value);
			}
		}
		
		public Node insert(Point2D point){
			Integer cmp = this.compareTo(point);
			
			if(cmp == 0){
				return this;
			}
			
			if(cmp > 0){
				return insertLeft(point);
			}
			
			return insertRight(point);
		}
			
		public Boolean contains(Point2D point){
			Integer cmp = this.compareTo(point);
			
			if(cmp == 0){
				return Boolean.TRUE;
			}
			
			if(cmp > 0){
				return containsIn(left, point);
			}
			
			return containsIn(right, point);			
		}
		
		private Boolean containsIn(Node node, Point2D point){
			if(node == null){
				return Boolean.FALSE;
			}
			
			return node.contains(point);
		}
		
		private Node insertLeft(Point2D point){
			if(left == null){
				left = createNext(point);
				return left;
			}
			return left.insert(point);
		}
		
		private Node insertRight(Point2D point){
			if(right == null){
				right = createNext(point);
				return right;
			}
			return right.insert(point);
		}
		
		public void executeRectCommand(TreeCommand command, Double minX, Double minY, Double maxX, Double maxY){
			Boolean continues = command.execute(this, minX, minY, maxX, maxY);
			
			if(!continues){
				return;
			}
			
			if(left != null){
				if(usesY){
					left.executeRectCommand(command, minX, minY, maxX, this.value.y());	
				}else{
					left.executeRectCommand(command, minX, minY, this.value.x(), maxY);
				}
			}
			
			if(right != null){
				if(usesY){
					right.executeRectCommand(command, minX, this.value.y(), maxX, maxY);	
				}else{
					right.executeRectCommand(command, this.value.x(), minY, maxX, maxY);
				}
			}
		}
		
		public String toString(){
			return value.toString() + " " + usesY;
		}
	}
	
	private interface TreeCommand{
		public Boolean execute(Node n, Double minX, Double minY, Double maxX, Double maxY);
	}
	
	private class TreeDraw implements TreeCommand{

		@Override
		public Boolean execute(Node node, Double minX, Double minY, Double maxX,
				Double maxY) {
			Point2D value = node.value;
			StdDraw.setPenRadius(10D);
			StdDraw.setPenColor(StdDraw.BLACK);
			value.draw();
			StdDraw.show(0);
			StdDraw.setPenRadius();
			StdDraw.setPenColor();
			
			if(node.usesY){
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.line(minX, value.y(), maxX, value.y());
			}else{
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.line(value.x(), minY, value.x(), maxY);
			}
			
			StdDraw.show(0);
			
			return Boolean.TRUE;
		}
		
	}
	
	private class RangeSearch implements TreeCommand{
		List<Point2D> points = new ArrayList<Point2D>();
		RectHV rect;
		
		public RangeSearch(RectHV rect){
			this.rect = rect;
		}
		
		@Override
		public Boolean execute(Node n, Double minX, Double minY, Double maxX,
				Double maxY) {
			if(rect.distanceTo(n.value) == 0D){
				points.add(n.value);
			}
			
			if(!rect.intersects(new RectHV(minX, minY, maxX, maxY))){
				return Boolean.FALSE;
			}
			
			return Boolean.TRUE;
		}
	}
	
	private class NearestSearch implements TreeCommand{
		Point2D queryPoint;
		Point2D nearest;
		Double nearestDistance = 0D;
		
		public NearestSearch(Point2D queryPoint){
			this.queryPoint = queryPoint;
		}

		@Override
		public Boolean execute(Node n, Double minX, Double minY, Double maxX,
				Double maxY) {
			Double distance = n.value.distanceSquaredTo(queryPoint);
			
			if(distance < nearestDistance || nearest == null){
				nearest = n.value;
				nearestDistance = distance;
			}
			
			RectHV rect = new RectHV(minX, minY, maxX, maxY);
			
			if(rect.distanceSquaredTo(queryPoint) > nearestDistance){
				return Boolean.FALSE;
			}
			
			return Boolean.TRUE;
		}
		
	}
	
	public KdTree(){
		super();
		this.size = 0;
	}
	
	public boolean isEmpty() {
		return size.equals(new Integer(0));
	} // is the set empty?

	public int size() {
		return size;
	} // number of points in the set

	public void insert(Point2D p) {
		if(isEmpty()){
			root = Node.xNode(p);
		}else{
			root.insert(p);			
		}
		
		size++;
	} // add the point p to the set (if it is not already in the set){}
	
	public boolean contains(Point2D p) {
		if(isEmpty()){
			return Boolean.FALSE;
		}
		
		return root.contains(p);
	} // does the set contain the point p?

	public void draw() {
		if(this.isEmpty()){
			StdDraw.show();
			return;
		}
		
		StdDraw.setXscale(minX, maxX);
		StdDraw.setYscale(minY, maxY);
		StdDraw.show(0);
		
		root.executeRectCommand(new TreeDraw(),minX, minY, maxX, maxY);				
	} // draw all of the points to standard draw
//
	public Iterable<Point2D> range(RectHV rect) {
		if(this.isEmpty()){
			return new ArrayList<Point2D>();
		}
		
		RangeSearch search = new RangeSearch(rect);
		
		root.executeRectCommand(search, minX, minY, maxX, maxY);
		
		return search.points;
	} // all points in the set that are inside the rectangle
//
	public Point2D nearest(Point2D p) {
		if(isEmpty()){
			return null;
		}
		
		NearestSearch search = new NearestSearch(p);
		
		root.executeRectCommand(search, minX, minY, maxX, maxY);
		
		return search.nearest;
	}
	
	public static void main(String[] args){
		KdTree tree = new KdTree();
		
		tree.insert(new Point2D(70, 20));
		tree.insert(new Point2D(50, 40));
		tree.insert(new Point2D(20, 30));
		tree.insert(new Point2D(40, 70)); 
		tree.insert(new Point2D(90, 60));
		
		System.out.println(tree.size());

		System.out.println("Contains");
		System.out.println(tree.contains(new Point2D(40, 70)));
		System.out.println(tree.contains(new Point2D(8, 6456)));
		
		System.out.println("Nearest");
		Point2D point1 = new Point2D(55, 45);
		System.out.println(tree.nearest(point1));
		Point2D point2 = new Point2D(90, 80);
		System.out.println(tree.nearest(point2));
		
		
		
		System.out.println("Range");
		tree.draw();
		
		RectHV rect = new RectHV(30, 50, 95, 80);
		
		rect.draw();
		
		Iterable<Point2D> pointsInRange = tree.range(rect);
		
		for(Point2D p:pointsInRange){
			System.out.println(p);
		}
		
		StdDraw.setPenRadius(10D);
		point1.draw();
		point2.draw();
		StdDraw.setPenRadius();
		
		StdDraw.show(0);
	}
}
