package api.hbm.item;

import java.util.List;

import com.hbm.util.Tuple.Pair;
import com.hbm.util.Tuple.Triplet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface IGunHUDProvider {

	/**
	 * Gets the progress for the status bar right of the toolbar. Usually reserved for durability.
	 * It's now a list! More bars for stuff like overheating and special charges!
	 * @param stack
	 * @param player
	 * @return a list of triplets holding progress, foreground and background color.
	 */
	public List<Triplet<Double, Integer, Integer>> getStatusBars(ItemStack stack, EntityPlayer player);
	
	/**
	 * Gets a list of any size containing a preview icon for loaded ammo as well as text indicating the ammo count.
	 * Can also be used for any kind of tooltip or maybe a built-in compass of sorts.
	 * @param stack
	 * @param player
	 * @return
	 */
	public List<Pair<IIcon, String>> getAmmoInfo(ItemStack stack, EntityPlayer player);
}
