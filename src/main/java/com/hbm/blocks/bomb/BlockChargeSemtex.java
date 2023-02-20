package com.hbm.blocks.bomb;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.hbm.config.GeneralConfig;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.ExplosionEffectStandard;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class BlockChargeSemtex extends BlockChargeBase {

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {
		
		if(!world.isRemote) {
			safe = true;
			world.setBlockToAir(x, y, z);
			safe = false;
			
			ExplosionVNT xnt = new ExplosionVNT(world, x + 0.5, y + 0.5, z + 0.5, 10F);
			xnt.setBlockAllocator(new BlockAllocatorStandard(32));
			xnt.setBlockProcessor(new BlockProcessorStandard()
					.setAllDrop()
					.setFortune(3));
			xnt.setSFX(new ExplosionEffectStandard());
			xnt.explode();
			
			return BombReturnCode.DETONATED;
		}
		
		return BombReturnCode.UNDEFINED;
	}

	@Override
	public int getRenderType() {
		return BlockChargeC4.renderID;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		super.addInformation(stack, player, list, ext);
		list.add(EnumChatFormatting.BLUE + "Will drop all blocks.");
		list.add(EnumChatFormatting.BLUE + "Does not do damage.");
		list.add(EnumChatFormatting.BLUE + "");
		list.add(EnumChatFormatting.LIGHT_PURPLE + "Fortune III");
	}
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
	if(!world.isRemote) {
			if(GeneralConfig.enableExtendedLogging) {
			MainRegistry.logger.log(Level.INFO, "[BOMBPL]" + this.getLocalizedName() + " placed at " + x + " / " + y + " / " + z + "! " + "by "+ player.getCommandSenderName());
		}	
	}
	}
}
