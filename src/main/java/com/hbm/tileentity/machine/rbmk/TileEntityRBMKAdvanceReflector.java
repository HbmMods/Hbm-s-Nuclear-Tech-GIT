package com.hbm.tileentity.machine.rbmk;

import com.hbm.entity.projectile.EntityRBMKDebris;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityRBMKAdvanceReflector extends TileEntityRBMKBase {
    protected int setting = 0;
    //protected int duration = 0;
    protected String[] mode = new String[]{"NORMAL","NORTH","EAST","SOUTH","WEST"};
    public void modeSetting() {
        if(++setting > 4) {
            setting = 1;
        }
    }
    @Override
    public void onMelt(int reduce) {

        int count = 1 + worldObj.rand.nextInt(2);

        for(int i = 0; i < count; i++) {
            spawnDebris(EntityRBMKDebris.DebrisType.BLANK);
        }

        super.onMelt(reduce);
    }

    @Override
    public TileEntityRBMKConsole.ColumnType getConsoleType() {
        return TileEntityRBMKConsole.ColumnType.REFLECTOR;
    }
    //public int getSetting(){ return this.setting; }

    @Override
    public void readFromNBT(NBTTagCompound nbt){
        super.readFromNBT(nbt);
        //this.setting = nbt.getInteger("setting");
        this.mode[setting] = nbt.getString("mode");
    }
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        //nbt.setInteger("setting", this.setting);
        nbt.setString("mode", mode[setting]);
		/*if(this.setting == 0){
			nbt.setString("mode", "NORMAL");
		}*/
    }
}
