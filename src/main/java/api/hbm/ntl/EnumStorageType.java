package api.hbm.ntl;

@Deprecated
public enum EnumStorageType {
	CLUTTER,	//potentially unsorted storage (like crates) with many slots that have low capacity
	MASS		//storage with very few lots (usually 1) and very high capacity
}
