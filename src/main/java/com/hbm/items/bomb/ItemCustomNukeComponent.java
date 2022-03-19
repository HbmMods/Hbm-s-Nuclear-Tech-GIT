package com.hbm.items.bomb;

import com.hbm.tileentity.bomb.TileEntityNukeCustom.EnumBombType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemCustomNukeComponent extends Item {
	
	//Use getItemDamage() for the boolean type
	public void writeToNBT(ItemStack stack, EnumBombType stage, float value, float multiplier) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setByte("stage", (byte)stage.ordinal());
		stack.stackTagCompound.setFloat("value", value);
		stack.stackTagCompound.setFloat("multiplier", multiplier);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote) {
			player.addChatComponentMessage(new ChatComponentText("eat this, you gay pony").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
		}
		
		return stack;
	}
	
	private IIcon secondaryIcon;
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		
		if(getHasSubtypes()) {
			secondaryIcon = reg.registerIcon(this.getIconString() + ".secondary");
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		
		if(meta == 1 && secondaryIcon != null) {
			return this.secondaryIcon;
		} else {
			return this.itemIcon;
		}
	}
}
