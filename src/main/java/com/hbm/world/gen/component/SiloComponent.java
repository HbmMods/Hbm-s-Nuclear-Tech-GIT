package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent.BlockSelector;
import net.minecraftforge.common.util.ForgeDirection;

public class SiloComponent extends Component {
	
	public SiloComponent() {
		
	}
	
	public SiloComponent(Random rand, int minX, int minY, int minZ) {
		super(rand, minX, minY, minZ, 42, 29, 26);
	}
	
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
		
		//TODO add method to only count the surface portion for height offset
		/*if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
			return false;
		}*/
		
		//alright kids, we're adding the largest single-component mapgen structure in ntm, 2x3 chunks. what do?
		//you break it into sections instead of doing the whole thing at once. adding it that is, the server won't die
		//from checking a lot of small switches and if statements in some for loops i hope...
		
		/* SURFACE */
		//Floor
		fillWithBlocks(world, box, 13, 25, 2, 42, 25, 4, ModBlocks.asphalt); //it's okay to cut corners (vanilla does it all the time!), but
		fillWithBlocks(world, box, 13, 25, 5, 34, 25, 9, ModBlocks.asphalt); //being pretty careful trades unnecessary setBlocks for some extra lines.
		fillWithBlocks(world, box, 13, 25, 10, 14, 25, 18, ModBlocks.asphalt);
		fillWithBlocks(world, box, 24, 25, 10, 35, 25, 12, ModBlocks.asphalt);
		fillWithBlocks(world, box, 24, 25, 13, 26, 25, 18, ModBlocks.asphalt);
		fillWithBlocks(world, box, 13, 25, 19, 42, 25, 20, ModBlocks.asphalt);
		fillWithBlocks(world, box, 40, 25, 5, 42, 25, 18, ModBlocks.asphalt);
		fillWithBlocks(world, box, 39, 25, 10, 39, 25, 12, ModBlocks.asphalt); 
		fillWithMetadataBlocks(world, box, 15, 25, 10, 23, 25, 10, ModBlocks.concrete_colored_ext, 5);
		fillWithMetadataBlocks(world, box, 15, 25, 11, 15, 25, 17, ModBlocks.concrete_colored_ext, 5);
		fillWithMetadataBlocks(world, box, 15, 25, 18, 23, 25, 18, ModBlocks.concrete_colored_ext, 5);
		fillWithMetadataBlocks(world, box, 23, 25, 11, 23, 25, 17, ModBlocks.concrete_colored_ext, 5);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 5, 16, 25, 11, box); //it's figuring out meta that makes you shoot yourself anyway
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 5, 22, 25, 11, box);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 5, 16, 25, 17, box);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 5, 22, 25, 17, box);
		
		ConcreteBricks ConcreteBricks = new ConcreteBricks();
		
		fillWithRandomizedBlocks(world, box, 27, 25, 13, 39, 25, 18, rand, ConcreteBricks);
		fillWithBlocks(world, box, 36, 25, 4, 38, 25, 4, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 35, 25, 5, 39, 25, 9, ModBlocks.concrete_smooth);
		//Fences
		fillWithBlocks(world, box, 13, 26, 2, 13, 28, 2, ModBlocks.deco_steel);
		fillWithBlocks(world, box, 42, 26, 2, 42, 28, 2, ModBlocks.deco_steel);
		fillWithBlocks(world, box, 13, 26, 20, 13, 28, 20, ModBlocks.deco_steel);
		fillWithBlocks(world, box, 42, 26, 20, 42, 28, 20, ModBlocks.deco_steel);
		//N-facing
		fillWithBlocks(world, box, 38, 26, 2, 41, 27, 2, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 34, 26, 2, 36, 27, 2, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 30, 26, 2, 31, 27, 2, ModBlocks.fence_metal);
		placeBlockAtCurrentPosition(world, ModBlocks.fence_metal, 0, 28, 27, 2, box);
		fillWithBlocks(world, box, 22, 26, 2, 28, 26, 2, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 23, 27, 2, 26, 27, 2, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 18, 26, 2, 20, 26, 2, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 14, 26, 2, 16, 26, 2, ModBlocks.fence_metal);
		placeBlockAtCurrentPosition(world, ModBlocks.fence_metal, 0, 14, 27, 2, box);
		fillWithMetadataBlocks(world, box, 38, 28, 2, 41, 28, 2, ModBlocks.barbed_wire, 5);
		fillWithMetadataBlocks(world, box, 35, 28, 2, 36, 28, 2, ModBlocks.barbed_wire, 5);
		fillWithMetadataBlocks(world, box, 23, 28, 2, 25, 28, 2, ModBlocks.barbed_wire, 5);
		placeBlockAtCurrentPosition(world, ModBlocks.barbed_wire, 5, 14, 28, 2, box);
		//W-facing
		fillWithBlocks(world, box, 13, 26, 3, 13, 27, 4, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 13, 26, 5, 13, 26, 6, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 13, 26, 9, 13, 27, 9, ModBlocks.fence_metal);
		placeBlockAtCurrentPosition(world, ModBlocks.fence_metal, 0, 13, 26, 11, box);
		fillWithBlocks(world, box, 13, 26, 12, 13, 27, 19, ModBlocks.fence_metal);
		fillWithMetadataBlocks(world, box, 13, 28, 3, 13, 28, 4, ModBlocks.barbed_wire, 2);
		fillWithMetadataBlocks(world, box, 13, 28, 15, 13, 28, 19, ModBlocks.barbed_wire, 2);
		//E-facing
		fillWithBlocks(world, box, 42, 26, 3, 42, 27, 4, ModBlocks.fence_metal);
		placeBlockAtCurrentPosition(world, ModBlocks.fence_metal, 0, 42, 26, 7, box);
		fillWithBlocks(world, box, 42, 26, 9, 42, 26, 12, ModBlocks.fence_metal);
		placeBlockAtCurrentPosition(world, ModBlocks.fence_metal, 0, 42, 26, 14, box);
		fillWithBlocks(world, box, 42, 26, 15, 42, 27, 19, ModBlocks.fence_metal);
		fillWithMetadataBlocks(world, box, 42, 28, 3, 42, 28, 4, ModBlocks.barbed_wire, 3);
		fillWithMetadataBlocks(world, box, 42, 28, 15, 42, 28, 19, ModBlocks.barbed_wire, 3);
		//S-facing
		fillWithBlocks(world, box, 14, 26, 20, 17, 27, 20, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 18, 26, 20, 22, 26, 20, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 20, 27, 20, 21, 27, 20, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 24, 26, 20, 25, 26, 20, ModBlocks.fence_metal);
		placeBlockAtCurrentPosition(world, ModBlocks.fence_metal, 0, 27, 26, 20, box);
		fillWithBlocks(world, box, 29, 26, 20, 32, 27, 20, ModBlocks.fence_metal);
		placeBlockAtCurrentPosition(world, ModBlocks.fence_metal, 0, 33, 26, 20, box);
		fillWithBlocks(world, box, 35, 26, 20, 37, 26, 20, ModBlocks.fence_metal);
		placeBlockAtCurrentPosition(world, ModBlocks.fence_metal, 0, 36, 27, 20, box);
		placeBlockAtCurrentPosition(world, ModBlocks.fence_metal, 0, 39, 26, 20, box);
		fillWithBlocks(world, box, 40, 26, 20, 41, 27, 20, ModBlocks.fence_metal);
		fillWithMetadataBlocks(world, box, 14, 28, 20, 17, 28, 20, ModBlocks.barbed_wire, 4);
		fillWithMetadataBlocks(world, box, 29, 28, 20, 32, 28, 20, ModBlocks.barbed_wire, 4);
		fillWithMetadataBlocks(world, box, 40, 28, 20, 41, 28, 20, ModBlocks.barbed_wire, 4);
		
		//Defense Platforms
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 27, 26, 13, box);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 32, 26, 13, box);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 27, 26, 18, box);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 32, 26, 18, box);
		fillWithRandomizedBlocks(world, box, 28, 26, 14, 31, 26, 17, rand, ConcreteBricks);
		
		ConcreteStairs ConcreteStairs = new ConcreteStairs();
		
		int stairW = this.getStairMeta(0);
		int stairE = this.getStairMeta(1);
		int stairN = this.getStairMeta(2);
		int stairS = this.getStairMeta(3);
		
		ConcreteStairs.setMetadata(stairN);
		fillWithRandomizedBlocks(world, box, 28, 26, 13, 31, 26, 13, rand, ConcreteStairs);
		ConcreteStairs.setMetadata(stairW);
		fillWithRandomizedBlocks(world, box, 27, 26, 14, 27, 26, 17, rand, ConcreteStairs);
		ConcreteStairs.setMetadata(stairS);
		fillWithRandomizedBlocks(world, box, 28, 26, 18, 31, 26, 18, rand, ConcreteStairs);
		fillWithMetadataBlocks(world, box, 27, 27, 13, 32, 27, 13, ModBlocks.concrete_slab, 1);
		fillWithMetadataBlocks(world, box, 27, 27, 14, 27, 27, 17, ModBlocks.concrete_slab, 1);
		fillWithMetadataBlocks(world, box, 27, 27, 18, 32, 27, 18, ModBlocks.concrete_slab, 1);
		fillWithMetadataBlocks(world, box, 32, 27, 14, 32, 27, 17, ModBlocks.concrete_slab, 1);
		//Methusalem
		placeBlockAtCurrentPosition(world, ModBlocks.turret_howard_damaged, 12, 29, 27, 15, box);
		fillSpace(world, box, 29, 27, 15, new int[] { 0, 0, 1, 0, 1, 0 }, ModBlocks.turret_howard_damaged, ForgeDirection.NORTH);
		//Destroyed platform
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 34, 26, 13, box);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 39, 26, 13, box);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 34, 26, 18, box);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 39, 26, 18, box);
		fillWithRandomizedBlocks(world, box, 35, 26, 13, 38, 26, 13, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 32, 26, 15, 34, 26, 17, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 35, 26, 18, 38, 26, 18, rand, ConcreteStairs);
		ConcreteStairs.setMetadata(stairE);
		fillWithRandomizedBlocks(world, box, 39, 26, 14, 39, 26, 15, rand, ConcreteStairs);
		
		DestroyedBricks DestroyedBricks = new DestroyedBricks(); //it's funny cause i'm probably gonna use this like once in this entire thing
		
		fillWithRandomizedBlocks(world, box, 35, 26, 14, 38, 26, 17, rand, DestroyedBricks); //destroyed layer
		fillWithMetadataBlocks(world, box, 33, 27, 15, 33, 27, 17, ModBlocks.concrete_slab, 1);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 1, 34, 27, 17, box);
		fillWithMetadataBlocks(world, box, 34, 27, 18, 36, 27, 18, ModBlocks.concrete_slab, 1);
		fillWithMetadataBlocks(world, box, 37, 27, 13, 39, 27, 13, ModBlocks.concrete_slab, 1);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 1, 39, 27, 14, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 37, 25, 15, box); //deco
		placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_rim_rusted, 0, 37, 26, 15, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 36, 25, 16, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_quad_rusted, 0, 36, 26, 16, box);
		placeBlockAtCurrentPosition(world, Blocks.chest, 2, 36, 26, 17, box); //TODO move containers to one place per section
		
		//Access Building (staircase not included)
		fillWithRandomizedBlocks(world, box, 35, 26, 5, 39, 28, 5, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 35, 26, 6, 35, 28, 9, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 39, 26, 6, 39, 28, 9, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 36, 26, 10, 38, 28, 10, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 36, 27, 11, 38, 27, 11, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 36, 26, 12, 38, 26, 12, rand, ConcreteBricks);
		ConcreteStairs.setMetadata(stairS);
		fillWithRandomizedBlocks(world, box, 36, 28, 11, 38, 28, 11, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 36, 27, 12, 38, 27, 12, rand, ConcreteStairs);
		fillWithBlocks(world, box, 36, 29, 5, 38, 29, 9, ModBlocks.concrete); //Roof
		fillWithBlocks(world, box, 35, 29, 5, 35, 29, 9, ModBlocks.concrete_stairs);
		fillWithMetadataBlocks(world, box, 36, 29, 10, 38, 29, 10, ModBlocks.concrete_stairs, 3);
		fillWithMetadataBlocks(world, box, 39, 29, 5, 39, 29, 9, ModBlocks.concrete_stairs, 1);
		//Deco
		placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, 35, 27, 7, box);
		placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, 39, 27, 7, box);
		placeDoor(world, box, ModBlocks.door_metal, 1, false, rand.nextBoolean(), 37, 26, 5);
		
		//Stuff not-bolted down
		//Tent
		for(int j = 4; j <= 8; j += 2) {
			placeBlockAtCurrentPosition(world, ModBlocks.steel_beam, 2, 20, 26, j, box);
			fillWithMetadataBlocks(world, box, 16, 26, j, 16, 27, j, ModBlocks.steel_beam, 3);
		}
		
		fillWithBlocks(world, box, 16, 28, 4, 17, 28, 8, ModBlocks.brick_slab);
		fillWithMetadataBlocks(world, box, 18, 27, 4, 19, 27, 8, ModBlocks.brick_slab, 8);
		fillWithBlocks(world, box, 20, 27, 4, 20, 27, 8, ModBlocks.brick_slab);
		fillWithMetadataBlocks(world, box, 16, 28, 6, 17, 28, 6, ModBlocks.brick_slab, 5);
		fillWithMetadataBlocks(world, box, 18, 27, 6, 19, 27, 6, ModBlocks.brick_slab, 13);
		placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 5, 20, 27, 6, box);
		//Supplies
		//TODO make another block selector for this so it's randomized n shit
		//Wreckage
		//TODO ditto
		
		//Large Silo Hatch
		placeBlockAtCurrentPosition(world, ModBlocks.silo_hatch_large, 12, 19, 26, 14, box);
		fillSpace(world, box, 19, 26, 14, new int[] { 0, 0, 3, 3, 3, 3 }, ModBlocks.silo_hatch_large, ForgeDirection.SOUTH);
		
		
		return true;
	}
	
	public static class ConcreteStairs extends BlockSelector {
		
		public void setMetadata(int meta) {
			this.selectedBlockMetaData = meta;
		}
		
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
			float chance = rand.nextFloat();
			
			if(chance < 0.4F)
				this.field_151562_a = ModBlocks.brick_concrete_stairs;
			else if (chance < 0.7F)
				this.field_151562_a = ModBlocks.brick_concrete_mossy_stairs;
			else if (chance < 0.9F)
				this.field_151562_a = ModBlocks.brick_concrete_cracked_stairs;
			else
				this.field_151562_a = ModBlocks.brick_concrete_broken_stairs;
			
		}
	}
	
	public static class DestroyedBricks extends BlockSelector {
		
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
			this.selectedBlockMetaData = 0;
			float chance = rand.nextFloat();
			
			if(chance < 0.3F) {
				this.field_151562_a = ModBlocks.concrete_brick_slab;
				chance = rand.nextFloat();
				
				if (chance >= 0.4F && chance < 0.7F)
					this.selectedBlockMetaData |= 1;
				else if (chance < 0.9F)
					this.selectedBlockMetaData |= 2;
				else 
					this.selectedBlockMetaData |= 3;
				
			} else if(chance < 0.6F) {
				this.selectedBlockMetaData = rand.nextInt(4);
				chance = rand.nextFloat();
				
				if(chance < 0.4F)
					this.field_151562_a = ModBlocks.brick_concrete_stairs;
				else if (chance < 0.7F)
					this.field_151562_a = ModBlocks.brick_concrete_mossy_stairs;
				else if (chance < 0.9F)
					this.field_151562_a = ModBlocks.brick_concrete_cracked_stairs;
				else
					this.field_151562_a = ModBlocks.brick_concrete_broken_stairs;
				
			} else if(chance < 0.9F) {
				chance = rand.nextFloat();
				
				if(chance < 0.4F)
					this.field_151562_a = ModBlocks.brick_concrete;
				else if (chance < 0.7F)
					this.field_151562_a = ModBlocks.brick_concrete_mossy;
				else if (chance < 0.9F)
					this.field_151562_a = ModBlocks.brick_concrete_cracked;
				else
					this.field_151562_a = ModBlocks.brick_concrete_broken;
				
			} else
				this.field_151562_a = Blocks.air;
		}
	}
}
