package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.items.food.ItemConserve.EnumFoodType;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockCanCrate extends Block {

	public BlockCanCrate(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if(player.getHeldItem() != null && player.getHeldItem().getItem().equals(ModItems.crowbar)) {
			if(!world.isRemote) {
				dropContents(world, x, y, z);
				world.setBlockToAir(x, y, z);
				world.playSoundEffect(x, y, z, "hbm:block.crateBreak", 0.5F, 1.0F);
			}
			return true;
		}
		return false;
	}

	Random rand = new Random();

	public void dropContents(World world, int x, int y, int z) {
		ArrayList<ItemStack> items = getContents(world, x, y, z);

		for(ItemStack item : items) {
			this.dropBlockAsItem(world, x, y, z, item);
		}
	}

	public ArrayList<ItemStack> getContents(World world, int x, int y, int z) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		int count = getContentAmount(world.rand);
		for(int i = 0; i < count; i++) {
			ret.add(getRandomItem(world.rand));
		}

		return ret;
	}

	public ItemStack getRandomItem(Random rand) {

		List<ItemStack> items = new ArrayList();
		for(int a = 0; a < EnumFoodType.values().length; a++)
			items.add(new ItemStack(ModItems.canned_conserve, 1, a));
		items.add(new ItemStack(ModItems.can_smart));
		items.add(new ItemStack(ModItems.can_creature));
		items.add(new ItemStack(ModItems.can_redbomb));
		items.add(new ItemStack(ModItems.can_mrsugar));
		items.add(new ItemStack(ModItems.can_overcharge));
		items.add(new ItemStack(ModItems.can_luna));
		items.add(new ItemStack(ModItems.can_breen));
		items.add(new ItemStack(ModItems.can_bepis));
		items.add(new ItemStack(ModItems.pudding));

		return items.get(rand.nextInt(items.size()));
	}

	public int getContentAmount(Random rand) {
		return 5 + rand.nextInt(4);
	}
}
