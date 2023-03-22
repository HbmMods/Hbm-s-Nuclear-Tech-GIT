package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ItemEnumMulti;
import com.hbm.render.icon.RGBMutatorInterpolatedComponentRemap;
import com.hbm.render.icon.TextureAtlasSpriteMutatable;
import com.hbm.util.EnumUtil;
import com.hbm.util.function.Function;
import com.hbm.util.function.Function.*;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

/*
 * Watz Isotropic Fuel, Oxidized
 */
public class ItemWatzPellet extends ItemEnumMulti {

	public ItemWatzPellet() {
		super(EnumWatzType.class, true, true);
	}

	public static enum EnumWatzType {

		//TODO: durability
		SCHRABIDIUM(	0x32FFFF, 0x005C5C, 200,	1D,	new FunctionLogarithmic(10), null, null),
		HES(			0xffffff, 0x000000, 0,		0,	null, null, null),
		LES(			0xffffff, 0x000000, 0,		0,	null, null, null),
		MES(			0xffffff, 0x000000, 0,		0,	null, null, null),
		NP(				0xffffff, 0x000000, 0,		0,	null, null, null),
		MEU(			0xffffff, 0x000000, 0,		0,	null, null, null),
		MEP(			0xffffff, 0x000000, 0,		0,	null, null, null),
		LEAD(			0xA6A6B2, 0x03030F, 0,		0,	null, null, new FunctionSqrt(10)), //standard absorber, negative coefficient
		DU(				0xC1C7BD, 0x2B3227, 0,		0, 	null, null, new FunctionQuadratic(1D, 1D).withDiv(100)); //absorber with positive coefficient 
		
		public int colorLight;
		public int colorDark;
		public double passive;		//base flux emission
		public double heatEmission;	//reactivity(1) to heat (heat per outgoing flux)
		public Function burnFunc;	//flux to reactivity(0) (classic reactivity)
		public Function heatMult;	//reactivity(0) to reactivity(1) based on heat (temperature coefficient)
		public Function absorbFunc;	//flux to heat (flux absobtion for non-active component)
		
		private EnumWatzType(int colorLight, int colorDark, double passive, double heatEmission, Function burnFunction, Function heatMultiplier, Function absorbFunction) {
			this.colorLight = colorLight;
			this.colorDark = colorDark;
			this.passive = passive;
			this.heatEmission = heatEmission;
			this.burnFunc = burnFunction;
			this.heatMult = heatMultiplier;
			this.absorbFunc = absorbFunction;
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		
		Enum[] enums = theEnum.getEnumConstants();
		this.icons = new IIcon[enums.length];
		
		if(reg instanceof TextureMap) {
			TextureMap map = (TextureMap) reg;
			
			for(int i = 0; i < EnumWatzType.values().length; i++) {
				EnumWatzType mat = EnumWatzType.values()[i];
				String placeholderName = this.getIconString() + "-" + mat.name(); 
				TextureAtlasSpriteMutatable mutableIcon = new TextureAtlasSpriteMutatable(placeholderName, new RGBMutatorInterpolatedComponentRemap(0xD2D2D2, 0x333333, mat.colorLight, mat.colorDark));
				map.setTextureEntry(placeholderName, mutableIcon);
				icons[i] = mutableIcon;
			}
		}
		
		this.itemIcon = reg.registerIcon(this.getIconString());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		IIcon icon = super.getIconFromDamage(meta);
		return icon == null ? this.itemIcon : icon; //fallback if TextureMap fails during register
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		EnumWatzType num = EnumUtil.grabEnumSafely(EnumWatzType.class, stack.getItemDamage());
		
		String color = EnumChatFormatting.GOLD + "";
		String reset = EnumChatFormatting.RESET + "";

		if(num.passive > 0) list.add(color + "Base fission rate: " + reset + num.passive);
		if(num.heatEmission > 0) list.add(color + "Heat per flux: " + reset + num.heatEmission + " TU");
		if(num.burnFunc != null) {
			list.add(color + "Reacton function: " + reset + num.burnFunc.getLabelForFuel());
			list.add(color + "Fuel type: " + reset + num.burnFunc.getDangerFromFuel());
		}
		if(num.heatMult != null) list.add(color + "Thermal coefficient: " + reset + num.heatMult.getLabelForFuel());
		if(num.absorbFunc != null) list.add(color + "Flux capture: " + reset + num.absorbFunc.getLabelForFuel());
	}
}
