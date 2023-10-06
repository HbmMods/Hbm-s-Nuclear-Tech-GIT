package com.hbm.items.tool;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.util.fauxpointtwelve.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.ForgeDirection;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ItemCMStructure extends Item  implements ILookOverlay {
    File file = new File(MainRegistry.configHbmDir, "CMstructureOutput.txt");
    public static BlockPos getAnchor(ItemStack stack) {

        if(!stack.hasTagCompound()) {
            return null;
        }

        return new BlockPos(stack.stackTagCompound.getInteger("anchorX"), stack.stackTagCompound.getInteger("anchorY"), stack.stackTagCompound.getInteger("anchorZ"));
    }
    public static void setAnchor(ItemStack stack, int x, int y, int z) {

        if(stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        }

        stack.stackTagCompound.setInteger("anchorX", x);
        stack.stackTagCompound.setInteger("anchorY", y);
        stack.stackTagCompound.setInteger("anchorZ", z);
    }
    public static void writeToFile(File config,ItemStack stack,World world){
        int anchorX = stack.stackTagCompound.getInteger("anchorX");
        int anchorY = stack.stackTagCompound.getInteger("anchorY");
        int anchorZ = stack.stackTagCompound.getInteger("anchorZ");
        int x1=stack.stackTagCompound.getInteger("x1");
        int y1=stack.stackTagCompound.getInteger("y1");
        int z1=stack.stackTagCompound.getInteger("z1");
        int x2=stack.stackTagCompound.getInteger("x2");
        int y2=stack.stackTagCompound.getInteger("y2");
        int z2=stack.stackTagCompound.getInteger("z2");
        ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(anchorX,anchorY,anchorZ));
        //ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
        int z=z1;z1=z<z2?z:z2;z2=z<z2?z2:z;
        int y=y1;y1=y<y2?y:y2;y2=y<y2?y2:y;
        int x=x1;x1=x<x2?x:x2;x2=x<x2?x2:x;
        if(dir == ForgeDirection.EAST || dir == ForgeDirection.WEST){
            z=x1;x1=z1;z1=z;
            z=x2;x2=z2;z2=z;
            int anchor=anchorX;anchorX=anchorZ;anchorZ=anchor;
        }
        try {
            JsonWriter writer = new JsonWriter(new FileWriter(config));
            writer.setIndent("  ");
            writer.beginObject();
            writer.name("components").beginArray();
            for(x=x1;x<=x2;x++){
                for(y=y1;y<=y2;y++){
                    for(z=z1;z<=z2;z++){
                        if(!((x==anchorX&&y==anchorY&&z==anchorZ)||((dir == ForgeDirection.EAST || dir == ForgeDirection.WEST)?world.getBlock(z, y, x)==Blocks.air:world.getBlock(x, y, z)==Blocks.air))){
                            writer.beginObject().setIndent("");
                            writer.name("block").value("hbm:" + ((dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) ? world.getBlock(z, y, x).getUnlocalizedName():world.getBlock(x, y, z).getUnlocalizedName()));
                            writer.name("x").value(x - anchorX);
                            writer.name("y").value(y - anchorY);
                            writer.name("z").value((dir == ForgeDirection.EAST || dir == ForgeDirection.WEST)?anchorZ - z : z - anchorZ);
                            writer.name("metas").beginArray();
                            writer.value(((dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) ? world.getBlockMetadata(z, y, x):world.getBlockMetadata(x, y, z)));
                            writer.endArray();
                            writer.endObject().setIndent("  ");
                        }
                    }
                }
            }
            writer.endArray();
            writer.endObject();
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
        Block b = world.getBlock(x, y, z);

        if(b == ModBlocks.cm_anchor) {
            this.setAnchor(stack, x, y, z);
            return true;
        }

        if(this.getAnchor(stack) == null) {
            return false;
        }
        if(!stack.stackTagCompound.hasKey("x1")) {
            stack.stackTagCompound.setInteger("x1", x);
            stack.stackTagCompound.setInteger("y1", y);
            stack.stackTagCompound.setInteger("z1", z);
        }
        else if(!stack.stackTagCompound.hasKey("x2")){
            stack.stackTagCompound.setInteger("x2", x);
            stack.stackTagCompound.setInteger("y2", y);
            stack.stackTagCompound.setInteger("z2", z);
        }
        else{
            writeToFile(file,stack,world);
            stack.stackTagCompound.removeTag("x1");
            stack.stackTagCompound.removeTag("y1");
            stack.stackTagCompound.removeTag("z1");
            stack.stackTagCompound.removeTag("x2");
            stack.stackTagCompound.removeTag("y2");
            stack.stackTagCompound.removeTag("z2");
        }
        return true;
    }
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
        super.addInformation(stack, player, list, ext);
        list.add(EnumChatFormatting.YELLOW + "Click Custom Machine Structure Positioning Anchor to");
        list.add(EnumChatFormatting.YELLOW + "Confirm the location of the custom machine core block.");
        list.add(EnumChatFormatting.YELLOW + "Output all blocks between Position1 and Position2 with");
        list.add(EnumChatFormatting.YELLOW + "metadata to \"CMstructureOutput.txt\" in hbmConfig.");
    }
    @Override
    public void printHook(RenderGameOverlayEvent.Pre event, World world, int x, int y, int z) {
        ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
        List<String> text = new ArrayList();

        BlockPos anchor = getAnchor(stack);

        if(anchor == null) {

            text.add(EnumChatFormatting.RED + "No Anchor");
        } else {
            int anchorX = stack.stackTagCompound.getInteger("anchorX");
            int anchorY = stack.stackTagCompound.getInteger("anchorY");
            int anchorZ = stack.stackTagCompound.getInteger("anchorZ");
            text.add(EnumChatFormatting.GOLD + "Anchor: " + anchorX + " / " + anchorY + " / " + anchorZ);
            if(stack.stackTagCompound.hasKey("x1")){
                int x1=stack.stackTagCompound.getInteger("x1");
                int y1=stack.stackTagCompound.getInteger("y1");
                int z1=stack.stackTagCompound.getInteger("z1");

                text.add(EnumChatFormatting.YELLOW + "Position1: " + x1 + " / " + y1 + " / " + z1);
            }
            if(stack.stackTagCompound.hasKey("x2")) {
                int x2=stack.stackTagCompound.getInteger("x2");
                int y2=stack.stackTagCompound.getInteger("y2");
                int z2=stack.stackTagCompound.getInteger("z2");
                text.add(EnumChatFormatting.YELLOW + "Position2: " + x2 + " / " + y2 + " / " + z2);
            }
        }

        ILookOverlay.printGeneric(event, this.getItemStackDisplayName(stack), 0xffff00, 0x404000, text);
    }
}
