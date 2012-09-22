/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

	// compare points by slope
	public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
		@Override
		public int compare(Point p1, Point p2) {
			Double s1 = Point.this.slopeTo(p1);
			Double s2 = Point.this.slopeTo(p2);
			return s1.compareTo(s2);
		}
	};

	private final int x; // x coordinate
	private final int y; // y coordinate

	// create the point (x, y)
	public Point(int x, int y) {
		/* DO NOT MODIFY */
		this.x = x;
		this.y = y;
	}

	// plot this point to standard drawing
	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	// draw line between this point and that point to standard drawing
	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	// slope between this point and that point
	public double slopeTo(Point that) {
		if (this.x == that.x && this.y == that.y) { // Degenerate
			return Integer.MIN_VALUE;
		}

		if (this.x == that.x) { // Vertical
			return Integer.MAX_VALUE;
		}

		if (this.y == that.y) { // Horizontal
			return 0;
		}

		return ((that.y - this.y) * 1D) / (that.x - this.x);
	}

	// is this point lexicographically smaller than that one?
	// comparing y-coordinates and breaking ties by x-coordinates
	public int compareTo(Point that) {
		if (this.y == that.y) {
			return this.x - that.x;
		}

		return this.y - that.y;
	}

	// return string representation of this point
	public String toString() {
		/* DO NOT MODIFY */
		return "(" + x + ", " + y + ")";
	}

	// unit test
	public static void main(String[] args) {
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);

		Point p1 = new Point(10000, 10066);
		Point p2 = new Point(15000, 10000);

		p1.drawTo(p2);
		
		StdDraw.show(0);
	}
}
