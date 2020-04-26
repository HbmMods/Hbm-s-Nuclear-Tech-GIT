package com.hbm.items.weapon;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.main.MainRegistry;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemGunBase extends Item implements IHoldableWeapon {

	public GunConfiguration mainConfig;
	public GunConfiguration altConfig;
	
	@SideOnly(Side.CLIENT)
	public boolean m1;// = false;
	@SideOnly(Side.CLIENT)
	public boolean m2;// = false;

	public boolean lastCurrentItem;
	public String firingSound; //TODO: make this system less crap, by doing something like allowing modification of mainConfig perhaps
	
	public ItemGunBase(GunConfiguration config) {
		mainConfig = config;
		firingSound = mainConfig.firingSound;
		this.setMaxStackSize(1);
	}
	
	public ItemGunBase(GunConfiguration config, GunConfiguration alt) {
		mainConfig = config;
		firingSound = mainConfig.firingSound;
		altConfig = alt;
		this.setMaxStackSize(1);
	}

	@Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {
		if(entity instanceof EntityPlayer) {
			isCurrentItem = ((EntityPlayer)entity).getHeldItem() == stack;
			// Make sure it's ammo type is correct
			if (!lastCurrentItem && isCurrentItem && getMagType(stack, mainConfig) == 0) {
				resetAmmoType(stack, world, (EntityPlayer)entity);
			}
			
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && world.isRemote) {
				updateClient(stack, world, (EntityPlayer)entity, slot, isCurrentItem);
			} else if (isCurrentItem) {
				updateServer(stack, world, (EntityPlayer)entity, slot, isCurrentItem);
			}
		}
    	
    }

	@SideOnly(Side.CLIENT)
	protected void updateClient(ItemStack stack, World world, EntityPlayer entity, int slot, boolean isCurrentItem) {
		
		boolean clickLeft = Mouse.isButtonDown(0);
		boolean clickRight = Mouse.isButtonDown(1);
		boolean left = m1;
		boolean right = m2;
		
		if(isCurrentItem) {
			if(left && right) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0));
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1));
				//setIsMouseDown(stack, false);
				//setIsAltDown(stack, false);
				m1 = false;
				m2 = false;
			}
			
			/// HANDLED IN MODEVENTHANDLERCLIENT.JAVA ///
			/*if(!left && !right) {
				if(clickLeft) {
					PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 0));
					//setIsMouseDown(stack, true);
					m1 = true;
				} else if(clickRight) {
					PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 1));
					//setIsAltDown(stack, true);
					m2 = true;
				}
			}*/
			
			if(left && !clickLeft) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0));
				//setIsMouseDown(stack, false);
				m1 = false;
				endActionClient(stack, world, entity, true);
			}
			
			if(right && !clickRight) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1));
				//setIsAltDown(stack, false);
				m2 = false;
				endActionClient(stack, world, entity, false);
			}
			
			if(mainConfig.reloadType != mainConfig.RELOAD_NONE || (altConfig != null && altConfig.reloadType != 0)) {
				if(Keyboard.isKeyDown(Keyboard.KEY_R) && getMag(stack) < mainConfig.ammoCap) {
					PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 2));
					setIsReloading(stack, true);
					resetReloadCycle(stack);
				}
			}
		} else {

			if(left) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0));
				m1 = false;
				endActionClient(stack, world, entity, true);
			}
			if(right) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1));
				m2 = false;
				endActionClient(stack, world, entity, false);
			}
		}
	}
	
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		
		if(getDelay(stack) > 0 && isCurrentItem)
			setDelay(stack, getDelay(stack) - 1);
				
		if(MainRegistry.enableGuns && mainConfig.firingMode == 1 && getIsMouseDown(stack) && tryShoot(stack, world, player, isCurrentItem)) {
			fire(stack, world, player);
			setDelay(stack, mainConfig.rateOfFire);
			//setMag(stack, getMag(stack) - 1);
			useUpAmmo(player, stack);
		}
		
		if(getIsReloading(stack) && isCurrentItem) {
			reload2(stack, world, player);
		}
	}
	
	//whether or not the gun can shoot in its current state
	protected boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
		if(main && getDelay(stack) == 0 && !getIsReloading(stack) && (mainConfig.unbreakable || getItemWear(stack) < mainConfig.durability)) {
			
			if(mainConfig.reloadType == mainConfig.RELOAD_NONE) {
				if (!mainConfig.ammoless) {
					return getBeltSize(player, getBeltType(player, stack)) > 0 && canShoot(stack, world, player, main);
				} else {
					return canShoot(stack, world, player, main);
				}
			} else {
				return getMag(stack) > 0 && canShoot(stack, world, player, main);
			}
		}
		
		if(!main && getDelay(stack) == 0 && !getIsReloading(stack) && (mainConfig.unbreakable || getItemWear(stack) < mainConfig.durability)) {
			//no extra conditions, alt fire has to be handled by every weapon individually in the altFire() method
			return canShoot(stack, world, player, main);
		}
		
		return false;
	}
	
	//called when trying to shoot (is used inside overrides to indicate if the gun is shootable or not)
	protected boolean canShoot(ItemStack stack, World world, EntityPlayer player, boolean main) { 
		return true; 
	}
	
	
	//called every time the gun shoots successfully, calls spawnProjectile(), sets item wear
	protected void fire(ItemStack stack, World world, EntityPlayer player) {

		BulletConfiguration config = null;
		
		if (!mainConfig.ammoless) { 
			if(mainConfig.reloadType == mainConfig.RELOAD_NONE) {
				config = getBeltCfg(player, stack);
			} else {
				config = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack, mainConfig)));
			}
		} else {
			config = BulletConfigSyncingUtil.pullConfig(getMagType(stack, mainConfig));
		}
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < mainConfig.roundsPerCycle; k++) {
			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config));
			}
			
			if (!mainConfig.unbreakable) { 
				setItemWear(stack, getItemWear(stack) + config.wear);
			}
		}
		world.playSoundAtEntity(player, firingSound, 1.0F, mainConfig.firingPitch);
	}
	
	//unlike fire(), being called does not automatically imply success, some things may still have to be handled before spawning the projectile
	protected void altFire(ItemStack stack, World world, EntityPlayer player) {
		if (altConfig != null) {
			if(!altConfig.firingSound.isEmpty())
				world.playSoundAtEntity(player, altConfig.firingSound, 1.0F, altConfig.firingPitch);
		}
	}
	
	//spawns the actual projectile, can be overridden to change projectile entity
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		EntityBulletBase bullet = new EntityBulletBase(world, config, player);
		world.spawnEntityInWorld(bullet);
	}
	
	//called on click (server side, called by mouse packet) for semi-automatics and specific events
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {
		if(mainConfig.firingMode == mainConfig.FIRE_MANUAL && main && tryShoot(stack, world, player, main)) {
			fire(stack, world, player);
			setDelay(stack, mainConfig.rateOfFire);
			//setMag(stack, getMag(stack) - 1);
			useUpAmmo(player, stack);
		}
		
		if(!main && canShoot(stack, world, player, main)) {
			// Actually start running confirmation stuff on this, canShoot
			// means that it verifies it without needing to run the other methods
			altFire(stack, world, player);
		}
	}
	
	//called on click (client side, called by update cycle)
	public void startActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) { }
	
	//called on click release (server side, called by mouse packet) for release actions like charged shots
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main) { }
	
	//called on click release (client side, called by update cycle)
	public void endActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) { }
	
	
	//reload action, if existent
	protected void reload(ItemStack stack, World world, EntityPlayer player) {
		
		if(getReloadCycle(stack) < 0 && stack == player.getHeldItem()) {
			//if the mag has bullet in them -> load only the same type
			if(getMag(stack) > 0) {
				
				Item ammo = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack, mainConfig))).ammo;
				
				//how many bullets to load
				int count;
				if (mainConfig.shellsPerReload != 0) {
					count = mainConfig.shellsPerReload;
				} else {
					count = 1;
				}
				
				if(mainConfig.reloadType == 1) {
					
					count = mainConfig.ammoCap - getMag(stack);
				}
				
				if(count == 0)
					setIsReloading(stack, false);
				
				for(int i = 0; i < count; i++) {
					
					if(getMag(stack) < mainConfig.ammoCap) {
						
						if(player.inventory.hasItem(ammo)) {
							player.inventory.consumeInventoryItem(ammo);
							setMag(stack, getMag(stack) + 1);
						} else {
							setIsReloading(stack, false);
							world.playSoundAtEntity(player, mainConfig.reloadSound, 1.0F, 1.0F);
							break;
						}
					}
					
					if(getMag(stack) == mainConfig.ammoCap) {
						setIsReloading(stack, false);
						world.playSoundAtEntity(player, mainConfig.reloadSound, 1.0F, 1.0F);
						break;
					} else {
						resetReloadCycle(stack);
					}
				}
				
			//if the mag has no bullets in them -> load new type
			} else {
				
				Item ammo = null;
				
				//determine new type
				for(Integer config : mainConfig.config) {
					
					BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
					
					if(player.inventory.hasItem(cfg.ammo)) {
						ammo = cfg.ammo;
						setMagType(stack, mainConfig.config.indexOf(config));
						break;
					}
				}
				
				//load new type if bullets are present
				if(ammo != null) {
					
					int count = 1;
					
					if(mainConfig.reloadType == 1) {
						
						count = mainConfig.ammoCap - getMag(stack);
					}
					
					for(int i = 0; i < count; i++) {
						
						if(getMag(stack) < mainConfig.ammoCap) {
							
							if(player.inventory.hasItem(ammo)) {
								player.inventory.consumeInventoryItem(ammo);
								setMag(stack, getMag(stack) + 1);
							} else {
								setIsReloading(stack, false);
								world.playSoundAtEntity(player, mainConfig.reloadSound, 1.0F, 1.0F);
								break;
							}
						}
						
						if(getMag(stack) == mainConfig.ammoCap) {
							setIsReloading(stack, false);
							world.playSoundAtEntity(player, mainConfig.reloadSound, 1.0F, 1.0F);
							break;
						} else {
							resetReloadCycle(stack);
						}
					}
				}
			}
		} else {
			setReloadCycle(stack, getReloadCycle(stack) - 1);
		}
		
		if(stack != player.getHeldItem()) {
			setReloadCycle(stack, 0);
			setIsReloading(stack, false);
		}
	}
	
	//martin 2 reload algorithm
	//now with less WET and more DRY
	//compact, readable and most importantly, FUNCTIONAL
	protected void reload2(ItemStack stack, World world, EntityPlayer player) {
		// Do not reload if it's already above the cap
		if(getMag(stack) >= mainConfig.ammoCap) {
			setIsReloading(stack, false);
			return;
		}
		// Basically make sure that it's not already reloading
		if(getReloadCycle(stack) < 0) {
			// If it isn't already reloading, then check if it's using
			// ammoless type ammunition which can change how it loads
			if (mainConfig.ammoless) {
				// If it's ammoless then reload this kind of way
				if (mainConfig.reloadType == mainConfig.RELOAD_FULL) {
					setMag(stack, mainConfig.ammoCap - getMag(stack));
				} else if (mainConfig.reloadType == mainConfig.RELOAD_SINGLE) {
					setMag(stack, getMag(stack) + 1);
				}
				resetAmmoType(stack, world, player);
			} else {
				// Ensure valid ammo type
				if(getMag(stack) == 0) {
					resetAmmoType(stack, world, player);
				}
				
				// Determine the kind of rounds to use
				BulletConfiguration bcfg = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack, mainConfig)));
				Item ammo = bcfg.ammo;
				
				boolean hasLoaded = false;
				int count = 0;
				if (mainConfig.reloadType == mainConfig.RELOAD_FULL) {
					count = (mainConfig.ammoCap - getMag(stack)) / bcfg.ammoMultiplier;
				} else if (mainConfig.reloadType == mainConfig.RELOAD_SINGLE) {
					int totalLoaded = mainConfig.shellsPerReload * bcfg.ammoMultiplier;
					if (getMag(stack) + totalLoaded > mainConfig.ammoCap) {
						// Figure out how many can be loaded
						count = (mainConfig.ammoCap - getMag(stack)) / bcfg.ammoMultiplier;
					} else {
						count = mainConfig.shellsPerReload;
					}
					/*if (getMag(stack) + mainConfig.shellsPerReload > mainConfig.ammoCap) {
						count = mainConfig.ammoCap - getMag(stack);
					} else {
						//count = mainConfig.
					}*/
					// TODO: implement
				}
				
				if (count != 0) {
					for (int i = 0; i < count; i++) {
						if (player.inventory.hasItem(ammo)) {
							player.inventory.consumeInventoryItem(ammo);
							hasLoaded = true;
							setMag(stack, getMag(stack) + bcfg.ammoMultiplier);
						} else {
							setIsReloading(stack, false);
							break;
						}
					}
					
					if (getMag(stack) >= mainConfig.ammoCap) {
						setIsReloading(stack, false);
					} else {
						resetReloadCycle(stack);
					}
					
					if (hasLoaded && mainConfig.reloadSoundEnd) {
						world.playSoundAtEntity(player, mainConfig.reloadSound, 1.0F, 1.0F);
					}
				} else {
					setIsReloading(stack, false);
					return;
				}
			}
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

		if(getIsReloading(stack))
			return;
		
		if(!mainConfig.reloadSoundEnd)
			world.playSoundAtEntity(player, mainConfig.reloadSound, 1.0F, 1.0F);
		
		setIsReloading(stack, true);
		resetReloadCycle(stack);
	}
	
	public boolean canReload(ItemStack stack, World world, EntityPlayer player) {

		if (!mainConfig.ammoless) {
			if(getMag(stack) == 0) {
			
				for(Integer config : mainConfig.config) {
				
					BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
				
					if(player.inventory.hasItem(cfg.ammo)) {
						return true;
					}
				}
			
			} else {

				Item ammo = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack, mainConfig))).ammo;
				if(player.inventory.hasItem(ammo))
					return true;
			}
		} else {
			// Ammoless guns are much simpler
			if (getMag(stack) < mainConfig.ammoCap) {
				return true;
			}
		}
		
		return false;
	}
	
	//searches the player's inv for next fitting ammo type and changes the gun's mag
	protected void resetAmmoType(ItemStack stack, World world, EntityPlayer player) {
		if (!mainConfig.ammoless) { 
			for(Integer config : mainConfig.config) {
			
				BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
				
				if(player.inventory.hasItem(cfg.ammo)) {
					setMagType(stack, mainConfig.config.indexOf(config));
					break;
				}
			}
		} else {
			if (getMagType(stack, mainConfig) == 0) {
				System.out.println("Ammo type set");
				setMagType(stack, mainConfig.ammoType);
			}
		}
	}
	
	//item mouseover text
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		// Create the variables to generate the information string off of
		String ammoName;
		String ammoCount;
		String ammoMax;
		String ammoType;
		// Figure out the ammo name
		if (mainConfig.ammoName != null) {
			ammoName = mainConfig.ammoName;
		} else {
			ammoName = "Ammo";
		}
		// Figure out what the display as the max for ammo
		if (mainConfig.ammoMaxValue != null) {
			ammoMax = mainConfig.ammoMaxValue;
		} else {
			if (mainConfig.reloadType == mainConfig.RELOAD_NONE) {
				ammoMax = null;
			} else {
				ammoMax = Integer.toString(mainConfig.ammoCap);
			}
		}
		// Figure out what to actually display for ammo
		if (mainConfig.ammoDisplayTag != null) {
			ammoCount = Integer.toString(readNBT(stack, mainConfig.ammoDisplayTag));
		} else {
			if (mainConfig.reloadType == mainConfig.RELOAD_NONE) {
				if (mainConfig.ammoless) {
					ammoCount = "Infinite";
				} else {
					ammoCount = Integer.toString(getMag(stack));
				}
			} else {
				ammoCount = Integer.toString(getMag(stack));
			}
		}
		// Figure out the ammo type
		if (!mainConfig.ammoless) {
			ammoType = I18n.format(BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack, mainConfig))).ammo.getUnlocalizedName() + ".name");
		} else {
			ammoType = null;
		}
		// Display the ammo count value
		list.add(ammoName + ": " + ((ammoMax != null) ? ammoCount + " / " + ammoMax : ammoCount));
		if (ammoType != null) {
			list.add("Ammo type: " + ammoType);
		}
		/*if (!mainConfig.ammoless) {
			Item ammo = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack, mainConfig))).ammo;
		
			if(mainConfig.ammoCap > 0)
				list.add("Ammo: " + getMag(stack) + " / " + mainConfig.ammoCap);
			else
				list.add("Ammo: Belt");
		
			list.add("Ammo Type: " + I18n.format(ammo.getUnlocalizedName() + ".name"));
		} else {
			list.add("Ammo: " + getMag(stack) + " / " + mainConfig.ammoCap);
			
			list.add("Ammo Type: N/A");
		}*/
		
		if (!mainConfig.unbreakable) {
			int dura = mainConfig.durability - getItemWear(stack);
		
			if(dura < 0)
				dura = 0;
		
			list.add("Durability: " + dura + " / " + mainConfig.durability);
		}
		
		//if(MainRegistry.enableDebugMode) {
			list.add("");
			list.add("Name: " + mainConfig.name);
			list.add("Manufacturer: " + mainConfig.manufacturer);
		//}
		
		if(!mainConfig.comment.isEmpty()) {
			list.add("");
			for(String s : mainConfig.comment)
				list.add(EnumChatFormatting.ITALIC + s);
		}
		
		if(MainRegistry.enableExtendedLogging) {
			list.add("");
			list.add("Type: " + getMagType(stack, mainConfig));
			list.add("Is Reloading: " + getIsReloading(stack));
			list.add("Reload Cycle: " + getReloadCycle(stack));
			list.add("RoF Cooldown: " + getDelay(stack));
			list.add("Uses ammo: " + !mainConfig.ammoless);
		}
	}
	
	//returns ammo item of belt-weapons
	public static Item getBeltType(EntityPlayer player, ItemStack stack) {
		ItemGunBase gun = (ItemGunBase)stack.getItem();
		Item ammo = BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(0)).ammo;

		for(Integer config : gun.mainConfig.config) {
			
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
			
			if(player.inventory.hasItem(cfg.ammo)) {
				ammo = cfg.ammo;
				break;
			}
		}
		return ammo;
	}
	
	//returns BCFG of belt-weapons
	public static BulletConfiguration getBeltCfg(EntityPlayer player, ItemStack stack) {
		ItemGunBase gun = (ItemGunBase)stack.getItem();
		getBeltType(player, stack);

		for(Integer config : gun.mainConfig.config) {
			
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
			
			if(player.inventory.hasItem(cfg.ammo)) {
				return cfg;
			}
		}

		return BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(0));
	}

	//returns ammo capacity of belt-weapons for current ammo
	public static int getBeltSize(EntityPlayer player, Item ammo) {
		
		int amount = 0;
		
		for(ItemStack stack : player.inventory.mainInventory) {
			if(stack != null && stack.getItem() == ammo)
				amount += stack.stackSize;
		}
		
		return amount;
	}
	
	//reduces ammo count for mag and belt-based weapons, should be called AFTER firing
	public void useUpAmmo(EntityPlayer player, ItemStack stack) {
		
		if(mainConfig.allowsInfinity && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0)
			return;

		for(int k = 0; k < mainConfig.roundsPerCycle; k++) {
			if(mainConfig.reloadType != mainConfig.RELOAD_NONE) {
				setMag(stack, getMag(stack) - 1);
			} else {
				if (!mainConfig.ammoless) {
					player.inventory.consumeInventoryItem(getBeltType(player, stack));
				} else {
					// Literally ammoless
					return;
				}
			}
		}
	}
	
	/*//returns main config from itemstack
	public static GunConfiguration extractConfig(ItemStack stack) {
		
		if(stack != null && stack.getItem() instanceof ItemGunBase) {
			return ((ItemGunBase)stack.getItem()).mainConfig;
		}
		
		return null;
	}*/
	
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
	
	/// Firerate delay ///
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
	
	public static int getMagType(ItemStack stack, GunConfiguration config) {
		return readNBT(stack, "magazineType");
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

}
