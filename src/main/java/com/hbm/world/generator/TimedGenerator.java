package com.hbm.world.generator;

import java.util.ArrayList;
import java.util.HashMap;

import com.hbm.interfaces.Spaghetti;

import net.minecraft.world.World;

@Deprecated
@Spaghetti("this class should be destroyed")
public class TimedGenerator {
	
	private static final HashMap<Integer, ArrayList<ITimedJob>> operations = new HashMap();
	
	public static void automaton(World world, int amount) {

		ArrayList<ITimedJob> list = operations.get(world.provider.dimensionId);
		
		if(list == null)
			return;
		
		long start = System.currentTimeMillis();
		
		while(start + 10 > System.currentTimeMillis()) {
			
			if(list.isEmpty())
				return;
			
			ITimedJob entry = list.get(0);
			list.remove(0);
			
			entry.work();
		}
	}
	
	public static void addOp(World world, ITimedJob job) {
		
		ArrayList<ITimedJob> list = operations.get(world.provider.dimensionId);
		
		if(list == null) {
			list = new ArrayList();
			operations.put(world.provider.dimensionId, list);
		}
		
		list.add(job);
	}
	
	//should i be doing this? probably not, but watch me go
	public interface ITimedJob {
		
		public void work();
		
	}
}
