package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FT_Gaseous;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.util.AstronomyUtil;
import com.hbm.util.I18nUtil;

import api.hbm.block.IToolable;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockAtmosphereEditor extends BlockContainer implements IToolable, ITooltipProvider, ILookOverlay {

    public BlockAtmosphereEditor(Material material) {
        super(material);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityAtmosphereEditor();
    }

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GOLD + "Use screwdriver to turn on and off");
		list.add(EnumChatFormatting.GOLD + "Use hand drill to increase/decrease throughput");
		list.add(EnumChatFormatting.GOLD + "Use defuser to switch emission/capture mode");
		list.add(EnumChatFormatting.GOLD + "Use fluid identifier to change fluid");
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityAtmosphereEditor))
			return;
		
		TileEntityAtmosphereEditor editor = (TileEntityAtmosphereEditor) te;

        CBT_Atmosphere atmosphere = CelestialBody.getTrait(world, CBT_Atmosphere.class);
        double pressure = atmosphere != null ? atmosphere.getPressure(editor.fluid) : 0;
        if(pressure < 0.00001) pressure = 0;
		
		List<String> text = new ArrayList<String>();

        text.add("State: " + (editor.isOn ? "RUNNING" : "OFF"));
        text.add("Current gas: " + editor.fluid.getLocalizedName() + " - " + pressure);
        text.add("Current mode: " + (editor.isEmitting ? "EMITTING" : "CAPTURING"));
        text.add("Current throughput: " + Math.pow(10, editor.throughputFactor) / AstronomyUtil.MB_PER_ATM);
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

    @Override
    public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
        TileEntity te = world.getTileEntity(x, y, z);

        if(!(te instanceof TileEntityAtmosphereEditor))
            return false;
		
        TileEntityAtmosphereEditor editor = (TileEntityAtmosphereEditor) te;

        if(tool == ToolType.SCREWDRIVER) {
            editor.isOn = !editor.isOn;
			editor.markDirty();

            return true;
        } else if(tool == ToolType.HAND_DRILL) {
            editor.throughputFactor += (player.isSneaking() ? -1 : 1);
            editor.markDirty();

            return true;
        } else if(tool == ToolType.DEFUSER) {
            editor.isEmitting = !editor.isEmitting;
            editor.markDirty();

            return true;
        }

        return false;
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if(world.isRemote)
            return true;

        if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
            TileEntity te = world.getTileEntity(x, y, z);
            
            if(!(te instanceof TileEntityAtmosphereEditor))
                return false;
            
            TileEntityAtmosphereEditor editor = (TileEntityAtmosphereEditor) te;
            FluidType type = ((IItemFluidIdentifier) player.getHeldItem().getItem()).getType(world, x, y, z, player.getHeldItem());
            editor.fluid = type;
            editor.markDirty();
            player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation(type.getConditionalName())).appendSibling(new ChatComponentText("!")));
            
            return true;
        }

        return false;
	}

    public static class TileEntityAtmosphereEditor extends TileEntity implements INBTPacketReceiver {

        private boolean isOn = false;
        private int throughputFactor = 10;
        private FluidType fluid = Fluids.AIR;
        private boolean isEmitting = true;

		@Override
		public void updateEntity() {
			if(worldObj.isRemote) return;

            if(isOn) {
                if(isEmitting) {
                    FT_Gaseous.release(worldObj, fluid, Math.pow(10, throughputFactor));
                } else {
                    FT_Gaseous.capture(worldObj, fluid, Math.pow(10, throughputFactor));
                }
            }
				
            NBTTagCompound data = new NBTTagCompound();
            data.setBoolean("isOn", isOn);
            data.setInteger("throughput", throughputFactor);
            data.setInteger("fluid", fluid.getID());
            data.setBoolean("emit", isEmitting);
            PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(data, xCoord, yCoord, zCoord), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
        }

		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
		}
		
		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.func_148857_g());
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
            isOn = nbt.getBoolean("isOn");
            throughputFactor = nbt.getInteger("throughput");
            fluid = Fluids.fromID(nbt.getInteger("fluid"));
            isEmitting = nbt.getBoolean("emit");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
            nbt.setBoolean("isOn", isOn);
            nbt.setInteger("throughput", throughputFactor);
            nbt.setInteger("fluid", fluid.getID());
            nbt.setBoolean("emit", isEmitting);
		}

        @Override
        public void networkUnpack(NBTTagCompound nbt) {
            isOn = nbt.getBoolean("isOn");
            throughputFactor = nbt.getInteger("throughput");
            fluid = Fluids.fromID(nbt.getInteger("fluid"));
            isEmitting = nbt.getBoolean("emit");
        }

    }
    
}
