
package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityMissileShuttle extends EntityMissileBaseNT {

	public EntityMissileShuttle(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileShuttle(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		ExplosionNT explosion = new ExplosionNT(worldObj, null, this.posX + 0.5F, this.posY + 0.5F, this.posZ + 0.5F, 20.0F).overrideResolution(64);
		explosion.atttributes.add(ExAttrib.NOSOUND);
		explosion.atttributes.add(ExAttrib.NOPARTICLE);
		explosion.explode();
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "rbmkmush");
		data.setFloat("scale", 10);
		PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, this.posX + 0.5, this.posY + 1, this.posZ + 0.5), new TargetPoint(worldObj.provider.dimensionId,this.posX + 0.5, this.posY + 1, this.posZ + 0.5, 250));
		MainRegistry.proxy.effectNT(data);

		this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:weapon.robin_explosion", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_steel, 8));
		list.add(new ItemStack(ModItems.thruster_medium, 2));
		list.add(new ItemStack(ModItems.canister_empty, 1));
		list.add(new ItemStack(Blocks.glass_pane, 2));

		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.missile_generic);
	}

	@Override
	public String getUnlocalizedName() {
		return "radar.target.shuttle";
	}

	@Override
	public ItemStack getMissileItemForInfo() {
		return new ItemStack(ModItems.missile_shuttle);
	}
}
