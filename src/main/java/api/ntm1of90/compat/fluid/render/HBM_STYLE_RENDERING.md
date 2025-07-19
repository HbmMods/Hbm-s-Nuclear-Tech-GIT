# HBM-Style Fluid Rendering Implementation

This document describes the implementation of HBM-style fluid rendering for the NTM 1 of 90 API, which replaces PNG-based textures with color-tinted base textures.

## Overview

The HBM-style rendering system detects HBM fluids that use `renderWithTint = true` or have meaningful colors, and renders them using base textures with OpenGL color tinting instead of individual PNG files.

## Key Components

### 1. HBMStyleFluidRenderer
- **Location**: `src/main/java/api/ntm1of90/compat/fluid/render/HBMStyleFluidRenderer.java`
- **Purpose**: Main class that handles HBM-style fluid rendering
- **Features**:
  - Detects which fluids should use tinted rendering
  - Manages base texture types (liquid, gas, viscous, plasma, antimatter, corrosive)
  - Provides texture registration and color application methods
  - Caches fluid type mappings for performance

### 2. Base Texture System
- **Location**: `src/main/resources/assets/hbm/textures/blocks/fluids/`
- **Base Types**:
  - `liquid_base`: Standard liquid fluids
  - `gas_base`: Gaseous fluids
  - `viscous_base`: Thick/viscous fluids
  - `plasma_base`: Plasma fluids
  - `antimatter_base`: Antimatter fluids
  - `corrosive_base`: Corrosive fluids
  - `fluid_base`: Default fallback
- **Variants**: Each type has `_still.png`, `_flowing.png`, and `.png` (inventory) variants

### 3. Enhanced ColoredForgeFluid
- **Location**: `src/main/java/api/ntm1of90/compat/fluid/render/ColoredForgeFluid.java`
- **Enhancements**:
  - Added `useHBMStyleRendering` flag
  - Modified `getStillIcon()` and `getFlowingIcon()` to use base textures when appropriate
  - Automatic detection of HBM-style rendering during construction

### 4. Updated FluidRegistry
- **Location**: `src/main/java/api/ntm1of90/compat/fluid/registry/FluidRegistry.java`
- **Changes**:
  - Registers base textures during texture stitching
  - Chooses between HBM-style and PNG-based rendering per fluid
  - Integrates with HBMStyleFluidRenderer for texture management

### 5. Enhanced Rendering
- **Location**: `src/main/java/api/ntm1of90/compat/fluid/render/NTMFluidCompatRenderer.java`
- **Features**:
  - Detects HBM-style fluids and applies color tinting
  - Falls back to traditional PNG rendering for non-HBM fluids
  - Added `renderFluidWithIcon()` method for pre-tinted rendering

## How It Works

### 1. Fluid Detection
```java
public static boolean shouldUseTintedRendering(FluidType hbmFluid) {
    // Check if fluid has renderWithTint flag
    if (hbmFluid.renderWithTint) return true;
    
    // Check if fluid has meaningful color
    int color = hbmFluid.getColor();
    if (color != 0xFFFFFF && color != 0x000000 && color != 0) return true;
    
    return false;
}
```

### 2. Base Texture Selection
```java
public static BaseTextureType determineBaseTextureType(FluidType hbmFluid) {
    if (hbmFluid.isAntimatter()) return BaseTextureType.ANTIMATTER;
    if (hbmFluid.isCorrosive()) return BaseTextureType.CORROSIVE;
    if (hbmFluid.getName().toLowerCase().contains("plasma")) return BaseTextureType.PLASMA;
    if (hbmFluid.hasTrait(FT_Gaseous.class)) return BaseTextureType.GAS;
    if (hbmFluid.hasTrait(FT_Viscous.class)) return BaseTextureType.VISCOUS;
    return BaseTextureType.LIQUID;
}
```

### 3. Color Application
```java
public static void applyFluidTint(FluidType hbmFluid) {
    int color = hbmFluid.renderWithTint ? hbmFluid.getTint() : hbmFluid.getColor();
    double r = ((color & 0xff0000) >> 16) / 255.0;
    double g = ((color & 0x00ff00) >> 8) / 255.0;
    double b = ((color & 0x0000ff) >> 0) / 255.0;
    GL11.glColor3d(r, g, b);
}
```

## Benefits

1. **Memory Efficiency**: Uses fewer texture files by sharing base textures
2. **Dynamic Colors**: Colors can be changed at runtime without new textures
3. **Consistency**: Matches HBM's rendering approach exactly
4. **Backward Compatibility**: Falls back to PNG textures for non-HBM fluids
5. **Performance**: Caches fluid type mappings to avoid repeated calculations

## Integration Points

### Initialization
The system is initialized in `MainRegistry.java`:
```java
api.ntm1of90.compat.fluid.render.HBMStyleFluidRenderer.initialize();
```

### Texture Registration
Base textures are registered during texture stitching events in `FluidRegistry.java`.

### Rendering
The system integrates with existing rendering pipelines in `NTMFluidCompatRenderer.java`.

## Future Enhancements

1. **Custom Base Textures**: Create proper artwork for each base texture type
2. **Animation Support**: Add support for animated base textures
3. **Shader Integration**: Potential integration with shader-based rendering
4. **Performance Optimization**: Further optimize texture caching and color application

## Testing

To test the implementation:
1. Run the mod and check console output for fluid analysis
2. Look for messages like "Fluid 'X' will use tinted rendering with base: Y"
3. Verify that fluids with `renderWithTint = true` use base textures
4. Confirm that colors are applied correctly in GUIs and world rendering

## Notes

- Base texture files need to be created (currently only placeholders exist)
- The system automatically detects which fluids should use HBM-style rendering
- PNG-based rendering is still used for fluids that don't meet the criteria
- The implementation is fully backward compatible with existing systems
