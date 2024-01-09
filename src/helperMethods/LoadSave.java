package helperMethods;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import obj.Tower;
import scenes.Playing;

public class LoadSave {
	
	public static BufferedImage getSpriteAtlas() {
		
		BufferedImage img = null;
		
		InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return img;
		
	}
	
	public static void saveLvlsDone(String name, int lvls) {
		File file = new File("res/" + name + ".txt");
		if(file.exists()) {
			PrintWriter pw;
			try {
				pw = new PrintWriter(file);
				pw.println(lvls);
				pw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("File: " + name + " doesn't exists!");
			return;
		}
	}
	
	public static void saveEverything(String name, int[][] idArr, ArrayList<Point> pathPoints, ArrayList<Tower> towers, int wave, int money, int lives) {
		File file = new File("res/" + name + ".txt");
		if(file.exists()) {
			try {
				PrintWriter pw = new PrintWriter(file);
				for(Integer i : Utility.TwoDIntToArray(idArr))
					pw.println(i);
				pw.println("----");
				for(Integer i : Utility.PointsToArray(pathPoints))
					pw.println(i);
				pw.println("----");
				for(Integer i : Utility.TowersToArray(towers))
					pw.println(i);
				pw.println("----");
				pw.println(wave);
				pw.println("----");
				pw.println(money);
				pw.println("----");
				pw.println(lives);
				pw.println("----");
				pw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("File: " + name + " doesn't exists!");
			return;
		}
	}
	
	public static void getData(String name, Playing playing) {
		File file = new File("res/" + name + ".txt");
		
		int[][] newArr = new int[20][20];
		ArrayList<Point> path = new ArrayList<>();
		ArrayList<Tower> towers = new ArrayList<>();
		int wave = -1, money = 1, lives = 10;
		
		boolean reading = true;
		int dataRemaining = 6;
		
		try {
			Scanner sc = new Scanner(file);
		
			while(file.exists() && dataRemaining > 0) {
					reading = true;
					ArrayList<Integer> list = new ArrayList<>();
					list.clear();
					while(reading) {
						String line = sc.nextLine();
						if(line.equals("----")) {
							reading = false;
							switch(dataRemaining) {
							case 6:
								newArr = Utility.ArrayToTwoDInt(list, 20, 20);
								System.out.println(dataRemaining);
								break;
							case 5:
								path = Utility.ArrayToPoints(list);
								System.out.println(dataRemaining);
								break;
							case 4:
								towers = Utility.ArrayToTowers(list);
								System.out.println(dataRemaining);
								break;
							case 3:
								if(list.size() > 0)
									wave = list.get(0);
								System.out.println(dataRemaining);
								break;
							case 2:
								if(list.size() > 0)
									money = list.get(0);
								System.out.println(dataRemaining);
								break;
							case 1:
								if(list.size() > 0)
									lives = list.get(0);
								System.out.println(dataRemaining);
								break;
							}
							dataRemaining--;
						}else{
							list.add(Integer.parseInt(line));
						}
					}
			}
			sc.close();
		}catch (FileNotFoundException e) {
				e.printStackTrace();
		}
		
		playing.loadLevel(newArr, path, towers, wave, money, lives);
		
	}
	
	public static int getLvlsDone(String name) {
		File file = new File("res/" + name + ".txt");
		int lvlsDone = 0;
		try {
			Scanner sc = new Scanner(file);
			String line = sc.nextLine();
			lvlsDone = Integer.parseInt(line);
			sc.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lvlsDone;
	}
	
}
