package com.hbm.blocks.generic;

import com.hbm.blocks.BlockMulti;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

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

public class BlockScaffold extends BlockMulti {
	
	protected String[] variants = new String[] {"scaffold_steel", "scaffold_red", "scaffold_white", "scaffold_yellow"};
	@SideOnly(Side.CLIENT) protected IIcon[] icons;

	public BlockScaffold() {
		super(Material.iron);
	}

	public static int renderIDScaffold = RenderingRegistry.getNextAvailableRenderId();
	
	@Override
	public int getRenderType(){
		return renderIDScaffold;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = ModBlocks.deco_steel.getIcon(0, 0);
		this.icons = new IIcon[variants.length];
		
		for(int i = 0; i < variants.length; i++) {
			this.icons[i] = reg.registerIcon(RefStrings.MODID + ":" + variants[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.icons[this.damageDropped(meta)];
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int meta = stack.getItemDamage();

		if(i % 2 == 0) {
			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		} else {
			world.setBlockMetadataWithNotify(x, y, z, meta + 8, 2);
		}
	}

	@Override
	public int damageDropped(int meta) {
		return rectify(meta) & 7;
	}

	@Override
	public int getSubCount() {
		return variants.length;
	}
}
