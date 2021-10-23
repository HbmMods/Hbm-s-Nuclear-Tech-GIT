package com.hbm.tileentity.turret;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.calc.EasyLocation;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudSolinium;
import com.hbm.entity.effect.EntityCloudTom;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.I18nUtil;

import api.hbm.block.ILaserable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLunarOni extends TileEntityMachineBase implements IConsumer, ILaserable, IControlReceiver
{
	private long power = 0;
	private long buffer = 0;
	private long energyIn = 0;
	public static final long powerRate = 1000000000L;
	public static final long maxPower = 1000000000000L;
	public static final long maxBuffer = 100000000000L;
	private float elevation = 0.0F;
	private float direction = 0.0F;
	private int range;
	private byte powPercentage = 0;
	private long yield = 0;
	public Mode currMode = Mode.DISABLED;
	public EntityLivingBase currEntity = null;
	private int[] custCoord = new int[3];
	private EasyLocation loc = null;
	private boolean bufferQueued = false;
	private boolean laserOn = false;
	public static enum Mode
	{
		/** Off **/
		DISABLED,
		/** Target entities **/
		TARG_ENTITY,
		/** Target blocks or anything **/
		TARG_MISC,
		/** cover yourself in moonlight **/
		FLOOD;
	}
	public TileEntityLunarOni()
	{
		super(0);
	}

	@Override
	public void updateEntity()
	{
		// TODO Auto-generated method stub
		if (!worldObj.isRemote)
		{
			if (!bufferQueued)
				buffer = 0;
			yield = maxBuffer * powPercentage / 100;
			if (currMode == Mode.TARG_ENTITY && currEntity != null)
				loc = new EasyLocation(currEntity);
			if (currMode == Mode.TARG_MISC && custCoord != null)
				loc = new EasyLocation(custCoord);
			
			if (bufferQueued && fillBuffer())
			{
				bufferQueued = false;
				purify();
			}
		}
		
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setLong("buffer", buffer);
		data.setLong("yield", yield);
		data.setFloat("elevation", elevation);
		data.setFloat("direction", direction);
		data.setByte("mode", modeToByte(currMode));
		data.setByte("percentage", powPercentage);
		data.setBoolean("charging", bufferQueued);
		data.setBoolean("laserOn", laserOn);
		if (currEntity != null)
			data.setInteger("entityID", currEntity.getEntityId());
		if (loc != null)
			data.setIntArray("custCoord", loc.getCoordInt());
		networkPack(data, 100);
	}
	
	private boolean cutThrough(EasyLocation target)
	{
		Vec3 origin = Vec3.createVectorHelper(xCoord, yCoord, zCoord);
		Vec3 point = Vec3.createVectorHelper(target.posX, target.posY, target.posZ);
		List<int[]> blocks = Library.getBlockPosInPath(xCoord, yCoord, zCoord, 25, Vec3.createVectorHelper(target.posX - xCoord, target.posY - yCoord, target.posZ - zCoord));
		short airCount = 0;
		short blockCount = 0;
		return false;
	}
	
	private void testLaser(EasyLocation target)
	{
		List<int[]> blocks = Library.getBlockPosInPath(xCoord, yCoord, zCoord, 25, Vec3.createVectorHelper(target.posX - xCoord, target.posY - yCoord, target.posZ - zCoord));
		for (int[] blockC: blocks)
		{
			EasyLocation currBlock = new EasyLocation(blockC);
			currBlock.setWorld(getWorldObj());
			if (currBlock.getBlockAtCoord() == Blocks.air)
				currBlock.setBlockAtCoord(ModBlocks.block_australium);
		}
	}
	
	private void purify()
	{
		// TODO
		switch (currMode)
		{
		case TARG_ENTITY:
		case TARG_MISC:
			cutThrough(loc);
			break;
		case FLOOD:
			int fYield = (int) (yield / 1000000000) * 5;
			EntityNukeExplosionMK3 flood = new EntityNukeExplosionMK3(getWorldObj());
			flood.posX = xCoord;
			flood.posY = yCoord + 2;
			flood.posZ = zCoord;
			flood.destructionRange = fYield;//Would be a wee bit large without this clamped
			flood.speed = BombConfig.blastSpeed;
			flood.coefficient = 100;
			flood.coefficient2 = 200;
			flood.waste = false;
			flood.extType = 2;
			worldObj.spawnEntityInWorld(flood);
			
			EntityCloudTom effect = new EntityCloudTom(getWorldObj(), fYield);
			effect.posX = xCoord;
			effect.posY = yCoord + 2;
			effect.posZ = zCoord;
			worldObj.spawnEntityInWorld(effect);
			break;
		default:
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:gui.buttonNo", 1.0F, 1.0F);
			break;
		}
		buffer = 0;
	}
	
	public String getEntityName()
	{
		return currEntity != null ? currEntity.getCommandSenderName() : I18nUtil.resolveKey("twr.target.none");
	}
	
	public String getEntityCoordinates()
	{
		if (currEntity != null && currMode == Mode.TARG_ENTITY)
		{
			double[] coord = new double[3];
			coord[0] = currEntity.posX;
			coord[1] = currEntity.posY;
			coord[2] = currEntity.posZ;
			return String.format("x: %s, y: %s, z: %s", coord[0], coord[1], coord[2]);// Don't ask why
		}
		else if (custCoord != null && currMode == Mode.TARG_MISC)
			return String.format("x: %s, y: %s, z: %s", custCoord[0], custCoord[1], custCoord[2]);
		else
			return "N/A";
	}
	
	private static Mode byteToMode(byte in)
	{
		switch (in)
		{
		case 1:
			return Mode.TARG_ENTITY;
		case 2:
			return Mode.TARG_MISC;
		case 3:
			return Mode.FLOOD;
		default:
			return Mode.DISABLED;
		}
	}
	
	private static byte modeToByte(Mode in)
	{
		switch (in)
		{
		case DISABLED:
			return 0;
		case TARG_ENTITY:
			return 1;
		case TARG_MISC:
			return 2;
		case FLOOD:
			return 3;
		default:
			return 0;
		}
	}
	
	@Override
	public void handleButtonPacket(int value, int meta)
	{
		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "gui.button.press", 1.0F, 1.0F);
		if (value == 0 && !bufferQueued)
		{
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:alarm.defconstage", 1.0F, 1.0F);
			switch(meta)
			{
			case 0:
				currMode = Mode.DISABLED;
				break;
			case 1:
				currMode = Mode.TARG_ENTITY;
				break;
			case 2:
				currMode = Mode.TARG_MISC;
				break;
			case 3:
				currMode = Mode.FLOOD;
				break;
			default:
				break;
			}
		}
		else if (bufferQueued)
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:gui.buttonNo", 1.0F, 1.0F);
		else
		{
			if (maxBuffer * (powPercentage / 100) > power || currMode == Mode.DISABLED || powPercentage == 0 || bufferQueued)
			{
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:gui.buttonNo", 1.0F, 1.0F);
				return;
			}
			else
				bufferQueued = true;
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt)
	{
		power = nbt.getLong("power");
		buffer = nbt.getLong("buffer");
		yield = nbt.getLong("yield");
		elevation = nbt.getFloat("elevation");
		direction = nbt.getFloat("direction");
		currMode = byteToMode(nbt.getByte("mode"));
		powPercentage = nbt.getByte("percentage");
		bufferQueued = nbt.getBoolean("charging");
		laserOn = nbt.getBoolean("laserOn");
		if (nbt.hasKey("entityID"))
			currEntity = (EntityLivingBase) worldObj.getEntityByID(nbt.getInteger("entityID"));
		if (nbt.hasKey("custCoord"))
			loc = new EasyLocation(nbt.getIntArray("custCoord"));
	}
	/**
	 * Charge up sequence
	 * @return If it has finished
	 */
	private boolean fillBuffer()
	{
		if (power >= powerRate && buffer + powerRate <= yield)
		{
			power -= powerRate;
			buffer += powerRate;
		}
		return buffer >= yield;
	}
	
	private void clamp()
	{
		direction = MathHelper.clamp_float(direction, 0F, 360F);
		elevation = MathHelper.clamp_float(elevation, -50F, 50F);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setFloat("elevation", elevation);
		nbt.setFloat("direction", direction);
		nbt.setByte("mode", modeToByte(currMode));
		nbt.setByte("percentage", powPercentage);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		elevation = nbt.getFloat("elevation");
		direction = nbt.getFloat("direction");
		currMode = byteToMode(nbt.getByte("mode"));
		powPercentage = nbt.getByte("percentage");
	}
	
	@Override
	public void setPower(long i)
	{
		power = i;
	}

	@Override
	public long getPower()
	{
		return power;
	}
	
	public long getBuffer()
	{
		return buffer;
	}

	@Override
	public long getMaxPower()
	{
		return maxPower;
	}

	@Override
	public String getName()
	{
		return "container.turretLunarOni";
	}
	
	public float getDirection()
	{
		return direction;
	}
	public float getElevation()
	{
		return elevation;
	}
	public byte getLevel()
	{
		return powPercentage;
	}
	public boolean isCharging()
	{
		return bufferQueued;
	}

	@Override
	public void addEnergy(World world, int x, int y, int z, long energy, ForgeDirection dir)
	{
		energyIn = energy;
	}

	@Override
	public boolean hasPermission(EntityPlayer player)
	{
		return isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data)
	{
		System.out.println("Got data");
		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "gui.button.press", 1.0F, 1.0F);
		if (data.hasKey("input"))
			powPercentage = ((data.getByte("input") == -1) ? powPercentage : data.getByte("input"));
		if (data.hasKey("coord"))
			custCoord = data.getIntArray("coord");
	}
	
	private void makeExplosion(double x, double y, double z, int yield, boolean highPower)
	{
		if (highPower)
		{
			EntityNukeExplosionMK3 field = new EntityNukeExplosionMK3(getWorldObj());
			field.posX = x;
			field.posY = y;
			field.posZ = z;
			field.speed = 25;
			field.coefficient = 1.0F;
			field.waste = false;
			worldObj.spawnEntityInWorld(field);
			EntityCloudSolinium sphere = new EntityCloudSolinium(getWorldObj(), yield);
			sphere.posX = x;
			sphere.posY = y;
			sphere.posZ = z;
			worldObj.spawnEntityInWorld(sphere);
		}
		else
		{
			worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFacNoRad(getWorldObj(), yield, x, y, z));
			
			EntityNukeCloudSmall cloud = new EntityNukeCloudSmall(getWorldObj(), yield * 4, yield * 0.005F);
			cloud.posX = x;
			cloud.posY = y;
			cloud.posZ = z;
			worldObj.spawnEntityInWorld(cloud);
		}
	}
}
