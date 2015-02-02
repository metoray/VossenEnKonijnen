package mirlexpat.voskonijn.logic;

/**
 * Provide a counter for a participant in the simulation.
 * This includes an identifying string and a count of how
 * many participants of this type currently exist within 
 * the simulation.
 * 
 * @author Patrick Breukelman, Lex Hermans, Mirko Rog
 * @version 1.0
 */
public class Counter
{
    // A name for this type of simulation participant
    private Class cls;
    // How many of this type exist in the simulation.
    private int count;

    /**
     * Provide a name for one of the simulation types.
     * @param cls The class for the animal counted
     */
    public Counter(Class cls)
    {
        this.cls = cls;
        count = 0;
    }
    
    /**
     * @return The short description of this type.
     */
    public String getName()
    {
        return cls.getSimpleName();
    }
    
    /**
     * @return The original class
     */
    public Class getClazz(){
    	return cls;
    }

    /**
     * @return The current count for this type.
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Increment the current count by one.
     */
    public void increment()
    {
        count++;
    }
    
    /**
     * Reset the current count to zero.
     */
    public void reset()
    {
        count = 0;
    }
}
