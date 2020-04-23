package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockReactor extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconAlt;

	public BlockReactor(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		this.iconAlt = iconRegister.registerIcon(RefStrings.MODID + ":code");
		
		if(this == ModBlocks.reactor_conductor)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":reactor_conductor_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":reactor_conductor_side");
		}
		if(this == ModBlocks.reactor_control)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":reactor_control_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":reactor_control_side");
		}
		if(this == ModBlocks.reactor_element)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":reactor_element_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":reactor_element_side");
			this.iconAlt = iconRegister.registerIcon(RefStrings.MODID + ":reactor_element_base");
		}
		if(this == ModBlocks.fusion_conductor)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + /*":fusion_conductor_top_alt"*/":block_steel");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + /*":fusion_conductor_alt"*/":fusion_conductor_side_alt3");
		}
		if(this == ModBlocks.fusion_center)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":fusion_center_top_alt");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":fusion_center_side_alt");
		}
		if(this == ModBlocks.fusion_motor)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":fusion_motor_top_alt");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":fusion_motor_side_alt");
		}
		if(this == ModBlocks.fusion_heater)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":fusion_heater_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":fusion_heater_side");
		}
		if(this == ModBlocks.factory_titanium_conductor)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":factory_titanium_conductor");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":factory_titanium_hull");
		}
		if(this == ModBlocks.factory_advanced_conductor)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":factory_advanced_conductor");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":factory_advanced_hull");
		}
		if(this == ModBlocks.watz_element)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":watz_element_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":watz_element_side");
		}
		if(this == ModBlocks.watz_control)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":watz_control_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":watz_control_side");
		}
		if(this == ModBlocks.watz_conductor)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":watz_conductor_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":watz_conductor_side");
		}
		if(this == ModBlocks.fwatz_conductor)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":block_combine_steel");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":fwatz_conductor_side");
		}
		if(this == ModBlocks.fwatz_cooler)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":fwatz_cooler_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":fwatz_cooler");
		}
		if(this == ModBlocks.block_fiberglass)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":block_fiberglass_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":block_fiberglass_side");
		}
		if(this == ModBlocks.meteor_battery)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":meteor_power");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":meteor_spawner_side");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		if(this == ModBlocks.reactor_element && metadata == 1)
			return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.iconAlt);
		
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}

	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(this != ModBlocks.reactor_element)
			return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
		
		if(player.isSneaking())
		{
			if(world.getBlockMetadata(x, y, z) == 0) {
				world.setBlockMetadataWithNotify(x, y, z, 1, 3);
			} else {
				world.setBlockMetadataWithNotify(x, y, z, 0, 3);
			}
			
			return true;
		}
		
		return false;
	}

}
