package com.hbm.tileentity.machine;

import api.hbm.block.ICrucibleAcceptor;
import api.hbm.fluid.IFluidStandardTransceiver;
import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.container.ContainerAssemfac;
import com.hbm.inventory.container.ContainerStrandCaster;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIAssemfac;
import com.hbm.inventory.gui.GUIStrandCaster;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.util.fauxpointtwelve.DirPos;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

//god thank you bob for this base class
public class TileEntityMachineStrandCaster extends TileEntityFoundryCastingBase implements IGUIProvider, ICrucibleAcceptor,ISidedInventory, IFluidStandardTransceiver, INBTPacketReceiver {
    public FluidTank water;
    public FluidTank steam;

    public ItemStack[] slots = new ItemStack[6];

    public TileEntityMachineStrandCaster() {

        water = new FluidTank(Fluids.WATER, 64_000);
        steam = new FluidTank(Fluids.SPENTSTEAM, 64_000);
    }
    int cooldown = 10;
    @Override
    public void updateEntity() {
        super.updateEntity();

        if(!worldObj.isRemote) {

            if(this.amount > this.getCapacity()) {
                this.amount = this.getCapacity();
            }

            if(this.amount == 0) {
                this.type = null;
            }

            if(worldObj.getTotalWorldTime() % 20 == 0) {
                this.updateConnections();
            }

            for(DirPos pos : getConPos()) {
                this.sendFluid(steam, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
            }

            ItemMold.Mold mold = this.getInstalledMold();

            if(mold != null && this.amount >= this.getCapacity() && slots[1] == null && water.getFill() >= getWaterRequired() ) {
                cooldown--;

                if(cooldown <= 0) {
                    this.amount -= mold.getCost();

                    ItemStack out = mold.getOutput(type);

                    for(int i = 1; i < 7; i++) {
                        if(slots[i].isItemEqual(out) && slots[i].stackSize + out.stackSize <= out.getMaxStackSize()) {
                            continue;
                        }

                        if(slots[i] == null) {
                            slots[i] = out.copy();
                        } else {
                            slots[i].stackSize += out.stackSize;
                        }
                    }
                }

                    water.setFill(water.getFill() - getWaterRequired());
                    steam.setFill(steam.getFill() + getWaterRequired());

                    cooldown = 20;
                }
                NBTTagCompound data = new NBTTagCompound();

                water.writeToNBT(data, "w");
                steam.writeToNBT(data, "s");

                this.networkPack(data, 150);

            } else {
                cooldown = 20;
            }
        }


    public DirPos[] getConPos() {

        ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
        ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

        return new DirPos[] {
                new DirPos(xCoord - dir.offsetX, yCoord, zCoord, rot),
                new DirPos(xCoord, yCoord, zCoord + dir.offsetX, rot),
                new DirPos(xCoord, yCoord, zCoord + dir.offsetX * 5, rot.getOpposite()),
                new DirPos(xCoord- dir.offsetX, yCoord, zCoord + dir.offsetX * 5, rot.getOpposite()),
        };
    }
    @Override
    public int getMoldSize() {
        return getInstalledMold().size;
    }

    @Override
    public int getCapacity() {
        ItemMold.Mold mold = this.getInstalledMold();
        return mold == null ? 0 : mold.getCost() * 10;
    }
    private int getWaterRequired() {
        return getInstalledMold() != null ? 5 * getInstalledMold().getCost() : 10;
    }
    private void updateConnections() {
        for(DirPos pos : getConPos()) {
            this.trySubscribe(water.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
        }
    }

    @Override
    public FluidTank[] getSendingTanks() {
        return new FluidTank[] { steam };
    }

    @Override
    public FluidTank[] getReceivingTanks() {
        return new FluidTank[] { water };
    }

    @Override
    public FluidTank[] getAllTanks() {
        return new FluidTank[] { water, steam };
    }

    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerStrandCaster(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GUIStrandCaster(player.inventory, this);
    }
    public void networkPack(NBTTagCompound nbt, int range) {

        if(!worldObj.isRemote)
            PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, xCoord, yCoord, zCoord), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
    }
    @Override
    public void networkUnpack(NBTTagCompound nbt) {
        water.readFromNBT(nbt, "w");
        steam.readFromNBT(nbt, "s");
    }
    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack) {

        if(i == 0) {
            return stack.getItem() == ModItems.mold;
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int meta) {
        return new int[] { 1, 2, 3, 4, 5, 6};
    }

}
