package api.hbm.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IButtonReceiver {

	/**
	 * Called in ModEventHandlerClient for any keyboard input related to this item
	 * @param stack
	 * @param player
	 */
	@SideOnly(Side.CLIENT)
	public void handleKeyboardInput(ItemStack stack, EntityPlayer player);
}
