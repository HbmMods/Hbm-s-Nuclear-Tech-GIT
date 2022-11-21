package com.hbm.inventory.transfer;

import java.util.List;

import net.minecraft.item.ItemStack;

public class TransferUtil {

	public static void transfer(ITransferSource source, ITransferFilter filter, ITransferTarget target) {
		
		List<ItemStack> filtered = filter.select(source.offer());
		source.remove(filtered);
		target.fill(filtered);
	}
}
