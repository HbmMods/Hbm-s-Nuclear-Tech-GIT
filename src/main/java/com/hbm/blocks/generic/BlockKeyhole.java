package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockLoot.TileEntityLoot;
import com.hbm.blocks.generic.BlockPedestal.TileEntityPedestal;
import com.hbm.itempool.ItemPool;
import com.hbm.itempool.ItemPoolsRedRoom;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemModDoor;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockStone;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockKeyhole extends BlockStone {

	protected IIcon iconTop;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconTop = iconRegister.registerIcon("stone");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? this.iconTop : this.blockIcon;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return new ItemStack(Blocks.stone);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(player.getHeldItem() != null) {
			boolean cracked = player.getHeldItem().getItem() == ModItems.key_red_cracked;
			if((player.getHeldItem().getItem() == ModItems.key_red || cracked) && side != 0 && side != 1) {
				if(cracked) player.getHeldItem().stackSize--;
				if(world.isRemote) return true;
				ForgeDirection dir = ForgeDirection.getOrientation(side);
				generateRoom(world, x - dir.offsetX * 4, y - 2, z - dir.offsetZ * 4);
				int b = 0;
				if(side == 2) b = 1;
				if(side == 5) b = 2;
				if(side == 3) b = 3;
				if(side == 4) b = 0;
				ItemModDoor.placeDoorBlock(world, x, y - 1, z, b, ModBlocks.door_red);
				world.playSoundAtEntity(player, "hbm:block.lockOpen", 1.0F, 1.0F);
				player.triggerAchievement(MainRegistry.achRedRoom);
				return true;
			}
		}
		
		return false;
	}
	
	protected static void generateRoom(World world, int x, int y, int z) {
		
		int size = 9;
		int height = 5;
		int width = size / 2;
		
		//Outer Edges, top and bottom
		for(int i = -width; i <= width; i++) {
			world.setBlock(x + i, y, z + width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x + i, y, z - width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x + width, y, z + i, ModBlocks.brick_red, 6, 3);
			world.setBlock(x - width, y, z + i, ModBlocks.brick_red, 6, 3);
			world.setBlock(x + i, y + height - 1, z + width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x + i, y + height - 1, z - width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x + width, y + height - 1, z + i, ModBlocks.brick_red, 6, 3);
			world.setBlock(x - width, y + height - 1, z + i, ModBlocks.brick_red, 6, 3);
		}
		
		for(int i = 1; i <= height - 2; i++) {
			//Outer edges, sides
			world.setBlock(x + width, y + i, z + width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x + width, y + i, z - width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x - width, y + i, z + width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x - width, y + i, z - width, ModBlocks.brick_red, 6, 3);
			
			//Walls
			for(int j = -width + 1; j <= width - 1; j++) {
				world.setBlock(x + width, y + i, z + j, ModBlocks.brick_red, 4, 3);
				world.setBlock(x - width, y + i, z + j, ModBlocks.brick_red, 5, 3);
				world.setBlock(x + j, y + i, z + width, ModBlocks.brick_red, 2, 3);
				world.setBlock(x + j, y + i, z - width, ModBlocks.brick_red, 3, 3);
			}
		}
		
		if(world.rand.nextInt(1) == 0) {
			int r = world.rand.nextInt(4);
			if(r == 0) world.setBlock(x + width, y + 2, z, ModBlocks.stone_keyhole_meta, 4, 3);
			if(r == 1) world.setBlock(x - width, y + 2, z, ModBlocks.stone_keyhole_meta, 5, 3);
			if(r == 2) world.setBlock(x, y + 2, z + width, ModBlocks.stone_keyhole_meta, 2, 3);
			if(r == 3) world.setBlock(x, y + 2, z - width, ModBlocks.stone_keyhole_meta, 3, 3);
		}
		
		for(int i = -width + 1; i <= width - 1; i++) {
			for(int j = -width + 1; j <= width - 1; j++) {
				//Floor and ceiling
				world.setBlock(x + i, y, z + j, ModBlocks.brick_red, 1, 3);
				world.setBlock(x + i, y + height - 1, z + j, ModBlocks.brick_red, 0, 3);
				
				for(int k = 1; k <= height - 2; k++) {
					world.setBlock(x + i, y + k, z + j, Blocks.air);
				}
			}
		}

		//Torches
		int torchDist = width - 1;
		int torchOff = torchDist - 1;
		world.setBlock(x + torchDist, y + 2, z + torchOff, Blocks.torch);
		world.setBlock(x + torchDist, y + 2, z - torchOff, Blocks.torch);
		world.setBlock(x - torchDist, y + 2, z + torchOff, Blocks.torch);
		world.setBlock(x - torchDist, y + 2, z - torchOff, Blocks.torch);
		world.setBlock(x + torchOff, y + 2, z + torchDist, Blocks.torch);
		world.setBlock(x - torchOff, y + 2, z + torchDist, Blocks.torch);
		world.setBlock(x + torchOff, y + 2, z - torchDist, Blocks.torch);
		world.setBlock(x - torchOff, y + 2, z - torchDist, Blocks.torch);
		
		//Cobwebs
		if(world.rand.nextInt(4) == 0) {
			for(int i = -width + 1; i <= width - 1; i++) {
				for(int j = -width + 1; j <= width - 1; j++) {
					if(world.rand.nextBoolean()) world.setBlock(x + i, y + height - 2, z + j, Blocks.web);
				}
			}
		}
		
		//Pillars
		if(world.rand.nextInt(4) == 0) {
			for(int i = 1; i <= height - 2; i++) {
				world.setBlock(x + width - 2, y + i, z + width - 2, ModBlocks.concrete_colored, 14, 3);
				world.setBlock(x + width - 2, y + i, z - width + 2, ModBlocks.concrete_colored, 14, 3);
				world.setBlock(x - width + 2, y + i, z + width - 2, ModBlocks.concrete_colored, 14, 3);
				world.setBlock(x - width + 2, y + i, z - width + 2, ModBlocks.concrete_colored, 14, 3);
			}
		}
		
		//Fire
		if(world.rand.nextInt(4) == 0) {
			world.setBlock(x + width - 1, y, z + width - 1, Blocks.netherrack);
			world.setBlock(x + width - 1, y, z - width + 1, Blocks.netherrack);
			world.setBlock(x - width + 1, y, z + width - 1, Blocks.netherrack);
			world.setBlock(x - width + 1, y, z - width + 1, Blocks.netherrack);
			world.setBlock(x + width - 1, y + 1, z + width - 1, Blocks.fire);
			world.setBlock(x + width - 1, y + 1, z - width + 1, Blocks.fire);
			world.setBlock(x - width + 1, y + 1, z + width - 1, Blocks.fire);
			world.setBlock(x - width + 1, y + 1, z - width + 1, Blocks.fire);
		}
		
		//Circle
		if(world.rand.nextInt(4) == 0) {
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
					if(i != 0 || j != 0) world.setBlock(x + i, y, z + j, ModBlocks.concrete_colored, 14, 3);
				}
			}
		}
		
		//Lava
		if(world.rand.nextInt(4) == 0) {
			world.setBlock(x + width - 2, y, z + width - 1, Blocks.lava);
			world.setBlock(x + width - 3, y, z + width - 1, Blocks.lava);
			world.setBlock(x - width + 2, y, z + width - 1, Blocks.lava);
			world.setBlock(x - width + 3, y, z + width - 1, Blocks.lava);
			world.setBlock(x + width - 2, y, z - width + 1, Blocks.lava);
			world.setBlock(x + width - 3, y, z - width + 1, Blocks.lava);
			world.setBlock(x - width + 2, y, z - width + 1, Blocks.lava);
			world.setBlock(x - width + 3, y, z - width + 1, Blocks.lava);
			world.setBlock(x + width - 1, y, z + width - 2, Blocks.lava);
			world.setBlock(x + width - 1, y, z + width - 3, Blocks.lava);
			world.setBlock(x + width - 1, y, z - width + 2, Blocks.lava);
			world.setBlock(x + width - 1, y, z - width + 3, Blocks.lava);
			world.setBlock(x - width + 1, y, z + width - 2, Blocks.lava);
			world.setBlock(x - width + 1, y, z + width - 3, Blocks.lava);
			world.setBlock(x - width + 1, y, z - width + 2, Blocks.lava);
			world.setBlock(x - width + 1, y, z - width + 3, Blocks.lava);
		}
		
		int rand = world.rand.nextInt(20);
		
		if(rand == 0) {
	 		world.setBlock(x, y + 1, z, ModBlocks.deco_loot);
			TileEntityLoot loot = (TileEntityLoot) world.getTileEntity(x, y + 1, z);
			loot.addItem(new ItemStack(ModItems.trenchmaster_helmet), 0, 0, 0);
			loot.addItem(new ItemStack(ModItems.trenchmaster_plate), 0, 0, 0);
			loot.addItem(new ItemStack(ModItems.trenchmaster_legs), 0, 0, 0);
			loot.addItem(new ItemStack(ModItems.trenchmaster_boots), 0, 0, 0);
		} else {
			spawnPedestalItem(world, x, y + 1, z);
		}
		
		//Clear dropped items
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x + 0.5, y, z + 0.5, x + 0.5, y + height, z + 0.5).expand(size / 2D, 0, size / 2D));
		for(EntityItem item : items) item.setDead();
	}
	
	public static void spawnPedestalItem(World world, int x, int y, int z) {
		world.setBlock(x, y, z, ModBlocks.pedestal);
		TileEntityPedestal pedestal = (TileEntityPedestal) world.getTileEntity(x, y, z);
		WeightedRandomChestContent content = (WeightedRandomChestContent) WeightedRandom.getRandomItem(world.rand, ItemPool.getPool(ItemPoolsRedRoom.POOL_RED_PEDESTAL));
		pedestal.item = content.theItemId.copy();
	}
}
