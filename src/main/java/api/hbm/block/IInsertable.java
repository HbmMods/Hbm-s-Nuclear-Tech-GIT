package api.hbm.block;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IInsertable { //uwu
	
	public boolean insertItem(World world, int x, int y, int z, ForgeDirection dir, ItemStack stack);
}
