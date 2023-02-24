package com.hbm.blocks.fluid;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.RogueWorldHandler;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class CryogenicBlock extends BlockFluidClassic {

	@SideOnly(Side.CLIENT)
	public static IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon flowingIcon;
	public Random rand = new Random();
	
	private String stillName;
	private String flowingName;

	public float damage;
	public DamageSource damageSource;

	public CryogenicBlock(Fluid fluid, Material material, String still, String flowing) {
		super(fluid, material);
		setCreativeTab(null);
		stillName = still;
		flowingName = flowing;
		displacements.put(this, false);
	}

	public CryogenicBlock setDamage(String source, float amount) {
		damageSource = new DamageSource(source);
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

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		
		if(damageSource != null && entity instanceof EntityLivingBase) {

			if(!world.isRemote) {
				entity.attackEntityFrom(damageSource, damage);				
			}
			
			if(entity.ticksExisted % 5 == 0) {
				world.playSoundAtEntity(entity, "random.fizz", 0.2F, 1F);
				world.spawnParticle("cloud", entity.posX, entity.posY, entity.posZ, 0.0, 0.1, 0.0);
			}
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

			RogueWorldHandler.freeze(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, -180);
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

			RogueWorldHandler.freeze(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, -180);
		}
	}

	@Override
	public int tickRate(World p_149738_1_) {
		return 15;
	}
}
