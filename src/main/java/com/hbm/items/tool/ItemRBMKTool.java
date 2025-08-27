package com.hbm.items.tool;

import java.util.List;

import com.google.common.collect.Multimap;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.tileentity.machine.rbmk.TileEntityCraneConsole;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemRBMKTool extends Item {
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fX, float fY, float fZ) {
		
		Block b = world.getBlock(x, y, z);
		
		if(b instanceof RBMKBase) {
			
			int[] pos = ((BlockDummyable)b).findCore(world, x, y, z);
			
			if(pos != null && !world.isRemote) {
				
				if(!stack.hasTagCompound())
					stack.stackTagCompound = new NBTTagCompound();

				stack.stackTagCompound.setInteger("posX", pos[0]);
				stack.stackTagCompound.setInteger("posY", pos[1]);
				stack.stackTagCompound.setInteger("posZ", pos[2]);
				
				player.addChatComponentMessage(new ChatComponentTranslation(this.getUnlocalizedName() + ".linked").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
			}
			
			return true;
		}
		
		if(b == ModBlocks.rbmk_console && stack.hasTagCompound()) {
			
			if(!world.isRemote) {
				
				int[] pos = ((BlockDummyable)b).findCore(world, x, y, z);
				
				TileEntityRBMKConsole console = (TileEntityRBMKConsole)world.getTileEntity(pos[0], pos[1], pos[2]);
				int tx = stack.stackTagCompound.getInteger("posX");
				int ty = stack.stackTagCompound.getInteger("posY");
				int tz = stack.stackTagCompound.getInteger("posZ");
				console.setTarget(tx, ty, tz);
				player.addChatComponentMessage(new ChatComponentTranslation(this.getUnlocalizedName() + ".set").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
			}
			
			return true;
		}
		
		if(b == ModBlocks.rbmk_crane_console && stack.hasTagCompound()) {
			
			if(!world.isRemote) {
				
				int[] pos = ((BlockDummyable)b).findCore(world, x, y, z);
				
				TileEntityCraneConsole console = (TileEntityCraneConsole)world.getTileEntity(pos[0], pos[1], pos[2]);
				int tx = stack.stackTagCompound.getInteger("posX");
				int ty = stack.stackTagCompound.getInteger("posY");
				int tz = stack.stackTagCompound.getInteger("posZ");
				console.setTarget(tx, ty, tz);
				player.addChatComponentMessage(new ChatComponentTranslation(this.getUnlocalizedName() + ".set").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		for(String s : I18nUtil.resolveKeyArray(this.getUnlocalizedName() + ".desc"))
			list.add(EnumChatFormatting.YELLOW + s);
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		
		Multimap multimap = super.getAttributeModifiers(stack);
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 2, 0));
		return multimap;
	}
}
