package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.TrappedBrick.Trap;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.TimedGenerator;
import com.hbm.world.generator.TimedGenerator.ITimedJob;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class JungleDungeonRoomWeakness extends JungleDungeonRoom {

	public JungleDungeonRoomWeakness(CellularDungeon parent) {
		super(parent);
	}

	public void generateMain(final World world, final int x, final int y, final int z) {
		super.generateMain(world, x, y, z);
		
		ITimedJob job = new ITimedJob() {

			@Override
			public void work() {

				
				for(int a = 1; a < 4; a++) {
					for(int b = 1; b < 4; b++) {
						
						if(world.rand.nextInt(2) == 0) {
							Block bl = world.getBlock(x + a, y, z + b);
							if(bl == ModBlocks.brick_jungle || bl == ModBlocks.brick_jungle_cracked || bl == ModBlocks.brick_jungle_lava) {
								world.setBlock(x + a, y, z + b, ModBlocks.brick_jungle_trap, Trap.WEAKNESS.ordinal(), 3);
							}
						}
					}
				}
			}
		};
		
		TimedGenerator.addOp(world, job);
	}
}
