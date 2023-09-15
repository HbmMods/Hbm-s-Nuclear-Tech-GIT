package com.hbm.tileentity.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GuiScreenRadioTelex;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;
import com.hbm.util.ItemStackUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityRadioTelex extends TileEntity implements INBTPacketReceiver, IControlReceiver, IGUIProvider {

	public static final int lineWidth = 33;
	public String txChannel = "";
	public String rxChannel = "";
	public String[] txBuffer = new String[] {"", "", "", "", ""};
	public String[] rxBuffer = new String[] {"", "", "", "", ""};
	public int sendingLine = 0;
	public int sendingIndex = 0;
	public boolean isSending = false;
	public int sendingWait = 0;
	public int writingLine = 0;
	public boolean printAfterRx = false;
	public boolean deleteOnReceive = true;
	public char sendingChar = ' ';

	public static final char eol = '\n';
	public static final char eot = '\u0004';
	public static final char bell = '\u0007';
	public static final char print = '\u000c';
	public static final char pause = '\u0016';
	public static final char clear = '\u007f';
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.sendingChar = ' ';
			
			if(this.isSending && this.txChannel.isEmpty()) this.isSending = false;
			
			if(this.isSending) {
				
				if(sendingWait > 0) {
					sendingWait--;
				} else {
					
					String line = txBuffer[sendingLine];
					
					if(line.length() > sendingIndex) {
						char c = line.charAt(sendingIndex);
						sendingIndex++;
						if(c == pause) {
							sendingWait = 20;
						} else {
							RTTYSystem.broadcast(worldObj, this.txChannel, c);
							this.sendingChar = c;
						}
					} else {
						
						if(sendingLine >= 4) {
							this.isSending = false;
							RTTYSystem.broadcast(worldObj, this.txChannel, eot);
							this.sendingLine = 0;
							this.sendingIndex = 0;
						} else {
							RTTYSystem.broadcast(worldObj, this.txChannel, eol);
							this.sendingLine++;
							this.sendingIndex = 0;
						}
					}
				}
			}
			
			if(!this.rxChannel.isEmpty()) {
				RTTYChannel chan = RTTYSystem.listen(worldObj, this.rxChannel);
				
				if(chan != null && chan.signal instanceof Character && (chan.timeStamp > worldObj.getTotalWorldTime() - 2 && chan.timeStamp != -1)) {
					char c = (char) chan.signal;
					
					if(this.deleteOnReceive) {
						this.deleteOnReceive = false;
						for(int i = 0; i < 5; i++) this.rxBuffer[i] = "";
						this.writingLine = 0;
					}
					
					if(c == eot) {
						if(this.printAfterRx) {
							this.printAfterRx = false;
							this.print();
						}
						this.deleteOnReceive = true;
					} else if(c == eol) {
						if(this.writingLine < 4) this.writingLine++;
						this.markDirty();
					} else if(c == bell) {
						worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.orb", 2F, 0.5F);
					} else if(c == print) {
						this.printAfterRx = true;
					} else if(c == clear) {
						for(int i = 0; i < 5; i++) this.rxBuffer[i] = "";
						this.writingLine = 0;
					} else {
						this.rxBuffer[this.writingLine] += c;
						this.markDirty();
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			for(int i = 0; i < 5; i++) {
				data.setString("tx" + i, txBuffer[i]);
				data.setString("rx" + i, rxBuffer[i]);
			}
			data.setString("txChan", txChannel);
			data.setString("rxChan", rxChannel);
			data.setInteger("sending", sendingChar);
			INBTPacketReceiver.networkPack(this, data, 16);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {

		for(int i = 0; i < 5; i++) {
			txBuffer[i] = nbt.getString("tx" + i);
			rxBuffer[i] = nbt.getString("rx" + i);
		}
		this.txChannel = nbt.getString("txChan");
		this.rxChannel = nbt.getString("rxChan");
		this.sendingChar = (char) nbt.getInteger("sending");
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		for(int i = 0; i < 5; i++) {
			if(data.hasKey("tx" + i)) this.txBuffer[i] = data.getString("tx" + i);
		}
		
		String cmd = data.getString("cmd");
		
		if("snd".equals(cmd) && !this.isSending) {
			this.isSending = true;
			this.sendingLine = 0;
			this.sendingIndex = 0;
		}
		
		if("rxprt".equals(cmd)) {
			print();
		}
		
		if("rxcls".equals(cmd)) {
			for(int i = 0; i < 5; i++) this.rxBuffer[i] = "";
			this.writingLine = 0;
		}
		
		if("sve".equals(cmd)) {
			this.txChannel = data.getString("txChan");
			this.rxChannel = data.getString("rxChan");
			this.markDirty();
		}
	}
	
	public void print() {
		ItemStack stack = new ItemStack(Items.paper);
		List<String> text = new ArrayList();
		for(int i = 0; i < 5; i++) {
			if(!rxBuffer[i].isEmpty()) text.add(rxBuffer[i]);
		}
		ItemStackUtil.addTooltipToStack(stack, text.toArray(new String[0]));
		stack.setStackDisplayName("Message");
		worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord + 0.5, yCoord + 1, zCoord + 0.5, stack));
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 16 * 16;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		for(int i = 0; i < 5; i++) {
			txBuffer[i] = nbt.getString("tx" + i);
			rxBuffer[i] = nbt.getString("rx" + i);
		}
		this.txChannel = nbt.getString("txChan");
		this.rxChannel = nbt.getString("rxChan");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < 5; i++) {
			nbt.setString("tx" + i, txBuffer[i]);
			nbt.setString("rx" + i, rxBuffer[i]);
		}
		nbt.setString("txChan", txChannel);
		nbt.setString("rxChan", rxChannel);
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GuiScreenRadioTelex(this);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 2,
					zCoord + 2
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
