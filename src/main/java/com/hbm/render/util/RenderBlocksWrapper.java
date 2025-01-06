package com.hbm.render.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RenderBlocksWrapper extends RenderBlocks {
	
	public static final RenderBlocksWrapper INSTANCE = new RenderBlocksWrapper();
	public RenderBlocks rb;
	
	public void setup(RenderBlocks rb) { this.rb = rb; }
	
	@Override
	public boolean renderStandardBlock(Block block, int x, int y, int z) {
		int color = block.colorMultiplier(this.blockAccess, x, y, z);
		float r = (float) (color >> 16 & 255) / 255.0F;
		float g = (float) (color >> 8 & 255) / 255.0F;
		float b = (float) (color & 255) / 255.0F;

		if(EntityRenderer.anaglyphEnable) {
			float ar = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
			float ag = (r * 30.0F + g * 70.0F) / 100.0F;
			float ab = (r * 30.0F + b * 70.0F) / 100.0F;
			r = ar;
			g = ag;
			b = ab;
		}

		return Minecraft.isAmbientOcclusionEnabled() && block.getLightValue() == 0
				? (this.partialRenderBounds ? this.renderStandardBlockWithAmbientOcclusionPartial(block, x, y, z, r, g, b)
						: this.renderStandardBlockWithAmbientOcclusion(block, x, y, z, r, g, b))
				: this.renderStandardBlockWithColorMultiplier(block, x, y, z, r, g, b);
	}

	// the unholy mass
	@Override public void setOverrideBlockTexture(IIcon p_147757_1_) { rb.setOverrideBlockTexture(p_147757_1_); }
	@Override public void clearOverrideBlockTexture() { rb.clearOverrideBlockTexture(); }
	@Override public boolean hasOverrideBlockTexture() { return rb.hasOverrideBlockTexture(); }
	@Override public void setRenderFromInside(boolean p_147786_1_) { rb.setRenderFromInside(p_147786_1_); }
	@Override public void setRenderAllFaces(boolean p_147753_1_) { rb.setRenderAllFaces(p_147753_1_); }
	@Override public void setRenderBounds(double p_147782_1_, double p_147782_3_, double p_147782_5_, double p_147782_7_, double p_147782_9_, double p_147782_11_) { rb.setRenderBounds(p_147782_1_, p_147782_3_, p_147782_5_, p_147782_7_, p_147782_9_, p_147782_11_); }
	@Override public void setRenderBoundsFromBlock(Block p_147775_1_) { rb.setRenderBoundsFromBlock(p_147775_1_); }
	@Override public void overrideBlockBounds(double p_147770_1_, double p_147770_3_, double p_147770_5_, double p_147770_7_, double p_147770_9_, double p_147770_11_) { rb.overrideBlockBounds(p_147770_1_, p_147770_3_, p_147770_5_, p_147770_7_, p_147770_9_, p_147770_11_); }
	@Override public void unlockBlockBounds() { rb.unlockBlockBounds(); }
	@Override public void renderBlockUsingTexture(Block p_147792_1_, int p_147792_2_, int p_147792_3_, int p_147792_4_, IIcon p_147792_5_) { rb.renderBlockUsingTexture(p_147792_1_, p_147792_2_, p_147792_3_, p_147792_4_, p_147792_5_); }
	@Override public void renderBlockAllFaces(Block p_147769_1_, int p_147769_2_, int p_147769_3_, int p_147769_4_) { rb.renderBlockAllFaces(p_147769_1_, p_147769_2_, p_147769_3_, p_147769_4_); }
	@Override public boolean renderBlockByRenderType(Block p_147805_1_, int p_147805_2_, int p_147805_3_, int p_147805_4_) { return rb.renderBlockByRenderType(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_); }
	@Override public boolean renderBlockEndPortalFrame(BlockEndPortalFrame p_147743_1_, int p_147743_2_, int p_147743_3_, int p_147743_4_) { return rb.renderBlockEndPortalFrame(p_147743_1_, p_147743_2_, p_147743_3_, p_147743_4_); }
	@Override public boolean renderBlockBed(Block p_147773_1_, int p_147773_2_, int p_147773_3_, int p_147773_4_) { return rb.renderBlockBed(p_147773_1_, p_147773_2_, p_147773_3_, p_147773_4_); }
	@Override public boolean renderBlockBrewingStand(BlockBrewingStand p_147741_1_, int p_147741_2_, int p_147741_3_, int p_147741_4_) { return rb.renderBlockBrewingStand(p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_); }
	@Override public boolean renderBlockCauldron(BlockCauldron p_147785_1_, int p_147785_2_, int p_147785_3_, int p_147785_4_) { return rb.renderBlockCauldron(p_147785_1_, p_147785_2_, p_147785_3_, p_147785_4_); }
	@Override public boolean renderBlockFlowerpot(BlockFlowerPot p_147752_1_, int p_147752_2_, int p_147752_3_, int p_147752_4_) { return rb.renderBlockFlowerpot(p_147752_1_, p_147752_2_, p_147752_3_, p_147752_4_); }
	@Override public boolean renderBlockAnvil(BlockAnvil p_147725_1_, int p_147725_2_, int p_147725_3_, int p_147725_4_) { return rb.renderBlockAnvil(p_147725_1_, p_147725_2_, p_147725_3_, p_147725_4_); }
	@Override public boolean renderBlockAnvilMetadata(BlockAnvil p_147780_1_, int p_147780_2_, int p_147780_3_, int p_147780_4_, int p_147780_5_) { return rb.renderBlockAnvilMetadata(p_147780_1_, p_147780_2_, p_147780_3_, p_147780_4_, p_147780_5_); }
	@Override public boolean renderBlockAnvilOrient(BlockAnvil p_147728_1_, int p_147728_2_, int p_147728_3_, int p_147728_4_, int p_147728_5_, boolean p_147728_6_) { return rb.renderBlockAnvilOrient(p_147728_1_, p_147728_2_, p_147728_3_, p_147728_4_, p_147728_5_, p_147728_6_); }
	@Override public float renderBlockAnvilRotate(BlockAnvil p_147737_1_, int p_147737_2_, int p_147737_3_, int p_147737_4_, int p_147737_5_, float p_147737_6_, float p_147737_7_, float p_147737_8_, float p_147737_9_, boolean p_147737_10_, boolean p_147737_11_, int p_147737_12_) { return rb.renderBlockAnvilRotate(p_147737_1_, p_147737_2_, p_147737_3_, p_147737_4_, p_147737_5_, p_147737_6_, p_147737_7_, p_147737_8_, p_147737_9_, p_147737_10_, p_147737_11_, p_147737_12_); }
	@Override public boolean renderBlockTorch(Block p_147791_1_, int p_147791_2_, int p_147791_3_, int p_147791_4_) { return rb.renderBlockTorch(p_147791_1_, p_147791_2_, p_147791_3_, p_147791_4_); }
	@Override public boolean renderBlockRepeater(BlockRedstoneRepeater p_147759_1_, int p_147759_2_, int p_147759_3_, int p_147759_4_) { return rb.renderBlockRepeater(p_147759_1_, p_147759_2_, p_147759_3_, p_147759_4_); }
	@Override public boolean renderBlockRedstoneComparator(BlockRedstoneComparator p_147781_1_, int p_147781_2_, int p_147781_3_, int p_147781_4_) { return rb.renderBlockRedstoneComparator(p_147781_1_, p_147781_2_, p_147781_3_, p_147781_4_); }
	@Override public boolean renderBlockRedstoneDiode(BlockRedstoneDiode p_147748_1_, int p_147748_2_, int p_147748_3_, int p_147748_4_) { return rb.renderBlockRedstoneDiode(p_147748_1_, p_147748_2_, p_147748_3_, p_147748_4_); }
	@Override public void renderBlockRedstoneDiodeMetadata(BlockRedstoneDiode p_147732_1_, int p_147732_2_, int p_147732_3_, int p_147732_4_, int p_147732_5_) { rb.renderBlockRedstoneDiodeMetadata(p_147732_1_, p_147732_2_, p_147732_3_, p_147732_4_, p_147732_5_); }
	@Override public void renderPistonBaseAllFaces(Block p_147804_1_, int p_147804_2_, int p_147804_3_, int p_147804_4_) { rb.renderPistonBaseAllFaces(p_147804_1_, p_147804_2_, p_147804_3_, p_147804_4_); }
	@Override public boolean renderPistonBase(Block p_147731_1_, int p_147731_2_, int p_147731_3_, int p_147731_4_, boolean p_147731_5_) { return rb.renderPistonBase(p_147731_1_, p_147731_2_, p_147731_3_, p_147731_4_, p_147731_5_); }
	@Override public void renderPistonRodUD(double p_147763_1_, double p_147763_3_, double p_147763_5_, double p_147763_7_, double p_147763_9_, double p_147763_11_, float p_147763_13_, double p_147763_14_) { rb.renderPistonRodUD(p_147763_1_, p_147763_3_, p_147763_5_, p_147763_7_, p_147763_9_, p_147763_11_, p_147763_13_, p_147763_14_); }
	@Override public void renderPistonRodSN(double p_147789_1_, double p_147789_3_, double p_147789_5_, double p_147789_7_, double p_147789_9_, double p_147789_11_, float p_147789_13_, double p_147789_14_) { rb.renderPistonRodSN(p_147789_1_, p_147789_3_, p_147789_5_, p_147789_7_, p_147789_9_, p_147789_11_, p_147789_13_, p_147789_14_); }
	@Override public void renderPistonRodEW(double p_147738_1_, double p_147738_3_, double p_147738_5_, double p_147738_7_, double p_147738_9_, double p_147738_11_, float p_147738_13_, double p_147738_14_) { rb.renderPistonRodEW(p_147738_1_, p_147738_3_, p_147738_5_, p_147738_7_, p_147738_9_, p_147738_11_, p_147738_13_, p_147738_14_); }
	@Override public void renderPistonExtensionAllFaces(Block p_147750_1_, int p_147750_2_, int p_147750_3_, int p_147750_4_, boolean p_147750_5_) { rb.renderPistonExtensionAllFaces(p_147750_1_, p_147750_2_, p_147750_3_, p_147750_4_, p_147750_5_); }
	@Override public boolean renderPistonExtension(Block p_147809_1_, int p_147809_2_, int p_147809_3_, int p_147809_4_, boolean p_147809_5_) { return rb.renderPistonExtension(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_, p_147809_5_); }
	@Override public boolean renderBlockLever(Block p_147790_1_, int p_147790_2_, int p_147790_3_, int p_147790_4_) { return rb.renderBlockLever(p_147790_1_, p_147790_2_, p_147790_3_, p_147790_4_); }
	@Override public boolean renderBlockTripWireSource(Block p_147723_1_, int p_147723_2_, int p_147723_3_, int p_147723_4_) { return rb.renderBlockTripWireSource(p_147723_1_, p_147723_2_, p_147723_3_, p_147723_4_); }
	@Override public boolean renderBlockTripWire(Block p_147756_1_, int p_147756_2_, int p_147756_3_, int p_147756_4_) { return rb.renderBlockTripWire(p_147756_1_, p_147756_2_, p_147756_3_, p_147756_4_); }
	@Override public boolean renderBlockFire(BlockFire p_147801_1_, int p_147801_2_, int p_147801_3_, int p_147801_4_) { return rb.renderBlockFire(p_147801_1_, p_147801_2_, p_147801_3_, p_147801_4_); }
	@Override public boolean renderBlockRedstoneWire(Block p_147788_1_, int p_147788_2_, int p_147788_3_, int p_147788_4_) { return rb.renderBlockRedstoneWire(p_147788_1_, p_147788_2_, p_147788_3_, p_147788_4_); }
	@Override public boolean renderBlockMinecartTrack(BlockRailBase p_147766_1_, int p_147766_2_, int p_147766_3_, int p_147766_4_) { return rb.renderBlockMinecartTrack(p_147766_1_, p_147766_2_, p_147766_3_, p_147766_4_); }
	@Override public boolean renderBlockLadder(Block p_147794_1_, int p_147794_2_, int p_147794_3_, int p_147794_4_) { return rb.renderBlockLadder(p_147794_1_, p_147794_2_, p_147794_3_, p_147794_4_); }
	@Override public boolean renderBlockVine(Block p_147726_1_, int p_147726_2_, int p_147726_3_, int p_147726_4_) { return rb.renderBlockVine(p_147726_1_, p_147726_2_, p_147726_3_, p_147726_4_); }
	@Override public boolean renderBlockStainedGlassPane(Block p_147733_1_, int p_147733_2_, int p_147733_3_, int p_147733_4_) { return rb.renderBlockStainedGlassPane(p_147733_1_, p_147733_2_, p_147733_3_, p_147733_4_); }
	@Override public boolean renderBlockPane(BlockPane p_147767_1_, int p_147767_2_, int p_147767_3_, int p_147767_4_) { return rb.renderBlockPane(p_147767_1_, p_147767_2_, p_147767_3_, p_147767_4_); }
	@Override public boolean renderCrossedSquares(Block p_147746_1_, int p_147746_2_, int p_147746_3_, int p_147746_4_) { return rb.renderCrossedSquares(p_147746_1_, p_147746_2_, p_147746_3_, p_147746_4_); }
	@Override public boolean renderBlockDoublePlant(BlockDoublePlant p_147774_1_, int p_147774_2_, int p_147774_3_, int p_147774_4_) { return rb.renderBlockDoublePlant(p_147774_1_, p_147774_2_, p_147774_3_, p_147774_4_); }
	@Override public boolean renderBlockStem(Block p_147724_1_, int p_147724_2_, int p_147724_3_, int p_147724_4_) { return rb.renderBlockStem(p_147724_1_, p_147724_2_, p_147724_3_, p_147724_4_); }
	@Override public boolean renderBlockCrops(Block p_147796_1_, int p_147796_2_, int p_147796_3_, int p_147796_4_) { return rb.renderBlockCrops(p_147796_1_, p_147796_2_, p_147796_3_, p_147796_4_); }
	@Override public void renderTorchAtAngle(Block p_147747_1_, double p_147747_2_, double p_147747_4_, double p_147747_6_, double p_147747_8_, double p_147747_10_, int p_147747_12_) { rb.renderTorchAtAngle(p_147747_1_, p_147747_2_, p_147747_4_, p_147747_6_, p_147747_8_, p_147747_10_, p_147747_12_); }
	@Override public void drawCrossedSquares(IIcon p_147765_1_, double p_147765_2_, double p_147765_4_, double p_147765_6_, float p_147765_8_) { rb.drawCrossedSquares(p_147765_1_, p_147765_2_, p_147765_4_, p_147765_6_, p_147765_8_); }
	@Override public void renderBlockStemSmall(Block p_147730_1_, int p_147730_2_, double p_147730_3_, double p_147730_5_, double p_147730_7_, double p_147730_9_) { rb.renderBlockStemSmall(p_147730_1_, p_147730_2_, p_147730_3_, p_147730_5_, p_147730_7_, p_147730_9_); }
	@Override public boolean renderBlockLilyPad(Block p_147783_1_, int p_147783_2_, int p_147783_3_, int p_147783_4_) { return rb.renderBlockLilyPad(p_147783_1_, p_147783_2_, p_147783_3_, p_147783_4_); }
	@Override public void renderBlockStemBig(BlockStem p_147740_1_, int p_147740_2_, int p_147740_3_, double p_147740_4_, double p_147740_6_, double p_147740_8_, double p_147740_10_) { rb.renderBlockStemBig(p_147740_1_, p_147740_2_, p_147740_3_, p_147740_4_, p_147740_6_, p_147740_8_, p_147740_10_); }
	@Override public void renderBlockCropsImpl(Block p_147795_1_, int p_147795_2_, double p_147795_3_, double p_147795_5_, double p_147795_7_) { rb.renderBlockCropsImpl(p_147795_1_, p_147795_2_, p_147795_3_, p_147795_5_, p_147795_7_); }
	@Override public boolean renderBlockLiquid(Block p_147721_1_, int p_147721_2_, int p_147721_3_, int p_147721_4_) { return rb.renderBlockLiquid(p_147721_1_, p_147721_2_, p_147721_3_, p_147721_4_); }
	@Override public float getLiquidHeight(int p_147729_1_, int p_147729_2_, int p_147729_3_, Material p_147729_4_) { return rb.getLiquidHeight(p_147729_1_, p_147729_2_, p_147729_3_, p_147729_4_); }
	@Override public void renderBlockSandFalling(Block p_147749_1_, World p_147749_2_, int p_147749_3_, int p_147749_4_, int p_147749_5_, int p_147749_6_) { rb.renderBlockSandFalling(p_147749_1_, p_147749_2_, p_147749_3_, p_147749_4_, p_147749_5_, p_147749_6_); }
	@Override public boolean renderBlockLog(Block p_147742_1_, int p_147742_2_, int p_147742_3_, int p_147742_4_) { return rb.renderBlockLog(p_147742_1_, p_147742_2_, p_147742_3_, p_147742_4_); }
	@Override public boolean renderBlockQuartz(Block p_147779_1_, int p_147779_2_, int p_147779_3_, int p_147779_4_) { return rb.renderBlockQuartz(p_147779_1_, p_147779_2_, p_147779_3_, p_147779_4_); }
	@Override public boolean renderStandardBlockWithAmbientOcclusion(Block p_147751_1_, int p_147751_2_, int p_147751_3_, int p_147751_4_, float p_147751_5_, float p_147751_6_, float p_147751_7_) { return rb.renderStandardBlockWithAmbientOcclusion(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, p_147751_5_, p_147751_6_, p_147751_7_); }
	@Override public boolean renderStandardBlockWithAmbientOcclusionPartial(Block p_147808_1_, int p_147808_2_, int p_147808_3_, int p_147808_4_, float p_147808_5_, float p_147808_6_, float p_147808_7_) { return rb.renderStandardBlockWithAmbientOcclusionPartial(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, p_147808_5_, p_147808_6_, p_147808_7_); }
	@Override public int getAoBrightness(int p_147778_1_, int p_147778_2_, int p_147778_3_, int p_147778_4_) { return rb.getAoBrightness(p_147778_1_, p_147778_2_, p_147778_3_, p_147778_4_); }
	@Override public int mixAoBrightness(int p_147727_1_, int p_147727_2_, int p_147727_3_, int p_147727_4_, double p_147727_5_, double p_147727_7_, double p_147727_9_, double p_147727_11_) { return rb.mixAoBrightness(p_147727_1_, p_147727_2_, p_147727_3_, p_147727_4_, p_147727_5_, p_147727_7_, p_147727_9_, p_147727_11_); }
	@Override public boolean renderStandardBlockWithColorMultiplier(Block p_147736_1_, int p_147736_2_, int p_147736_3_, int p_147736_4_, float p_147736_5_, float p_147736_6_, float p_147736_7_) { return rb.renderStandardBlockWithColorMultiplier(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, p_147736_5_, p_147736_6_, p_147736_7_); }
	@Override public boolean renderBlockCocoa(BlockCocoa p_147772_1_, int p_147772_2_, int p_147772_3_, int p_147772_4_) { return rb.renderBlockCocoa(p_147772_1_, p_147772_2_, p_147772_3_, p_147772_4_); }
	@Override public boolean renderBlockBeacon(BlockBeacon p_147797_1_, int p_147797_2_, int p_147797_3_, int p_147797_4_) { return rb.renderBlockBeacon(p_147797_1_, p_147797_2_, p_147797_3_, p_147797_4_); }
	@Override public boolean renderBlockCactus(Block p_147755_1_, int p_147755_2_, int p_147755_3_, int p_147755_4_) { return rb.renderBlockCactus(p_147755_1_, p_147755_2_, p_147755_3_, p_147755_4_); }
	@Override public boolean renderBlockCactusImpl(Block p_147754_1_, int p_147754_2_, int p_147754_3_, int p_147754_4_, float p_147754_5_, float p_147754_6_, float p_147754_7_) { return rb.renderBlockCactusImpl(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, p_147754_5_, p_147754_6_, p_147754_7_); }
	@Override public boolean renderBlockFence(BlockFence p_147735_1_, int p_147735_2_, int p_147735_3_, int p_147735_4_) { return rb.renderBlockFence(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_); }
	@Override public boolean renderBlockWall(BlockWall p_147807_1_, int p_147807_2_, int p_147807_3_, int p_147807_4_) { return rb.renderBlockWall(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_); }
	@Override public boolean renderBlockDragonEgg(BlockDragonEgg p_147802_1_, int p_147802_2_, int p_147802_3_, int p_147802_4_) { return rb.renderBlockDragonEgg(p_147802_1_, p_147802_2_, p_147802_3_, p_147802_4_); }
	@Override public boolean renderBlockFenceGate(BlockFenceGate p_147776_1_, int p_147776_2_, int p_147776_3_, int p_147776_4_) { return rb.renderBlockFenceGate(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_); }
	@Override public boolean renderBlockHopper(BlockHopper p_147803_1_, int p_147803_2_, int p_147803_3_, int p_147803_4_) { return rb.renderBlockHopper(p_147803_1_, p_147803_2_, p_147803_3_, p_147803_4_); }
	@Override public boolean renderBlockHopperMetadata(BlockHopper p_147799_1_, int p_147799_2_, int p_147799_3_, int p_147799_4_, int p_147799_5_, boolean p_147799_6_) { return rb.renderBlockHopperMetadata(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_, p_147799_5_, p_147799_6_); }
	@Override public boolean renderBlockStairs(BlockStairs p_147722_1_, int p_147722_2_, int p_147722_3_, int p_147722_4_) { return rb.renderBlockStairs(p_147722_1_, p_147722_2_, p_147722_3_, p_147722_4_); }
	@Override public boolean renderBlockDoor(Block p_147760_1_, int p_147760_2_, int p_147760_3_, int p_147760_4_) { return rb.renderBlockDoor(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_); }
	@Override public void renderFaceYNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_) { rb.renderFaceYNeg(p_147768_1_, p_147768_2_, p_147768_4_, p_147768_6_, p_147768_8_); }
	@Override public void renderFaceYPos(Block p_147806_1_, double p_147806_2_, double p_147806_4_, double p_147806_6_, IIcon p_147806_8_) { rb.renderFaceYPos(p_147806_1_, p_147806_2_, p_147806_4_, p_147806_6_, p_147806_8_); }
	@Override public void renderFaceZNeg(Block p_147761_1_, double p_147761_2_, double p_147761_4_, double p_147761_6_, IIcon p_147761_8_) { rb.renderFaceZNeg(p_147761_1_, p_147761_2_, p_147761_4_, p_147761_6_, p_147761_8_); }
	@Override public void renderFaceZPos(Block p_147734_1_, double p_147734_2_, double p_147734_4_, double p_147734_6_, IIcon p_147734_8_) { rb.renderFaceZPos(p_147734_1_, p_147734_2_, p_147734_4_, p_147734_6_, p_147734_8_); }
	@Override public void renderFaceXNeg(Block p_147798_1_, double p_147798_2_, double p_147798_4_, double p_147798_6_, IIcon p_147798_8_) { rb.renderFaceXNeg(p_147798_1_, p_147798_2_, p_147798_4_, p_147798_6_, p_147798_8_); }
	@Override public void renderFaceXPos(Block p_147764_1_, double p_147764_2_, double p_147764_4_, double p_147764_6_, IIcon p_147764_8_) { rb.renderFaceXPos(p_147764_1_, p_147764_2_, p_147764_4_, p_147764_6_, p_147764_8_); }
	@Override public void renderBlockAsItem(Block p_147800_1_, int p_147800_2_, float p_147800_3_) { rb.renderBlockAsItem(p_147800_1_, p_147800_2_, p_147800_3_); }
	@Override public IIcon getBlockIcon(Block p_147793_1_, IBlockAccess p_147793_2_, int p_147793_3_, int p_147793_4_, int p_147793_5_, int p_147793_6_) { return rb.getBlockIcon(p_147793_1_, p_147793_2_, p_147793_3_, p_147793_4_, p_147793_5_, p_147793_6_); }
	@Override public IIcon getBlockIconFromSideAndMetadata(Block block, int side, int meta) { return rb.getBlockIconFromSideAndMetadata(block, side, meta); }
	@Override public IIcon getBlockIconFromSide(Block block, int side) { return rb.getBlockIconFromSide(block, side); }
	@Override public IIcon getBlockIcon(Block block) { return rb.getBlockIcon(block); }
	@Override public IIcon getIconSafe(IIcon icon) { return rb.getIconSafe(icon); }
}
