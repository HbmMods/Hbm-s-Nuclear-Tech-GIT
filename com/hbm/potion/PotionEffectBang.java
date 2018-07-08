package com.hbm.potion;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class PotionEffectBang extends Potion {
    
    public static PotionEffectBang instance = new PotionEffectBang(Library.getFirstNullIndex(1, Potion.potionTypes), true, 1118481);
    Random rand = new Random();
    
	public PotionEffectBang(int p_i1573_1_, boolean p_i1573_2_, int p_i1573_3_) {
		super(p_i1573_1_, p_i1573_2_, p_i1573_3_);
		this.setPotionName("potion.hbm_bang");
    	this.setIconIndex(3, 0);
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
    	entity.attackEntityFrom(ModDamageSource.bang, 1000);
    	entity.setHealth(0.0F);
    	
    	if(!(entity instanceof EntityPlayer))
    		entity.setDead();
    	
    	entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "hbm:weapon.laserBang", 100.0F, 1.0F);
    	//entity.worldObj.spawnParticle("hugeexplosion", entity.posX, entity.posY, entity.posZ, 0, 0, 0);
    	ExplosionLarge.spawnParticles(entity.worldObj, entity.posX, entity.posY, entity.posZ, 10);
    }
    
    public boolean isReady(int par1, int par2)
    {
		return par1 <= 10;
    }

}
