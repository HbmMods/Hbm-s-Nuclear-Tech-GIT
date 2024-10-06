package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.IStepTickReceiver;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

// A lot is copied from BlockMultiSlab, but I couldn't come up with a nice way to reuse it properly
public class BlockColoredMultiSlab extends BlockSlab {
    public static List<Object[]> recipeGen = new ArrayList();
	
	public Block slabMaterial;
	public Block single;
    public int metaOffset;
    public int metaCount;

	public BlockColoredMultiSlab(Block single, Material mat, Block slabMaterial, int metaOffset, int metaCount) {
		super(single != null, mat);
		this.single = single;
		this.slabMaterial = slabMaterial;
        this.metaOffset = metaOffset;
        this.metaCount = metaCount;
		this.useNeighborBrightness = true;
		
		if(single == null) {
			for(int i = 0; i < metaCount; i++) {
				recipeGen.add(new Object[] {slabMaterial, metaOffset + i, this, i});
			}
		}
		
		this.setBlockTextureName(RefStrings.MODID + ":concrete_smooth");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		meta = (meta & 7) % metaCount;
		return slabMaterial.getIcon(side, metaOffset + meta);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(single != null ? single : this);
	}
	
	@Override
	protected ItemStack createStackedBlock(int meta) {
		return new ItemStack(Item.getItemFromBlock(single != null ? single : this), 2, (meta & 7) % metaCount);
	}
	
    @SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(single != null ? single : this);
	}

	@Override
	public String func_150002_b(int meta) {
		meta = (meta & 7) % metaCount;
        BlockEnumMulti block = (BlockEnumMulti)slabMaterial;
        String variantName = block.theEnum.getEnumConstants()[metaOffset + meta].name().toLowerCase(Locale.US);
		String baseName = super.getUnlocalizedName();
        if (baseName.charAt(baseName.length() - 2) == '_') {
            // We trim the _0, _1, etc. from the unlocalized name to better decouple the localization
            // from the implementation details
            baseName = baseName.substring(0, baseName.length() - 2);
        }
        return baseName + "." + variantName;
	}
	
	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return (super.getDamageValue(world, x, y, z) & 7) % metaCount;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		if(single == null) {
			for(int i = 0; i < metaCount; ++i) {
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		int meta = world.getBlockMetadata(x, y, z);
		meta = (meta & 7) % metaCount;
		return slabMaterial.getExplosionResistance(entity);
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		meta = (meta & 7) % metaCount;
		return slabMaterial.getBlockHardness(world, x, y, z); //relies on block not assuming that they are at that position
	}
}
