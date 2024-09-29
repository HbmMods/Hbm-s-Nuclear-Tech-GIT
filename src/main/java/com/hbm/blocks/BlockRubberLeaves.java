package com.hbm.blocks;

import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BlockRubberLeaves extends Block {
	

	public BlockRubberLeaves(Material mat) {
		super(mat);
		this.setTickRandomly(true);
	}



	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return ModItems.ingot_polymer;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	protected boolean canSilkHarvest() {
		return false;
	}
}
