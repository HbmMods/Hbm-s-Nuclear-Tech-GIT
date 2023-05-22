# HBM's Nuclear Tech Mod for Minecraft 1.7.10

[NTM on Modrinth](https://modrinth.com/mod/ntm)

[NTM on CurseForge](https://minecraft.curseforge.com/projects/hbms-nuclear-tech-mod?gameCategorySlug=mc-mods&projectID=235439)

**This is for 1.7.10!** For 1.12, check out these projects:

* NTM Reloaded: https://github.com/TheOriginalGolem/Hbm-s-Nuclear-Tech-GIT/releases
* NTM Extended Edition (Alcater): https://github.com/Alcatergit/Hbm-s-Nuclear-Tech-GIT/releases

For 1.18, try Martin's remake: https://github.com/MartinTheDragon/Nuclear-Tech-Mod-Remake/releases

## Downloading pre-compiled versions from GitHub

Simply navigate to "Releases" on the right side of the page, download links for the compiled JAR as well as the corresponding source code are under the "Assets" category below the changelog. Make sure to review all changelogs when updating!

## Building from source

Tired of waiting until the next version comes out? Here is a tutorial on how to compile the very newest version yourself:
Please note that these installation instructions are assuming you're running Microsoft Windows operating system. Linux users should know what to do by looking at the same guide.

 1. Make sure you have JDK8 installed. If not, download it from [adoptium.net](https://adoptium.net/?variant=openjdk8&jvmVariant=hotspot)
 2. If you don't have git installed, download&install it from [here](https://git-scm.com/downloads).
 3. Open up "Git Bash":
    * Press Windows Button, type "Git Bash" and press ENTER
 4. Enter the directory where you would like the sources to be (advanced users can use any directory)
 ```bash
     cd $HOME/Downloads
 ```
 5. Download the source code:
 ```bash
     git clone https://github.com/HbmMods/Hbm-s-Nuclear-Tech-GIT.git
 ```
 4. Enter the source code directory
 ```bash
     cd Hbm-s-Nuclear-Tech-GIT
 ```
 5. Build the mod
 ```bash
     ./gradlew build
 ```
 6. Locate the mod file.
    1. Open up your file explorer.
    2. Navigate to the location where you downloaded the sources.
       * If you exactly followed step 1, it should be `C:/Users/%USER%/Downloads`.
    3. Enter the downloaded source tree.
    4. Navigate to `build/libs`.
    5. Grab the "HBM-NTM-<version>.jar" one.
        * This is your mod file. You can install it like any other mod by putting it into your mods directory.

## Contributing
If you want to make some changes to the mod, follow this guide:
1. Follow steps 1-2 from *Building from source* section
2. Create a directory where the repository will reside, using a name that is not "Hbm-s-Nuclear-Tech-GIT"
3. Download the forge src from [here](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.7.10.html) and extract it into the directory.
4. Download the source code:
  * Using Git Bash, enter wherever your directory is located:
```bash
    cd $HOME/Downloads
```
   * Download the source code:
```bash
    git clone https://github.com/HbmMods/Hbm-s-Nuclear-Tech-GIT.git
 ```
   * Move or copy every file within the new folder into your directory, making sure to overwrite any files.
   * Feel free to delete the remaining folder and rename your directory (such as "Hbm-s-Nuclear-Tech-GIT")
5. Enter the source directory
```bash
    cd Hbm-s-Nuclear-Tech-GIT
```
6. Setup forge decompilation workspace
```bash
    ./gradlew setupDecompWorkspace
```
### Necessary for Eclipse users
7. Generate eclipse files
```bash
    ./gradlew eclipse
```
8. Switch to the **eclipse** folder inside your directory as a workspace.
9. If necessary, make sure that Eclipse is using the JDK8.
   * On Linux, enter Windows>Preferences>Java>Installed JREs.
      * Click search to navigate to /usr/lib/jvm and open it. Select the Java 8 JDK (e.g., java-8-openjdk).
      * Afterwards, enter Execution Environment, select JavaSE-1.8, and select the jre listed as a **[perfect match]**
   * On Windows, you may need to set your JAVA_HOME.
      * Search for Environment Variables and click Edit the System Environment Variables.
      * Click Environment Variables. Click new under System Variables.
      * Enter **JAVA_HOME** under Variable Name and enter the path to your JDK 8 under Variable Value (e.g., C:\Program-Files\Java\jdk1.8.0_102).
      * In Eclipse, now enter Windows>Preferences>Java>Installed JREs.
      * Click **Add Standard VM**; in the JRE home, navigate to the directory where the JDK is installed, then click finish and select it.
10. Code!

## Compatibility notice
NTM has certain behaviors intended to fix vanilla code or to increase compatibility in certain cases where it otherwise would not be possible. These behaviors have the potential of not playing well with other mods, and while no such cases are currently known, here's a list of them.

### Skybox chainloader
NTM adds a few small things to the skybox using a custom skybox renderer. Minecraft can only have a single skybox renderer loaded, so setting the skybox to the NTM custom one would break compatibility with other mods' skyboxes. To mend this, NTM employs a **chainloader**. This chainloader will detect if a different skybox is loaded, save a reference to that skybox and then use NTM's skybox, which when used will also make sure to run the previous modded skybox renderer. In the event that NTM's skybox were to cause trouble, it can be disabled with the config option `1.31_enableSkyboxes`.

### Custom world provider
A world provider is a piece of code that minecraft can load to determine certain aspects of how the world should be handled, like light levels, sky color, day/night cycle, etc. In order for the Tom impact effects to work, NTM employs such a world provider, although this is known to cause issues with Hardcore Darkness. The world provider can be disabled with the config option `1.32_enableImpactWorldProvider`.

### Stat re-registering
An often overlooked aspect of Minecraft is its stats, the game keeps track of how many of an item were crafted, placed, broken, etc. By default, Minecraft can only handle vanilla items, modded items would not show up in the stats window. Forge does little to fix this, and since NTM has to keep track of certain things (such as the use of an acidizer for spawning Mask Man) it will run its own code which re-registers all stats for all modded items. In the event that re-registering causes issues, or another mod already does this better already, this behavior can be disabled with the config option `1.33_enableStatReRegistering`.

### Keybind overlap
An often annoying aspect of modded Minecraft is its keybinds. Even though multiple binds can be assigned the same key, all but one will show up as "conflicting" and only the non-conflicting one will work. Which one this is is usually arbitrary, and there is no reason to have such limitation. Often times keybinds are only applicable in certain scenarios, and a commonly found degree of overlap is within reason. Therefore, NTM will run its own key handling code which allows conflicting keybinds to work. If there should be any issues with this behavior, it can be disabled with the config option `1.34_enableKeybindOverlap`.

# License
This software is licensed under the GNU Lesser General Public License version 3. In short: This software is free, you may run the software freely, create modified versions, distribute this software and distribute modified versions, as long as the modified software too has a free software license (with an exception for linking to this software, as stated by the "Lesser" part of the LGPL, where this may not be required). You win this round, Stallman. The full license can be found in the `LICENSE` and `LICENSE.LESSER` files.
