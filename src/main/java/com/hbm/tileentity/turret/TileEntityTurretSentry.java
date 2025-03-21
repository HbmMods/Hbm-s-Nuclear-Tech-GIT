package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.CasingEjector;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.container.ContainerTurretBase;
import com.hbm.inventory.gui.GUITurretSentry;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.factory.XFactory9mm;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTurretSentry extends TileEntityTurretBaseNT implements IGUIProvider {

	protected boolean didJustShootLeft = false;
	protected boolean retractingLeft = false;
	public double barrelLeftPos = 0;
	public double lastBarrelLeftPos = 0;
	protected boolean didJustShootRight = false;
	protected boolean retractingRight = false;
	public double barrelRightPos = 0;
	public double lastBarrelRightPos = 0;

	static List<Integer> configs = new ArrayList();

	static {
		configs.add(XFactory9mm.p9_sp.id);
		configs.add(XFactory9mm.p9_fmj.id);
		configs.add(XFactory9mm.p9_jhp.id);
		configs.add(XFactory9mm.p9_ap.id);
	}

	@Override
	protected List<Integer> getAmmoList() {
		return configs;
	}

	@Override
	public String getName() {
		return "container.turretSentry";
	}

	@Override
	public double getTurretDepression() {
		return 20D;
	}

	@Override
	public double getTurretElevation() {
		return 20D;
	}

	@Override
	public int getDecetorInterval() {
		return 10;
	}

	@Override
	public double getDecetorRange() {
		return 24D;
	}

	@Override
	public double getDecetorGrace() {
		return 2D;
	}

	@Override
	public long getMaxPower() {
		return 1_000;
	}

	@Override
	public long getConsumption() {
		return 5;
	}

	@Override
	public double getBarrelLength() {
		return 1.25D;
	}

	@Override
	public double getAcceptableInaccuracy() {
		return 15;
	}

	@Override
	public boolean hasThermalVision() {
		return false;
	}

	@Override
	public Vec3 getHorizontalOffset() {
		return Vec3.createVectorHelper(0.5, 0, 0.5);
	}

	@Override
	public void updateEntity() {

		if(worldObj.isRemote) {
			this.lastBarrelLeftPos = this.barrelLeftPos;
			this.lastBarrelRightPos = this.barrelRightPos;

			float retractSpeed = 0.5F;
			float pushSpeed = 0.25F;

			if(this.retractingLeft) {
				this.barrelLeftPos += retractSpeed;

				if(this.barrelLeftPos >= 1) {
					this.retractingLeft = false;
				}

			} else {
				this.barrelLeftPos -= pushSpeed;
				if(this.barrelLeftPos < 0) {
					this.barrelLeftPos = 0;
				}
			}

			if(this.retractingRight) {
				this.barrelRightPos += retractSpeed;

				if(this.barrelRightPos >= 1) {
					this.retractingRight = false;
				}

			} else {
				this.barrelRightPos -= pushSpeed;
				if(this.barrelRightPos < 0) {
					this.barrelRightPos = 0;
				}
			}
		}

		super.updateEntity();
	}

	boolean shotSide = false;
	int timer;

	@Override
	public void updateFiringTick() {

		timer++;

		if(timer % 10 == 0) {

			BulletConfig conf = this.getFirstConfigLoaded();

			if(conf != null) {
				this.cachedCasingConfig = conf.casing;
				this.spawnBullet(conf, 5F);
				this.conusmeAmmo(conf.ammo);
				this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.sentry_fire", 2.0F, 1.0F);

				Vec3 pos = this.getTurretPos();
				Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
				vec.rotateAroundZ((float) -this.rotationPitch);
				vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));

				Vec3 side = Vec3.createVectorHelper(0.125 * (shotSide ? 1 : -1), 0, 0);
				side.rotateAroundY((float) -(this.rotationYaw));

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 1F);
				data.setByte("count", (byte)1);
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord + side.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord + side.zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));

				if(shotSide) {
					this.didJustShootLeft = true;
				} else {
					this.didJustShootRight = true;
				}
				shotSide = !shotSide;
			}
		}
	}

	@Override
	protected Vec3 getCasingSpawnPos() {

		Vec3 pos = this.getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(0, 0.25,-0.125);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));

		return Vec3.createVectorHelper(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord);
	}

	protected static CasingEjector ejector = new CasingEjector().setMotion(-0.3, 0.6, 0).setAngleRange(0.01F, 0.01F);

	@Override
	protected CasingEjector getEjector() {
		return ejector.setMotion(0.3, 0.6, 0);
	}

	@Override
	public boolean usesCasings() {
		return true;
	}

	@Override
	protected void seekNewTarget() {
		Entity lastTarget = this.target;
		super.seekNewTarget();

		if(lastTarget != this.target && this.target != null) {
			worldObj.playSoundAtEntity(target, "hbm:turret.sentry_lockon", 2.0F, 1.5F);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(didJustShootLeft);
		buf.writeBoolean(didJustShootRight);
		didJustShootLeft = false;
		didJustShootRight = false;
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.retractingLeft = buf.readBoolean();
		this.retractingRight = buf.readBoolean();
	}

	protected void updateConnections() {
		this.trySubscribe(worldObj, xCoord, yCoord - 1, zCoord, ForgeDirection.DOWN);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerTurretBase(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretSentry(player.inventory, this);
	}
}
