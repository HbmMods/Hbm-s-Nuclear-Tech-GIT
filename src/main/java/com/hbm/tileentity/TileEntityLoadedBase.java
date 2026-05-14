package com.hbm.tileentity;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.main.NTMSounds;
import com.hbm.packet.toclient.BufPacket;
import com.hbm.sound.AudioWrapper;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.tile.ILoadedTile;
import cpw.mods.fml.common.network.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLoadedBase extends TileEntity implements ILoadedTile, IBufPacketReceiver {

	public boolean isLoaded = true;
	public boolean muffled = false;
	public boolean tilted = false;
	public int tiltBlocksChecked = 0;
	public int tiltBlocksValid = 0;

	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		this.isLoaded = false;
	}

	/** The "chunks is modified, pls don't forget to save me" effect of markDirty, minus the block updates */
	public void markChanged() {
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
	}

	public AudioWrapper createAudioLoop() { return null; }

	public AudioWrapper rebootAudio(AudioWrapper wrapper) {
		wrapper.stopSound();
		AudioWrapper audio = createAudioLoop();
		audio.startSound();
		return audio;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.muffled = nbt.getBoolean("muffled");
		this.tilted = nbt.getBoolean("tilted");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("muffled", muffled);
		nbt.setBoolean("tilted", tilted);
	}

	public float getVolume(float baseVolume) {
		return muffled ? baseVolume * 0.1F : baseVolume;
	}

	private ByteBuf lastPackedBuf;

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(muffled);
		buf.writeBoolean(tilted);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.muffled = buf.readBoolean();
		this.tilted = buf.readBoolean();
	}

	/** Sends a sync packet that uses ByteBuf for efficient information-cramming */
	public void networkPackNT(int range) {
		if(worldObj.isRemote) return;

		BufPacket packet = new BufPacket(xCoord, yCoord, zCoord, this);

		ByteBuf preBuf = packet.getCompiledBuffer();

		// Don't send unnecessary packets, except for maybe one every second or so.
		// If we stop sending duplicate packets entirely, this causes issues when
		// a client unloads and then loads back a chunk with an unchanged tile entity.
		// For that client, the tile entity will appear default until anything changes about it.
		// In my testing, this can be reliably reproduced with a full fluid barrel, for instance.
		// I think it might be fixable by doing something with getDescriptionPacket() and onDataPacket(),
		// but this sidesteps the problem for the mean time.
		if(preBuf.equals(lastPackedBuf) && this.worldObj.getTotalWorldTime() % 20 != 0) return;

		this.lastPackedBuf = preBuf.copy();

		PacketThreading.createAllAroundThreadedPacket(packet, new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}
	
	public static enum TiltType {
		UNAVOIDABLE, CONFIG;
	}
	
	public void checkTilt(TiltType cfg, boolean extraHeavy) {
		boolean doesTilt = false;
		if(cfg == TiltType.UNAVOIDABLE) doesTilt = true;
		if(cfg == TiltType.CONFIG && GeneralConfig.enableMachineGravity) doesTilt = true;
		if(cfg == TiltType.CONFIG && GeneralConfig.enable528MachineGravity) doesTilt = true;
		
		if(!doesTilt) { this.tilted = false; return; }
		if(this.getFloorCount() <= 0) { this.tilted = false; return; }
		if((this.worldObj.getTotalWorldTime() + BlockPos.getIdentity(xCoord, yCoord, zCoord)) % 20 != 0) return;
		
		if(this.tiltBlocksChecked >= this.getFloorCount()) {
			
			if(this.tiltBlocksValid >= this.tiltBlocksChecked * 0.95) {
				this.tilted = false;
			} else {
				if(!this.tilted) worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, NTMSounds.METAL_IMPACT, 3F, 1F);
				this.tilted = true;
			}
			
			this.markChanged();
			this.tiltBlocksChecked = 0;
			this.tiltBlocksValid = 0;
		}
		
		BlockPos pos = getFloorPosFromIndex(this.tiltBlocksChecked);
		if(pos == null) return;
		
		Block ground = worldObj.getBlock(pos.getX(), pos.getY(), pos.getZ());
		this.tiltBlocksChecked++;
		
		// for extra heavy machines, the ground needs to:
		// * be a fully solid block (side UP is checked for custom behavior)
		// * be opaque
		// * NOT be sand, cloth or ground material
		// * have an explosion resistance of stone or greater
		if(extraHeavy) {
			if(!ground.isBlockSolid(worldObj, pos.getX(), pos.getY(), pos.getZ(), 1)) return;
			if(!ground.isNormalCube()) return;
			if(ground.getMaterial() == Material.sand || ground.getMaterial() == Material.cloth || ground.getMaterial() == Material.ground) return;
			if(ground.getExplosionResistance(null) < Blocks.stone.getExplosionResistance(null)) return;
			this.tiltBlocksValid++;
		// for standard machines, the ground needs to:
		// * be solid at the top
		// * NOT be sand
		} else {
			if(!ground.isSideSolid(worldObj, pos.getX(), pos.getY(), pos.getZ(), ForgeDirection.UP)) return;
			if(ground.getMaterial() == Material.sand) return;
			if(ground == ModBlocks.dirt_dead || ground == ModBlocks.dirt_oily || ground == ModBlocks.stone_cracked) return;
			this.tiltBlocksValid++;
		}
	}
	
	public int getFloorCount() { return 0; }
	public BlockPos getFloorPosFromIndex(int index) { return null; }

	public BlockPos standardFloor3x3(int index) {
		return new BlockPos(xCoord - 1 + (index / 2) * 2, yCoord - 1, zCoord - 1 + (index % 2) * 2);
	}
	public BlockPos standardFloor5x5(int index) {
		return new BlockPos(xCoord - 2 + (index / 3) * 2, yCoord - 1, zCoord - 2 + (index % 3) * 2);
	}
	public BlockPos standardFloor7x7(int index) {
		return new BlockPos(xCoord - 3 + (index / 4) * 2, yCoord - 1, zCoord - 3 + (index % 4) * 2);
	}
}
