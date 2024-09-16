package com.hbm.items.tool;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.hbm.config.GeneralConfig;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.IBomb.BombReturnCode;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.PlayerInformPacket;
import com.hbm.util.ChatBuilder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemLaserDetonator extends Item implements IHoldableWeapon {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add("Aim & click to detonate!");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		MovingObjectPosition pos = Library.rayTrace(player, 500, 1);
		int x = pos.blockX;
		int y = pos.blockY;
		int z = pos.blockZ;

		if(!world.isRemote) {
			if(world.getBlock(x, y, z) instanceof IBomb) {
				BombReturnCode ret = ((IBomb) world.getBlock(x, y, z)).explode(world, x, y, z);

				if(GeneralConfig.enableExtendedLogging)
					MainRegistry.logger.log(Level.INFO, "[DET] Tried to detonate block at " + x + " / " + y + " / " + z + " by " + player.getDisplayName() + "!");
				
				world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
				PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("").nextTranslation(ret.getUnlocalizedMessage()).color(ret.wasSuccessful() ? EnumChatFormatting.YELLOW : EnumChatFormatting.RED).flush(), MainRegistry.proxy.ID_DETONATOR), (EntityPlayerMP) player);
				
			} else {
				world.playSoundAtEntity(player, "hbm:item.techBoop", 1.0F, 1.0F);
				PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("").nextTranslation(BombReturnCode.ERROR_NO_BOMB.getUnlocalizedMessage()).color(EnumChatFormatting.RED).flush(), MainRegistry.proxy.ID_DETONATOR), (EntityPlayerMP) player);
			}
		} else {
			
			Vec3 vec = Vec3.createVectorHelper(x + 0.5 - player.posX, y + 0.5 - player.posY, z + 0.5 - player.posZ);
			double len = Math.min(vec.lengthVector(), 15D);
			vec = vec.normalize();
			
			for(int i = 0; i < len; i++) {
				double rand = world.rand.nextDouble() * len + 3;
				world.spawnParticle("reddust", player.posX + vec.xCoord * rand, player.posY + vec.yCoord * rand, player.posZ + vec.zCoord * rand, 0, 0, 0);
			}
		}

		return stack;
	}

	@Override
	public Crosshair getCrosshair() {
		return Crosshair.L_ARROWS;
	}
}
