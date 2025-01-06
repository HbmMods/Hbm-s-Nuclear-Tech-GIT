package com.hbm.entity.item;

import java.util.ArrayList;
import java.util.Iterator;

import com.hbm.blocks.BlockFallingNT;
import com.hbm.blocks.ISpotlight;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFallingBlockNT extends Entity {

	private Block fallingBlock;
	private int fallingMeta = -1;
	public int fallingTicks;
	public boolean canDrop;
	private boolean destroyOnLand;
	private boolean canHurtEntities;
	private int damageCap;
	private float damageAmount;
	public NBTTagCompound tileNBT;

	public EntityFallingBlockNT(World world) {
		super(world);
		this.canDrop = true;
		this.damageCap = 40;
		this.damageAmount = 2.0F;
		this.setSize(0.98F, 0.98F);
		this.yOffset = this.height / 2.0F;
	}

	public EntityFallingBlockNT(World world, double x, double y, double z, Block block) {
		this(world, x, y, z, block, 0);
	}

	public EntityFallingBlockNT(World world, double x, double y, double z, Block block, int meta) {
		super(world);
		this.canDrop = true;
		this.damageCap = 40;
		this.damageAmount = 2.0F;
		this.fallingBlock = block;
		this.dataWatcher.updateObject(10, Block.getIdFromBlock(fallingBlock));
		this.fallingMeta = meta;
		this.dataWatcher.updateObject(11, fallingMeta);
		this.preventEntitySpawning = true;
		this.setPosition(x, y, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	@Override protected void entityInit() {
		this.dataWatcher.addObject(10, new Integer(0));
		this.dataWatcher.addObject(11, new Integer(0));
	}
	
	public Block getBlock() {
		if(this.fallingBlock != null) return this.fallingBlock;
		
		this.fallingBlock = Block.getBlockById(this.dataWatcher.getWatchableObjectInt(10));
		return this.fallingBlock;
	}
	
	public int getMeta() {
		if(this.fallingMeta != -1) return this.fallingMeta;
		this.fallingMeta = 0;
		
		this.fallingMeta = this.dataWatcher.getWatchableObjectInt(11);
		return this.fallingMeta;
	}
	
	@Override protected boolean canTriggerWalking() { return false; }
	@Override public boolean canBeCollidedWith() { return !this.isDead; }

	public void onUpdate() {
		
		if(this.getBlock().getMaterial() == Material.air || this.getBlock() instanceof ISpotlight) {
			this.setDead();
		} else {
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			++this.fallingTicks;
			this.motionY -= 0.04D;
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.98D;
			this.motionY *= 0.98D;
			this.motionZ *= 0.98D;

			if(!this.worldObj.isRemote) {
				int x = MathHelper.floor_double(this.posX);
				int y = MathHelper.floor_double(this.posY);
				int z = MathHelper.floor_double(this.posZ);
				int meta = this.getMeta();

				if(this.fallingTicks == 1) {
					if(this.worldObj.getBlock(x, y, z) != this.getBlock()) {
						this.setDead();
						return;
					}

					this.worldObj.setBlockToAir(x, y, z);
				}

				if(this.onGround) {
					this.motionX *= 0.7D;
					this.motionZ *= 0.7D;
					this.motionY *= -0.5D;

					if(this.worldObj.getBlock(x, y, z) != Blocks.piston_extension) {
						this.setDead();

						if(!this.destroyOnLand && replacementCheck(x, y, z) && this.worldObj.setBlock(x, y, z, this.getBlock(), meta, 3)) {

							if(this.getBlock() instanceof BlockFalling) ((BlockFalling) this.getBlock()).func_149828_a(this.worldObj, x, y, z, meta);
							if(this.getBlock() instanceof BlockFallingNT) ((BlockFallingNT) this.getBlock()).onLand(this.worldObj, x, y, z, meta);

							if(this.tileNBT != null && this.getBlock() instanceof ITileEntityProvider) {
								TileEntity tileentity = this.worldObj.getTileEntity(x, y, z);

								if(tileentity != null) {
									NBTTagCompound nbt = new NBTTagCompound();
									tileentity.writeToNBT(nbt);
									Iterator it = this.tileNBT.func_150296_c().iterator();

									while(it.hasNext()) {
										String s = (String) it.next();
										NBTBase nbtbase = this.tileNBT.getTag(s);

										if(!s.equals("x") && !s.equals("y") && !s.equals("z")) {
											nbt.setTag(s, nbtbase.copy());
										}
									}

									tileentity.readFromNBT(nbt);
									tileentity.markDirty();
								}
							}
						} else if(this.canDrop && !this.destroyOnLand && this.getBlock().getItemDropped(meta, rand, 0) != null) {
							this.entityDropItem(new ItemStack(this.getBlock().getItemDropped(meta, rand, 0), 1, this.getBlock().damageDropped(meta)), 0.0F);
						}
					}
				} else if(this.fallingTicks > 100 && !this.worldObj.isRemote && (y < 1 || y > 256) || this.fallingTicks > 600) {
					if(this.canDrop && this.getBlock().getItemDropped(meta, rand, 0) != null) {
						this.entityDropItem(new ItemStack(this.getBlock().getItemDropped(meta, rand, 0), 1, this.getBlock().damageDropped(meta)), 0.0F);
					}

					this.setDead();
				}
			}
		}
	}
	
	public boolean replacementCheck(int x, int y, int z) {
		return worldObj.getBlock(x, y, z).isReplaceable(worldObj, x, y, z) && this.getBlock().canBlockStay(worldObj, x, y, z);
	}

	@Override
	protected void fall(float fallDistance) {
		
		if(this.canHurtEntities) {
			int fall = MathHelper.ceiling_float_int(fallDistance - 1.0F);

			if(fall > 0) {
				ArrayList arraylist = new ArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox));
				boolean isAnvil = this.getBlock() == Blocks.anvil;
				DamageSource damagesource = isAnvil ? DamageSource.anvil : DamageSource.fallingBlock;
				Iterator iterator = arraylist.iterator();

				while(iterator.hasNext()) {
					Entity entity = (Entity) iterator.next();
					entity.attackEntityFrom(damagesource, (float) Math.min(MathHelper.floor_float((float) fall * this.damageAmount), this.damageCap));
				}

				if(isAnvil && (double) this.rand.nextFloat() < 0.05D + (double) fall * 0.05D) {
					int j = this.getMeta() >> 2;
					int k = this.getMeta() & 3;
					++j;

					if(j > 2) {
						this.destroyOnLand = true;
					} else {
						this.fallingMeta = k | j << 2;
					}
				}
			}
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setByte("Tile", (byte) Block.getIdFromBlock(this.fallingBlock));
		nbt.setInteger("TileID", Block.getIdFromBlock(this.fallingBlock));
		nbt.setByte("Data", (byte) this.fallingMeta);
		nbt.setByte("Time", (byte) this.fallingTicks);
		nbt.setBoolean("DropItem", this.canDrop);
		nbt.setBoolean("HurtEntities", this.canHurtEntities);
		nbt.setFloat("FallHurtAmount", this.damageAmount);
		nbt.setInteger("FallHurtMax", this.damageCap);

		if(this.tileNBT != null) {
			nbt.setTag("TileEntityData", this.tileNBT);
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		
		if(nbt.hasKey("TileID", 99)) {
			this.fallingBlock = Block.getBlockById(nbt.getInteger("TileID"));
		} else {
			this.fallingBlock = Block.getBlockById(nbt.getByte("Tile") & 255);
		}

		this.fallingMeta = nbt.getByte("Data") & 255;
		this.fallingTicks = nbt.getByte("Time") & 255;

		if(nbt.hasKey("HurtEntities", 99)) {
			this.canHurtEntities = nbt.getBoolean("HurtEntities");
			this.damageAmount = nbt.getFloat("FallHurtAmount");
			this.damageCap = nbt.getInteger("FallHurtMax");
		} else if(this.fallingBlock == Blocks.anvil) {
			this.canHurtEntities = true;
		}

		if(nbt.hasKey("DropItem", 99)) {
			this.canDrop = nbt.getBoolean("DropItem");
		}

		if(nbt.hasKey("TileEntityData", 10)) {
			this.tileNBT = nbt.getCompoundTag("TileEntityData");
		}

		if(this.fallingBlock.getMaterial() == Material.air) {
			this.fallingBlock = Blocks.sand;
		}
	}

	public void func_145806_a(boolean p_145806_1_) {
		this.canHurtEntities = p_145806_1_;
	}

	@Override
	public void addEntityCrashInfo(CrashReportCategory report) {
		super.addEntityCrashInfo(report);
		report.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(this.fallingBlock)));
		report.addCrashSection("Immitating block data", Integer.valueOf(this.fallingMeta));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	@SideOnly(Side.CLIENT)
	public World getWorldForRender() {
		return this.worldObj;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire() {
		return false;
	}

	public Block getBlockForRender() {
		return this.getBlock();
	}
}
