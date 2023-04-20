package com.hbm.items.tool;

import com.hbm.items.IAnimatedItem;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;

import api.hbm.block.IToolable;
import api.hbm.block.IToolable.ToolType;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemBoltgun extends Item implements IAnimatedItem {

	public ItemBoltgun() {
		this.setMaxStackSize(1);
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		super.setUnlocalizedName(unlocalizedName);
		this.setTextureName(RefStrings.MODID + ":"+ unlocalizedName);
		return this;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fX, float fY, float fZ) {
		
		Block b = world.getBlock(x, y, z);
		
		if(b instanceof IToolable && ((IToolable)b).onScrew(world, player, x, y, z, side, fX, fY, fZ, ToolType.BOLT)) {

			if(!world.isRemote) {
				
				player.inventoryContainer.detectAndSendChanges();
				ForgeDirection dir = ForgeDirection.getOrientation(side);
				double off = 0.25;

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 1F);
				data.setByte("count", (byte)1);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x + fX + dir.offsetX * off, y + fY + dir.offsetY * off, z + fZ + dir.offsetZ * off), new TargetPoint(world.provider.dimensionId, x, y, z, 50));

				NBTTagCompound d0 = new NBTTagCompound();
				d0.setString("type", "anim");
				d0.setString("mode", "generic");
				PacketDispatcher.wrapper.sendTo(new AuxParticlePacketNT(d0, 0, 0, 0), (EntityPlayerMP) player);
			}
			
			return false;
		}
		
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BusAnimation getAnimation(NBTTagCompound data, ItemStack stack) {
		return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(1, 0, 1, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 1, 100)));
	}
}
