package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ArmorUtil;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BarbedWire extends Block {

	public BarbedWire(Material mat) {
		super(mat);
	}

	public void onEntityCollidedWithBlock(World p_149670_1_, int x, int y, int z, Entity ent) {

		ent.setInWeb();

		ent.motionX *= 0.15D;
		ent.motionY *= 0.1D;
		ent.motionZ *= 0.15D;

		if(this == ModBlocks.barbed_wire) {
			ent.attackEntityFrom(DamageSource.cactus, 2.0F);
		}

		if(this == ModBlocks.barbed_wire_fire) {
			ent.attackEntityFrom(DamageSource.cactus, 2.0F);
			ent.setFire(1);
		}

		if(this == ModBlocks.barbed_wire_poison) {
			ent.attackEntityFrom(DamageSource.cactus, 2.0F);

			if(ent instanceof EntityLivingBase)
				((EntityLivingBase) ent).addPotionEffect(new PotionEffect(Potion.poison.id, 5 * 20, 2));

		}

		if(this == ModBlocks.barbed_wire_acid) {
			ent.attackEntityFrom(DamageSource.cactus, 2.0F);

			if(ent instanceof EntityPlayer) {
				ArmorUtil.damageSuit((EntityPlayer) ent, 0, 1);
				ArmorUtil.damageSuit((EntityPlayer) ent, 1, 1);
				ArmorUtil.damageSuit((EntityPlayer) ent, 2, 1);
				ArmorUtil.damageSuit((EntityPlayer) ent, 3, 1);
			}
		}

		if(this == ModBlocks.barbed_wire_wither) {
			ent.attackEntityFrom(DamageSource.cactus, 2.0F);

			if(ent instanceof EntityLivingBase)
				((EntityLivingBase) ent).addPotionEffect(new PotionEffect(Potion.wither.id, 5 * 20, 4));
		}

		if(this == ModBlocks.barbed_wire_ultradeath) {
			ent.attackEntityFrom(ModDamageSource.pc, 5.0F);

			if(ent instanceof EntityLivingBase)
				((EntityLivingBase) ent).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 5 * 20, 9));
		}
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
		return null;
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	public int getRenderType() {
		return renderID;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if(i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
	}
}
