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
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && entity instanceof EntityPlayer && world.isRemote) {
			updateClient(stack, world, (EntityPlayer)entity, slot, isCurrentItem);
		} else {
			updateServer(stack, world, (EntityPlayer)entity, slot, isCurrentItem);
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
			
			if(mainConfig.reloadType != 0 || (altConfig != null && altConfig.reloadType != 0)) {
				
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
	
	private void updateServer(ItemStack stack, World world, EntityPlayer entity, int slot, boolean isCurrentItem) {
		
		if(getDelay(stack) > 0)
			setDelay(stack, getDelay(stack) - 1);
			
		if(mainConfig.firingMode == 1 && getIsMouseDown(stack) && getDelay(stack) == 0 && getMag(stack) > 0 && !getIsReloading(stack)) {
			fire(stack, world, entity);
			setDelay(stack, mainConfig.rateOfFire);
			setMag(stack, getMag(stack) - 1);
		}
		
		if(getIsReloading(stack)) {
			reload(stack, world, entity);
		}
	}
	
	//tries to shoot, bullet checks are done here
	private boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
		return false;
	}
	
	//called every time the gun shoots, overridden to change bullet entity/special additions
	private void fire(ItemStack stack, World world, EntityPlayer player) {

		BulletConfiguration config = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack)));
		
		int bullets = config.bulletsMin;
		
		if(config.bulletsMax > config.bulletsMin)
			bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
		
		for(int i = 0; i < bullets; i++) {
			EntityBulletBase bullet = new EntityBulletBase(world, mainConfig.config.get(getMagType(stack)), player);
			world.spawnEntityInWorld(bullet);
		}
		
		setItemWear(stack, getItemWear(stack) + config.wear);
		
		//player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_revolver_gold_ammo));
	}
	
	//called on click (server side, called by mouse packet)
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {

		if(mainConfig.firingMode == 0 && getIsMouseDown(stack) && getDelay(stack) == 0 && getMag(stack) > 0 && !getIsReloading(stack)) {
			fire(stack, world, player);
			setDelay(stack, mainConfig.rateOfFire);
			setMag(stack, getMag(stack) - 1);
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
							break;
						}
					}
					
					if(getMag(stack) == mainConfig.ammoCap) {
						setIsReloading(stack, false);
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
								break;
							}
						}
						
						if(getMag(stack) == mainConfig.ammoCap) {
							setIsReloading(stack, false);
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
	
	/// RoF cooldown ///
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
