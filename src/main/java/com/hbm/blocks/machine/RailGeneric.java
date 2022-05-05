package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.ITooltipProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RailGeneric extends BlockRailBase implements ITooltipProvider {
	
	@SideOnly(Side.CLIENT)
	protected IIcon turnedIcon;
	
	protected static final float baseSpeed = 0.4F;
	protected float maxSpeed = 0.4F;
	protected boolean slopable = true;
	protected boolean flexible = true;

	public RailGeneric() {
		super(false);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return (flexible && meta >= 6) ? this.turnedIcon : this.blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		
		if(flexible)
			this.turnedIcon = reg.registerIcon(this.getTextureName() + "_turned");
	}
	
	@Override
	public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z) {
		return maxSpeed;
	}
	
	public RailGeneric setMaxSpeed(float speed) {
		this.maxSpeed = speed;
		return this;
	}

	@Override
	public boolean isFlexibleRail(IBlockAccess world, int y, int x, int z) {
		return !isPowered();
	}
	
	public RailGeneric setFlexible(boolean flexible) {
		this.flexible = flexible;
		return this;
	}

	@Override
	public boolean canMakeSlopes(IBlockAccess world, int x, int y, int z) {
		return true;
	}
	
	public RailGeneric setSlopable(boolean slopable) {
		this.slopable = slopable;
		return this;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		float speed = this.maxSpeed / this.baseSpeed;
		
		if(speed != 1F) {
			list.add((speed > 1 ? EnumChatFormatting.BLUE : EnumChatFormatting.RED) + "Speed: " + ((int) (speed * 100)) + "%");
		}
		
		if(!flexible) {
			list.add(EnumChatFormatting.RED + "Cannot be used for turns!");
		}
		
		if(!slopable) {
			list.add(EnumChatFormatting.RED + "Cannot be used for slopes!");
		}
	}
}
