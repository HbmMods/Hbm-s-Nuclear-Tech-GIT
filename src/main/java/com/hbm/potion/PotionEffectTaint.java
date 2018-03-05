package com.hbm.potion;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class PotionEffectTaint extends Potion {
    
    public static PotionEffectTaint instance = new PotionEffectTaint(Library.getFirstNullIndex(1, Potion.potionTypes), true, 8388736);
    Random rand = new Random();
    
	public PotionEffectTaint(int p_i1573_1_, boolean p_i1573_2_, int p_i1573_3_) {
		super(p_i1573_1_, p_i1573_2_, p_i1573_3_);
		this.setPotionName("potion.hbm_taint");
    	this.setIconIndex(0, 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		ResourceLocation loc = new ResourceLocation("hbm","textures/gui/potions.png");
		Minecraft.getMinecraft().renderEngine.bindTexture(loc);
		return super.getStatusIconIndex();
	}


    public void performEffect(EntityLivingBase entity, int level)
    {
    	entity.attackEntityFrom(ModDamageSource.taint, (level + 1));
    	
    	if(!entity.worldObj.isRemote) {
    		
	    	for(int i = 0; i < 15; i++) {
	    		int a = rand.nextInt(5) + (int)entity.posX - 2;
	    		int b = rand.nextInt(5) + (int)entity.posY - 2;
	    		int c = rand.nextInt(5) + (int)entity.posZ - 2;
	            if(entity.worldObj.getBlock(a, b, c).isReplaceable(entity.worldObj, a, b, c) && BlockTaint.hasPosNeightbour(entity.worldObj, a, b, c))
	            	entity.worldObj.setBlock(a, b, c, ModBlocks.taint, rand.nextInt(3), 2);
	    	}
    	}
    }
    
    public boolean isReady(int par1, int par2)
    {
		int k = 80 >> par2;
        return k > 0 ? par1 % k == 0 : true;
    }

}
