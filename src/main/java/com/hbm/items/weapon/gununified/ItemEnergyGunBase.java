package com.hbm.items.weapon.gununified;

import java.util.List;

import org.lwjgl.input.Mouse;

import com.hbm.config.GeneralConfig;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.handler.HbmKeybinds;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;
import com.hbm.util.BobMathUtil;
import com.hbm.util.ChatBuilder;
import com.hbm.util.I18nUtil;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class ItemEnergyGunBase extends ItemGunBase implements IBatteryItem {
	
	public ItemEnergyGunBase(GunConfiguration config) {
		super(config);
	}
	
	public ItemEnergyGunBase(GunConfiguration config, GunConfiguration alt) {
		super(config, alt);
	}
	
	@Override
public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add("Energy Stored: " + BobMathUtil.getShortNumber(getCharge(stack)) + "/" + BobMathUtil.getShortNumber(mainConfig.maxCharge) + "HE");
		list.add("Charge rate: " + BobMathUtil.getShortNumber(mainConfig.chargeRate) + "HE/t");
		
		BulletConfiguration config = getConfig(stack);
		
		list.add("");
		list.add("Mode: " + I18nUtil.resolveKey(config.modeName));
		list.add("Mode info:");
		list.add("Average damage: " + ((float)(config.dmgMax + config.dmgMin) / 2F));
		list.add("Firing Rate: " + BobMathUtil.roundDecimal((1F / (((float)config.firingRate) / 20F)), 2) + " rounds per second");
		list.add("Power Consumption per Shot: " +  BobMathUtil.getShortNumber(config.dischargePerShot) + "HE");
		
		list.add("");
		list.add("Name: " + mainConfig.name);
		list.add("Manufacturer: " + mainConfig.manufacturer);
		
		if(!mainConfig.comment.isEmpty()) {
			list.add("");
			for(String s : mainConfig.comment)
				list.add(EnumChatFormatting.ITALIC + s);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected void updateClient(ItemStack stack, World world, EntityPlayer entity, int slot, boolean isCurrentItem) {
		
		if(!world.isRemote)
			return;
		
		boolean clickLeft = Mouse.isButtonDown(0);
		boolean clickRight = Mouse.isButtonDown(1);
		boolean left = m1;
		boolean right = m2;
		
		if(isCurrentItem) {
			if(left && right) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0));
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1));
				m1 = false;
				m2 = false;
			}
			
			if(left && !clickLeft) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0));
				m1 = false;
				endActionClient(stack, world, entity, true);
			}
			
			if(right && !clickRight) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1));
				m2 = false;
				endActionClient(stack, world, entity, false);
			}
		}
	}
	
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		
		if(getDelay(stack) > 0 && isCurrentItem)
			setDelay(stack, getDelay(stack) - 1);
		
		if(getIsMouseDown(stack) && !(player.getHeldItem() == stack)) {
			setIsMouseDown(stack, false);
		}
		
		if(getIsAltDown(stack) && !isCurrentItem) {
			setIsAltDown(stack, false);
		}
			
		if(GeneralConfig.enableGuns && mainConfig.firingMode == 1 && getIsMouseDown(stack) && tryShoot(stack, world, player, isCurrentItem)) {
			
			fire(stack, world, player);
			setDelay(stack, getConfig(stack).firingRate);
		}
	}
	
	protected boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
	
		
		if(main && getDelay(stack) == 0) {
			return getConfig(stack).dischargePerShot <= getCharge(stack);
		}
	
		return false;
	}
	
	protected void fire(ItemStack stack, World world, EntityPlayer player) {
		
		BulletConfiguration config = getConfig(stack);
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < mainConfig.roundsPerCycle; k++) {
			
			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config));
			}
			
			setCharge(stack, getCharge(stack) - config.dischargePerShot);;
		}
		
		world.playSoundAtEntity(player, mainConfig.firingSound, 1.0F, mainConfig.firingPitch);

		if(player.getDisplayName().equals("Vic4Games")) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("type", "justTilt");
			nbt.setInteger("time", mainConfig.rateOfFire + 1);
			PacketDispatcher.wrapper.sendTo(new AuxParticlePacketNT(nbt, player.posX, player.posY, player.posZ), (EntityPlayerMP) player);
		}
	}
	
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		
		EntityBulletBase bullet = new EntityBulletBase(world, config, player);
		world.spawnEntityInWorld(bullet);
		
		if(this.mainConfig.animations.containsKey(AnimType.CYCLE) && player instanceof EntityPlayerMP)
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal()), (EntityPlayerMP) player);
			
	}
	
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		if(mainConfig.firingMode == mainConfig.FIRE_MANUAL && main && tryShoot(stack, world, player, main)) {
			fire(stack, world, player);
			setDelay(stack, mainConfig.rateOfFire);
			
		}
		
		if(!main && stack.getItem() instanceof ItemEnergyGunBase) {
			
			byte mode = stack.hasTagCompound() ? stack.getTagCompound().getByte("mode") : 0;
			
			if(!stack.hasTagCompound())
				stack.stackTagCompound = new NBTTagCompound();
			
			mode++;
			if(mode >= mainConfig.config.size()) {
				mode = 0;
			}
			
			stack.getTagCompound().setByte("mode", mode);
			
			if(!world.isRemote) {
				BulletConfiguration config = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(mode));
				//PacketDispatcher.wrapper.sendTo(new PlayerInformPacket("" + config.chatColour + config.modeName, MainRegistry.proxy.ID_GUN_MODE), (EntityPlayerMP)player);
				
				player.addChatMessage(ChatBuilder.start("")
						.nextTranslation("weapon.elecGun.modeChange").color(EnumChatFormatting.WHITE)
						.nextTranslation(" ")
						.nextTranslation(config.modeName).color(config.chatColour).flush());
			}
		}
	}
	
	// yummy boilerplate
	
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - (double) getCharge(stack) / (double) getMaxCharge();
	}

	@Override
	public void chargeBattery(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemEnergyGunBase) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") + i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}
	}

	@Override
	public void setCharge(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemEnergyGunBase) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}
	}

	@Override
	public void dischargeBattery(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemEnergyGunBase) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") - i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", ((ItemEnergyGunBase)stack.getItem()).mainConfig.maxCharge - i);
			}
		}
	}

	@Override
	public long getCharge(ItemStack stack) {
		if(stack.getItem() instanceof ItemEnergyGunBase) {
			if(stack.hasTagCompound()) {
				return stack.stackTagCompound.getLong("charge");
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", ((ItemEnergyGunBase) stack.getItem()).mainConfig.maxCharge);
				return stack.stackTagCompound.getLong("charge");
			}
		}

		return 0;
	}

	@Override
	public long getMaxCharge() {
		return mainConfig.maxCharge;
	}

	@Override
	public long getChargeRate() {
		return mainConfig.chargeRate;
	}

	@Override
	public long getDischargeRate() {
		return 0;
	}
	
	public BulletConfiguration getConfig(ItemStack stack) {
		
		byte mode = 0;
		
		if(stack.hasTagCompound())
			mode = stack.getTagCompound().getByte("mode");
		
		return BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(mode));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {

		ItemStack stack = new ItemStack(item);
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setLong("charge", ((ItemEnergyGunBase) item).getMaxCharge());

		list.add(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(Pre event, ElementType type, EntityPlayer player, ItemStack stack) {
		
		if(type == ElementType.CROSSHAIRS && GeneralConfig.enableCrosshairs) {

			event.setCanceled(true);
			
			if(!(mainConfig.hasSights && player.isSneaking()))
				RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, ((IHoldableWeapon)player.getHeldItem().getItem()).getCrosshair());
			else
				RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, Crosshair.NONE);
		}
	}

}
