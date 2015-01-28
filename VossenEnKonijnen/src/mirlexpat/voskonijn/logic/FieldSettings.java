package mirlexpat.voskonijn.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import mirlexpat.voskonijn.actor.Actor;

public class FieldSettings implements Serializable {
	
	private static final long serialVersionUID = -3696332681664249116L;
	private int width, depth;
	private ArrayList<AnimalEntry> spawnList;
	
	public FieldSettings(){
		width = 120;
		depth = 80;
		spawnList = new ArrayList<AnimalEntry>();
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
