package com.hbm.blocks.bomb;

import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import com.hbm.particle.helper.ExplosionCreator;

public class BlockChargeLeveler extends BlockChargeBase {

    @Override
    public BombReturnCode explode(World world, int x, int y, int z) {
        
        if(!world.isRemote) {
            safe = true;
            world.setBlockToAir(x, y, z);
            safe = false;
            
            int targetY = y;
            for(int tx = x-8; tx <= x+8; tx++) {
                for(int tz = z-8; tz <= z+8; tz++) {
                    double dx = tx - x;
                    double dz = tz - z;
                    if(dx*dx + dz*dz <= 64) {
                        for(int ty = targetY; ty < targetY + 3; ty++) {
                            Block b = world.getBlock(tx, ty, tz);
                            
                            if(b.getBlockHardness(world, tx, ty, tz) > 2.0F) continue;
                            
                            world.func_147480_a(tx, ty, tz, true);
                        }
                    }
                }
            }
            
            ExplosionCreator.composeEffectSmall(world, x + 0.5, y + 1, z + 0.5);
            
            return BombReturnCode.DETONATED;
        }
        
        return BombReturnCode.UNDEFINED;
    }

    @Override
    public int getRenderType() {
        return BlockChargeC4.renderID;
    }
    
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
        super.addInformation(stack, player, list, ext);
        list.add(EnumChatFormatting.GREEN + "Flattens a moderate amount of terrain.");
        list.add(EnumChatFormatting.BLUE + "Drops blocks, no damage.");
        list.add(EnumChatFormatting.GRAY + "Stone hardness only.");
    }
}
