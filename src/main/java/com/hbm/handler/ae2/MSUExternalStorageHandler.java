package com.hbm.handler.ae2;

import com.hbm.tileentity.machine.storage.TileEntityMassStorage;
import com.hbm.util.ItemStackUtil;
import cpw.mods.fml.common.Optional;

import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IExternalStorageHandler;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.me.storage.MEMonitorIInventory;
import appeng.util.inv.IMEAdaptor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "appeng.api.storage.IExternalStorageHandler", modid = "appliedenergistics2")})
public class MSUExternalStorageHandler implements IExternalStorageHandler {

    public MSUExternalStorageHandler() {}

    @Override
    public boolean canHandle(TileEntity te, ForgeDirection d, StorageChannel channel, BaseActionSource mySrc) {
        return channel == StorageChannel.ITEMS && te instanceof TileEntityMassStorage;
    }

    @Override
    public IMEInventory getInventory(TileEntity te, ForgeDirection d, StorageChannel channel, BaseActionSource src) {
        if (!canHandle(te, d, channel, src))
            return null;
        
        // Note: apparently I need this, though I'm not sure why. Storage drawers does it.
        // Here's a relevant discussion, if anyone wants to dive into that rabbit hole:
        // https://github.com/AppliedEnergistics/Applied-Energistics-2/issues/418
        return new MEMonitorIInventory(new IMEAdaptor(new MassStorageMEInventory((TileEntityMassStorage)te), src)) {
            @Override
            public boolean isPrioritized(IAEItemStack stack) {
                ItemStack type = ((TileEntityMassStorage)te).getType();

                return type != null && ItemStackUtil.areStacksCompatible(stack.getItemStack(), type);
            }
        };
    }
    
}
