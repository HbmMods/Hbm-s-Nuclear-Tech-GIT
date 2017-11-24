package com.hbm.items.food;

import java.util.List;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemEnergy extends Item {

    @Override
	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
        if (!p_77654_3_.capabilities.isCreativeMode)
        {
            --p_77654_1_.stackSize;
        }

        if (!p_77654_2_.isRemote)
        {
        	if(this == ModItems.can_smart)
        	{
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 0));
        	}
        	if(this == ModItems.can_creature)
        	{
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.regeneration.id, 30 * 20, 1));
        	}
        	if(this == ModItems.can_redbomb)
        	{
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 30 * 20, 2));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 1));
        	}
        	if(this == ModItems.can_mrsugar)
        	{
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 30 * 20, 1));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 2));
        	}
        	if(this == ModItems.can_overcharge)
        	{
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 0));
        	}
        	if(this == ModItems.chocolate_milk)
        	{
        		ExplosionLarge.explode(p_77654_2_, p_77654_3_.posX, p_77654_3_.posY, p_77654_3_.posZ, 50, true, false, false);
        	}
        	if(this == ModItems.bottle_nuka)
        	{
        		p_77654_3_.heal(4F);
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 30 * 20, 1));
        	}
        	if(this == ModItems.bottle_cherry)
        	{
        		p_77654_3_.heal(6F);
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 2));
        	}
        	if(this == ModItems.bottle_quantum)
        	{
        		p_77654_3_.heal(10F);
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 1));
        	}
        	if(this == ModItems.bottle2_korl)
        	{
        		p_77654_3_.heal(6);
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 30 * 20, 2));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 2));
        	}
        	if(this == ModItems.bottle2_fritz)
        	{
        		p_77654_3_.heal(6);
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 2));
        	}
        	if(this == ModItems.bottle2_korl_special)
        	{
        		p_77654_3_.heal(16);
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 120 * 20, 1));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 120 * 20, 2));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 120 * 20, 2));
        	}
        	if(this == ModItems.bottle2_fritz_special)
        	{
        		p_77654_3_.heal(16);
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 120 * 20, 1));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 120 * 20, 2));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.jump.id, 120 * 20, 2));
        	}
        }

        if (!p_77654_3_.capabilities.isCreativeMode && this != ModItems.chocolate_milk)
        {
        	if(this == ModItems.can_creature || this == ModItems.can_mrsugar || this == ModItems.can_overcharge || this == ModItems.can_redbomb || this == ModItems.can_smart) {
            	p_77654_3_.inventory.addItemStackToInventory(new ItemStack(ModItems.ring_pull));
        		if (p_77654_1_.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.can_empty);
            	}

            	p_77654_3_.inventory.addItemStackToInventory(new ItemStack(ModItems.can_empty));
        	}
        	
        	if(this == ModItems.bottle_cherry || this == ModItems.bottle_nuka) {
            	p_77654_3_.inventory.addItemStackToInventory(new ItemStack(ModItems.cap_nuka));
        		if (p_77654_1_.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.bottle_empty);
            	}

            	p_77654_3_.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle_empty));
        	}
        	
        	if(this == ModItems.bottle_quantum) {
            	p_77654_3_.inventory.addItemStackToInventory(new ItemStack(ModItems.cap_quantum));
        		if (p_77654_1_.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.bottle_empty);
            	}

            	p_77654_3_.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle_empty));
        	}
        	
        	if(this == ModItems.bottle2_korl || this == ModItems.bottle2_korl_special) {
            	p_77654_3_.inventory.addItemStackToInventory(new ItemStack(ModItems.cap_korl));
        		if (p_77654_1_.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.bottle2_empty);
            	}

            	p_77654_3_.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle2_empty));
        	}
        	
        	if(this == ModItems.bottle2_fritz || this == ModItems.bottle2_fritz_special) {
            	p_77654_3_.inventory.addItemStackToInventory(new ItemStack(ModItems.cap_fritz));
        		if (p_77654_1_.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.bottle2_empty);
            	}

            	p_77654_3_.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle2_empty));
        	}
        }

        return p_77654_1_;
    }
    
    @Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 32;
    }

    @Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.drink;
    }

    @Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
    	if(!(this == ModItems.can_creature || this == ModItems.can_mrsugar || this == ModItems.can_overcharge || this == ModItems.can_redbomb || this == ModItems.can_smart || this == ModItems.chocolate_milk))
    		if(!p_77659_3_.inventory.hasItem(ModItems.bottle_opener))
    			return p_77659_1_;
    	
    	p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
    	
    	return p_77659_1_;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_)
    {
    	if(this == ModItems.can_smart)
    	{
    		list.add("Cheap and full of bubbles");
    	}
    	if(this == ModItems.can_creature)
    	{
            list.add("Basically gasoline in a tin can");
    	}
    	if(this == ModItems.can_redbomb)
    	{
            list.add("Liquefied explosives");
    	}
    	if(this == ModItems.can_mrsugar)
    	{
            list.add("An intellectual drink, for the chosen ones!");
    	}
    	if(this == ModItems.can_overcharge)
    	{
            list.add("Possible side effects include heart attacks, seizures or zombification");
    	}
    	if(this == ModItems.chocolate_milk)
    	{
            list.add("Regular chocolate milk. Safe to drink.");
            list.add("Totally not made from nitroglycerine.");
    	}
    	if(this == ModItems.bottle_nuka)
    	{
            list.add("Contains about 210 kcal and 1500 mSv.");
    	}
    	if(this == ModItems.bottle_cherry)
    	{
            list.add("Now with severe radiation poisoning in every seventh bottle!");
    	}
    	if(this == ModItems.bottle_quantum)
    	{
            list.add("Comes with a colorful mix of over 70 isotopes!");
    	}
    	if(this == ModItems.bottle2_korl)
    	{
            list.add("Contains actual orange juice!");
    	}
    	if(this == ModItems.bottle2_fritz)
    	{
            list.add("moremore caffeine");
    	}
    	if(this == ModItems.bottle2_korl_special)
    	{
    		if(MainRegistry.polaroidID == 11)
    			list.add("shgehgev u rguer");
    		else
                list.add("Contains actual orange juice!");
    	}
    	if(this == ModItems.bottle2_fritz_special)
    	{
    		if(MainRegistry.polaroidID == 11)
    			list.add("ygrogr fgrof bf");
    		else
    			list.add("moremore caffeine");
    	}
    }
}
