package com.hbm.inventory.container;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.SlotMachineOutput;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityMachineSingGen;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerMachineSingGen extends Container
{
	private TileEntityMachineSingGen tileEntity;
	/* Shift click items */
	// Items that go into the corners
	public static Item[] cornerItems = new Item[] {ModItems.nugget_euphemium, ModItems.plate_advanced_alloy, ModItems.plate_combine_steel, ModItems.plate_euphemium, ModItems.plate_dineutronium};	
	// Items that go into the sides
	public static Item[] sideItems = new Item[] {ModItems.powder_power, ModItems.ingot_magnetized_tungsten, ModItems.powder_spark_mix, ModItems.crystal_xen, ModItems.singularity_counter_resonant, ModItems.singularity_super_heated};
	// Items that can go anywhere in the outer ring
	public static Item[] anyItems = new Item[] {ModItems.crystal_xen, ModItems.powder_nitan_mix, ModItems.board_copper, ModItems.screwdriver, ModItems.powder_neptunium, ModItems.powder_iodine, ModItems.powder_thorium, ModItems.powder_astatine, ModItems.powder_neodymium, ModItems.powder_caesium, ModItems.powder_strontium, ModItems.powder_bromine, ModItems.powder_cobalt, ModItems.powder_tennessine, ModItems.powder_niobium, ModItems.powder_cerium};
	// Items that go into the center
	public static Item[] centerItems = new Item[] {Item.getItemFromBlock(ModBlocks.block_schrabidium), ModItems.singularity, ModItems.black_hole};

	public ContainerMachineSingGen(InventoryPlayer invPlayer, TileEntityMachineSingGen tEntity)
	{
		tileEntity = tEntity;
		/* -- Input Slots --
		 * 5  9  6
		 * 11 4  12
		 * 7  10 8
		 */
		// Battery
		this.addSlotToContainer(new Slot(tEntity, 0, 8, 112));
		// Output
		this.addSlotToContainer(new SlotMachineOutput(tEntity, 1, 80, 106));
		// Fluid input
		this.addSlotToContainer(new Slot(tEntity, 2, 152, 94));
		// Fluid output
		this.addSlotToContainer(new SlotMachineOutput(tEntity, 3, 152, 112));
		// Center item input
		this.addSlotToContainer(new Slot(tEntity, 4, 80, 49));
		// Corner item input
		this.addSlotToContainer(new Slot(tEntity, 5, 54, 23));
		this.addSlotToContainer(new Slot(tEntity, 6, 106, 23));
		this.addSlotToContainer(new Slot(tEntity, 7, 54, 75));
		this.addSlotToContainer(new Slot(tEntity, 8, 106, 75));
		// Side item input
		this.addSlotToContainer(new Slot(tEntity, 9, 80, 16));
		this.addSlotToContainer(new Slot(tEntity, 10, 80, 82));
		this.addSlotToContainer(new Slot(tEntity, 11, 47, 49));
		this.addSlotToContainer(new Slot(tEntity, 12, 113, 49));
		
		for (int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 60));
			}
		}
		
		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 60));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tileEntity.isUseableByPlayer(player);
	}
	// TODO Finish
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
            if (par2 <= 3) {
				if (!this.mergeItemStack(var5, 4, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 3, false))
			{
				return null;
			}
			
			if (var5.stackSize == 0)
			{
				var4.putStack((ItemStack) null);
			}
			else
			{
				var4.onSlotChanged();
			}
		}
		
		return var3;
    }}
