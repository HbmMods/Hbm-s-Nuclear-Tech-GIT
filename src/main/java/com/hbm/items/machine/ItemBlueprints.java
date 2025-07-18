package com.hbm.items.machine;

import java.util.List;
import java.util.Map.Entry;

import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemBlueprints extends Item {

	@SideOnly(Side.CLIENT) protected IIcon iconDiscover;
	@SideOnly(Side.CLIENT) protected IIcon iconSecret;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		this.iconDiscover = reg.registerIcon(this.getIconString() + "_discover");
		this.iconSecret = reg.registerIcon(this.getIconString() + "_secret");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {
		return this.getIcon(stack, 0);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		
		if(stack.hasTagCompound()) {
			String poolName = stack.stackTagCompound.getString("pool");
			if(poolName == null) return this.itemIcon;
			if(poolName.startsWith(GenericRecipes.POOL_PREFIX_DISCOVER)) return this.iconDiscover;
			if(poolName.startsWith(GenericRecipes.POOL_PREFIX_SECRET)) return this.iconSecret;
		}
		
		return this.itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(Entry<String, List<String>> pool : GenericRecipes.blueprintPools.entrySet()) {
			String poolName = pool.getKey();
			if(!poolName.startsWith(GenericRecipes.POOL_PREFIX_SECRET)) list.add(make(poolName));
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(world.isRemote) return stack;
		if(!stack.hasTagCompound()) return stack;
		
		String poolName = stack.stackTagCompound.getString("pool");
		
		if(poolName.startsWith(GenericRecipes.POOL_PREFIX_SECRET)) return stack;
		if(!player.inventory.hasItem(Items.paper)) return stack;
		
		player.inventory.consumeInventoryItem(Items.paper);
		player.swingItem();
		
		ItemStack copy = stack.copy();
		copy.stackSize = 1;
		
		if(!player.capabilities.isCreativeMode) {
			if(stack.stackSize < stack.getMaxStackSize()) {
				stack.stackSize++;
				return stack;
			}
			
			if(!player.inventory.addItemStackToInventory(copy)) {
				copy = stack.copy();
				copy.stackSize = 1;
				player.dropPlayerItemWithRandomChoice(copy, false);
			}
			
			player.inventoryContainer.detectAndSendChanges();
		} else {
			player.dropPlayerItemWithRandomChoice(copy, false);
		}
		
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		
		if(!stack.hasTagCompound()) {
			return;
		}
		
		String poolName = stack.stackTagCompound.getString("pool");
		List<String> pool = GenericRecipes.blueprintPools.get(poolName);
		
		if(pool == null || pool.isEmpty()) {
			return;
		}
		if(poolName.startsWith(GenericRecipes.POOL_PREFIX_SECRET)) {
			list.add(EnumChatFormatting.RED + "Cannot be copied!");
		} else {
			list.add(EnumChatFormatting.YELLOW + "Right-click to copy (requires paper)");
		}
		
		for(String name : pool) {
			GenericRecipe recipe = GenericRecipes.pooledBlueprints.get(name);
			if(recipe != null) {
				list.add(recipe.getLocalizedName());
			}
		}
	}
	
	public static String grabPool(ItemStack stack) {
		if(stack == null) return null;
		if(stack.getItem() != ModItems.blueprints) return null;
		if(!stack.hasTagCompound()) return null;
		if(!stack.stackTagCompound.hasKey("pool")) return null;
		return stack.stackTagCompound.getString("pool");
	}
	
	public ItemStack make(String pool) {
		ItemStack stack = new ItemStack(ModItems.blueprints);
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setString("pool", pool);
		return stack;
	}
}
