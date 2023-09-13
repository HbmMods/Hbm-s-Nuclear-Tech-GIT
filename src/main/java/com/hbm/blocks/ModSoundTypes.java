package com.hbm.blocks;

import com.hbm.main.MainRegistry;
import net.minecraft.block.Block;

class ModSoundTypes {
    static final ModSoundType grate = ModSoundType.customStep(Block.soundTypeStone, "hbm:step.metalBlock", 0.5F, 1.0F);
    static final ModSoundType pipe = ModSoundType.customDig(Block.soundTypeMetal, "hbm:block.pipePlaced", 0.85F, 0.65F).enveloped(MainRegistry.instance.rand).pitchFunction((in, rand, type) -> { if (type == ModSoundType.SubType.BREAK) in -= 0.15F; return type == ModSoundType.SubType.STEP ? in : in + rand.nextFloat() * 0.2F; });
}
