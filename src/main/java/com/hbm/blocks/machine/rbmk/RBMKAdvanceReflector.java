package com.hbm.blocks.machine.rbmk;

import com.hbm.config.GeneralConfig;
import com.hbm.inventory.RecipesCommon;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKAdvanceReflector;
import com.hbm.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RBMKAdvanceReflector extends RBMKBase  {
    public String[] names;
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        if(meta >= this.offset)
            return new TileEntityRBMKAdvanceReflector();
        return null;
    }

    @Override
    public int getRenderType(){
        return this.renderIDPassive;
    }
    @Override
    public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {

        if(tool != ToolType.SCREWDRIVER && tool != ToolType.HAND_DRILL && !GeneralConfig.enableAdjustableNeutronReflector)
            return false;

        if(world.isRemote) return true;

        int[] pos = this.findCore(world, x, y, z);

        if(pos == null) return false;

        TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

        if(!(te instanceof TileEntityRBMKAdvanceReflector)) return false;

        TileEntityRBMKAdvanceReflector tile = (TileEntityRBMKAdvanceReflector) te;
        /*settings=tile.getSetting();
        duration=tile.getDuration();
        List<RecipesCommon.AStack> list = new ArrayList();
        for(RecipesCommon.AStack stack : new RecipesCommon.AStack[] {new RecipesCommon.ComparableStack(ModItems.plate_paa, 5)}) list.add(stack);
        if(settings==0){
            if(InventoryUtil.doesPlayerHaveAStacks(player, list , true)) {
                tile.setDuration(10000);
                tile.modeSetting();
            }
        }
        else{tile.modeSetting();}*/
        tile.modeSetting();
        tile.markDirty();
        return true;
    }
}
