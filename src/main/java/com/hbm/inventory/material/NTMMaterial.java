package com.hbm.inventory.material;

/**
 * Encapsulates most materials that are currently listed as DictFrames, even vanilla ones.
 * @author hbm
 *
 */
public class NTMMaterial {

	public String[] names;
	public MaterialShapes[] shapes = new MaterialShapes[0];
	public boolean omitItemGen = false;
	public boolean smeltable = false;
	
	public NTMMaterial(String... names) {
		this.names = names;
	}
	
	public NTMMaterial setShapes(MaterialShapes... shapes) {
		this.shapes = shapes;
		return this;
	}
	
	public NTMMaterial omit() {
		this.omitItemGen = true;
		return this;
	}
	
	public NTMMaterial smeltable() {
		this.smeltable = true;
		return this;
	}
}
