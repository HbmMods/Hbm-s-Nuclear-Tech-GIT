package com.hbm.module;

import java.util.List;

import com.hbm.items.special.ItemBedrockOreNew;
import com.hbm.util.BufferUtil;
import com.hbm.util.ItemStackUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ModulePatternMatcher {

	public static final String MODE_EXACT = "exact";
	public static final String MODE_WILDCARD = "wildcard";
	public static final String MODE_BEDROCK = "bedrock";
	public String[] modes;
	
	public ModulePatternMatcher() {
		this.modes = new String[1];
	}
	
	public ModulePatternMatcher(int count) {
		this.modes = new String[count];
	}
	
	public void initPatternSmart(World world, ItemStack stack, int i) {
		
		if(world.isRemote) return;
		
		if(stack == null) {
			modes[i] = null;
			return;
		}
		
		List<String> names = ItemStackUtil.getOreDictNames(stack);

		if(iterateAndCheck(names, i ,"ingot")) return;
		if(iterateAndCheck(names, i ,"block")) return;
		if(iterateAndCheck(names, i ,"dust")) return;
		if(iterateAndCheck(names, i ,"nugget")) return;
		if(iterateAndCheck(names, i ,"plate")) return;
		
		if(stack.getItem() instanceof ItemBedrockOreNew) {
			modes[i] = MODE_BEDROCK;
		} else if(stack.getHasSubtypes()) {
			modes[i] = MODE_EXACT;
		} else {
			modes[i] = MODE_WILDCARD;
		}
	}
	
	private boolean iterateAndCheck(List<String> names, int i, String prefix) {
		
		for(String s : names) {
			if(s.startsWith(prefix)) {
				modes[i] = s;
				return true;
			}
		}
		
		return false;
	}
	
	public void initPatternStandard(World world, ItemStack stack, int i) {
		
		if(world.isRemote) return;
		
		if(stack == null) {
			modes[i] = null;
			return;
		}

		if(stack.getItem() instanceof ItemBedrockOreNew) {
			modes[i] = MODE_BEDROCK;
		} else if(stack.getHasSubtypes()) {
			modes[i] = MODE_EXACT;
		} else {
			modes[i] = MODE_WILDCARD;
		}
	}
	
	public void nextMode(World world, ItemStack pattern, int i) {
		
		if(world.isRemote) return;
		
		if(pattern == null) {
			modes[i] = null;
			return;
		}
		
		if(modes[i] == null) {
			modes[i] = MODE_EXACT;
		} else if(MODE_EXACT.equals(modes[i])) {
			if(pattern.getItem() instanceof ItemBedrockOreNew) {
				modes[i] = MODE_BEDROCK;
			} else {
				modes[i] = MODE_WILDCARD;
			}
		} else if(MODE_BEDROCK.equals(modes[i])) {
			modes[i] = MODE_WILDCARD;
		} else if(MODE_WILDCARD.equals(modes[i])) {
			
			List<String> names = ItemStackUtil.getOreDictNames(pattern);
			
			if(names.isEmpty()) {
				modes[i] = MODE_EXACT;
			} else {
				modes[i] = names.get(0);
			}
		} else {
			
			List<String> names = ItemStackUtil.getOreDictNames(pattern);
			
			if(names.size() < 2 || modes[i].equals(names.get(names.size() - 1))) {
				modes[i] = MODE_EXACT;
			} else {
				
				for(int j = 0; j < names.size() - 1; j++) {
					
					if(modes[i].equals(names.get(j))) {
						modes[i] = names.get(j + 1);
						return;
					}
				}
			}
		}
	}
	
	public boolean isValidForFilter(ItemStack filter, int index, ItemStack input) {
		
		String mode = modes[index];
		
		if(mode == null) {
			modes[index] = mode = MODE_EXACT;
		}
		
		switch(mode) {
		case MODE_EXACT: return input.isItemEqual(filter) && ItemStack.areItemStackTagsEqual(input, filter);
		case MODE_WILDCARD: return input.getItem() == filter.getItem() && ItemStack.areItemStackTagsEqual(input, filter);
		case MODE_BEDROCK:
			if(input.getItem() != filter.getItem()) return false;
			if(!(input.getItem() instanceof ItemBedrockOreNew)) return false;
			return ((ItemBedrockOreNew)input.getItem()).getGrade(input.getItemDamage()) == ((ItemBedrockOreNew)filter.getItem()).getGrade(filter.getItemDamage());
		default:
			List<String> keys = ItemStackUtil.getOreDictNames(input);
			return keys.contains(mode);
		}
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		
		for(int i = 0; i < modes.length; i++) {
			if(nbt.hasKey("mode" + i)) {
				modes[i] = nbt.getString("mode" + i);
			} else {
				modes[i] = null;
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt) {

		for(int i = 0; i < modes.length; i++) {
			if(modes[i] != null) {
				nbt.setString("mode" + i, modes[i]);
			}
		}
	}
	
	public void serialize(ByteBuf buf) {
		for(int i = 0; i < modes.length; i++) {
			BufferUtil.writeString(buf, modes[i]);
		}
	}
	
	public void deserialize(ByteBuf buf) {
		for(int i = 0; i < modes.length; i++) {
			modes[i] = BufferUtil.readString(buf);
		}
	}

	public static String getLabel(String mode) {
		switch(mode) {
		case MODE_EXACT: return EnumChatFormatting.YELLOW + "Item and meta match";
		case MODE_WILDCARD: return EnumChatFormatting.YELLOW + "Item matches";
		case MODE_BEDROCK: return EnumChatFormatting.YELLOW + "Item and bedrock grade match";
		default: return EnumChatFormatting.YELLOW + "Ore dict key matches: " + mode;
		}
	}

}
