package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.special.ItemHazard;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class ItemFuelRod extends ItemHazard {
	
	private IIcon rodOverlaySingle;
	private IIcon rodOverlayDual;
	private IIcon rodOverlayQuad;

	public int lifeTime;
	public int heat;
	private boolean useableInPHWR;
	public int rodNumber;
	
	public ItemFuelRod(float radiation, boolean blinding, int life, int heat, boolean useableInHeavyWater, int rodCount) {
		super();
		this.lifeTime = life;
		this.heat = heat;
		this.canRepair = false;
		this.useableInPHWR = useableInHeavyWater;
		this.rodNumber = rodCount;
		
		this.addRadiation(radiation);
		if(blinding)
			this.addBlinding();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		if(useableInPHWR)
			return true;
		return false;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        this.rodOverlaySingle = par1IconRegister.registerIcon(RefStrings.MODID + ":rod_zrclad_empty");
        this.rodOverlayDual = par1IconRegister.registerIcon(RefStrings.MODID + ":rod_dual_overlay");
        this.rodOverlayQuad = par1IconRegister.registerIcon(RefStrings.MODID + ":rod_quad_overlay");
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add(EnumChatFormatting.YELLOW + "[Reactor Fuel Rod]");
		
		list.add(EnumChatFormatting.DARK_AQUA + "  Generates " + heat + " heat per tick");
		list.add(EnumChatFormatting.DARK_AQUA + "  Lasts " + Library.getShortNumber(lifeTime) + " ticks");
		if(this.useableInPHWR) {
			list.add(EnumChatFormatting.BLUE + "Requires Heavy Water Moderation");
		} else {
			list.add(EnumChatFormatting.AQUA + "Requires Light Water Moderation");
		}
		
		super.addInformation(itemstack, player, list, bool);
	}
	
	public static void setLifeTime(ItemStack stack, int time) {
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger("life", time);
	}
	
	public static int getLifeTime(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getInteger("life");
	}
    
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }
    
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return (double)getLifeTime(stack) / (double)((ItemFuelRod)stack.getItem()).lifeTime;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int par1, int par2)
    {
    	switch(par2) {
    		case 0: return this.itemIcon;
    		case 1: 
    			switch(rodNumber) {
    				case 1: return this.rodOverlaySingle;
    				case 2: return this.rodOverlayDual;
    				case 4: return this.rodOverlayQuad;
    			}
    	}
    	return this.rodOverlaySingle;
    }
    
    public boolean getUseableInPHWR() {
    	return useableInPHWR;
    }

}
