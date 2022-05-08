package com.hbm.entity.cart;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemModMinecart.EnumMinecart;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMinecartDestroyer extends EntityMinecartContainerBase {

	public EntityMinecartDestroyer(World p_i1712_1_) {
		super(p_i1712_1_);
	}

	public EntityMinecartDestroyer(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		if(net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.minecart.MinecartInteractEvent(this, player)))
			return true;
		if(!this.worldObj.isRemote) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModItems.guiID_cart_destroyer, worldObj, this.getEntityId(), 0, 0);
		}

		return true;
	}

	@Override
	public Block func_145817_o() {
		return ModBlocks.machine_shredder;
	}

	@Override
	public int getSizeInventory() {
		return 18;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity) {
		return entity.boundingBox;
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return this.boundingBox;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!worldObj.isRemote && this.ticksExisted % 5 == 0) {
			
			List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(
					posX - 2.5,
					posY - 1.5,
					posZ - 2.5,
					posX + 2.5,
					posY + 2,
					posZ + 2.5));
			
			boolean sound = false;
			
			outer: for(EntityItem item : items) {
				ItemStack stack = item.getEntityItem();
				
				//Match meta
				for(int i = 0; i < 9; i++) {
					ItemStack match = this.slots[i];
					
					if(match != null && match.getItem() == stack.getItem() && match.getItemDamage() == stack.getItemDamage()) {
						item.setDead();
						sound = true;
						continue outer;
					}
				}
				
				//Match wildcard
				for(int i = 9; i < 18; i++) {
					ItemStack match = this.slots[i];
					
					if(match != null && match.getItem() == stack.getItem()) {
						item.setDead();
						sound = true;
						continue outer;
					}
				}
			}
			
			if(sound)
				worldObj.playSoundEffect(posX, posY, posZ, "mob.zombie.woodbreak", 0.5F, 0.5F + worldObj.rand.nextFloat() * 0.2F);
		}
		
		if(worldObj.isRemote && this.ticksExisted % 5 == 0) {
			worldObj.spawnParticle("smoke", posX, posY + 0.75, posZ, 0.0, 0.01, 0.0);
		}
	}

	@Override
	public void killMinecart(DamageSource p_94095_1_) {
		this.setDead();
		ItemStack itemstack = DictFrame.fromOne(ModItems.cart, EnumMinecart.DESTROYER);

		if(this.func_95999_t() != null) {
			itemstack.setStackDisplayName(this.func_95999_t());
		}

		this.entityDropItem(itemstack, 0.0F);
	}

	@Override
	public ItemStack getCartItem() {
		return DictFrame.fromOne(ModItems.cart, EnumMinecart.DESTROYER);
	}
}
