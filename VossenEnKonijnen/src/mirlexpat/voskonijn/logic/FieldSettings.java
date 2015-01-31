package mirlexpat.voskonijn.logic;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Random;

import mirlexpat.voskonijn.actor.Actor;
import mirlexpat.voskonijn.actor.Fox;
import mirlexpat.voskonijn.actor.Grass;
import mirlexpat.voskonijn.actor.Hunter;
import mirlexpat.voskonijn.actor.KomodoDragon;
import mirlexpat.voskonijn.actor.Rabbit;

public class FieldSettings implements Serializable {
	
	private static final long serialVersionUID = -3696332681664249116L;
	private int width, depth, randomSeed;
	private double grassGrowthChance;
	private LinkedHashMap<Class<Actor>,AnimalEntry> spawnList;
	
	public FieldSettings(FieldSettings other){
		this.width = other.width;
		this.depth = other.depth;
		this.grassGrowthChance = other.grassGrowthChance;
		this.randomSeed = other.randomSeed;
		this.spawnList = other.copySpawnList();
	}
	
	private LinkedHashMap<Class<Actor>,AnimalEntry> copySpawnList(){
		LinkedHashMap<Class<Actor>,AnimalEntry> newMap = new LinkedHashMap<Class<Actor>,AnimalEntry>();
		for(Entry<Class<Actor>,AnimalEntry> mapEntry: spawnList.entrySet()){
			newMap.put(mapEntry.getKey(), new AnimalEntry(mapEntry.getValue()));
		}
		return newMap;
	}
	
	public FieldSettings(){
		width = 120;
		depth = 80;
		grassGrowthChance = 0.1;
		randomSeed = 1111;
		spawnList = new LinkedHashMap<Class<Actor>,AnimalEntry>();
		AnimalEntry fox, rabbit, komodoDragon, hunter;
		fox = new AnimalEntry(0.04, Fox.class);
		fox.set("max age", 150).set("max litter size", 8).set("food value", 45).set("breeding chance", 0.08).set("breeding age", 5);
		rabbit = new AnimalEntry(0.2, Rabbit.class);
		rabbit.set("max age", 100).set("max litter size", 8).set("food value", 6).set("breeding chance", 0.14).set("breeding age", 15);
		komodoDragon = new AnimalEntry(0.01, KomodoDragon.class);
		komodoDragon.set("max age", 750).set("max litter size", 2).set("breeding chance", 0.08).set("breeding age", 400);
		hunter = new AnimalEntry(0.01, Hunter.class);
		
		fox.setSpawner(new Spawner() {
			
			@Override
			public Actor getActor(Field field, Location loc) {
				return new Fox(true, field, loc);
			}
		});
		
		rabbit.setSpawner(new Spawner() {
			
			@Override
			public Actor getActor(Field field, Location loc) {
				return new Rabbit(true, field, loc);
			}
		});
		
		komodoDragon.setSpawner(new Spawner() {
			
			@Override
			public Actor getActor(Field field, Location loc) {
				return new KomodoDragon(true, field, loc);
			}
		});
		
		hunter.setSpawner(new Spawner() {
			
			@Override
			public Actor getActor(Field field, Location loc) {
				return new Hunter(true, field, loc);
			}
		});
		
		addToSpawnList(fox, rabbit, komodoDragon, hunter);
	}
	
	public Field generateField(){
		return new Field(this);
	}
	
	
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public HashMap<Class<Actor>, AnimalEntry> getSpawnList(){
		return spawnList;
	}
	
	public boolean equals(Object o){
		if(o==this) return true;
		if(o instanceof FieldSettings){
			FieldSettings other = (FieldSettings) o;
			return other.depth==depth&&
				   other.width==width;
		}
		return false;
	}
	
	public static abstract class Spawner implements Serializable {
		public abstract Actor getActor(Field field, Location loc);
	}
	
    public static class AnimalEntry implements Serializable {
		
    	private Class clazz;
    	private Spawner spawner;
    	private LinkedHashMap<String,Number> settings;
		
		public LinkedHashMap<String, Number> getSettings() {
			return settings;
		}

		/**
		 * @param spawnChance
		 * @param clazz
		 */
		public AnimalEntry(double spawnChance, Class clazz) {
			super();
			this.clazz = clazz;
			this.settings = new LinkedHashMap<String,Number>();
			this.set("spawn chance", spawnChance);
		}
		
		public AnimalEntry(AnimalEntry other){
			this.clazz = other.clazz;
			this.spawner = other.spawner;
			this.settings = (LinkedHashMap<String, Number>) other.settings.clone();
		}
		
		public void setSpawner(Spawner spawner){
			this.spawner = spawner;
		}

		public Class getClazz() {
			return clazz;
		}

		public double getChance() {
			return spawner==null?0:this.getDouble("spawn chance");
		}

		public String getName(){
			return clazz.getSimpleName();
		}
		
		public Actor trySpawn(Field field, Location location, Random rand){
			if(rand.nextDouble() <= getChance()){
				return spawner.getActor(field, location);
			}
			return null;
		}
		
		public AnimalEntry set(String key, int value) {
			settings.put(key, new Integer(value));
			return this;
		}
		
		public AnimalEntry set(String key, double value){
			settings.put(key, new Double(value));
			return this;
		}
		
		public int getInteger(String key, int defaultVal){
			if(settings.containsKey(key)){
				return settings.get(key).intValue();
			}
			else{
				return defaultVal;
			}
		}
		
		public double getDouble(String key, double defaultVal){
			if(settings.containsKey(key)){
				return settings.get(key).doubleValue();
			}
			else{
				return defaultVal;
			}
		}
		
		public int getInteger(String key){
			return getInteger(key, 0);
		}
		
		public double getDouble(String key){
			return getDouble(key, 0.0d);
		}
		
		public boolean isDouble(String key){
			return settings.containsKey(key)?(settings.get(key) instanceof Double):false;
		}
    	
    }
    
    public void addToSpawnList(AnimalEntry entry){
    	spawnList.put(entry.getClazz(), entry);
    }
    
    public void addToSpawnList(AnimalEntry... entries){
    	for(AnimalEntry entry: entries){
    		addToSpawnList(entry);
    	}
    }

	public double getGrassGrowthChance() {
		return grassGrowthChance;
	}

	public void setGrassGrowthChance(double grassGrowthChance) {
		this.grassGrowthChance = grassGrowthChance;
	}

	public int getRandomSeed() {
		return randomSeed;
	}

	public void setRandomSeed(int randomSeed) {
		this.randomSeed = randomSeed;
	}


}
