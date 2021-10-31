package com.hbm.items.machine;

import com.hbm.items.special.ItemHazard;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class ItemWasteRod extends ItemHazard {
	
	private IIcon rodOverlaySingle;
	private IIcon rodOverlayDual;
	private IIcon rodOverlayQuad;

	public boolean useableInPHWR;
	public int rodNumber;
	
	public ItemWasteRod(boolean useableInHeavyWater, int rodCount) {
		super();
		this.canRepair = false;
		this.useableInPHWR = useableInHeavyWater;
		this.rodNumber = rodCount;
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
        this.rodOverlayDual = par1IconRegister.registerIcon(RefStrings.MODID + ":rod_dual_zrclad_empty");
        this.rodOverlayQuad = par1IconRegister.registerIcon(RefStrings.MODID + ":rod_quad_waste_overlay");
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

}
