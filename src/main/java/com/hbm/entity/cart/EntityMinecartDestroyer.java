package com.hbm.entity.cart;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerCartDestroyer;
import com.hbm.inventory.gui.GUICartDestroyer;
import com.hbm.items.tool.ItemModMinecart;
import com.hbm.items.tool.ItemModMinecart.EnumCartBase;
import com.hbm.items.tool.ItemModMinecart.EnumMinecart;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.entity.item.RenderNeoCart;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityMinecartDestroyer extends EntityMinecartContainerBase implements IGUIProvider {

	public EntityMinecartDestroyer(World world) {
		super(world);
	}

	public EntityMinecartDestroyer(World world, double x, double y, double z, EnumCartBase type) {
		super(world, x, y, z, type);
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		if(net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.minecart.MinecartInteractEvent(this, player)))
			return true;
		if(!this.worldObj.isRemote) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, worldObj, this.getEntityId(), 0, 0);
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
	public ItemStack getCartItem() {
		return ItemModMinecart.createCartItem(this.getBase(), EnumMinecart.DESTROYER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderSpecialContent(RenderNeoCart renderer) {
		renderer.bindTexture(ResourceManager.cart_destroyer_tex);
		ResourceManager.cart_destroyer.renderAll();
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCartDestroyer(player.inventory, (EntityMinecartDestroyer)player.worldObj.getEntityByID(x));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICartDestroyer(player.inventory, (EntityMinecartDestroyer) player.worldObj.getEntityByID(x));
	}
}
