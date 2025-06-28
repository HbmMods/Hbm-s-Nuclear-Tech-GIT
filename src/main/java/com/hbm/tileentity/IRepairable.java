package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.items.tool.ItemBlowtorch;
import com.hbm.util.InventoryUtil;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public interface IRepairable {

	public boolean isDamaged();
	public List<AStack> getRepairMaterials();
	public void repair();
	
	public static List<AStack> getRepairMaterials(World world, int x, int y, int z, BlockDummyable dummy, EntityPlayer player) {
		
		ItemStack held = player.getHeldItem();
		
		if(held == null || !(held.getItem() instanceof ItemBlowtorch)) return null;

		int[] pos = dummy.findCore(world, x, y, z);
		if(pos == null) return null;
		TileEntity core = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(core instanceof IRepairable)) return null;
		
		IRepairable repairable = (IRepairable) core;
		
		if(!repairable.isDamaged()) return null;
		return repairable.getRepairMaterials();
	}
	
	public static boolean tryRepairMultiblock(World world, int x, int y, int z, BlockDummyable dummy, EntityPlayer player) {

		int[] pos = dummy.findCore(world, x, y, z);
		if(pos == null) return false;
		TileEntity core = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(core instanceof IRepairable)) return false;
		
		IRepairable repairable = (IRepairable) core;
		
		if(!repairable.isDamaged()) return false;
		
		List<AStack> list = repairable.getRepairMaterials();
		if(list == null || list.isEmpty() || InventoryUtil.doesPlayerHaveAStacks(player, list, true)) {
			if(!world.isRemote) repairable.repair();
			return true;
		}
		
		return false;
	}

	@SideOnly(Side.CLIENT)
	public static void addGenericOverlay(Pre event, World world, int x, int y, int z, BlockDummyable dummyable) {
		
		List<AStack> materials = IRepairable.getRepairMaterials(world, x, y, z, dummyable, Minecraft.getMinecraft().thePlayer);
		
		if(materials == null) return;
		
		List<String> text = new ArrayList();
		text.add(EnumChatFormatting.GOLD + "Repair with:");
		
		for(AStack stack : materials) {
			try {
				ItemStack display = stack.extractForCyclingDisplay(20);
				text.add("- " + display.getDisplayName() + " x" + display.stackSize);
			} catch(Exception ex) {
				text.add(EnumChatFormatting.RED + "- ERROR");
			}
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(dummyable.getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
	
	public void tryExtinguish(World world, int x, int y, int z, EnumExtinguishType type);
	
	public static enum EnumExtinguishType {
		WATER,
		FOAM,
		SAND,
		CO2
	}
}
