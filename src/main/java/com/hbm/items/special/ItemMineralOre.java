package com.hbm.items.special;

import com.hbm.config.BombConfig;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.projectile.EntityArtilleryShell;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.items.ItemEnumMulti;
import com.hbm.items.special.ItemProduct.EnumProduct;

import static com.hbm.items.special.ItemProduct.EnumProduct.*;

import java.util.List;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.particle.SpentCasing;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;

public class ItemMineralOre extends Item {

	protected IIcon overlayIcon;

	public static Mineral[] itemTypes =	new Mineral[ /* >>> */ 6 /* <<< */ ];
	String name;

	/* item types */
	public final int CLUMP_PEROXIDE = 0;
	public final int CLUMP_NITRIC = 1;
	public final int CLUMP_SULFURIC = 2;
	public final int CLUMP_SOLVENT = 3;
	public final int CLUMP_HYDROCHLORIC = 4;
	public final int CLUMP_SCHRABIDIC = 5;
	/* non-item shell types */
	
	public ItemMineralOre() {
		this.setHasSubtypes(true);
		this.setCreativeTab(MainRegistry.partsTab);
		init();

	}
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		String r = EnumChatFormatting.RED + "";
		String y = EnumChatFormatting.YELLOW + "";
		String b = EnumChatFormatting.BLUE + "";
		String o = EnumChatFormatting.GOLD + "";
		String g = EnumChatFormatting.GREEN + "";
		
		switch(stack.getItemDamage()) {
		case CLUMP_PEROXIDE:
			list.add(y + "[Peroxide]");
			break;
		case CLUMP_NITRIC:
			list.add(o + "[Nitric]");
			break;
		case CLUMP_SULFURIC:
			list.add(y + "[Sulfuric]");
			break;
		case CLUMP_SOLVENT:
			list.add(r + "[Solvent]");
			break;
		case CLUMP_HYDROCHLORIC:
			list.add(g + "[Chloric]");
			break;
		case CLUMP_SCHRABIDIC:
			list.add(b + "[Schrabidic]");
			break;
		}
	}
	private IIcon[] icons = new IIcon[itemTypes.length];

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, CLUMP_PEROXIDE));
		list.add(new ItemStack(item, 1, CLUMP_NITRIC));
		list.add(new ItemStack(item, 1, CLUMP_SULFURIC));
		list.add(new ItemStack(item, 1, CLUMP_SOLVENT));
		list.add(new ItemStack(item, 1, CLUMP_HYDROCHLORIC));
		list.add(new ItemStack(item, 1, CLUMP_SCHRABIDIC));
	}


	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_94581_1_) {
		//super.registerIcons(p_94581_1_);
		this.icons = new IIcon[itemTypes.length];

		//this.icons[1] = p_94581_1_.registerIcon(RefStrings.MODID + ":clump_h2o2" );
		//this.icons[2] = p_94581_1_.registerIcon(RefStrings.MODID + ":clump_nitric" );
		//this.icons[3] = p_94581_1_.registerIcon(RefStrings.MODID + ":clump_sulfuric");
		//this.icons[4] = p_94581_1_.registerIcon(RefStrings.MODID + ":clump_hydrochloric");
		for(int i = 0; i < icons.length; i++) {
			this.icons[i] = p_94581_1_.registerIcon(RefStrings.MODID + ":" + itemTypes[i].name);
		}
		
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {
		
		return this.getIconFromDamage(stack.getItemDamage());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return this.icons[meta];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + itemTypes[Math.abs(stack.getItemDamage()) % itemTypes.length].name;
	}
	
	//@Override
	//public String getItemStackDisplayName(ItemStack stack) {
		
		//EnumMineralOre ore = EnumUtil.grabEnumSafely(EnumMineralOre.class, stack.getItemDamage());
		//String oreName = StatCollector.translateToLocal("item.ore." + ore.oreName.toLowerCase());
		///return StatCollector.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".name", oreName);
	//}
	public abstract class Mineral {
		
		String name;
		
		public Mineral(String name) {
			this.name = name;
		}
		
	}
	private void init() {
		this.itemTypes[CLUMP_PEROXIDE] = new Mineral("clump_peroxide"){};
		this.itemTypes[CLUMP_NITRIC] = new Mineral("clump_nitric") {};
		this.itemTypes[CLUMP_SULFURIC] = new Mineral("clump_sulfuric") {};
		this.itemTypes[CLUMP_SOLVENT] = new Mineral("clump_solvent") {};
		this.itemTypes[CLUMP_HYDROCHLORIC] = new Mineral("clump_hydrochloric") {};
		this.itemTypes[CLUMP_SCHRABIDIC] = new Mineral("clump_schrabidic") {};
	}

	
}
