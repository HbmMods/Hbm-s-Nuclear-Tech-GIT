package com.hbm.items.machine;

import java.util.List;

import com.hbm.interfaces.IItemHazard;
import com.hbm.main.MainRegistry;
import com.hbm.modules.ItemHazardModule;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemRBMKPellet extends Item implements IItemHazard {
	
	public String fullName = "";
	ItemHazardModule module;

	public ItemRBMKPellet(String fullName) {
		this.fullName = fullName;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(MainRegistry.controlTab);
		this.module = new ItemHazardModule();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for(int i = 0; i < 10; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon[] enrichmentOverlays = new IIcon[5];
	private IIcon xenonOverlay;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_94581_1_) {
		super.registerIcons(p_94581_1_);
		
		for(int i = 0; i < enrichmentOverlays.length; i++) {
			enrichmentOverlays[i] = p_94581_1_.registerIcon("hbm:rbmk_pellet_overlay_e" + i);
		}
		
		xenonOverlay = p_94581_1_.registerIcon("hbm:rbmk_pellet_overlay_xenon");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int meta) {
		return hasXenon(meta) ? 3 : 2;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(stack, player, list, bool);

		list.add(EnumChatFormatting.ITALIC + this.fullName);
		list.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC + "Pellet for recycling");
		
		int meta = rectify(stack.getItemDamage());
		
		switch(meta % 5) {
		case 0: list.add(EnumChatFormatting.GOLD + "Brand New"); break;
		case 1: list.add(EnumChatFormatting.YELLOW + "Barely Depleted"); break;
		case 2: list.add(EnumChatFormatting.GREEN + "Moderately Depleted"); break;
		case 3: list.add(EnumChatFormatting.DARK_GREEN + "Highly Depleted"); break;
		case 4: list.add(EnumChatFormatting.DARK_GRAY + "Fully Depleted"); break;
		}
		
		if(hasXenon(meta))
			list.add(EnumChatFormatting.DARK_PURPLE + "High Xenon Poison");
		
		updateModule(stack);
		this.module.addInformation(stack, player, list, bool);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		
		if(pass == 0)
			return this.itemIcon;
		
		if(pass == 2)
			return this.xenonOverlay;
		
		return this.enrichmentOverlays[rectify(meta) % 5];
	}
	
	private boolean hasXenon(int meta) {
		return rectify(meta) >= 5;
	}
	
	private int rectify(int meta) {
		return Math.abs(meta) % 10;
	}

	@Override
	public ItemHazardModule getModule() {
		return this.module;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
		
		if(entity instanceof EntityLivingBase) {
			updateModule(stack);
			this.module.applyEffects((EntityLivingBase) entity, stack.stackSize, i, b);
		}
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem item) {
		
		super.onEntityItemUpdate(item);
		updateModule(item.getEntityItem());
		return this.module.onEntityItemUpdate(item);
	}
	
	private void updateModule(ItemStack stack) {
		
		int index = stack.getItemDamage() % 5;
		float mod = (index * index) / 5F;
		
		if(stack.getItemDamage() >= 5) {
			mod *= 10F;
			mod += 1F;
		}
		
		this.module.setMod(1F + mod);
	}
}
