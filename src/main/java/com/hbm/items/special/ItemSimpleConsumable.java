package com.hbm.items.special;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.hbm.items.ModItems;
import com.hbm.util.Tuple.Pair;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSimpleConsumable extends Item {
	
	//if java is giving me the power of generics and delegates then i'm going to use them, damn it!
	private BiConsumer<ItemStack, EntityPlayer> useAction;
	private BiConsumer<ItemStack, EntityPlayer> useActionServer;
	private BiConsumer<ItemStack, Pair<EntityLivingBase, EntityLivingBase>> hitAction;
	private BiConsumer<ItemStack, Pair<EntityLivingBase, EntityLivingBase>> hitActionServer;

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if(this.useAction != null)
			this.useAction.accept(stack, player);
		
		if(!world.isRemote && this.useActionServer != null)
			this.useActionServer.accept(stack, player);
		
		return stack;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase entityPlayer) {
		
		if(this.hitAction != null)
			this.hitAction.accept(stack, new Pair(entity, entityPlayer));
		
		if(!entity.worldObj.isRemote && this.hitActionServer != null)
			this.hitActionServer.accept(stack, new Pair(entity, entityPlayer));
		
		return false;
	}
	
	public static void tryAddItem(EntityLivingBase entity, ItemStack stack) {
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if(!player.inventory.addItemStackToInventory(stack)) {
				player.dropPlayerItemWithRandomChoice(stack, false);
			}
		}
	}
	
	//this formatting style probably already has a name but i will call it "the greg"
	public ItemSimpleConsumable setUseAction(		BiConsumer<ItemStack, EntityPlayer> delegate) {								this.useAction = delegate;			return this; }
	public ItemSimpleConsumable setUseActionServer(	BiConsumer<ItemStack, EntityPlayer> delegate) {								this.useActionServer = delegate;	return this; }
	public ItemSimpleConsumable setHitAction(		BiConsumer<ItemStack, Pair<EntityLivingBase, EntityLivingBase>> delegate) {	this.hitAction = delegate;			return this; }
	public ItemSimpleConsumable setHitActionServer(	BiConsumer<ItemStack, Pair<EntityLivingBase, EntityLivingBase>> delegate) {	this.hitActionServer = delegate;	return this; }
}
