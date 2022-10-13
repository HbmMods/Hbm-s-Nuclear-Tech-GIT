package com.hbm.items.weapon;

import java.util.List;

import com.hbm.entity.projectile.EntityArtilleryRocket;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorStandard;
import com.hbm.explosion.vanillant.standard.ExplosionEffectStandard;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class ItemAmmoHIMARS extends Item {
	
	public static HIMARSRocket[] itemTypes = new HIMARSRocket[ /* >>> */ 2 /* <<< */ ];
	
	public final int SMALL = 0;
	public final int LARGE = 1;
	
	public ItemAmmoHIMARS() {
		this.setHasSubtypes(true);
		this.setCreativeTab(MainRegistry.weaponTab);
		this.setTextureName(RefStrings.MODID + ":ammo_rocket");
		init();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, SMALL));
		list.add(new ItemStack(item, 1, LARGE));
	}
	
	public abstract class HIMARSRocket {
		
		public final ResourceLocation texture;
		public final int amount;
		public final int modelType; /* 0 = sixfold/standard ; 1 = single */
		
		public HIMARSRocket(String name, int type, int amount) {
			this.texture = new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/" + name + ".png");
			this.amount = amount;
			this.modelType = type;
		}
		
		public abstract void onImpact(EntityArtilleryRocket rocket, MovingObjectPosition mop);
		public void onUpdate(EntityArtilleryRocket rocket) { }
	}
	
	public static void standardExplosion(EntityArtilleryRocket rocket, MovingObjectPosition mop, float size, float rangeMod, boolean breaksBlocks) {
		rocket.worldObj.playSoundEffect(rocket.posX, rocket.posY, rocket.posZ, "hbm:weapon.explosionMedium", 20.0F, 0.9F + rocket.worldObj.rand.nextFloat() * 0.2F);
		Vec3 vec = Vec3.createVectorHelper(rocket.motionX, rocket.motionY, rocket.motionZ).normalize();
		ExplosionVNT xnt = new ExplosionVNT(rocket.worldObj, mop.hitVec.xCoord - vec.xCoord, mop.hitVec.yCoord - vec.yCoord, mop.hitVec.zCoord - vec.zCoord, size);
		if(breaksBlocks) {
			xnt.setBlockAllocator(new BlockAllocatorStandard(48));
			xnt.setBlockProcessor(new BlockProcessorStandard().setNoDrop());
		}
		xnt.setEntityProcessor(new EntityProcessorStandard().withRangeMod(rangeMod));
		xnt.setPlayerProcessor(new PlayerProcessorStandard());
		xnt.setSFX(new ExplosionEffectStandard());
		xnt.explode();
		rocket.killAndClear();
	}
	
	private void init() {
		/* STANDARD ROCKETS */
		this.itemTypes[SMALL] = new HIMARSRocket("himars_standard", 0, 6) { public void onImpact(EntityArtilleryRocket rocket, MovingObjectPosition mop) { standardExplosion(rocket, mop, 25F, 3F, true); }};
		this.itemTypes[LARGE] = new HIMARSRocket("himars_single", 1, 1) { public void onImpact(EntityArtilleryRocket rocket, MovingObjectPosition mop) { standardExplosion(rocket, mop, 50F, 5F, true); }};
	}
}
