package com.hbm.handler.ae2;

import com.hbm.tileentity.machine.TileEntityMachineArcFurnaceLarge;
import com.hbm.tileentity.TileEntityProxyCombo;

import cpw.mods.fml.common.Optional;

import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;
import net.minecraft.item.ItemStack;

@Optional.InterfaceList({@Optional.Interface(iface = "appeng.api.storage.IMEInventory", modid = "appliedenergistics2")})
public class ArcFurnaceLargeMEInventory implements IMEInventory<IAEItemStack> {

    private TileEntityMachineArcFurnaceLarge afl;

    public ArcFurnaceLargeMEInventory(TileEntityMachineArcFurnaceLarge afl) {
        this.afl = afl;
    }

    @Override
    public IAEItemStack injectItems(IAEItemStack input, Actionable type, BaseActionSource src) {
        ItemStack is = input.getItemStack();
        is = afl.distributeInput(is, type == Actionable.MODULATE);

		if(is == null) return null;
        return AEApi.instance().storage().createItemStack(is);
    }

    @Override
    public IAEItemStack extractItems(IAEItemStack request, Actionable mode, BaseActionSource src) {
		ItemStack is = request.getItemStack();
		is = afl.collectRequested(is, mode == Actionable.MODULATE);

		if(is == null) return null;
		return AEApi.instance().storage().createItemStack(is);
    }

    @Override
    public IItemList<IAEItemStack> getAvailableItems(IItemList<IAEItemStack> out) {
        ItemStack is;
		for(int i = 0; i < 25; i++) {
			is = afl.getAvailableItemFromSlot(i);
			if(is != null) out.add(AEApi.instance().storage().createItemStack(is));
		}

        return out;
    }

    @Override
    public StorageChannel getChannel() {
        return StorageChannel.ITEMS;
    }

}
