# HOW TO USE
# Make sure all your animation actions start on frame 0 and are named as such:
#     Name.Part
# and run the export, they'll be split into Animation groups with each part being assigned as a Bus.
# EG. Reload.Body will apply an animation called Reload to the bus called Body
# For best results, make sure your object Transform Mode is set to YZX Euler, so rotations match in-game
# When importing, you can use the Action Editor to assign the imported animations to parts to view and modify them

bl_info = {
    "name": "Export JSON Animation",
    "blender": (2, 79, 0),
    "category": "Import-Export",
}

import bpy
import json
import math
import mathutils

from bpy_extras.io_utils import ExportHelper, ImportHelper
from bpy.props import StringProperty, BoolProperty, EnumProperty
from bpy.types import Operator


class ExportJSONAnimation(Operator, ExportHelper):
    """Exports an animation in a NTM JSON format"""
    bl_idname = "export.ntm_json"        # Unique identifier for buttons and menu items to reference.
    bl_label = "Export NTM .json"         # Display name in the interface.
    bl_options = {'REGISTER'}
    
    # ExportHelper mix-in class uses this.
    filename_ext = ".json"

    filter_glob = StringProperty(
        default="*.json",
        options={'HIDDEN'},
        maxlen=255,  # Max internal buffer length, longer would be clamped.
    )

    def execute(self, context):        # execute() is called when running the operator.
        print("writing JSON data to file...")
        f = open(self.filepath, 'w', encoding='utf-8')

        collection = {"anim": {}, "offset": {}, "hierarchy": {}, "rotmode": {}}
        dimensions = ["x", "z", "y"] # Swizzled to X, Z, Y
        mult = [1, -1, 1] # +X, -Z, +Y

        # Reset to first frame, so our offsets are set to model defaults
        # If you get weird offset issues, make sure your model is in its rest pose before exporting
        context.scene.frame_set(0)

        animations = collection['anim']
        offsets = collection['offset']
        modes = collection['rotmode']
        hierarchy = collection['hierarchy']

        actions = bpy.data.actions
        for action in actions:
            split = action.name.split('.')
            if len(split) != 2:
                continue
            name = split[0]
            part = split[1]
            
            if name not in animations:
                animations[name] = {}
                
            animations[name][part] = {}
            animation = animations[name][part]
            
            # Fetch all the animation data
            for fcu in action.fcurves:
                dimension = dimensions[fcu.array_index]
                
                if not fcu.data_path in animation:
                    animation[fcu.data_path] = {}
                if not dimension in animation[fcu.data_path]:
                    animation[fcu.data_path][dimension] = []
                
                multiplier = mult[fcu.array_index]
                if fcu.data_path == 'rotation_euler':
                    multiplier *= 180 / math.pi
                    
                previousMillis = 0
                previousInterpolation = ""
                     
                for keyframe in fcu.keyframe_points:
                    timeToFrame = keyframe.co.x * (1 / context.scene.render.fps) * 1000
                    millis = timeToFrame - previousMillis
                    value = keyframe.co.y * multiplier
                    tuple = [value, millis, keyframe.interpolation, keyframe.easing]
                    if previousInterpolation == "BEZIER":
                        tuple.append(keyframe.handle_left.x * (1 / context.scene.render.fps) * 1000)
                        tuple.append(keyframe.handle_left.y * multiplier)
                        tuple.append(keyframe.handle_left_type)
                    if keyframe.interpolation == "BEZIER":
                        tuple.append(keyframe.handle_right.x * (1 / context.scene.render.fps) * 1000)
                        tuple.append(keyframe.handle_right.y * multiplier)
                        tuple.append(keyframe.handle_right_type)
                    if keyframe.interpolation == "ELASTIC":
                        tuple.append(keyframe.amplitude)
                        tuple.append(keyframe.period)
                    if keyframe.interpolation == "BACK":
                        tuple.append(keyframe.back)
                    previousMillis = timeToFrame
                    previousInterpolation = keyframe.interpolation
                    animation[fcu.data_path][dimension].append(tuple)
                    
        for object in bpy.data.objects:
            if object.type != 'MESH':
                continue
            
            if object.parent:
                hierarchy[object.name] = object.parent.name
            
            if object.location != mathutils.Vector(): # don't export 0,0,0
                offsets[object.name] = [object.location.x, object.location.z, -object.location.y]
            
            if object.rotation_mode != 'YZX':
                modes[object.name] = object.rotation_mode


        
        f.write(json.dumps(collection))
        f.close()

        return {'FINISHED'}            # Lets Blender know the operator finished successfully.





class ImportJSONAnimation(Operator, ImportHelper):
    """Imports an animation from a NTM JSON format"""
    bl_idname = "import.ntm_json"  # important since its how bpy.ops.import_test.some_data is constructed
    bl_label = "Import NTM .json"
    bl_options = {'REGISTER'}

    # ImportHelper mix-in class uses this.
    filename_ext = ".json"

    filter_glob = StringProperty(
        default="*.json",
        options={'HIDDEN'},
        maxlen=255,  # Max internal buffer length, longer would be clamped.
    )
    
    def execute(self, context):
        print("reading JSON data from file...")
        f = open(self.filepath, 'r', encoding='utf-8')
        data = f.read()
        f.close()
        
        dimensions = ["x", "z", "y"] # Swizzled to X, Z, Y
        mult = [1, -1, 1] # +X, -Z, +Y

        collection = json.loads(data)
        animations = collection["anim"]
        for name in animations:
            for part in animations[name]:
                actionName = name + '.' + part
                animation = animations[name][part]
                action = bpy.data.actions.find(actionName) >= 0 and bpy.data.actions[actionName] or bpy.data.actions.new(actionName)
                
                action.use_fake_user = True
                
                # Keep the actions, in case they're already associated with objects
                # but remove the frames to replace with fresh ones
                for fcurve in action.fcurves:
                    action.fcurves.remove(fcurve)
                # action.fcurves.clear()
                
                for path in animation:
                    for dimension in animation[path]:
                        dimIndex = dimensions.index(dimension)
                        curve = action.fcurves.new(path, index=dimIndex)
                        
                        multiplier = mult[dimIndex]
                        if path == 'rotation_euler':
                            multiplier *= math.pi / 180
                        
                        millis = 0
                        previousInterpolation = ''
                        
                        for tuple in animation[path][dimension]:
                            value = tuple[0] * multiplier
                            millis = millis + tuple[1]
                            frame = round(millis * context.scene.render.fps / 1000)
                            
                            keyframe = curve.keyframe_points.insert(frame, value)
                            keyframe.interpolation = 'LINEAR'
                            if len(tuple) >= 3:
                                keyframe.interpolation = tuple[2]
                                
                            if len(tuple) >= 4:
                                keyframe.easing = tuple[3]
                                
                            i = 4
                            
                            if previousInterpolation == 'BEZIER':
                                keyframe.handle_left.x = tuple[i] * context.scene.render.fps / 1000
                                i += 1
                                keyframe.handle_left.y = tuple[i] * multiplier
                                i += 1
                                keyframe.handle_left_type = tuple[i]
                                i += 1
                            if keyframe.interpolation == 'BEZIER':
                                keyframe.handle_right.x = tuple[i] * context.scene.render.fps / 1000
                                i += 1
                                keyframe.handle_right.y = tuple[i] * multiplier
                                i += 1
                                keyframe.handle_right_type = tuple[i]
                                i += 1
                            if keyframe.interpolation == 'ELASTIC':
                                keyframe.amplitude = tuple[i]
                                i += 1
                                keyframe.period = tuple[i]
                                i += 1
                            if keyframe.interpolation == 'BACK':
                                keyframe.back = tuple[i]
                                i += 1
                                
                            previousInterpolation = keyframe.interpolation

        for object in bpy.data.objects:
            if object.type != 'MESH':
                continue
                
            bpy.ops.object.select_all(action='DESELECT')
            object.select = True
            bpy.ops.object.transform_apply(location=False, rotation=True, scale=False)
            object.rotation_mode = 'YZX'
        
        if 'rotmode' in collection:
            modes = collection['rotmode']
            for mode in modes:
                bpy.data.objects[mode].rotation_mode = modes[mode]

        if 'hierarchy' in collection:
            hierarchy = collection['hierarchy']
            for name in hierarchy:
                parent = hierarchy[name]
                
                bpy.data.objects[name].parent = bpy.data.objects[parent]
                            
        offsets = collection['offset']
        for name in offsets:
            offset = offsets[name]
            
            for object in bpy.data.objects:
                if object.type != 'MESH':
                    continue
                
                bpy.ops.object.select_all(action='DESELECT')
                object.select = True
                
                if object.name == name:
                    savedLocation = bpy.context.scene.cursor_location
                    bpy.context.scene.cursor_location = (-offset[0], -offset[2], offset[1])
                    bpy.ops.object.origin_set(type='ORIGIN_CURSOR')
                    bpy.context.scene.cursor_location = savedLocation
        
        return {'FINISHED'}
    
    
    



def menu_export(self, context):
    self.layout.operator(ExportJSONAnimation.bl_idname)
    
def menu_import(self, context):
    self.layout.operator(ImportJSONAnimation.bl_idname)

def register():
    bpy.utils.register_class(ExportJSONAnimation)
    bpy.utils.register_class(ImportJSONAnimation)
    if hasattr(bpy.types, "TOPBAR_MT_file_export"):
        bpy.types.TOPBAR_MT_file_export.append(menu_export)
        bpy.types.TOPBAR_MT_file_import.append(menu_import)
    elif hasattr(bpy.types, "INFO_MT_file_export"):
        bpy.types.INFO_MT_file_export.append(menu_export)
        bpy.types.INFO_MT_file_import.append(menu_import)

def unregister():
    bpy.utils.unregister_class(ExportJSONAnimation)
    bpy.utils.unregister_class(ImportJSONAnimation)
    if hasattr(bpy.types, "TOPBAR_MT_file_export"):
        bpy.types.TOPBAR_MT_file_export.remove(menu_export)
        bpy.types.TOPBAR_MT_file_import.remove(menu_import)
    elif hasattr(bpy.types, "INFO_MT_file_export"):
        bpy.types.INFO_MT_file_export.remove(menu_export)
        bpy.types.INFO_MT_file_import.remove(menu_import)


# This allows you to run the script directly from Blender's Text editor
# to test the add-on without having to install it.
if __name__ == "__main__":
    register()
