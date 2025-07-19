package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Liquid;
import com.hbm.inventory.gui.GUITurretFritz;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.factory.XFactoryFlamer;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import api.hbm.fluid.IFluidStandardReceiver;
import com.hbm.tileentity.IFluidCopiable;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTurretFritz extends TileEntityTurretBaseNT implements IFluidStandardReceiver, IFluidCopiable {

	public FluidTank tank;

	public TileEntityTurretFritz() {
		super();
		this.tank = new FluidTank(Fluids.DIESEL, 16000);
	}

	@Override
	public String getName() {
		return "container.turretFritz";
	}

	@Override
	protected List<Integer> getAmmoList() {
		return null;
	}

	@SideOnly(Side.CLIENT)
	public List<ItemStack> getAmmoTypesForDisplay() {

		if(ammoStacks != null)
			return ammoStacks;

		ammoStacks = new ArrayList();

		ammoStacks.add(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.FLAME_DIESEL.ordinal()));

		for(FluidType type : Fluids.getInNiceOrder()) {
			if(type.hasTrait(FT_Combustible.class) && type.hasTrait(FT_Liquid.class)) {
				ammoStacks.add(new ItemStack(ModItems.fluid_icon, 1, type.getID()));
			}
		}

		return ammoStacks;
	}

	@Override
	public double getDecetorRange() {
		return 32D;
	}

	@Override
	public double getDecetorGrace() {
		return 2D;
	}

	@Override
	public double getTurretElevation() {
		return 45D;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}

	@Override
	public double getBarrelLength() {
		return 2.25D;
	}

	@Override
	public double getAcceptableInaccuracy() {
		return 15;
	}

	@Override
	public void updateFiringTick() {

		if(this.tank.getTankType().hasTrait(FT_Flammable.class) && this.tank.getTankType().hasTrait(FT_Liquid.class) && this.tank.getFill() >= 2) {

			FT_Flammable trait = this.tank.getTankType().getTrait(FT_Flammable.class);
			this.tank.setFill(this.tank.getFill() - 2);

			Vec3 pos = this.getTurretPos();
			Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
			vec.rotateAroundZ((float) -this.rotationPitch);
			vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));

			EntityBulletBaseMK4 proj = new EntityBulletBaseMK4(worldObj, XFactoryFlamer.flame_nograv, (float) (trait.getHeatEnergy() / 500_000F), 0.05F, (float) rotationYaw, (float) rotationPitch);
			proj.setPositionAndRotation(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord, proj.rotationYaw, proj.rotationPitch);
			worldObj.spawnEntityInWorld(proj);

			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:weapon.flamethrowerShoot", 2F, 1F + worldObj.rand.nextFloat() * 0.5F);

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaburst");
			data.setString("mode", "flame");
			data.setInteger("count", 2);
			data.setDouble("motion", 0.025D);
			PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
	}

	public int getDelay() {
		return 2;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if(!worldObj.isRemote) {
			tank.setType(9, 9, slots);
			tank.loadTank(0, 1, slots);

			for(int i = 1; i < 10; i++) {

				if(slots[i] != null && slots[i].getItem() == ModItems.ammo_standard && slots[i].getItemDamage() == EnumAmmo.FLAME_DIESEL.ordinal()) {
					if(this.tank.getTankType() == Fluids.DIESEL && this.tank.getFill() + 1000 <= this.tank.getMaxFill()) {
						this.tank.setFill(this.tank.getFill() + 1000);
						this.decrStackSize(i, 1);
					}
				}
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		tank.deserialize(buf);
	}

	@Override //TODO: clean this shit up
	protected void updateConnections() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.trySubscribe(worldObj, xCoord + dir.offsetX * -1 + rot.offsetX * 0, yCoord, zCoord + dir.offsetZ * -1 + rot.offsetZ * 0, dir.getOpposite());
		this.trySubscribe(worldObj, xCoord + dir.offsetX * -1 + rot.offsetX * -1, yCoord, zCoord + dir.offsetZ * -1 + rot.offsetZ * -1, dir.getOpposite());
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 0 + rot.offsetX * -2, yCoord, zCoord + dir.offsetZ * 0 + rot.offsetZ * -2, rot.getOpposite());
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 1 + rot.offsetX * -2, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * -2, rot.getOpposite());
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 0 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 0 + rot.offsetZ * 1, rot);
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 1 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * 1, rot);
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * 0, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * 0, dir);
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * -1, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * -1, dir);

		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * -1 + rot.offsetX * 0, yCoord, zCoord + dir.offsetZ * -1 + rot.offsetZ * 0, dir.getOpposite());
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * -1 + rot.offsetX * -1, yCoord, zCoord + dir.offsetZ * -1 + rot.offsetZ * -1, dir.getOpposite());
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * 0 + rot.offsetX * -2, yCoord, zCoord + dir.offsetZ * 0 + rot.offsetZ * -2, rot.getOpposite());
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * 1 + rot.offsetX * -2, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * -2, rot.getOpposite());
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * 0 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 0 + rot.offsetZ * 1, rot);
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * 1 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * 1, rot);
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * 0, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * 0, dir);
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * -1, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * -1, dir);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt, "diesel");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tank.writeToNBT(nbt, "diesel");
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UP && dir != ForgeDirection.DOWN;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretFritz(player.inventory, this);
	}

	@Override
	public FluidTank getTankToPaste() {
		return tank;
	}
}
