package mirlexpat.voskonijn.logic;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Random;

import mirlexpat.voskonijn.actor.*;

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
		fox.set("max age", 150).set("max litter size", 2).set("food value", 14).set("breeding chance", 0.08).set("breeding age", 15);
		rabbit = new AnimalEntry(0.2, Rabbit.class);
		rabbit.set("max age", 100).set("max litter size", 8).set("breeding chance", 0.14).set("breeding age", 15).set("food value",10).set("vulnerable chance", 0.9);
		komodoDragon = new AnimalEntry(0.01, KomodoDragon.class);
		komodoDragon.set("max age", 750).set("max litter size", 2).set("breeding chance", 0.08).set("breeding age", 400).set("food value", 45);
		hunter = new AnimalEntry(0.01, Hunter.class);
		/**
		 * This method creates the foxes in the simulation.
		 */
		fox.setSpawner(new Spawner() {

			@Override
			public Actor getActor(Field field, Location loc) {
				return new Fox(true, field, loc);
			}
		});
		/**
		 * This method creates the rabbits in the simulation.
		 */
		rabbit.setSpawner(new Spawner() {

			@Override
			public Actor getActor(Field field, Location loc) {
				return new Rabbit(true, field, loc);
			}
		});
		/**
		 * This method creates the komododragons in the simulation.
		 */
		komodoDragon.setSpawner(new Spawner() {

			@Override
			public Actor getActor(Field field, Location loc) {
				return new KomodoDragon(true, field, loc);
			}
		});
		/**
		 * This method creates the hunters in the simulation.
		 */
		hunter.setSpawner(new Spawner() {

			@Override
			public Actor getActor(Field field, Location loc) {
				return new Hunter(true, field, loc);
			}
		});

		addToSpawnList(fox, rabbit, komodoDragon, hunter);
	}
	/**
	 * Generates a new field.
	 */
	public Field generateField(){
		return new Field(this);
	}


	/**
	 * Returns the width of the field.
	 * @return width of the field
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Sets the width of the field.
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * Returns the depth of the field.
	 * @return depth of the field
	 */
	public int getDepth() {
		return depth;
	}
	/**
	 * Sets the depth of the field.
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}
	/**
	 * Returns the spawnlist of the field.
	 * @return spawnList
	 */
	public HashMap<Class<Actor>, AnimalEntry> getSpawnList(){
		return spawnList;
	}
	/**
	 * Equals method for fields.
	 * @return FieldSettings of other field.
	 * @return false if not the same fields.
	 */
	public boolean equals(Object o){
		if(o==this) return true;
		if(o instanceof FieldSettings){
			FieldSettings other = (FieldSettings) o;
			return other.depth==depth&&
					other.width==width;
		}
		return false;
	}
	/**
	 * Abstract class for the spawner of instances in the simulatorfield.
	 * @author Mirko Rog
	 * @version 1.0
	 *
	 */
	public static abstract class Spawner implements Serializable {
		public abstract Actor getActor(Field field, Location loc);
	}
	/**
	 * Class which holds the animals/hunters on the field.
	 * @author Mirko Rog
	 * @version 1.0
	 */
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
		/**
		 * Equals method for AnimalEntry.
		 * @param other
		 */
		public AnimalEntry(AnimalEntry other){
			this.clazz = other.clazz;
			this.spawner = other.spawner;
			this.settings = (LinkedHashMap<String, Number>) other.settings.clone();
		}
		/**
		 * Setter for the spawner.
		 * @param spawner
		 */
		public void setSpawner(Spawner spawner){
			this.spawner = spawner;
		}
		/**
		 * Getter voor de clazz variable.
		 * @return clazz
		 */
		public Class getClazz() {
			return clazz;
		}
		/**
		 * Getting for the spawn chances of an animal or a hunter.
		 * @return spawn chance
		 */
		public double getChance() {
			return spawner==null?0:this.getDouble("spawn chance");
		}
		/**
		 * Getter for the name of a an animal or a hunter.
		 * @return clazz name
		 */
		public String getName(){
			return clazz.getSimpleName();
		}
		/**
		 * Method that tries to spawn the animals and hunters.
		 * @param field Field to spawn on.
		 * @param location Location in the field to spawn in.
		 * @param rand Randomizer method to check whether or not to spawn a certain instance or not.
		 * @return Place to spawn an instance.
		 * @return false If not spawning.
		 */
		public Actor trySpawn(Field field, Location location, Random rand){
			if(rand.nextDouble() <= getChance()){
				return spawner.getActor(field, location);
			}
			return null;
		}
		/**
		 * Setter for AnimalEntry. (ints)
		 * @param key to put
		 * @param value to put
		 * @return Entered keys and values.
		 */
		public AnimalEntry set(String key, int value) {
			settings.put(key, new Integer(value));
			return this;
		}
		/**
		 * Setter for AnimalEntry. (doubles)
		 * @param key to put
		 * @param value to put
		 * @return Entered keys and values.
		 */
		public AnimalEntry set(String key, double value){
			settings.put(key, new Double(value));
			return this;
		}
		/**
		 * Returns an integer based on the entered key.
		 * @param key entered
		 * @param defaultVal returned when key does not match.
		 * @return defaultVal returned when key doesn't match.
		 */
		public int getInteger(String key, int defaultVal){
			if(settings.containsKey(key)){
				return settings.get(key).intValue();
			}
			else{
				return defaultVal;
			}
		}
		/**
		 * Returns a double based on the entered key.
		 * @param key entered
		 * @param defaultVal returned when key does not match.
		 * @return defaultVal returned when key doesn't match.
		 */
		public double getDouble(String key, double defaultVal){
			if(settings.containsKey(key)){
				return settings.get(key).doubleValue();
			}
			else{
				return defaultVal;
			}
		}
		/**
		 * Returns an integer based on the key that was entered.
		 * @param key
		 * @return integer
		 */
		public int getInteger(String key){
			return getInteger(key, 0);
		}
		/**
		 * Returns a double based on the key that was entered.
		 * @param key
		 * @return double
		 */
		public double getDouble(String key){
			return getDouble(key, 0.0d);
		}
		/**
		 * Checks whether or not the entered key is a double.
		 * @param key
		 * @return false if double
		 */
		public boolean isDouble(String key){
			return settings.containsKey(key)?(settings.get(key) instanceof Double):false;
		}

	}
	/**
	 * Adds a class to the spawnlist.
	 * @param entry
	 */
	public void addToSpawnList(AnimalEntry entry){
		spawnList.put(entry.getClazz(), entry);
	}
	/**
	 * Allows to add multiple classes to the spawnlist.
	 * @param entries
	 */
	public void addToSpawnList(AnimalEntry... entries){
		for(AnimalEntry entry: entries){
			addToSpawnList(entry);
		}
	}
	/**
	 * Returns the chances of grass growing.
	 * @return grassGrowthChance
	 */
	public double getGrassGrowthChance() {
		return grassGrowthChance;
	}
	/**
	 * Sets the chances of grass growing.
	 * @param grassGrowthChance
	 */
	public void setGrassGrowthChance(double grassGrowthChance) {
		this.grassGrowthChance = grassGrowthChance;
	}
	/**
	 * Returns a random seed that the simulator can use.
	 * @return random seed
	 */
	public int getRandomSeed() {
		return randomSeed;
	}
	/**
	 * Sets a random seed that the simulator can use.
	 * @param randomSeed
	 */
	public void setRandomSeed(int randomSeed) {
		this.randomSeed = randomSeed;
	}


}
