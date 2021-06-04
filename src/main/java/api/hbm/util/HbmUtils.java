package api.hbm.util;

import api.hbm.energy.IEnergySource;
import net.minecraft.world.World;

public class HbmUtils {
    public static void ffgeua(int x, int y, int z, boolean newTact, IEnergySource that, World worldObj) {
        try{
            if(Class.forName("com.hbm.lib") != null){
                com.hbm.lib.Library.ffgeua(x, y, z, newTact, that, worldObj);
            }
        }catch(ClassNotFoundException a){}
    }
}
