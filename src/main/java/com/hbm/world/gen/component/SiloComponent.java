package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.itempool.ItemPool;
import com.hbm.itempool.ItemPoolsComponent;
import com.hbm.itempool.ItemPoolsLegacy;
import com.hbm.itempool.ItemPoolsSingle;
import com.hbm.items.ModItems;
import com.hbm.tileentity.bomb.TileEntityLandmine;
import com.hbm.tileentity.bomb.TileEntityLaunchPadRusted;
import com.hbm.tileentity.network.TileEntityRadioTorchBase;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.util.ForgeDirection;

public class SiloComponent extends Component {
	
	public int freq = 0; //frequency of RTTY torches, this is the easiest way to sync them up.
	public int freqHatch = 0;
	
	public SiloComponent() {
		
	}
	
	public SiloComponent(Random rand, int minX, int minZ) {
		super(rand, minX, 64, minZ, 42, 29, 26);
		this.freq = rand.nextInt(); //so other silos won't conflict, hopefully
		this.freqHatch = rand.nextInt();
	}
	
	/** Set to NBT */
	protected void func_143012_a(NBTTagCompound nbt) {
		super.func_143012_a(nbt);
		nbt.setInteger("freq", freq);
		nbt.setInteger("freqHatch", freqHatch);
	}
	
	/** Get from NBT */
	protected void func_143011_b(NBTTagCompound nbt) {
		super.func_143011_b(nbt);
		this.freq = nbt.getInteger("freq");
		this.freqHatch = nbt.getInteger("freqHatch");
	}
	
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
		//seems to work
		if(this.hpos == -1) {
			StructureBoundingBox area = getRotatedBoundingBox(getXWithOffset(13, 2), getYWithOffset(25), getZWithOffset(13, 2), 29, 3, 18); //anchor offset/world pos already accounted for with offset methods
			this.hpos = this.getAverageHeight(world, area, box, getYWithOffset(25));
			this.boundingBox.offset(0, this.hpos - 1 - getYWithOffset(25), 0);
		}
		
		/*if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
			return false;
		}*/
		
		//alright kids, we're adding the largest single-component mapgen structure in ntm, 2x3 chunks. what do?
		//you break it into sections instead of doing the whole thing at once. adding it that is, the server won't die
		//from checking a lot of small switches and if statements in some for loops i hope...
		
		/* SURFACE */
		fillWithAir(world, box, 13, 26, 2, 42, 36, 20);
		
		placeFoundationUnderneath(world, ModBlocks.concrete_colored_ext, 0, 13, 2, 42, 20, 24, box);
		
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
		placeCore(world, box, ModBlocks.turret_howard_damaged, ForgeDirection.SOUTH, 29, 27, 15);
		fillSpace(world, box, 29, 27, 15, new int[] { 0, 0, 1, 0, 1, 0 }, ModBlocks.turret_howard_damaged, ForgeDirection.SOUTH);
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
		
		//Access Building (staircase not included)
		fillWithRandomizedBlocks(world, box, 35, 26, 5, 39, 28, 5, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 35, 26, 6, 35, 28, 9, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 39, 26, 6, 39, 28, 9, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 36, 26, 9, 38, 28, 10, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 36, 27, 11, 38, 27, 11, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 36, 26, 12, 38, 26, 12, rand, ConcreteBricks);
		ConcreteStairs.setMetadata(stairS);
		fillWithRandomizedBlocks(world, box, 36, 28, 11, 38, 28, 11, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 36, 27, 12, 38, 27, 12, rand, ConcreteStairs);
		fillWithBlocks(world, box, 36, 29, 5, 38, 29, 9, ModBlocks.concrete); //Roof
		fillWithMetadataBlocks(world, box, 35, 29, 5, 35, 29, 9, ModBlocks.concrete_stairs, stairW);
		fillWithMetadataBlocks(world, box, 36, 29, 10, 38, 29, 10, ModBlocks.concrete_stairs, stairS);
		fillWithMetadataBlocks(world, box, 39, 29, 5, 39, 29, 9, ModBlocks.concrete_stairs, stairE);
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
		SiloSupplies Supplies = new SiloSupplies();
		
		fillWithRandomizedBlocks(world, box, 27, 26, 7, 29, 26, 9, rand, Supplies);
		fillWithRandomizedBlocks(world, box, 17, 26, 4, 19, 26, 8, rand, Supplies);
		//Wreckage
		//not really worth the effort to make yet another selector
		placeBlockAtCurrentPosition(world, ModBlocks.barrel_corroded, 0, 32, 26, 5, box);
		fillWithRandomizedBlocks(world, box, 32, 26, 7, 32, 26, 7, rand, DestroyedBricks);
		placeBlockAtCurrentPosition(world, ModBlocks.barrel_corroded, 0, 31, 26, 9, box);
		fillWithRandomizedBlocks(world, box, 31, 26, 11, 32, 26, 11, rand, DestroyedBricks);
		fillWithRandomizedBlocks(world, box, 34, 26, 11, 34, 26, 11, rand, DestroyedBricks);
		fillWithRandomizedBlocks(world, box, 41, 26, 17, 41, 26, 17, rand, DestroyedBricks);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 1, 37, 26, 19, box);
		
		//Large Silo Hatch
		placeCore(world, box, ModBlocks.silo_hatch_large, ForgeDirection.SOUTH, 19, 26, 14);
		fillSpace(world, box, 19, 26, 14, new int[] { 0, 0, 3, 3, 3, 3 }, ModBlocks.silo_hatch_large, ForgeDirection.SOUTH);
		placeBlockAtCurrentPosition(world, ModBlocks.radio_torch_receiver, 1, 16, 25, 17, box);
		setRTTYFreq(world, box, 16, 25, 17, freqHatch);
		
		//Containers
		generateInvContents(world, box, rand, Blocks.chest, 2, 36, 26, 17, ItemPool.getPool(ItemPoolsLegacy.POOL_VERTIBIRD), 5);
		
		/* Stairway */
		fillWithAir(world, box, 37, 26, 9, 37, 27, 10);
		placeBlockAtCurrentPosition(world, Blocks.air, 11, 37, 25, 10, box);
		fillWithAir(world, box, 37, 24, 11, 37, 26, 11);
		fillWithAir(world, box, 37, 23, 12, 37, 25, 12);
		fillWithAir(world, box, 37, 21, 13, 37, 24, 14);
		//bottoms
		for(int i = 0; i < 5; i++) {
			fillWithRandomizedBlocks(world, box, 36, 24 - i, 9 + i, 38, 24 - i, 9 + i, rand, ConcreteBricks);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairS, 37, 25 - i, 9 + i, box);
		}
		
		//walls
		for(int i = 36; i <= 38; i += 2) {
			fillWithRandomizedBlocks(world, box, i, 26, 11, i, 26, 11, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, i, 25, 10, i, 25, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, i, 24, 10, i, 24, 15, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, i, 23, 11, i, 23, 15, rand, ConcreteBricks);
			fillWithMetadataBlocks(world, box, i, 22, 12, i, 22, 15, ModBlocks.concrete_colored, 11);
			fillWithRandomizedBlocks(world, box, i, 21, 13, i, 21, 15, rand, ConcreteBricks);
		}
		
		fillWithBlocks(world, box, 36, 20, 14, 38, 20, 15, ModBlocks.concrete_smooth);
		fillWithAir(world, box, 36, 21, 14, 36, 22, 14);
		
		/* Blue Control Room */
		//Air
			placeBlockAtCurrentPosition(world, Blocks.air, 0, 36, 23, 17, box);
			fillWithAir(world, box, 34, 21, 13, 35, 23, 19);
			fillWithAir(world, box, 33, 21, 13, 33, 23, 15);
			fillWithAir(world, box, 29, 21, 16, 31, 23, 19);
			fillWithAir(world, box, 29, 21, 12, 32, 23, 15);
			fillWithAir(world, box, 28, 21, 10, 32, 23, 11);
			fillWithAir(world, box, 27, 21, 7, 31, 23, 9);
			fillWithAir(world, box, 27, 21, 5, 30, 23, 6);
			fillWithAir(world, box, 27, 21, 4, 29, 23, 4);
			fillWithAir(world, box, 27, 21, 3, 28, 23, 3);
			fillWithAir(world, box, 26, 22, 7, 26, 23, 8);
			fillWithAir(world, box, 25, 22, 7, 25, 23, 7);
			fillWithAir(world, box, 24, 21, 2, 26, 23, 6);
			fillWithAir(world, box, 22, 21, 5, 23, 23, 5);
			fillWithAir(world, box, 16, 21, 1, 23, 23, 4);
		//Floor and Ceiling
		for(int i = 20; i <= 24; i += 4) {
			fillWithBlocks(world, box, 15, i, 0, 23, i, 5, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 24, i, 1, 26, i, 6, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 25, i, 7, 26, i, 7, ModBlocks.concrete_smooth);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 26, i, 8, box);
			fillWithBlocks(world, box, 27, i, 2, 28, i, 6, ModBlocks.concrete_smooth);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 29, i, 3, box);
			fillWithBlocks(world, box, 29, i, 4, 30, i, 4, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 29, i, 5, 31, i, 6, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 27, i, 7, 32, i, 9, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 28, i, 10, 33, i, 20, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 34, i, 12, 35, i, 15, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 34, i, 16, 37, i, 20, ModBlocks.concrete_smooth);
		}
		//Walls
		//Curve (N-facing)
		for(int i = 21; i <= 23; i += 2) {
			fillWithRandomizedBlocks(world, box, 15, i, 0, 23, i, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 24, i, 1, 26, i, 1, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 27, i, 2, 28, i, 2, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 29, i, 3, 29, i, 3, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 30, i, 4, 30, i, 4, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 31, i, 5, 31, i, 6, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 32, i, 7, 32, i, 9, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 33, i, 10, 33, i, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 34, i, 12, 35, i, 12, rand, ConcreteBricks);
		}
		
		fillWithMetadataBlocks(world, box, 15, 22, 0, 23, 22, 0, ModBlocks.concrete_colored, 11);
		fillWithMetadataBlocks(world, box, 24, 22, 1, 26, 22, 1, ModBlocks.concrete_colored, 11);
		fillWithMetadataBlocks(world, box, 27, 22, 2, 28, 22, 2, ModBlocks.concrete_colored, 11);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored, 11, 29, 22, 3, box);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored, 11, 30, 22, 4, box);
		fillWithMetadataBlocks(world, box, 31, 22, 5, 31, 22, 6, ModBlocks.concrete_colored, 11);
		fillWithMetadataBlocks(world, box, 32, 22, 7, 32, 22, 9, ModBlocks.concrete_colored, 11);
		fillWithMetadataBlocks(world, box, 33, 22, 10, 33, 22, 12, ModBlocks.concrete_colored, 11);
		fillWithMetadataBlocks(world, box, 34, 22, 12, 35, 22, 12, ModBlocks.concrete_colored, 11);
		//W-facing side
		fillWithRandomizedBlocks(world, box, 15, 21, 1, 15, 21, 4, rand, ConcreteBricks);
		fillWithMetadataBlocks(world, box, 15, 22, 1, 15, 22, 4, ModBlocks.concrete_colored, 11);
		fillWithRandomizedBlocks(world, box, 15, 23, 1, 15, 23, 4, rand, ConcreteBricks);
		//Inner Curve (S-facing)
		for(int i = 20; i <= 23; i += 3) {
			fillWithRandomizedBlocks(world, box, 15, i, 6, 16, i + 1, 6, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 22, i, 6, 23, i + 1, 6, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 24, i, 7, 24, i + 1, 7, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 25, i, 8, 25, i + 1, 8, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 26, i, 9, 26, i + 1, 9, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 27, i, 10, 27, i + 1, 11, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 27, i, 17, 27, i + 1, 18, rand, ConcreteBricks);
		}
		fillWithRandomizedBlocks(world, box, 15, 21, 5, 18, 21, 5, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 20, 21, 5, 21, 21, 5, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 15, 23, 5, 21, 23, 5, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 28, 21, 12, 28, 21, 13, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 28, 21, 15, 28, 21, 20, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 28, 23, 12, 28, 23, 20, rand, ConcreteBricks);
		fillWithMetadataBlocks(world, box, 15, 22, 6, 16, 22, 6, ModBlocks.concrete_colored, 11);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored, 11, 22, 22, 6, box);
		placeBlockAtCurrentPosition(world, ModBlocks.reinforced_glass, 0, 23, 22, 6, box);
		placeBlockAtCurrentPosition(world, ModBlocks.reinforced_glass, 0, 24, 22, 7, box);
		placeBlockAtCurrentPosition(world, ModBlocks.reinforced_glass, 0, 25, 22, 8, box);
		placeBlockAtCurrentPosition(world, ModBlocks.reinforced_glass, 0, 26, 22, 9, box);
		placeBlockAtCurrentPosition(world, ModBlocks.reinforced_glass, 0, 27, 22, 10, box);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored, 11, 27, 22, 11, box);
		fillWithMetadataBlocks(world, box, 27, 22, 17, 27, 22, 18, ModBlocks.concrete_colored, 11);
		fillWithMetadataBlocks(world, box, 15, 22, 5, 18, 22, 5, ModBlocks.concrete_colored, 11);
		fillWithMetadataBlocks(world, box, 20, 22, 5, 21, 22, 5, ModBlocks.concrete_colored, 11);
		fillWithMetadataBlocks(world, box, 28, 22, 12, 28, 22, 13, ModBlocks.concrete_colored, 11);
		fillWithMetadataBlocks(world, box, 28, 22, 15, 28, 22, 20, ModBlocks.concrete_colored, 11);
		//S-facing side
		fillWithRandomizedBlocks(world, box, 29, 21, 20, 36, 21, 20, rand, ConcreteBricks);
		fillWithMetadataBlocks(world, box, 29, 22, 20, 36, 22, 20, ModBlocks.concrete_colored, 11);
		fillWithRandomizedBlocks(world, box, 29, 23, 20, 36, 23, 20, rand, ConcreteBricks);
		//E-facing side
		fillWithRandomizedBlocks(world, box, 37, 21, 15, 37, 21, 20, rand, ConcreteBricks);
		fillWithMetadataBlocks(world, box, 37, 22, 15, 37, 22, 20, ModBlocks.concrete_colored, 11);
		fillWithRandomizedBlocks(world, box, 37, 23, 15, 37, 23, 20, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 37, 24, 15, 37, 24, 15, rand, ConcreteBricks);
		//Internal walls
		fillWithRandomizedBlocks(world, box, 32, 21, 16, 32, 21, 19, rand, ConcreteBricks);
		fillWithMetadataBlocks(world, box, 32, 22, 16, 32, 22, 19, ModBlocks.concrete_colored, 11);
		fillWithRandomizedBlocks(world, box, 32, 23, 16, 32, 23, 19, rand, ConcreteBricks);
		//Arches
		ConcreteStairs.setMetadata(stairS | 4);
		fillWithRandomizedBlocks(world, box, 24, 23, 2, 26, 23, 2, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 27, 23, 3, 28, 23, 3, rand, ConcreteStairs);
		ConcreteStairs.setMetadata(stairW | 4);
		fillWithRandomizedBlocks(world, box, 30, 23, 5, 30, 23, 6, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 31, 23, 7, 31, 23, 9, rand, ConcreteStairs);
		//Doors
		placeDoor(world, box, ModBlocks.door_bunker, 1, true, rand.nextBoolean(), 19, 21, 5);
		placeDoor(world, box, ModBlocks.door_bunker, 2, false, rand.nextBoolean(), 28, 21, 14);
		//Deco
		//Computer area
		int decoS = getDecoMeta(2);
		int decoN = getDecoMeta(3);
		int decoE = getDecoMeta(4);
		int decoW = getDecoMeta(5);
		int pillarWE = getPillarMeta(4);
		int pillarNS = getPillarMeta(8);
		int decoModelN = getDecoModelMeta(0);
		int decoModelW = getDecoModelMeta(2);
		int decoModelE = getDecoModelMeta(3);
		
		fillWithBlocks(world, box, 33, 21, 19, 33, 23, 19, ModBlocks.deco_steel);
		fillWithBlocks(world, box, 33, 21, 17, 33, 23, 17, ModBlocks.deco_steel);
		placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, decoW, 33, 21, 18, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_crt, getCRTMeta(1) | 8, 33, 22, 18, box);
		placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, decoW, 33, 23, 18, box);
		fillWithMetadataBlocks(world, box, 33, 21, 16, 33, 23, 16, ModBlocks.tape_recorder, decoW);
		placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone_stairs, stairE | 4, 34, 21, 19, box);
		placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 8, 34, 21, 18, box);
		placeBlockAtCurrentPosition(world, Blocks.heavy_weighted_pressure_plate, 0, 34, 22, 18, box);
		placeBlockAtCurrentPosition(world, ModBlocks.capacitor_copper, decoE, 36, 21, 16, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 36, 21, 17, box);
		placeBlockAtCurrentPosition(world, ModBlocks.fusion_conductor, 0, 36, 21, 19, box);
		fillWithMetadataBlocks(world, box, 36, 22, 16, 36, 23, 16, ModBlocks.tape_recorder, decoE);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, decoModelW, 36, 22, 17, box);
		fillWithMetadataBlocks(world, box, 36, 21, 18, 36, 23, 18, ModBlocks.tape_recorder, decoE);
		fillWithMetadataBlocks(world, box, 36, 22, 19, 36, 23, 19, ModBlocks.deco_crt, getCRTMeta(3) | 12);
		//Cabinets + Pipe
		fillWithBlocks(world, box, 32, 21, 11, 32, 22, 11, ModBlocks.deco_pipe_framed_green_rusted);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_framed_green_rusted, pillarNS, 32, 23, 10, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 32, 23, 11, box);
		fillWithMetadataBlocks(world, box, 32, 23, 12, 32, 23, 15, ModBlocks.deco_pipe_framed_green_rusted, pillarNS);
		
		placeBlockAtCurrentPosition(world, ModBlocks.turret_sentry_damaged, 0, 30, 21, 16, box);
		//Desk Area
		fillWithBlocks(world, box, 27, 21, 9, 28, 21, 9, ModBlocks.deco_steel);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_beryllium, 0, 26, 21, 8, box);
		fillWithBlocks(world, box, 25, 21, 7, 26, 21, 7, ModBlocks.deco_steel);
		fillWithBlocks(world, box, 24, 21, 5, 24, 21, 6, ModBlocks.deco_steel);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, decoModelN, 28, 22, 9, box);
		placeBlockAtCurrentPosition(world, Blocks.lever, 6, 26, 22, 8, box); //placed on ground
		placeBlockAtCurrentPosition(world, Blocks.lever, 6, 25, 22, 7, box);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairS, 28, 21, 7, box);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairW, 27, 21, 5, box);
		
		placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, decoE, 30, 21, 5, box);
		placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 27, 21, 3, box);
		
		placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 25, 22, 2, box);
		
		placeCore(world, box, ModBlocks.radio_telex, ForgeDirection.WEST, 25, 21, 5);
		fillSpace(world, box, 25, 21, 5, new int[] {0, 0, 0, 0, 1, 0}, ModBlocks.radio_telex, ForgeDirection.WEST);
		placeBlockAtCurrentPosition(world, ModBlocks.radio_torch_sender, 0, 26, 20, 8, box); //Launchpad lever
		setRTTYFreq(world, box, 26, 20, 8, freq);
		placeBlockAtCurrentPosition(world, ModBlocks.radio_torch_sender, 0, 25, 20, 7, box); //hatch lever
		setRTTYFreq(world, box, 25, 20, 7, freqHatch);
		
		//Machine/Small Desk Area
		placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_framed_green_rusted, pillarWE, 23, 23, 1, box);
		fillWithMetadataBlocks(world, box, 16, 23, 1, 19, 23, 1, ModBlocks.deco_pipe_framed_green_rusted, pillarWE);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 20, 21, 1, box);
		placeBlockAtCurrentPosition(world, ModBlocks.capacitor_copper, decoN, 20, 22, 1, box);
		placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone_stairs, stairS | 4, 21, 21, 1, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_crt, getCRTMeta(2) | 4, 21, 22, 1, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 22, 21, 1, box);
		placeBlockAtCurrentPosition(world, ModBlocks.capacitor_copper, decoN, 22, 22, 1, box);
		fillWithBlocks(world, box, 20, 23, 1, 22, 23, 1, ModBlocks.deco_steel);
		placeBlockAtCurrentPosition(world, ModBlocks.hev_battery, 0, 23, 21, 1, box);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairW, 18, 21, 2, box);
		fillWithBlocks(world, box, 16, 21, 1, 16, 21, 3, ModBlocks.deco_steel);
		
		placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, decoModelE, 16, 22, 2, box);
		placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 16, 22, 3, box);
		placeRandomBobble(world, box, rand, 16, 22, 4);
		
		//Containers
		generateInvContents(world, box, rand, ModBlocks.filing_cabinet, decoModelW, 31, 21, 17, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
		generateInvContents(world, box, rand, ModBlocks.filing_cabinet, decoModelW, 31, 21, 18, ItemPool.getPool(ItemPoolsComponent.POOL_VAULT_LAB), 6);
		generateInvContents(world, box, rand, ModBlocks.filing_cabinet, decoModelW, 31, 21, 19, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
		generateInvContents(world, box, rand, ModBlocks.filing_cabinet, decoModelW, 31, 22, 17, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
		generateInvContents(world, box, rand, ModBlocks.filing_cabinet, decoModelW, 31, 22, 19, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
		generateInvContents(world, box, rand, ModBlocks.crate_steel, 2, 29, 21, 19, ItemPool.getPool(ItemPoolsComponent.POOL_OFFICE_TRASH), 8);
		generateInvContents(world, box, rand, ModBlocks.filing_cabinet, decoModelE, 29, 21, 18, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
		generateInvContents(world, box, rand, ModBlocks.filing_cabinet, decoModelE, 29, 21, 17, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
		
		generateInvContents(world, box, rand, ModBlocks.filing_cabinet, decoModelW, 31, 21, 8, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 5);
		
		generateInvContents(world, box, rand, ModBlocks.crate_steel, 3, 25, 21, 2, ItemPool.getPool(ItemPoolsComponent.POOL_MACHINE_PARTS), 4);
		
		generateInvContents(world, box, rand, ModBlocks.filing_cabinet, decoModelN, 23, 21, 5, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 5);
		
		generateLockableContents(world, box, rand, ModBlocks.safe, decoW, 16, 21, 4, ItemPool.getPool(ItemPoolsSingle.POOL_VAULT_RUSTY), 3, 1D);
		
		/* Silo */
		//	TOP
		//Air
		fillWithAir(world, box, 17, 21, 6, 21, 23, 6);
		fillWithAir(world, box, 15, 21, 7, 23, 23, 10);
		fillWithAir(world, box, 24, 21, 8, 24, 23, 10);
		fillWithAir(world, box, 25, 21, 9, 25, 23, 10);
		fillWithAir(world, box, 26, 21, 10, 26, 23, 10);
		fillWithAir(world, box, 23, 21, 11, 26, 23, 17);
		fillWithAir(world, box, 27, 21, 12, 27, 23, 16);
		fillWithAir(world, box, 26, 21, 18, 26, 23, 18);
		fillWithAir(world, box, 25, 21, 18, 25, 23, 19);
		fillWithAir(world, box, 24, 21, 18, 24, 23, 20);
		fillWithAir(world, box, 15, 21, 18, 23, 23, 21);
		fillWithAir(world, box, 17, 21, 22, 21, 23, 22);
		fillWithAir(world, box, 14, 21, 18, 14, 23, 20);
		fillWithAir(world, box, 13, 21, 18, 13, 23, 19);
		fillWithAir(world, box, 12, 21, 18, 12, 23, 18);
		fillWithAir(world, box, 12, 21, 11, 15, 23, 17);
		fillWithAir(world, box, 11, 21, 12, 11, 23, 16);
		fillWithAir(world, box, 12, 21, 10, 12, 23, 10);
		fillWithAir(world, box, 13, 21, 9, 13, 23, 10);
		fillWithAir(world, box, 14, 21, 8, 14, 23, 10);
		//Floor
		fillWithBlocks(world, box, 13, 20, 9, 13, 20, 11, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 14, 20, 8, 14, 20, 9, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 15, 20, 7, 16, 20, 8, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 17, 20, 6, 21, 20, 7, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 22, 20, 7, 23, 20, 8, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 24, 20, 8, 24, 20, 9, ModBlocks.concrete_smooth);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 25, 20, 9, box);
		fillWithBlocks(world, box, 25, 20, 10, 26, 20, 11, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 26, 20, 12, 27, 20, 16, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 25, 20, 17, 26, 20, 18, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 24, 20, 19, 25, 20, 19, ModBlocks.concrete_smooth);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 24, 20, 20, box);
		fillWithBlocks(world, box, 22, 20, 20, 23, 20, 21, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 17, 20, 21, 21, 20, 22, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 15, 20, 20, 16, 20, 21, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 14, 20, 19, 14, 20, 20, ModBlocks.concrete_smooth);
		//grates
		fillWithMetadataBlocks(world, box, 14, 20, 10, 15, 20, 18, ModBlocks.steel_grate, 7);
		fillWithMetadataBlocks(world, box, 13, 20, 12, 13, 20, 16, ModBlocks.steel_grate, 7);
		fillWithMetadataBlocks(world, box, 17, 20, 8, 21, 20, 8, ModBlocks.steel_grate, 7);
		fillWithMetadataBlocks(world, box, 15, 20, 9, 23, 20, 9, ModBlocks.steel_grate, 7);
		fillWithMetadataBlocks(world, box, 16, 20, 10, 22, 20, 10, ModBlocks.steel_grate, 7);
		fillWithMetadataBlocks(world, box, 23, 20, 10, 24, 20, 18, ModBlocks.steel_grate, 7);
		fillWithMetadataBlocks(world, box, 25, 20, 12, 25, 20, 16, ModBlocks.steel_grate, 7);
		fillWithMetadataBlocks(world, box, 22, 20, 19, 23, 20, 19, ModBlocks.steel_grate, 7);
		fillWithMetadataBlocks(world, box, 15, 20, 19, 16, 20, 19, ModBlocks.steel_grate, 7);
		fillWithMetadataBlocks(world, box, 16, 20, 18, 22, 20, 18, ModBlocks.steel_grate, 7);
		//Ceiling
		fillWithBlocks(world, box, 11, 24, 12, 11, 24, 16, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 12, 24, 10, 15, 24, 18, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 13, 24, 9, 15, 24, 9, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 14, 24, 8, 15, 24, 8, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 13, 24, 19, 15, 24, 19, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 14, 24, 20, 15, 24, 20, ModBlocks.concrete_smooth);
		
		fillWithBlocks(world, box, 17, 24, 6, 21, 24, 6, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 15, 24, 7, 23, 24, 7, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 16, 24, 8, 22, 24, 10, ModBlocks.concrete_smooth);
		
		fillWithBlocks(world, box, 27, 24, 12, 27, 24, 16, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 23, 24, 10, 26, 24, 18, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 23, 24, 9, 25, 24, 9, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 23, 24, 8, 24, 24, 8, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 23, 24, 19, 25, 24, 19, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 23, 24, 20, 24, 24, 20, ModBlocks.concrete_smooth);
		
		fillWithBlocks(world, box, 17, 24, 22, 21, 24, 22, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 15, 24, 21, 23, 24, 21, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 16, 24, 18, 22, 24, 20, ModBlocks.concrete_smooth);
		//Walls
		fillWithRandomizedBlocks(world, box, 14, 20, 7, 14, 24, 7, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 13, 20, 8, 13, 24, 8, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 12, 21, 9, 12, 24, 9, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 11, 21, 10, 11, 24, 11, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 10, 21, 12, 10, 24, 16, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 11, 21, 17, 11, 24, 18, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 12, 21, 19, 12, 24, 19, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 13, 21, 20, 13, 24, 20, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 14, 20, 21, 14, 24, 21, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 15, 20, 22, 16, 24, 22, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 17, 20, 23, 21, 24, 23, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 22, 20, 22, 23, 24, 22, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 24, 20, 21, 24, 24, 21, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 25, 20, 20, 25, 24, 20, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 26, 20, 19, 26, 24, 19, rand, ConcreteBricks);

		//	CENTER
		//Air
		fillWithAir(world, box, 17, 2, 12, 21, 25, 16);
		for(int i = 5; i <= 17; i += 4) {
			if(((i - 5) / 4) % 2 == 0) { //stairs facing N
				fillWithAir(world, box, 17, i, 8, 20, i + 3, 9);
				fillWithAir(world, box, 17, i, 10, 21, i + 2, 10);
				fillWithAir(world, box, 17, i, 18, 21, i + 2, 20);
			} else { //stairs facing S
				fillWithAir(world, box, 18, i, 19, 21, i + 3, 20);
				fillWithAir(world, box, 17, i, 18, 21, i + 2, 18);
				fillWithAir(world, box, 17, i, 8, 21, i + 2, 10);
			}
			fillWithAir(world, box, 22, i, 10, 22, i + 2, 10);
			fillWithAir(world, box, 22, i, 9, 23, i + 2, 9);
			fillWithAir(world, box, 23, i, 10, 24, i + 2, 18);
			fillWithAir(world, box, 25, i, 12, 25, i + 2, 16);
			fillWithAir(world, box, 22, i, 19, 23, i + 2, 19);
			fillWithAir(world, box, 22, i, 18, 22, i + 2, 18);
			fillWithAir(world, box, 16, i, 18, 16, i + 2, 18);
			fillWithAir(world, box, 15, i, 19, 16, i + 2, 19);
			fillWithAir(world, box, 14, i, 10, 15, i + 2, 18);
			fillWithAir(world, box, 13, i, 12, 13, i + 2, 16);
			fillWithAir(world, box, 15, i, 9, 16, i + 2, 9);
			fillWithAir(world, box, 16, i, 10, 16, i + 2, 10);
		}
		for(int i = 6; i <= 22; i += 4) {
			fillWithAir(world, box, 16, i, 11, 18, i + 1, 11);
			fillWithAir(world, box, 16, i, 12, 16, i + 1, 13);
			fillWithAir(world, box, 16, i, 15, 16, i + 1, 16);
			fillWithAir(world, box, 16, i, 17, 18, i + 1, 17);
			fillWithAir(world, box, 20, i, 17, 22, i + 1, 17);
			fillWithAir(world, box, 22, i, 15, 22, i + 1, 16);
			fillWithAir(world, box, 22, i, 12, 22, i + 1, 13);
			fillWithAir(world, box, 20, i, 11, 22, i + 1, 11);
		}
		//Supports
		fillWithRandomizedBlocks(world, box, 22, 24, 17, 22, 24, 17, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 17, 24, 17, 21, 25, 17, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 16, 24, 17, 16, 24, 17, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 16, 24, 12, 16, 25, 16, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 16, 24, 11, 16, 24, 11, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 17, 24, 11, 21, 25, 11, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 22, 24, 11, 22, 24, 11, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 22, 24, 12, 22, 25, 16, rand, ConcreteBricks);
		
		fillWithRandomizedBlocks(world, box, 19, 5, 11, 19, 23, 11, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 22, 5, 14, 22, 23, 14, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 19, 5, 17, 19, 23, 17, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 16, 5, 14, 16, 23, 14, rand, ConcreteBricks);
		//Grates + Railing
		for(int j = 8; j <= 20; j += 4) {
			for(int i = 16; i <= 22; i += 6) {
				fillWithMetadataBlocks(world, box, i, j, 15, i, j, 16, ModBlocks.steel_grate, 7);
				fillWithMetadataBlocks(world, box, i, j, 12, i, j, 13, ModBlocks.steel_grate, 7);
				fillWithBlocks(world, box, i, j + 1, 15, i, j + 1, 16, ModBlocks.fence_metal);
				fillWithBlocks(world, box, i, j + 1, 12, i, j + 1, 13, ModBlocks.fence_metal);
			}
			for(int k = 11; k <= 17; k += 6) {
				fillWithMetadataBlocks(world, box, 16, j, k, 18, j, k, ModBlocks.steel_grate, 7);
				fillWithMetadataBlocks(world, box, 20, j, k, 22, j, k, ModBlocks.steel_grate, 7);
				fillWithBlocks(world, box, 16, j + 1, k, 18, j + 1, k, ModBlocks.fence_metal);
				fillWithBlocks(world, box, 20, j + 1, k, 22, j + 1, k, ModBlocks.fence_metal);
			}
		}
		//Floor
		for(int j = 8; j <= 16; j += 4) {
			fillWithBlocks(world, box, 15, j, 11, 15, j, 17, ModBlocks.concrete);
			fillWithBlocks(world, box, 16, j, 10, 22, j, 10, ModBlocks.concrete);
			fillWithBlocks(world, box, 23, j, 11, 23, j, 17, ModBlocks.concrete);
			fillWithBlocks(world, box, 16, j, 18, 22, j, 18, ModBlocks.concrete);
			fillWithBlocks(world, box, 15, j, 9, 16, j, 9, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 14, j, 10, 15, j, 10, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 14, j, 11, 14, j, 17, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 13, j, 12, 13, j, 16, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 14, j, 18, 15, j, 18, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 15, j, 19, 16, j, 19, ModBlocks.concrete_smooth);
			
			if((j / 4) % 2 == 0) {
				fillWithBlocks(world, box, 20, j, 19, 21, j, 20, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 19, j, 19, 19, j + 1, 20, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 18, j, 19, 18, j + 2, 20, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 17, j, 19, 17, j + 3, 20, ModBlocks.concrete_smooth);
				for(int i = 0; i < 4; i++)
					fillWithMetadataBlocks(world, box, 20 - i, j + 1 + i, 19, 20 - i, j + 1 + i, 20, ModBlocks.concrete_smooth_stairs, stairE);
			} else {
				fillWithBlocks(world, box, 17, j, 8, 18, j, 9, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 19, j, 8, 19, j + 1, 9, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 20, j, 8, 20, j + 2, 9, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 21, j, 8, 21, j + 3, 9, ModBlocks.concrete_smooth);
				for(int i = 0; i < 4; i++)
					fillWithMetadataBlocks(world, box, 18 + i, j + 1 + i, 8, 18 + i, j + 1 + i, 9, ModBlocks.concrete_smooth_stairs, stairW);
			}
			
			fillWithBlocks(world, box, 22, j, 9, 23, j, 9, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 23, j, 10, 24, j, 10, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 24, j, 11, 24, j, 17, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 25, j, 12, 25, j, 16, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 23, j, 18, 24, j, 18, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 22, j, 19, 23, j, 19, ModBlocks.concrete_smooth);
		}
		
		//Walls
		fillWithRandomizedBlocks(world, box, 17, 5, 7, 21, 19, 7, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 15, 4, 8, 16, 19, 8, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 14, 4, 9, 14, 19, 9, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 13, 4, 10, 13, 19, 11, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 12, 5, 12, 12, 19, 16, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 13, 4, 17, 13, 19, 18, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 14, 4, 19, 14, 19, 19, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 15, 4, 20, 16, 19, 20, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 17, 5, 21, 21, 19, 21, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 22, 4, 20, 23, 19, 20, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 24, 4, 19, 24, 19, 19, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 25, 4, 17, 25, 19, 18, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 26, 5, 12, 26, 19, 16, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 25, 4, 10, 25, 19, 11, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 24, 4, 9, 24, 19, 9, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 22, 4, 8, 23, 19, 8, rand, ConcreteBricks);
		
		//	EXHAUST
		//dark area N/S
		fillWithMetadataBlocks(world, box, 17, 0, 7, 21, 0, 21, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 18, 1, 7, 20, 1, 7, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 17, 1, 7, 17, 1, 12, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 21, 1, 7, 21, 1, 12, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 18, 1, 11, 18, 1, 12, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 20, 1, 11, 20, 1, 12, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 18, 1, 21, 20, 1, 21, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 17, 1, 16, 17, 1, 21, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 21, 1, 16, 21, 1, 21, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 18, 1, 16, 18, 1, 17, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 20, 1, 16, 20, 1, 17, ModBlocks.concrete_colored, 7);
		//W/E
		fillWithMetadataBlocks(world, box, 12, 0, 12, 16, 0, 16, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 22, 0, 12, 26, 0, 16, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 12, 1, 13, 12, 1, 15, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 12, 1, 16, 16, 1, 16, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 12, 1, 12, 16, 1, 12, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 16, 1, 15, 17, 1, 15, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 16, 1, 13, 17, 1, 13, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 26, 1, 13, 26, 1, 15, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 22, 1, 16, 26, 1, 16, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 22, 1, 12, 26, 1, 12, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 21, 1, 15, 22, 1, 15, ModBlocks.concrete_colored, 7);
		fillWithMetadataBlocks(world, box, 21, 1, 13, 22, 1, 13, ModBlocks.concrete_colored, 7);
		//gray area walls
		fillWithBlocks(world, box, 18, 2, 21, 20, 3, 21, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 21, 2, 17, 21, 3, 21, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 22, 2, 16, 26, 3, 16, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 26, 2, 13, 26, 3, 15, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 22, 2, 12, 26, 3, 12, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 21, 2, 7, 21, 3, 11, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 18, 2, 7, 20, 3, 7, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 17, 2, 7, 17, 3, 11, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 12, 2, 12, 16, 3, 12, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 12, 2, 13, 12, 3, 15, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 12, 2, 16, 16, 3, 16, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 17, 2, 17, 17, 3, 21, ModBlocks.concrete_smooth);
		//Floor
		fillWithBlocks(world, box, 18, 2, 21, 20, 3, 21, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 21, 2, 17, 21, 3, 21, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 22, 2, 16, 26, 3, 16, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 26, 2, 13, 26, 3, 15, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 22, 2, 12, 26, 3, 12, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 21, 2, 7, 21, 3, 11, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 18, 2, 7, 20, 3, 7, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 17, 2, 7, 17, 3, 11, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 12, 2, 12, 16, 3, 12, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 12, 2, 13, 12, 3, 15, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 12, 2, 16, 16, 3, 16, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 17, 2, 17, 17, 2, 21, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 17, 4, 17, 21, 4, 21, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 22, 4, 17, 23, 4, 19, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 24, 4, 17, 24, 4, 18, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 22, 4, 12, 26, 4, 16, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 24, 4, 10, 24, 4, 11, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 22, 4, 9, 23, 4, 11, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 17, 4, 7, 21, 4, 11, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 15, 4, 9, 16, 4, 11, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 14, 4, 10, 14, 4, 11, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 12, 4, 12, 16, 4, 16, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 14, 4, 17, 14, 4, 18, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 15, 4, 17, 16, 4, 19, ModBlocks.concrete_smooth);
		//Stairs
		fillWithBlocks(world, box, 19, 5, 8, 19, 5, 9, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 20, 5, 8, 20, 6, 9, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 21, 5, 8, 21, 7, 9, ModBlocks.concrete_smooth);
		for(int i = 0; i < 4; i++)
			fillWithMetadataBlocks(world, box, 18 + i, 5 + i, 8, 18 + i, 5 + i, 9, ModBlocks.concrete_smooth_stairs, stairW);
		//Railing and Deco
		placeBlockAtCurrentPosition(world, ModBlocks.fence_metal, 0, 18, 5, 11, box);
		fillWithBlocks(world, box, 20, 5, 11, 22, 5, 11, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 22, 5, 12, 22, 5, 13, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 22, 5, 15, 22, 5, 17, ModBlocks.fence_metal);
		placeBlockAtCurrentPosition(world, ModBlocks.fence_metal, 0, 20, 5, 17, box);
		fillWithBlocks(world, box, 16, 5, 17, 18, 5, 17, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 16, 5, 15, 16, 5, 16, ModBlocks.fence_metal);
		fillWithBlocks(world, box, 16, 5, 11, 16, 5, 13, ModBlocks.fence_metal);
		placeBlockAtCurrentPosition(world, Blocks.air, 0, 21, 5, 17, box);
		placeBlockAtCurrentPosition(world, Blocks.air, 0, 17, 5, 11, box);
		
		fillWithMetadataBlocks(world, box, 17, 2, 12, 17, 4, 12, ModBlocks.ladder_steel, decoN);
		fillWithMetadataBlocks(world, box, 21, 2, 16, 21, 4, 16, ModBlocks.ladder_steel, decoS);
		//Launch Pad
		placeCoreLaunchpad(world, box, ModBlocks.launch_pad_rusted, ForgeDirection.SOUTH, 19, 1, 14);
		fillSpace(world, box, 19, 1, 14, new int[] {0, 0, 1, 1, 1, 1}, ModBlocks.launch_pad_rusted, ForgeDirection.SOUTH);
		for(int i = 0; i <= 2; i += 2)
			for(int k = 0; k <= 2; k += 2)
				makeExtra(world, box, ModBlocks.launch_pad_rusted, 18 + i, 1, 13 + k);
		placeBlockAtCurrentPosition(world, ModBlocks.radio_torch_receiver, 3, 19, 0, 14, box);
		setRTTYFreq(world, box, 19, 0, 14, freq);
		
		//Air
		fillWithAir(world, box, 18, 1, 8, 20, 3, 10);
		fillWithAir(world, box, 18, 2, 11, 20, 3, 11);
		fillWithAir(world, box, 19, 1, 11, 19, 1, 12);
		fillWithAir(world, box, 19, 1, 16, 19, 1, 17);
		fillWithAir(world, box, 18, 2, 17, 20, 3, 17);
		fillWithAir(world, box, 18, 1, 18, 20, 3, 20);
		fillWithAir(world, box, 13, 1, 13, 15, 3, 15);
		fillWithAir(world, box, 16, 2, 13, 16, 3, 15);
		fillWithAir(world, box, 16, 1, 14, 17, 1, 14);
		fillWithAir(world, box, 21, 1, 14, 22, 1, 14);
		fillWithAir(world, box, 22, 2, 13, 22, 3, 15);
		fillWithAir(world, box, 23, 1, 13, 25, 3, 15);
		
		/* Red Sector */
		//Air
		fillWithAir(world, box, 2, 17, 9, 11, 18, 11);
		fillWithAir(world, box, 2, 19, 10, 11, 19, 10);
		fillWithAir(world, box, 2, 17, 13, 11, 18, 15);
		fillWithAir(world, box, 2, 19, 14, 11, 19, 14);
		fillWithAir(world, box, 8, 17, 17, 12, 18, 25);
		fillWithAir(world, box, 9, 19, 17, 11, 19, 25);
		fillWithAir(world, box, 2, 17, 17, 6, 18, 21);
		fillWithAir(world, box, 3, 19, 17, 5, 19, 21);
		fillWithAir(world, box, 2, 17, 22, 3, 18, 25);
		fillWithAir(world, box, 3, 19, 22, 3, 19, 25);
		fillWithAir(world, box, 5, 17, 23, 6, 19, 25);
		//Ceiling
		fillWithBlocks(world, box, 1, 20, 8, 12, 20, 16, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 1, 20, 17, 13, 20, 26, ModBlocks.concrete_smooth);
		//Floor
		fillWithBlocks(world, box, 2, 16, 7, 11, 16, 7, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 1, 16, 8, 12, 16, 11, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 1, 16, 12, 11, 16, 16, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 1, 16, 17, 12, 16, 18, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 1, 16, 19, 13, 16, 26, ModBlocks.concrete_smooth);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 12, 16, 14, box);
		//Walls
		//N
		fillWithRandomizedBlocks(world, box, 2, 17, 7, 11, 19, 7, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 11, 17, 8, 12, 17, 8, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 8, 17, 8, 8, 17, 8, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 5, 17, 8, 5, 17, 8, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 1, 17, 8, 2, 17, 8, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 1, 19, 8, 12, 19, 8, rand, ConcreteBricks);
		fillWithMetadataBlocks(world, box, 1, 18, 8, 2, 18, 8, ModBlocks.concrete_colored, 14);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored, 14, 5, 18, 8, box);
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored, 14, 8, 18, 8, box);
		fillWithMetadataBlocks(world, box, 11, 18, 8, 12, 18, 8, ModBlocks.concrete_colored, 14);
		//W
		fillWithMetadataBlocks(world, box, 1, 18, 9, 1, 18, 25, ModBlocks.concrete_colored, 14);
		//S
		fillWithMetadataBlocks(world, box, 1, 18, 26, 13, 18, 26, ModBlocks.concrete_colored, 14);
		//E
		fillWithMetadataBlocks(world, box, 13, 18, 17, 13, 18, 25, ModBlocks.concrete_colored, 14);
		fillWithMetadataBlocks(world, box, 12, 18, 9, 12, 18, 16, ModBlocks.concrete_colored, 14);
		fillWithMetadataBlocks(world, box, 13, 18, 10, 13, 18, 11, ModBlocks.concrete_colored, 14);
		//Internal
		fillWithMetadataBlocks(world, box, 2, 18, 12, 11, 18, 12, ModBlocks.concrete_colored, 14);
		fillWithMetadataBlocks(world, box, 2, 18, 16, 11, 18, 16, ModBlocks.concrete_colored, 14);
		fillWithMetadataBlocks(world, box, 7, 18, 17, 7, 18, 25, ModBlocks.concrete_colored, 14);
		fillWithMetadataBlocks(world, box, 4, 18, 22, 6, 18, 22, ModBlocks.concrete_colored, 14);
		fillWithMetadataBlocks(world, box, 4, 18, 23, 4, 18, 25, ModBlocks.concrete_colored, 14);
		for(int i = 17; i <= 19; i += 2) {
			//W
			fillWithRandomizedBlocks(world, box, 1, i, 9, 1, i, 25, rand, ConcreteBricks);
			//S
			fillWithRandomizedBlocks(world, box, 1, i, 26, 13, i, 26, rand, ConcreteBricks);
			//E
			fillWithRandomizedBlocks(world, box, 13, i, 19, 13, i, 25, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 12, i, 9, 12, i, 11, rand, ConcreteBricks);
			//Internal
			fillWithRandomizedBlocks(world, box, 2, i, 12, 11, i, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 2, i, 16, 11, i, 16, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 7, i, 17, 7, i, 25, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, i, 22, 6, i, 22, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, i, 23, 4, i, 25, rand, ConcreteBricks);
		}
		//Arches
		ConcreteStairs.setMetadata(stairS | 4);
		fillWithRandomizedBlocks(world, box, 2, 19, 9, 11, 19, 9, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 2, 19, 13, 11, 19, 13, rand, ConcreteStairs);
		ConcreteStairs.setMetadata(stairN | 4);
		fillWithRandomizedBlocks(world, box, 2, 19, 11, 11, 19, 11, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 2, 19, 15, 11, 19, 15, rand, ConcreteStairs);
		ConcreteStairs.setMetadata(stairW | 4);
		fillWithRandomizedBlocks(world, box, 12, 19, 17, 12, 19, 25, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 6, 19, 17, 6, 19, 21, rand, ConcreteStairs);
		ConcreteStairs.setMetadata(stairE | 4);
		fillWithRandomizedBlocks(world, box, 8, 19, 17, 8, 19, 25, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 2, 19, 17, 2, 19, 25, rand, ConcreteStairs);
		//Doors
		placeDoor(world, box, ModBlocks.door_bunker, 2, false, rand.nextBoolean(), 12, 17, 14);
		placeDoor(world, box, ModBlocks.door_bunker, 3, false, rand.nextBoolean(), 10, 17, 12);
		placeDoor(world, box, ModBlocks.door_bunker, 1, false, rand.nextBoolean(), 10, 17, 16);
		placeDoor(world, box, ModBlocks.door_bunker, 1, false, rand.nextBoolean(), 4, 17, 16);
		placeDoor(world, box, ModBlocks.door_metal, 0, false, rand.nextBoolean(), 4, 17, 24);
		//Deco
		//Living Room
		placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone_stairs, stairW | 4, 12, 17, 17, box);
		placeBlockAtCurrentPosition(world, Blocks.cauldron, 0, 12, 17, 18, box);
		fillWithMetadataBlocks(world, box, 12, 17, 19, 12, 17, 20, ModBlocks.reinforced_stone_stairs, stairW | 4);
		placeBlockAtCurrentPosition(world, ModBlocks.machine_electric_furnace_off, decoE, 12, 17, 21, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_toaster, getCRTMeta(3) | 4, 12, 18, 17, box);
		placeLever(world, box, 2, true, 12, 18, 18);
		placeBlockAtCurrentPosition(world, ModBlocks.machine_microwave, decoE, 12, 18, 19, box);
		placeBlockAtCurrentPosition(world, ModBlocks.hev_battery, 0, 12, 18, 20, box);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairS, 8, 17, 17, box);
		fillWithMetadataBlocks(world, box, 8, 17, 19, 9, 17, 19, ModBlocks.reinforced_stone_stairs, stairS | 4);
		fillWithMetadataBlocks(world, box, 8, 17, 20, 9, 17, 20, ModBlocks.reinforced_stone_stairs, stairN | 4);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairN, 8, 17, 22, box);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairE, 10, 17, 23, box);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairS, 11, 17, 23, box);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairW, 12, 17, 23, box);
		fillWithMetadataBlocks(world, box, 10, 17, 25, 12, 17, 25, ModBlocks.reinforced_stone_stairs, stairN | 4);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_crt, getCRTMeta(0), 11, 18, 25, box);
		
		//Bathroom
		placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 6, 17, 17, box);
		fillWithBlocks(world, box, 6, 17, 18, 6, 17, 20, Blocks.cauldron);
		placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 6, 17, 21, box);
		for(int i = 0; i < 3; i++)
			placeLever(world, box, 2, true, 6, 18, 18 + i);
		placeBlockAtCurrentPosition(world, Blocks.hopper, decoW, 6, 17, 24, box);
		placeBlockAtCurrentPosition(world, Blocks.trapdoor, decoModelW >> 2, 6, 18, 24, box);
		//Bedroom
		for(int i = 3; i <= 7; i += 2)
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone_stairs, stairN | 4, i, 17, 11, box);
		
		for(int i = 4; i <= 10; i += 3)
			for(int j = 17; j <= 18; j++)
				placeBed(world, box, 1, i, j, 8);
		
		//Containers
		generateInvContents(world, box, rand, ModBlocks.crate_steel, 2, 8, 17, 25, ItemPool.getPool(ItemPoolsComponent.POOL_VAULT_LOCKERS), 6);
		
		generateInvContents(world, box, rand, ModBlocks.crate_steel, 2, 2, 17, 11, ItemPool.getPool(ItemPoolsComponent.POOL_VAULT_LOCKERS), 6); //placed separately for loot table control
		generateInvContents(world, box, rand, ModBlocks.crate_steel, 2, 4, 17, 11, ItemPool.getPool(ItemPoolsLegacy.POOL_EXPENSIVE), 2);
		generateInvContents(world, box, rand, ModBlocks.crate_steel, 2, 6, 17, 11, ItemPool.getPool(ItemPoolsComponent.POOL_VAULT_LOCKERS), 6);
		generateInvContents(world, box, rand, ModBlocks.crate_steel, 2, 8, 17, 11, ItemPool.getPool(ItemPoolsComponent.POOL_VAULT_LOCKERS), 6);
		//Mines
		fillWithMines(world, box, rand, 2, 17, 9, 11, 17, 11);
		fillWithMines(world, box, rand, 9, 17, 17, 11, 17, 24);
		fillWithMines(world, box, rand, 5, 17, 23, 6, 17, 25);
		
		/* Yellow Sector */
		//Air
		fillWithAir(world, box, 27, 13, 13, 33, 14, 15);
		fillWithAir(world, box, 27, 15, 14, 33, 15, 14);
		fillWithAir(world, box, 27, 13, 17, 33, 14, 21);
		fillWithAir(world, box, 27, 15, 18, 33, 15, 20);
		fillWithAir(world, box, 27, 13, 9, 29, 14, 11);
		fillWithAir(world, box, 28, 15, 9, 28, 15, 11);
		fillWithAir(world, box, 31, 13, 9, 33, 14, 11);
		fillWithAir(world, box, 32, 15, 9, 32, 15, 11);
		//Ceiling + Floor
		for(int i = 12; i <= 16; i += 4) {
			fillWithBlocks(world, box, 26, i, 17, 26, i, 22, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 27, i, 8, 34, i, 22, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 26, i, 8, 26, i, 11, ModBlocks.concrete_smooth);
		}
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 26, 12, 14, box);
		//Walls
		//N
		fillWithMetadataBlocks(world, box, 26, 14, 8, 34, 14, 8, ModBlocks.concrete_colored, 4);
		//E
		fillWithMetadataBlocks(world, box, 34, 14, 9, 34, 14, 21, ModBlocks.concrete_colored, 4);
		//S
		fillWithMetadataBlocks(world, box, 26, 14, 22, 34, 14, 22, ModBlocks.concrete_colored, 4);
		//W
		fillWithMetadataBlocks(world, box, 26, 14, 9, 26, 14, 21, ModBlocks.concrete_colored, 4);
		fillWithMetadataBlocks(world, box, 25, 14, 17, 25, 14, 18, ModBlocks.concrete_colored, 4);
		fillWithMetadataBlocks(world, box, 25, 14, 10, 25, 14, 11, ModBlocks.concrete_colored, 4);
		//Internal
		fillWithMetadataBlocks(world, box, 27, 14, 16, 33, 14, 16, ModBlocks.concrete_colored, 4);
		fillWithMetadataBlocks(world, box, 27, 14, 12, 33, 14, 12, ModBlocks.concrete_colored, 4);
		fillWithMetadataBlocks(world, box, 30, 14, 9, 30, 14, 11, ModBlocks.concrete_colored, 4);
		for(int i = 13; i <= 15; i += 2) {
			//N
			fillWithRandomizedBlocks(world, box, 26, i, 8, 34, i, 8, rand, ConcreteBricks);
			//E
			fillWithRandomizedBlocks(world, box, 34, i, 9, 34, i, 21, rand, ConcreteBricks);
			//S
			fillWithRandomizedBlocks(world, box, 26, i, 22, 34, i, 22, rand, ConcreteBricks);
			//W
			fillWithRandomizedBlocks(world, box, 26, i, 15, 26, i, 21, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 26, i, 9, 26, i, 13, rand, ConcreteBricks);
			//Internal
			fillWithRandomizedBlocks(world, box, 27, i, 16, 33, i, 16, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 27, i, 12, 33, i, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 30, i, 9, 30, i, 11, rand, ConcreteBricks);
		}
		//Arches
		ConcreteStairs.setMetadata(stairN | 4);
		fillWithRandomizedBlocks(world, box, 27, 15, 21, 33, 15, 21, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 27, 15, 15, 33, 15, 15, rand, ConcreteStairs);
		ConcreteStairs.setMetadata(stairS | 4);
		fillWithRandomizedBlocks(world, box, 27, 15, 17, 33, 15, 17, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 27, 15, 13, 33, 15, 13, rand, ConcreteStairs);
		ConcreteStairs.setMetadata(stairW | 4);
		fillWithRandomizedBlocks(world, box, 33, 15, 9, 33, 15, 11, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 29, 15, 9, 29, 15, 11, rand, ConcreteStairs); 
		ConcreteStairs.setMetadata(stairE | 4);
		fillWithRandomizedBlocks(world, box, 31, 15, 9, 31, 15, 11, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 27, 15, 9, 27, 15, 11, rand, ConcreteStairs);
		//Doors
		placeDoor(world, box, ModBlocks.door_bunker, 0, false, rand.nextBoolean(), 26, 13, 14);
		placeDoor(world, box, ModBlocks.door_bunker, 3, false, rand.nextBoolean(), 28, 13, 12);
		placeDoor(world, box, ModBlocks.door_bunker, 3, false, rand.nextBoolean(), 32, 13, 12);
		placeDoor(world, box, ModBlocks.door_bunker, 1, false, rand.nextBoolean(), 32, 13, 16);
		//Deco
		//Room 1
		placeBlockAtCurrentPosition(world, ModBlocks.crate_ammo, 0, 27, 13, 9, box);
		placeBlockAtCurrentPosition(world, ModBlocks.crate_can, 0, 27, 13, 10, box);
		placeBlockAtCurrentPosition(world, ModBlocks.crate_can, 0, 27, 14, 9, box);
		placeBlockAtCurrentPosition(world, ModBlocks.crate_can, 0, 28, 13, 9, box);
		placeBlockAtCurrentPosition(world, ModBlocks.barrel_corroded, 0, 29, 13, 9, box);
		//Room 2
		placeBlockAtCurrentPosition(world, ModBlocks.crate_can, 0, 31, 13, 9, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, decoModelE, 31, 13, 11, box);
		
		placeBlockAtCurrentPosition(world, ModBlocks.crate_can, 0, 33, 13, 11, box);
		//Workshop
		placeBlockAtCurrentPosition(world, ModBlocks.machine_transformer, 0, 33, 13, 17, box);
		fillWithRandomizedBlocks(world, box, 33, 13, 18, 33, 13, 20, rand, Supplies);
		
		placeBlockAtCurrentPosition(world, ModBlocks.anvil_iron, decoN, 31, 13, 21, box);
		fillWithBlocks(world, box, 28, 13, 18, 29, 13, 20, Blocks.planks);
		placeBlockAtCurrentPosition(world, Blocks.crafting_table, 0, 29, 13, 19, box);
		placeBlockAtCurrentPosition(world, ModBlocks.radiorec, decoE, 28, 14, 19, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_toaster, getCRTMeta(1), 28, 13, 17, box);
		
		//Containers
		generateInvContents(world, box, rand, ModBlocks.crate_steel, 2, 32, 13, 9, ItemPool.getPool(ItemPoolsComponent.POOL_SILO), 6);
		generateInvContents(world, box, rand, ModBlocks.safe, decoN, 33, 13, 9, ItemPool.getPool(ItemPoolsComponent.POOL_MACHINE_PARTS), 6);
		
		generateInvContents(world, box, rand, ModBlocks.crate_steel, 2, 33, 13, 21, ItemPool.getPool(ItemPoolsComponent.POOL_VAULT_LAB), 8);
		//Mines
		fillWithMines(world, box, rand, 27, 13, 13, 33, 13, 15);
		
		/* Green Sector */
		//Air
		fillWithAir(world, box, 1, 9, 13, 11, 10, 15);
		fillWithAir(world, box, 1, 11, 14, 8, 11, 14);
		fillWithAir(world, box, 1, 9, 7, 6, 10, 11);
		fillWithAir(world, box, 1, 11, 8, 6, 11, 10);
		fillWithAir(world, box, 7, 9, 7, 11, 10, 7);
		fillWithAir(world, box, 7, 9, 11, 11, 10, 11);
		fillWithAir(world, box, 2, 9, 17, 4, 11, 23);
		fillWithAir(world, box, 5, 9, 17, 5, 9, 18);
		fillWithAir(world, box, 5, 9, 22, 5, 9, 23);
		fillWithAir(world, box, 1, 9, 17, 1, 9, 18);
		fillWithAir(world, box, 1, 9, 22, 1, 9, 23);
		fillWithAir(world, box, 7, 9, 17, 11, 10, 23);
		fillWithAir(world, box, 8, 11, 17, 10, 11, 23);
		//Floor + Ceiling
		placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 12, 8, 14, box);
		for(int i = 8; i <= 12; i += 4) {
			fillWithBlocks(world, box, 0, i, 6, 12, i, 11, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 0, i, 12, 11, i, 16, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 0, i, 17, 12, i, 24, ModBlocks.concrete_smooth);
		}
		//Walls
		//N
		fillWithMetadataBlocks(world, box, 0, 10, 6, 12, 10, 6, ModBlocks.concrete_colored, 13);
		//W
		fillWithMetadataBlocks(world, box, 0, 10, 7, 0, 10, 23, ModBlocks.concrete_colored, 13);
		//S
		fillWithMetadataBlocks(world, box, 0, 10, 24, 12, 10, 24, ModBlocks.concrete_colored, 13);
		//E
		fillWithMetadataBlocks(world, box, 12, 10, 7, 12, 10, 23, ModBlocks.concrete_colored, 13);
		fillWithMetadataBlocks(world, box, 13, 10, 17, 13, 10, 18, ModBlocks.concrete_colored, 13);
		fillWithMetadataBlocks(world, box, 13, 10, 10, 13, 10, 11, ModBlocks.concrete_colored, 13);
		//Internal
		fillWithMetadataBlocks(world, box, 1, 10, 12, 11, 10, 12, ModBlocks.concrete_colored, 13);
		fillWithMetadataBlocks(world, box, 1, 10, 16, 11, 10, 16, ModBlocks.concrete_colored, 13);
		fillWithMetadataBlocks(world, box, 6, 10, 17, 6, 10, 23, ModBlocks.concrete_colored, 13);
		for(int i = 9; i <= 11; i += 2) {
			//N
			fillWithRandomizedBlocks(world, box, 0, i, 6, 12, i, 6, rand, ConcreteBricks);
			//W
			fillWithRandomizedBlocks(world, box, 0, i, 7, 0, i, 23, rand, ConcreteBricks);
			//S
			fillWithRandomizedBlocks(world, box, 0, i, 24, 12, i, 24, rand, ConcreteBricks);
			//E
			fillWithRandomizedBlocks(world, box, 12, i, 17, 12, i, 23, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 12, i, 7, 12, i, 11, rand, ConcreteBricks);
			//Internal
			fillWithRandomizedBlocks(world, box, 1, i, 12, 11, i, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, i, 16, 11, i, 16, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 6, i, 17, 6, i, 23, rand, ConcreteBricks);
		}
		//Arches
		ConcreteStairs.setMetadata(stairS | 4);
		fillWithRandomizedBlocks(world, box, 1, 11, 7, 11, 11, 7, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 1, 11, 13, 11, 11, 13, rand, ConcreteStairs);
		ConcreteStairs.setMetadata(stairN | 4);
		fillWithRandomizedBlocks(world, box, 1, 11, 11, 11, 11, 11, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 1, 11, 15, 11, 11, 15, rand, ConcreteStairs);
		ConcreteStairs.setMetadata(stairW | 4);
		fillWithRandomizedBlocks(world, box, 11, 11, 17, 11, 11, 23, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 5, 11, 17, 5, 11, 18, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 5, 11, 22, 5, 11, 23, rand, ConcreteStairs);
		ConcreteStairs.setMetadata(stairE | 4);
		fillWithRandomizedBlocks(world, box, 7, 11, 17, 7, 11, 23, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 1, 11, 17, 1, 11, 18, rand, ConcreteStairs);
		fillWithRandomizedBlocks(world, box, 1, 11, 22, 1, 11, 23, rand, ConcreteStairs);
		//Doors
		placeDoor(world, box, ModBlocks.door_bunker, 2, false, rand.nextBoolean(), 12, 9, 14);
		placeDoor(world, box, ModBlocks.door_bunker, 1, false, rand.nextBoolean(), 9, 9, 16);
		placeDoor(world, box, ModBlocks.door_bunker, 1, false, rand.nextBoolean(), 3, 9, 16);
		placeDoor(world, box, ModBlocks.door_bunker, 3, false, rand.nextBoolean(), 3, 9, 12);
		//Deco
		//Fuel Infrastructure
		fillWithMetadataBlocks(world, box, 17, 11, 14, 18, 11, 14, ModBlocks.deco_pipe_quad_rusted, pillarWE);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 16, 11, 14, box);
		fillWithMetadataBlocks(world, box, 13, 11, 14, 15, 11, 14, ModBlocks.deco_pipe_quad_rusted, pillarWE);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 12, 11, 14, box);
		fillWithMetadataBlocks(world, box, 10, 11, 14, 11, 11, 14, ModBlocks.deco_pipe_quad_rusted, pillarWE);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 9, 11, 14, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_quad_rusted, pillarNS, 9, 11, 15, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 9, 11, 16, box);
		fillWithMetadataBlocks(world, box, 9, 11, 17, 9, 11, 19, ModBlocks.deco_pipe_quad_rusted, pillarNS);
		placeBlockAtCurrentPosition(world, ModBlocks.fluid_duct_gauge, 0, 9, 11, 20, box);
		fillWithMetadataBlocks(world, box, 9, 11, 21, 9, 11, 22, ModBlocks.deco_pipe_quad_rusted, pillarNS);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 9, 11, 23, box);
		fillWithBlocks(world, box, 9, 9, 23, 9, 10, 23, ModBlocks.deco_pipe_framed_rusted);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 9, 8, 23, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_quad_rusted, pillarWE, 10, 11, 20, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 11, 11, 20, box);
		fillWithBlocks(world, box, 11, 9, 20, 11, 10, 20, ModBlocks.deco_pipe_framed_rusted);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 11, 8, 20, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_quad_rusted, pillarWE, 8, 11, 20, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 7, 11, 20, box);
		fillWithBlocks(world, box, 7, 9, 20, 7, 10, 20, ModBlocks.deco_pipe_framed_rusted);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 7, 8, 20, box);
		fillWithBlocks(world, box, 8, 8, 18, 10, 8, 22, ModBlocks.deco_lead);
		//Barrels in tank room
		placeBlockAtCurrentPosition(world, ModBlocks.lox_barrel, 0, 7, 9, 17, box);
		placeBlockAtCurrentPosition(world, ModBlocks.pink_barrel, 0, 11, 9, 19, box);
		placeBlockAtCurrentPosition(world, ModBlocks.pink_barrel, 0, 11, 9, 22, box);
		fillWithBlocks(world, box, 11, 9, 23, 11, 10, 23, ModBlocks.pink_barrel);
		placeBlockAtCurrentPosition(world, ModBlocks.pink_barrel, 0, 10, 9, 23, box);
		fillWithBlocks(world, box, 7, 9, 23, 8, 9, 23, ModBlocks.lox_barrel);
		fillWithBlocks(world, box, 7, 9, 21, 7, 9, 22, ModBlocks.lox_barrel);
		//Capacitor Room
		for(int i = 1; i <= 5; i += 4) {
			fillWithMetadataBlocks(world, box, i, 10, 17, i, 10, 18, ModBlocks.deco_pipe_quad_red, pillarNS);
			fillWithMetadataBlocks(world, box, i, 10, 22, i, 10, 23, ModBlocks.deco_pipe_quad_red, pillarNS);
			fillWithBlocks(world, box, i, 9, 19, i, 9, 21, ModBlocks.deco_lead);
			fillWithMetadataBlocks(world, box, i, 10, 19, i, 10, 21, ModBlocks.capacitor_copper, i == 1 ? decoW : decoE);
			fillWithBlocks(world, box, i, 11, 19, i, 11, 21, ModBlocks.deco_lead);
		}
		//Generator Room
		placeBlockAtCurrentPosition(world, ModBlocks.barrel_corroded, 0, 1, 9, 11, box);
		fillWithBlocks(world, box, 1, 9, 8, 1, 9, 9, ModBlocks.barrel_corroded);
		fillWithBlocks(world, box, 1, 9, 7, 1, 10, 7, ModBlocks.barrel_corroded);
		placeBlockAtCurrentPosition(world, ModBlocks.barrel_corroded, 0, 2, 9, 7, box);
		
		fillWithBlocks(world, box, 7, 9, 10, 11, 9, 10, ModBlocks.deco_lead);
		fillWithBlocks(world, box, 7, 10, 10, 11, 10, 10, ModBlocks.hadron_coil_alloy);
		fillWithBlocks(world, box, 7, 11, 10, 11, 11, 10, ModBlocks.deco_lead);
		fillWithBlocks(world, box, 7, 9, 9, 11, 9, 9, ModBlocks.hadron_coil_alloy);
		fillWithBlocks(world, box, 8, 10, 9, 11, 10, 9, ModBlocks.deco_red_copper);
		placeBlockAtCurrentPosition(world, ModBlocks.red_cable_gauge, decoE, 7, 10, 9, box);
		fillWithBlocks(world, box, 7, 11, 9, 11, 11, 9, ModBlocks.hadron_coil_alloy);
		fillWithBlocks(world, box, 7, 9, 8, 11, 9, 8, ModBlocks.deco_lead);
		fillWithBlocks(world, box, 7, 10, 8, 11, 10, 8, ModBlocks.hadron_coil_alloy);
		fillWithBlocks(world, box, 7, 11, 8, 11, 11, 8, ModBlocks.deco_lead);
		
		//Containers
		generateInvContents(world, box, rand, ModBlocks.crate_steel, 2, 4, 9, 7, ItemPool.getPool(ItemPoolsComponent.POOL_NUKE_FUEL), 5);
		//Mines
		fillWithMines(world, box, rand, 1, 9, 7, 6, 9, 11);
		fillWithMines(world, box, rand, 8, 9, 17, 10, 9, 22);
		
		/* Black Sector */
		//Air
		fillWithAir(world, box, 27, 5, 13, 31, 6, 15);
		fillWithAir(world, box, 27, 7, 14, 31, 7, 14);
		fillWithAir(world, box, 28, 2, 11, 31, 3, 15);
		//Floor + Ceiling
		fillWithBlocks(world, box, 28, 0, 11, 31, 0, 15, Blocks.dirt);
		randomlyFillWithBlocks(world, box, rand, 0.5F, 28, 0, 11, 31, 0, 15, Blocks.dirt, 2);
		fillWithBlocks(world, box, 27, 4, 11, 31, 4, 15, ModBlocks.concrete_smooth);
		fillWithBlocks(world, box, 27, 8, 12, 32, 8, 16, ModBlocks.concrete_smooth);
		//Walls
		//N
		fillWithRandomizedBlocks(world, box, 27, 0, 10, 32, 4, 10, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 27, 5, 12, 32, 5, 12, rand, ConcreteBricks);
		fillWithMetadataBlocks(world, box, 27, 6, 12, 32, 6, 12, ModBlocks.concrete_colored, 15);
		fillWithRandomizedBlocks(world, box, 27, 7, 12, 32, 7, 12, rand, ConcreteBricks);
		//E
		fillWithRandomizedBlocks(world, box, 32, 0, 11, 32, 4, 12, rand, ConcreteBricks);
		fillWithRandomizedBlocks(world, box, 32, 0, 13, 32, 5, 15, rand, ConcreteBricks);
		fillWithMetadataBlocks(world, box, 32, 6, 13, 32, 6, 15, ModBlocks.concrete_colored, 15);
		fillWithRandomizedBlocks(world, box, 32, 7, 13, 32, 7, 15, rand, ConcreteBricks);
		//S
		fillWithRandomizedBlocks(world, box, 27, 0, 16, 32, 5, 16, rand, ConcreteBricks);
		fillWithMetadataBlocks(world, box, 27, 6, 16, 32, 6, 16, ModBlocks.concrete_colored, 15);
		fillWithRandomizedBlocks(world, box, 27, 7, 16, 32, 7, 16, rand, ConcreteBricks);
		//W
		fillWithRandomizedBlocks(world, box, 27, 0, 11, 27, 3, 15, rand, ConcreteBricks);
		//Arches
		ConcreteStairs.setMetadata(stairN | 4);
		fillWithRandomizedBlocks(world, box, 27, 7, 15, 31, 7, 15, rand, ConcreteStairs);
		ConcreteStairs.setMetadata(stairS | 4);
		fillWithRandomizedBlocks(world, box, 27, 7, 13, 31, 7, 13, rand, ConcreteStairs);
		//Water
		fillWithBlocks(world, box, 28, 1, 11, 31, 1, 15, Blocks.water);
		//Deco
		fillWithBlocks(world, box, 26, 5, 14, 26, 6, 14, ModBlocks.concrete_smooth); //doorway
		fillWithMetadataBlocks(world, box, 31, 2, 15, 31, 4, 15, ModBlocks.ladder_steel, decoE);
		//Top Room
		randomlyFillWithBlocks(world, box, rand, 0.15F, 27, 5, 13, 30, 6, 15, Blocks.web); //webs
		randomlyFillWithBlocks(world, box, rand, 0.15F, 31, 6, 13, 31, 6, 15, Blocks.web);
		randomlyFillWithBlocks(world, box, rand, 0.15F, 27, 7, 14, 31, 7, 14, Blocks.web);
		
		//Flooded Room
		randomlyFillWithBlocks(world, box, rand, 0.15F, 28, 2, 11, 31, 2, 15, ModBlocks.reeds);
		fillWithMetadataBlocks(world, box, 28, 3, 12, 28, 3, 15, ModBlocks.deco_pipe_framed_green_rusted, pillarNS);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 28, 3, 11, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_rim_green_rusted, 0, 28, 2, 11, box);
		placeBlockAtCurrentPosition(world, ModBlocks.deco_steel, 0, 28, 0, 11, box);
		fillWithBlocks(world, box, 31, 1, 11, 31, 1, 12, ModBlocks.deco_beryllium);
		fillWithMetadataBlocks(world, box, 31, 2, 11, 31, 2, 12, ModBlocks.tape_recorder, decoE);
		
		placeBlockAtCurrentPosition(world, ModBlocks.hev_battery, 0, 30, 2, 11, box);
		
		//Containers
		generateLockableContents(world, box, rand, ModBlocks.safe, decoE, 31, 5, 13, launchKey, 1, 0.1D);
		generateInvContents(world, box, rand, ModBlocks.crate_steel, 2, 31, 5, 14, ItemPool.getPool(ItemPoolsLegacy.POOL_NUKE_TRASH), 5);
		generateInvContents(world, box, rand, ModBlocks.safe, decoE, 31, 5, 15, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 5);
		
		generateInvContents(world, box, rand, ModBlocks.crate_iron, 2, 30, 1, 11, ItemPool.getPool(ItemPoolsLegacy.POOL_EXPENSIVE), 7);
		//Mines
		fillWithMines(world, box, rand, 27, 5, 13, 30, 5, 15);
		
		return true;
	}

	public static WeightedRandomChestContent[] launchKey = new WeightedRandomChestContent[] { new WeightedRandomChestContent(ModItems.launch_key, 0, 1, 1, 1) };
	
	protected void setRTTYFreq(World world, StructureBoundingBox box, int featureX, int featureY, int featureZ, int freq) {
		int posX = this.getXWithOffset(featureX, featureZ);
		int posY = this.getYWithOffset(featureY);
		int posZ = this.getZWithOffset(featureX, featureZ);
		
		if(!box.isVecInside(posX, posY, posZ)) return;
		
		TileEntityRadioTorchBase torch = (TileEntityRadioTorchBase) world.getTileEntity(posX, posY, posZ);
		
		if(torch != null) {
			//for some reason, the silo hatch torch stays at signal 15, despite the others not doing so.
			//this is only an issue with this method changing the existing TE, so it will never occur during natural generation.
			torch.channel = String.valueOf(freq); //int for convenience
			torch.lastState = 0; //just in case
		}
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
	
	public static class SiloSupplies extends BlockSelector {
		
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
			float chance = rand.nextFloat();
			
			if(chance < 0.2F)
				this.field_151562_a = ModBlocks.barrel_corroded;
			else if(chance < 0.4F)
				this.field_151562_a = ModBlocks.crate_can;
			else if(chance < 0.45F)
				this.field_151562_a = ModBlocks.red_barrel;
			else if(chance < 0.5F)
				this.field_151562_a = ModBlocks.pink_barrel;
			else
				this.field_151562_a = Blocks.air;
		}
	}
	//stop-gap because there's no point in replacing all the blockselector classes yet
	//this is basically just an in-line version of it anyway
	protected void fillWithMines(World world, StructureBoundingBox box, Random rand, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		
		if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
			return;
		
		for(int x = minX; x <= maxX; x++) {
			
			for(int z = minZ; z <= maxZ; z++) {
				int posX = getXWithOffset(x, z);
				int posZ = getZWithOffset(x, z);
				
				if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
					for(int y = minY; y <= maxY; y++) {
						int posY = getYWithOffset(y);
						//shouldn't cause issues
						if(rand.nextInt(15) == 0 && world.getBlock(posX, posY, posZ).isAir(world, posX, posY, posZ) && !world.getBlock(posX, posY - 1, posZ).isAir(world, posX, posY - 1, posZ)) {
							world.setBlock(posX, posY, posZ, ModBlocks.mine_ap, 0, 2);
							
							TileEntityLandmine mine = (TileEntityLandmine)world.getTileEntity(posX, posY, posZ);
							if(mine != null)
								mine.waitingForPlayer = true;
						}
					}
				}
			}
		}
	}
	protected void placeCoreLaunchpad(World world, StructureBoundingBox box, Block block, ForgeDirection dir, int x, int y, int z) {
		int posX = getXWithOffset(x, z);
		int posZ = getZWithOffset(x, z);
		int posY = getYWithOffset(y);
		
		if(!box.isVecInside(posX, posY, posZ)) return;
		
		if(dir == null)
			dir = ForgeDirection.NORTH;
		
		dir = getDirection(dir.getOpposite());
		world.setBlock(posX, posY, posZ, block, dir.ordinal() + BlockDummyable.offset, 2);
		
		TileEntity launchpad = world.getTileEntity(posX, posY, posZ);
		if(launchpad instanceof TileEntityLaunchPadRusted) {
			((TileEntityLaunchPadRusted) launchpad).missileLoaded = true;
		}
	}
	//this might be a decent method to have in general actually
	protected StructureBoundingBox getRotatedBoundingBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		switch(this.coordBaseMode) {
		default: //0 & 2
			return new StructureBoundingBox(minX, minY, minZ, minX + maxX, minY + maxY, minZ + maxZ);
		case 1:
		case 3:
			return new StructureBoundingBox(minX, minY, minZ, minX + maxZ, minY + maxY, minZ + maxX);
		}
	}
}
