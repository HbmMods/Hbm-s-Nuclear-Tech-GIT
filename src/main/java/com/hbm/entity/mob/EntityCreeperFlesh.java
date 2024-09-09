package com.hbm.entity.mob;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorBulkie;
import com.hbm.explosion.vanillant.standard.BlockMutatorBulkie;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorStandard;
import com.hbm.explosion.vanillant.standard.ExplosionEffectStandard;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityCreeperFlesh extends EntityCreeper {

	public EntityCreeperFlesh(World world) {
		super(world);
	}

	@Override
	public void func_146077_cc() {
		if(!this.worldObj.isRemote) {
			NBTTagCompound vdat = new NBTTagCompound();
			vdat.setString("type", "giblets");
			vdat.setInteger("ent", getEntityId());
			vdat.setInteger("cDiv", 2);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(vdat, posX, posY + height * 0.5, posZ), new TargetPoint(dimension, posX, posY + height * 0.5, posZ, 150));

			this.setDead();
			
			ExplosionVNT vnt = new ExplosionVNT(worldObj, posX, posY, posZ, this.getPowered() ? 7 : 4, this);
			vnt.setBlockAllocator(new BlockAllocatorBulkie(30, this.getPowered() ? 16 : 8));
			vnt.setBlockProcessor(new BlockProcessorStandard().withBlockEffect(new BlockMutatorBulkie(ModBlocks.tumor)));
			vnt.setEntityProcessor(new EntityProcessorStandard().withRangeMod(0.25F));
			vnt.setPlayerProcessor(new PlayerProcessorStandard());
			vnt.setSFX(new ExplosionEffectStandard());
			vnt.explode();
		}
	}
	
}
