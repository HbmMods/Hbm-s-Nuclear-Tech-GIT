package com.hbm.items.special;

import java.util.List;

import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.lib.ModDamageSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemUnstable extends Item {

	IIcon iconElements;
	IIcon iconArsenic;
	IIcon iconVault;
	
	int radius;
	int timer;
	
	public ItemUnstable(int radius, int timer) {
		this.radius = radius;
		this.timer = timer;
        this.setHasSubtypes(true);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
    	
    	if(stack.getItemDamage() != 0)
    		return;
		
		list.add("Decay: " + (getTimer(stack) * 100 / timer) + "%");
	}
	
    public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
    	
    	if(stack.getItemDamage() != 0)
    		return;
    	
    	this.setTimer(stack, this.getTimer(stack) + 1);
    	
    	if(this.getTimer(stack) == timer && !world.isRemote) {
    		world.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(world, radius, entity.posX, entity.posY, entity.posZ));
    		world.playSoundAtEntity(entity, "hbm:entity.oldExplosion", 1.0F, 1.0F);
    		entity.attackEntityFrom(ModDamageSource.nuclearBlast, 10000);
    		
    		stack.stackSize = 0;
    	}
    }
    
    private void setTimer(ItemStack stack, int time) {
    	if(!stack.hasTagCompound())
    		stack.stackTagCompound = new NBTTagCompound();
    	
    	stack.stackTagCompound.setInteger("timer", time);
    }
    
    private int getTimer(ItemStack stack) {
    	if(!stack.hasTagCompound())
    		stack.stackTagCompound = new NBTTagCompound();
    	
    	return stack.stackTagCompound.getInteger("timer");
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);

        this.iconElements = p_94581_1_.registerIcon("hbm:hs-elements");
        this.iconArsenic = p_94581_1_.registerIcon("hbm:hs-arsenic");
        this.iconVault = p_94581_1_.registerIcon("hbm:hs-vault");
    }

    @Override
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int layer)
    {
    	switch(damage) {
	    	case 1: return this.iconElements;
	    	case 2: return this.iconArsenic;
	    	case 3: return this.iconVault;
	    	default: return this.itemIcon;
    	}
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
    	switch(stack.getItemDamage()) {
	    	case 1: return "ELEMENTS";
	    	case 2: return "ARSENIC";
	    	case 3: return "VAULT";
	    	default: return ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
    	}
    }

}
