package com.hbm.items.weapon;

import java.util.List;

import com.hbm.handler.GunConfigurationEnergy;
import com.hbm.interfaces.IFastChargeable;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBatteryFast;
import com.hbm.lib.HbmCollection;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class ItemGunEnergyBase extends ItemGunBase implements IFastChargeable
{
	@SuppressWarnings("hiding")
	public GunConfigurationEnergy mainConfig;
	@SuppressWarnings("hiding")
	public GunConfigurationEnergy altConfig;
	public long maxCharge;
	public long chargeRate;

	public ItemGunEnergyBase(GunConfigurationEnergy main)
	{
		super(main);
		if (main.ammoRate > main.ammoChargeCap)
			throw new IllegalArgumentException("Energy consumption rate exceeds energy cap!");
		mainConfig = main;
		maxCharge = mainConfig.ammoChargeCap;
		chargeRate = mainConfig.chargeRate;
	}
	
	public ItemGunEnergyBase(GunConfigurationEnergy main, GunConfigurationEnergy alt)
	{
		super(main, alt);
		if (main.ammoRate > main.ammoChargeCap || alt.ammoRate > main.ammoChargeCap)
			throw new IllegalArgumentException("Energy consumption rate exceeds energy cap!");
		mainConfig = main;
		altConfig = alt;
		maxCharge = mainConfig.ammoChargeCap;
		chargeRate = mainConfig.chargeRate;
		
	}
	// FIXME not working anymore
	@Override
	public boolean hasAmmo(ItemStack stack, EntityPlayer player, boolean main)
	{
		return getCharge(stack) >= (main ? mainConfig.ammoRate : altConfig.ammoRate);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		GunConfigurationEnergy mainConfigEnergy = mainConfig;
		GunConfigurationEnergy altConfigEnergy = altConfig;
		long gunChargeMax = mainConfigEnergy.ammoChargeCap;
		long gunCurrentCharge = getGunCharge(stack);
		String gunChargeMaxString = BobMathUtil.getShortNumber(gunChargeMax);
		String gunCurrentChargeString = BobMathUtil.getShortNumber(gunCurrentCharge);
		list.add(I18nUtil.resolveKey(HbmCollection.charge, gunCurrentChargeString, gunChargeMaxString));
		list.add(I18nUtil.resolveKey(HbmCollection.chargeRate, BobMathUtil.getShortNumber(chargeRate)));
//		list.add(String.format("Ammo: %s / %s", Math.floorDiv(gunCurrentCharge, mainConfigEnergy.ammoRate), Math.floorDiv(gunChargeMax, mainConfigEnergy.ammoRate)));
		
		list.add(I18nUtil.resolveKey(HbmCollection.ammo, I18nUtil.resolveKey(HbmCollection.ammoMag, Math.floorDiv(gunCurrentCharge, mainConfigEnergy.ammoRate), Math.floorDiv(gunChargeMax, mainConfigEnergy.ammoRate))));
		
//		list.add(String.format("Ammo Type: Energy; %sHE per shot%s", Library.getShortNumber(mainConfigEnergy.ammoRate), altConfig != null ? "; " + Library.getShortNumber(altConfigEnergy.ammoRate) + "HE per alt shot" : ""));
		
		list.add(I18nUtil.resolveKey(HbmCollection.ammoEnergy, BobMathUtil.getShortNumber(mainConfigEnergy.ammoRate)));
		if (altConfig != null)
			list.add(I18nUtil.resolveKey(HbmCollection.altAmmoEnergy, BobMathUtil.getShortNumber(altConfigEnergy.ammoRate)));
		
		addAdditionalInformation(stack, list);
	}

	@Override
	public void useUpAmmo(EntityPlayer player, ItemStack stack, boolean main)
	{
		GunConfigurationEnergy config = (main ? mainConfig : (altConfig != null ? altConfig : null));
		
		if (config == null)
			return;
		
		dischargeBattery(stack, config.ammoRate);
	}
	
	@Override
	protected void altFire(ItemStack stack, World world, EntityPlayer player)
	{
		super.altFire(stack, world, player);
		useUpAmmo(player, stack, false);
	}
	// FIXME
	@Override
	protected void reload2(ItemStack stack, World world, EntityPlayer player)
	{
		System.out.println("Started reload action");
		if (getCharge(stack) >= getMaxCharge())
		{
			System.out.println("Reload not needed");
			setIsReloading(stack, false);
			return;
		}
		if (getReloadCycle(stack) < 0 && stack == player.getHeldItem())
		{
			System.out.println("Needs reload!");
			boolean hasReloaded = false;
			for (ItemStack playerSlot : player.inventory.mainInventory)
			{
//				ItemBatteryFast battery;
				System.out.println("Checking slot...");
				if (playerSlot == null || !(playerSlot.getItem() instanceof ItemBatteryFast))
					continue;
				System.out.println("Slot is good!");
				hasReloaded = fastDischarge(player, stack, playerSlot);
			}
			
			if (getCharge(stack) >= getMaxCharge())
				setIsReloading(stack, false);
			else
				resetReloadCycle(stack);
			System.out.println("Reload cycle complete");
			if (hasReloaded && mainConfig.reloadSoundEnd)
				world.playSoundAtEntity(player, mainConfig.reloadSound.isEmpty() ? "hbm.item.battery" : mainConfig.reloadSound, 1.0F, 1.0F);
		}
		else
			setReloadCycle(stack, getReloadCycle(stack) - 1);
		
		if(stack != player.getHeldItem())
		{
			setReloadCycle(stack, 0);
			setIsReloading(stack, false);
		}

	}
	
	@Override
	public boolean canReload(ItemStack stack, World world, EntityPlayer player)
	{
		if (getCharge(stack) == getMaxCharge())
			return false;
		for (ItemStack playerStack : player.inventory.mainInventory)
		{
			if (playerStack == null || !(playerStack.getItem() instanceof ItemBatteryFast))
				continue;
			
			return IBatteryItem.getChargeStatic(playerStack) > 0;
		}
		return false;
	}
	
	@Override
	public void startReloadAction(ItemStack stack, World world, EntityPlayer player)
	{
		System.out.println("Trying to start reload cycle");
		if (getCharge(stack) == getMaxCharge() || getIsReloading(stack))
			return;
		
		if (!mainConfig.reloadSoundEnd)
			world.playSoundAtEntity(player, mainConfig.reloadSound.isEmpty() ? "hbm:item.battery" : mainConfig.reloadSound, 1.0F, 1.0F);
		
		PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.RELOAD.ordinal()), (EntityPlayerMP) player);
		
		setIsReloading(stack, true);
		resetReloadCycle(stack);
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
	@Deprecated
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
//		ItemGunEnergyBase gun = (ItemGunEnergyBase)stack.getItem();
		if (type == ElementType.HOTBAR)
		{
			int ammoRemaining = (int)Math.floorDiv(getCharge(stack), mainConfig.ammoRate);
			int ammoMax = (int)Math.floorDiv(getMaxCharge(), mainConfig.ammoRate);
			int dura = getItemWear(stack) * 50 / mainConfig.durability;
			
			RenderScreenOverlay.renderAmmo(event.resolution, Minecraft.getMinecraft().ingameGUI, new ItemStack(ModItems.battery_creative), ammoRemaining, ammoMax, dura, mainConfig.showAmmo);
		}
		if (type == ElementType.CROSSHAIRS)
		{
			event.setCanceled(true);
			
			RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, !(mainConfig.hasSights && player.isSneaking()) ? ((IHoldableWeapon)player.getHeldItem().getItem()).getCrosshair() : Crosshair.NONE);
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
