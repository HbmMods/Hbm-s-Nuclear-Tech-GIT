package com.hbm.tileentity;

import com.hbm.interfaces.ICopiable;
import com.hbm.inventory.material.Mats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IMetalCopiable extends ICopiable {
	
	int[] getMatsToCopy();

	@Override
	default NBTTagCompound getSettings(World world, int x, int y, int z) {
		NBTTagCompound tag = new NBTTagCompound();
		if(getMatsToCopy().length > 0) tag.setIntArray("matFilter", getMatsToCopy());
		return tag;
	}

	@Override
	default String[] infoForDisplay(World world, int x, int y, int z) {
		int[] ids = getMatsToCopy();
		String[] names = new String[ids.length];
		for(int i = 0; i < ids.length; i++) names[i] = Mats.matById.get(ids[i]).getUnlocalizedName();
		return names;
	}

	@Override
	default void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) { };
}
