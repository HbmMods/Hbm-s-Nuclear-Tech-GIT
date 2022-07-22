package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityWatzCore;

import api.hbm.fluid.IFluidConnectorBlock;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class WatzHatch extends Block implements IFluidConnectorBlock {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	public WatzHatch(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":watz_hatch");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":reinforced_brick");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return metadata == 0 && side == 3 ? this.iconFront : (side == metadata ? this.iconFront : this.blockIcon);
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(this);
    }
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		//this.setDefaultDirection(world, x, y, z);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			if(world.getBlockMetadata(x, y, z) == 2)
			{
				if(world.getTileEntity(x, y, z + 3) instanceof TileEntityWatzCore)
				{
					if(((TileEntityWatzCore)world.getTileEntity(x, y, z + 3)).isStructureValid(world))
					{
						FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_watz_multiblock, world, x, y, z + 3);
					} else {
						player.addChatMessage(new ChatComponentText("[Watz Power Plant] Error: Reactor Structure not valid!"));
					}
				} else {
					player.addChatMessage(new ChatComponentText("[Watz Power Plant] Error: Reactor Core not found!"));
				}
			}
			if(world.getBlockMetadata(x, y, z) == 3)
			{
				if(world.getTileEntity(x, y, z - 3) instanceof TileEntityWatzCore)
				{
					if(((TileEntityWatzCore)world.getTileEntity(x, y, z - 3)).isStructureValid(world))
					{
						FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_watz_multiblock, world, x, y, z - 3);
					} else {
						player.addChatMessage(new ChatComponentText("[Watz Power Plant] Error: Reactor Structure not valid!"));
					}
				} else {
					player.addChatMessage(new ChatComponentText("[Watz Power Plant] Error: Reactor Core not found!"));
				}
			}
			if(world.getBlockMetadata(x, y, z) == 4)
			{
				if(world.getTileEntity(x + 3, y, z) instanceof TileEntityWatzCore)
				{
					if(((TileEntityWatzCore)world.getTileEntity(x + 3, y, z)).isStructureValid(world))
					{
						FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_watz_multiblock, world, x + 3, y, z);
					} else {
						player.addChatMessage(new ChatComponentText("[Watz Power Plant] Error: Reactor Structure not valid!"));
					}
				} else {
					player.addChatMessage(new ChatComponentText("[Watz Power Plant] Error: Reactor Core not found!"));
				}
			}
			if(world.getBlockMetadata(x, y, z) == 5)
			{
				if(world.getTileEntity(x - 3, y, z) instanceof TileEntityWatzCore)
				{
					if(((TileEntityWatzCore)world.getTileEntity(x - 3, y, z)).isStructureValid(world))
					{
						FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_watz_multiblock, world, x - 3, y, z);
					} else {
						player.addChatMessage(new ChatComponentText("[Watz Power Plant] Error: Reactor Structure not valid!"));
					}
				} else {
					player.addChatMessage(new ChatComponentText("[Watz Power Plant] Error: Reactor Core not found!"));
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canConnect(FluidType type, IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		return type == Fluids.WATZ;
	}
}
