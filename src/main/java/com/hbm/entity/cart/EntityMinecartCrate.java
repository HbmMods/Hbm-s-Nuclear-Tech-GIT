package com.hbm.entity.cart;

import java.io.IOException;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerCrateSteel;
import com.hbm.inventory.gui.GUICrateSteel;
import com.hbm.items.tool.ItemModMinecart;
import com.hbm.items.tool.ItemModMinecart.EnumCartBase;
import com.hbm.items.tool.ItemModMinecart.EnumMinecart;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMinecartCrate extends EntityMinecartContainerBase implements IGUIProvider {

	public EntityMinecartCrate(World world) {
		super(world);
	}

	public EntityMinecartCrate(World world, double x, double y, double z, EnumCartBase type, ItemStack stack) {
		super(world, x, y, z, type);
		if(stack.hasTagCompound()) {
			for(int i = 0; i < getSizeInventory(); i++) {
				setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("slot" + i)));
			}
		}
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
		return ModBlocks.crate_steel;
	}

	@Override
	public int getSizeInventory() {
		return 9 * 6;
	}

	@Override
	public void killMinecart(DamageSource p_94095_1_) {
		this.setDead();
		ItemStack itemstack = ItemModMinecart.createCartItem(EnumCartBase.VANILLA, EnumMinecart.CRATE);
		
		NBTTagCompound nbt = new NBTTagCompound();
			
			for(int i = 0; i < getSizeInventory(); i++) {
				
			ItemStack stack = getStackInSlot(i);
			if(stack == null)
				continue;
			
			NBTTagCompound slot = new NBTTagCompound();
			stack.writeToNBT(slot);
			nbt.setTag("slot" + i, slot);
		}
		
		if(!nbt.hasNoTags()) {
			itemstack.stackTagCompound = nbt;
		}

		if(this.func_95999_t() != null) {
			itemstack.setStackDisplayName(this.func_95999_t());
		}
		
		try {
			byte[] abyte = CompressedStreamTools.compress(nbt);
			
			if(abyte.length > 6000) {
				worldObj.newExplosion(this, posX, posY, posZ, 2F, true, true);
				this.entityDropItem(ItemModMinecart.createCartItem(EnumCartBase.VANILLA, EnumMinecart.CRATE), 0.0F);
			}
			
		} catch(IOException e) { }

		this.entityDropItem(itemstack, 0.0F);
	}

	@Override
	public ItemStack getCartItem() {
		return ItemModMinecart.createCartItem(EnumCartBase.VANILLA, EnumMinecart.CRATE);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCrateSteel(player.inventory, (EntityMinecartCrate)player.worldObj.getEntityByID(x));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICrateSteel(player.inventory, (EntityMinecartCrate) player.worldObj.getEntityByID(x));
	}
}
