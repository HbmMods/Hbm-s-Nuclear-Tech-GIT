package com.hbm.items.tool;

import com.google.common.collect.Multimap;
import com.hbm.items.ModItems;

import api.hbm.block.IToolable.ToolType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemToolingWeapon extends ItemTooling {
	
	protected float damage = 0;

	public ItemToolingWeapon(ToolType type, int durability, float damage) {
		super(type, durability);
		this.damage = damage;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase player) {
		
		World world = player.worldObj;

		if(this == ModItems.wrench) {

			Vec3 vec = player.getLookVec();

			double dX = vec.xCoord * 0.5;
			double dY = vec.yCoord * 0.5;
			double dZ = vec.zCoord * 0.5;

			entity.motionX += dX;
			entity.motionY += dY;
			entity.motionZ += dZ;
			world.playSoundAtEntity(entity, "random.anvil_land", 3.0F, 0.75F);
		}
		
		return false;
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		
		Multimap multimap = super.getAttributeModifiers(stack);
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", damage, 0));
		
		if(this == ModItems.wrench) {
			multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Wrench modifier", -0.1, 1));
		}
		
		return multimap;
	}
}
