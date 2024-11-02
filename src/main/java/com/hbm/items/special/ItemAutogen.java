package com.hbm.items.special;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.lib.RefStrings;
import com.hbm.render.icon.RGBMutatorInterpolatedComponentRemap;
import com.hbm.render.icon.TextureAtlasSpriteMutatable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemAutogen extends Item {

	MaterialShapes shape;
	
	private HashMap<NTMMaterial, String> textureOverrides = new HashMap();
	private HashMap<NTMMaterial, IIcon> iconMap = new HashMap();
	private String overrideUnlocalizedName = null;
	
	public ItemAutogen(MaterialShapes shape) {
		this.setHasSubtypes(true);
		this.shape = shape;
	}
	
	/** add override texture */
	public ItemAutogen aot(NTMMaterial mat, String tex) {
		textureOverrides.put(mat, tex);
		return this;
	}
	public ItemAutogen oun(String overrideUnlocalizedName) {
		this.overrideUnlocalizedName = overrideUnlocalizedName;
		return this;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		
		if(reg instanceof TextureMap) {
			TextureMap map = (TextureMap) reg;
			
			for(NTMMaterial mat : Mats.orderedList) {
				if(!textureOverrides.containsKey(mat) && mat.solidColorLight != mat.solidColorDark && (shape == null || mat.autogen.contains(shape))) { //only generate icons if there is no override, color variation is available and if the icon will actually be used
					String placeholderName = this.getIconString() + "-" + mat.names[0]; //the part after the dash is discarded - the name only has to be unique so that the hashmap which holds all the icon definitions can hold multiple references
					TextureAtlasSpriteMutatable mutableIcon = new TextureAtlasSpriteMutatable(placeholderName, new RGBMutatorInterpolatedComponentRemap(0xFFFFFF, 0x505050, mat.solidColorLight, mat.solidColorDark));
					map.setTextureEntry(placeholderName, mutableIcon);
					iconMap.put(mat, mutableIcon);
				}
			}
		}
		
		for(Entry<NTMMaterial, String> tex : textureOverrides.entrySet()) {
			iconMap.put(tex.getKey(), reg.registerIcon(RefStrings.MODID + ":" + tex.getValue()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {

		for(NTMMaterial mat : Mats.orderedList) {
			if(mat.autogen.contains(this.shape)) {
				list.add(new ItemStack(item, 1, mat.id));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		
		NTMMaterial mat = Mats.matById.get(meta);
		
		if(mat != null) {
			IIcon override = iconMap.get(mat);
			if(override != null) {
				return override;
			}
		}
		
		return this.itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int layer) {
		
		if(this.getIconFromDamage(stack.getItemDamage()) != this.itemIcon) {
			return 0xffffff; //custom textures don't need tints
		}
		
		NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
		
		if(mat != null) {
			return mat.moltenColor;
		}
		
		return 0xffffff;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		
		NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
		
		if(mat == null) {
			return "UNDEFINED";
		}
		
		String matName = StatCollector.translateToLocal(mat.getUnlocalizedName());
		return StatCollector.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".name", matName);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return overrideUnlocalizedName != null ? "item." + overrideUnlocalizedName : super.getUnlocalizedName(stack);
	}
}
