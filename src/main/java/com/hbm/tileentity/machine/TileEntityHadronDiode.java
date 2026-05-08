package com.hbm.tileentity.machine;
import net.minecraft.tileentity.TileEntity;
public class TileEntityHadronDiode extends TileEntity {
public enum DiodeConfig { IN, OUT }
public final DiodeConfig[] sides = new DiodeConfig[6];
}
