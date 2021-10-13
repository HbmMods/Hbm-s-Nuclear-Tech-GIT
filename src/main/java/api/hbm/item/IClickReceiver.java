package api.hbm.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IClickReceiver {

	/**
	 * Called in ModEventHandlerClient for any mouse input related to this item
	 * @param stack
	 * @param player
	 * @param button
	 * @param state
	 * @return true if the event should be canceled
	 */
	@SideOnly(Side.CLIENT)
	public boolean handleMouseInput(ItemStack stack, EntityPlayer player, int button, boolean state);
}
