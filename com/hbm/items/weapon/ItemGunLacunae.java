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

public class ItemGunLacunae extends ItemGunBase {
	
	public ItemGunLacunae(GunConfiguration config) {
		super(config);
	}
	
	@Override
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {

		setDelay(stack, 20);
		world.playSoundAtEntity(player, "hbm:weapon.lacunaeSpinup", 1.0F, 1.0F);
	}
	
	@Override
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		world.playSoundAtEntity(player, "hbm:weapon.lacunaeSpindown", 1.0F, 1.0F);
	}
	
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		
		super.updateServer(stack, world, player, slot, isCurrentItem);
		
		if(getIsMouseDown(stack)) {
			
			int rot = readNBT(stack, "rot") % 360;
			rot += 25;
			writeNBT(stack, "rot", rot);
		}
	}
}
