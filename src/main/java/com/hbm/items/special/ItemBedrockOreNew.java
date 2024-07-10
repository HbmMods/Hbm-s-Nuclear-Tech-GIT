package com.hbm.items.special;

import static com.hbm.inventory.OreDictManager.*;
import static com.hbm.items.special.ItemBedrockOreNew.ProcessingTrait.*;

import java.util.List;
import java.util.Locale;

import com.hbm.items.ModItems;
import com.hbm.util.EnumUtil;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.items.ItemEnums.EnumChunkType;
import com.hbm.lib.RefStrings;
import com.hbm.render.icon.RGBMutatorInterpolatedComponentRemap;
import com.hbm.render.icon.TextureAtlasSpriteMutatable;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class ItemBedrockOreNew extends Item {

	public IIcon[] icons = new IIcon[BedrockOreType.values().length * BedrockOreGrade.values().length];
	public IIcon[] overlays = new IIcon[ProcessingTrait.values().length];
	
	public ItemBedrockOreNew() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {

		
		if(reg instanceof TextureMap) {
			TextureMap map = (TextureMap) reg;
			
			for(int i = 0; i < BedrockOreGrade.values().length; i++) { BedrockOreGrade grade = BedrockOreGrade.values()[i];
				for(int j = 0; j < BedrockOreType.values().length; j++) { BedrockOreType type = BedrockOreType.values()[j];
					String placeholderName = RefStrings.MODID + ":bedrock_ore_" + grade.prefix + "_" + type.suffix + "-" + (i * BedrockOreType.values().length + j);
					TextureAtlasSpriteMutatable mutableIcon = new TextureAtlasSpriteMutatable(placeholderName, new RGBMutatorInterpolatedComponentRemap(0xFFFFFF, 0x505050, type.light, type.dark));
					map.setTextureEntry(placeholderName, mutableIcon);
					this.icons[i * BedrockOreType.values().length + j] = mutableIcon;
				}
			}
		}
		
		for(int i = 0; i < overlays.length; i++) {
			ProcessingTrait trait = ProcessingTrait.values()[i];
			overlays[i] = reg.registerIcon(RefStrings.MODID + ":bedrock_ore_overlay." + trait.name().toLowerCase(Locale.US));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		
		for(int j = 0; j < BedrockOreType.values().length; j++) { BedrockOreType type = BedrockOreType.values()[j];
			for(int i = 0; i < BedrockOreGrade.values().length; i++) { BedrockOreGrade grade = BedrockOreGrade.values()[i];
				list.add(this.make(grade, type));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public int getRenderPasses(int metadata) {
		return 1 + this.getGrade(metadata).traits.length;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		if(pass == 0) return this.getIconFromDamage(meta);
		return this.overlays[this.getGrade(meta).traits[pass - 1].ordinal()];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		int icon = this.getGrade(meta).ordinal() * BedrockOreType.values().length + this.getType(meta).ordinal();
		return icons[Math.abs(icon % icons.length)];
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		int meta = stack.getItemDamage();
		String type = StatCollector.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".type." + this.getType(meta).suffix + ".name");
		return StatCollector.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".grade." + this.getGrade(meta).name().toLowerCase(Locale.US) + ".name", type);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		for(ProcessingTrait trait : this.getGrade(stack.getItemDamage()).traits) {
			list.add(I18nUtil.resolveKey(this.getUnlocalizedNameInefficiently(stack) + ".trait." + trait.name().toLowerCase(Locale.US)));
		}
	}

	public static enum BedrockOreType {
		//												primary						sulfuric						solvent							radsolvent
		LIGHT_METAL(	0xFFFFFF, 0x353535, "light",	IRON, CU,					TI, AL, AL,						CHLOROCALCITE, LI, NA,			CHLOROCALCITE, LI, NA),
		HEAVY_METAL(	0x868686, 0x000000, "heavy",	W, PB,						GOLD, GOLD, BE,					W, PB, GOLD,					BI, BI, GOLD),
		RARE_EARTH(		0xE6E6B6, 0x1C1C00, "rare",		CO, EnumChunkType.RARE,		B, LA, NB,						ND, SR, ZR,						NB, ND, SR),
		ACTINIDE(		0xC1C7BD, 0x2B3227, "actinide",	U, TH232,					RA226, RA226, PO210,			RA226, RA226, PO210,			TC99, TC99, U238),
		NON_METAL(		0xAFAFAF, 0x0F0F0F, "nonmetal",	COAL, S,					LIGNITE, KNO, F,				P_RED, F, S,					CHLOROCALCITE, SI, SI),
		CRYSTALLINE(	0xE2FFFA, 0x1E8A77, "crystal",	REDSTONE, CINNABAR,			SODALITE, ASBESTOS, DIAMOND,	CINNABAR, ASBESTOS, EMERALD,	BORAX, MOLYSITE, SODALITE);
		//sediment
		
		public int light;
		public int dark;
		public String suffix;
		public Object primary1, primary2;
		public Object byproductAcid1, byproductAcid2, byproductAcid3;
		public Object byproductSolvent1, byproductSolvent2, byproductSolvent3;
		public Object byproductRad1, byproductRad2, byproductRad3;
		
		private BedrockOreType(int light, int dark, String suffix, Object p1, Object p2, Object bA1, Object bA2, Object bA3, Object bS1, Object bS2, Object bS3, Object bR1, Object bR2, Object bR3) {
			this.light = light;
			this.dark = dark;
			this.suffix = suffix;
			this.primary1 = p1; this.primary2 = p2;
			this.byproductAcid1 = bA1; this.byproductAcid2 = bA2; this.byproductAcid3 = bA3;
			this.byproductSolvent1 = bS1; this.byproductSolvent2 = bS2; this.byproductSolvent3 = bS3;
			this.byproductRad1 = bR1; this.byproductRad2 = bR2; this.byproductRad3 = bR3;
		}
	}
	
	public static MaterialStack toFluid(Object o, int amount) {
		if(o instanceof DictFrame) {
			NTMMaterial mat = Mats.matByName.get(((DictFrame) o).mats[0]);
			if(mat != null && mat.smeltable == SmeltingBehavior.SMELTABLE) {
				return new MaterialStack(mat, amount);
			}
		}
		return null;
	}

	public static ItemStack extract(Object o) {
		return extract(o, 1);
	}
	
	public static ItemStack extract(Object o, int amount) {
		if(o instanceof EnumChunkType) return new ItemStack(ModItems.chunk_ore, amount, ((EnumChunkType) o).ordinal());
		if(o instanceof DictFrame) {
			DictFrame frame = (DictFrame) o;
			List<ItemStack> gems = OreDictionary.getOres(frame.gem(), false); if(!gems.isEmpty()) return fromList(gems, amount);
			List<ItemStack> dusts = OreDictionary.getOres(frame.dust(), false); if(!dusts.isEmpty()) return fromList(dusts, amount);
			List<ItemStack> crystals = OreDictionary.getOres(frame.crystal(), false); if(!crystals.isEmpty()) return fromList(crystals, amount);
			List<ItemStack> billets = OreDictionary.getOres(frame.billet(), false); if(!billets.isEmpty()) return fromList(billets, amount);
			List<ItemStack> ingots = OreDictionary.getOres(frame.ingot(), false); if(!ingots.isEmpty()) return fromList(ingots, amount);
		}
		//Solely for recipe handling, to make the code more compact
		if(o instanceof ItemStack){
			ItemStack stack = (ItemStack) o;
			stack.stackSize = amount;
			return stack;
		}
		return new ItemStack(ModItems.nothing);
	}
	
	private static ItemStack fromList(List<ItemStack> list, int amount) {
		ItemStack first = list.get(0).copy();
		first.stackSize = amount;
		return first;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(pass != 0) return 0xFFFFFF;
		BedrockOreGrade grade = this.getGrade(stack.getItemDamage());
		return grade.tint;
	}

	public static final int none = 0xFFFFFF;
	public static final int roasted = 0xCFCFCF;
	public static final int arc = 0xC3A2A2;
	public static final int washed = 0xDBE2CB;
	
	public static enum ProcessingTrait {
		ROASTED,
		ARC,
		WASHED,
		CENTRIFUGED,
		SULFURIC,
		SOLVENT,
		RAD
	}
	
	public static enum BedrockOreGrade {
		BASE(none, "base"),												//from the slopper
		BASE_ROASTED(roasted, "base", ROASTED),							//optional combination oven step, yields vitriol
		BASE_WASHED(washed, "base", WASHED),							//primitive-ass acidizer with water
		PRIMARY(none, "primary", CENTRIFUGED),							//centrifuging for more primary
		PRIMARY_ROASTED(roasted, "primary", ROASTED),					//optional comboven
		PRIMARY_SULFURIC(0xFFFFD3, "primary", SULFURIC),				//sulfuric acid
		PRIMARY_NOSULFURIC(0xD3D4FF, "primary", CENTRIFUGED, SULFURIC),	//from centrifuging, sulfuric byproduct removed
		PRIMARY_SOLVENT(0xD3F0FF, "primary", SOLVENT),					//solvent
		PRIMARY_NOSOLVENT(0xFFDED3, "primary", CENTRIFUGED, SOLVENT),	//solvent byproduct removed
		PRIMARY_RAD(0xECFFD3, "primary", RAD),							//radsolvent
		PRIMARY_NORAD(0xEBD3FF, "primary", CENTRIFUGED, RAD),			//radsolvent byproduct removed
		PRIMARY_FIRST(0xFFD3D4, "primary", CENTRIFUGED),				//higher first material yield
		PRIMARY_SECOND(0xD3FFEB, "primary", CENTRIFUGED),				//higher second material yield
		CRUMBS(none, "crumbs", CENTRIFUGED),							//endpoint for primary, recycling
		
		SULFURIC_BYPRODUCT(none, "sulfuric", CENTRIFUGED, SULFURIC),	//from centrifuging
		SULFURIC_ROASTED(roasted, "sulfuric", ROASTED, SULFURIC),		//comboven again
		SULFURIC_ARC(arc, "sulfuric", ARC, SULFURIC),					//alternate step
		SULFURIC_WASHED(washed, "sulfuric", WASHED, SULFURIC),			//sulfuric endpoint
		
		SOLVENT_BYPRODUCT(none, "solvent", CENTRIFUGED, SOLVENT),		//from centrifuging
		SOLVENT_ROASTED(roasted, "solvent", ROASTED, SOLVENT),			//comboven again
		SOLVENT_ARC(arc, "solvent", ARC, SOLVENT),						//alternate step
		SOLVENT_WASHED(washed, "solvent", WASHED, SOLVENT),				//solvent endpoint
		
		RAD_BYPRODUCT(none, "rad", CENTRIFUGED, RAD),					//from centrifuging
		RAD_ROASTED(roasted, "rad", ROASTED, RAD),						//comboven again
		RAD_ARC(arc, "rad", ARC, RAD),									//alternate step
		RAD_WASHED(washed, "rad", WASHED, RAD);							//rad endpoint
		
		public int tint;
		public String prefix;
		public ProcessingTrait[] traits;
		
		private BedrockOreGrade(int tint, String prefix, ProcessingTrait... traits) {
			this.tint = tint;
			this.prefix = prefix;
			this.traits = traits;
		}
	}
	
	public static ItemStack make(BedrockOreGrade grade, BedrockOreType type) {
		return make(grade, type, 1);
	}
	
	public static ItemStack make(BedrockOreGrade grade, BedrockOreType type, int amount) {
		return new ItemStack(ModItems.bedrock_ore, amount, grade.ordinal() << 4 | type.ordinal());
	}
	
	public BedrockOreGrade getGrade(int meta) {
		return EnumUtil.grabEnumSafely(BedrockOreGrade.class, meta >> 4);
	}
	
	public BedrockOreType getType(int meta) {
		return EnumUtil.grabEnumSafely(BedrockOreType.class, meta & 15);
	}
}
