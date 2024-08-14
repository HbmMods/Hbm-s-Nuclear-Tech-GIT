package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.container.ContainerFileCabinet;
import com.hbm.inventory.gui.GUIFileCabinet;
import com.hbm.packet.BufPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IBufPacketReceiver;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityFileCabinet extends TileEntityCrateBase implements IGUIProvider, IBufPacketReceiver {
	
	private int timer = 0;
	private int playersUsing = 0;
	//meh, it's literally just two extra variables
	public float lowerExtent = 0; //i don't know a term for how 'open' something is
	public float prevLowerExtent = 0;
	public float upperExtent = 0;
	public float prevUpperExtent = 0;
	
	public TileEntityFileCabinet() {
		super(8);
	}
	
	@Override
	public String getInventoryName() {
		return "container.fileCabinet";
	}
	
	@Override
	public void openInventory() {
		if(!worldObj.isRemote) this.playersUsing++;
	}

	@Override
	public void closeInventory() {
		if(!worldObj.isRemote) this.playersUsing--;
	}

	@Override public void serialize(ByteBuf buf) {
		buf.writeInt(timer);
		buf.writeInt(playersUsing);
	}
	
	@Override public void deserialize(ByteBuf buf) {
		timer = buf.readInt();
		playersUsing = buf.readInt();
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.playersUsing > 0) {
				if(timer < 10) {
					timer++;
				}
			} else
				timer = 0;
			
			PacketDispatcher.wrapper.sendToAllAround(new BufPacket(xCoord, yCoord, zCoord, this), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 25));
		} else {
			this.prevLowerExtent = lowerExtent;
			this.prevUpperExtent = upperExtent;
		}
		
		float openSpeed = playersUsing > 0 ? 1F / 16F : 1F / 25F;
		float maxExtent = 0.8F;
		
		if(this.playersUsing > 0) {
			if(lowerExtent == 0F && upperExtent == 0F)
				this.worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:block.crateOpen", 0.8F, 1.0F);
			else {
				if(upperExtent + openSpeed >= maxExtent && lowerExtent < maxExtent)
					this.worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:block.crateOpen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.7F);
				
				if(lowerExtent + openSpeed >= maxExtent && lowerExtent < maxExtent)
					this.worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:block.crateOpen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.7F);
			}
			
			this.lowerExtent += openSpeed;
			
			if(timer >= 10)
				this.upperExtent += openSpeed;
			
		} else if(lowerExtent > 0) {
			if(upperExtent - openSpeed < maxExtent / 2 && upperExtent >= maxExtent / 2 && upperExtent != lowerExtent)
				this.worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:block.crateClose", 0.8F, 1.0F);
			
			if(lowerExtent - openSpeed < maxExtent / 2 && lowerExtent >= maxExtent / 2)
				this.worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:block.crateClose", 0.8F, 1.0F);
			
			this.upperExtent -= openSpeed;
			this.lowerExtent -= openSpeed;
		}
		
		this.lowerExtent = MathHelper.clamp_float(lowerExtent, 0F, maxExtent);
		this.upperExtent = MathHelper.clamp_float(upperExtent, 0F, maxExtent);
	}
	
	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFileCabinet(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFileCabinet(player.inventory, this);
	}

	//No automation, it's a filing cabinet.
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 1,
					yCoord + 1,
					zCoord + 1
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
