package com.hbm.items.weapon;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;

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

public class ItemGunBase extends Item implements IHoldableWeapon {

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
		mainConfig = config;
		altConfig = alt;
		this.setMaxStackSize(1);
	}

	@Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {
		
		//if(!isCurrentItem)
		//	return;
		
		if(entity instanceof EntityPlayer) {
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && world.isRemote) {
				updateClient(stack, world, (EntityPlayer)entity, slot, isCurrentItem);
			} else if(isCurrentItem) {
				updateServer(stack, world, (EntityPlayer)entity, slot, isCurrentItem);
			}
		}
    	
    }

	@SideOnly(Side.CLIENT)
	private void updateClient(ItemStack stack, World world, EntityPlayer entity, int slot, boolean isCurrentItem) {
		
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
			}
			
			if(right && !clickRight) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1));
				//setIsAltDown(stack, false);
				m2 = false;
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
			}
			if(right) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1));
				m2 = false;
			}
		}
	}
	
	private void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		
		if(getDelay(stack) > 0 && isCurrentItem)
			setDelay(stack, getDelay(stack) - 1);
			
		if(MainRegistry.enableGuns && mainConfig.firingMode == 1 && getIsMouseDown(stack) && tryShoot(stack, world, player, isCurrentItem)) {
			
			fire(stack, world, player);
			setDelay(stack, mainConfig.rateOfFire);
			//setMag(stack, getMag(stack) - 1);
			useUpAmmo(player, stack);
		}
		
		if(getIsReloading(stack) && isCurrentItem) {
			reload(stack, world, player);
		}
	}
	
	//tries to shoot, bullet checks are done here
	private boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		if(getDelay(stack) == 0 && !getIsReloading(stack) && getItemWear(stack) < mainConfig.durability) {
			
			if(mainConfig.reloadType == mainConfig.RELOAD_NONE) {
				return getBeltSize(player, getBeltType(player, stack)) > 0;
				
			} else {
				return getMag(stack) > 0;
			}
		}
		
		return false;
	}
	
	//called every time the gun shoots, overridden to change bullet entity/special additions
	private void fire(ItemStack stack, World world, EntityPlayer player) {

		BulletConfiguration config = null;
		
		if(mainConfig.reloadType == mainConfig.RELOAD_NONE) {
			config = getBeltCfg(player, stack);
		} else {
			config = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack)));
		}
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < mainConfig.roundsPerCycle; k++) {
			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config));
			}
			
			setItemWear(stack, getItemWear(stack) + config.wear);
		}
		world.playSoundAtEntity(player, mainConfig.firingSound, 1.0F, mainConfig.firingPitch);
		
		//player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_revolver_gold_ammo));
	}
	
	private void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		
		EntityBulletBase bullet = new EntityBulletBase(world, config, player);
		world.spawnEntityInWorld(bullet);
	}
	
	//called on click (server side, called by mouse packet)
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {

		if(mainConfig.firingMode == mainConfig.FIRE_MANUAL && getIsMouseDown(stack) && tryShoot(stack, world, player, main)) {
			fire(stack, world, player);
			setDelay(stack, mainConfig.rateOfFire);
			//setMag(stack, getMag(stack) - 1);
			useUpAmmo(player, stack);
		}
	}
	
	//called on click release
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
	}
	
	//reload action, if existent
	private void reload(ItemStack stack, World world, EntityPlayer player) {
		
		if(getReloadCycle(stack) < 0 && stack == player.getHeldItem()) {
			
			//if the mag has bullet in them -> load only the same type
			if(getMag(stack) > 0) {
				
				Item ammo = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack))).ammo;
				
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
					setIsReloading(stack, false);
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
	
	//item mouseover text
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		Item ammo = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack))).ammo;
		
		if(mainConfig.ammoCap > 0)
			list.add("Ammo: " + getMag(stack) + " / " + mainConfig.ammoCap);
		else
			list.add("Ammo: Belt");
		
		list.add("Ammo Type: " + I18n.format(ammo.getUnlocalizedName() + ".name"));
		
		int dura = mainConfig.durability - getItemWear(stack);
		
		if(dura < 0)
			dura = 0;
		
		list.add("Durability: " + dura + " / " + mainConfig.durability);
		
		if(MainRegistry.enableDebugMode) {
			list.add("");
			list.add("Name: " + mainConfig.name);
			list.add("Manufacturer: " + mainConfig.manufacturer);
		}
		
		if(MainRegistry.enableExtendedLogging) {
			list.add("");
			list.add("Type: " + getMagType(stack));
			list.add("Is Reloading: " + getIsReloading(stack));
			list.add("Reload Cycle: " + getReloadCycle(stack));
			list.add("RoF Cooldown: " + getDelay(stack));
		}
	}
	
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
	
	public static BulletConfiguration getBeltCfg(EntityPlayer player, ItemStack stack) {
		
		ItemGunBase gun = (ItemGunBase)stack.getItem();
		Item ammo = getBeltType(player, stack);

		for(Integer config : gun.mainConfig.config) {
			
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
			
			if(player.inventory.hasItem(cfg.ammo)) {
				return cfg;
			}
		}

		return BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(0));
	}
	
	public static int getBeltSize(EntityPlayer player, Item ammo) {
		
		int amount = 0;
		
		for(ItemStack stack : player.inventory.mainInventory) {
			if(stack != null && stack.getItem() == ammo)
				amount += stack.stackSize;
		}
		
		return amount;
	}
	
	public void useUpAmmo(EntityPlayer player, ItemStack stack) {
		
		if(mainConfig.allowsInfinity && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0)
			return;

		for(int k = 0; k < mainConfig.roundsPerCycle; k++) {
			if(mainConfig.reloadType != mainConfig.RELOAD_NONE)
				setMag(stack, getMag(stack) - 1);
			else
				player.inventory.consumeInventoryItem(getBeltType(player, stack));
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
	
	/// NBT utility ///
	private static void writeNBT(ItemStack stack, String key, int value) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger(key, value);
	}
	
	private static int readNBT(ItemStack stack, String key) {
		
		if(!stack.hasTagCompound())
			return 0;
		
		return stack.stackTagCompound.getInteger(key);
	}

	@Override
	public Crosshair getCrosshair() {
		return mainConfig.crosshair;
	}

}
