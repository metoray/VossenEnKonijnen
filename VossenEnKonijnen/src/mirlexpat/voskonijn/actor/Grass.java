package mirlexpat.voskonijn.actor;

import java.util.List;
import java.util.Random;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Location;
import mirlexpat.voskonijn.logic.Randomizer;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Grass extends Animal
{
	public Grass(boolean randomAge, Field field, Location location) {

		super(randomAge,field, location);

	}
	@Override
	public void act(List<Actor> newGrass)
	{
		incrementAge();
		if(isAlive()) {
			giveBirth(newGrass);
		}
	}
	protected Actor getNew(Field field, Location loc){
		return new Grass(false, field, loc);
	}

}
