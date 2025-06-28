package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.blocks.BlockMulti;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.MetaBlock;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.util.InventoryUtil;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.block.IToolable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockToolConversion extends BlockMulti implements IToolable, ILookOverlay {
	
	public IIcon[] icons;
	public String[] names;
	
	public BlockToolConversion(Material mat) {
		super(mat);
	}
	
	public BlockToolConversion addVariant(String... name) {
		this.names = name;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		
		if(names != null) {
			icons = new IIcon[names.length];
			for(int i = 0; i < names.length; i++) {
				icons[i] = iconRegister.registerIcon(getTextureName() + names[i]);
			}
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		
		int meta = stack.getItemDamage() - 1;
		
		if(meta == -1 || names == null || meta >= names.length) {
			return this.getUnlocalizedName();
		}
		
		return this.getUnlocalizedName() + names[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		metadata -= 1;
		
		if(metadata == -1 || icons == null || metadata >= icons.length) {
			return super.getIcon(side, metadata);
		}
		
		return icons[metadata];
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(world.isRemote) return false;
		
		Pair<AStack[], MetaBlock> result = conversions.get(new Pair(tool, new MetaBlock(this, world.getBlockMetadata(x, y, z))));
		
		if(result == null) return false;
		
		List<AStack> list = new ArrayList();
		for(AStack stack : result.key) list.add(stack);
		
		if(list == null || list.isEmpty() || InventoryUtil.doesPlayerHaveAStacks(player, list, true)) {
			world.setBlock(x, y, z, result.value.block, result.value.meta, 3);
			return true;
		}
		
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		ItemStack held = Minecraft.getMinecraft().thePlayer.getHeldItem();
		if(held == null) return;
		ToolType tool = this.quickLookup(held);
		if(tool == null) return;
		
		Pair<AStack[], MetaBlock> result = conversions.get(new Pair(tool, new MetaBlock(this, world.getBlockMetadata(x, y, z))));
		
		if(result == null) return;
		
		List<String> text = new ArrayList();
		text.add(EnumChatFormatting.GOLD + "Requires:");
		List<AStack> materials = new ArrayList();
		for(AStack stack : result.key) materials.add(stack);
		
		List<ItemStack> tools = tool.stacksForDisplay;
		ItemStack displayTool = tools.get((int) (Math.abs(System.currentTimeMillis() / 1000) % tools.size()));
		text.add(EnumChatFormatting.BLUE + "- " + displayTool.getDisplayName());
		
		for(AStack stack : materials) {
			try {
				ItemStack display = stack.extractForCyclingDisplay(20);
				text.add("- " + display.getDisplayName() + " x" + display.stackSize);
			} catch(Exception ex) {
				text.add(EnumChatFormatting.RED + "- ERROR");
			}
		}
		
		if(!materials.isEmpty()) {
			int meta = world.getBlockMetadata(x, y, z);
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(this.getUnlocalizedName(new ItemStack(this, 1, meta)) + ".name"), 0xffff00, 0x404000, text);
		}
	}

	@Override
	public int getSubCount() {
		return names != null ? names.length + 1 : 1;
	}
	
	public static ToolType quickLookup(ItemStack stack) {
		return ToolType.getType(stack);
	}
	
	public static HashMap<Pair<ToolType, MetaBlock>, Pair<AStack[], MetaBlock>> conversions = new HashMap();
	
	public static void registerRecipes() {
		conversions.put(new Pair(ToolType.BOLT, new MetaBlock(ModBlocks.watz_end, 0)), new Pair(new AStack[] {new OreDictStack(OreDictManager.DURA.bolt(), 4)}, new MetaBlock(ModBlocks.watz_end, 1)));
		conversions.put(new Pair(ToolType.TORCH, new MetaBlock(ModBlocks.fusion_conductor, 0)), new Pair(new AStack[] {new OreDictStack(OreDictManager.STEEL.plateCast())}, new MetaBlock(ModBlocks.fusion_conductor, 1)));
		conversions.put(new Pair(ToolType.TORCH, new MetaBlock(ModBlocks.icf_component, 1)), new Pair(new AStack[] {new OreDictStack(OreDictManager.ANY_BISMOIDBRONZE.plateCast())}, new MetaBlock(ModBlocks.icf_component, 2)));
		conversions.put(new Pair(ToolType.BOLT, new MetaBlock(ModBlocks.icf_component, 3)), new Pair(new AStack[] {new OreDictStack(OreDictManager.STEEL.plateCast()), new OreDictStack(OreDictManager.DURA.bolt(), 4)}, new MetaBlock(ModBlocks.icf_component, 4)));
	}

	public static HashMap<Object[], Object> bufferedRecipes = new HashMap();
	public static HashMap<Object[], Object> bufferedTools = new HashMap();
	
	public static HashMap<Object[], Object> getRecipes(boolean recipes) {
		
		if(!bufferedRecipes.isEmpty()) return recipes ? bufferedRecipes : bufferedTools;
		
		for(Entry<Pair<ToolType, MetaBlock>, Pair<AStack[], MetaBlock>> entry : conversions.entrySet()) {
			
			List<AStack> list = new ArrayList();
			
			for(AStack stack : entry.getValue().getKey()) {
				list.add(stack);
			}
			list.add(new ComparableStack(entry.getKey().getValue().block, 1, entry.getKey().getValue().meta));

			Object[] inputInstance = list.toArray(new AStack[0]); // the instance has to match for the machine lookup to succeed
			bufferedRecipes.put(inputInstance, new ItemStack(entry.getValue().getValue().block, 1, entry.getValue().getValue().meta));
			bufferedTools.put(inputInstance, entry.getKey().getKey().stacksForDisplay.toArray(new ItemStack[0]));
		}
		
		return recipes ? bufferedRecipes : bufferedTools;
	}
}
