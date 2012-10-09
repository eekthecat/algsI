import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
	private TreeSet<Point2D> points;
	
	public PointSET() {
		super();
		this.points = new TreeSet<Point2D>();
	} // construct an empty set of points

	public boolean isEmpty() {
		return points.isEmpty();
	} // is the set empty?

	public int size() {
		return points.size();
	} // number of points in the set

	public void insert(Point2D p) {
		points.add(p);
	} // add the point p to the set (if it is not already in the set){}

	public boolean contains(Point2D p) {
		return points.contains(p);
	} // does the set contain the point p?

	public void draw() {
		for(Point2D p:points){
			p.draw();
		}
	} // draw all of the points to standard draw

	public Iterable<Point2D> range(RectHV rect) {
		List<Point2D> pointsInRect = new ArrayList<Point2D>();
		
		for(Point2D point:points){
			if(rect.distanceTo(point) == 0D){
				pointsInRect.add(point);
			}
		}
		
		return pointsInRect;
	} // all points in the set that are inside the rectangle

	public Point2D nearest(Point2D aPoint) {
		Point2D[] arrPoints = points.toArray(new Point2D[0]);
		
		if(arrPoints.length == 0){
			return null;
		}

		Point2D nearest = arrPoints[0];
		Double nearestDistance = aPoint.distanceSquaredTo(nearest);
		
		for(int i = 1; i <  arrPoints.length; i ++){
			Point2D point = arrPoints[i];
			Double distance = aPoint.distanceSquaredTo(point);
			if(distance < nearestDistance){
				nearest = point;
				nearestDistance = distance;
			}
		}
		
		return nearest;
	} // a nearest neighbor in the set to p; null if set is empty
	
	public static void main(String [] args){
		StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
//        StdDraw.show(0);
        
        Point2D p1 = new Point2D(10, 400);
        Point2D p2 = new Point2D(10, 4000);
        Point2D p3 = new Point2D(1000, 10000);
        PointSET pointSet = new PointSET();
		pointSet.insert(p1);
		pointSet.insert(p2);
		pointSet.insert(p3);
        pointSet.draw();
        
        
        RectHV rec = new RectHV(100, 100, 20000, 20000);
        rec.draw();
        
//        StdDraw.show(0);
        
       System.out.println(pointSet.range(rec));
       System.out.println(pointSet.nearest(new Point2D(10, 4001)));
     }
}