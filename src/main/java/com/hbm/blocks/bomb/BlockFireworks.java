package com.hbm.blocks.bomb;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityFireworks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockFireworks extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconBottom;

	public BlockFireworks(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":fireworks_top");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":fireworks_bottom");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":fireworks_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFireworks();
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float fx, float fy, float fz) {
		
		if(world.isRemote)
			return true;
		
		TileEntityFireworks te = (TileEntityFireworks)world.getTileEntity(x, y, z);
		
		if(player.getHeldItem() != null) {
			
			if(player.getHeldItem().getItem() == Items.gunpowder) {
				te.charges += player.getHeldItem().stackSize * 3;
				te.markDirty();
				player.getHeldItem().stackSize = 0;
				return true;
			}
			
			if(player.getHeldItem().getItem() == ModItems.sulfur) {
				te.charges += player.getHeldItem().stackSize;
				te.markDirty();
				player.getHeldItem().stackSize = 0;
				return true;
			}
			
			if(player.getHeldItem().getItem() instanceof ItemDye) {
				te.color = ItemDye.field_150922_c[player.getHeldItem().getItemDamage()];
				te.markDirty();
				player.getHeldItem().stackSize--;
				return true;
			}
			
			if(player.getHeldItem().getItem() == Items.name_tag) {
				te.message = player.getHeldItem().getDisplayName();
				te.markDirty();
				player.getHeldItem().stackSize--;
				return true;
			}
		}

		player.addChatComponentMessage(new ChatComponentTranslation(this.getUnlocalizedName() + ".name").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
		player.addChatComponentMessage(new ChatComponentTranslation(this.getUnlocalizedName() + ".charges", te.charges).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
		player.addChatComponentMessage(new ChatComponentTranslation(this.getUnlocalizedName() + ".color", Integer.toHexString(te.color)).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
		player.addChatComponentMessage(new ChatComponentTranslation(this.getUnlocalizedName() + ".message", te.message).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
		
		return true;
	}

}
