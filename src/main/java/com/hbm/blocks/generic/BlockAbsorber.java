package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.IBlockMulti;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAbsorber extends BlockEnumMulti implements IBlockMulti {

	// Enum for tiers they are in order of meta data, 0, 1, 2, 3 for Base, Red, Green, Pink
	public static enum EnumAbsorberTier {

		BASE(2.5F, "absorber"), RED(10F, "absorber_red"), GREEN(100F, "absorber_green"), PINK(10000F, "absorber_pink");

		public final float absorbAmount;
		public final String textureName;

		private EnumAbsorberTier(float absorb, String texture) {
			this.absorbAmount = absorb;
			this.textureName = texture;
		}
	}

	public BlockAbsorber(Material mat) {
		super(mat, EnumAbsorberTier.class, true, true);
		this.setTickRandomly(true);
		this.setBlockName("rad_absorber");
	}

	public EnumAbsorberTier getTier(int meta) {
		return EnumAbsorberTier.values()[rectify(meta)];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		EnumAbsorberTier tier = getTier(stack.getItemDamage());
		String tierName = net.minecraft.util.StatCollector.translateToLocal("tile.rad_absorber." + tier.name().toLowerCase());
		return tierName;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(net.minecraft.client.renderer.texture.IIconRegister reg) {
		icons = new IIcon[EnumAbsorberTier.values().length];
		for(int i = 0; i < icons.length; i++) {
			icons[i] = reg.registerIcon(RefStrings.MODID + ":" + EnumAbsorberTier.values()[i].textureName);
		}
	}

	// All that rad math shit that was on there already, did not touch this
	// -Wolf

	@Override
	public int tickRate(World world) {
		return 10;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		EnumAbsorberTier tier = getTier(world.getBlockMetadata(x, y, z));
		ChunkRadiationManager.proxy.decrementRad(world, x, y, z, tier.absorbAmount);
		world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
	}
}
