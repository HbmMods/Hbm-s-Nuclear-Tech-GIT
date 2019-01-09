package com.hbm.items.weapon;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemGunBase extends Item implements IHoldableWeapon {

	private GunConfiguration mainConfig;
	private GunConfiguration altConfig;
	
	private boolean m1 = false;
	private boolean m2 = false;
	
	public ItemGunBase(GunConfiguration config) {
		mainConfig = config;
	}
	
	public ItemGunBase(GunConfiguration config, GunConfiguration alt) {
		mainConfig = config;
		altConfig = alt;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return stack;
	}

	@Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && entity instanceof EntityPlayer && world.isRemote) {
			updateClient(stack, world, (EntityPlayer)entity, slot, isCurrentItem);
		} else {
			updateServer(stack, world, (EntityPlayer)entity, slot, isCurrentItem);
		}
    	
    }
	
	private void updateClient(ItemStack stack, World world, EntityPlayer entity, int slot, boolean isCurrentItem) {
		
		boolean clickLeft = Mouse.isButtonDown(0);
		boolean clickRight = Mouse.isButtonDown(1);
		boolean left = m1; //getIsMouseDown(stack);
		boolean right = m2; //getIsAltDown(stack);
		
		if(isCurrentItem) {
			if(left && right) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0));
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1));
				//setIsMouseDown(stack, false);
				//setIsAltDown(stack, false);
				m1 = false;
				m2 = false;
			}
			
			if(!left && !right) {
				if(clickLeft) {
					PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 0));
					//setIsMouseDown(stack, true);
					m1 = true;
				} else if(clickRight) {
					PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 1));
					//setIsAltDown(stack, true);
					m2 = true;
				}
			}
			
			if(left && !clickLeft) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0));
				//setIsMouseDown(stack, false);
				m1 = false;
			}
			
			if(right && !clickRight) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1));
				//setIsAltDown(stack, false);
				m2 = false;
			}
			
			if(mainConfig.reloadType != 0 || (altConfig != null && altConfig.reloadType != 0)) {
				
				if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
					PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 2));
					setIsReloading(stack, true);
				}
			}
		}
	}
	
	private void updateServer(ItemStack stack, World world, EntityPlayer entity, int slot, boolean isCurrentItem) {
		
	}
	
	//called every time the gun shoots
	private void fire(ItemStack stack, World world, EntityPlayer player) {
		
	}
	
	//called on click
	private void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
	}
	
	//called on click release
	private void endAction(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
	}
	
	//reload action, if existent
	private void reload(ItemStack stack, World world, EntityPlayer player) {
		
	}
	
	//item mouseover text
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
	}
	
	/*//returns main config from itemstack
	public static GunConfiguration extractConfig(ItemStack stack) {
		
		if(stack != null && stack.getItem() instanceof ItemGunBase) {
			return ((ItemGunBase)stack.getItem()).mainConfig;
		}
		
		return null;
	}*/
	
	/// if reloading routine is active ///
	public static void setIsReloading(ItemStack stack, boolean b) {
		writeNBT(stack, "isReloading", b ? 1 : 0);
	}
	
	public static boolean getIsReloading(ItemStack stack) {
		return readNBT(stack, "isReloading") == 1;
	}
	
	/// if left mouse button is down ///
	public static void setIsMouseDown(ItemStack stack, boolean b) {
		writeNBT(stack, "isMouseDown", b ? 1 : 0);
	}
	
	public static boolean getIsMouseDown(ItemStack stack) {
		return readNBT(stack, "isMouseDown") == 1;
	}
	
	/// if alt mouse button is down ///
	public static void setIsAltDown(ItemStack stack, boolean b) {
		writeNBT(stack, "isMouseDown", b ? 1 : 0);
	}
	
	public static boolean getIsAltDown(ItemStack stack) {
		return readNBT(stack, "isMouseDown") == 1;
	}
	
	/// RoF cooldown ///
	public static void setDelay(ItemStack stack, int i) {
		writeNBT(stack, "dlay", i);
	}
	
	public static int getDelay(ItemStack stack) {
		return readNBT(stack, "dlay");
	}
	
	/// R/W cycle animation timer ///
	public static void setCycleAnim(ItemStack stack, int i) {
		writeNBT(stack, "cycle", i);
	}
	
	public static int getCycleAnim(ItemStack stack) {
		return readNBT(stack, "cycle");
	}
	
	/// R/W reload animation timer ///
	public static void setReloadCycle(ItemStack stack, int i) {
		writeNBT(stack, "reload", i);
	}
	
	public static int getReloadCycle(ItemStack stack) {
		return readNBT(stack, "reload");
	}
	
	/// magazine capacity ///
	public static void setMag(ItemStack stack, int i) {
		writeNBT(stack, "magazine", i);
	}
	
	public static int getMag(ItemStack stack) {
		return readNBT(stack, "magazine");
	}
	
	/// magazine type (int specified by index in bullet config list) ///
	public static void setMagType(ItemStack stack, int i) {
		writeNBT(stack, "magazineType", i);
	}
	
	public static int getMagType(ItemStack stack) {
		return readNBT(stack, "magazineType");
	}
	
	/// NBT utility ///
	private static void writeNBT(ItemStack stack, String key, int value) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger(key, value);
	}
	
	private static int readNBT(ItemStack stack, String key) {
		
		if(!stack.hasTagCompound())
			return 0;
		
		return stack.stackTagCompound.getInteger(key);
	}

}
