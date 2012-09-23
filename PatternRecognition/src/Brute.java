public class Brute {
	public static void findFourInSameLineSegment(Point[] points) {
		for (int a = 0; a < points.length; a++) {
			for (int b = a + 1; b < points.length; b++) {
				for (int c = b + 1; c < points.length; c++) {
					for (int d = c + 1; d < points.length; d++) {
						if (areCollinear(points[a], points[b], points[c],
								points[d])) {
							System.out.println(points[a] + " -> " + points[b]
									+ " -> " + points[c] + " -> " + points[d]);

							points[a].drawTo(points[d]);
							points[b].drawTo(points[c]);
							points[c].drawTo(points[d]);
						}
					}
				}
			}
		}
	}

	private static boolean areCollinear(Point p1, Point p2, Point p3, Point p4) {
		double s1 = p1.slopeTo(p2);
		double s2 = p1.slopeTo(p3);
		double s3 = p1.slopeTo(p4);

		return (s1 == s2 && s1 == s2 && s1 == s3);
	}

	public static void main(String[] asd) {
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
