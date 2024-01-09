package helperMethods;

import java.awt.Point;
import java.util.ArrayList;

import obj.Tower;

public class Utility {
	
	public static int[][] ArrayToTwoDInt(ArrayList<Integer> list, int ySize, int xSize){
		int [][] newArr = new int[ySize][xSize];
		
		for(int i = 0; i < newArr.length; i++)
			for(int j = 0; j < newArr[i].length; j++) {
				int idx = i*ySize + j;
				newArr[i][j] = list.get(idx);
			}
		return newArr;
	}
	
	public static int[] TwoDIntToArray(int[][] twoArr) {
		int[] newArr = new int[twoArr.length * twoArr[0].length];
		
		for(int i = 0; i < twoArr.length; i++)
			for(int j = 0; j < twoArr[i].length; j++) {
				int idx = i*twoArr[i].length + j;
				newArr[idx] = twoArr[i][j];
			}
		return newArr;
	}
	
	public static ArrayList<Point> ArrayToPoints(ArrayList<Integer> list){
		ArrayList<Point> newList = new ArrayList<>();
		
		for(int i = 0; i < list.size(); i++) {
			newList.add(new Point(list.get(i), list.get(++i)));
		}
		for(Point p : newList)
			System.out.println(p.toString());
		return newList;
	}
	
	public static int[] PointsToArray(ArrayList<Point> points) {
		int[] newArr = new int[points.size()*2];
		
		int j = 0;
		for(int i = 0; i < points.size(); i++) {
			newArr[j] = points.get(i).x;
			newArr[++j] = (int) points.get(i).y;
			++j;
		}
		
		return newArr;
	}
	
	public static ArrayList<Tower> ArrayToTowers(ArrayList<Integer> list){
		ArrayList<Tower> newList = new ArrayList<>();
		
		for(int i = 0; i < list.size(); i++) {
			newList.add(new Tower(list.get(i), list.get(++i), list.get(++i), list.get(++i)));
		}
		return newList;
	}

	public static int[] TowersToArray(ArrayList<Tower> towers) {
		int[] newArr = new int[towers.size()*4];
		
		int j = 0;
		for(int i = 0; i < towers.size(); i++) {
			newArr[j] = towers.get(i).getX();
			newArr[++j] = towers.get(i).getY();
			newArr[++j] = towers.get(i).getId();
			newArr[++j] = towers.get(i).getTowerType();
			++j;
		}
		
		return newArr;
	}
	
	public static int GetDistance(float x1, float y1, float x2, float y2) {
		float a = Math.abs(x1 - x2);
		float b = Math.abs(y1 - y2);
		return (int) Math.hypot(a, b);
	}

}
