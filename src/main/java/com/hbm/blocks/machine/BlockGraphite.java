package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockFlammable;
import com.hbm.items.ModItems;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.ParticleBurstPacket;

import api.hbm.block.IToolable;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGraphite extends BlockFlammable implements IToolable {

	public BlockGraphite(Material mat, int en, int flam) {
		super(mat, en, flam);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool != ToolType.HAND_DRILL)
			return false;
		
		if(!world.isRemote) {
			world.setBlock(x, y, z, ModBlocks.block_graphite_drilled);
			PacketDispatcher.wrapper.sendToAllAround(new ParticleBurstPacket(x, y, z, Block.getIdFromBlock(this), 0), new TargetPoint(world.provider.dimensionId, x, y, z, 50));
			world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, this.stepSound.func_150496_b(), (this.stepSound.getVolume() + 1.0F) / 2.0F, this.stepSound.getPitch() * 0.8F);
			
			ForgeDirection dir = ForgeDirection.getOrientation(side);
			EntityItem dust = new EntityItem(world, x + 0.5D + dir.offsetX * 0.75D, y + 0.5D + dir.offsetY * 0.75D, z + 0.5D + dir.offsetZ * 0.75D, new ItemStack(ModItems.powder_coal));
			dust.motionX = dir.offsetX * 0.25;
			dust.motionY = dir.offsetY * 0.25;
			dust.motionZ = dir.offsetZ * 0.25;
			world.spawnEntityInWorld(dust);
		}
		
		return true;
	}

}
