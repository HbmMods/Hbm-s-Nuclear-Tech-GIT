package com.hbm.tileentity;

import com.hbm.blocks.test.TestContainer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityChest;

public class TileEntityTestContainer extends TileEntityChest {
	
	private String customName;
	
	private ItemStack[] chestContents = new ItemStack[9];
	
	private int cachedChestType;
	
	public TileEntityTestContainer adjacentChestZNeg;
    public TileEntityTestContainer adjacentChestXPos;
    public TileEntityTestContainer adjacentChestXNeg;
    public TileEntityTestContainer adjacentChestZPos;
    
    @Override
	public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.customName : "container.testContainer";
    }
    
    @Override
	public boolean hasCustomInventoryName()
    {
        return this.customName != null && this.customName.length() > 0;
    }

    @Override
	public void func_145976_a(String p_145976_1_)
    {
        this.customName = p_145976_1_;
    }

    @Override
	public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
        this.chestContents = new ItemStack[this.getSizeInventory()];

        if (p_145839_1_.hasKey("TestContainer", 8))
        {
            this.customName = p_145839_1_.getString("TestContainer");
        }

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.chestContents.length)
            {
                this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    @Override
	public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.chestContents.length; ++i)
        {
            if (this.chestContents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.chestContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        p_145841_1_.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            p_145841_1_.setString("TestContainer", this.customName);
        }
    }
    
    private void func_145978_a(TileEntityTestContainer p_145978_1_, int p_145978_2_)
    {
        if (p_145978_1_.isInvalid())
        {
            this.adjacentChestChecked = false;
        }
        else if (this.adjacentChestChecked)
        {
            switch (p_145978_2_)
            {
                case 0:
                    if (this.adjacentChestZPos != p_145978_1_)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case 1:
                    if (this.adjacentChestXNeg != p_145978_1_)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case 2:
                    if (this.adjacentChestZNeg != p_145978_1_)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case 3:
                    if (this.adjacentChestXPos != p_145978_1_)
                    {
                        this.adjacentChestChecked = false;
                    }
            }
        }
    }
    
    @Override
	public void checkForAdjacentChests()
    {
        if (!this.adjacentChestChecked)
        {
            this.adjacentChestChecked = true;
            this.adjacentChestZNeg = null;
            this.adjacentChestXPos = null;
            this.adjacentChestXNeg = null;
            this.adjacentChestZPos = null;

            if (this.func_145977_a(this.xCoord - 1, this.yCoord, this.zCoord))
            {
                this.adjacentChestXNeg = (TileEntityTestContainer)this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
            }

            if (this.func_145977_a(this.xCoord + 1, this.yCoord, this.zCoord))
            {
                this.adjacentChestXPos = (TileEntityTestContainer)this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
            }

            if (this.func_145977_a(this.xCoord, this.yCoord, this.zCoord - 1))
            {
                this.adjacentChestZNeg = (TileEntityTestContainer)this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
            }

            if (this.func_145977_a(this.xCoord, this.yCoord, this.zCoord + 1))
            {
                this.adjacentChestZPos = (TileEntityTestContainer)this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
            }

            if (this.adjacentChestZNeg != null)
            {
                this.adjacentChestZNeg.func_145978_a(this, 0);
            }

            if (this.adjacentChestZPos != null)
            {
                this.adjacentChestZPos.func_145978_a(this, 2);
            }

            if (this.adjacentChestXPos != null)
            {
                this.adjacentChestXPos.func_145978_a(this, 1);
            }

            if (this.adjacentChestXNeg != null)
            {
                this.adjacentChestXNeg.func_145978_a(this, 3);
            }
        }
    }
    
    private boolean func_145977_a(int p_145977_1_, int p_145977_2_, int p_145977_3_)
    {
        if (this.worldObj == null)
        {
            return false;
        }
        else
        {
            Block block = this.worldObj.getBlock(p_145977_1_, p_145977_2_, p_145977_3_);
            return block instanceof TestContainer && ((TestContainer)block).field_149956_a == this.func_145980_j();
        }
    }
    
    @Override
	public int func_145980_j()
    {
        if (this.cachedChestType == -1)
        {
            if (this.worldObj == null || !(this.getBlockType() instanceof TestContainer))
            {
                return 0;
            }

            this.cachedChestType = ((BlockChest)this.getBlockType()).field_149956_a;
        }

        return this.cachedChestType;
    }
}
