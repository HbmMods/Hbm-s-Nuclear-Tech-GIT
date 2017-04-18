package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class MachineRTG extends Block {

	private final Random field_149933_a = new Random();
	private Random rand;
	
	@SideOnly(Side.CLIENT)
	//private IIcon iconFront;
	private IIcon iconTop;
	private IIcon iconBottom;

    public MachineRTG(Material p_i45386_1_) {
		super(p_i45386_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		String s;
		//if(this == ModBlocks.machine_rtg_blue)
		//	s = "blue";
		/*else*/ if(this == ModBlocks.machine_rtg_cyan)
			s = "cyan";
		//else if(this == ModBlocks.machine_rtg_green)
		//	s = "green";
		else if(this == ModBlocks.machine_rtg_grey)
			s = "grey";
		//else if(this == ModBlocks.machine_rtg_orange)
		//	s = "orange";
		//else if(this == ModBlocks.machine_rtg_purple)
		//	s = "purple";
		//else if(this == ModBlocks.machine_rtg_red)
		//	s = "red";
		//else if(this == ModBlocks.machine_rtg_yellow)
		//	s = "yellow";
		else 
			s = "null";
		
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + (":machine_rtg_top_" + s));
		//this.iconFront = iconRegister.registerIcon(RefStrings.MODID + (":reactor_front"));
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + (":red_wire_coated"));
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_rtg_side_" + s);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}
}
