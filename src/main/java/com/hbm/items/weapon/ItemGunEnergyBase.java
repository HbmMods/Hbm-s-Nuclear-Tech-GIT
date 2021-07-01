package com.hbm.items.weapon;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.hbm.config.GeneralConfig;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.handler.GunConfigurationEnergy;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.interfaces.IItemHUD;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.lib.Library;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class ItemGunEnergyBase extends ItemGunBase implements IHoldableWeapon, IItemHUD, IBatteryItem
{
	public GunConfigurationEnergy mainConfig;
	public GunConfigurationEnergy altConfig;
	public long maxCharge;
	public long chargeRate;

	public ItemGunEnergyBase(GunConfigurationEnergy main)
	{
		super(main);
		if (main.ammoRate > main.ammoCap)
			throw new IllegalArgumentException("Energy consumption rate exceeds energy cap!");
		mainConfig = main;
		maxCharge = mainConfig.ammoCap;
		chargeRate = mainConfig.chargeRate;
	}
	
	public ItemGunEnergyBase(GunConfigurationEnergy main, GunConfigurationEnergy alt)
	{
		super(main, alt);
		if (main.ammoRate > main.ammoCap || alt.ammoRate > main.ammoCap)
			throw new IllegalArgumentException("Energy consumption rate exceeds energy cap!");
		mainConfig = main;
		altConfig = alt;
		maxCharge = mainConfig.ammoCap;
		chargeRate = mainConfig.chargeRate;
		
	}
	
	@Override
	public boolean hasAmmo(ItemStack stack, EntityPlayer player, boolean main)
	{
		if (main)
			return getCharge(stack) >= mainConfig.ammoRate;
		else
			return getCharge(stack) >= altConfig.ammoRate;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		GunConfigurationEnergy mainConfigEnergy = (GunConfigurationEnergy)mainConfig;
		GunConfigurationEnergy altConfigEnergy = (GunConfigurationEnergy)altConfig;
		long gunChargeMax = mainConfigEnergy.ammoCap;
		long gunCurrentCharge = getGunCharge(stack);
		String gunChargeMaxString = Library.getShortNumber(gunChargeMax);
		String gunCurrentChargeString = Library.getShortNumber(gunCurrentCharge);
		Item ammo = ModItems.battery_creative;
		list.add(String.format("Charge: %s / %sHE", gunCurrentChargeString, gunChargeMaxString));
		list.add(String.format("Charge rate: %sHE/tick", Library.getShortNumber(chargeRate)));
		list.add(String.format("Ammo: %s / %s", Math.floorDiv(gunCurrentCharge, mainConfigEnergy.ammoRate), Math.floorDiv(gunChargeMax, mainConfigEnergy.ammoRate)));
		list.add(String.format("Ammo Type: Energy; %sHE per shot%s", Library.getShortNumber(mainConfigEnergy.ammoRate), altConfig != null ? "; " + Library.getShortNumber(altConfigEnergy.ammoRate) + "HE per alt shot" : ""));
		if (mainConfig.damage != "" || !mainConfig.damage.isEmpty())
			list.add("Damage: " + mainConfig.damage);
		
		int dura = mainConfigEnergy.durability - getItemWear(stack);
		if (dura < 0)
			dura = 0;
		
		list.add("Durability: " + dura + " / " + mainConfig.durability);
		list.add("");
		list.add("Name: " + mainConfig.name);
		list.add("Manufacturer: " + mainConfig.manufacturer);
		if (!mainConfig.comment.isEmpty())
			list.add("");
			for (String s : mainConfig.comment)
				list.add(EnumChatFormatting.ITALIC + s);
		
		if(GeneralConfig.enableExtendedLogging)
		{
			list.add("");
			list.add("Type: " + getMagType(stack));
			list.add("Is Reloading: " + getIsReloading(stack));
			list.add("Reload Cycle: " + getReloadCycle(stack));
			list.add("RoF Cooldown: " + getDelay(stack));
		}
		if (!mainConfig.advLore.isEmpty() || !mainConfig.advFuncLore.isEmpty())
			list.add("");
		
		if (!mainConfig.advLore.isEmpty())
			list.add(String.format("%s%sHold <%sLSHIFT%s%s%s> to view in-depth lore", EnumChatFormatting.DARK_GRAY, EnumChatFormatting.ITALIC, EnumChatFormatting.YELLOW, EnumChatFormatting.RESET, EnumChatFormatting.DARK_GRAY, EnumChatFormatting.ITALIC));
		
		if (!mainConfig.advFuncLore.isEmpty())
			list.add(String.format("%s%sHold <%sLCTRL%s%s%s> to view in-depth functionality", EnumChatFormatting.DARK_GRAY, EnumChatFormatting.ITALIC, EnumChatFormatting.YELLOW, EnumChatFormatting.RESET, EnumChatFormatting.DARK_GRAY, EnumChatFormatting.ITALIC));
	
		if (!mainConfig.advLore.isEmpty() && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			list.clear();
			list.add(EnumChatFormatting.YELLOW + "-- Lore --");
			for (String s : mainConfig.advLore)
				list.add(s);
		}
		if (!mainConfig.advLore.isEmpty() && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
		{
			list.clear();
			list.add(EnumChatFormatting.YELLOW + "-- Function --");
			for (String s : mainConfig.advFuncLore)
				list.add(s);
		}

	}

	@Override
	public void useUpAmmo(EntityPlayer player, ItemStack stack, boolean main)
	{
		GunConfigurationEnergy config = mainConfig;
		if (!main && altConfig == null)
			return;
		
		if (!main)
			config = altConfig;
		
		dischargeBattery(stack, config.ammoRate);
	}
	
	@Override
	protected void altFire(ItemStack stack, World world, EntityPlayer player)
	{
		super.altFire(stack, world, player);
		useUpAmmo(player, stack, false);
	}
	
	// TODO finish, probably should just import the code from gun batteries
	@Override
	protected void reload2(ItemStack stack, World world, EntityPlayer player)
	{
		if (getReloadCycle(stack) < 0 && stack == player.getHeldItem())
		{
			for (ItemStack playerSlot : player.inventory.mainInventory)
			{
				ItemBatteryGun battery;
				if (playerSlot == null)
					continue;
				
				if (playerSlot.getItem() instanceof ItemBatteryGun)
				{
					battery = (ItemBatteryGun)playerSlot.getItem();
					if (battery.getCharge(playerSlot) == 0)
						continue;
					else
					{
						long chargeDiff = maxCharge - getCharge(stack);
						if (chargeDiff >= battery.getCharge(stack))
						{
							chargeBattery(stack, battery.getCharge(playerSlot));
							battery.setCharge(playerSlot, 0);
						}
						else
						{
							setCharge(stack, maxCharge);
							battery.dischargeBattery(playerSlot, chargeDiff);
						}
			            world.playSoundAtEntity(player, "hbm:item.battery", 1.0F, 1.0F);
					}
				}
			}
		}
	}
	
	@Override
	public boolean canReload(ItemStack stack, World world, EntityPlayer player)
	{
		Item[] batteryItems = new Item[] {ModItems.battery_gun_basic, ModItems.battery_gun_enhanced, ModItems.battery_gun_advanced, ModItems.battery_gun_elite};
		for (Item batt : batteryItems)
			if (player.inventory.hasItem(batt))
				return true;
		return false;
	}
	
	@Override
	public void chargeBattery(ItemStack stack, long i)
	{
		if(stack.getItem() instanceof ItemGunEnergyBase)
		{
			if(stack.hasTagCompound())
			{
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") + i);
			}
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}		
	}

	@Override
	public void setCharge(ItemStack stack, long i)
	{
		if(stack.getItem() instanceof ItemGunEnergyBase)
		{
			if(stack.hasTagCompound())
			{
				stack.stackTagCompound.setLong("charge", i);
			}
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}
	}

	@Override
	public void dischargeBattery(ItemStack stack, long i)
	{
		if(stack.getItem() instanceof ItemGunEnergyBase)
		{
			if(stack.hasTagCompound())
			{
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") - i);
			}
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", this.maxCharge - i);
			}
		}
	}

	@Override
	public long getCharge(ItemStack stack)
	{
		if(stack.getItem() instanceof ItemGunEnergyBase)
		{
			if(stack.hasTagCompound())
			{
				return stack.stackTagCompound.getLong("charge");
			}
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", ((ItemGunEnergyBase) stack.getItem()).maxCharge);
				return stack.stackTagCompound.getLong("charge");
			}
		}
		return 0;
	}
	
	public static ItemStack getEmptyGun(Item itemIn)
	{
		if (itemIn instanceof ItemGunEnergyBase)
		{
			ItemStack stack = new ItemStack(itemIn);
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("charge", 0);
			return stack.copy();
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(Pre event, ElementType type, EntityPlayer player, ItemStack stack)
	{
		ItemGunEnergyBase gun = (ItemGunEnergyBase)stack.getItem();
		if (type == ElementType.HOTBAR)
		{
			int ammoRemaining = (int)Math.floorDiv(getCharge(stack), mainConfig.ammoRate);
			int ammoMax = (int)Math.floorDiv(getMaxCharge(), mainConfig.ammoRate);
			int dura = ItemGunEnergyBase.getItemWear(stack) * 50 / mainConfig.durability;
			
			RenderScreenOverlay.renderAmmo(event.resolution, Minecraft.getMinecraft().ingameGUI, ModItems.battery_creative, ammoRemaining, ammoMax, dura, mainConfig.showAmmo);
		}
		if (type == ElementType.CROSSHAIRS)
		{
			event.setCanceled(true);
			
			if(!(mainConfig.hasSights && player.isSneaking()))
				RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, ((IHoldableWeapon)player.getHeldItem().getItem()).getCrosshair());
			else
				RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, Crosshair.NONE);
		}
	}
	
	public static void setGunCharge(ItemStack stack, long i)
	{
		writeNBT(stack, "charge", i);
	}
	
	public static long getGunCharge(ItemStack stack)
	{
		return readNBTLong(stack, "charge");
	}

	
	@Override
	public long getMaxCharge()
	{
		return maxCharge;
	}

	@Override
	public long getChargeRate()
	{
		return chargeRate;
	}

	@Override
	public long getDischargeRate()
	{
		return 0;
	}
}
