package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockBase;
import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;

import api.hbm.block.IToolable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockBoltable extends BlockBase implements IToolable, ILookOverlay, IBlockMulti {
	
	public BlockBoltable(Material mat) {
		super(mat);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if(tool != ToolType.BOLT) return false;
		
		return true;
	}
	
	public List<AStack> getMaterials(int meta) {
		List<AStack> list = new ArrayList();
		
		return list;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		ItemStack held = Minecraft.getMinecraft().thePlayer.getHeldItem();
		if(held == null || held.getItem() != ModItems.boltgun) return;
		
		List<String> text = new ArrayList();
		text.add(EnumChatFormatting.GOLD + "Requires:");
		List<AStack> materials = getMaterials(world.getBlockMetadata(x, y, z));
		
		for(AStack stack : materials) {
			try {
				ItemStack display = stack.extractForCyclingDisplay(20);
				text.add("- " + display.getDisplayName() + " x" + display.stackSize);
			} catch(Exception ex) {
				text.add(EnumChatFormatting.RED + "- ERROR");
			}
		}
		
		if(!materials.isEmpty()) {
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(this.getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
		}
	}

	@Override
	public int getSubCount() {
		return 1;
	}
}
