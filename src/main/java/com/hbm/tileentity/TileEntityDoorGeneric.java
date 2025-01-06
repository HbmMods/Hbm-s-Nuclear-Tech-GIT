
package com.hbm.tileentity;

import java.util.HashSet;
import java.util.Set;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.generic.BlockDoorGeneric;
import com.hbm.interfaces.IAnimatedDoor;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.TEDoorAnimationPacket;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.machine.TileEntityLockableBase;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.Rotation;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDoorGeneric extends TileEntityLockableBase implements IAnimatedDoor {

	//0: closed, 1: open, 2: closing, 3: opening
	public byte state = 0;
	protected DoorDecl doorType;
	public int openTicks = 0;
	public long animStartTime = 0;
	public int redstonePower;
	public boolean shouldUseBB = false;
	private byte skinIndex = 0;

	public Set<BlockPos> activatedBlocks = new HashSet<>(4);

	private AudioWrapper audio;
	private AudioWrapper audio2;
	
	@Override
	public void updateEntity(){
		if(state == 3) {
			openTicks++;
			if(openTicks >= getDoorType().timeToOpen()) {
				openTicks = getDoorType().timeToOpen();
			}
		} else if(state == 2) {
			openTicks--;
			if(openTicks <= 0) {
				openTicks = 0;
			}
		}

		if(!worldObj.isRemote) {
			
			BlockPos pos = new BlockPos(this);
			
			int[][] ranges = getDoorType().getDoorOpenRanges();
			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
			
			if(state == 3) {
				
				for(int i = 0; i < ranges.length; i++) {
					
					int[] range = ranges[i];
					BlockPos startPos = new BlockPos(range[0], range[1], range[2]);
					float time = getDoorType().getDoorRangeOpenTime(openTicks, i);
					
					for(int j = 0; j < Math.abs(range[3]); j++) {
						
						if((float)j / (Math.abs(range[3] - 1)) > time)
							break;
						
						for(int k = 0; k < range[4]; k++) {
							BlockPos add = new BlockPos(0, 0, 0);
							switch(range[5]){
							case 0: add = new BlockPos(0, k, (int)Math.signum(range[3]) * j); break;
							case 1: add = new BlockPos(k, (int)Math.signum(range[3]) * j, 0); break;
							case 2: add = new BlockPos((int)Math.signum(range[3]) * j, k, 0); break;
							}
							
							Rotation r = Rotation.getBlockRotation(dir);
							if(dir == Library.POS_X || dir == Library.NEG_X)
								r = r.add(Rotation.CLOCKWISE_180);
							
							BlockPos finalPos = startPos.add(add).rotate(r).add(pos);
							
							if(finalPos.equals(pos)) {
								this.shouldUseBB = false;
							} else {
								((BlockDummyable)getBlockType()).makeExtra(worldObj, finalPos.getX(), finalPos.getY(), finalPos.getZ());
							}
						}
					}
				}
				
			} else if(state == 2){
				
				for(int i = 0; i < ranges.length; i++) {
					
					int[] range = ranges[i];

					BlockPos startPos = new BlockPos(range[0], range[1], range[2]);
					float time = getDoorType().getDoorRangeOpenTime(openTicks, i);
					
					for(int j = Math.abs(range[3])-1; j >= 0; j--) {
						
						if((float)j / (Math.abs(range[3] - 1)) < time)
							break;
						
						for(int k = 0; k < range[4]; k++) {
							BlockPos add = new BlockPos(0, 0, 0);
							switch(range[5]){
							case 0: add = new BlockPos(0, k, (int)Math.signum(range[3]) * j); break;
							case 1: add = new BlockPos(k, (int)Math.signum(range[3]) * j, 0); break;
							case 2: add = new BlockPos((int)Math.signum(range[3]) * j, k, 0); break;
							}

							Rotation r = Rotation.getBlockRotation(dir);
							if(dir == Library.POS_X || dir == Library.NEG_X)
								r = r.add(Rotation.CLOCKWISE_180);
							
							BlockPos finalPos = startPos.add(add).rotate(r).add(pos);
							
							if(finalPos.equals(pos)) {
								this.shouldUseBB = false;
							} else {
								((BlockDummyable)getBlockType()).removeExtra(worldObj, finalPos.getX(), finalPos.getY(), finalPos.getZ());
							}
						}
					}
				}
			}
			if(state == 3 && openTicks == getDoorType().timeToOpen()) {
				state = 1;
			}
			if(state == 2 && openTicks == 0) {
				state = 0;
			}
			PacketDispatcher.wrapper.sendToAllAround(new TEDoorAnimationPacket(xCoord, yCoord, zCoord, state, skinIndex, (byte)(shouldUseBB ? 1 : 0)), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 100));
			
			if(redstonePower == -1 && state == 1){
				tryToggle(-1);
			} else if(redstonePower > 0 && state == 0){
				tryToggle(-1);
			}
			if(redstonePower == -1){
				redstonePower = 0;
			}
		}
	}

	@Override
	public void onChunkUnload() {
		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
		if(audio2 != null) {
			audio2.stopSound();
			audio2 = null;
		}
	}
	
	public DoorDecl getDoorType(){
		
		if(this.doorType == null && this.getBlockType() instanceof BlockDoorGeneric)
			this.doorType = ((BlockDoorGeneric)this.getBlockType()).type;
		
		return this.doorType;
	}

	public boolean tryToggle(EntityPlayer player){
		
		if(this.isLocked() && player == null) return false;
		
		if(state == 0 && redstonePower > 0){
			//Redstone "power locks" doors, just like minecraft iron doors
			return false;
		}
		if(this.state == 0) {
			if(!worldObj.isRemote && canAccess(player)) {
				this.state = 3;
			}
			return true;
		} else if(this.state == 1) {
			if(!worldObj.isRemote && canAccess(player)) {
				this.state = 2;
			}
			return true;
		}
		return false;
	}
	
	public boolean tryToggle(int passcode){
		if(this.isLocked() && passcode != this.lock)
			return false;
		if(this.state == 0) {
			if(!worldObj.isRemote) {
				this.state = 3;
			}
			return true;
		} else if(this.state == 1) {
			if(!worldObj.isRemote) {
				this.state = 2;
			}
			return true;
		}
		return false;
	}

	@Override
	public void open(){
		if(state == 0)
			toggle();
	}

	@Override
	public void close(){
		if(state == 1)
			toggle();
	}

	@Override
	public DoorState getState(){
		return DoorState.values()[state];
	}

	@Override
	public void toggle(){
		if(state == 0) {
			state = 3;
		} else if(state == 1) {
			state = 2;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleNewState(byte state){
		
		if(this.state != state) {
			DoorDecl doorType = getDoorType();

			if(this.state == 0 && state == 3){ // Door transitioning to open
				if(audio != null) {
					audio.stopSound();
					audio.setKeepAlive(0);
				}

				if(doorType.getOpenSoundLoop() != null){
					audio = MainRegistry.proxy.getLoopedSound(doorType.getOpenSoundLoop(), xCoord, yCoord, zCoord, doorType.getSoundVolume(), 10F, 1F);
					audio.startSound();
				}

				if(doorType.getOpenSoundStart() != null){
					worldObj.playSound(xCoord, yCoord, zCoord, doorType.getOpenSoundStart(), doorType.getSoundVolume(), 1F, false);
				}

				if(doorType.getSoundLoop2() != null){
					if(audio2 != null) audio2.stopSound();

					audio2 = MainRegistry.proxy.getLoopedSound(doorType.getSoundLoop2(), xCoord, yCoord, zCoord, doorType.getSoundVolume(), 10F, 1F);
					audio2.startSound();
				}
			}

			if(this.state == 1 && state == 2){ // Door transitioning to closed
				if(audio != null) {
					audio.stopSound();
				}

				if(doorType.getCloseSoundLoop() != null){
					audio = MainRegistry.proxy.getLoopedSound(doorType.getCloseSoundLoop(), xCoord, yCoord, zCoord, doorType.getSoundVolume(), 10F, 1F);
					audio.startSound();
				}

				if(doorType.getCloseSoundStart() != null){
					worldObj.playSound(xCoord, yCoord, zCoord, doorType.getCloseSoundStart(), doorType.getSoundVolume(), 1F, false);
				}

				if(doorType.getSoundLoop2() != null){
					if(audio2 != null) audio2.stopSound();

					audio2 = MainRegistry.proxy.getLoopedSound(doorType.getSoundLoop2(), xCoord, yCoord, zCoord, doorType.getSoundVolume(), 10F, 1F);
					audio2.startSound();
				}
			}

			if(state == 1 || state == 0){ // Door finished any transition
				if(audio != null){
					audio.stopSound();
					audio = null;
				}
				if(audio2 != null){
					audio2.stopSound();
					audio2 = null;
				}
			}

			if(this.state == 3 && state == 1){ // Door finished transitioning to open
				if(doorType.getOpenSoundEnd() != null){
					worldObj.playSound(xCoord, yCoord, zCoord, doorType.getOpenSoundEnd(), doorType.getSoundVolume(), 1F, false);
				}
			}

			if(this.state == 2 && state == 0){ // Door finished transitioning to closed
				if(doorType.getCloseSoundEnd() != null){
					worldObj.playSound(xCoord, yCoord, zCoord, doorType.getCloseSoundEnd(), doorType.getSoundVolume(), 1F, false);
				}
			}
			
			
			this.state = state;
			if(state > 1)
				animStartTime = System.currentTimeMillis();
		}
	}

	//Ah yes piggy backing on this packet
	@Override
	public void setTextureState(byte tex){
		shouldUseBB = tex > 0;
	}

	public int getSkinIndex() {
		return skinIndex;
	}

	@Override
	public boolean setSkinIndex(byte skinIndex) {
		if(!getDoorType().hasSkins())
			return false;
		if(getDoorType().getSkinCount() < skinIndex) {
			return false;
		}
		this.skinIndex = skinIndex;
		return true;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox(){
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public double getMaxRenderDistanceSquared(){
		return 65536D;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		this.state = tag.getByte("state");
		this.openTicks = tag.getInteger("openTicks");
		this.animStartTime = tag.getInteger("animStartTime");
		this.redstonePower = tag.getInteger("redstoned");
		this.shouldUseBB = tag.getBoolean("shouldUseBB");
		this.skinIndex = tag.getByte("skin");
		NBTTagCompound activatedBlocks = tag.getCompoundTag("activatedBlocks");
		this.activatedBlocks.clear();
		for(int i = 0; i < activatedBlocks.func_150296_c().size() / 3; i++) {
			this.activatedBlocks.add(new BlockPos(activatedBlocks.getInteger("x" + i), activatedBlocks.getInteger("y" + i), activatedBlocks.getInteger("z" + i)));
		}
		super.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		tag.setByte("state", state);
		tag.setInteger("openTicks", openTicks);
		tag.setLong("animStartTime", animStartTime);
		tag.setInteger("redstoned", redstonePower);
		tag.setBoolean("shouldUseBB", shouldUseBB);
		if(getDoorType().hasSkins())
			tag.setByte("skin", skinIndex);
		NBTTagCompound activatedBlocks = new NBTTagCompound();
		int i = 0;
		for(BlockPos p : this.activatedBlocks) {
			activatedBlocks.setInteger("x" + i, p.getX());
			activatedBlocks.setInteger("y" + i, p.getY());
			activatedBlocks.setInteger("z" + i, p.getZ());
			i++;
		}
		tag.setTag("activatedBlocks", activatedBlocks);
	}
	
	@Override
	public void validate(){
		super.validate();
	}
	
	@Override
	public void invalidate(){
		super.invalidate();
		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
		if(audio2 != null) {
			audio2.stopSound();
			audio2 = null;
		}
	}

	public void updateRedstonePower(int x, int y, int z) {
		//Drillgon200: Best I could come up with without having to use dummy tile entities
		BlockPos pos = new BlockPos(x, y, z);
		boolean powered = worldObj.isBlockIndirectlyGettingPowered(x, y, z);
		boolean contained = activatedBlocks.contains(pos);
		if(!contained && powered){
			activatedBlocks.add(pos);
			if(redstonePower == -1){
				redstonePower = 0;
			}
			redstonePower++;
		} else if(contained && !powered){
			activatedBlocks.remove(pos);
			redstonePower--;
			if(redstonePower == 0){
				redstonePower = -1;
			}
		}
	}

}