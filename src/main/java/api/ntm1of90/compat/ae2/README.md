# Applied Energistics 2 Compatibility API

This API provides compatibility between HBM's Nuclear Tech Mod and Applied Energistics 2. It allows for fluid storage and autocrafting using HBM's custom fluid system.

## Features

- Fluid storage in AE2 networks
- Fluid autocrafting
- Fluid cell rendering with proper colors
- Fluid terminal integration

## Usage

### Basic Usage

The API is automatically initialized when the main NTM1of90 API is initialized:

```java
NTM1of90API.initialize();
```

This should be done during mod initialization.

## Implementation Details

The API uses a bridge approach to provide compatibility between HBM's custom fluid system and AE2's fluid storage system. It does not modify AE2's core functionality, but instead provides adapters and converters that work alongside it.

The main components of the API are:

1. **AE2FluidCompat**: Main entry point for the AE2 compatibility API
2. **AE2FluidStorageHandler**: Handles fluid storage in AE2 networks
3. **AE2FluidCellRenderer**: Renders fluid cells with proper colors
4. **AE2FluidGUIHandler**: Handles GUI integration for fluid terminals

## Notes

- This API is designed to work with Applied Energistics 2 rv2-beta-33 and later
- It is compatible with HBM's Nuclear Tech Mod 1.0.27 and later
