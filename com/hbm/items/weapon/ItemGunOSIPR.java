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

public class ItemGunOSIPR extends ItemGunBase {

	public ItemGunOSIPR(GunConfiguration config, GunConfiguration alt) {
		super(config, alt);
	}
	
	@Override
	protected void altFire(ItemStack stack, World world, EntityPlayer player) {
		
		if(player.inventory.hasItem(ModItems.gun_osipr_ammo2)) {
			setCharge(stack, 1);
			world.playSoundAtEntity(player, "hbm:weapon.osiprCharging", 1.0F, 1F);
		}
	}
	
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		super.updateServer(stack, world, player, slot, isCurrentItem);
		
		if(!isCurrentItem || !player.inventory.hasItem(ModItems.gun_osipr_ammo2)) {
			setCharge(stack, 0);
			return;
		}
		
		int i = getCharge(stack);
		
		if(i >= 20) {
			EntityCombineBall entityarrow = new EntityCombineBall(player.worldObj, player, 3.0F);
			entityarrow.setDamage(1000);
			world.spawnEntityInWorld(entityarrow);
			world.playSoundAtEntity(player, altConfig.firingSound, 1.0F, 1F);
			setCharge(stack, 0);
			setDelay(stack, altConfig.rateOfFire);
			player.inventory.consumeInventoryItem(ModItems.gun_osipr_ammo2);
			
		} else if(i > 0)
			setCharge(stack, i + 1);
	}
	
	protected boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		return super.tryShoot(stack, world, player, main) && getCharge(stack) == 0;
	}
	
	/// CMB charge state ///
	public static void setCharge(ItemStack stack, int i) {
		writeNBT(stack, "cmb_charge", i);
	}
	
	public static int getCharge(ItemStack stack) {
		return readNBT(stack, "cmb_charge");
	}
}
