package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerOreSlopper;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIOreSlopper;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBedrockOreBase;
import com.hbm.items.special.ItemBedrockOreNew;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreGrade;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityMachineOreSlopper extends TileEntityMachineBase implements IGUIProvider {
	
	public long power;
	public static final long maxPower = 1_000_000;
	
	public static final int waterUsedBase = 1_000;
	public int waterUsed = waterUsedBase;
	public static final long consumptionBase = 200;
	public long consumption = consumptionBase;
	
	public float progress;
	public boolean processing;
	
	public SlopperAnimation animation = SlopperAnimation.LOWERING;
	public float slider;
	public float prevSlider;
	public float bucket;
	public float prevBucket;
	public int delay;
	
	public static FluidTank[] tanks;
	public double[] ores = new double[BedrockOreType.values().length];

	public TileEntityMachineOreSlopper() {
		super(11);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.WATER, 16_000);
		tanks[1] = new FluidTank(Fluids.SLOP, 16_000);
	}

	@Override
	public String getName() {
		return "container.machineOreSlopper";
	}
	
	public static enum SlopperAnimation {
		LOWERING, LIFTING, MOVE_SHREDDER, DUMPING, MOVE_BUCKET
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			tanks[0].setType(1, slots);
			FluidType conversion = this.getFluidOutput(tanks[0].getTankType());
			if(conversion != null) tanks[1].setTankType(conversion);
			
			this.processing = false;
			
			if(canSlop()) {
				this.power -= this.consumption;
				this.progress += 1F/200F;
				this.processing = true;
				boolean markDirty = false;
				
				while(progress >= 1F && canSlop()) {
					progress -= 1F;
					
					for(BedrockOreType type : BedrockOreType.values()) {
						ores[type.ordinal()] += ItemBedrockOreBase.getOreAmount(slots[2], type);
					}
					
					this.decrStackSize(2, 1);
					this.tanks[0].setFill(this.tanks[0].getFill() - waterUsed);
					this.tanks[1].setFill(this.tanks[1].getFill() + waterUsed);
					markDirty = true;
				}
				
				if(markDirty) this.markDirty();
				
			} else {
				this.progress = 0;
			}

			for(BedrockOreType type : BedrockOreType.values()) {
				ItemStack output = ItemBedrockOreNew.make(BedrockOreGrade.BASE, type);
				outer: while(ores[type.ordinal()] >= 1) {
					for(int i = 3; i <= 8; i++) if(slots[i] != null && slots[i].getItem() == output.getItem() && slots[i].getItemDamage() == output.getItemDamage() && slots[i].stackSize < output.getMaxStackSize()) {
						slots[i].stackSize++; ores[type.ordinal()] -= 1F; continue outer;
					}
					for(int i = 3; i <= 8; i++) if(slots[i] == null) {
						slots[i] = output; ores[type.ordinal()] -= 1F; continue outer;
					}
				}
			}
			
		} else {
			
			this.prevSlider = this.slider;
			this.prevBucket = this.bucket;
			
			if(this.processing) {
				
				if(delay > 0) {
					delay--;
					return;
				}
				
				switch(animation) {
				case LOWERING:
					this.bucket += 1F/40F;
					if(bucket >= 1F) {
						bucket = 1F;
						animation = SlopperAnimation.LIFTING;
						delay = 20;
					}
					break;
				case LIFTING:
					this.bucket -= 1F/40F;
					if(bucket <= 0) {
						bucket = 0F;
						animation = SlopperAnimation.MOVE_SHREDDER;
						delay = 10;
					}
					break;
				case MOVE_SHREDDER:
					this.slider += 1/60F;
					if(slider >= 1F) {
						slider = 1F;
						animation = SlopperAnimation.DUMPING;
						delay = 60;
					}
					break;
				case DUMPING:
					animation = SlopperAnimation.MOVE_BUCKET;
					break;
				case MOVE_BUCKET:
					this.slider -= 1/60F;
					if(slider <= 0F) {
						animation = SlopperAnimation.LOWERING;
						delay = 10;
					}
					break;
				}
			}
		}
	}

	@Override public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		buf.writeFloat(progress);
		buf.writeBoolean(processing);
	}
	
	@Override public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.progress = buf.readFloat();
		this.processing = buf.readBoolean();
	}
	
	public boolean canSlop() {
		if(this.getFluidOutput(tanks[0].getTankType()) == null) return false;
		if(tanks[0].getFill() < waterUsed) return false;
		if(tanks[1].getFill() + waterUsed > tanks[1].getMaxFill()) return false;
		if(power < consumption) return false;
		
		return slots[2] != null && slots[2].getItem() == ModItems.bedrock_ore_base;
	}
	
	public FluidType getFluidOutput(FluidType input) {
		if(input == Fluids.WATER) return Fluids.SLOP;
		return null;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerOreSlopper(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIOreSlopper(player.inventory, this);
	}
}
