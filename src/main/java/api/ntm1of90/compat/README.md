# NTM Compatibility API

This package contains compatibility APIs for NTM1of90's extensions to HBM's Nuclear Tech Mod. It provides utilities and APIs for interacting with other mods.

## Subpackages

The NTM Compatibility API is organized into the following subpackages:

1. **fluid**: Contains the Forge Fluid Compatibility API, which provides compatibility between HBM's custom fluid system and Forge's standard fluid system.
2. **ae2**: Contains the Applied Energistics 2 Compatibility API, which provides compatibility with Applied Energistics 2 for fluid storage and autocrafting.

## Forge Fluid Compatibility API

The Forge Fluid Compatibility API is organized into the following subpackages:

1. **adapter**: Contains adapter classes that implement Forge's IFluidHandler interface for HBM's fluid system.
2. **bridge**: Contains bridge classes that connect HBM's fluid network to Forge's fluid system.
3. **registry**: Contains registry classes for mapping between HBM's FluidType and Forge's Fluid.
4. **render**: Contains rendering-related classes for fluid compatibility.
5. **util**: Contains utility classes for fluid compatibility.

## Usage

For detailed usage instructions, see the README.md files in the respective subpackages:

- [Forge Fluid Compatibility API](fluid/README.md)
- [Applied Energistics 2 Compatibility API](ae2/README.md)

## Main Entry Point

The main entry point for the NTM Compatibility API is the `NTM1of90API` class in the `api.ntm1of90` package. This class provides methods for initializing the API and accessing its components.

```java
// Initialize the API
NTM1of90API.initialize();

// Check if the API has been initialized
boolean initialized = NTM1of90API.isInitialized();

// Get the API version
String version = NTM1of90API.getVersion();
```

## Implementation Details

The NTM Compatibility API uses a modular design to provide compatibility with various mods. Each compatibility module is organized into its own subpackage, with its own entry point and documentation.

This design allows for better encapsulation and avoids exposing internal implementation details of the mod, making the API more stable and easier to maintain.
