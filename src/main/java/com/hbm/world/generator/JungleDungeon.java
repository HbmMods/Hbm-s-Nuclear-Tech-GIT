package com.hbm.world.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.NotableComments;
import com.hbm.inventory.RecipesCommon.MetaBlock;
import com.hbm.world.generator.TimedGenerator.ITimedJob;

import net.minecraft.world.World;

@Deprecated
@NotableComments
public class JungleDungeon extends CellularDungeon {
	
	public boolean hasHole = false;

	public JungleDungeon(int width, int height, int dimX, int dimZ, int tries, int branches) {
		super(width, height, dimX, dimZ, tries, branches);

		this.floor.add(new MetaBlock(ModBlocks.brick_jungle));
		this.floor.add(new MetaBlock(ModBlocks.brick_jungle_cracked));
		
		for(int i = 0; i < 50; i++) {
			this.wall.add(new MetaBlock(ModBlocks.brick_jungle));
			this.wall.add(new MetaBlock(ModBlocks.brick_jungle_cracked));
		}
		for(int i = 0; i < 16; i++) {
			this.wall.add(new MetaBlock(ModBlocks.brick_jungle_glyph, i));
		}
		
		this.ceiling.add(new MetaBlock(ModBlocks.brick_jungle));
		this.ceiling.add(new MetaBlock(ModBlocks.brick_jungle_cracked));
	}
	
	public void generate(final World world, final int x, final int y, final int z, final Random rand) {
		super.generate(world, x, y, z, rand);
		
		TimedGenerator.addOp(world, new ITimedJob() {
			@Override
			public void work() {
				
				JungleDungeon that = JungleDungeon.this;
				
				//A hole has not been made -> this is the bottom floor
				if(!that.hasHole) {
					
					List<int[]> rooms = new ArrayList();
					
					for(int i = 0; i < that.cells.length; i++) {
						for(int j = 0; j < that.cells[0].length; j++) {
							
							if(that.cells[i][j] != null)
								rooms.add(new int[]{i, j});
						}
					}
					
					if(!rooms.isEmpty()) {

						int ix = x - dimX * width / 2;
						int iz = z - dimZ * width / 2;
						
						int[] room = rooms.get(world.rand.nextInt(rooms.size()));
						world.setBlock(ix + room[0] * (width - 1) + width / 2, y, iz + room[1] * (width - 1) + width / 2, ModBlocks.brick_jungle_circle);
					}
				}
				
				that.hasHole = false;
			}
		} );
		
		//since all the building is timed jobs, this has to be as well. timed jobs are ordered so this works!
		//is it shitty coding? is it not? who knows?
		//future bob here: yes it is, go fuck yourself
	}

}
