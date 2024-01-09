package helperMethods;

public class Constants {

	public static class Direction{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class Tiles{
		public static final int WATER_TILE = 0;
		public static final int GRASS_TILE = 1;
		public static final int ROAD_TILE = 2;
	}
	
	public static class Enemies{
		public static final int TRASHCAN = 0;
		public static final int BLOB = 1;
		public static final int PLASTICINE = 2;
		
		public static float GetSpeed(int enemyType) {
			switch(enemyType) {
			case BLOB:
				return 0.5f;
			case TRASHCAN:
				return 0.3f;
			case PLASTICINE:
				return 0.75f;
			}
			
			return 0;
		}
		
		public static int GetStartingHealth(int enemyType) {
			switch(enemyType) {
			case BLOB:
				return 100;
			case TRASHCAN:
				return 250;
			case PLASTICINE:
				return 85;
			}
			
			return 0;
		}
		
	}
	
	public static class Towers{
		public static final int ARCHER = 0;
		public static final int CANNON = 1;
		public static final int ICE = 2;
		
		public static String GetName(int towerType) {
			switch(towerType) {
			case CANNON:
				return "Cannon";
			case ARCHER:
				return "Archer";
			case ICE:
				return "Ice";
			}
			return "";
		}
		
		public static int GetDefaultDamage(int towerType) {
			switch(towerType) {
			case CANNON:
				return 20;
			case ARCHER:
				return 12;
			case ICE:
				return 0;
			}
			return 0;
		}
		
		public static float GetDefaultRange(int towerType) {
			switch(towerType) {
			case CANNON:
				return 110;
			case ARCHER:
				return 90;
			case ICE:
				return 60;
			}
			return 0;
		}
		
		public static float GetDefaultCooldown(int towerType) {
			switch(towerType) {
			case CANNON:
				return 120;
			case ARCHER:
				return 20;
			case ICE:
				return 40;
			}
			return 0;
		}
		
	}
	
	public static class Projectiles{
		public static final int ARROW = 0;
		public static final int BOMB = 1;
		public static final int SNOW = 2;
		
		public static float GetSpeed(int type) {
			switch(type) {
			case BOMB:
				return 8f;
			case ARROW:
				return 4f;
			case SNOW:
				return 6f;
			}
			return 0f;
		}
	}
	
}
