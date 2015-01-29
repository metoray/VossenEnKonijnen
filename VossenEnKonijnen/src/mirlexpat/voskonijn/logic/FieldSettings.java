package mirlexpat.voskonijn.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
	private LinkedHashMap<Class,AnimalEntry> spawnList;
	
	public FieldSettings(){
		width = 120;
		depth = 80;
		grassGrowthChance = 0.1;
		randomSeed = 1111;
		spawnList = new LinkedHashMap<Class,AnimalEntry>();
		addToSpawnList(new AnimalEntry(0.04, 150, Fox.class) {
			
			@Override
			public Actor getActor(Field field, Location location) {
				return new Fox(true, field, location);
			}
		});
		addToSpawnList(new AnimalEntry(0.2, 100, Rabbit.class) {
			
			@Override
			public Actor getActor(Field field, Location location) {
				return new Rabbit(true, field, location);
			}
		});
		addToSpawnList(new AnimalEntry(0.01, 750, KomodoDragon.class) {
			
			@Override
			public Actor getActor(Field field, Location location) {
				return new KomodoDragon(true, field, location);
			}
		});
		addToSpawnList(new AnimalEntry(0.01, 0, Hunter.class) {
			
			@Override
			public Actor getActor(Field field, Location location) {
				return new Hunter(true, field, location);
			}
		});
		addToSpawnList(new AnimalEntry(0.0, 0, Grass.class) {
			
			@Override
			public Actor getActor(Field field, Location location) {
				return new Grass(true, field, location);
			}
		});
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

	public HashMap<Class, AnimalEntry> getSpawnList(){
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
	
    public static abstract class AnimalEntry{
    	
    	private int maxAge;
    	
    	private double chance;
    	private Class clazz;
		/**
		 * @param chance
		 * @param clazz
		 */
		public AnimalEntry(double chance, int maxAge, Class clazz) {
			super();
			this.chance = chance;
			this.setMaxAge(maxAge);
			this.clazz = clazz;
		}
		
		public String getName(){
			return clazz.getSimpleName();
		}
		
		public Actor trySpawn(Field field, Location location, Random rand){
			if(rand.nextDouble() <= chance){
				return getActor(field, location);
			}
			return null;
		}
		
		public Class getClazz(){
			return clazz;
		}
		
		public abstract Actor getActor(Field field, Location location);

		public int getMaxAge() {
			return maxAge;
		}

		public void setMaxAge(int maxAge) {
			this.maxAge = maxAge;
		}
    	
    }
    
    public void addToSpawnList(AnimalEntry entry){
    	spawnList.put(entry.getClazz(), entry);
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
