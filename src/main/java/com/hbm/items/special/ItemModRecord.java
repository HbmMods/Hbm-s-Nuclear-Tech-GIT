package com.hbm.items.special;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemModRecord extends ItemRecord {

	private static final Map modRecords = new HashMap();
	public final String recordName;

	public ItemModRecord(String string) {
		super(string);
		recordName = string;
		modRecords.put(string, this);
	}

	@Override
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {

		if(p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) == Blocks.jukebox && p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_) == 0) {
			
			if(p_77648_3_.isRemote) {
				return true;
				
			} else {
				((BlockJukebox) Blocks.jukebox).func_149926_b(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_1_);
				p_77648_3_.playAuxSFXAtEntity((EntityPlayer) null, 1005, p_77648_4_, p_77648_5_, p_77648_6_, Item.getIdFromItem(this));
				--p_77648_1_.stackSize;
				return true;
			}
			
		} else {
			return false;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
		p_77624_3_.add(this.getRecordNameLocal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getRecordNameLocal() {
		return StatCollector.translateToLocal("item.record." + this.recordName + ".desc");
	}

	/**
	 * Return an item rarity from EnumRarity
	 */
	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumRarity.rare;
	}

	/**
	 * Return the record item corresponding to the given name.
	 */
	@SideOnly(Side.CLIENT)
	public static ItemRecord getRecord(String p_150926_0_) {
		return (ItemRecord) modRecords.get(p_150926_0_);
	}
	
	@Override
	public ResourceLocation getRecordResource(String name) {

		String s = "";

		if(name.equals("records.lc"))
			s = RefStrings.MODID + ":music.recordLambdaCore";
		if(name.equals("records.ss"))
			s = RefStrings.MODID + ":music.recordSectorSweep";
		if(name.equals("records.vc"))
			s = RefStrings.MODID + ":music.recordVortalCombat";
		if(name.equals("records.glass"))
			s = RefStrings.MODID + ":music.transmission";
		if(name.equals("records.gs"))
			s = RefStrings.MODID + ":music.recordGodSpeed";
		if(name.equals("records.gp"))
			s = RefStrings.MODID + ":music.recordGoop";
		if(name.equals("records.el"))
			s = RefStrings.MODID + ":music.recordEthereal";

		return new ResourceLocation(s);
	}

	@Override
	public String getItemStackDisplayName(ItemStack p_77653_1_) {
		String s = (StatCollector.translateToLocal(Items.record_11.getUnlocalizedName() + ".name")).trim();

		return s;
	}
}
