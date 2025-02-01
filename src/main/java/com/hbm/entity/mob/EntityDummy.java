package com.hbm.entity.mob;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.world.World;

public class EntityDummy extends EntityLiving implements IAnimals {

	public EntityDummy(World world) {
		super(world);
	}
	
	@Override
	public boolean interact(EntityPlayer player) {
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemArmor) {
			ItemArmor armor = (ItemArmor) player.getHeldItem().getItem();
			this.setCurrentItemOrArmor(4 - armor.armorType, player.getHeldItem().copy());
		}
		
		return super.interact(player);
	}

	@Override @SideOnly(Side.CLIENT) public boolean getAlwaysRenderNameTagForRender() { return true; }
	@Override public String getCommandSenderName() {
		return (int) (this.getHealth() * 10) / 10F + " / " + (int) (this.getMaxHealth() * 10) / 10F; }
		//return (int) this.rotationYaw + " " + (int) this.renderYawOffset + " " + (int) this.rotationYawHead + " " + (int) this.newRotationYaw; }
	
	@Override protected void dropEquipment(boolean b, int i) { }
}
