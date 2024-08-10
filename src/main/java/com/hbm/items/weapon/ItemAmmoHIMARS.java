package com.hbm.items.weapon;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityArtilleryRocket;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockMutatorDebris;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorCross;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.particle.helper.ExplosionCreator;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
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
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class ItemAmmoHIMARS extends Item {
	
	public static HIMARSRocket[] itemTypes = new HIMARSRocket[ /* >>> */ 8 /* <<< */ ];

	public static final int SMALL = 0;
	public static final int LARGE = 1;
	public static final int SMALL_HE = 2;
	public static final int SMALL_WP = 3;
	public static final int SMALL_TB = 4;
	public static final int LARGE_TB = 5;
	public static final int SMALL_MINI_NUKE = 6;
	public static final int SMALL_LAVA = 7;
	
	public ItemAmmoHIMARS() {
		this.setHasSubtypes(true);
		this.setCreativeTab(MainRegistry.weaponTab);
		this.setTextureName(RefStrings.MODID + ":ammo_rocket");
		this.setMaxStackSize(1);
		init();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, SMALL));
		list.add(new ItemStack(item, 1, SMALL_HE));
		list.add(new ItemStack(item, 1, SMALL_WP));
		list.add(new ItemStack(item, 1, SMALL_TB));
		list.add(new ItemStack(item, 1, SMALL_LAVA));
		list.add(new ItemStack(item, 1, SMALL_MINI_NUKE));
		list.add(new ItemStack(item, 1, LARGE));
		list.add(new ItemStack(item, 1, LARGE_TB));
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		String r = EnumChatFormatting.RED + "";
		String y = EnumChatFormatting.YELLOW + "";
		String b = EnumChatFormatting.BLUE + "";
		
		switch(stack.getItemDamage()) {
		case SMALL:
			list.add(y + "Strength: 20");
			list.add(y + "Damage modifier: 3x");
			list.add(b + "Does not destroy blocks");
			break;
		case SMALL_HE:
			list.add(y + "Strength: 20");
			list.add(y + "Damage modifier: 3x");
			list.add(r + "Destroys blocks");
			break;
		case SMALL_WP:
			list.add(y + "Strength: 20");
			list.add(y + "Damage modifier: 3x");
			list.add(r + "Phosphorus splash");
			list.add(b + "Does not destroy blocks");
			break;
		case SMALL_TB:
			list.add(y + "Strength: 20");
			list.add(y + "Damage modifier: 10x");
			list.add(r + "Destroys blocks");
			break;
		case SMALL_MINI_NUKE:
			list.add(y + "Strength: 20");
			list.add(r + "Deals nuclear damage");
			list.add(r + "Destroys blocks");
			break;
		case SMALL_LAVA:
			list.add(y + "Strength: 20");
			list.add(r + "Creates volcanic lava");
			list.add(r + "Destroys blocks");
			break;
		case LARGE:
			list.add(y + "Strength: 50");
			list.add(y + "Damage modifier: 5x");
			list.add(r + "Destroys blocks");
			break;
		case LARGE_TB:
			list.add(y + "Strength: 50");
			list.add(y + "Damage modifier: 12x");
			list.add(r + "Destroys blocks");
			break;
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item.ammo_himars_" + itemTypes[Math.abs(stack.getItemDamage()) % itemTypes.length].name;
	}
	
	public abstract class HIMARSRocket {
		
		public final String name;
		public final ResourceLocation texture;
		public final int amount;
		public final int modelType; /* 0 = sixfold/standard ; 1 = single */
		
		public HIMARSRocket(String name, String texture, int type) {
			this.name = name;
			this.texture = new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/" + texture + ".png");
			this.amount = type == 0 ? 6 : 1;
			this.modelType = type;
		}
		
		public abstract void onImpact(EntityArtilleryRocket rocket, MovingObjectPosition mop);
		public void onUpdate(EntityArtilleryRocket rocket) { }
	}
	
	public static void standardExplosion(EntityArtilleryRocket rocket, MovingObjectPosition mop, float size, float rangeMod, boolean breaksBlocks, Block slag, int slagMeta) {
		Vec3 vec = Vec3.createVectorHelper(rocket.motionX, rocket.motionY, rocket.motionZ).normalize();
		ExplosionVNT xnt = new ExplosionVNT(rocket.worldObj, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, size);
		if(breaksBlocks) {
			xnt.setBlockAllocator(new BlockAllocatorStandard(48));
			xnt.setBlockProcessor(new BlockProcessorStandard().setNoDrop().withBlockEffect(new BlockMutatorDebris(slag, slagMeta)));
		}
		xnt.setEntityProcessor(new EntityProcessorCross(7.5).withRangeMod(rangeMod));
		xnt.setPlayerProcessor(new PlayerProcessorStandard());
		xnt.explode();
		rocket.killAndClear();
	}
	
	public static void standardMush(EntityArtilleryRocket rocket, MovingObjectPosition mop, float size) {
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "rbmkmush");
		data.setFloat("scale", size);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord), new TargetPoint(rocket.dimension, rocket.posX, rocket.posY, rocket.posZ, 250));
	}
	
	private void init() {
		/* STANDARD ROCKETS */
		this.itemTypes[SMALL] = new HIMARSRocket("standard", "himars_standard",					0) { public void onImpact(EntityArtilleryRocket rocket, MovingObjectPosition mop) { standardExplosion(rocket, mop, 20F, 3F, false, ModBlocks.block_slag, 1); ExplosionCreator.composeEffect(rocket.worldObj, mop.blockX + 0.5, mop.blockY + 0.5, mop.blockZ + 0.5, 15, 5F, 1F, 45F, 10, 0, 50, 1F, 3F, -2F, 200); }};
		this.itemTypes[SMALL_HE] = new HIMARSRocket("standard_he", "himars_standard_he",		0) { public void onImpact(EntityArtilleryRocket rocket, MovingObjectPosition mop) { standardExplosion(rocket, mop, 20F, 3F, true, ModBlocks.block_slag, 1); ExplosionCreator.composeEffect(rocket.worldObj, mop.blockX + 0.5, mop.blockY + 0.5, mop.blockZ + 0.5, 15, 5F, 1F, 45F, 10, 16, 50, 1F, 3F, -2F, 200); }};
		this.itemTypes[SMALL_LAVA] = new HIMARSRocket("standard_lava", "himars_standard_lava",	0) { public void onImpact(EntityArtilleryRocket rocket, MovingObjectPosition mop) { standardExplosion(rocket, mop, 20F, 3F, true, ModBlocks.volcanic_lava_block, 0); }};
		this.itemTypes[LARGE] = new HIMARSRocket("single", "himars_single",						1) { public void onImpact(EntityArtilleryRocket rocket, MovingObjectPosition mop) { standardExplosion(rocket, mop, 50F, 5F, true, ModBlocks.block_slag, 1); ExplosionCreator.composeEffect(rocket.worldObj, mop.blockX + 0.5, mop.blockY + 0.5, mop.blockZ + 0.5, 30, 6.5F, 2F, 65F, 25, 16, 50, 1.25F, 3F, -2F, 350); }};

		this.itemTypes[SMALL_MINI_NUKE] = new HIMARSRocket("standard_mini_nuke", "himars_standard_mini_nuke", 0) {
			public void onImpact(EntityArtilleryRocket rocket, MovingObjectPosition mop) {
				rocket.killAndClear();
				Vec3 vec = Vec3.createVectorHelper(rocket.motionX, rocket.motionY, rocket.motionZ).normalize();
				ExplosionNukeSmall.explode(rocket.worldObj, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, ExplosionNukeSmall.PARAMS_MEDIUM);
			}
		};
		
		this.itemTypes[SMALL_WP] = new HIMARSRocket("standard_wp", "himars_standard_wp", 0) {
			public void onImpact(EntityArtilleryRocket rocket, MovingObjectPosition mop) {
				rocket.worldObj.playSoundEffect(rocket.posX, rocket.posY, rocket.posZ, "hbm:weapon.explosionMedium", 20.0F, 0.9F + rocket.worldObj.rand.nextFloat() * 0.2F);
				standardExplosion(rocket, mop, 20F, 3F, false, ModBlocks.block_slag, 1);
				ExplosionLarge.spawnShrapnels(rocket.worldObj, (int) mop.hitVec.xCoord, (int) mop.hitVec.yCoord, (int) mop.hitVec.zCoord, 30);
				ExplosionChaos.burn(rocket.worldObj, (int) mop.hitVec.xCoord, (int) mop.hitVec.yCoord, (int) mop.hitVec.zCoord, 20);
				int radius = 30;
				List<Entity> hit = rocket.worldObj.getEntitiesWithinAABBExcludingEntity(rocket, AxisAlignedBB.getBoundingBox(rocket.posX - radius, rocket.posY - radius, rocket.posZ - radius, rocket.posX + radius, rocket.posY + radius, rocket.posZ + radius));
				for(Entity e : hit) {
					e.setFire(5);
					if(e instanceof EntityLivingBase) {
						PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 30 * 20, 0, true);
						eff.getCurativeItems().clear();
						((EntityLivingBase)e).addPotionEffect(eff);
					}
				}
				for(int i = 0; i < 10; i++) {
					NBTTagCompound haze = new NBTTagCompound();
					haze.setString("type", "haze");
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(haze, mop.hitVec.xCoord + rocket.worldObj.rand.nextGaussian() * 15, mop.hitVec.yCoord, mop.hitVec.zCoord + rocket.worldObj.rand.nextGaussian() * 15), new TargetPoint(rocket.dimension, rocket.posX, rocket.posY, rocket.posZ, 150));
				}
				standardMush(rocket, mop, 15);
			}};
			
		this.itemTypes[SMALL_TB] = new HIMARSRocket("standard_tb", "himars_standard_tb", 0) {
			public void onImpact(EntityArtilleryRocket rocket, MovingObjectPosition mop) {
				rocket.worldObj.playSoundEffect(rocket.posX, rocket.posY, rocket.posZ, "hbm:weapon.explosionMedium", 20.0F, 0.9F + rocket.worldObj.rand.nextFloat() * 0.2F);
				standardExplosion(rocket, mop, 20F, 10F, true, ModBlocks.block_slag, 1);
				ExplosionLarge.spawnShrapnels(rocket.worldObj, (int) mop.hitVec.xCoord, (int) mop.hitVec.yCoord, (int) mop.hitVec.zCoord, 30);
				standardMush(rocket, mop, 20);
			}};
			
		this.itemTypes[LARGE_TB] = new HIMARSRocket("single_tb", "himars_single_tb", 1) {
			public void onImpact(EntityArtilleryRocket rocket, MovingObjectPosition mop) {
				rocket.worldObj.playSoundEffect(rocket.posX, rocket.posY, rocket.posZ, "hbm:weapon.explosionMedium", 20.0F, 0.9F + rocket.worldObj.rand.nextFloat() * 0.2F);
				standardExplosion(rocket, mop, 50F, 12F, true, ModBlocks.block_slag, 1);
				ExplosionLarge.spawnShrapnels(rocket.worldObj, (int) mop.hitVec.xCoord, (int) mop.hitVec.yCoord, (int) mop.hitVec.zCoord, 30);
				standardMush(rocket, mop, 35);
			}};
		}
}
