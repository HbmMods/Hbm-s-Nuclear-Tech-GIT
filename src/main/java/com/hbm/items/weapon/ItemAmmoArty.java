package com.hbm.items.weapon;

import java.util.List;

import com.hbm.entity.projectile.EntityArtilleryShell;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ParticleUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class ItemAmmoArty extends Item {

	public static ArtilleryShell[] types = new ArtilleryShell[ /* >>> */ 6 /* <<< */ ];
	public int NORMAL = 0;
	public int CLASSIC = 1;
	public int EXPLOSIVE = 2;
	public int MINI_NUKE = 3;
	public int NUKE = 4;
	public int PHOSPHORUS = 5;
	
	public ItemAmmoArty() {
		this.setHasSubtypes(true);
		this.setCreativeTab(MainRegistry.weaponTab);
		init();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, NORMAL));
		list.add(new ItemStack(item, 1, CLASSIC));
		list.add(new ItemStack(item, 1, EXPLOSIVE));
		list.add(new ItemStack(item, 1, PHOSPHORUS));
		list.add(new ItemStack(item, 1, MINI_NUKE));
		list.add(new ItemStack(item, 1, NUKE));
	}
	
	private IIcon[] icons = new IIcon[types.length];

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		
		this.icons = new IIcon[types.length];

		for(int i = 0; i < icons.length; i++) {
			this.icons[i] = reg.registerIcon(RefStrings.MODID + ":" + types[i].name);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return this.icons[meta];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + types[Math.abs(stack.getItemDamage()) % types.length].name;
	}
	
	public static abstract class ArtilleryShell {
		
		String name;
		
		public ArtilleryShell(String name) {
			this.name = name;
		}
		
		public abstract void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop);
	}
	
	private void init() {
		this.types[NORMAL] = new ArtilleryShell("ammo_arty") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				Vec3 vec = Vec3.createVectorHelper(shell.motionX, shell.motionY, shell.motionZ).normalize();
				shell.worldObj.newExplosion(shell, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, 15F, false, false);
				shell.setDead();
			}
		};
		this.types[CLASSIC] = new ArtilleryShell("ammo_arty_classic") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				Vec3 vec = Vec3.createVectorHelper(shell.motionX, shell.motionY, shell.motionZ).normalize();
				shell.worldObj.newExplosion(shell, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, 25F, false, false);
				shell.setDead();
			}
		};
		this.types[EXPLOSIVE] = new ArtilleryShell("ammo_arty_he") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				Vec3 vec = Vec3.createVectorHelper(shell.motionX, shell.motionY, shell.motionZ).normalize();
				shell.worldObj.newExplosion(shell, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, 15F, false, true);
				shell.setDead();
			}
		};
		this.types[MINI_NUKE] = new ArtilleryShell("ammo_arty_mini_nuke") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				//Vec3 vec = Vec3.createVectorHelper(shell.motionX, shell.motionY, shell.motionZ).normalize();
				ExplosionNukeSmall.explode(shell.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, ExplosionNukeSmall.medium);
				shell.setDead();
			}
		};
		this.types[NUKE] = new ArtilleryShell("ammo_arty_nuke") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				shell.setDead();
			}
		};
		this.types[PHOSPHORUS] = new ArtilleryShell("ammo_arty_phosphorus") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				
				Vec3 vec = Vec3.createVectorHelper(shell.motionX, shell.motionY, shell.motionZ).normalize();
				double x = mop.hitVec.xCoord - vec.xCoord;
				double y = mop.hitVec.yCoord - vec.yCoord;
				double z = mop.hitVec.zCoord - vec.zCoord;
				shell.worldObj.newExplosion(shell, x, y, z, 15F, true, false);
				
				int radius = 15;
				List<Entity> hit = shell.worldObj.getEntitiesWithinAABBExcludingEntity(shell, AxisAlignedBB.getBoundingBox(shell.posX - radius, shell.posY - radius, shell.posZ - radius, shell.posX + radius, shell.posY + radius, shell.posZ + radius));
				
				for(Entity e : hit) {
					
					if(!Library.isObstructed(shell.worldObj, shell.posX, shell.posY, shell.posZ, e.posX, e.posY + e.getEyeHeight(), e.posZ)) {
						e.setFire(5);
						
						if(e instanceof EntityLivingBase) {
							
							PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 30 * 20, 0, true);
							eff.getCurativeItems().clear();
							((EntityLivingBase)e).addPotionEffect(eff);
						}
					}
				}
				
				for(int i = 0; i < 5; i++) {
					NBTTagCompound haze = new NBTTagCompound();
					haze.setString("type", "haze");
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(haze, x + shell.worldObj.rand.nextGaussian() * 10, y, z + shell.worldObj.rand.nextGaussian() * 10), new TargetPoint(shell.dimension, shell.posX, shell.posY, shell.posZ, 150));
				}
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "rbmkmush");
				data.setFloat("scale", 10);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord), new TargetPoint(shell.dimension, x, y, z, 250));
				
				shell.setDead();
			}
		};
	}
}
