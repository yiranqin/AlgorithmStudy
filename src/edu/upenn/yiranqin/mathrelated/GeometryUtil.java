package edu.upenn.yiranqin.mathrelated;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;

public class GeometryUtil {
	public static final double epsilon = 0.00001;
	
	public static class Line{
		private double slope;
		private double yIntercept;
		private boolean isInfiniteSlope = false;
		
		public Line(double _slope, double _yIntercept){
			slope = _slope;
			yIntercept = _yIntercept;
		}
		
		public Line(double _slope, double _yIntercept, boolean _isInfiniteSlope){
			slope = _slope;
			yIntercept = _yIntercept;
			isInfiniteSlope = _isInfiniteSlope;
		}
		
		public Line(Point a, Point b){
			if(Math.abs(a.getX() - b.getX()) > epsilon){
				slope = (a.getY() - b.getY()) / (a.getX() - b.getX());
				yIntercept = a.getY() - slope * a.getX(); 
			}else{
				slope = 0.0;
				isInfiniteSlope = true;
				yIntercept = a.getX(); //only intercept with x axis, this is vital
			}
		}
		
		public double getSlope(){
			return slope;
		}
		
		public double getYIntercept(){
			return yIntercept;
		}
		
		public boolean getInfiniteSlopeFlag(){
			return isInfiniteSlope;
		}
		
		public boolean isCrossPoint(Point point){
			if(isInfiniteSlope)
				return point.getX() - yIntercept <= epsilon;
			else
				return point.getY() - slope * point.getX() - yIntercept <= epsilon;
		}
		/**
		 * 7.3
		 * Only when slope is the same(for float number, never use ==) or intercept on y axis will two lines intercept
		 * @param other
		 * @return
		 */
		public boolean isIntersect(Line other){
			return (Math.abs(slope - other.getSlope()) < epsilon) || (Math.abs(yIntercept - other.getYIntercept()) < epsilon);
		}
		
		@Override
		public int hashCode(){
			int scale = (int)((double) 1.0 / epsilon);
			return (int) slope * scale | (int) yIntercept * scale;			
		}
		
		@Override
		public boolean equals(Object o){
			Line other = (Line)o;
			if(this.getInfiniteSlopeFlag() == other.getInfiniteSlopeFlag()
					&& Math.abs(this.getSlope() - other.getSlope()) < epsilon
					&& Math.abs(this.getYIntercept() - other.getYIntercept()) < epsilon)
				return true;
			else
				return false;
		}
	}
	
	/**
	 * the square implementation include all four points 
	 */
	public static class Square{
		public Point topLeft;
		public Point bottomLeft;
		public Point topRight;
		public Point bottomRight;
				
		public Square(Point _topLeft, Point _bottomLeft, Point _topRight, Point _bottomRight){
			topLeft = _topLeft;
			bottomLeft = _bottomLeft;
			topRight = _topRight;
			bottomRight = _bottomRight;
		}
		
		public Point middle(){
			return new Point((topLeft.getX() + topRight.getX()) / 2.0, (topLeft.getY() + bottomLeft.getY())/2.0);
		}
		/**
		 * 7.5
		 * @return
		 */
		public Line cutTwoSquareInHalves(Square other){
			Point thisMiddle = this.middle();
			Point otherMiddle = other.middle();
			
			if(thisMiddle.equals(otherMiddle))
				return new Line(topLeft, bottomRight);
			else
				return new Line(thisMiddle, otherMiddle);
		}
	}
	
	public static class Point{
		private double x;
		private double y;		
		
		public Point(double _x, double _y){
			x = _x;
			y = _y;
		}
		
		public double getX(){
			return x;
		}
		
		public double getY(){
			return y;
		}
		
		public boolean equals(Point other){
			if(Math.abs(this.x - other.getX()) < epsilon && Math.abs(this.y - other.getY()) < epsilon)
				return true;
			else
				return false;
		}
		
		@Override
		public String toString(){
			return "x:" + x + " y:" + y; 
		}
	}
	
	public static class PointPair{
		private Point a;
		private Point b;		
		
		public PointPair(Point _a, Point _b){
			a = _a;
			b = _b;
		}
		
		public Point getA(){
			return a;
		}
		
		public Point getB(){
			return b;
		}
		
		@Override
		public String toString(){
			return "x1:" + a.getX() + " y1:" + a.getY() +
					" x2:" + b.getX() + " y2:" + b.getY(); 
		}
	}
		
	public static class PointsWithDistanceToOrigin implements Comparable<Object>{
		public Point point;
		public double distanceToOrigin;
		
		public PointsWithDistanceToOrigin(Point _point){
			point = _point;
			double x = point.getX();
			double y = point.getY();
			distanceToOrigin = x*x + y*y;
		}
		
		@Override
		public int compareTo(Object o){
			PointsWithDistanceToOrigin other = (PointsWithDistanceToOrigin)o;
			if(this.distanceToOrigin == other.distanceToOrigin)
				return 0;
			else if(this.distanceToOrigin > other.distanceToOrigin)
				return 1;
			else
				return -1;
		}
		
	}
	
	/**
	 * 7.6
	 * Find the best line the intercept the most points, best line is the one being formed by two points most times
	 * @param points
	 * @return
	 */
	public static Line findBestLine(Point[] points){
		HashMap<Line, Integer> map = new HashMap<Line, Integer>();
		Line maxPointsLine = null;
		int maxPoints = 0;
		
		for(int i = 0; i < points.length; i++){
			for(int j = i + 1; j < points.length; j++){
				Line cur = new Line(points[i], points[j]);
				int curPoints = 0;
				if(map.get(cur) == null)
					map.put(cur, 1);
				else{
					curPoints = map.get(cur);
					map.put(cur, curPoints + 1);
				}
				curPoints++;
				
				if(curPoints > maxPoints){
					maxPoints = curPoints;
					maxPointsLine = cur;
				}
			}
		}
		return maxPointsLine;
	}
	
	public static Point[] findKPointsClosestToOrigin(Point[] points, int k){
		/* wrap up into a object 
		 * or use a hashtable and an array for distance since the hashtable uses object rather than values
		 * we don't suffer problem that same distance for different points 
		 * */
		PointsWithDistanceToOrigin[] array = new PointsWithDistanceToOrigin[points.length];
		for(int i = 0; i < points.length; i++){
			array[i] = new PointsWithDistanceToOrigin(points[i]);
		}
		ArrayUtil.quickSelection(array, k, true);
		
		Point[] result = new Point[k];
		for(int i = 0; i < k; i++){
			result[i] = array[i].point;
		}
		return result;
	}
	
	public static Point[] findKPointsClosestToOriginHeap(Point[] points, int k){
		if(k > points.length)
			return points;
		
		Double[] kClosestDistance = new Double[k];
		HashMap<Double, Integer> map = new HashMap<Double, Integer>();
		for(int i = 0; i < k; i++){
			Double distance = new Double(points[i].getX() * points[i].getX()
					+ points[i].getY() * points[i].getY());
			kClosestDistance[i] = distance;
			map.put(distance, i);	
		}
		ArrayUtil.buildHeap(kClosestDistance, kClosestDistance.length, false);
		
		for(int i = k; i < points.length; i++){
			Double distance = new Double(points[i].getX() * points[i].getX()
					+ points[i].getY() * points[i].getY());			
			if(distance < kClosestDistance[0]){
				kClosestDistance[0] = distance;
				ArrayUtil.percolateDown(kClosestDistance, 0, k - 1, false);
			}				
			map.put(distance, i);	
		}
		
		Point[] result = new Point[k];
		for(int i = 0; i < k; i++){
			result[i] = points[map.get(kClosestDistance[i])];
		}
		return result;
	}
	
	public static class XComparator implements Comparator<Point>{
		@Override
		public int compare(Point p1, Point p2) {
			if(Math.abs(p1.getX() - p2.getX()) > epsilon){
				if(p1.getX() > p2.getX())
					return 1;
				else
					return -1;
			}else
				return 0;
		}
	}
	
	public static class YComparator implements Comparator<Point>{
		@Override
		public int compare(Point p1, Point p2) {
//			if(Math.abs(p1.getY() - p2.getY()) > epsilon){
//				if(p1.getY() > p2.getY())
//					return 1;
//				else
//					return -1;
//			}else
//				return 0;
			return new Double(p1.getY()).compareTo(new Double(p2.getY()));
		}
	}
	
	public static double distance(PointPair pair){
		return Math.sqrt(Math.pow(pair.getA().getX() - pair.getB().getX(), 2) +
				Math.pow(pair.getA().getY() - pair.getB().getY(), 2));
	}
	
	/**
	 * Input file format as
	 * numOfPoints1
	 * x11 y11
	 * x12 y12
	 * numOfPoints2
	 * x21 y21
	 * x22 y22
	 * x23 y23
	 * .
	 * x2n y2n
	 * 0
	 * @param filePath
	 */
	public static void findClosestPointPairWithinFiles(String filePath) {
    	if(filePath == null)
    		System.exit(-1);
    	
        File file = new File(filePath);
        if(!file.exists())
            return;
        
        BufferedReader in = null;
        ArrayList<Point> points = null;
        try{
	        in = new BufferedReader(new FileReader(file));
	        String line;
	        while ((line = in.readLine()) != null) {
	            String[] lineArray = line.split(" ");
	            if(lineArray.length == 1){
	            	int num = Integer.parseInt(lineArray[0]);
	            	/*the point list is of length between 0 and 10000*/
	            	if(num > 0 && num <= 10000){
	            		if(points != null){
	            			PointPair closestPair = closestPointPair(points);
		            		double dis = distance(closestPair);
		            		/*number format should be keep last four digit after decimal*/
		            		DecimalFormat numFormat = new DecimalFormat("#.0000");
		            		if(dis >= 10000)
		            			System.out.println("INFINITY");
		            		else
		            			System.out.println(numFormat.format(dis));
	            		}
	            		points = new ArrayList<Point>(num);
	            	}else if(num == 0){
	            		break;
	            	}
	            }
	            	
	            if (points != null && lineArray.length == 2) {
	            	double x = Double.parseDouble(lineArray[0]);
	            	double y = Double.parseDouble(lineArray[1]);
	            	if(x >= 0 && x < 40000 && y >=0 && y < 40000)
	            		points.add(new Point(x, y));
	            }
	        }
	    }catch(Exception ex){
	    	ex.printStackTrace();
	        System.exit(-1);
        }finally{
        	try{
        		if(in != null)
        			in.close();
        	}catch(Exception e){
        		e.printStackTrace();
    	        System.exit(-1);
        	}
        }
        System.exit(0);
    }
	
	public static PointPair closestPointPair(Point[] points){
		if(points.length <= 1)
			return null;
		
		ArrayList<Point> pointList = new ArrayList<Point>(points.length);
		for(Point p : points){
			pointList.add(p);
		}		
		return closestPointPair(pointList);
 	}
	
	public static PointPair closestPointPair(ArrayList<Point> points){
		if(points.size() <= 1)
			return null;
		
		ArrayList<Point> Px = new ArrayList<Point>();
		ArrayList<Point> Py = new ArrayList<Point>();
		
		for(Point point : points){
			Px.add(point);
			Py.add(point);
		}
		
		Collections.sort(Px, new XComparator());
		Collections.sort(Py, new YComparator());
		
		return closestPointPairRec(Px, Py);
 	}
	
	private static PointPair closestPointPairRec(ArrayList<Point> Px, ArrayList<Point> Py){
		int pointNum = Px.size();
		/* The base condition should actually be just 2 points,
		 *  but it's better to make three to simplify the logic
		 */
		if(pointNum <= 3){
			if(pointNum == 2){
				return new PointPair(Px.get(0), Px.get(1));
			}else if(pointNum == 3){
				PointPair pair1 = new PointPair(Px.get(0), Px.get(1));
				PointPair pair2 = new PointPair(Px.get(1), Px.get(2));
				PointPair pair3 = new PointPair(Px.get(0), Px.get(2));
				if(distance(pair1) < distance(pair2) && distance(pair1) < distance(pair3))
					return pair1;
				else if(distance(pair2) < distance(pair1) && distance(pair2) < distance(pair3))
					return pair2;
				else
					return pair3;
			}else
				return null;			
		}
		/**
		 * Construct(new) the list for x and y for each half every time rather than use start, end
		 * Since no matter what the points list ordered by Y axis will have to be newed every time
		 * 
		 * Basically need to split the already ordered Y ordered points list into halves
		 *  and we do this using a set for the left half points ordered by X 
		 */
		HashSet<Point> lXSet = new HashSet<Point>();
		ArrayList<Point> PLx = new ArrayList<Point>();
		ArrayList<Point> PLy = new ArrayList<Point>();
		ArrayList<Point> PRx = new ArrayList<Point>();
		ArrayList<Point> PRy = new ArrayList<Point>();
		
		for(int i = 0; i < pointNum/2; i++){
			lXSet.add(Px.get(i));
			PLx.add(Px.get(i));
		}		
		
		for(int i = pointNum/2; i < pointNum; i++)
			PRx.add(Px.get(i));
		
		for(Point point : Py){
			if(lXSet.contains(point))
				PLy.add(point);
			else
				PRy.add(point);
		}
		/* To start the recursion and break the problem into smaller pieces */
		PointPair leftMinPair = closestPointPairRec(PLx, PLy);
		PointPair rightMinPair = closestPointPairRec(PRx, PRy);
		
		double leftMinDis = distance(leftMinPair);
		double rightMinDis = distance(rightMinPair);
		double minDis = Math.min(leftMinDis, rightMinDis);
		
		ArrayList<Point> z = new ArrayList<Point>();
		/* The pivot(splitting point is the last one on the left half) */
		Point pivot = PLx.get(PLx.size() - 1);
		for(Point cur : Py){
			/**
			 * filter in the Y ordered points list(no need to merge PLy and PRy in this implementation)
			 * with condition that the |Xi - Xk/2| <= minDis
			 */
			if(Math.abs(cur.getX() - pivot.getX()) <= minDis){
				z.add(cur);
			}
		}
		
		double minDisInZ = minDis;
		PointPair minPair = leftMinPair;
		
		for(int index = 0; index < z.size(); index++){
			for(int candidate = index + 1; candidate < index + 11 &&
					candidate < z.size(); candidate++){				
				PointPair curPair = new PointPair(z.get(index), z.get(candidate));
				double curDis = distance(curPair);
				if(curDis < minDisInZ){
					minDisInZ = curDis;
					minPair = curPair;
				}
			}
		}
		
		if(minDisInZ < minDis)
			return minPair;
		else if(leftMinDis < rightMinDis)
			return leftMinPair;
		else
			return rightMinPair;
	}
}

