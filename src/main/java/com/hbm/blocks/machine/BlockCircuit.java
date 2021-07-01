package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockCircuit extends Block {

	public int strength;
	/**
	 * Construct a new circuit tier
	 * @param mat - Material of the block, usually always iron
	 * @param strength - Calculation strength
	 */
	public BlockCircuit(Material mat, int strength)
	{
		super(mat);
		setStepSound(soundTypeMetal);
		this.strength = strength;
	}
	
	// Disassemble
	@Spaghetti("pain")
	@Override
	public boolean onBlockActivated(World worldIn, int x, int y, int z,
			EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		if (player.isSneaking())
		{
			if (player.getHeldItem() != null && player.getHeldItem().getItem().equals(ModItems.screwdriver))
			{
				List<ItemStack> dropStack = new ArrayList<ItemStack>();
	
				worldIn.setBlockToAir(x, y, z);
				//dropStack = AssemblerRecipes.recipes.get(this);
				if (this == ModBlocks.block_circuit_tier_1)
				{
					dropStack.add(new ItemStack(ModBlocks.steel_scaffold, 1));
					dropStack.add(new ItemStack(ModItems.circuit_targeting_tier1, 6));
					dropStack.add(new ItemStack(ModItems.circuit_aluminium, 2));
					dropStack.add(new ItemStack(Items.comparator, 2));
					dropStack.add(new ItemStack(ModItems.plate_steel, 4));
					dropStack.add(new ItemStack(ModItems.wire_aluminium, 8));
					dropStack.add(new ItemStack(Items.redstone, 6));
				}
				if (this == ModBlocks.block_circuit_tier_2)
				{
					dropStack.add(new ItemStack(ModBlocks.steel_scaffold, 1));
					dropStack.add(new ItemStack(ModItems.circuit_targeting_tier2, 6));
					dropStack.add(new ItemStack(ModItems.circuit_copper, 2));
					dropStack.add(new ItemStack(ModItems.circuit_aluminium, 2));
					dropStack.add(new ItemStack(ModItems.plate_copper, 4));
					dropStack.add(new ItemStack(ModItems.wire_copper, 8));
					dropStack.add(new ItemStack(ModItems.wafer_silicon, 4));
				}
				if (this == ModBlocks.block_circuit_tier_3)
				{
					dropStack.add(new ItemStack(ModBlocks.steel_scaffold, 1));
					dropStack.add(new ItemStack(ModItems.circuit_targeting_tier3, 6));
					dropStack.add(new ItemStack(ModItems.circuit_red_copper, 2));
					dropStack.add(new ItemStack(ModItems.circuit_copper, 2));
					dropStack.add(new ItemStack(ModItems.plate_polymer, 4));
					dropStack.add(new ItemStack(ModItems.wire_red_copper, 8));
					dropStack.add(new ItemStack(ModItems.wafer_gold, 4));
				}
				if (this == ModBlocks.block_circuit_tier_4)
				{
					dropStack.add(new ItemStack(ModBlocks.steel_scaffold, 1));
					dropStack.add(new ItemStack(ModItems.circuit_targeting_tier4, 6));
					dropStack.add(new ItemStack(ModItems.circuit_gold, 2));
					dropStack.add(new ItemStack(ModItems.circuit_red_copper, 2));
					dropStack.add(new ItemStack(ModItems.ingot_polymer, 4));
					dropStack.add(new ItemStack(ModItems.wire_gold, 8));
					dropStack.add(new ItemStack(ModItems.wafer_lapis, 4));
				}
				if (this == ModBlocks.block_circuit_tier_5)
				{
					dropStack.add(new ItemStack(ModBlocks.steel_scaffold, 1));
					dropStack.add(new ItemStack(ModItems.circuit_targeting_tier5, 6));
					dropStack.add(new ItemStack(ModItems.circuit_schrabidium, 2));
					dropStack.add(new ItemStack(ModItems.circuit_gold, 2));
					dropStack.add(new ItemStack(ModItems.ingot_desh, 4));
					dropStack.add(new ItemStack(ModItems.wire_schrabidium, 8));
					dropStack.add(new ItemStack(ModItems.wafer_diamond, 4));
				}
				if (this == ModBlocks.block_circuit_tier_6)
				{
					dropStack.add(new ItemStack(ModBlocks.cmb_brick, 1));
					dropStack.add(new ItemStack(ModItems.plate_desh, 2));
					dropStack.add(new ItemStack(ModItems.circuit_targeting_tier6, 6));
					dropStack.add(new ItemStack(ModItems.circuit_schrabidium, 4));
					dropStack.add(new ItemStack(ModItems.plate_saturnite, 4));
					dropStack.add(new ItemStack(ModItems.wire_schrabidium, 4));
					dropStack.add(new ItemStack(ModItems.wire_magnetized_tungsten, 4));
					dropStack.add(new ItemStack(ModItems.wafer_spark, 2));
					dropStack.add(new ItemStack(ModItems.powder_magic, 6));
				}
				dropItems(dropStack, worldIn, x, y, z);
				return true;
			}
			else
			{
				if (worldIn.isRemote)
				{
					player.addChatMessage(new ChatComponentText("In order to disassemble this supercomputer component without mangling everything, I'll need a more precise tool..."));
				}
			}
		}
		return true;
	}
	
	public void dropItems(List<ItemStack> items, World world, int x, int y, int z)
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
}
