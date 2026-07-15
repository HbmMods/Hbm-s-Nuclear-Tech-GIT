package com.hbm.world.gen.nbt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NBTStructureSafetyTest {

	@Test
	public void acceptsBoundedStructureDimensions() {
		assertTrue(NBTStructure.isSizeValid(64, 64, 64));
		assertFalse(NBTStructure.isSizeValid(0, 1, 1));
		assertFalse(NBTStructure.isSizeValid(257, 1, 1));
		assertFalse(NBTStructure.isSizeValid(256, 256, 256));
	}

	@Test
	public void rejectsPathTraversalAndSubdirectories() {
		assertTrue(NBTStructure.isSafeStructureFilename("reactor-01.nbt"));
		assertFalse(NBTStructure.isSafeStructureFilename("../server.nbt"));
		assertFalse(NBTStructure.isSafeStructureFilename("folder/structure.nbt"));
		assertFalse(NBTStructure.isSafeStructureFilename("structure.dat"));
	}
}
