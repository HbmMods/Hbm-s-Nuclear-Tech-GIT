package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.bomb.TurretBase;
import com.hbm.tileentity.bomb.TileEntityTurretBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemTurretChip extends Item {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Channel set to " + getFreq(itemstack));
	}
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if((world.getBlock(x, y, z) instanceof TurretBase))
		{
			TileEntity te = world.getTileEntity(x, y, z);
			if(te instanceof TileEntityTurretBase) {
				((TileEntityTurretBase)te).isAI = true;
				((TileEntityTurretBase)te).uuid = player.getUniqueID().toString();
				((TileEntityTurretBase)te).freq = getFreq(stack);
			}
	        if(world.isRemote)
			{
	        	player.addChatMessage(new ChatComponentText("Turret ownership set to: " + player.getDisplayName() + " on channel " + getFreq(stack)));
			}
			world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
        	
	        return true;
		}
    	
        return false;
    }

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		int i = 0;
		
		if(player.isSneaking()) {
			i = (getFreq(stack) - 1);
		} else {
			i = (getFreq(stack) + 1);
		}
		
		if(i == -1)
			i = 511;
		
		if(i == 512)
			i = 0;
		
		setFreq(stack, i);

        if(world.isRemote)
        	player.addChatMessage(new ChatComponentText("Channel set to " + i));

    	world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
		
		player.swingItem();
		
		return stack;
	}
	
	private static int getFreq(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		return stack.stackTagCompound.getInteger("freq");
	}
	
	private static void setFreq(ItemStack stack, int i) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		stack.stackTagCompound.setInteger("freq", i);
	}

}
