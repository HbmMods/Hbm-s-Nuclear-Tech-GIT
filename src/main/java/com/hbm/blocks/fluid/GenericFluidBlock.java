package com.hbm.blocks.fluid;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class GenericFluidBlock extends BlockFluidClassic {

	@SideOnly(Side.CLIENT)
	public IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	public IIcon flowingIcon;
	public Random rand = new Random();
	
	private String stillName;
	private String flowingName;

	public float damage;
	public DamageSource damageSource;

	public GenericFluidBlock(Fluid fluid, Material material, String still, String flowing) {
		super(fluid, material);
		setCreativeTab(null);
		stillName = still;
		flowingName = flowing;
		displacements.put(this, false);
	}

	public GenericFluidBlock setDamage(DamageSource source, float amount) {
		damageSource = source;
		damage = amount;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1) ? stillIcon : flowingIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		stillIcon = register.registerIcon(RefStrings.MODID + ":" + stillName);
		flowingIcon = register.registerIcon(RefStrings.MODID + ":" + flowingName);
	}

	/** Only temporary, will be moved into a subclass */
	@SuppressWarnings("unchecked")
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(damageSource != null) {
			
			if(entity instanceof EntityItem) {

				entity.motionX = 0;
				entity.motionY = 0;
				entity.motionZ = 0;
				
				if(entity.ticksExisted % 20 == 0 && !world.isRemote) {
					entity.attackEntityFrom(damageSource, damage * 0.1F);
					
					if(this == ModBlocks.sulfuric_acid_block && entity.isDead && ((EntityItem)entity).getEntityItem().getItem() == Items.slime_ball) {
						List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, entity.boundingBox.expand(10, 10, 10));
						
						for(EntityPlayer player : players)
							player.triggerAchievement(MainRegistry.achSulfuric);
					}
				}
				if(entity.ticksExisted % 5 == 0) {
					world.spawnParticle("cloud", entity.posX, entity.posY, entity.posZ, 0.0, 0.0, 0.0);
				}
			} else {
				
				if(entity.motionY < -0.2)
					entity.motionY *= 0.5;
				
				if(!world.isRemote) {
					entity.attackEntityFrom(damageSource, damage);
				}
			}
			
			if(entity.ticksExisted % 5 == 0) {
				world.playSoundAtEntity(entity, "random.fizz", 0.2F, 1F);
			}
		}
	}

}
