package com.hbm.items.weapon;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityCombineBall;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;
import com.hbm.sound.AudioWrapper;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;

public class ItemGunGauss extends ItemGunBase {
	
	private AudioWrapper chargeLoop;

	public ItemGunGauss(GunConfiguration config, GunConfiguration alt) {
		super(config, alt);
	}
	
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main) {

		if(getHasShot(stack)) {
			world.playSoundAtEntity(player, "hbm:weapon.sparkShoot", 1.0F, 1.0F);
			setHasShot(stack, false);
		}
	}
	
	public void endActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) {

		if(chargeLoop != null) {
			chargeLoop.stopSound();
			chargeLoop = null;
		}
		//setCharge(stack, 0);
	}
	
	@Override
	public void startActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) {

		if(!main) {
			setCharge(stack, 1);
			chargeLoop = MainRegistry.proxy.getLoopedSound("hbm:weapon.tauChargeLoop2", (float)player.posX, (float)player.posY, (float)player.posZ, 1.0F, 0.75F);
			world.playSoundAtEntity(player, "hbm:weapon.tauChargeLoop2", 1.0F, 0.75F);
			
			if(chargeLoop != null) {
				chargeLoop.startSound();
			}
		}
	}
	
	protected void updateClient(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		super.updateClient(stack, world, player, slot, isCurrentItem);
		
		/*if(!isCurrentItem) {
			setCharge(stack, 0);
			if(chargeLoop != null) {
				chargeLoop.stopSound();
				chargeLoop = null;
			}
			return;
		}*/
		
		int c = getCharge(stack);
		
		if(c > 0) {
			setCharge(stack, c + 1);
			System.out.println(c);
		}

		if(chargeLoop != null) {
			chargeLoop.updatePosition((float)player.posX, (float)player.posY, (float)player.posZ);
			chargeLoop.updatePitch(1.0F);
		}
	}
	
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		
		super.spawnProjectile(world, player, stack, config);
		setHasShot(stack, true);
	}
	
	public static void setHasShot(ItemStack stack, boolean b) {
		writeNBT(stack, "hasShot", b ? 1 : 0);
	}
	
	public static boolean getHasShot(ItemStack stack) {
		return readNBT(stack, "hasShot") == 1;
	}
	
	/// gauss charge state ///
	public static void setCharge(ItemStack stack, int i) {
		writeNBT(stack, "gauss_charge", i);
	}
	
	public static int getCharge(ItemStack stack) {
		return readNBT(stack, "gauss_charge");
	}
}
