package com.hbm.handler.ability;

import com.hbm.items.tool.IItemWithAbility;
import com.jcraft.jorbis.Block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IToolAreaAbility extends IBaseAbility {
    // Should call tool.breakExtraBlock on a bunch of blocks.
    // The initial block is always implicitly broken and shouldn't be included.
    // TODO: Explosion needs it not to be broken, as it bypasses the harvest ability
    void onDig(int level, World world, int x, int y, int z, EntityPlayer player, IItemWithAbility tool);
}
