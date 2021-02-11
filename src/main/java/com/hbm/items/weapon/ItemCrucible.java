package com.hbm.items.weapon;

import com.hbm.items.IEquipReceiver;
import com.hbm.items.tool.ItemSwordAbility;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemCrucible extends ItemSwordAbility implements IEquipReceiver {

	public ItemCrucible(float damage, double movement, ToolMaterial material) {
		super(damage, movement, material);
	}

	@Override
	public void onEquip(EntityPlayer player) {
		
		if(!(player instanceof EntityPlayerMP))
			return;
		
		World world = player.worldObj;
		world.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:weapon.cDeploy", 1.0F, 1.0F);
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("type", "anim");
		nbt.setString("mode", "crucible");
		PacketDispatcher.wrapper.sendTo(new AuxParticlePacketNT(nbt, 0, 0, 0), (EntityPlayerMP)player);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		
		if(!(entityLiving instanceof EntityPlayerMP))
			return false;
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("type", "anim");
		nbt.setString("mode", "cSwing");
		PacketDispatcher.wrapper.sendTo(new AuxParticlePacketNT(nbt, 0, 0, 0), (EntityPlayerMP)entityLiving);
		
		return false;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker) {

		attacker.worldObj.playSoundEffect(victim.posX, victim.posY, victim.posZ, "mob.zombie.woodbreak", 1.0F, 0.75F + victim.getRNG().nextFloat() * 0.2F);
		
		if(!attacker.worldObj.isRemote && !victim.isEntityAlive()) {
			int count = Math.min((int)Math.ceil(victim.getMaxHealth() / 3D), 250);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaburst");
			data.setInteger("count", count * 4);
			data.setDouble("motion", 0.1D);
			data.setString("mode", "blockdust");
			data.setInteger("block", Block.getIdFromBlock(Blocks.redstone_block));
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, victim.posX, victim.posY + victim.height * 0.5, victim.posZ), new TargetPoint(victim.dimension, victim.posX, victim.posY + victim.height * 0.5, victim.posZ, 50));
		}
		
		return super.hitEntity(stack, victim, attacker);
	}

}
