package mirlexpat.voskonijn.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import mirlexpat.voskonijn.actor.Actor;
import mirlexpat.voskonijn.actor.Fox;
import mirlexpat.voskonijn.actor.Grass;
import mirlexpat.voskonijn.actor.Hunter;
import mirlexpat.voskonijn.actor.KomodoDragon;
import mirlexpat.voskonijn.actor.Rabbit;

public class FieldSettings implements Serializable {
	
	private static final long serialVersionUID = -3696332681664249116L;
	private int width, depth;
	private ArrayList<AnimalEntry> spawnList;
	
	public FieldSettings(){
		width = 120;
		depth = 80;
		spawnList = new ArrayList<AnimalEntry>();
		spawnList.add(new AnimalEntry(0.2,Fox.class) {
			
			@Override
			public Actor getActor(Field field, Location location) {
				return new Fox(true, field, location);
			}
		});
		spawnList.add(new AnimalEntry(0.1,Rabbit.class) {
			
			@Override
			public Actor getActor(Field field, Location location) {
				return new Rabbit(true, field, location);
			}
		});
		spawnList.add(new AnimalEntry(0.01,KomodoDragon.class) {
			
			@Override
			public Actor getActor(Field field, Location location) {
				return new KomodoDragon(true, field, location);
			}
		});
		spawnList.add(new AnimalEntry(0.030,Hunter.class) {
			
			@Override
			public Actor getActor(Field field, Location location) {
				return new Hunter(true, field, location);
			}
		});
		spawnList.add(new AnimalEntry(0.0,Grass.class) {
			
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

	public ArrayList<AnimalEntry> getSpawnList(){
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
    	private double chance;
    	private Class clazz;
		/**
		 * @param chance
		 * @param clazz
		 */
		public AnimalEntry(double chance, Class clazz) {
			super();
			this.chance = chance;
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
		
		public abstract Actor getActor(Field field, Location location);
    	
    }

}
