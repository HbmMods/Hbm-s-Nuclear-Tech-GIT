package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityMist;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.projectile.EntityArtilleryShell;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockMutatorDebris;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorCross;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.particle.helper.ExplosionCreator;
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
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class ItemAmmoArty extends Item {

	public static Random rand = new Random();
	public static ArtilleryShell[] itemTypes =	new ArtilleryShell[ /* >>> */ 12 /* <<< */ ];
	/* item types */
	public final int NORMAL = 0;
	public final int CLASSIC = 1;
	public final int EXPLOSIVE = 2;
	public final int MINI_NUKE = 3;
	public final int NUKE = 4;
	public final int PHOSPHORUS = 5;
	public final int MINI_NUKE_MULTI = 6;
	public final int PHOSPHORUS_MULTI = 7;
	public final int CARGO = 8;
	public final int CHLORINE = 9;
	public final int PHOSGENE = 10;
	public final int MUSTARD = 11;
	/* non-item shell types */

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
		list.add(new ItemStack(item, 1, PHOSPHORUS_MULTI));
		list.add(new ItemStack(item, 1, MINI_NUKE));
		list.add(new ItemStack(item, 1, MINI_NUKE_MULTI));
		list.add(new ItemStack(item, 1, NUKE));
		list.add(new ItemStack(item, 1, CARGO));
		list.add(new ItemStack(item, 1, CHLORINE));
		list.add(new ItemStack(item, 1, PHOSGENE));
		list.add(new ItemStack(item, 1, MUSTARD));
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
		case PHOSPHORUS_MULTI:
			list.add(r + "Splits x10");
			break;
		case MINI_NUKE:
			list.add(y + "Strength: 20");
			list.add(r + "Deals nuclear damage");
			list.add(r + "Destroys blocks");
			break;
		case MINI_NUKE_MULTI:
			list.add(r + "Splits x5");
			break;
		case NUKE:
			list.add(r + "☠");
			list.add(r + "(that is the best skull and crossbones");
			list.add(r + "minecraft's unicode has to offer)");
			break;
		case CARGO:

			if(stack.hasTagCompound() && stack.stackTagCompound.getCompoundTag("cargo") != null) {
				ItemStack cargo = ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("cargo"));
				list.add(y + cargo.getDisplayName());
			} else {
				list.add(r + "Empty");
			}
			break;
		}
	}

	private IIcon[] icons = new IIcon[itemTypes.length];
	private IIcon iconCargo;

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {

		this.icons = new IIcon[itemTypes.length];

		for(int i = 0; i < icons.length; i++) {
			this.icons[i] = reg.registerIcon(RefStrings.MODID + ":" + itemTypes[i].name);
		}

		this.iconCargo = reg.registerIcon(RefStrings.MODID + ":ammo_arty_cargo_full");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {

		if(stack.getItemDamage() == CARGO && stack.hasTagCompound() && stack.stackTagCompound.getCompoundTag("cargo") != null) {
			return this.iconCargo;
		}

		return this.getIconFromDamage(stack.getItemDamage());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return this.icons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + itemTypes[Math.abs(stack.getItemDamage()) % itemTypes.length].name;
	}

	protected static SpentCasing SIXTEEN_INCH_CASE = new SpentCasing(CasingType.STRAIGHT).setScale(15F, 15F, 10F).setupSmoke(1F, 1D, 200, 60).setMaxAge(300).setBounceMotion(1F, 0.5F);

	public abstract class ArtilleryShell {

		String name;
		public SpentCasing casing;

		public ArtilleryShell(String name, int casingColor) {
			this.name = name;
			this.casing = SIXTEEN_INCH_CASE.clone().register(name).setColor(casingColor);
		}

		public abstract void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop);
		public void onUpdate(EntityArtilleryShell shell) { }
	}

	public static void standardExplosion(EntityArtilleryShell shell, MovingObjectPosition mop, float size, float rangeMod, boolean breaksBlocks) {
		Vec3 vec = Vec3.createVectorHelper(shell.motionX, shell.motionY, shell.motionZ).normalize();
		ExplosionVNT xnt = new ExplosionVNT(shell.worldObj, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, size);
		if(breaksBlocks) {
			xnt.setBlockAllocator(new BlockAllocatorStandard(48));
			xnt.setBlockProcessor(new BlockProcessorStandard().setNoDrop().withBlockEffect(new BlockMutatorDebris(ModBlocks.block_slag, 1)));
		}
		xnt.setEntityProcessor(new EntityProcessorCross(7.5D).withRangeMod(rangeMod));
		xnt.setPlayerProcessor(new PlayerProcessorStandard());
		//xnt.setSFX(new ExplosionEffectStandard());
		xnt.explode();
		shell.killAndClear();
	}

	public static void standardCluster(EntityArtilleryShell shell, int clusterType, int amount, double splitHeight, double deviation) {
		if(!shell.getWhistle() || shell.motionY > 0) return;
		if(shell.getTargetHeight() + splitHeight < shell.posY) return;

		shell.killAndClear();

		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "plasmablast");
		data.setFloat("r", 1.0F);
		data.setFloat("g", 1.0F);
		data.setFloat("b", 1.0F);
		data.setFloat("scale", 50F);
		PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, shell.posX, shell.posY, shell.posZ),
				new TargetPoint(shell.dimension, shell.posX, shell.posY, shell.posZ, 500));

		for(int i = 0; i < amount; i++) {
			EntityArtilleryShell cluster = new EntityArtilleryShell(shell.worldObj);
			cluster.setType(clusterType);
			cluster.motionX = i == 0 ? shell.motionX : (shell.motionX + rand.nextGaussian() * deviation);
			cluster.motionY = shell.motionY;
			cluster.motionZ = i == 0 ? shell.motionZ : (shell.motionZ + rand.nextGaussian() * deviation);
			cluster.setPositionAndRotation(shell.posX, shell.posY, shell.posZ, shell.rotationYaw, shell.rotationPitch);
			double[] target = shell.getTarget();
			cluster.setTarget(target[0], target[1], target[2]);
			cluster.setWhistle(shell.getWhistle() && !shell.didWhistle());
			shell.worldObj.spawnEntityInWorld(cluster);
		}
	}

	private void init() {
		/* STANDARD SHELLS */
		this.itemTypes[NORMAL] = new ArtilleryShell("ammo_arty", SpentCasing.COLOR_CASE_16INCH) { public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) { standardExplosion(shell, mop, 10F, 3F, false); ExplosionCreator.composeEffect(shell.worldObj, mop.blockX + 0.5, mop.blockY + 0.5, mop.blockZ + 0.5, 10, 2F, 0.5F, 25F, 5, 0, 20, 0.75F, 1F, -2F, 150); }};
		this.itemTypes[CLASSIC] = new ArtilleryShell("ammo_arty_classic", SpentCasing.COLOR_CASE_16INCH) { public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) { standardExplosion(shell, mop, 15F, 5F, false); ExplosionCreator.composeEffect(shell.worldObj, mop.blockX + 0.5, mop.blockY + 0.5, mop.blockZ + 0.5, 15, 5F, 1F, 45F, 10, 0, 50, 1F, 3F, -2F, 200); }};
		this.itemTypes[EXPLOSIVE] = new ArtilleryShell("ammo_arty_he", SpentCasing.COLOR_CASE_16INCH) { public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) { standardExplosion(shell, mop, 15F, 3F, true); ExplosionCreator.composeEffect(shell.worldObj, mop.blockX + 0.5, mop.blockY + 0.5, mop.blockZ + 0.5, 15, 5F, 1F, 45F, 10, 16, 50, 1F, 3F, -2F, 200); }};

		/* MINI NUKE */
		this.itemTypes[MINI_NUKE] = new ArtilleryShell("ammo_arty_mini_nuke", SpentCasing.COLOR_CASE_16INCH_NUKE) {
			public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				shell.killAndClear();
				Vec3 vec = Vec3.createVectorHelper(shell.motionX, shell.motionY, shell.motionZ).normalize();
				ExplosionNukeSmall.explode(shell.worldObj, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, ExplosionNukeSmall.PARAMS_MEDIUM);
			}
		};

		/* FULL NUKE */
		this.itemTypes[NUKE] = new ArtilleryShell("ammo_arty_nuke", SpentCasing.COLOR_CASE_16INCH_NUKE) {
			public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				shell.worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(shell.worldObj, BombConfig.missileRadius, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord));
				EntityNukeTorex.statFac(shell.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, BombConfig.missileRadius);
				shell.setDead();
			}
		};

		/* PHOSPHORUS */
		this.itemTypes[PHOSPHORUS] = new ArtilleryShell("ammo_arty_phosphorus", SpentCasing.COLOR_CASE_16INCH_PHOS) {
			public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				shell.worldObj.playSoundEffect(shell.posX, shell.posY, shell.posZ, "hbm:weapon.explosionMedium", 20.0F, 0.9F + rand.nextFloat() * 0.2F);
				standardExplosion(shell, mop, 10F, 3F, false);
				//shell.worldObj.playSoundEffect(shell.posX, shell.posY, shell.posZ, "hbm:weapon.explosionMedium", 20.0F, 0.9F + shell.worldObj.rand.nextFloat() * 0.2F);
				ExplosionLarge.spawnShrapnels(shell.worldObj, (int) mop.hitVec.xCoord, (int) mop.hitVec.yCoord, (int) mop.hitVec.zCoord, 15);
				ExplosionChaos.burn(shell.worldObj, (int) mop.hitVec.xCoord, (int) mop.hitVec.yCoord, (int) mop.hitVec.zCoord, 12);
				int radius = 15;
				List<Entity> hit = shell.worldObj.getEntitiesWithinAABBExcludingEntity(shell, AxisAlignedBB.getBoundingBox(shell.posX - radius, shell.posY - radius, shell.posZ - radius, shell.posX + radius, shell.posY + radius, shell.posZ + radius));
				for(Entity e : hit) {
					e.setFire(5);
					if(e instanceof EntityLivingBase) {
						PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 30 * 20, 0, true);
						eff.getCurativeItems().clear();
						((EntityLivingBase)e).addPotionEffect(eff);
					}
				}
				for(int i = 0; i < 5; i++) {
					NBTTagCompound haze = new NBTTagCompound();
					haze.setString("type", "haze");
					PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(haze, mop.hitVec.xCoord + shell.worldObj.rand.nextGaussian() * 10, mop.hitVec.yCoord, mop.hitVec.zCoord + shell.worldObj.rand.nextGaussian() * 10), new TargetPoint(shell.dimension, shell.posX, shell.posY, shell.posZ, 150));
				}
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "rbmkmush");
				data.setFloat("scale", 10);
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord), new TargetPoint(shell.dimension, shell.posX, shell.posY, shell.posZ, 250));
			}
		};

		/* THIS DOOFUS */
		this.itemTypes[CARGO] = new ArtilleryShell("ammo_arty_cargo", SpentCasing.COLOR_CASE_16INCH) { public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
			if(mop.typeOfHit == MovingObjectType.BLOCK) {
				shell.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
				shell.getStuck(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit);
			}
		}};

		/* GAS */
		this.itemTypes[CHLORINE] = new ArtilleryShell("ammo_arty_chlorine", SpentCasing.COLOR_CASE_16INCH) {
			public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				shell.killAndClear();
				Vec3 vec = Vec3.createVectorHelper(shell.motionX, shell.motionY, shell.motionZ).normalize();
				shell.worldObj.createExplosion(shell, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, 5F, false);
				EntityMist mist = new EntityMist(shell.worldObj);
				mist.setType(Fluids.CHLORINE);
				mist.setPosition(mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord - 3, mop.hitVec.zCoord - vec.zCoord);
				mist.setArea(15, 7.5F);
				shell.worldObj.spawnEntityInWorld(mist);
				PollutionHandler.incrementPollution(shell.worldObj, mop.blockX, mop.blockY, mop.blockZ, PollutionType.HEAVYMETAL, 5F);
			}
		};
		this.itemTypes[PHOSGENE] = new ArtilleryShell("ammo_arty_phosgene", SpentCasing.COLOR_CASE_16INCH_NUKE) {
			public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				shell.killAndClear();
				Vec3 vec = Vec3.createVectorHelper(shell.motionX, shell.motionY, shell.motionZ).normalize();
				shell.worldObj.createExplosion(shell, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, 5F, false);
				for(int i = 0; i < 3; i++) {
					EntityMist mist = new EntityMist(shell.worldObj);
					mist.setType(Fluids.PHOSGENE);
					double x = mop.hitVec.xCoord - vec.xCoord;
					double z = mop.hitVec.zCoord - vec.zCoord;
					if(i > 0) {
						x += rand.nextGaussian() * 15;
						z += rand.nextGaussian() * 15;
					}
					mist.setPosition(x, mop.hitVec.yCoord - vec.yCoord - 5, z);
					mist.setArea(15, 10);
					shell.worldObj.spawnEntityInWorld(mist);
				}
				PollutionHandler.incrementPollution(shell.worldObj, mop.blockX, mop.blockY, mop.blockZ, PollutionType.HEAVYMETAL, 10F);
				PollutionHandler.incrementPollution(shell.worldObj, mop.blockX, mop.blockY, mop.blockZ, PollutionType.POISON, 15F);
			}
		};
		this.itemTypes[MUSTARD] = new ArtilleryShell("ammo_arty_mustard_gas", SpentCasing.COLOR_CASE_16INCH_NUKE) {
			public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				shell.killAndClear();
				Vec3 vec = Vec3.createVectorHelper(shell.motionX, shell.motionY, shell.motionZ).normalize();
				shell.worldObj.createExplosion(shell, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, 5F, false);
				for(int i = 0; i < 5; i++) {
					EntityMist mist = new EntityMist(shell.worldObj);
					mist.setType(Fluids.MUSTARDGAS);
					double x = mop.hitVec.xCoord - vec.xCoord;
					double z = mop.hitVec.zCoord - vec.zCoord;
					if(i > 0) {
						x += rand.nextGaussian() * 25;
						z += rand.nextGaussian() * 25;
					}
					mist.setPosition(x, mop.hitVec.yCoord - vec.yCoord - 5, z);
					mist.setArea(20, 10);
					shell.worldObj.spawnEntityInWorld(mist);
				}
				PollutionHandler.incrementPollution(shell.worldObj, mop.blockX, mop.blockY, mop.blockZ, PollutionType.HEAVYMETAL, 15F);
				PollutionHandler.incrementPollution(shell.worldObj, mop.blockX, mop.blockY, mop.blockZ, PollutionType.POISON, 30F);
			}
		};

		/* CLUSTER SHELLS */
		this.itemTypes[PHOSPHORUS_MULTI] = new ArtilleryShell("ammo_arty_phosphorus_multi", SpentCasing.COLOR_CASE_16INCH_PHOS) {
			public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) { ItemAmmoArty.this.itemTypes[PHOSPHORUS].onImpact(shell, mop); }
			public void onUpdate(EntityArtilleryShell shell) { standardCluster(shell, PHOSPHORUS, 10, 300, 5); }
		};
		this.itemTypes[MINI_NUKE_MULTI] = new ArtilleryShell("ammo_arty_mini_nuke_multi", SpentCasing.COLOR_CASE_16INCH_NUKE) {
			public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) { ItemAmmoArty.this.itemTypes[MINI_NUKE].onImpact(shell, mop); }
			public void onUpdate(EntityArtilleryShell shell) { standardCluster(shell, MINI_NUKE, 5, 300, 5); }
		};
	}
}
