package com.hbm.items;

import java.util.List;

import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class WeaponSpecial extends ItemSword {

	public WeaponSpecial(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}
    
    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
    {
		return EnumRarity.rare;
    }
    
    public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase entityPlayer)
    {
    	World world = entity.worldObj;
        if (!world.isRemote)
        {
        	entity.setHealth(0.0F);
        }
        world.playSoundAtEntity(entity, "random.anvil_land", 3.0F, 0.1F);
		
		return false;
    }
    
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)-0.5, 1));
        return multimap;
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Even though it says \"+1000000000");
		list.add("damage\", it's actually \"onehit anything\"");
	}

}
