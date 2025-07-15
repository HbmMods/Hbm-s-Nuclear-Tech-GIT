# Forge Fluid Compatibility API

This API provides compatibility between HBM's Nuclear Tech Mod's custom fluid system and Forge's standard fluid system. It allows for seamless interaction between mods that use either system.

## Features

- Automatic mapping between HBM's FluidType and Forge's Fluid
- Adapter classes for implementing Forge's IFluidHandler interface for HBM tile entities
- Texture mapping for proper rendering of HBM fluids in Forge-compatible containers
- Localization support for fluid names
- Color information for fluid rendering

## Usage

### Basic Usage

To initialize the API, call:

```java
ForgeFluidCompatManager.initialize();
```

This should be done during mod initialization.

### Converting between HBM and Forge fluids

To convert between HBM's FluidType and Forge's Fluid:

```java
// Convert from Forge Fluid to HBM FluidType
FluidType hbmFluid = FluidMappingRegistry.getHbmFluidType(forgeFluid);

// Convert from HBM FluidType to Forge Fluid
Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(hbmFluid);
```

### Getting a Forge IFluidHandler for an HBM tile entity

To get a Forge IFluidHandler for an HBM tile entity:

```java
IFluidHandler handler = ForgeFluidAdapterRegistry.getFluidHandler(tileEntity);
```

### Registering a custom fluid mapping

To register a custom mapping between a Forge fluid name and an HBM FluidType:

```java
FluidMappingRegistry.registerFluidMapping("forge_fluid_name", hbmFluidType);
```

## Package Structure

- `adapter`: Contains adapter classes that implement Forge's IFluidHandler interface for HBM's fluid system
- `bridge`: Contains bridge classes that connect HBM's fluid network to Forge's fluid system
- `registry`: Contains registry classes for mapping between HBM's FluidType and Forge's Fluid
- `render`: Contains rendering-related classes for fluid compatibility
- `util`: Contains utility classes for fluid compatibility

## Implementation Details

The API uses a non-invasive approach to provide compatibility between the two fluid systems. It does not modify HBM's core fluid system or Forge's fluid system, but instead provides a bridge between them.

The main components of the API are:

1. **FluidMappingRegistry**: Maps between HBM's FluidType and Forge's Fluid
2. **ForgeFluidAdapterRegistry**: Provides IFluidHandler implementations for HBM tile entities
3. **ForgeFluidCapabilityHook**: Hooks into Forge's capability system to provide IFluidHandler capabilities for HBM tile entities
4. **NTMFluidTextureMapper**: Maps fluid textures between the two systems
5. **NTMFluidLocalization**: Provides localization support for fluid names

## Notes

- This API is designed to work with Forge 1.7.10
- It is compatible with HBM's Nuclear Tech Mod 1.0.27 and later
- It does not replace HBM's fluid system, but provides a bridge to Forge's fluid system
