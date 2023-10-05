package com.hbm.blocks;

import com.hbm.main.MainRegistry;
import net.minecraft.block.Block;

public class ModSoundTypes {
	
	public static final ModSoundType grate = ModSoundType.customStep(Block.soundTypeStone, "hbm:step.metalBlock", 0.5F, 1.0F);
	public static final ModSoundType pipe = ModSoundType.customDig(Block.soundTypeMetal, "hbm:block.pipePlaced", 0.85F, 0.85F).enveloped(MainRegistry.instance.rand).pitchFunction((in, rand, type) -> {
		if(type == ModSoundType.SubType.BREAK) in -= 0.15F;
		return in + rand.nextFloat() * 0.2F;
	});
	public static final ModSoundType mork = ModSoundType.customBreak(Block.soundTypeStone, "hbm:step.morkite", 0.85F, 0.85F).enveloped(MainRegistry.instance.rand).pitchFunction((in, rand, type) -> {
		if(type == ModSoundType.SubType.BREAK) in -= 0.15F;
		return in + rand.nextFloat() * 0.2F;
	});
}
