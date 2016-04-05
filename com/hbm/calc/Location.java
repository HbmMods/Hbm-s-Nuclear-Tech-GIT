package com.hbm.calc;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Location {

	public int x;
	public int y;
	public int z;
	public World world;
	
	public Location(World world, int x, int y, int z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Location add(int xa, int ya, int za) {		
		return new Location(world, x + xa, y + ya, z + za);	
	}
	
	public Location add(ForgeDirection dir) {		
		return add(dir.offsetX, dir.offsetY, dir.offsetZ);	
	}
	
	public TileEntity getTileEntity() {
		return world.getTileEntity(x, y, z);
	}
	
}
