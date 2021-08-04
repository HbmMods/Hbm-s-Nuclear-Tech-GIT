package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.AssemblerRecipes;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockCircuit extends Block
{

	public int strength;
	/**
	 * Construct a new circuit tier
	 * @param strength - Calculation strength
	 */
	public BlockCircuit(int strength)
	{
		super(Material.iron);
		setStepSound(soundTypeMetal);
		this.strength = strength;
		setCreativeTab(MainRegistry.machineTab);
		setHardness(10.0F);
		setResistance(2.5F);
	}
	
	// Disassemble
	@Override
	public boolean onBlockActivated(World worldIn, int x, int y, int z,
			EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		if (player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.screwdriver)
		{
			List<ItemStack> dropStack = new ArrayList<ItemStack>();
			AStack[] toDrop = AssemblerRecipes.recipes.get(new ComparableStack(this));
			worldIn.setBlockToAir(x, y, z);
			dropItems(toDrop, worldIn, x, y, z);
			return true;
		}
		else if (worldIn.isRemote && player.getHeldItem() == null)
			player.addChatMessage(new ChatComponentText("In order to disassemble this supercomputer component without mangling everything, I'll need a more precise tool..."));
		return super.onBlockActivated(worldIn, x, y, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
	}
	@Deprecated
	private void dropItems(List<ItemStack> items, World world, int x, int y, int z)
	{		
		Random rand = new Random();
		
        float f = rand.nextFloat() * 0.8F + 0.1F;
        float f1 = rand.nextFloat() * 0.8F + 0.1F;
        float f2 = rand.nextFloat() * 0.8F + 0.1F;
    	
        for (ItemStack stack : items)
        {
	        EntityItem entityItem = new EntityItem(world, x + f, y + f1, z + f2, stack);
	        
	        float f3 = 0.05F;
	        entityItem.motionX = (float)rand.nextGaussian() * f3;
	        entityItem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
	        entityItem.motionZ = (float)rand.nextGaussian() * f3;
			if (!world.isRemote)
				world.spawnEntityInWorld(entityItem);
		}
	}
	private void dropItems(AStack[] items, World world, int x, int y, int z)
	{
		Random rand = new Random();
		float[] floats = new float[4];
		for (int i = 0; i < 3; i++)
			floats[i] = rand.nextFloat() * 0.8F + 0.1F;
		
		for (AStack stack : items)
		{
			floats[3] = 0.05F;
			EntityItem entityItem = new EntityItem(world, x + floats[0], y + floats[1], z + floats[2]);
			entityItem.setEntityItemStack(stack.getStack());
			
			entityItem.motionX = (float)rand.nextGaussian() * floats[3];
			entityItem.motionY = (float)rand.nextGaussian() * floats[3] + 0.2F;
			entityItem.motionZ = (float)rand.nextGaussian() * floats[3];
			if (!world.isRemote)
				world.spawnEntityInWorld(entityItem);
		}
	}
}
