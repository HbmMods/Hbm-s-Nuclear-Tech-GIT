package com.hbm.items.tool;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.hbm.entity.item.EntityDeliveryDrone;
import com.hbm.items.ItemEnumMulti;
import com.hbm.main.MainRegistry;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemDrone extends ItemEnumMulti {

	public ItemDrone() {
		super(EnumDroneType.class, true, true);
		this.setCreativeTab(MainRegistry.machineTab);
	}

	public static enum EnumDroneType {
		PATROL,
		PATROL_CHUNKLOADING,
		PATROL_EXPRESS,
		PATROL_EXPRESS_CHUNKLOADING,
		REQUEST
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer entity, World world, int x, int y, int z, int side, float fx, float fy, float fz) {

		if(side != 1) return false;
		if(world.isRemote) return true;
		
		Entity toSpawn = null;
		
		if(stack.getItemDamage() < 4) {
			toSpawn = new EntityDeliveryDrone(world);
			if(stack.getItemDamage() % 2 == 1) {
				((EntityDeliveryDrone) toSpawn).setChunkLoading();
			}
			if(stack.getItemDamage() > 1) {
				((EntityDeliveryDrone) toSpawn).getDataWatcher().updateObject(11, (byte) 1);
			}
		}
		
		if(toSpawn != null) {
			toSpawn.setPosition(x + 0.5, y + 1, z + 0.5);
			world.spawnEntityInWorld(toSpawn);
		}
		
		stack.stackSize--;
		
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			for(String s : I18nUtil.resolveKeyArray(stack.getUnlocalizedName() + ".desc"))
				list.add(EnumChatFormatting.YELLOW + s);
		} else {
			list.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC + "Hold <" + EnumChatFormatting.YELLOW + "" + EnumChatFormatting.ITALIC + "LSHIFT" + EnumChatFormatting.DARK_GRAY
					+ "" + EnumChatFormatting.ITALIC + "> to display more info");
		}
	}
}
