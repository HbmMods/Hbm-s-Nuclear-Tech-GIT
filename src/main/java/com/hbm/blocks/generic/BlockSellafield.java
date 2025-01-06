package com.hbm.blocks.generic;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.render.icon.RGBMutatorInterpolatedComponentRemap;
import com.hbm.render.icon.TextureAtlasSpriteMutatable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSellafield extends BlockHazard {
	
	@SideOnly(Side.CLIENT)
	protected IIcon[][] icons;
	
	public static final int SELLAFITE_LEVELS = 6;
	public static final int TEXTURE_VARIANTS = 4;
	
	public BlockSellafield(Material mat) {
		super(mat);
		this.setCreativeTab(MainRegistry.blockTab);
		this.needsRandomTick = true;
		this.rad = 0.5F;
	}
	
	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(entity instanceof EntityLivingBase)
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, meta < 5 ? meta : meta * 2));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		int meta = world.getBlockMetadata(x, y, z);
		ChunkRadiationManager.proxy.incrementRad(world, x, y, z, this.rad * (meta + 1));
		
		if(rand.nextInt(meta == 0 ? 25 : 15) == 0) {
			if(meta > 0)
				world.setBlockMetadataWithNotify(x, y, z, meta - 1, 2);
			else
				world.setBlock(x, y, z, ModBlocks.sellafield_slaked);
		}
	}

	@Override public void onBlockAdded(World world, int x, int y, int z) { }
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		for(byte i = 0; i < SELLAFITE_LEVELS; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		icons =  new IIcon[SELLAFITE_LEVELS][TEXTURE_VARIANTS];
		String[] names = new String[] {
				RefStrings.MODID + ":sellafield_slaked",
				RefStrings.MODID + ":sellafield_slaked_1",
				RefStrings.MODID + ":sellafield_slaked_2",
				RefStrings.MODID + ":sellafield_slaked_3"
		};
		
		if(reg instanceof TextureMap) {
			TextureMap map = (TextureMap) reg;
			
			int[][] colors = new int[][] {
				{0x4C7939, 0x41463F},
				{0x418223, 0x3E443B},
				{0x338C0E, 0x3B5431},
				{0x1C9E00, 0x394733},
				{0x02B200, 0x37492F},
				{0x00D300, 0x324C26}
			};
			
			for(int level = 0; level < SELLAFITE_LEVELS; level++) {
				int[] tint = colors[level];
				
				for(int subtype = 0; subtype < TEXTURE_VARIANTS; subtype++) {
					String texName = names[subtype];
					String placeholderName = texName + "-" + level + "-" + subtype;
					TextureAtlasSpriteMutatable mutableIcon = new TextureAtlasSpriteMutatable(placeholderName, new RGBMutatorInterpolatedComponentRemap(0x858384, 0x434343, tint[0], tint[1])).setBlockAtlas();
					map.setTextureEntry(placeholderName, mutableIcon);
					icons[level][subtype] = mutableIcon;
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		long l = (long) (x * 3129871) ^ (long)y * 116129781L ^ (long)z;
		l = l * l * 42317861L + l * 11L;
		int i = (int)(l >> 16 & 3L);
		int meta = world.getBlockMetadata(x, y, z);
		return icons[(int)(Math.abs(meta) % SELLAFITE_LEVELS)][(int)(Math.abs(i) % TEXTURE_VARIANTS)];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.icons[(int) Math.abs(meta) % this.icons.length][0];
	}
}
