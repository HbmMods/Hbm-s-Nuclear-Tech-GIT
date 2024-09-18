package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.dim.CelestialBody;
import com.hbm.dim.SolarSystem;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.inventory.container.ContainerTransporterRocket;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FT_Rocket;
import com.hbm.inventory.gui.GUITransporterRocket;
import com.hbm.items.ItemVOTVdrive.Target;
import com.hbm.lib.Library;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityTransporterRocket extends TileEntityTransporterBase {

	public boolean hasRocket = true;
	public int launchTicks = 0;

	public int threshold = 0;

    public TileEntityTransporterRocket() {
        super(16, 8, 128_000, 0, 2, 64_000);

		tanks[8].setTankType(Fluids.HYDROGEN);
		tanks[9].setTankType(Fluids.OXYGEN);
    }

	
	@Override
	public void updateEntity() {
		super.updateEntity();

		// If our transporter state sync is incorrect, fix whichever one gets updated first
		if(!worldObj.isRemote && linkedTransporter != null && linkedTransporter instanceof TileEntityTransporterRocket) {
			if(hasRocket == ((TileEntityTransporterRocket) linkedTransporter).hasRocket) {
				hasRocket = !hasRocket;
			}
		}

		launchTicks = MathHelper.clamp_int(launchTicks + (hasRocket ? -1 : 1), hasRocket ? -20 : 0, 100);

		if(worldObj.isRemote && launchTicks > 0 && launchTicks < 100) {
			ParticleUtil.spawnGasFlame(worldObj, xCoord + 0.5, yCoord + 0.5 + launchTicks, zCoord + 0.5, 0.0, -1.0, 0.0);

			if(launchTicks < 10) {
				ExplosionLarge.spawnShock(worldObj, xCoord + 0.5, yCoord, zCoord + 0.5, 1 + worldObj.rand.nextInt(3), 1 + worldObj.rand.nextGaussian());
			}
		}

	}

	public int getThreshold() {
		return threshold == 0 ? 0 : (int)Math.pow(2, threshold - 1);
	}

	private final int MASS_MULT = 100;

	// Check that we have enough fuel to send to our destination
	@Override
	protected boolean canSend(TileEntityTransporterBase linkedTransporter) {
		if(launchTicks > -20) return false;
		if(((TileEntityTransporterRocket)linkedTransporter).launchTicks < 100) return false;
		if(!hasRocket) return false;

		int mass = itemCount();
		if(mass < getThreshold()) return false;

		FT_Rocket fuelStats = tanks[8].getTankType().getTrait(FT_Rocket.class);
		if(fuelStats == null) fuelStats = tanks[9].getTankType().getTrait(FT_Rocket.class);

		if(fuelStats == null) return false;

		Target from = CelestialBody.getTarget(worldObj, xCoord, zCoord);
		Target to = CelestialBody.getTarget(linkedTransporter.getWorldObj(), linkedTransporter.xCoord, linkedTransporter.zCoord);

		int sendCost = Math.min(64_000, SolarSystem.getCostBetween(from.body, to.body, mass * MASS_MULT, (int)fuelStats.getThrust(), fuelStats.getISP(), from.inOrbit, to.inOrbit));

		return tanks[8].getFill() >= sendCost && tanks[9].getFill() >= sendCost;
	}

	@Override
	protected void hasSent(TileEntityTransporterBase linkedTransporter, int quantitySent) {
		// Recalculate send cost from what was actually successfully sent
		FT_Rocket fuelStats = tanks[8].getTankType().getTrait(FT_Rocket.class);
		if(fuelStats == null) fuelStats = tanks[9].getTankType().getTrait(FT_Rocket.class);

		Target from = CelestialBody.getTarget(worldObj, xCoord, zCoord);
		Target to = CelestialBody.getTarget(linkedTransporter.getWorldObj(), linkedTransporter.xCoord, linkedTransporter.zCoord);

		int sendCost = Math.min(64_000, SolarSystem.getCostBetween(from.body, to.body, quantitySent * MASS_MULT, (int)fuelStats.getThrust(), fuelStats.getISP(), from.inOrbit, to.inOrbit));

		tanks[8].setFill(tanks[8].getFill() - sendCost);
		tanks[9].setFill(tanks[9].getFill() - sendCost);

		hasRocket = false;
		((TileEntityTransporterRocket)linkedTransporter).hasRocket = true;
	}

	@Override
	protected void hasConnected(TileEntityTransporterBase linkedTransporter) {
		hasRocket = true;
		((TileEntityTransporterRocket)linkedTransporter).hasRocket = false;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerTransporterRocket(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITransporterRocket(player.inventory, this);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(hasRocket);
		buf.writeInt(threshold);
		super.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		hasRocket = buf.readBoolean();
		threshold = buf.readInt();
		super.deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		hasRocket = nbt.getBoolean("rocket");
		threshold = nbt.getInteger("threshold");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("rocket", hasRocket);
		nbt.setInteger("threshold", threshold);
	}

	private DirPos[] conPos;

	@Override
	protected DirPos[] getConPos() {
		if(conPos == null) {
			List<DirPos> list = new ArrayList<>();

			// Below
			for(int x = -1; x <= 1; x++) {
				for(int z = -1; z <= 1; z++) {
					list.add(new DirPos(xCoord + x, yCoord - 1, zCoord + z, Library.NEG_Y));
				}
			}

			// Sides
			for(int i = -1; i <= 1; i++) {
				list.add(new DirPos(xCoord + i, yCoord, zCoord + 2, Library.POS_Z));
				list.add(new DirPos(xCoord + i, yCoord, zCoord - 2, Library.NEG_Z));
				list.add(new DirPos(xCoord + 2, yCoord, zCoord + i, Library.POS_X));
				list.add(new DirPos(xCoord - 2, yCoord, zCoord + i, Library.NEG_X));
			}

			conPos = list.toArray(new DirPos[0]);
		}
		
		return conPos;
	}

	
	@Override
	public void receiveControl(NBTTagCompound nbt) {
		super.receiveControl(nbt);
		if(nbt.hasKey("threshold"))
			threshold = nbt.getInteger("threshold");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
    
}
