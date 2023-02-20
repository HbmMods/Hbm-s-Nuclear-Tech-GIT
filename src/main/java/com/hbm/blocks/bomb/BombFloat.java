package com.hbm.blocks.bomb;

import org.apache.logging.log4j.Level;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.interfaces.IBomb;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BombFloat extends Block implements IBomb {

	public World worldObj;

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BombFloat(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		if(this == ModBlocks.float_bomb) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":bomb_float_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":bomb_float");
		}
		if(this == ModBlocks.emp_bomb) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":bomb_emp_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":bomb_emp_side");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
	if(!world.isRemote) {
			if(GeneralConfig.enableExtendedLogging) {
			MainRegistry.logger.log(Level.INFO, "[BOMBPL]" + this.getLocalizedName() + " placed at " + x + " / " + y + " / " + z + "! " + "by "+ player.getCommandSenderName());
		}	
	}
	}

	@Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_) {
		this.worldObj = p_149695_1_;
		if(p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z)) {
			explode(p_149695_1_, x, y, z);
		}
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {
		world.playSoundEffect(x, y, z, "hbm:weapon.sparkShoot", 5.0f, world.rand.nextFloat() * 0.2F + 0.9F);

		if(!world.isRemote) {
			world.setBlock(x, y, z, Blocks.air);
			if(this == ModBlocks.float_bomb) {
				ExplosionChaos.floater(world, x, y, z, 15, 50);
				ExplosionChaos.move(world, x, y, z, 15, 0, 50, 0);
			}
			if(this == ModBlocks.emp_bomb) {
				ExplosionNukeGeneric.empBlast(world, x, y, z, 50);
				EntityEMPBlast wave = new EntityEMPBlast(world, 50);
				wave.posX = x + 0.5;
				wave.posY = y + 0.5;
				wave.posZ = z + 0.5;
				world.spawnEntityInWorld(wave);
			}
		}
		
		return BombReturnCode.DETONATED;
	}

}
