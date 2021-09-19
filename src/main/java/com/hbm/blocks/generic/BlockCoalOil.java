package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.tool.ItemToolAbility;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCoalOil extends Block {

	public BlockCoalOil(Material mat) {
		super(mat);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

			Block n = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

			if(n == ModBlocks.ore_coal_oil_burning || n == ModBlocks.balefire || n == Blocks.fire || n.getMaterial() == Material.lava) {
				world.scheduleBlockUpdate(x, y, z, this, world.rand.nextInt(20) + 2);
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		world.setBlock(x, y, z, ModBlocks.ore_coal_oil_burning);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return Items.coal;
	}

	@Override
	public int quantityDropped(Random rand) {
		return 2 + rand.nextInt(2);
	}

	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		
		if(doesToolIgnite(player)) {
			if(world.rand.nextInt(10) == 0)
				world.setBlock(x, y, z, Blocks.fire);
		}
	}

	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {

		if(!world.isRemote)
			return;
		
		if(doesToolIgnite(player)) {

			Random rand = world.rand;
			for(int i = 0; i < 15; i++) {
				Vec3 vec = Vec3.createVectorHelper(1, 0, 0);
				vec.rotateAroundZ((float)(Math.PI * rand.nextDouble() * 2D));
				vec.rotateAroundY((float)(Math.PI * rand.nextDouble() * 2D));
				
				double dX = vec.xCoord;
				double dY = vec.yCoord;
				double dZ = vec.zCoord;

				if(Math.abs(dX) > 1)
					dX /= Math.abs(dX);
				if(Math.abs(dY) > 1)
					dY /= Math.abs(dY);
				if(Math.abs(dX) > 1)
					dZ /= Math.abs(dZ);
				
				world.spawnParticle("flame", x + 0.5 + dX * 0.75, y + 0.5 + dY * 0.75, z + 0.5 + dZ * 0.75, 0.0, 0.0, 0.0);
			}
		}
	}

	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
		world.setBlock(x, y, z, Blocks.fire);
	}
	
	private boolean doesToolIgnite(EntityPlayer player) {

		if(player.getHeldItem() == null)
			return false;

		if(!(player.getHeldItem().getItem() instanceof ItemTool || player.getHeldItem().getItem() instanceof ItemToolAbility))
			return false;

		ItemTool tool = (ItemTool) player.getHeldItem().getItem();

		return tool.func_150913_i() != ToolMaterial.WOOD;
	}
}
