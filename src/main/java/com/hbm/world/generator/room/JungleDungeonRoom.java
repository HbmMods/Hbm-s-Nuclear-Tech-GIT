package com.hbm.world.generator.room;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.MetaBlock;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;
import com.hbm.world.generator.JungleDungeon;
import com.hbm.world.generator.TimedGenerator;
import com.hbm.world.generator.TimedGenerator.ITimedJob;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class JungleDungeonRoom extends CellularDungeonRoom {

	public JungleDungeonRoom(CellularDungeon parent) {
		super(parent);
	}

	public void generateMain(final World world, final int x, final int y, final int z) {
		
		if(!(this.parent instanceof JungleDungeon))
			return; //just to be safe
		
		ITimedJob job = new ITimedJob() {

			@Override
			public void work() {
				
				DungeonToolbox.generateBox(world, x, y, z, parent.width, 1, parent.width, parent.floor);
				DungeonToolbox.generateBox(world, x, y + 1, z, parent.width, parent.height - 1, parent.width, Blocks.air);
				DungeonToolbox.generateBox(world, x, y + parent.height - 1, z, parent.width, 1, parent.width, parent.ceiling);
				
				int rtd = world.rand.nextInt(50);
				
				// 1:10 chance to have a lava floor
				if(rtd < 5) {
					List<MetaBlock> metas = new ArrayList() {{
						add(new MetaBlock(ModBlocks.brick_jungle_cracked));
						add(new MetaBlock(ModBlocks.brick_jungle_lava));
						add(new MetaBlock(ModBlocks.brick_jungle_lava));
					}};
					
					DungeonToolbox.generateBox(world, x + parent.width / 2 - 1, y, z + parent.width / 2 - 1, 3, 1, 3, metas );
				
				// 1:5 chance to have a jungle crate
				} else if(rtd < 10) {
					world.setBlock(x + 1 + world.rand.nextInt(parent.width - 1), y + 1, z + world.rand.nextInt(parent.width - 1), ModBlocks.crate_jungle, 0, 2);

				// 1:5 chance to try for making a hole
				} else if(rtd < 20) {
					
					if(!((JungleDungeon)JungleDungeonRoom.this.parent).hasHole) {
						
						boolean punched = false;
						
						for(int a = 0; a < 3; a++) {
							for(int b = 0; b < 3; b++) {
								
								Block bl = world.getBlock(x + 1 + a, y - 4, z + 1 + b);
								
								if(world.getBlock(x + 1 + a, y - 1, z + 1 + b) == Blocks.air) {
									if(bl == ModBlocks.brick_jungle || bl == ModBlocks.brick_jungle_cracked || bl == ModBlocks.brick_jungle_lava || bl == ModBlocks.brick_jungle_trap) {
										world.setBlock(x + 1 + a, y, z + 1 + b, ModBlocks.brick_jungle_fragile);
										punched = true;
									}
								}
							}
						}
						
						if(punched)
							((JungleDungeon)JungleDungeonRoom.this.parent).hasHole = true;
					}
				}
			}
		};
		
		TimedGenerator.addOp(world, job);
	}
	
	public void generateWall(final World world, final int x, final int y, final int z, final ForgeDirection wall, final boolean door) {
		
		ITimedJob job = new ITimedJob() {

			@Override
			public void work() {
				
				if(wall == ForgeDirection.NORTH) {
					DungeonToolbox.generateBox(world, x, y + 1, z, parent.width, parent.height - 2, 1, parent.wall);
					
					if(door)
						DungeonToolbox.generateBox(world, x + parent.width / 2 - 1, y + 1, z, 3, 3, 1, Blocks.air);
				}
				
				if(wall == ForgeDirection.SOUTH) {
					DungeonToolbox.generateBox(world, x, y + 1, z + parent.width - 1, parent.width, parent.height - 2, 1, parent.wall);
					
					if(door)
						DungeonToolbox.generateBox(world, x + parent.width / 2 - 1, y + 1, z + parent.width - 1, 3, 3, 1, Blocks.air);
				}
				
				if(wall == ForgeDirection.WEST) {
					DungeonToolbox.generateBox(world, x, y + 1, z, 1, parent.height - 2, parent.width, parent.wall);
					
					if(door)
						DungeonToolbox.generateBox(world, x, y + 1, z + parent.width / 2 - 1, 1, 3, 3, Blocks.air);
				}
				
				if(wall == ForgeDirection.EAST) {
					DungeonToolbox.generateBox(world, x + parent.width - 1, y + 1, z, 1, parent.height - 2, parent.width, parent.wall);
					
					if(door)
						DungeonToolbox.generateBox(world, x + parent.width - 1, y + 1, z + parent.width / 2 - 1, 1, 3, 3, Blocks.air);
				}
			}
		};
		
		TimedGenerator.addOp(world, job);
	}
}
