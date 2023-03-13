package com.hbm.items.special;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.items.tool.ItemColtanCompass.TextureColtass;
import com.hbm.lib.RefStrings;
import com.hbm.render.icon.RGBMutatorMultiplicative;
import com.hbm.render.icon.TextureAtlasSpriteMutatable;
import com.hbm.util.I18nUtil;

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
	private HashMap<NTMMaterial, IIcon> iconOverrides = new HashMap();
	
	public ItemAutogen(MaterialShapes shape) {
		this.setHasSubtypes(true);
		this.shape = shape;
	}
	
	/** add override texture */
	public ItemAutogen aot(NTMMaterial mat, String tex) {
		textureOverrides.put(mat, tex);
		return this;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		
		/*if(reg instanceof TextureMap) {
			TextureMap map = (TextureMap) reg;
			TextureAtlasSpriteMutatable jumpstart_my_shart = new TextureAtlasSpriteMutatable(this.getIconString(), new RGBMutatorMultiplicative(0xff0000));
			map.setTextureEntry(this.getIconString(), jumpstart_my_shart);
			this.itemIcon = jumpstart_my_shart;
		} else {
			this.itemIcon = reg.registerIcon(this.getIconString());
		}*/
		
		for(Entry<NTMMaterial, String> tex : textureOverrides.entrySet()) {
			iconOverrides.put(tex.getKey(), reg.registerIcon(RefStrings.MODID + ":" + tex.getValue()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		
		for(NTMMaterial mat : Mats.orderedList) {
			if(mat.shapes.contains(this.shape)) {
				list.add(new ItemStack(item, 1, mat.id));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		
		NTMMaterial mat = Mats.matById.get(meta);
		
		if(mat != null) {
			IIcon override = iconOverrides.get(mat);
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
			//return mat.solidColor;
		}
		
		return 0xffffff;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		
		NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
		
		if(mat == null) {
			return "UNDEFINED";
		}
		
		String matName = I18nUtil.resolveKey(mat.getUnlocalizedName());
		return StatCollector.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".name", matName);
	}
}
