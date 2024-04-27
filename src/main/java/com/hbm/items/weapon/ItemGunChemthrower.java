package com.hbm.items.weapon;

import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.entity.projectile.EntityChemical;
import com.hbm.handler.GunConfiguration;
import com.hbm.handler.guncfg.GunEnergyFactory;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import api.hbm.fluid.IFillableItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class ItemGunChemthrower extends ItemGunBase implements IFillableItem {

	public ItemGunChemthrower() {
		super(GunEnergyFactory.getChemConfig());
	}
	
	@Override
	protected void fire(ItemStack stack, World world, EntityPlayer player) {

		if(!hasAmmo(stack, player, true))
			return;
		
		int bullets = 1;

		for(int i = 0; i < bullets; i++) {
			spawnProjectile(world, player, stack, 0);
		}

		if(player instanceof EntityPlayerMP)
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal()), (EntityPlayerMP) player);

		useUpAmmo(player, stack, true);
		player.inventoryContainer.detectAndSendChanges();

		int wear = (int) Math.ceil(10 / (1F + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack)));
		setItemWear(stack, getItemWear(stack) + wear);
	}

	@Override
	public boolean hasAmmo(ItemStack stack, EntityPlayer player, boolean main) {
		return getMag(stack) >= 0 + this.getConsumption(stack);
	}

	@Override
	public void useUpAmmo(EntityPlayer player, ItemStack stack, boolean main) {
		
		if(!main && altConfig == null)
			return;
		
		GunConfiguration config = mainConfig;
		
		if(!main)
			config = altConfig;
		
		if(hasInfinity(stack, config))
			return;
		
		setMag(stack, getMag(stack) - this.getConsumption(stack));
	}

	@Override
	public boolean canReload(ItemStack stack, World world, EntityPlayer player) {
		return false;
	}

	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		//spawn fluid projectile
		EntityChemical chem = new EntityChemical(world, player);
		chem.setFluid(this.getFluidType(stack));
		world.spawnEntityInWorld(chem);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		list.add("Ammo: " + getMag(stack) + " / " + mainConfig.ammoCap + "mB");
		
		list.add("Ammo Type: " + this.getFluidType(stack).getLocalizedName());
		
		int dura = mainConfig.durability - getItemWear(stack);
		
		if(dura < 0)
			dura = 0;
		
		list.add("Durability: " + dura + " / " + mainConfig.durability);
		list.add("");
		list.add("Name: " + mainConfig.name);
		list.add("Manufacturer: " + mainConfig.manufacturer);
		
		if(!mainConfig.comment.isEmpty()) {
			list.add("");
			for(String s : mainConfig.comment)
				list.add(EnumChatFormatting.ITALIC + s);
		}
		
		if(GeneralConfig.enableExtendedLogging) {
			list.add("");
			list.add("Type: " + getMagType(stack));
			list.add("Is Reloading: " + getIsReloading(stack));
			list.add("Reload Cycle: " + getReloadCycle(stack));
			list.add("RoF Cooldown: " + getDelay(stack));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(Pre event, ElementType type, EntityPlayer player, ItemStack stack) {
		
		ItemGunBase gun = ((ItemGunBase)stack.getItem());
		GunConfiguration gcfg = gun.mainConfig;
		
		if(type == ElementType.HOTBAR) {
			
			FluidType fluid = this.getFluidType(stack);
			
			ItemStack ammo = ItemFluidIcon.make(fluid, 1);
			
			int count = ItemGunBase.getMag(stack);
			int max = gcfg.ammoCap;
			boolean showammo = gcfg.showAmmo;
			
			int dura = ItemGunBase.getItemWear(stack) * 50 / gcfg.durability;
			
			RenderScreenOverlay.renderAmmo(event.resolution, Minecraft.getMinecraft().ingameGUI, ammo, count, max, dura, showammo);
		}
		
		if(type == ElementType.CROSSHAIRS && GeneralConfig.enableCrosshairs) {

			event.setCanceled(true);
			
			if(!(gcfg.hasSights && player.isSneaking()))
				RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, ((IHoldableWeapon)player.getHeldItem().getItem()).getCrosshair());
			else
				RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, Crosshair.NONE);
		}
	}
	
	@Override
	protected void reload2(ItemStack stack, World world, EntityPlayer player) {
		this.setIsReloading(stack, false);
	}
	
	public FluidType getFluidType(ItemStack stack) {
		return Fluids.fromID(this.getMagType(stack));
	}
	
	public int getConsumption(ItemStack stack) {
		return 3;
	}

	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		return getFluidType(stack) == type || this.getMag(stack) == 0;
	}
	
	public static final int transferSpeed = 50;

	@Override
	public int tryFill(FluidType type, int amount, ItemStack stack) {
		
		if(!acceptsFluid(type, stack))
			return amount;
		
		if(this.getMag(stack) == 0)
			this.setMagType(stack, type.getID());
		
		int fill = this.getMag(stack);
		int req = this.mainConfig.ammoCap - fill;
		
		int toFill = Math.min(amount, req);
		toFill = Math.min(toFill, transferSpeed);
		
		this.setMag(stack, fill + toFill);
		
		return amount - toFill;
	}

	@Override
	public boolean providesFluid(FluidType type, ItemStack stack) {
		return getFluidType(stack) == type;
	}

	@Override
	public int tryEmpty(FluidType type, int amount, ItemStack stack) {
		
		int fill = this.getMag(stack);
		int toUnload = Math.min(fill, amount);
		toUnload = Math.min(toUnload, transferSpeed);
		
		this.setMag(stack, fill - toUnload);
		
		return toUnload;
	}

	@Override
	public FluidType getFirstFluidType(ItemStack stack) {
		return this.getFluidType(stack);
	}

	@Override
	public int getFill(ItemStack stack) {
		return this.getMag(stack);
	}
}
