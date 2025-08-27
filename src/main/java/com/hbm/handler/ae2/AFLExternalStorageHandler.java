package com.hbm.handler.ae2;

import com.hbm.tileentity.machine.TileEntityMachineArcFurnaceLarge;
import com.hbm.tileentity.TileEntityProxyCombo;
import cpw.mods.fml.common.Optional;

import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IExternalStorageHandler;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.StorageChannel;
import appeng.me.storage.MEMonitorIInventory;
import appeng.util.inv.IMEAdaptor;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "appeng.api.storage.IExternalStorageHandler", modid = "appliedenergistics2")})
public class AFLExternalStorageHandler implements IExternalStorageHandler {

    public AFLExternalStorageHandler() {}

    @Override
    public boolean canHandle(TileEntity te, ForgeDirection d, StorageChannel channel, BaseActionSource mySrc) {
		boolean coreProxy = te instanceof TileEntityProxyCombo && ((TileEntityProxyCombo) te).getTile() instanceof TileEntityMachineArcFurnaceLarge;
        return channel == StorageChannel.ITEMS && (te instanceof TileEntityMachineArcFurnaceLarge || coreProxy);
    }

    @Override
    public IMEInventory getInventory(TileEntity te, ForgeDirection d, StorageChannel channel, BaseActionSource src) {
        if (!canHandle(te, d, channel, src)) return null;
		if (te instanceof TileEntityProxyCombo) return new MEMonitorIInventory(new IMEAdaptor(new ArcFurnaceLargeMEInventory((TileEntityMachineArcFurnaceLarge) ((TileEntityProxyCombo)te).getTile()), src)) {};
		return new MEMonitorIInventory(new IMEAdaptor(new ArcFurnaceLargeMEInventory((TileEntityMachineArcFurnaceLarge) te), src)) {};
    }

}
