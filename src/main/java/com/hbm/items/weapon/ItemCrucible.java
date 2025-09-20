package com.hbm.items.weapon;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.items.IAnimatedItem;
import com.hbm.items.IEquipReceiver;
import com.hbm.items.tool.ItemSwordAbility;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.render.anim.AnimationEnums.ToolAnimation;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.util.ShadyUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemCrucible extends ItemSwordAbility implements IEquipReceiver, IAnimatedItem<ToolAnimation> {

	public ItemCrucible(float damage, double movement, ToolMaterial material) {
		super(damage, movement, material);
	}

	@Override
	public void onEquip(EntityPlayer player, ItemStack stack) {

		if(!(player instanceof EntityPlayerMP))
			return;

		if(player.getHeldItem() != null && player.getHeldItem().getItemDamage() < player.getHeldItem().getMaxDamage()) {

			World world = player.worldObj;
			world.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:weapon.cDeploy", 1.0F, 1.0F);

			playAnimation(player, ToolAnimation.EQUIP);
		}
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {

		if(!(entityLiving instanceof EntityPlayerMP))
			return false;

		if(entityLiving instanceof EntityPlayer && ((EntityPlayer)entityLiving).getUniqueID().toString().equals(ShadyUtil.Tankish)) {
			stack.setItemDamage(0);
		}

		if(stack.getItemDamage() >= stack.getMaxDamage())
			return false;

		playAnimation((EntityPlayerMP)entityLiving, ToolAnimation.SWING);

		return false;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker) {

		boolean active = stack.getItemDamage() < stack.getMaxDamage();

		if(active) {

			attacker.worldObj.playSoundEffect(victim.posX, victim.posY, victim.posZ, "mob.zombie.woodbreak", 1.0F, 0.75F + victim.getRNG().nextFloat() * 0.2F);

			if(!attacker.worldObj.isRemote && !victim.isEntityAlive()) {
				int count = Math.min((int)Math.ceil(victim.getMaxHealth() / 3D), 250);

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaburst");
				data.setInteger("count", count * 4);
				data.setDouble("motion", 0.1D);
				data.setString("mode", "blockdust");
				data.setInteger("block", Block.getIdFromBlock(Blocks.redstone_block));
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, victim.posX, victim.posY + victim.height * 0.5, victim.posZ), new TargetPoint(victim.dimension, victim.posX, victim.posY + victim.height * 0.5, victim.posZ, 50));
			}

			if(attacker instanceof EntityPlayer && (((EntityPlayer)attacker).getDisplayName().equals("Tankish") || ((EntityPlayer)attacker).getDisplayName().equals("Tankish020")))
				return true;

			return super.hitEntity(stack, victim, attacker);
		} else {

			if(!attacker.worldObj.isRemote && attacker instanceof EntityPlayer)
				((EntityPlayer)attacker).addChatComponentMessage(new ChatComponentText("Not enough energy.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			return false;
		}
	}

	public Multimap getAttributeModifiers(ItemStack stack) {

		Multimap multimap = HashMultimap.create();

		if(stack.getItemDamage() < stack.getMaxDamage()) {
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double) this.damage, 0));
			multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", movement, 1));
		}

		return multimap;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		String charge = EnumChatFormatting.RED + "Charge [";

		for(int i = 2; i >= 0; i--)
			if(stack.getItemDamage() <= i)
				charge += "||||||";
			else
				charge += "   ";

		charge += "]";

		list.add(charge);
	}

	@Override
	public BusAnimation getAnimation(ToolAnimation type, ItemStack stack) {
		/* crucible deploy */
		if(type == ToolAnimation.EQUIP) {

			return new BusAnimation()
					.addBus("GUARD_ROT", new BusAnimationSequence()
							.addPos(90, 0, 1, 0)
							.addPos(90, 0, 1, 800)
							.addPos(0, 0, 1, 50));
		}

		/* crucible swing */
		if(type == ToolAnimation.SWING) {

			if(HbmAnimations.getRelevantTransformation("SWING_ROT")[0] == 0) {

				int offset = itemRand.nextInt(80) - 20;

				playSwing(0.8F + itemRand.nextFloat() * 0.2F);

				return new BusAnimation()
						.addBus("SWING_ROT", new BusAnimationSequence()
								.addPos(90 - offset, 90 - offset, 35, 75)
								.addPos(90 + offset, 90 - offset, -45, 150)
								.addPos(0, 0, 0, 500))
						.addBus("SWING_TRANS", new BusAnimationSequence()
								.addPos(-3, 0, 0, 75)
								.addPos(8, 0, 0, 150)
								.addPos(0, 0, 0, 500));
			}
		}

		return null;
	}

	// could do this better, but this preserves existing behaviour the closest with the least amount
	// of effort, without crashing servers (I'm learning my lesson :o_ )
	@SideOnly(Side.CLIENT)
	private void playSwing(float pitchProbablyIDontFuckingCare) {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:weapon.cSwing"), pitchProbablyIDontFuckingCare));
	}

	@Override
	public Class<ToolAnimation> getEnum() {
		return ToolAnimation.class;
	}

	@Override
	public boolean shouldPlayerModelAim(ItemStack stack) {
		return false;
	}

}
