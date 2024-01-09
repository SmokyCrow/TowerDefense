package handlers;

import java.util.ArrayList;
import java.util.Arrays;

import enemies.Wave;
import scenes.Playing;

public class WaveHandler {
	
	private Playing playing;
	private ArrayList<Wave> waves = new ArrayList<>();
	private int enemySpawnTickMax = 60;
	private int enemySpawnTick = enemySpawnTickMax;
	private int enemyIdx, waveIdx = -1;

	public WaveHandler(Playing playing) {
		this.playing = playing;
		createWaves();
	}
	
	public void update() {
		if(!isTimeForSpawn())
			enemySpawnTick++;
	}
	
	private void createWaves() {
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,2,1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,2,1,1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,2,2,1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,0,1,1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2,0,1,1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,2,0,1,1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,2,1,0,1,2))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,2,2,1,0,1,2,1))));
	}
	
	public void nextWave() {
		waveIdx++;
		enemyIdx = 0;
	}

	public boolean isTimeForSpawn() {
		return enemySpawnTick >= enemySpawnTickMax;
	}
	
	public boolean isEnemyListEmpty() {
		return enemyIdx >= waves.get(waveIdx).getEnemies().size();
	}
	
	public boolean isLastWave() {
		return waveIdx + 1 >= waves.size();
	}
	
	public ArrayList<Wave> getWaves(){
		return waves;
	}
	
	public int getWaveIdx() {
		return waveIdx;
	}
	
	public int getEnemyIdx() {
		return enemyIdx;
	}
	
	public int getNextEnemy() {
		enemySpawnTick = 0;
		return waves.get(waveIdx).getEnemies().get(enemyIdx++);
	}

	public void setWaveIdx(int waveData) {
		waveIdx = waveData;
	}

	public void reset() {
		waves.clear();
		createWaves();
		enemyIdx = 0;
		waveIdx = -1;
		enemySpawnTick = enemySpawnTickMax;
	}
	
}
