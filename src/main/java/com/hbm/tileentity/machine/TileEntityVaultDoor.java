package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.DummyBlockVault;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.TEVaultPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityVaultDoor extends TileEntityLockableBase {
	
	public boolean isOpening = false;
	//0: closed, 1: opening/closing, 2:open
	public int state = 0;
	public long sysTime;
	private int timer = 0;
	public int type;
	public static final int maxTypes = 7;
	public boolean redstoned = false;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
    public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(!isLocked()) {
				boolean flagX = false;
				boolean flagZ = false;

				for(int x = xCoord - 2; x <= xCoord + 2; x++)
					for(int y = yCoord; y <= yCoord + 5; y++)
						if(worldObj.isBlockIndirectlyGettingPowered(x, y, zCoord)) {
							flagX = true;
							break;
						}
				
				for(int z = zCoord - 2; z <= zCoord + 2; z++)
					for(int y = yCoord; y <= yCoord + 5; y++)
						if(worldObj.isBlockIndirectlyGettingPowered(xCoord, y, z)) {
							flagZ = true;
							break;
						}

				if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 2 || worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 3) {
					if(flagX) {
						
						if(!redstoned) {
							this.tryToggle();
						}
						
						redstoned = true;
					} else {
						
						redstoned = false;
					}
				}
				if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 4 || worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 5) {
					if(flagZ) {
						
						if(!redstoned) {
							this.tryToggle();
						}
						
						redstoned = true;
					} else {
						
						redstoned = false;
					}
				}
			}

	    	if(isOpening && state == 1) {
				
	    		if(timer == 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultScrapeNew", 1.0F, 1.0F);
	    		if(timer == 45)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 55)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 65)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 75)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 85)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 95)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 105)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 115)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    	}
	    	if(!isOpening && state == 1) {

	    		if(timer == 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 10)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 20)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 30)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 40)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 50)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 60)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(timer == 70)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		
	    		if(timer == 80)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultScrapeNew", 1.0F, 1.0F);
	    	}	
	    			
	    	if(state != 1) {
	    		timer = 0;
	    	} else {
	    		timer++;
	    		
	    		if(timer >= 120) {
	    			
	    			if(isOpening)
	    				finishOpen();
	    			else
	    				finishClose();
	    		}
	    	}
	    	
	    	PacketDispatcher.wrapper.sendToAllAround(new TEVaultPacket(xCoord, yCoord, zCoord, isOpening, state, 0, type), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
		}
    }
	
	public void open() {
		if(state == 0) {
	    	PacketDispatcher.wrapper.sendToAllAround(new TEVaultPacket(xCoord, yCoord, zCoord, isOpening, state, 1, type), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
			isOpening = true;
			state = 1;
			
			openHatch();
		}
	}
	
	public void finishOpen() {
		state = 2;
	}
	
	public void close() {
		if(state == 2) {
	    	PacketDispatcher.wrapper.sendToAllAround(new TEVaultPacket(xCoord, yCoord, zCoord, isOpening, state, 1, type), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
			isOpening = false;
			state = 1;
			
			closeHatch();
		}
	}
	
	public void finishClose() {
		state = 0;
	}
	
	public boolean canOpen() {
		return state == 0;
	}
	
	public boolean canClose() {
		return state == 2 && isHatchFree();
	}
	
	public void tryToggle() {

		if(canOpen())
			open();
		else if(canClose())
			close();
	}
	
	public boolean placeDummy(int x, int y, int z) {

		if(!worldObj.getBlock(x, y, z).isReplaceable(worldObj, x, y, z)) worldObj.func_147480_a(x, y, z, false);
		
		worldObj.setBlock(x, y, z, ModBlocks.dummy_block_vault);
		
		TileEntity te = worldObj.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy)te;
			dummy.targetX = xCoord;
			dummy.targetY = yCoord;
			dummy.targetZ = zCoord;
		}
		
		return true;
	}
	
	public void removeDummy(int x, int y, int z) {
		
		if(worldObj.getBlock(x, y, z) == ModBlocks.dummy_block_vault) {
			DummyBlockVault.safeBreak = true;
			worldObj.setBlock(x, y, z, Blocks.air);
			DummyBlockVault.safeBreak = false;
		}
	}
	
	private boolean isHatchFree() {

		if(this.getBlockMetadata() == 2 || this.getBlockMetadata() == 3)
			return checkNS();
		else if(this.getBlockMetadata() == 4 || this.getBlockMetadata() == 5)
			return checkEW();
		else
			return true;
	}
	
	private void closeHatch() {

		if(this.getBlockMetadata() == 2 || this.getBlockMetadata() == 3)
			fillNS();
		else if(this.getBlockMetadata() == 4 || this.getBlockMetadata() == 5)
			fillEW();
	}
	
	private void openHatch() {

		if(this.getBlockMetadata() == 2 || this.getBlockMetadata() == 3)
			removeNS();
		else if(this.getBlockMetadata() == 4 || this.getBlockMetadata() == 5)
			removeEW();
	}
	
	private boolean checkNS() {
		return worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord).isReplaceable(worldObj, xCoord - 1, yCoord + 1, zCoord) &&
				worldObj.getBlock(xCoord, yCoord + 1, zCoord).isReplaceable(worldObj, xCoord, yCoord + 1, zCoord) &&
				worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord).isReplaceable(worldObj, xCoord + 1, yCoord + 1, zCoord) &&
				worldObj.getBlock(xCoord - 1, yCoord + 2, zCoord).isReplaceable(worldObj, xCoord - 1, yCoord + 2, zCoord) &&
				worldObj.getBlock(xCoord, yCoord + 2, zCoord).isReplaceable(worldObj, xCoord, yCoord + 2, zCoord) &&
				worldObj.getBlock(xCoord + 1, yCoord + 2, zCoord).isReplaceable(worldObj, xCoord + 1, yCoord + 2, zCoord) &&
				worldObj.getBlock(xCoord - 1, yCoord + 3, zCoord).isReplaceable(worldObj, xCoord - 1, yCoord + 3, zCoord) &&
				worldObj.getBlock(xCoord, yCoord + 3, zCoord).isReplaceable(worldObj, xCoord, yCoord + 3, zCoord) &&
				worldObj.getBlock(xCoord + 1, yCoord + 3, zCoord).isReplaceable(worldObj, xCoord + 1, yCoord + 3, zCoord);
	}
	
	private boolean checkEW() {
		return worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1).isReplaceable(worldObj, xCoord, yCoord + 1, zCoord -1) &&
				worldObj.getBlock(xCoord, yCoord + 1, zCoord).isReplaceable(worldObj, xCoord, yCoord, zCoord) &&
				worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1).isReplaceable(worldObj, xCoord, yCoord + 1, zCoord + 1) &&
				worldObj.getBlock(xCoord, yCoord + 2, zCoord - 1).isReplaceable(worldObj, xCoord, yCoord + 2, zCoord - 1) &&
				worldObj.getBlock(xCoord, yCoord + 2, zCoord).isReplaceable(worldObj, xCoord, yCoord + 2, zCoord) &&
				worldObj.getBlock(xCoord, yCoord + 2, zCoord + 1).isReplaceable(worldObj, xCoord, yCoord + 2, zCoord + 1) &&
				worldObj.getBlock(xCoord, yCoord + 3, zCoord - 1).isReplaceable(worldObj, xCoord, yCoord + 3, zCoord - 1) &&
				worldObj.getBlock(xCoord, yCoord + 3, zCoord).isReplaceable(worldObj, xCoord, yCoord + 3, zCoord) &&
				worldObj.getBlock(xCoord, yCoord + 3, zCoord + 1).isReplaceable(worldObj, xCoord, yCoord + 3, zCoord + 1);
	}
	
	private void fillNS() {

		placeDummy(xCoord - 1, yCoord + 1, zCoord);
		placeDummy(xCoord - 1, yCoord + 2, zCoord);
		placeDummy(xCoord - 1, yCoord + 3, zCoord);
		placeDummy(xCoord, yCoord + 1, zCoord);
		placeDummy(xCoord, yCoord + 2, zCoord);
		placeDummy(xCoord, yCoord + 3, zCoord);
		placeDummy(xCoord + 1, yCoord + 1, zCoord);
		placeDummy(xCoord + 1, yCoord + 2, zCoord);
		placeDummy(xCoord + 1, yCoord + 3, zCoord);
	}
	
	private void fillEW() {

		placeDummy(xCoord, yCoord + 1, zCoord - 1);
		placeDummy(xCoord, yCoord + 2, zCoord - 1);
		placeDummy(xCoord, yCoord + 3, zCoord - 1);
		placeDummy(xCoord, yCoord + 1, zCoord);
		placeDummy(xCoord, yCoord + 2, zCoord);
		placeDummy(xCoord, yCoord + 3, zCoord);
		placeDummy(xCoord, yCoord + 1, zCoord + 1);
		placeDummy(xCoord, yCoord + 2, zCoord + 1);
		placeDummy(xCoord, yCoord + 3, zCoord + 1);
	}
	
	private void removeNS() {

		removeDummy(xCoord - 1, yCoord + 1, zCoord);
		removeDummy(xCoord - 1, yCoord + 2, zCoord);
		removeDummy(xCoord - 1, yCoord + 3, zCoord);
		removeDummy(xCoord, yCoord + 1, zCoord);
		removeDummy(xCoord, yCoord + 2, zCoord);
		removeDummy(xCoord, yCoord + 3, zCoord);
		removeDummy(xCoord + 1, yCoord + 1, zCoord);
		removeDummy(xCoord + 1, yCoord + 2, zCoord);
		removeDummy(xCoord + 1, yCoord + 3, zCoord);
	}
	
	private void removeEW() {

		removeDummy(xCoord, yCoord + 1, zCoord - 1);
		removeDummy(xCoord, yCoord + 2, zCoord - 1);
		removeDummy(xCoord, yCoord + 3, zCoord - 1);
		removeDummy(xCoord, yCoord + 1, zCoord);
		removeDummy(xCoord, yCoord + 2, zCoord);
		removeDummy(xCoord, yCoord + 3, zCoord);
		removeDummy(xCoord, yCoord + 1, zCoord + 1);
		removeDummy(xCoord, yCoord + 2, zCoord + 1);
		removeDummy(xCoord, yCoord + 3, zCoord + 1);
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		isOpening = nbt.getBoolean("isOpening");
		state = nbt.getInteger("state");
		sysTime = nbt.getLong("sysTime");
		timer = nbt.getInteger("timer");
		type = nbt.getInteger("type");
		redstoned = nbt.getBoolean("redstoned");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("isOpening", isOpening);
		nbt.setInteger("state", state);
		nbt.setLong("sysTime", sysTime);
		nbt.setInteger("timer", timer);
		nbt.setInteger("type", type);
		nbt.setBoolean("redstoned", redstoned);
	}
}
