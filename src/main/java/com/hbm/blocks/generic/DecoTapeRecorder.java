package com.hbm.blocks.generic;

import com.hbm.world.gen.nbt.INBTBlockTransformable;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class DecoTapeRecorder extends BlockContainer implements INBTBlockTransformable {

	public DecoTapeRecorder(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

    public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType(){
		return renderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		return INBTBlockTransformable.transformMetaDeco(meta, coordBaseMode);
	}

}