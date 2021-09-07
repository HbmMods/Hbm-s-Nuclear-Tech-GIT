package api.hbm.item;

import java.util.List;

import com.hbm.util.ArmorRegistry.HazardClass;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IGasMask {

	/**
	 * Returns a list of HazardClasses which can not be protected against by this mask (e.g. chlorine gas for half masks)
	 * @param stack
	 * @param player
	 * @return an empty list if there's no blacklist
	 */
	public List<HazardClass> getBlacklist(ItemStack stack, EntityPlayer player);
	
	/**
	 * Returns the loaded filter, if there is any
	 * @param stack
	 * @param player
	 * @return null if no filter is installed
	 */
	public ItemStack getFilter(ItemStack stack, EntityPlayer player);
	
	/**
	 * Checks whether the provided filter can be screwed into the mask, does not take already applied filters into account (those get ejected)
	 * @param stack
	 * @param player
	 * @param filter
	 * @return
	 */
	public boolean isFilterApplicable(ItemStack stack, EntityPlayer player, ItemStack filter);
	
	/**
	 * This will write the filter to the stack's NBT, it ignores any previously installed filter and won't eject those
	 * @param stack
	 * @param player
	 * @param filter
	 */
	public void installFilter(ItemStack stack, EntityPlayer player, ItemStack filter);
	
	/**
	 * Damages the installed filter, if there is one
	 * @param stack
	 * @param player
	 * @param damage
	 */
	public void damageFilter(ItemStack stack, EntityPlayer player, int damage);
}
