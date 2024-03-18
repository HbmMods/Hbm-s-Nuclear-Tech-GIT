package com.hbm.wiaj.cannery;

import com.hbm.blocks.ModBlocks;
import com.hbm.util.I18nUtil;
import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.JarScript;
import com.hbm.wiaj.WorldInAJar;
import com.hbm.wiaj.actions.ActionCreateActor;
import com.hbm.wiaj.actions.ActionRemoveActor;
import com.hbm.wiaj.actions.ActionRotateBy;
import com.hbm.wiaj.actions.ActionSetBlock;
import com.hbm.wiaj.actions.ActionSetZoom;
import com.hbm.wiaj.actions.ActionWait;
import com.hbm.wiaj.actors.ActorFancyPanel;
import com.hbm.wiaj.actors.ActorFancyPanel.Orientation;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;

public class CanneryHadron extends CanneryBase {

    @Override
    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.hadron_core);
    }

    @Override
    public String getName() {
        return "cannery.hadron";
    }

    @Override
    public JarScript createScript() {
		WorldInAJar world = new WorldInAJar(25, 5, 25);
		JarScript script = new JarScript(world);
		

        // FIRST SCENE: Show and explain the core component
		JarScene scene0 = new JarScene(script);
		
        scene0.add(new ActionSetBlock(12, 2, 12, ModBlocks.hadron_core, ForgeDirection.NORTH.ordinal()));
        
		scene0.add(new ActionSetZoom(4, 0));

        scene0.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -20, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.0")}}, 200)
            .setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene0.add(new ActionWait(100));
		scene0.add(new ActionRemoveActor(1));
        scene0.add(new ActionWait(5));

        scene0.add(new ActionCreateActor(2, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -20, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.1")}}, 200)
            .setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

        scene0.add(new ActionWait(100));
        scene0.add(new ActionRemoveActor(2));
        scene0.add(new ActionWait(10));

        scene0.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -14, 4, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.2")}}, 200)
            .setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

        scene0.add(new ActionWait(80));
        scene0.add(new ActionRemoveActor(3));
        scene0.add(new ActionWait(5));

        scene0.add(new ActionRotateBy(-90, 0, 10));

        scene0.add(new ActionCreateActor(4, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 14, 4, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.3")}}, 200)
            .setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

        scene0.add(new ActionWait(80));
        scene0.add(new ActionRemoveActor(4));
        scene0.add(new ActionWait(5));

        scene0.add(new ActionRotateBy(90, 0, 10));


        // SECOND SCENE: Begin building a coil around the core component
        JarScene scene1 = new JarScene(script);

        scene1.add(new ActionSetBlock(12, 2, 12, ModBlocks.hadron_core, ForgeDirection.NORTH.ordinal()));

        scene1.add(new ActionSetZoom(4, 0));
        scene1.add(new ActionSetZoom(-2, 10));

        for(int i = 0; i < 8; i++) {
            double r = i * Math.PI / 4;
            scene1.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), 12, ModBlocks.hadron_coil_alloy));
            scene1.add(new ActionWait(2));
        }

        scene1.add(new ActionWait(5));

        scene1.add(new ActionCreateActor(5, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -25, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.4")}}, 200)
            .setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

        scene1.add(new ActionWait(40));

        for(Block block : new Block[] {
            ModBlocks.hadron_coil_gold,
            ModBlocks.hadron_coil_neodymium,
            ModBlocks.hadron_coil_magtung,
            ModBlocks.hadron_coil_schrabidium,
            ModBlocks.hadron_coil_schrabidate,
            ModBlocks.hadron_coil_starmetal,
            ModBlocks.hadron_coil_chlorophyte,
            ModBlocks.hadron_coil_mese
        }) {
            for(int i = 0; i < 8; i++) {
                double r = i * Math.PI / 4;
                scene1.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), 12, block));
                scene1.add(new ActionWait(1));
            }

            scene1.add(new ActionWait(4));
        }
        
        scene1.add(new ActionWait(20));
        scene1.add(new ActionRemoveActor(5));
        scene1.add(new ActionWait(5));

        for(int i = 0; i < 12; i++) {
            double r = i * Math.PI / 6;
            scene1.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 2.75F), 2 + (int)(Math.sin(r) * 2.75F), 12, ModBlocks.hadron_plating));
            scene1.add(new ActionWait(2));
        }

        scene1.add(new ActionCreateActor(6, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -40, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.5")}}, 200)
            .setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
        
        scene1.add(new ActionWait(60));
        scene1.add(new ActionRemoveActor(6));
        scene1.add(new ActionWait(5));


        // THIRD SCENE: Add the Access Terminal and Power Plug
		JarScene scene2 = new JarScene(script);
		
        scene2.add(new ActionSetBlock(12, 2, 12, ModBlocks.hadron_core, ForgeDirection.NORTH.ordinal()));
		scene2.add(new ActionSetZoom(2, 0));

        for(int i = 0; i < 8; i++) {
            double r = i * Math.PI / 4;
            scene2.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), 12, ModBlocks.hadron_coil_mese));
        }

        for(int i = 0; i < 12; i++) {
            double r = i * Math.PI / 6;
            scene1.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 2.75F), 2 + (int)(Math.sin(r) * 2.75F), 12, ModBlocks.hadron_plating));
        }

        scene2.add(new ActionWait(5));

        for(int i = 7; i >= 0; i--) {
            double r = i * Math.PI / 4;
            scene2.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), 12, ModBlocks.hadron_coil_neodymium));
            scene2.add(new ActionWait(1));
        }

        scene2.add(new ActionWait(20));

        scene2.add(new ActionSetBlock(12 - 2, 2, 12, Blocks.air));

        scene2.add(new ActionWait(15));

        scene2.add(new ActionSetBlock(12 - 2, 2, 12, ModBlocks.hadron_access, ForgeDirection.EAST.ordinal()));

        scene2.add(new ActionWait(10));

        scene2.add(new ActionCreateActor(7, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 36, 18, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.6")}}, 100)
            .setColors(colorCopper).setOrientation(Orientation.LEFT)));
        
        scene2.add(new ActionWait(80));
        scene2.add(new ActionRemoveActor(7));
        scene2.add(new ActionWait(20));

        scene2.add(new ActionSetBlock(12, 2 + 2, 12, Blocks.air));

        scene2.add(new ActionWait(15));

        scene2.add(new ActionSetBlock(12, 2 + 2, 12, ModBlocks.hadron_power));

        scene2.add(new ActionWait(10));

        scene2.add(new ActionCreateActor(8, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -40, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.7")}}, 200)
            .setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
        
        scene2.add(new ActionWait(80));
        scene2.add(new ActionRemoveActor(8));
        scene2.add(new ActionWait(20));


        script.addScene(scene0).addScene(scene1).addScene(scene2);

        return script;
    }
    
}
