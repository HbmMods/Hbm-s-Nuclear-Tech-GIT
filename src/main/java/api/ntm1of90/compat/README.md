# NTM Forge Fluid Compatibility API

This package contains utilities and APIs for providing compatibility between HBM's Nuclear Tech Mod's custom fluid system and Forge's standard fluid system, allowing for compatibility with mods that use either system.

## Overview

The NTM Forge Fluid Compatibility API provides several key components:

1. **FluidMappingRegistry**: Maps between HBM's FluidType and Forge's Fluid
2. **NTMFluidNetworkAdapter**: Adapts NTM fluid tanks to work with Forge's IFluidHandler interface
3. **NTMFluidNetworkBridge**: Transfers fluids between NTM's network and Forge's fluid system
4. **ProxyForgeAdapter**: Adapts proxy tile entities to work with Forge's IFluidHandler interface for multiblock structures

## Using ProxyForgeAdapter

The `ProxyForgeAdapter` class is designed to make multiblock structures compatible with Forge's fluid system. It extends `TileEntityProxyBase` and implements `IFluidHandler`, allowing proxy tile entities in multiblock structures to interact with other mods' fluid pipes.

### Example Usage

```java
// In your block class that creates a multiblock structure
@Override
public TileEntity createNewTileEntity(World world, int meta) {
    if(meta >= 12) return new YourForgeCompatibleTileEntity();
    if(meta >= 6) return new ProxyForgeAdapter(false, false, true);
    return null;
}
```

### Implementation Details

The `ProxyForgeAdapter` class delegates all fluid-related methods to the main tile entity, ensuring that fluid operations are properly handled by the multiblock structure's controller.

It also includes additional compatibility methods for various mods:
- `canConnectFluid(ForgeDirection from)`
- `isConnectable(ForgeDirection from)`
- `canInterface(ForgeDirection from)`
- And many others

## Creating Forge-Compatible Tile Entities

To create a Forge-compatible tile entity, you need to:

1. Implement the `IFluidHandler` interface
2. Implement the required methods:
   - `fill(ForgeDirection from, FluidStack resource, boolean doFill)`
   - `drain(ForgeDirection from, FluidStack resource, boolean doDrain)`
   - `drain(ForgeDirection from, int maxDrain, boolean doDrain)`
   - `canFill(ForgeDirection from, Fluid fluid)`
   - `canDrain(ForgeDirection from, Fluid fluid)`
   - `getTankInfo(ForgeDirection from)`

3. Use the `FluidMappingRegistry` to convert between HBM's FluidType and Forge's Fluid

### Example Implementation

```java
public class YourForgeCompatibleTileEntity extends YourBaseTileEntity implements IFluidHandler {

    static {
        // Initialize the fluid mapping registry
        FluidMappingRegistry.initialize();
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource == null || resource.amount <= 0) {
            return 0;
        }

        // Convert Forge fluid to HBM fluid
        FluidType hbmType = FluidMappingRegistry.getHbmFluidType(resource.getFluid());
        if (hbmType == null || hbmType == Fluids.NONE) {
            return 0; // Unknown fluid type
        }

        // Implement your fill logic here
        // ...
    }

    // Implement other IFluidHandler methods
    // ...
}
```

## Additional Compatibility Methods

For maximum compatibility with various mods, it's recommended to implement these additional methods:

```java
public boolean canConnectFluid(ForgeDirection from) {
    return true;
}

public boolean isConnectable(ForgeDirection from) {
    return true;
}

public boolean canInterface(ForgeDirection from) {
    return true;
}

public boolean canInputFluid(ForgeDirection from) {
    return true;
}

public boolean canOutputFluid(ForgeDirection from) {
    return true;
}
```

These methods are used by various mods to check if a tile entity can connect to fluid pipes.

## API Design Notes

The `ProxyForgeAdapter` class is designed to be a drop-in replacement for `TileEntityProxyCombo` in multiblock structures that need to interact with Forge fluid pipes. It maintains its own state for fluid handling capabilities, rather than relying on the internal state of `TileEntityProxyCombo`, making it more suitable for use in an API context.

This design allows for better encapsulation and avoids exposing internal implementation details of the mod, making the API more stable and easier to maintain.
