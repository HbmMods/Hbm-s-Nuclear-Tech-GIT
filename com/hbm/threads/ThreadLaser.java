package com.hbm.threads;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ThreadLaser implements Runnable {
	
	public Thread thread;
	public boolean running = false;
	public int x;
	public int y;
	public int z;
	public World world;
	public String direction;
	public int age;
	
	public ThreadLaser(World world, int x, int y, int z, String direction) {
		thread = new Thread(this);
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
		this.direction = direction;
		//MinecraftForge.EVENT_BUS.register(this);
		//FMLCommonHandler.instance().bus().register(this);
	}
	
	public void start() {
		running = true;
		if(running)
		{
			thread.start();
		}
	}
	
	public void stop() {
		running = false;
		thread.stop();
	}

	@Override
	public void run() {
		while(running)
		{
			if(direction == "north")
			{
				for(int i = -1; i < 2; i++)
				{
					for(int j = -1; j < 2; j++)
					{
						world.setBlock(x, y + i, z + j, Blocks.air);
					}
				}
				
				x += 1;
			}
			
			if(!(world.checkChunksExist(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1)))
				stop();
			
			
			try {
				age++;
				thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
