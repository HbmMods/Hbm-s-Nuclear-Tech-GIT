package com.hbm.blocks;

import com.hbm.main.MainRegistry;
import net.minecraft.block.Block;

public class ModSoundTypes {
	
	public static final ModSoundType grate = ModSoundType.customStep(Block.soundTypeStone, "hbm:step.metalBlock", 0.5F, 1.0F);
	public static final ModSoundType pipe = ModSoundType.customDig(Block.soundTypeMetal, "hbm:block.pipePlaced", 0.85F, 0.85F).enveloped(MainRegistry.instance.rand).pitchFunction((in, rand, type) -> {
		if(type == ModSoundType.SubType.BREAK) in -= 0.15F;
		return in + rand.nextFloat() * 0.2F;
	});
	public static final ModSoundType flesh = ModSoundType.placeBreakStep("hbm:block.flesh", "hbm:block.flesh", "hbm:block.flesh", 0.5F, 1.0F);
	public static final ModSoundType platemetal = ModSoundType.placeBreakStep("hbm:block.platemetalPlace", "hbm:block.platemetalPlace", "hbm:step.platemetal", 1.0F, 1.0F);
}
