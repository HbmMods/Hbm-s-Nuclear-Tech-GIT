package com.hbm.items.weapon;

import java.util.List;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.projectile.EntityArtilleryShell;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorStandard;
import com.hbm.explosion.vanillant.standard.ExplosionEffectStandard;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class ItemAmmoArty extends Item {

	public static ArtilleryShell[] types = new ArtilleryShell[ /* >>> */ 6 /* <<< */ ];
	public final int NORMAL = 0;
	public final int CLASSIC = 1;
	public final int EXPLOSIVE = 2;
	public final int MINI_NUKE = 3;
	public final int NUKE = 4;
	public final int PHOSPHORUS = 5;
	
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

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		String r = EnumChatFormatting.RED + "";
		String y = EnumChatFormatting.YELLOW + "";
		String b = EnumChatFormatting.BLUE + "";
		
		switch(stack.getItemDamage()) {
		case NORMAL:
			list.add(y + "Strength: 10");
			list.add(y + "Damage modifier: 3x");
			list.add(b + "Does not destroy blocks");
			break;
		case CLASSIC:
			list.add(y + "Strength: 15");
			list.add(y + "Damage modifier: 5x");
			list.add(b + "Does not destroy blocks");
			break;
		case EXPLOSIVE:
			list.add(y + "Strength: 15");
			list.add(y + "Damage modifier: 3x");
			list.add(r + "Destroys blocks");
			break;
		case PHOSPHORUS:
			list.add(y + "Strength: 10");
			list.add(y + "Damage modifier: 3x");
			list.add(r + "Phosphorus splash");
			list.add(b + "Does not destroy blocks");
			break;
		case MINI_NUKE:
			list.add(y + "Strength: 20");
			list.add(r + "Deals nuclear damage");
			list.add(r + "Destroys blocks");
		case NUKE:
			list.add(r + "☠");
			list.add(r + "(that is the best skull and crossbones");
			list.add(r + "minecraft's unicode has to offer)");
			break;
		}
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
				
				ExplosionVNT xnt = new ExplosionVNT(shell.worldObj, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, 10F);
				xnt.setEntityProcessor(new EntityProcessorStandard().withRangeMod(3F));
				xnt.setPlayerProcessor(new PlayerProcessorStandard());
				xnt.setSFX(new ExplosionEffectStandard());
				xnt.explode();
				
				shell.setDead();
			}
		};
		this.types[CLASSIC] = new ArtilleryShell("ammo_arty_classic") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				Vec3 vec = Vec3.createVectorHelper(shell.motionX, shell.motionY, shell.motionZ).normalize();
				
				ExplosionVNT xnt = new ExplosionVNT(shell.worldObj, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, 15F);
				xnt.setEntityProcessor(new EntityProcessorStandard().withRangeMod(5F));
				xnt.setPlayerProcessor(new PlayerProcessorStandard());
				xnt.setSFX(new ExplosionEffectStandard());
				xnt.explode();
				
				shell.setDead();
			}
		};
		this.types[EXPLOSIVE] = new ArtilleryShell("ammo_arty_he") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				Vec3 vec = Vec3.createVectorHelper(shell.motionX, shell.motionY, shell.motionZ).normalize();
				
				ExplosionVNT xnt = new ExplosionVNT(shell.worldObj, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, 20F);
				xnt.setBlockAllocator(new BlockAllocatorStandard(48));
				xnt.setBlockProcessor(new BlockProcessorStandard().setNoDrop());
				xnt.setEntityProcessor(new EntityProcessorStandard().withRangeMod(3F));
				xnt.setPlayerProcessor(new PlayerProcessorStandard());
				xnt.setSFX(new ExplosionEffectStandard());
				xnt.explode();
				
				shell.setDead();
			}
		};
		this.types[MINI_NUKE] = new ArtilleryShell("ammo_arty_mini_nuke") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				ExplosionNukeSmall.explode(shell.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, ExplosionNukeSmall.medium);
				shell.setDead();
			}
		};
		this.types[NUKE] = new ArtilleryShell("ammo_arty_nuke") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				shell.worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(shell.worldObj, BombConfig.missileRadius, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord));
				EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(shell.worldObj, 1000, BombConfig.missileRadius * 0.005F);
				entity2.posX = mop.hitVec.xCoord;
				entity2.posY = mop.hitVec.yCoord;
				entity2.posZ = mop.hitVec.zCoord;
				shell.worldObj.spawnEntityInWorld(entity2);
				shell.setDead();
			}
		};
		this.types[PHOSPHORUS] = new ArtilleryShell("ammo_arty_phosphorus") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				
				Vec3 vec = Vec3.createVectorHelper(shell.motionX, shell.motionY, shell.motionZ).normalize();
				double x = mop.hitVec.xCoord - vec.xCoord;
				double y = mop.hitVec.yCoord - vec.yCoord;
				double z = mop.hitVec.zCoord - vec.zCoord;
				
				ExplosionVNT xnt = new ExplosionVNT(shell.worldObj, x, y, z, 10F);
				xnt.setEntityProcessor(new EntityProcessorStandard().withRangeMod(3F));
				xnt.setPlayerProcessor(new PlayerProcessorStandard());
				xnt.setSFX(new ExplosionEffectStandard());
				xnt.explode();
				
				ExplosionLarge.spawnShrapnels(shell.worldObj, x, y, z, 15);
				ExplosionChaos.burn(shell.worldObj, (int)x, (int)y, (int)z, 12);
				
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
