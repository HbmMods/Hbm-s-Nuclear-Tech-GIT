package api.hbm.item;

import java.util.List;

import com.hbm.util.ArmorRegistry.HazardClass;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IGasMask {

	public List<HazardClass> getBlacklist(ItemStack stack, EntityPlayer player);
	public ItemStack getFilter(ItemStack stack, EntityPlayer player);
	public void damageFilter(ItemStack stack, EntityPlayer player);
}
