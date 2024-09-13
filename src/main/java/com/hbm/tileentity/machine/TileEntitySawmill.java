package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.entity.projectile.EntitySawblade;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.TileEntityMachineAutocrafter.InventoryCraftingAuto;
import com.hbm.util.ItemStackUtil;

import api.hbm.tile.IHeatSource;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySawmill extends TileEntityMachineBase {
	
	public int heat;
	public static final double diffusion = 0.1D;
	private int warnCooldown = 0;
	private int overspeed = 0;
	public boolean hasBlade = true;
	public int progress = 0;
	public static final int processingTime = 600;
	
	public float spin;
	public float lastSpin;

	public TileEntitySawmill() {
		super(3);
	}

	@Override
	public String getName() { return ""; }

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(hasBlade) {
				tryPullHeat();
				
				if(warnCooldown > 0)
					warnCooldown--;
				
				if(heat >= 100) {
					
					ItemStack result = this.getOutput(slots[0]);
					
					if(result != null) {
						progress += heat / 10;
						
						if(progress >= this.processingTime) {
							progress = 0;
							slots[0] = null;
							slots[1] = result;
							
							if(result.getItem() != ModItems.powder_sawdust) {
								float chance = result.getItem() == Items.stick ? 0.1F : 0.5F;
								if(worldObj.rand.nextFloat() < chance) {
									slots[2] = new ItemStack(ModItems.powder_sawdust);
								}
							}
							
							this.markDirty();
						}
						
					} else {
						this.progress = 0;
					}
					
					AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(-1D, 0.375D, -1D, -0.875, 2.375D, 1D);
					aabb = BlockDummyable.getAABBRotationOffset(aabb, xCoord + 0.5, yCoord, zCoord + 0.5, ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getRotation(ForgeDirection.UP));
					for(Object o : worldObj.getEntitiesWithinAABB(EntityLivingBase.class, aabb)) {
						EntityLivingBase e = (EntityLivingBase) o;
						if(e.isEntityAlive() && e.attackEntityFrom(ModDamageSource.turbofan, 100)) {
							worldObj.playSoundEffect(e.posX, e.posY, e.posZ, "mob.zombie.woodbreak", 2.0F, 0.95F + worldObj.rand.nextFloat() * 0.2F);
							int count = Math.min((int)Math.ceil(e.getMaxHealth() / 4), 250);
							NBTTagCompound data = new NBTTagCompound();
							data.setString("type", "vanillaburst");
							data.setInteger("count", count * 4);
							data.setDouble("motion", 0.1D);
							data.setString("mode", "blockdust");
							data.setInteger("block", Block.getIdFromBlock(Blocks.redstone_block));
							PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, e.posX, e.posY + e.height * 0.5, e.posZ), new TargetPoint(e.dimension, e.posX, e.posY, e.posZ, 50));
						}
					}
					
				} else {
					this.progress = 0;
				}
				
				if(heat > 300) {
					
					this.overspeed++;
					
					if(overspeed > 60 && warnCooldown == 0) {
						warnCooldown = 100;
						worldObj.playSoundEffect(xCoord + 0.5, yCoord + 1, zCoord + 0.5, "hbm:block.warnOverspeed", 2.0F, 1.0F);
					}
					
					if(overspeed > 300) {
						this.hasBlade = false;
						this.worldObj.newExplosion(null, xCoord + 0.5, yCoord + 1, zCoord + 0.5, 5F, false, false);
						
						int orientation = this.getBlockMetadata() - BlockDummyable.offset;
						ForgeDirection dir = ForgeDirection.getOrientation(orientation);
						EntitySawblade cog = new EntitySawblade(worldObj, xCoord + 0.5 + dir.offsetX, yCoord + 1, zCoord + 0.5 + dir.offsetZ).setOrientation(orientation);
						ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
						
						cog.motionX = rot.offsetX;
						cog.motionY = 1 + (heat - 100) * 0.0001D;
						cog.motionZ = rot.offsetZ;
						worldObj.spawnEntityInWorld(cog);
						
						this.markDirty();
					}
					
				} else {
					this.overspeed = 0;
				}
			} else {
				this.overspeed = 0;
				this.warnCooldown = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("heat", heat);
			data.setInteger("progress", progress);
			data.setBoolean("hasBlade", hasBlade);

			NBTTagList list = new NBTTagList();
			for(int i = 0; i < slots.length; i++) {
				if(slots[i] != null) {
					NBTTagCompound nbt1 = new NBTTagCompound();
					nbt1.setByte("slot", (byte) i);
					slots[i].writeToNBT(nbt1);
					list.appendTag(nbt1);
				}
			}
			data.setTag("items", list);
			
			INBTPacketReceiver.networkPack(this, data, 150);
			
			this.heat = 0;
			
		} else {
			
			float momentum = heat * 25F / ((float) 300);
			
			this.lastSpin = this.spin;
			this.spin += momentum;
			
			if(this.spin >= 360F) {
				this.spin -= 360F;
				this.lastSpin -= 360F;
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.heat = nbt.getInteger("heat");
		this.progress = nbt.getInteger("progress");
		this.hasBlade = nbt.getBoolean("hasBlade");
		
		NBTTagList list = nbt.getTagList("items", 10);

		slots = new ItemStack[3];
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.hasBlade = nbt.getBoolean("hasBlade");
		this.progress = nbt.getInteger("progress");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("hasBlade", hasBlade);
		nbt.setInteger("progress", progress);
	}
	
	protected void tryPullHeat() {
		TileEntity con = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int heatSrc = (int) (source.getHeatStored() * diffusion);
			
			if(heatSrc > 0) {
				source.useUpHeat(heatSrc);
				this.heat += heatSrc;
				return;
			}
		}
		
		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
	}
	
	protected InventoryCraftingAuto craftingInventory = new InventoryCraftingAuto(1, 1);

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return i == 0 && slots[0] == null && slots[1] == null && slots[2] == null && stack.stackSize == 1 && getOutput(stack) != null;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i > 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {0, 1, 2};
	}
	
	public ItemStack getOutput(ItemStack input) {
		
		if(input == null)
			return null;
		
		craftingInventory.setInventorySlotContents(0, input);
		
		List<String> names = ItemStackUtil.getOreDictNames(input);
		
		if(names.contains("stickWood")) {
			return new ItemStack(ModItems.powder_sawdust);
		}
		
		if(names.contains("logWood")) {
			for(Object o : CraftingManager.getInstance().getRecipeList()) {
				IRecipe recipe = (IRecipe) o;
				if(recipe.matches(craftingInventory, worldObj)) {
					ItemStack out = recipe.getCraftingResult(craftingInventory);
					if(out != null) {
						out = out.copy(); //for good measure
						out.stackSize = out.stackSize * 6 / 4; //4 planks become 6
						return out;
					}
				}
			}
		}
		
		if(names.contains("plankWood")) {
			return new ItemStack(Items.stick, 6);
		}
		
		if(names.contains("treeSapling")) {
			return new ItemStack(Items.stick, 1);
		}
		
		return null;
	}

	public static HashMap getRecipes() {
		
		HashMap<Object, Object[]> recipes = new HashMap<Object, Object[]>();

		recipes.put(new OreDictStack("logWood"), new ItemStack[] { new ItemStack(Blocks.planks, 6), ItemStackUtil.addTooltipToStack(new ItemStack(ModItems.powder_sawdust), EnumChatFormatting.RED + "50%") });
		recipes.put(new OreDictStack("plankWood"), new ItemStack[] { new ItemStack(Items.stick, 6), ItemStackUtil.addTooltipToStack(new ItemStack(ModItems.powder_sawdust), EnumChatFormatting.RED + "10%") });
		recipes.put(new OreDictStack("stickWood"), new ItemStack[] { new ItemStack(ModItems.powder_sawdust) });
		recipes.put(new OreDictStack("treeSapling"), new ItemStack[] { new ItemStack(Items.stick, 1), ItemStackUtil.addTooltipToStack(new ItemStack(ModItems.powder_sawdust), EnumChatFormatting.RED + "10%") });
		
		return recipes;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 2,
					zCoord + 2
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
