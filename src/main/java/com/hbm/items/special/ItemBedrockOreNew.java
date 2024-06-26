package com.hbm.items.special;

import static com.hbm.inventory.OreDictManager.*;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.hbm.items.ItemEnums.EnumChunkType;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBedrockOreNew extends Item {
	
	public IIcon[] icons = new IIcon[BedrockOreType.values().length * BedrockOreGrade.values().length];

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		
		for(int i = 0; i < BedrockOreGrade.values().length; i++) {
			BedrockOreGrade grade = BedrockOreGrade.values()[i];
			for(int j = 0; j < BedrockOreType.values().length; j++) {
				BedrockOreType type = BedrockOreType.values()[j];
				this.icons[i * BedrockOreType.values().length + j] = reg.registerIcon(RefStrings.MODID + ":bedrock_ore_" + grade.prefix + "_" + type.suffix);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		
		for(int i = 0; i < BedrockOreGrade.values().length; i++) {
			BedrockOreGrade grade = BedrockOreGrade.values()[i];
			for(int j = 0; j < BedrockOreType.values().length; j++) {
				BedrockOreType type = BedrockOreType.values()[j];
				list.add(this.make(grade, type));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		int icon = this.getGrade(meta).ordinal() * BedrockOreType.values().length + this.getType(meta).ordinal();
		return icons[Math.abs(icon % icons.length)];
	}

	public static enum BedrockOreType {
		//							primary															sulfuric						solvent							radsolvent
		LIGHT_METAL(	"light",	IRON, CU,														TI, AL, AL,						CHLOROCALCITE, LI, NA,			CHLOROCALCITE, LI, NA),
		HEAVY_METAL(	"heavy",	W, PB,															GOLD, GOLD, BE,					W, PB, GOLD,					BI, BI, GOLD),
		RARE_EARTH(		"rare",		CO, DictFrame.fromOne(ModItems.chunk_ore, EnumChunkType.RARE),	B, LA, NB,						ND, B, ZR,						CO, ND, ZR),
		ACTINIDE(		"actinide",	U, TH232,														RA226, RA226, PO210,			RA226, RA226, PO210,			TC99, TC99, U238),
		NON_METAL(		"nonmetal",	COAL, S,														LIGNITE, KNO, F,				P_RED, F, S,					CHLOROCALCITE, SI, SI),
		CRYSTALLINE(	"crystal",	DIAMOND, SODALITE,												CINNABAR, ASBESTOS, REDSTONE,	CINNABAR, ASBESTOS, EMERALD,	BORAX, MOLYSITE, SODALITE);

		public String suffix;
		public Object primary1, primary2;
		public Object byproductAcid1, byproductAcid2, byproductAcid3;
		public Object byproductSolvent1, byproductSolvent2, byproductSolvent3;
		public Object byproductRad1, byproductRad2, byproductRad3;
		
		private BedrockOreType(String suffix, Object p1, Object p2, Object bA1, Object bA2, Object bA3, Object bS1, Object bS2, Object bS3, Object bR1, Object bR2, Object bR3) {
			this.suffix = suffix;
			this.primary1 = p1; this.primary2 = p2;
			this.byproductAcid1 = bA1; this.byproductAcid2 = bA2; this.byproductAcid3 = bA3;
			this.byproductSolvent1 = bS1; this.byproductSolvent2 = bS2; this.byproductSolvent3 = bS3;
			this.byproductRad1 = bR1; this.byproductRad2 = bR2; this.byproductRad3 = bR3;
		}
	}
	
	public static enum BedrockOreGrade {
		BASE("base"),					//from the slopper
		BASE_ROASTED("base"),			//optional combination oven step, yields vitriol
		BASE_WASHED("base"),			//primitive-ass acidizer with water
		PRIMARY("primary"),				//centrifuging for more primary
		PRIMARY_ROASTED("primary"),		//optional comboven
		PRIMARY_SULFURIC("primary"),	//sulfuric acid
		PRIMARY_NOSULFURIC("primary"),	//from centrifuging, sulfuric byproduct removed
		PRIMARY_SOLVENT("primary"),		//solvent
		PRIMARY_NOSOLVENT("primary"),	//solvent byproduct removed
		PRIMARY_RAD("primary"),			//radsolvent
		PRIMARY_NORAD("primary"),		//radsolvent byproduct removed
		PRIMARY_FIRST("primary"),		//higher first material yield
		PRIMARY_SECOND("primary"),		//higher second material yield
		CRUMBS("crumbs"),				//endpoint for primary, recycling
		
		SULFURIC_BYPRODUCT("sulfuric"),	//from centrifuging
		SULFURIC_ROASTED("sulfuric"),	//comboven again
		SULFURIC_ARC("sulfuric"),		//alternate step
		SULFURIC_WASHED("sulfuric"),	//sulfuric endpoint
		
		SOLVENT_BYPRODUCT("solvent"),	//from centrifuging
		SOLVENT_ROASTED("solvent"),		//comboven again
		SOLVENT_ARC("solvent"),			//alternate step
		SOLVENT_WASHED("solvent"),		//solvent endpoint
		
		RAD_BYPRODUCT("rad"),			//from centrifuging
		RAD_ROASTED("rad"),				//comboven again
		RAD_ARC("rad"),					//alternate step
		RAD_WASHED("rad");				//rad endpoint
		
		public String prefix;
		
		private BedrockOreGrade(String prefix) {
			this.prefix = prefix;
		}
	}
	
	public static ItemStack make(BedrockOreGrade grade, BedrockOreType type) {
		return new ItemStack(ModItems.bedrock_ore, 1, grade.ordinal() << 4 | type.ordinal());
	}
	
	public BedrockOreGrade getGrade(int meta) {
		return EnumUtil.grabEnumSafely(BedrockOreGrade.class, meta >> 4);
	}
	
	public BedrockOreType getType(int meta) {
		return EnumUtil.grabEnumSafely(BedrockOreType.class, meta & 15);
	}
}
