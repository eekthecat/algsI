import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Fast {
	public static void findFourInSameLineSegment(Point[] points) {
		Point[] others = new Point[points.length - 1];
		
		for (int i = 0; i < points.length; i++) {
			Point mainPoint = points[i];
			
			for(int j = 0; j < others.length; j++){
				others[j] = points[j >= i?j + 1:j]; 
			}
			
			Arrays.sort(others, mainPoint.SLOPE_ORDER);

			Double slope = others[0].slopeTo(mainPoint);
			List<Point> line = new ArrayList<Point>();
			line.add(mainPoint);
			
			for(int a = 1; a < others.length; a ++){
				Double thisSlope = others[a].slopeTo(mainPoint);
				if(!thisSlope.equals(slope)){
					if(line.size() > 3){
						Collections.sort(line);
						System.out.println(line);
						line.get(0).drawTo(line.get(line.size() - 1));
					}
					slope = thisSlope;
					line.clear();
					line.add(mainPoint);
				}
				
				line.add(others[a]);
			}
			 
		}
	}
	
	public static void main(String [] args){
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);
        StdDraw.setPenRadius(StdDraw.getPenRadius());

		In in = new In(
				"/home/nsuarez/Cursos/Algorithms I/programmingExcercises/PatternRecognition/input.txt");

		int N = in.readInt();

		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			Point p = new Point(x, y);
			points[i] = p;
			p.draw();
		}

		findFourInSameLineSegment(points);

		StdDraw.show();
	}
}
