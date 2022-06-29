package api.hbm.ntl;

import net.minecraft.item.ItemStack;

public interface IStorageComponent {

	/**
	 * @return The type of storage this tile entity represents.
	 */
	public EnumStorageType getType();
	
	/**
	 * @return A StorageManifest instance containing all managed stacks 
	 */
	public StorageManifest getManifest();
	
	/**
	 * @return An integer representing the version of the manifest. The higher the numberm, the more recent the manifest
	 * (i.e. always count up), the version has to change every time the manifest updates.
	 */
	public int getManifestVersion();
	
	/**
	 * @param stack The stack to be stored
	 * @param simulate Whether the changes should actually be written or if the operation is only for checking
	 * @return The remainder of the stack after being stored, null if nothing remains
	 */
	public ItemStack storeStack(ItemStack stack, boolean simulate);
}
