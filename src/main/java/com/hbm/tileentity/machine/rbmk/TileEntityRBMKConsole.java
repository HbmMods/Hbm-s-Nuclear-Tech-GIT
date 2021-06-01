package com.hbm.tileentity.machine.rbmk;

import java.util.Set;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class TileEntityRBMKConsole extends TileEntityMachineBase implements IControlReceiver {
	
	private int targetX;
	private int targetY;
	private int targetZ;
	
	//made this one-dimensional because it's a lot easier to serialize
	public RBMKColumn[] columns = new RBMKColumn[15 * 15];

	public TileEntityRBMKConsole() {
		super(0);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.worldObj.getTotalWorldTime() % 10 == 0) {
				rescan();
				prepareNetworkPack();
			}
		}
	}
	
	private void rescan() {
		
		for(int i = -7; i <= 7; i++) {
			for(int j = -7; j <= 7; j++) {
				
				TileEntity te = worldObj.getTileEntity(targetX + i, targetY, targetZ + j);
				int index = (i + 7) + (j + 7) * 15;
				
				if(te instanceof TileEntityRBMKBase) {
					
					TileEntityRBMKBase rbmk = (TileEntityRBMKBase)te;
					
					columns[index] = new RBMKColumn(rbmk.getConsoleType(), rbmk.getNBTForConsole());
					columns[index].data.setDouble("heat", rbmk.heat);
					columns[index].data.setDouble("maxHeat", rbmk.maxHeat());
					
				} else {
					columns[index] = null;
				}
			}
		}
	}
	
	private void prepareNetworkPack() {
		
		NBTTagCompound data = new NBTTagCompound();
		
		for(int i = 0; i < columns.length; i++) {
			
			if(this.columns[i] != null) {
				data.setTag("column_" + i, this.columns[i].data);
				data.setShort("type_" + i, (short)this.columns[i].type.ordinal());
			}
		}
		
		this.networkPack(data, 50);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		
		this.columns = new RBMKColumn[15 * 15];
		
		for(int i = 0; i < columns.length; i++) {
			
			if(data.hasKey("type_" + i)) {
				this.columns[i] = new RBMKColumn(ColumnType.values()[data.getShort("type_" + i)], (NBTTagCompound)data.getTag("column_" + i));
			}
		}
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("level")) {
			
			Set<String> keys = data.func_150296_c();
			
			for(String key : keys) {
				
				if(key.startsWith("sel_")) {

					int x = data.getInteger(key) % 15 - 7;
					int z = data.getInteger(key) / 15 - 7;
					
					TileEntity te = worldObj.getTileEntity(targetX + x, targetY, targetZ + z);
					
					if(te instanceof TileEntityRBMKControlManual) {
						((TileEntityRBMKControlManual)te).targetLevel = MathHelper.clamp_double(data.getDouble("level"), 0, 1);
						te.markDirty();
					}
				}
			}
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 4, zCoord + 3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	public void setTarget(int x, int y, int z) {
		this.targetX = x;
		this.targetY = y;
		this.targetZ = z;
		this.markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.targetX = nbt.getInteger("tX");
		this.targetY = nbt.getInteger("tY");
		this.targetZ = nbt.getInteger("tZ");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("tX", this.targetX);
		nbt.setInteger("tY", this.targetY);
		nbt.setInteger("tZ", this.targetZ);
	}
	
	public static class RBMKColumn {
		
		public ColumnType type;
		public NBTTagCompound data;
		
		public RBMKColumn(ColumnType type) {
			this.type = type;
		}
		
		public RBMKColumn(ColumnType type, NBTTagCompound data) {
			this.type = type;
			
			if(data != null) {
				this.data = data;
			} else {
				this.data = new NBTTagCompound();
			}
		}
	}
	
	public static enum ColumnType {
		BLANK(0),
		FUEL(10),
		CONTROL(20),
		CONTROL_AUTO(30),
		BOILER(40),
		MODERATOR(50),
		ABSORBER(60),
		REFLECTOR(70),
		OUTGASSER(80),
		BREEDER(90);
		
		public int offset;
		
		private ColumnType(int offset) {
			this.offset = offset;
		}
	}
}
