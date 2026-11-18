package com.hbm.blocks.machine;

import com.hbm.lib.RefStrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

@Deprecated
public class MachineReactorControl extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;
	@SideOnly(Side.CLIENT)
	private IIcon iconBack;

	public MachineReactorControl(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":machine_controller_top");
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":machine_controller");
		this.iconBack = iconRegister.registerIcon(RefStrings.MODID + ":machine_controller_back");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_controller_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {

		if(metadata == 0)
			metadata = 3;

		if(metadata == side)
			return iconFront;

		if(side == 0 || side == 1)
			return iconTop;

		if(metadata == 2 && side == 3 || metadata == 3 && side == 2 || metadata == 4 && side == 5 || metadata == 5 && side == 4)
			return iconBack;

		return blockIcon;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}
}
