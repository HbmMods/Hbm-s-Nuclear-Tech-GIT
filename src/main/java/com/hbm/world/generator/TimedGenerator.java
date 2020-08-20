package com.hbm.world.generator;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class TimedGenerator {
	
	//TODO: replace with timed operations, allows not only for direct block placements but also snazzy conditioned stuff
	private static final HashMap<Integer, ArrayList<Object[]>> operations = new HashMap();
	
	public static void automaton(World world, int amount) {

		ArrayList<Object[]> list = operations.get(world.provider.dimensionId);
		
		if(list == null)
			return;
		
		long start = System.currentTimeMillis();
		
		while(start + 15 > System.currentTimeMillis()) {
			
			if(list.isEmpty())
				return;
			
			Object[] entry = list.get(0);
			list.remove(0);
			
			world.setBlock((Integer)entry[0], (Integer)entry[1], (Integer)entry[2], (Block)entry[3], (Integer)entry[4], (Integer)entry[5]);
			
			//amount --;
		}
	}
	
	public static void addOp(World world, int x, int y, int z, Block b, int meta, int flags) {
		
		ArrayList<Object[]> list = operations.get(world.provider.dimensionId);
		
		if(list == null) {
			list = new ArrayList();
			operations.put(world.provider.dimensionId, list);
		}
		
		list.add(new Object[] {x, y, z, b, meta, flags});
	}
}
