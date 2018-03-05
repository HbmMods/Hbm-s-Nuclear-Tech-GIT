package com.hbm.items.gear;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.items.ModItems;
import com.hbm.render.model.ModelGoggles;
import com.hbm.render.model.ModelJetPack;
import com.hbm.render.model.ModelOxygenMask;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class JetpackRegular extends ItemArmor {

	private ModelJetPack model;

	public JetpackRegular(ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
	}


	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		if (armorSlot == 1) {
			if (model == null) {
				this.model = new ModelJetPack();
			}
			return this.model;
		}
		
		return null;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "hbm:textures/models/JetPackRed.png";
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
    	
    	if(player.isSneaking()) {
    		
    		Vec3 vec = Vec3.createVectorHelper(player.getLookVec().xCoord, 0, player.getLookVec().zCoord);
    		vec.normalize();
    		player.motionY += 0.15;
    		
    		EntityGasFlameFX fx = new EntityGasFlameFX(world);
    		fx.posX = player.posX - vec.xCoord;
    		fx.posY = player.posY - 1;
    		fx.posZ = player.posZ - vec.zCoord;
    		fx.motionY = -0.15;
    		world.spawnEntityInWorld(fx);
    		
    		player.fallDistance = 0;
    	}
    }

}
