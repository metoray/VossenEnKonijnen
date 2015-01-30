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
	
	public FieldSettings(){
		width = 120;
		depth = 80;
		grassGrowthChance = 0.1;
		randomSeed = 1111;
		spawnList = new LinkedHashMap<Class<Actor>,AnimalEntry>();
		addToSpawnList(new AnimalEntry(0.04, Fox.class, 150, 8, 45, 0.08, 5) {
			
			@Override
			public Actor getActor(Field field, Location location) {
				return new Fox(true, field, location);
			}
		});
		addToSpawnList(new AnimalEntry(0.2, Rabbit.class, 100, 8, 6, 0.14, 15) {
			
			@Override
			public Actor getActor(Field field, Location location) {
				return new Rabbit(true, field, location);
			}
		});
		addToSpawnList(new AnimalEntry(0.01, KomodoDragon.class, 750, 2, 0, 0.08, 400) {
			
			@Override
			public Actor getActor(Field field, Location location) {
				return new KomodoDragon(true, field, location);
			}
		});
		addToSpawnList(new AnimalEntry(0.01, Hunter.class,  0, 0, 0, 0.0, 0) {
			
			@Override
			public Actor getActor(Field field, Location location) {
				return new Hunter(true, field, location);
			}
		});
		addToSpawnList(new AnimalEntry(0.0, Grass.class, 0, 8, 10, 0.5, 0) {
			
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
	
    public static abstract class AnimalEntry implements Serializable {
    	
    	private int maxAge, maxLitter, foodValue, breedAge;
    	
    	public int getBreedAge() {
			return breedAge;
		}

		public void setBreedAge(int breedAge) {
			this.breedAge = breedAge;
		}

		private double spawnChance, breedChance;
    	private Class clazz;
    	
    	
		
		/**
		 * @param spawnChance
		 * @param clazz
		 * @param maxAge
		 * @param maxLitter
		 * @param foodValue
		 * @param breedChance
		 * @param breedAge
		 */
		public AnimalEntry(double spawnChance, Class clazz, int maxAge,
				int maxLitter, int foodValue, double breedChance, int breedAge) {
			super();
			this.spawnChance = spawnChance;
			this.clazz = clazz;
			this.maxAge = maxAge;
			this.maxLitter = maxLitter;
			this.foodValue = foodValue;
			this.breedChance = breedChance;
			this.breedAge = breedAge;
		}

		public double getChance() {
			return spawnChance;
		}

		public void setChance(double chance) {
			this.spawnChance = chance;
		}

		public String getName(){
			return clazz.getSimpleName();
		}
		
		public Actor trySpawn(Field field, Location location, Random rand){
			if(rand.nextDouble() <= spawnChance){
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

		public int getMaxLitter() {
			return maxLitter;
		}

		public void setMaxLitter(int maxLitter) {
			this.maxLitter = maxLitter;
		}

		public int getFoodValue() {
			return foodValue;
		}

		public void setFoodValue(int foodValue) {
			this.foodValue = foodValue;
		}

		public double getBreedChance() {
			return breedChance;
		}

		public void setBreedChance(double breedChance) {
			this.breedChance = breedChance;
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
