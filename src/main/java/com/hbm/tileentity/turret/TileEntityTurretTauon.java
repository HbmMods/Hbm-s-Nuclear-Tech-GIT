package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.gui.GUITurretTauon;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.factory.XFactoryAccelerator;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretTauon extends TileEntityTurretBaseNT {

	static List<Integer> configs = new ArrayList();

	static {
		configs.add(XFactoryAccelerator.tau_uranium.id);
	}

	@Override
	protected List<Integer> getAmmoList() {
		return configs;
	}

	@Override
	public String getName() {
		return "container.turretTauon";
	}

	@Override
	public double getDecetorGrace() {
		return 3D;
	}

	@Override
	public double getTurretYawSpeed() {
		return 9D;
	}

	@Override
	public double getTurretPitchSpeed() {
		return 6D;
	}

	@Override
	public double getTurretElevation() {
		return 35D;
	}

	@Override
	public double getTurretDepression() {
		return 35D;
	}

	@Override
	public double getDecetorRange() {
		return 128D;
	}

	@Override
	public double getBarrelLength() {
		return 2.0D - 0.0625D;
	}

	@Override
	public long getMaxPower() {
		return 100000;
	}

	@Override
	public long getConsumption() {
		return 1000;
	}

	int timer;
	public int beam;
	public float spin;
	public float lastSpin;
	public double lastDist;

	@Override
	public void updateEntity() {

		if(worldObj.isRemote) {

			if(this.tPos != null) {
				Vec3 pos = this.getTurretPos();
				double length = Vec3.createVectorHelper(tPos.xCoord - pos.xCoord, tPos.yCoord - pos.yCoord, tPos.zCoord - pos.zCoord).lengthVector();
				this.lastDist = length;
			}

			if(beam > 0)
				beam--;

			this.lastSpin = this.spin;

			if(this.tPos != null) {
				this.spin += 45;
			}

			if(this.spin >= 360F) {
				this.spin -= 360F;
				this.lastSpin -= 360F;
			}
		}

		super.updateEntity();
	}

	@Override
	public void updateFiringTick() {

		timer++;

		if(timer % 5 == 0) {

			BulletConfig conf = this.getFirstConfigLoaded();

			if(conf != null && this.target != null) {
				this.target.attackEntityFrom(ModDamageSource.electricity, 30F + worldObj.rand.nextInt(11));
				this.conusmeAmmo(conf.ammo);
				this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:weapon.tauShoot", 4.0F, 0.9F + worldObj.rand.nextFloat() * 0.3F);

				this.shot = true;
				this.networkPackNT(250);
				this.shot = false;

				Vec3 pos = this.getTurretPos();
				Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
				vec.rotateAroundZ((float) -this.rotationPitch);
				vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));

				NBTTagCompound dPart = new NBTTagCompound();
				dPart.setString("type", "tau");
				dPart.setByte("count", (byte)5);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(dPart, pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			}
		}
	}

	private boolean shot = false;

	@Override
	public void serialize(ByteBuf buf) {
		if (this.shot)
			buf.writeBoolean(true);
		else {
			buf.writeBoolean(false);
			super.serialize(buf);
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		boolean shot = buf.readBoolean();
		if(shot)
			this.beam = 3;
		else
			super.deserialize(buf);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretTauon(player.inventory, this);
	}
}
