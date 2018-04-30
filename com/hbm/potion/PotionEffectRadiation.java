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

public class PotionEffectRadiation extends Potion {
    
    public static PotionEffectRadiation instance = new PotionEffectRadiation(Library.getFirstNullIndex(1, Potion.potionTypes), true, 8700200);
    Random rand = new Random();
    
	public PotionEffectRadiation(int p_i1573_1_, boolean p_i1573_2_, int p_i1573_3_) {
		super(p_i1573_1_, p_i1573_2_, p_i1573_3_);
		this.setPotionName("potion.hbm_radiation");
    	this.setIconIndex(1, 0);
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
    	if(entity.getHealth() > entity.getMaxHealth() - (level + 1))
    		entity.attackEntityFrom(ModDamageSource.radiation, 1);
    	
    	/*if(level > 15) {
    		if(rand.nextInt(10) == 0)
        		entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 0));
    		if(rand.nextInt(20) == 0)
        		entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5 * 20, 3));
    		if(rand.nextInt(20) == 0)
        		entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 5 * 20, 3));
    		if(rand.nextInt(20) == 0)
        		entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 5 * 20, 2));
    		if(rand.nextInt(30) == 0)
        		entity.addPotionEffect(new PotionEffect(Potion.wither.id, 3 * 20, 4));
    	} else if(level > 10) {
    		if(rand.nextInt(10) == 0)
        		entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 0));
    		if(rand.nextInt(20) == 0)
        		entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5 * 20, 3));
    		if(rand.nextInt(20) == 0)
        		entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 5 * 20, 3));
    		if(rand.nextInt(20) == 0)
        		entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 5 * 20, 2));
    	} else if(level > 4) {
    		if(rand.nextInt(10) == 0)
        		entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 0));
    		if(rand.nextInt(20) == 0)
        		entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5 * 20, 1));
    		if(rand.nextInt(20) == 0)
        		entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 5 * 20, 1));
    	}*/
    }
    
    public boolean isReady(int par1, int par2)
    {
		int k = 40 >> par2;
        return k > 0 ? par1 % k == 0 : true;
    }

}
