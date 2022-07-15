package api.hbm;

import java.util.HashSet;

/**
 * Notify an object that the time system has been updated, either by
 * command or if the object was unloaded for a while. For entities and
 * tile entities.
 * @author UFFR
 *
 */
public interface IHasAge
{
	public static final HashSet<IHasAge> registry = new HashSet<>();
	public static final int acceptableDelay = 12000;
	
	public static void updateAll()
	{
		for (IHasAge instance : registry)
			instance.incrementAge();
	}
	
	public default void register()
	{
		registry.add(this);
	}
	
	public void incrementAge();
	public void notifyTimeUpdated(Date newTime);
	public Date getInternalDate();
}
