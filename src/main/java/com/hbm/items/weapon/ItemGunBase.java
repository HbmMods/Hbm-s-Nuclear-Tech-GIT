package com.hbm.items.weapon;

import static com.hbm.config.GeneralConfig.magazineMode;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.eclipse.collections.api.factory.primitive.IntSets;
import org.eclipse.collections.api.set.primitive.IntSet;
import org.lwjgl.input.Mouse;

import com.hbm.config.GeneralConfig;
import com.hbm.config.GeneralConfig.MagazineMode;
import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.handler.HbmKeybinds;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.interfaces.IItemHUD;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.IEquipReceiver;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.main.ServerProxy;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;
import com.hbm.util.ChatBuilder;
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
			
			if(mainConfig.reloadType != GunConfiguration.RELOAD_NONE || (altConfig != null && altConfig.reloadType != 0)) {
				
				if(GameSettings.isKeyDown(HbmKeybinds.reloadKey) && Minecraft.getMinecraft().currentScreen == null && (getMag(stack) < mainConfig.ammoCap || hasInfinity(stack, mainConfig))) {
					PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 2));
					setIsReloading(stack, true);
					resetReloadCycle(stack);
				}
			}
		}
	}
	
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		
		// To try and fix some odd bug where the magazine is filled but the gun is not chambered
		if (magazineMode != MagazineMode.OFF && !isChambered(stack) && getMag(stack) > 0)
		{
			if (mainConfig.independentChamber)
				popMag(stack, mainConfig);
			else
				setChambered(stack, true);
		}
		
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
		
		if(getIsReloading(stack) && isCurrentItem)
		{
			if (GeneralConfig.magazineMode == MagazineMode.OFF)
				reload2(stack, world, player);
			else if (mainConfig.reloadType == GunConfiguration.RELOAD_FULL)
				reloadMagazine(stack, world, player);
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
		
		//cancel reload when trying to shoot if it's a single reload weapon and at least one round is loaded
		if(getIsReloading(stack) && mainConfig.reloadType == GunConfiguration.RELOAD_SINGLE && ItemGunBase.getMag(stack) > 0) {
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
		
		if(config.reloadType == GunConfiguration.RELOAD_NONE) {
			return getBeltSize(player, getBeltType(player, stack, main)) > 0;
			
		} else {
			//return getMag(stack) >= 0 + config.roundsPerCycle;
			return magazineMode == MagazineMode.OFF ? getMag(stack) > 0 : isChambered(stack);
		}
	}
	
	//called every time the gun shoots successfully, calls spawnProjectile(), sets item wear
	protected void fire(ItemStack stack, World world, EntityPlayer player) {

		final int magType = getMagType(stack);
		BulletConfiguration config = mainConfig.reloadType == GunConfiguration.RELOAD_NONE ? getBeltCfg(player, stack, true) : BulletConfigSyncingUtil.pullConfig(magazineMode == MagazineMode.OFF ? mainConfig.config.get(magType) : magType);
		
//		if(mainConfig.reloadType == GunConfiguration.RELOAD_NONE) {
//			config = getBeltCfg(player, stack, true);
//		} else {
//			// Pop the stack if using proper magazines
//			config = BulletConfigSyncingUtil.pullConfig(magazineMode == MagazineMode.OFF ? mainConfig.config.get(magType) : magType);
//		}
		
		final boolean validRound = magazineMode == MagazineMode.OFF ? magType >= 0 && magType < mainConfig.config.size() : mainConfig.config.contains(magType);
		// Fail if next bullet is incompatible
		if ((magazineMode == MagazineMode.FAST || magazineMode == MagazineMode.IMMERSIVE) && !validRound)
		{
			// Gun breaks, but can be fixed
			PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.startTranslation(HbmCollection.firedBadRound).color(EnumChatFormatting.YELLOW).flush(), ServerProxy.ID_GUN_MODE, 5000), (EntityPlayerMP) player);
			world.playSoundAtEntity(player, mainConfig.firingSound, mainConfig.firingVolume, mainConfig.firingPitch);
			setItemWear(stack, mainConfig.durability);
			// Pop and eject so player isn't stuck with a bad round
			setMagType(stack, ItemMagazineTest.popBullet(getMagazineNBT(stack, mainConfig), mainConfig));
			if(mainConfig.ejector != null && !mainConfig.ejector.getAfterReload())
				queueCasing(player, mainConfig.ejector, config, stack);
			return;
		} else if ((mainConfig.drm || magazineMode == MagazineMode.HIDEOUS) && !validRound)
		{
			// They weren't lying, that destructor do be hideous
			PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.startTranslation(HbmCollection.firedBadRoundDRM).nextTranslation(mainConfig.manufacturer.getKey()).color(EnumChatFormatting.RED).flush(), ServerProxy.ID_GUN_MODE, 5000), (EntityPlayerMP) player);
			world.createExplosion(player, player.posX, player.posY, player.posZ, 2 + config.explosive, false);
			stack.stackSize--;
			return;
		}
		
		// If outside of magazine mode
		if (!validRound)
		{
			player.addChatMessage(ChatBuilder.start("Could not fire weapon because bullet type is out of bounds, this should not be possible!").color(EnumChatFormatting.RED).flush());
			return;
		}
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < mainConfig.roundsPerCycle; k++) {
			
			if(!hasAmmo(stack, player, true))
				break;
			
			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, magazineMode == MagazineMode.OFF ? mainConfig.config.get(magType) : magType);//BulletConfigSyncingUtil.getKey(config));
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

		BulletConfiguration config = altConfig.reloadType == GunConfiguration.RELOAD_NONE ? getBeltCfg(player, stack, false) : BulletConfigSyncingUtil.pullConfig(magazineMode == MagazineMode.OFF ? altConfig.config.get(getMagType(stack)) : getMagType(stack));
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < altConfig.roundsPerCycle; k++) {
			
			if(altConfig.reloadType != GunConfiguration.RELOAD_NONE && !hasAmmo(stack, player, true))
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
	@SuppressWarnings("static-method")
	protected void spawnProjectile(World world, EntityPlayer player, @SuppressWarnings("unused") ItemStack stack, int config) {
		
		EntityBulletBaseNT bullet = new EntityBulletBaseNT(world, config, player);
		world.spawnEntityInWorld(bullet);
		
		if(player instanceof EntityPlayerMP)
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal()), (EntityPlayerMP) player);
			
	}
	
	//called on click (server side, called by mouse packet) for semi-automatics and specific events
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {

		if(mainConfig.firingMode == GunConfiguration.FIRE_MANUAL && main && tryShoot(stack, world, player, main)) {
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
	@SuppressWarnings("unused")
	public void startActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) {/**/ }
	
	//called on click release (server side, called by mouse packet) for release actions like charged shots
	@SuppressWarnings("unused")
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main) {/**/ }
	
	//called on click release (client side, called by update cycle)
	@SuppressWarnings("unused")
	public void endActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) {/**/ }
	
	//current reload
	protected void reload2(ItemStack stack, World world, EntityPlayer player) {
		
		if(getMag(stack) >= mainConfig.ammoCap) {
			setIsReloading(stack, false);
			return;
		}
		
		if(getReloadCycle(stack) <= 0) {
			// Don't have to mess with mag type retrieval here since this method is not used when magazines are on
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
	
	protected void reloadMagazine(ItemStack stack, @SuppressWarnings("unused") World world, EntityPlayer player)
	{
		// TODO Test
		// Failsafe to skip reloading if don't need to
		if (getMag(stack) >= mainConfig.ammoCap)
			return;

		ItemStack magStack = null;
		
		// Try reproduce magazine item and return to inventory or drop to ground if the gun has one inside
		if (mainConfig.absorbsMag && hasMagazineNBT(stack))
		{
			magStack = new ItemStack(ModItems.gun_magazine_test, 1, getMagazineType(stack));
			magStack.stackTagCompound = getMagazineNBT(stack, mainConfig);
			if (player.inventory.addItemStackToInventory(magStack))
				player.entityDropItem(magStack, 0);
		}
		
		if (player.inventory.hasItem(ModItems.gun_magazine_test))
		{
			// Try find first magazine
			magStack = null;// Reusing variable for convenience
			for (ItemStack testStack : player.inventory.mainInventory)
			{
				// TODO Check enum ordinal
				if (testStack != null && testStack.getItem() == ModItems.gun_magazine_test)
				{
					// If the test stack contains an illegal bullet ID
					final IntSet idStack = IntSets.immutable.of(ItemMagazineTest.getStack(testStack));
					// A bit complicated because negative entries represent unused ones and wouldn't work with a regular containsAll() call.
					// Also make sure the i < 0 condition is first for short circuiting on the easier operation
					if (!idStack.allSatisfy(i -> i < 0 || mainConfig.config.contains(i)))
					{
						// Skip if on fast mode
						if (magazineMode == MagazineMode.FAST)
							continue;
						else if (player instanceof EntityPlayerMP)
						{
//							PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start(magazineMode == MagazineMode.HIDEOUS ? "Using a special round not supported by this gun's DRM is against ToS" : "This magazine/speed loader contained a bullet that won't work in this gun...").color(EnumChatFormatting.YELLOW).flush(), ServerProxy.ID_GUN_MODE, 3000), (EntityPlayerMP) player);
							PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.startTranslation(magazineMode == MagazineMode.HIDEOUS ? HbmCollection.loadedBadRoundDRM : HbmCollection.loadedBadRound).color(EnumChatFormatting.YELLOW).flush(), ServerProxy.ID_GUN_MODE, 5000), (EntityPlayerMP) player);
							// Fail-fast if on IMMERSIVE mode
							if (magazineMode == MagazineMode.IMMERSIVE)
								return;
							else
								break;// Allow if on HIDEOUS mode
						}
					}
					
					magStack = testStack;
					break;
				}
			}
			
			if (magStack != null)
			{
				// Consume magazine and set NBT
				if (mainConfig.absorbsMag)
				{
					magStack.stackSize--;
					setMagazineType(stack, magStack);
					setMagazineNBT(stack, magStack, mainConfig);
				} else
				{
					// Eject old rounds
					while (getMagType(stack) >= 0)
					{
						final int bulletID = popMag(stack, mainConfig);
						player.inventory.addItemStackToInventory(BulletConfigSyncingUtil.pullConfig(bulletID).ammo.toStack());
					}
					
					// Now set magazine
					setMagazineType(stack, magStack);
					setMagazineNBT(stack, magStack, mainConfig);

					// Empty magazine
					Arrays.fill(ItemMagazineTest.getStack(magStack), -1);
					ItemMagazineTest.setStackPointer(magStack, -1);
				}
				if (mainConfig.independentChamber && !isChambered(stack) && getMag(stack) >= 0)
				{
					popMag(stack, mainConfig);
					setChambered(stack, true);
				}
			}
			setIsReloading(stack, false);
		}
	}
	
	//initiates a reload
	public void startReloadAction(ItemStack stack, World world, EntityPlayer player) {
		
		if(player.isSneaking() && hasInfinity(stack, mainConfig)) {
			
			if(ItemGunBase.getMag(stack) == mainConfig.ammoCap) {
				ItemGunBase.setMag(stack, 0);
				this.resetAmmoType(stack, world, player);
				world.playSoundAtEntity(player, "tile.piston.out", 1.0F, 1.0F);
			}
			
			return;
		}
		
		if(ItemGunBase.getMag(stack) == mainConfig.ammoCap)
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
		
		if (magazineMode != MagazineMode.OFF)
			return hasMagazineNBT(stack) || player.inventory.hasItem(ModItems.gun_magazine_test);// TODO Change to AStack of appropriate magazine type
		
		if(getMag(stack) >= mainConfig.ammoCap && hasInfinity(stack, mainConfig))
			return true;

		if(getMag(stack) == 0) {
			
//			for(int config : mainConfig.config.toArray()) {
			for (int i = 0; i < mainConfig.config.size(); i++)
			{
				final int config = mainConfig.config.get(i);
				if(InventoryUtil.doesPlayerHaveAStack(player, BulletConfigSyncingUtil.pullConfig(config).ammo, false, false)) {
					return true;
				}
			}
			
		} else {
			ComparableStack ammo = BulletConfigSyncingUtil.pullConfig(magazineMode == MagazineMode.OFF ? mainConfig.config.get(getMagType(stack)) : Math.max(getMagType(stack), mainConfig.config.getFirst())).ammo;
			return InventoryUtil.doesPlayerHaveAStack(player, ammo, false, false);
		}
		
		return false;
	}
	
	//searches the player's inv for next fitting ammo type and changes the gun's mag
	protected void resetAmmoType(ItemStack stack, World world, EntityPlayer player) {

//		for(int config : mainConfig.config.toArray()) {
		for (int i = 0; i < mainConfig.config.size(); i++)
		{
			final int config = mainConfig.config.get(i);
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
			
			if(InventoryUtil.doesPlayerHaveAStack(player, cfg.ammo, false, false)) {
				setMagType(stack, mainConfig.config.indexOf(config));
				break;
			}
		}
	}
	
	//item mouseover text
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, @SuppressWarnings("rawtypes") List list, boolean bool) {
		
		final int magType = getMagType(stack);
		ComparableStack ammo = BulletConfigSyncingUtil.pullConfig(magazineMode == MagazineMode.OFF ? mainConfig.config.get(magType) : Math.max(magType, mainConfig.config.get(0))).ammo;
		
		// +1 to account for it being the stack pointer and the round in the chamber
		list.add(I18nUtil.resolveKey(HbmCollection.ammo, mainConfig.ammoCap > 0 ? I18nUtil.resolveKey(HbmCollection.ammoMag, getMag(stack) + (isChambered(stack) ? 1 : 0), magazineMode == MagazineMode.OFF ? mainConfig.ammoCap : getMagCapacity(stack, mainConfig)) : I18nUtil.resolveKey(HbmCollection.ammoBelt)));
		
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
//			e.printStackTrace();
//			list.add("Error: " + e + " has occurred!");
			list.add("Caught exception " + e + " trying to generate main tooltip info");
			for (StackTraceElement trace : e.getStackTrace())
				list.add("    " + trace.toString());
		}

		addAdditionalInformation(stack, list);
	}
	
	protected void addAdditionalInformation(ItemStack stack, List<String> list)
	{
		try
		{
			final BulletConfiguration bulletConfig = BulletConfigSyncingUtil.pullConfig(magazineMode == MagazineMode.OFF ? mainConfig.config.get(getMagType(stack)) : Math.max(getMagType(stack), mainConfig.config.getFirst()));
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
				// Better readability with this demarcation
				list.add("");
				list.add(EnumChatFormatting.BLUE + "[EXTENDED INFO]");
				list.add("Type: " + getMagType(stack));
				list.add("Is Reloading: " + getIsReloading(stack));
				list.add("Reload Cycle: " + getReloadCycle(stack));
				list.add("RoF Cooldown: " + getDelay(stack));
				
				if (magazineMode != MagazineMode.OFF && hasMagazineNBT(stack))
				{
					final NBTTagCompound magNBT = getMagazineNBT(stack, mainConfig);
					list.add("Stack: " + Arrays.toString(ItemMagazineTest.getStack(magNBT)));
					list.add("Stack pointer: " + ItemMagazineTest.getStackPointer(magNBT));
					list.add("Stack capacity: " + magNBT.getShort(ItemMagazineTest.CAPACITY_KEY));// TODO Make method
					list.add("One chambered? " + isChambered(stack));
				}
			}
		} catch (Exception e)
		{
//			e.printStackTrace();
//			list.add("Error: " + e + " has occurred!");
			list.add("Caught exception " + e + " trying to generate additional tooltip info");
			for (StackTraceElement trace : e.getStackTrace())
				list.add("    " + trace.toString());
		}
	}
	
	//returns ammo item of belt-weapons
	public static ComparableStack getBeltType(EntityPlayer player, ItemStack stack, boolean main) {
		ItemGunBase gun = (ItemGunBase)stack.getItem();
		GunConfiguration guncfg = main ? gun.mainConfig : (gun.altConfig != null ? gun.altConfig : gun.mainConfig);

//		for(Integer config : guncfg.config.toArray()) {
		for (int i = 0; i < guncfg.config.size(); i++)
		{
			final int config = guncfg.config.get(i);
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
	
//		for(int config : guncfg.config.toArray()) {
		for (int i = 0; i < guncfg.config.size(); i++)
		{
			final int config = guncfg.config.get(i);
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

		if (config.reloadType != GunConfiguration.RELOAD_NONE && magazineMode == MagazineMode.OFF) {// Magazine depletes via pop
			setMag(stack, getMag(stack) - 1);
		} else if (config.reloadType != GunConfiguration.RELOAD_NONE && magazineMode != MagazineMode.OFF)
		{
			if (ItemMagazineTest.getStackPointer(getMagazineNBT(stack, config)) >= 0)
				popMag(stack, config);
			else
				setChambered(stack, false);
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
		return magazineMode == MagazineMode.OFF
				? readNBT(stack, "magazine")
						: hasMagazineNBT(stack) ? ItemMagazineTest.getStackPointer(getMagazineNBT(stack, null)) + 1 : 0;
	}
	
	/// magazine type (int specified by index in bullet config list) ///
	public static void setMagType(ItemStack stack, int i) {
		writeNBT(stack, magazineMode == MagazineMode.OFF ? "magazineType" : "bulletID", i);
	}
	
	public static int getMagType(ItemStack stack) {
		return readNBT(stack, magazineMode == MagazineMode.OFF ? "magazineType" : "bulletID");
	}
	
	/// Get and return current mag type, but also pop off a bullet from the stack and set that as the new type ///
	public static int popMag(ItemStack stack, GunConfiguration config)
	{
		final int bullet = getMagType(stack);
		setMagType(stack, ItemMagazineTest.popBullet(getMagazineNBT(stack, config), config));
		return bullet;
	}
	
	public static int getMagCapacity(ItemStack stack, GunConfiguration config)
	{
		return getMagazineNBT(stack, config).getShort(ItemMagazineTest.CAPACITY_KEY);
	}
	
	public static boolean isChambered(ItemStack stack)
	{
		if (!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		return stack.stackTagCompound.getBoolean("chambered");
	}
	
	public static void setChambered(ItemStack stack, boolean chambered)
	{
		if (!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setBoolean("chambered", chambered);
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
	
	public static void setMagazineNBT(ItemStack stack, ItemStack magazine, GunConfiguration config)
	{
		if (!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		if (!magazine.hasTagCompound())
		{
			magazine.stackTagCompound = new NBTTagCompound();
			ItemMagazineTest.initNBT(magazine, config);
		}
		
		stack.stackTagCompound.setTag("magazineNBT", magazine.stackTagCompound);
	}
	
	public static NBTTagCompound getMagazineNBT(ItemStack stack, @Nullable GunConfiguration config)
	{
		if (!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		if (!stack.stackTagCompound.hasKey("magazineNBT"))
		{
			stack.stackTagCompound.setTag("magazineNBT", new NBTTagCompound());
			if (config != null)
				ItemMagazineTest.initNBT(stack.stackTagCompound.getCompoundTag("magazineNBT"), config);
		}
		
		return stack.stackTagCompound.getCompoundTag("magazineNBT");
	}
	
	public static void setMagazineType(ItemStack stack, ItemStack magazine)
	{
		if (!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setShort("magItemType", (short) magazine.getItemDamage());
	}
	
	public static int getMagazineType(ItemStack stack)
	{
		if (!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		return stack.stackTagCompound.hasKey("magItemType") ? stack.stackTagCompound.getShort("magItemType") : -1;
	}
	
	public static boolean hasMagazine(ItemStack stack)
	{
		if (!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		return stack.stackTagCompound.hasKey("magItemType") || stack.stackTagCompound.getShort("magItemType") < 0;
	}
	
	public static EnumMagazine getMagazineEnum(ItemStack stack)
	{
		return EnumMagazine.values()[getMagazineType(stack)];
	}
	
	public static boolean hasMagazineNBT(ItemStack stack)
	{
		return stack.hasTagCompound() ? stack.stackTagCompound.hasKey("magItemType") : false;
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
			final int mag = ItemGunBase.getMagType(stack);
			if (gun.mainConfig.config.size() == 0) return;
			BulletConfiguration bcfg = BulletConfigSyncingUtil.pullConfig(magazineMode == MagazineMode.OFF ? gcfg.config.get(mag < gcfg.config.size() ? mag : 0) : Math.max(mag, gcfg.config.get(0)));
			
			if(bcfg == null) {
				return;
			}
			
			ComparableStack ammo = bcfg.ammo;
			int count = ItemGunBase.getMag(stack);
			if (magazineMode != MagazineMode.OFF && isChambered(stack))
				count++;// To account for the round in the chamber
			int max = magazineMode == MagazineMode.OFF ? gcfg.ammoCap : getMagCapacity(stack, mainConfig);
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
