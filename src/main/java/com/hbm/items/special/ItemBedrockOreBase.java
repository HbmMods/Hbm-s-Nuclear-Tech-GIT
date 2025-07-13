package com.hbm.items.special;

import java.util.List;
import java.util.Random;

import com.hbm.items.special.ItemBedrockOreNew.BedrockOreType;
import com.hbm.items.tool.ItemOreDensityScanner;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class ItemBedrockOreBase extends Item {
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		ItemStack ore = new ItemStack(item);
		EntityPlayer player = MainRegistry.proxy.me();
		if(player != null) setOreAmount(ore, (int) Math.floor(player.posX), (int) Math.floor(player.posZ));
		list.add(ore);
	}

	public static double getOreAmount(ItemStack stack, BedrockOreType type) {
		if(!stack.hasTagCompound()) return 0;
		NBTTagCompound data = stack.getTagCompound();
		return data.getDouble(type.suffix);
	}
	
	public static void setOreAmount(ItemStack stack, int x, int z) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		NBTTagCompound data = stack.getTagCompound();

		for(BedrockOreType type : BedrockOreType.values()) {
			data.setDouble(type.suffix, getOreLevel(x, z, type));
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		for(BedrockOreType type : BedrockOreType.values()) {
			double amount = this.getOreAmount(stack, type);
			String typeName = StatCollector.translateToLocalFormatted("item.bedrock_ore.type." + type.suffix + ".name");
			list.add(typeName + ": " + ((int) (amount * 100)) / 100D + " (" + ItemOreDensityScanner.getColor(amount) + StatCollector.translateToLocalFormatted(ItemOreDensityScanner.translateDensity(amount)) + EnumChatFormatting.GRAY + ")");
		}
	}

	private static NoiseGeneratorPerlin[] ores = new NoiseGeneratorPerlin[BedrockOreType.values().length];
	private static NoiseGeneratorPerlin level;
	
	public static double getOreLevel(int x, int z, BedrockOreType type) {
		
		if(level == null) level = new NoiseGeneratorPerlin(new Random(2114043), 4);
		if(ores[type.ordinal()] == null) ores[type.ordinal()] = new NoiseGeneratorPerlin(new Random(2082127 + type.ordinal()), 4);
		
		double scale = 0.01D;
		
		return MathHelper.clamp_double(Math.abs(level.func_151601_a(x * scale, z * scale) * ores[type.ordinal()].func_151601_a(x * scale, z * scale)) * 0.05, 0, 2);
	}
}
