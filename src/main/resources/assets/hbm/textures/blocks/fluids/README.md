# HBM-Style Fluid Base Textures

This directory contains base textures for HBM-style fluid rendering. These textures are designed to be tinted with colors at runtime, similar to how HBM renders fluids.

## Base Texture Types

- **liquid_base**: For standard liquid fluids
- **gas_base**: For gaseous fluids  
- **viscous_base**: For viscous/thick fluids
- **plasma_base**: For plasma fluids
- **antimatter_base**: For antimatter fluids
- **corrosive_base**: For corrosive fluids
- **fluid_base**: Default fallback texture

## Texture Variants

Each base type has three variants:
- `*_still.png`: For still/static fluid blocks
- `*_flowing.png`: For flowing fluid blocks  
- `*.png`: For inventory/GUI rendering

## How It Works

1. The system detects HBM fluids that have `renderWithTint = true` or meaningful colors
2. Instead of using individual PNG files, it uses these base textures
3. Colors are applied at render time using OpenGL tinting
4. This reduces texture memory usage and allows dynamic color changes

## Creating Base Textures

Base textures should be:
- 16x16 pixels for block textures
- Grayscale or neutral colored (white/light gray works best for tinting)
- Designed to look good when tinted with various colors
- Simple patterns that represent the fluid type (bubbles for gas, thick for viscous, etc.)
