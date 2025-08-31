package com.hbm.blocks.generic;

import com.hbm.blocks.BlockMulti;
import com.hbm.lib.RefStrings;
import com.hbm.world.gen.nbt.INBTBlockTransformable;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockDecoCRT extends BlockMulti implements INBTBlockTransformable {

	protected String[] variants = new String[] {"crt_clean", "crt_broken", "crt_blinking", "crt_bsod"};
	@SideOnly(Side.CLIENT) protected IIcon[] icons;

	public BlockDecoCRT(Material mat) {
		super(mat);
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
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.icons = new IIcon[variants.length];

		for(int i = 0; i < variants.length; i++) {
			this.icons[i] = reg.registerIcon(RefStrings.MODID + ":" + variants[i]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.icons[damageDropped(meta)];
	}

	@Override
	public int damageDropped(int meta) {
		return (Math.abs(meta) % 16) / 4;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int meta = stack.getItemDamage();
		world.setBlockMetadataWithNotify(x, y, z, meta * 4 + i, 2);
	}

	@Override
	public int getSubCount() {
		return 4;
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		return INBTBlockTransformable.transformMetaDecoModel(meta, coordBaseMode);
	}

}