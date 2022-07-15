package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.EnumGUI;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge;
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
import net.minecraft.world.World;

public class ReactorHatch extends Block {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	public ReactorHatch(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":reactor_hatch");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":brick_concrete");
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
	
	private void setDefaultDirection(World world, int x, int y, int z) {
		if(!world.isRemote)
		{
			Block block1 = world.getBlock(x, y, z - 1);
			Block block2 = world.getBlock(x, y, z + 1);
			Block block3 = world.getBlock(x - 1, y, z);
			Block block4 = world.getBlock(x + 1, y, z);
			
			byte b0 = 3;
			
			if(block1.func_149730_j() && !block2.func_149730_j())
			{
				b0 = 3;
			}
			if(block2.func_149730_j() && !block1.func_149730_j())
			{
				b0 = 2;
			}
			if(block3.func_149730_j() && !block4.func_149730_j())
			{
				b0 = 5;
			}
			if(block4.func_149730_j() && !block3.func_149730_j())
			{
				b0 = 4;
			}
			
			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
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
				if(world.getTileEntity(x, y, z + 2) instanceof TileEntityMachineReactorLarge)
				{
					if(((TileEntityMachineReactorLarge)world.getTileEntity(x, y, z + 2)).checkBody())
					{
						FMLNetworkHandler.openGui(player, MainRegistry.instance, EnumGUI.REACTOR_GENERIC_LARGE.ordinal(), world, x, y, z + 2);
					} else {
						player.addChatMessage(new ChatComponentText("[Nuclear Reactor] Error: Reactor Structure not valid!"));
					}
				} else {
					player.addChatMessage(new ChatComponentText("[Nuclear Reactor Error: Reactor Core not found!"));
				}
			}
			if(world.getBlockMetadata(x, y, z) == 3)
			{
				if(world.getTileEntity(x, y, z - 2) instanceof TileEntityMachineReactorLarge)
				{
					if(((TileEntityMachineReactorLarge)world.getTileEntity(x, y, z - 2)).checkBody())
					{
						FMLNetworkHandler.openGui(player, MainRegistry.instance, EnumGUI.REACTOR_GENERIC_LARGE.ordinal(), world, x, y, z - 2);
					} else {
						player.addChatMessage(new ChatComponentText("[Nuclear Reactor] Error: Reactor Structure not valid!"));
					}
				} else {
					player.addChatMessage(new ChatComponentText("[Nuclear Reactor Error: Reactor Core not found!"));
				}
			}
			if(world.getBlockMetadata(x, y, z) == 4)
			{
				if(world.getTileEntity(x + 2, y, z) instanceof TileEntityMachineReactorLarge)
				{
					if(((TileEntityMachineReactorLarge)world.getTileEntity(x + 2, y, z)).checkBody())
					{
						FMLNetworkHandler.openGui(player, MainRegistry.instance, EnumGUI.REACTOR_GENERIC_LARGE.ordinal(), world, x + 2, y, z);
					} else {
						player.addChatMessage(new ChatComponentText("[Nuclear Reactor] Error: Reactor Structure not valid!"));
					}
				} else {
					player.addChatMessage(new ChatComponentText("[Nuclear Reactor Error: Reactor Core not found!"));
				}
			}
			if(world.getBlockMetadata(x, y, z) == 5)
			{
				if(world.getTileEntity(x - 2, y, z) instanceof TileEntityMachineReactorLarge)
				{
					if(((TileEntityMachineReactorLarge)world.getTileEntity(x - 2, y, z)).checkBody())
					{
						FMLNetworkHandler.openGui(player, MainRegistry.instance, EnumGUI.REACTOR_GENERIC_LARGE.ordinal(), world, x - 2, y, z);
					} else {
						player.addChatMessage(new ChatComponentText("[Nuclear Reactor] Error: Reactor Structure not valid!"));
					}
				} else {
					player.addChatMessage(new ChatComponentText("[Nuclear Reactor Error: Reactor Core not found!"));
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
