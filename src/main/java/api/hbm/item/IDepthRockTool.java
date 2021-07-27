package api.hbm.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IDepthRockTool {

	public boolean canBreakRock(World world, EntityPlayer player, ItemStack tool, Block block, int x, int y, int z);
}
