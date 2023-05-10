package com.hbm.items.weapon;

import java.util.List;

import org.lwjgl.input.Mouse;

import com.hbm.config.GeneralConfig;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.handler.HbmKeybinds;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.interfaces.IItemHUD;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.IEquipReceiver;
import com.hbm.lib.HbmCollection;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;
import com.hbm.util.I18nUtil;
import com.hbm.util.InventoryUtil;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
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

public class ItemGunBase extends Item implements IHoldableWeapon, IItemHUD, IEquipReceiver {

	public GunConfiguration mainConfig;
	public GunConfiguration altConfig;
	
	@SideOnly(Side.CLIENT)
	public boolean m1;// = false;
	@SideOnly(Side.CLIENT)
	public boolean m2;// = false;
	
	public ItemGunBase(GunConfiguration config) {
		mainConfig = config;
		this.setMaxStackSize(1);
	}
	
	public ItemGunBase(GunConfiguration config, GunConfiguration alt) {
		this(config);
		altConfig = alt;
	}

	@Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {
		
		if(entity instanceof EntityPlayer) {
			
			isCurrentItem = ((EntityPlayer)entity).getHeldItem() == stack;
			
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && world.isRemote) {
				updateClient(stack, world, (EntityPlayer)entity, slot, isCurrentItem);
			} else {
				updateServer(stack, world, (EntityPlayer)entity, slot, isCurrentItem);
			}
		}
	}

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
			
			if(mainConfig.reloadType != mainConfig.RELOAD_NONE || (altConfig != null && altConfig.reloadType != 0)) {
				
				if(GameSettings.isKeyDown(HbmKeybinds.reloadKey) && Minecraft.getMinecraft().currentScreen == null && (getMag(stack) < mainConfig.ammoCap || hasInfinity(stack, mainConfig))) {
					PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 2));
					setIsReloading(stack, true);
					resetReloadCycle(stack);
				}
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
			setDelay(stack, mainConfig.rateOfFire);
		}
		
		if(getIsReloading(stack) && isCurrentItem) {
			reload2(stack, world, player);
		}
		
		BulletConfiguration queued = getCasing(stack);
		int timer = getCasingTimer(stack);
		
		if(queued != null && timer > 0) {
			
			timer--;
			
			if(timer <= 0) {
				trySpawnCasing(player, mainConfig.ejector, queued, stack);
			}
			
			setCasingTimer(stack, timer);
		}
	}
	
	//whether or not the gun can shoot in its current state
	protected boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		if(getIsReloading(stack) && mainConfig.reloadType == mainConfig.RELOAD_SINGLE) {
			setReloadCycle(stack, 0);
			setIsReloading(stack, false);
		}
		
		if(main && getDelay(stack) == 0 && !getIsReloading(stack) && getItemWear(stack) < mainConfig.durability) {
			return hasAmmo(stack, player, main);
		}
		
		if(!main && altConfig != null && getDelay(stack) == 0 && !getIsReloading(stack) && getItemWear(stack) < mainConfig.durability) {
			
			return hasAmmo(stack, player, false);
		}
		
		return false;
	}
	
	public boolean hasAmmo(ItemStack stack, EntityPlayer player, boolean main) {
		
		GunConfiguration config = mainConfig;
		
		if(!main)
			config = altConfig;
		
		if(config.reloadType == mainConfig.RELOAD_NONE) {
			return getBeltSize(player, getBeltType(player, stack, main)) > 0;
			
		} else {
			//return getMag(stack) >= 0 + config.roundsPerCycle;
			return getMag(stack) > 0;
		}
	}
	
	//called every time the gun shoots successfully, calls spawnProjectile(), sets item wear
	protected void fire(ItemStack stack, World world, EntityPlayer player) {

		BulletConfiguration config = null;
		
		if(mainConfig.reloadType == mainConfig.RELOAD_NONE) {
			config = getBeltCfg(player, stack, true);
		} else {
			config = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack)));
		}
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < mainConfig.roundsPerCycle; k++) {
			
			if(!hasAmmo(stack, player, true))
				break;
			
			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config));
			}
			
			useUpAmmo(player, stack, true);
			player.inventoryContainer.detectAndSendChanges();
			
			int wear = (int) Math.ceil(config.wear / (1F + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack)));
			setItemWear(stack, getItemWear(stack) + wear);
		}
		
		world.playSoundAtEntity(player, mainConfig.firingSound, mainConfig.firingVolume, mainConfig.firingPitch);
		
		if(mainConfig.ejector != null && !mainConfig.ejector.getAfterReload())
			queueCasing(player, mainConfig.ejector, config, stack);
	}
	
	//unlike fire(), being called does not automatically imply success, some things may still have to be handled before spawning the projectile
	protected void altFire(ItemStack stack, World world, EntityPlayer player) {
		
		if(altConfig == null)
			return;

		BulletConfiguration config = altConfig.reloadType == altConfig.RELOAD_NONE ? getBeltCfg(player, stack, false) : BulletConfigSyncingUtil.pullConfig(altConfig.config.get(getMagType(stack)));
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < altConfig.roundsPerCycle; k++) {
			
			if(altConfig.reloadType != altConfig.RELOAD_NONE && !hasAmmo(stack, player, true))
				break;
			
			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config));
			}
			
			useUpAmmo(player, stack, false);
			player.inventoryContainer.detectAndSendChanges();
			
			setItemWear(stack, getItemWear(stack) + config.wear);
		}
		
		world.playSoundAtEntity(player, altConfig.firingSound, mainConfig.firingVolume, altConfig.firingPitch);
		
		if(altConfig.ejector != null)
			queueCasing(player, altConfig.ejector, config, stack);
	}
	
	//spawns the actual projectile, can be overridden to change projectile entity
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		
		EntityBulletBase bullet = new EntityBulletBase(world, config, player);
		world.spawnEntityInWorld(bullet);
		
		if(player instanceof EntityPlayerMP)
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal()), (EntityPlayerMP) player);
			
	}
	
	//called on click (server side, called by mouse packet) for semi-automatics and specific events
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {

		if(mainConfig.firingMode == mainConfig.FIRE_MANUAL && main && tryShoot(stack, world, player, main)) {
			fire(stack, world, player);
			setDelay(stack, mainConfig.rateOfFire);
			//setMag(stack, getMag(stack) - 1);
			//useUpAmmo(player, stack, main);
			//player.inventoryContainer.detectAndSendChanges();
		}
		
		if(!main && altConfig != null && tryShoot(stack, world, player, main)) {
			altFire(stack, world, player);
			setDelay(stack, altConfig.rateOfFire);
			//useUpAmmo(player, stack, main);
			//player.inventoryContainer.detectAndSendChanges();
		}
	}
	
	//called on click (client side, called by mouse click event)
	public void startActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) { }
	
	//called on click release (server side, called by mouse packet) for release actions like charged shots
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main) { }
	
	//called on click release (client side, called by update cycle)
	public void endActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) { }
	
	//current reload
	protected void reload2(ItemStack stack, World world, EntityPlayer player) {
		
		if(getMag(stack) >= mainConfig.ammoCap) {
			setIsReloading(stack, false);
			return;
		}
		
		if(getReloadCycle(stack) <= 0) {
			
			BulletConfiguration prevCfg = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack)));
			
			if(getMag(stack) == 0)
				resetAmmoType(stack, world, player);
			
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack)));
			ComparableStack ammo = (ComparableStack) cfg.ammo.copy();
			
			final int countNeeded = (mainConfig.reloadType == GunConfiguration.RELOAD_FULL) ? mainConfig.ammoCap - getMag(stack) : 1;
			final int availableStacks = InventoryUtil.countAStackMatches(player, ammo, true);
			final int availableFills = availableStacks * cfg.ammoCount;
			final boolean hasLoaded = availableFills > 0;
			final int toAdd = Math.min(availableFills * cfg.ammoCount, countNeeded);
			final int toConsume = (int) Math.ceil((double) toAdd / cfg.ammoCount);
			
			// Skip logic if cannot reload
			if(availableFills == 0) {
				setIsReloading(stack, false);
				return;
			}
			
			ammo.stacksize = toConsume;
			setMag(stack, getMag(stack) + toAdd);
			if (getMag(stack) >= mainConfig.ammoCap)
				setIsReloading(stack, false);
			else
				resetReloadCycle(stack);
			
			if(hasLoaded && mainConfig.reloadSoundEnd)
				world.playSoundAtEntity(player, mainConfig.reloadSound, 1.0F, 1.0F);
			
			if(mainConfig.ejector != null && mainConfig.ejector.getAfterReload())
				queueCasing(player, mainConfig.ejector, prevCfg, stack);
			
			InventoryUtil.tryConsumeAStack(player.inventory.mainInventory, 0, player.inventory.mainInventory.length, ammo);
		} else {
			setReloadCycle(stack, getReloadCycle(stack) - 1);
		}
		
		if(stack != player.getHeldItem()) {
			setReloadCycle(stack, 0);
			setIsReloading(stack, false);
		}
	}
	
	//initiates a reload
	public void startReloadAction(ItemStack stack, World world, EntityPlayer player) {
		
		if(player.isSneaking() && hasInfinity(stack, mainConfig)) {
			
			if(this.getMag(stack) == mainConfig.ammoCap) {
				this.setMag(stack, 0);
				this.resetAmmoType(stack, world, player);
				world.playSoundAtEntity(player, "tile.piston.out", 1.0F, 1.0F);
			}
			
			return;
		}
		
		if(this.getMag(stack) == mainConfig.ammoCap)
			return;

		if(getIsReloading(stack))
			return;
		
		if(!mainConfig.reloadSoundEnd)
			world.playSoundAtEntity(player, mainConfig.reloadSound, 1.0F, 1.0F);
		
		if(!world.isRemote)
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.RELOAD.ordinal()), (EntityPlayerMP) player);
		
		setIsReloading(stack, true);
		resetReloadCycle(stack);
	}
	
	public boolean canReload(ItemStack stack, World world, EntityPlayer player) {
		
		if(getMag(stack) == mainConfig.ammoCap && hasInfinity(stack, mainConfig))
			return true;

		if(getMag(stack) == 0) {
			
			for(int config : mainConfig.config) {
				if(InventoryUtil.doesPlayerHaveAStack(player, BulletConfigSyncingUtil.pullConfig(config).ammo, false, false)) {
					return true;
				}
			}
			
		} else {
			ComparableStack ammo = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack))).ammo;
			return InventoryUtil.doesPlayerHaveAStack(player, ammo, false, false);
		}
		
		return false;
	}
	
	//searches the player's inv for next fitting ammo type and changes the gun's mag
	protected void resetAmmoType(ItemStack stack, World world, EntityPlayer player) {

		for(int config : mainConfig.config) {
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
			
			if(InventoryUtil.doesPlayerHaveAStack(player, cfg.ammo, false, false)) {
				setMagType(stack, mainConfig.config.indexOf(config));
				break;
			}
		}
	}
	
	//item mouseover text
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		ComparableStack ammo = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack))).ammo;
		
		list.add(I18nUtil.resolveKey(HbmCollection.ammo, mainConfig.ammoCap > 0 ? I18nUtil.resolveKey(HbmCollection.ammoMag, getMag(stack), mainConfig.ammoCap) : I18nUtil.resolveKey(HbmCollection.ammoBelt)));
		
		try {
			list.add(I18nUtil.resolveKey(HbmCollection.ammoType, ammo.toStack().getDisplayName()));

			if(altConfig != null && altConfig.ammoCap == 0) {
				ComparableStack ammo2 = BulletConfigSyncingUtil.pullConfig(altConfig.config.get(0)).ammo;
				if(!ammo.isApplicable(ammo2)) {
					list.add(I18nUtil.resolveKey(HbmCollection.altAmmoType, ammo2.toStack().getDisplayName()));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			list.add("Error: " + e + " has occurred!");
		}

		addAdditionalInformation(stack, list);
	}
	
	protected void addAdditionalInformation(ItemStack stack, List<String> list)
	{
		final BulletConfiguration bulletConfig = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack)));
		list.add(I18nUtil.resolveKey(HbmCollection.gunDamage, bulletConfig.dmgMin, bulletConfig.dmgMax));
		if(bulletConfig.bulletsMax != 1)
			list.add(I18nUtil.resolveKey(HbmCollection.gunPellets, bulletConfig.bulletsMin, bulletConfig.bulletsMax));
		int dura = Math.max(mainConfig.durability - getItemWear(stack), 0);
		
		list.add(I18nUtil.resolveKey(HbmCollection.durability, dura + " / " + mainConfig.durability));
		
		list.add("");
		String unloc = "gun.name." + mainConfig.name;
		String loc = I18nUtil.resolveKey(unloc);
		list.add(I18nUtil.resolveKey(HbmCollection.gunName, unloc.equals(loc) ? mainConfig.name : loc));
		list.add(I18nUtil.resolveKey(HbmCollection.gunMaker, I18nUtil.resolveKey(mainConfig.manufacturer.getKey())));
		
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
	
	//returns ammo item of belt-weapons
	public static ComparableStack getBeltType(EntityPlayer player, ItemStack stack, boolean main) {
		ItemGunBase gun = (ItemGunBase)stack.getItem();
		GunConfiguration guncfg = main ? gun.mainConfig : (gun.altConfig != null ? gun.altConfig : gun.mainConfig);

		for(Integer config : guncfg.config) {
			
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
			
			if(InventoryUtil.doesPlayerHaveAStack(player, cfg.ammo, false, true)) {
				return cfg.ammo;
			}
		}
		
		return BulletConfigSyncingUtil.pullConfig(guncfg.config.get(0)).ammo;
	}
	
	//returns BCFG of belt-weapons
	public static BulletConfiguration getBeltCfg(EntityPlayer player, ItemStack stack, boolean main) {
		ItemGunBase gun = (ItemGunBase)stack.getItem();
		GunConfiguration guncfg = main ? gun.mainConfig : (gun.altConfig != null ? gun.altConfig : gun.mainConfig);
		getBeltType(player, stack, main);
	
		for(int config : guncfg.config) {
			
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
			
			if(InventoryUtil.doesPlayerHaveAStack(player, cfg.ammo, false, false)) {
				return cfg;
			}
		}
	
		return BulletConfigSyncingUtil.pullConfig(guncfg.config.get(0));
	}

	//returns ammo capacity of belt-weapons for current ammo
	public static int getBeltSize(EntityPlayer player, ComparableStack ammo) {
		
		int amount = 0;
		
		for(ItemStack stack : player.inventory.mainInventory) {
			if(stack != null && ammo.matchesRecipe(stack, true)) {
				amount += stack.stackSize;
			}
		}
		
		return amount;
	}
	
	//reduces ammo count for mag and belt-based weapons, should be called AFTER firing
	public void useUpAmmo(EntityPlayer player, ItemStack stack, boolean main) {
		
		if(!main && altConfig == null)
			return;
		
		GunConfiguration config = mainConfig;
		
		if(!main)
			config = altConfig;
		
		if(hasInfinity(stack, config))
			return;

		if(config.reloadType != GunConfiguration.RELOAD_NONE) {
			setMag(stack, getMag(stack) - 1);
		} else {
			InventoryUtil.doesPlayerHaveAStack(player, getBeltType(player, stack, main), true, false);
		}
	}
	
	public boolean hasInfinity(ItemStack stack, GunConfiguration config) {
		return config.allowsInfinity && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
	}
	
	/// sets reload cycle to config defult ///
	public static void resetReloadCycle(ItemStack stack) {
		writeNBT(stack, "reload", ((ItemGunBase)stack.getItem()).mainConfig.reloadDuration);
	}
	
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
		writeNBT(stack, "isAltDown", b ? 1 : 0);
	}
	
	public static boolean getIsAltDown(ItemStack stack) {
		return readNBT(stack, "isAltDown") == 1;
	}
	
	/// RoF cooldown ///
	public static void setDelay(ItemStack stack, int i) {
		writeNBT(stack, "dlay", i);
	}
	
	public static int getDelay(ItemStack stack) {
		return readNBT(stack, "dlay");
	}
	
	/// Gun wear ///
	public static void setItemWear(ItemStack stack, int i) {
		writeNBT(stack, "wear", i);
	}
	
	public static int getItemWear(ItemStack stack) {
		return readNBT(stack, "wear");
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
	
	/// queued casing for ejection ///
	public static void setCasing(ItemStack stack, BulletConfiguration bullet) {
		writeNBT(stack, "casing", BulletConfigSyncingUtil.getKey(bullet));
	}
	
	public static BulletConfiguration getCasing(ItemStack stack) {
		return BulletConfigSyncingUtil.pullConfig(readNBT(stack, "casing"));
	}
	
	/// timer for ejecting casing ///
	public static void setCasingTimer(ItemStack stack, int i) {
		writeNBT(stack, "casingTimer", i);
	}
	
	public static int getCasingTimer(ItemStack stack) {
		return readNBT(stack, "casingTimer");
	}
	
	/// NBT utility ///
	public static void writeNBT(ItemStack stack, String key, int value) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger(key, value);
	}
	
	public static int readNBT(ItemStack stack, String key) {
		
		if(!stack.hasTagCompound())
			return 0;
		
		return stack.stackTagCompound.getInteger(key);
	}

	@Override
	public Crosshair getCrosshair() {
		return mainConfig.crosshair;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(Pre event, ElementType type, EntityPlayer player, ItemStack stack) {
		
		ItemGunBase gun = ((ItemGunBase)stack.getItem());
		GunConfiguration gcfg = gun.mainConfig;
		
		if(type == ElementType.HOTBAR) {
			int mag = ItemGunBase.getMagType(stack);
			if(gun.mainConfig.config.size() == 0) return;
			BulletConfiguration bcfg = BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(mag < gun.mainConfig.config.size() ? mag : 0));
			
			if(bcfg == null) {
				return;
			}
			
			ComparableStack ammo = bcfg.ammo;
			int count = ItemGunBase.getMag(stack);
			int max = gcfg.ammoCap;
			boolean showammo = gcfg.showAmmo;
			
			if(gcfg.reloadType == GunConfiguration.RELOAD_NONE) {
				ammo = ItemGunBase.getBeltType(player, stack, true);
				count = ItemGunBase.getBeltSize(player, ammo);
				max = -1;
			}
			
			int dura = ItemGunBase.getItemWear(stack) * 50 / gcfg.durability;
			
			RenderScreenOverlay.renderAmmo(event.resolution, Minecraft.getMinecraft().ingameGUI, ammo.toStack(), count, max, dura, showammo);
			
			if(gun.altConfig != null && gun.altConfig.reloadType == GunConfiguration.RELOAD_NONE) {
				ComparableStack oldAmmo = ammo;
				ammo = ItemGunBase.getBeltType(player, stack, false);
				
				if(!ammo.isApplicable(oldAmmo)) {
					count = ItemGunBase.getBeltSize(player, ammo);
					RenderScreenOverlay.renderAmmoAlt(event.resolution, Minecraft.getMinecraft().ingameGUI, ammo.toStack(), count);
				}
			}
		}
		
		if(type == ElementType.CROSSHAIRS && GeneralConfig.enableCrosshairs) {

			event.setCanceled(true);
			
			if(!(gcfg.hasSights && player.isSneaking()))
				RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, ((IHoldableWeapon)player.getHeldItem().getItem()).getCrosshair());
			else
				RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, Crosshair.NONE);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public BusAnimation getAnimation(ItemStack stack, AnimType type) {
		GunConfiguration config = ((ItemGunBase) stack.getItem()).mainConfig;
		return config.animations.get(type);
	}
	
	@Override
	public void onEquip(EntityPlayer player) {
		if(!mainConfig.equipSound.isEmpty() && !player.worldObj.isRemote) {
			player.worldObj.playSoundAtEntity(player, mainConfig.equipSound, 1, 1);
		}
	}
	
	protected static void queueCasing(Entity entity, CasingEjector ejector, BulletConfiguration bullet, ItemStack stack) {
		
		if(ejector == null || bullet == null || bullet.spentCasing == null) return;
		
		if(ejector.getDelay() <= 0) {
			trySpawnCasing(entity, ejector, bullet, stack);
		} else {
			setCasing(stack, bullet);
			setCasingTimer(stack, ejector.getDelay());
		}
	}
	
	protected static void trySpawnCasing(Entity entity, CasingEjector ejector, BulletConfiguration bullet, ItemStack stack) {
		
		if(ejector == null) return; //abort if the gun can't eject bullets at all
		if(bullet == null) return; //abort if there's no valid bullet cfg
		if(bullet.spentCasing == null) return; //abort if the bullet is caseless
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "casing");
		data.setFloat("pitch", (float) Math.toRadians(entity.rotationPitch));
		data.setFloat("yaw", (float) Math.toRadians(entity.rotationYaw));
		data.setBoolean("crouched", entity.isSneaking());
		data.setString("name", bullet.spentCasing.getName());
		data.setInteger("ej", ejector.getId());
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 50));
	}
}
