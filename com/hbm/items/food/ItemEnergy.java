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
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            --stack.stackSize;
        }

        if (!world.isRemote)
        {
        	if(this == ModItems.can_smart)
        	{
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 0));
        	}
        	if(this == ModItems.can_creature)
        	{
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
                player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 30 * 20, 1));
        	}
        	if(this == ModItems.can_redbomb)
        	{
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
                player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 30 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 1));
        	}
        	if(this == ModItems.can_mrsugar)
        	{
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
                player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 30 * 20, 1));
                player.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 2));
        	}
        	if(this == ModItems.can_overcharge)
        	{
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 0));
        	}
        	if(this == ModItems.can_luna)
        	{
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 1));
                player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 30 * 20, 2));
        	}
        	if(this == ModItems.chocolate_milk)
        	{
        		ExplosionLarge.explode(world, player.posX, player.posY, player.posZ, 50, true, false, false);
        	}
        	if(this == ModItems.bottle_nuka)
        	{
        		player.heal(4F);
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 30 * 20, 1));
        	}
        	if(this == ModItems.bottle_cherry)
        	{
        		player.heal(6F);
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
                player.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 2));
        	}
        	if(this == ModItems.bottle_quantum)
        	{
        		player.heal(10F);
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 1));
        	}
        	if(this == ModItems.bottle2_korl)
        	{
        		player.heal(6);
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 30 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 2));
        	}
        	if(this == ModItems.bottle2_fritz)
        	{
        		player.heal(6);
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 2));
        	}
        	if(this == ModItems.bottle2_korl_special)
        	{
        		player.heal(16);
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 120 * 20, 1));
                player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 120 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 120 * 20, 2));
        	}
        	if(this == ModItems.bottle2_fritz_special)
        	{
        		player.heal(16);
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 120 * 20, 1));
                player.addPotionEffect(new PotionEffect(Potion.resistance.id, 120 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.jump.id, 120 * 20, 2));
        	}
        	if(this == ModItems.bottle_sparkle)
        	{
        		player.heal(10F);
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 120 * 20, 1));
                player.addPotionEffect(new PotionEffect(Potion.resistance.id, 120 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 120 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 120 * 20, 1));
        	}
        	if(this == ModItems.bottle2_sunset)
        	{
        		player.heal(6);
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60 * 20, 1));
                player.addPotionEffect(new PotionEffect(Potion.resistance.id, 60 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 60 * 20, 2));
                player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 60 * 20, 2));
        	}
        }

        if (!player.capabilities.isCreativeMode && this != ModItems.chocolate_milk)
        {
        	if(this == ModItems.can_creature || this == ModItems.can_mrsugar || 
        			this == ModItems.can_overcharge || this == ModItems.can_redbomb || 
        			this == ModItems.can_smart || this == ModItems.can_luna) {
            	player.inventory.addItemStackToInventory(new ItemStack(ModItems.ring_pull));
        		if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.can_empty);
            	}

            	player.inventory.addItemStackToInventory(new ItemStack(ModItems.can_empty));
        	}
        	
        	if(this == ModItems.bottle_cherry || this == ModItems.bottle_nuka) {
            	player.inventory.addItemStackToInventory(new ItemStack(ModItems.cap_nuka));
        		if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.bottle_empty);
            	}

            	player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle_empty));
        	}
        	
        	if(this == ModItems.bottle_quantum) {
            	player.inventory.addItemStackToInventory(new ItemStack(ModItems.cap_quantum));
        		if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.bottle_empty);
            	}

            	player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle_empty));
        	}
        	
        	if(this == ModItems.bottle2_korl || this == ModItems.bottle2_korl_special) {
            	player.inventory.addItemStackToInventory(new ItemStack(ModItems.cap_korl));
        		if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.bottle2_empty);
            	}

            	player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle2_empty));
        	}
        	
        	if(this == ModItems.bottle2_fritz || this == ModItems.bottle2_fritz_special) {
            	player.inventory.addItemStackToInventory(new ItemStack(ModItems.cap_fritz));
        		if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.bottle2_empty);
            	}

            	player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle2_empty));
        	}
        	
        	if(this == ModItems.bottle_sparkle) {
            	player.inventory.addItemStackToInventory(new ItemStack(ModItems.cap_sparkle));
        		if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.bottle_empty);
            	}

            	player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle_empty));
        	}
        	
        	if(this == ModItems.bottle2_sunset) {
        		
        		if(world.rand.nextInt(10) == 0)
        			player.inventory.addItemStackToInventory(new ItemStack(ModItems.cap_star));
        		else
        			player.inventory.addItemStackToInventory(new ItemStack(ModItems.cap_sunset));
        		
        		if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.bottle2_empty);
            	}

            	player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle2_empty));
        	}
        }

        return stack;
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
    	if(!(this == ModItems.can_creature || this == ModItems.can_mrsugar || 
    			this == ModItems.can_overcharge || this == ModItems.can_redbomb || 
    			this == ModItems.can_smart || this == ModItems.chocolate_milk || 
    			this == ModItems.can_luna))
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
    	if(this == ModItems.can_luna)
    	{
            list.add("Contains actual selenium and star metal. Tastes like night.");
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
    	if(this == ModItems.bottle_sparkle)
    	{
    		if(MainRegistry.polaroidID == 11)
    			list.add("Contains trace amounts of taint.");
    		else
    			list.add("The most delicious beverage in the wasteland!");
    	}
    	if(this == ModItems.bottle2_sunset)
    	{
    		if(MainRegistry.polaroidID == 11) {
    			list.add("\"Authentic Sunset Juice\"");
    			list.add("");
    			list.add("This smells like fish.");
    			list.add("*sip*");
    			list.add("Yup, that's pretty disugsting.");
    			list.add("...");
    			list.add("...");
    			list.add("*sip*");
    		} else {
    			list.add("The eternal #2. Screw you, Bradberton!");
    		}
    	}
    }
}
