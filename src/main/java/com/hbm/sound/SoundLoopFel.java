package com.hbm.sound;

import com.hbm.tileentity.machine.TileEntityFEL;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class SoundLoopFel extends SoundLoopMachine {

    public static List<SoundLoopFel> list = new ArrayList<>();

    private TileEntityFEL plant;

    public SoundLoopFel(ResourceLocation path, TileEntityFEL te) {
        super(path, te);
        list.add(this);
        plant = te;
    }

    @Override
    public void update() {
        super.update();

        if(this.volume != 3)
            volume = 3;

        if(!plant.isOn)
            this.donePlaying = true;
    }

    public TileEntity getTE() {
        return te;
    }
}
