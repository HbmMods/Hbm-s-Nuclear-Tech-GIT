package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ItemCustomLore;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemStarterKit extends ItemCustomLore
{
	public enum KitType
	{
		ARMOR("kitArmor"),
		HAZMAT("kitHaz"),
		POOL("kitPool"),
		PACK("kitPack"),
		MISC("");
		final String key;
		private KitType(String key)
		{
			this.key = key;
		}
		public String getLoc()
		{
			return key.isEmpty() ? "" : "desc.item.".concat(key);
		}
	}
	private ItemStack[] kit = new ItemStack[] {};
	private Item[] armor = new Item[] {};
	private KitType[] type = new KitType[] {};
	/** Special case kits, also serves as the primary for all instances, does basically nothing by itself **/
    public ItemStarterKit()
    {
        setMaxStackSize(1);
    }
    /**
     * Kit with a designated pool
     * @param kitIn - Desired kit from collection
     */
    public ItemStarterKit(ItemStack[] kitIn)
    {
    	this();
    	kit = kitIn;
    }
    /**
     * Armor kit
     * @param armorIn - Desired armor set
     */
    public ItemStarterKit(Item[] armorIn)
    {
    	this();
    	armor = armorIn;
    }
    /**
     * Kit with both armor and item pool
     * @param kitIn - Desired item pool
     * @param armorIn - Desired armor set
     */
    public ItemStarterKit(ItemStack[] kitIn, Item[] armorIn)
    {
    	this();
    	kit = kitIn;
    	armor = armorIn;
    }
    
    public ItemStarterKit setType(KitType... typeIn)
    {
    	type = typeIn;
    	return this;
    }
    @Deprecated
    private void giveHaz(World world, EntityPlayer p, int tier) {
    	
    	for(int i = 0; i < 4; i++) {
    		
    		if(p.inventory.armorInventory[i] != null && !world.isRemote) {
    			world.spawnEntityInWorld(new EntityItem(world, p.posX, p.posY + p.eyeHeight, p.posZ, p.inventory.armorInventory[i]));
    		}
    	}

    	switch(tier) {
    	case 0:
	    	p.inventory.armorInventory[3] = new ItemStack(ModItems.hazmat_helmet);
	    	p.inventory.armorInventory[2] = new ItemStack(ModItems.hazmat_plate);
	    	p.inventory.armorInventory[1] = new ItemStack(ModItems.hazmat_legs);
	    	p.inventory.armorInventory[0] = new ItemStack(ModItems.hazmat_boots);
	    	break;
    	case 1:
	    	p.inventory.armorInventory[3] = new ItemStack(ModItems.hazmat_helmet_red);
	    	p.inventory.armorInventory[2] = new ItemStack(ModItems.hazmat_plate_red);
	    	p.inventory.armorInventory[1] = new ItemStack(ModItems.hazmat_legs_red);
	    	p.inventory.armorInventory[0] = new ItemStack(ModItems.hazmat_boots_red);
	    	break;
    	case 2:
	    	p.inventory.armorInventory[3] = new ItemStack(ModItems.hazmat_helmet_grey);
	    	p.inventory.armorInventory[2] = new ItemStack(ModItems.hazmat_plate_grey);
	    	p.inventory.armorInventory[1] = new ItemStack(ModItems.hazmat_legs_grey);
	    	p.inventory.armorInventory[0] = new ItemStack(ModItems.hazmat_boots_grey);
	    	break;
    	}
    }
    private void giveArmor(World worldIn, EntityPlayer playerIn)
    {
    	if (armor != null || armor.length > 0)
    	{
	    	for (int i = 0; i < 4; i++)
	    		if (playerIn.inventory.armorInventory[i] != null && !worldIn.isRemote)
	    			worldIn.spawnEntityInWorld(new EntityItem(worldIn, playerIn.posX, playerIn.posY + playerIn.eyeHeight, playerIn.posZ, playerIn.inventory.armorInventory[i]));
	    	int i = 3;
	    	for (Item armor : armor)
	    	{
	    		playerIn.inventory.armorInventory[i] = new ItemStack(armor);
	    		i--;
	    	}
    	}
    }
    
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (kit != null || kit.length > 0)
			for (ItemStack stackPool : kit)
				player.inventory.addItemStackToInventory(stackPool.copy());
		
		if (armor != null || armor.length > 0)
			giveArmor(world, player);
		
		if (this == ModItems.stealth_boy)
			player.addPotionEffect(new PotionEffect(Potion.invisibility.id, 30 * 20, 1, true));
		

		if(this == ModItems.letter && world.isRemote)
		{
			if (player.getUniqueID().toString().equals(Library.a20))
				player.addChatMessage(new ChatComponentText("Error: null reference @ com.hbm.items.ItemStarterKit.class, please report this to the modder!"));
			else
				player.addChatMessage(new ChatComponentText("You rip the letter in half; nothing happens."));
			
		}

		world.playSoundAtEntity(player, "hbm:item.unpack", 1.0F, 1.0F);
		stack.stackSize--;
		return stack;
		
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b)
    {
    	super.addInformation(stack, player, list, b);
    	if (type != null || type.length > 0)
    	{
			for (KitType t : type)
			{
				if (t.getLoc().isEmpty())
					continue;
				else
					list.add(I18nUtil.resolveKey(t.getLoc()));
			}
    	}
    }

}
