package com.hbm.handler.ae2;

import com.hbm.tileentity.machine.storage.TileEntityMassStorage;
import com.hbm.util.ItemStackUtil;

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
public class MassStorageMEInventory implements IMEInventory<IAEItemStack> {

    private TileEntityMassStorage tile;

    public MassStorageMEInventory(TileEntityMassStorage tile) {
        this.tile = tile;
    }

    @Override
    public IAEItemStack injectItems(IAEItemStack input, Actionable type, BaseActionSource src) {
        ItemStack typeStack = tile.getType();

        if (typeStack == null || !ItemStackUtil.areStacksCompatible(input.getItemStack(), typeStack))
            return input;
        
        // If you're working with amounts greater than MAX_INT, you shouldn't use MSUs in the first place
        int remaining = tile.increaseTotalStockpile((int)input.getStackSize(), type == Actionable.MODULATE);
    
        if (remaining == 0) {
            return null;
        }
        
        return AEApi.instance().storage()
            .createItemStack(typeStack)
            .setStackSize(remaining);
    }

    @Override
    public IAEItemStack extractItems(IAEItemStack request, Actionable mode, BaseActionSource src) {
        ItemStack typeStack = tile.getType();

        if (typeStack == null || !ItemStackUtil.areStacksCompatible(request.getItemStack(), typeStack))
            return null;

        // If you're working with amounts greater than MAX_INT, you shouldn't use MSUs in the first place
        int missing = tile.decreaseTotalStockpile((int)request.getStackSize(), mode == Actionable.MODULATE);
        long fulfilled = request.getStackSize() - missing;

        if (fulfilled == 0) {
            return null;
        }

        return AEApi.instance().storage()
            .createItemStack(typeStack)
            .setStackSize(fulfilled);
    }

    @Override
    public IItemList<IAEItemStack> getAvailableItems(IItemList<IAEItemStack> out) {
        ItemStack typeStack = tile.getType();

        if (typeStack != null) {
            out.add(
                AEApi.instance().storage()
                .createItemStack(typeStack)
                .setStackSize(tile.getTotalStockpile())
            );
        }

        return out;
    }

    @Override
    public StorageChannel getChannel() {
        return StorageChannel.ITEMS;
    }
    
}
