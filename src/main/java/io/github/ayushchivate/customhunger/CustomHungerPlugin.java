package io.github.ayushchivate.customhunger;

import net.dohaw.corelib.CoreLib;
import net.dohaw.corelib.JPUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class CustomHungerPlugin extends JavaPlugin {

    private CustomHungerConfig customHungerConfig;
    private CustomHungerCommands customHungerCommands;

    private CustomHungerData wretchedData;
    private CustomHungerData squalidData;
    private CustomHungerData poorData;
    private CustomHungerData modestData;
    private CustomHungerData comfyData;
    private CustomHungerData wealthyData;
    private CustomHungerData aristocraticData;

    private boolean isInDebugMode;

    @Override
    public void onEnable() {

        CoreLib.setInstance(this);
        /* adds the data files to the data folder.*/
        getDataFolder().mkdirs();
        validateFiles("config.yml", "wretched.yml", "squalid.yml", "poor.yml", "modest.yml", "comfy.yml",
                "wealthy.yml", "aristocratic.yml");

        /* represents the configurable file that the client can edit */
        this.customHungerConfig = new CustomHungerConfig();
        this.isInDebugMode = customHungerConfig.isInDebugMode();

        /* represents each of the data files to store the contents of the inventory */
        this.wretchedData = new CustomHungerData("wretched.yml", "Wretched");
        this.squalidData = new CustomHungerData("squalid.yml", "Squalid");
        this.poorData = new CustomHungerData("poor.yml", "Poor");
        this.modestData = new CustomHungerData("modest.yml", "Modest");
        this.comfyData = new CustomHungerData("comfy.yml", "Comfy");
        this.wealthyData = new CustomHungerData("wealthy.yml", "Wealthy");
        this.aristocraticData = new CustomHungerData("aristocratic.yml", "Aristocratic");

        /* create an instance of the commands class */
        this.customHungerCommands = new CustomHungerCommands(this, this.customHungerConfig);

        JPUtils.registerCommand("customhunger", customHungerCommands);
        JPUtils.registerEvents(customHungerCommands);
    }

    @Override
    public void onDisable() {
        this.wretchedData.saveData(this.customHungerCommands.getWretchedPages());
        this.squalidData.saveData(this.customHungerCommands.getSqualidPages());
        this.poorData.saveData(this.customHungerCommands.getPoorPages());
        this.modestData.saveData(this.customHungerCommands.getModestPages());
        this.comfyData.saveData(this.customHungerCommands.getComfyPages());
        this.wealthyData.saveData(this.customHungerCommands.getWealthyPages());
        this.aristocraticData.saveData(this.customHungerCommands.getAristocraticPages());
    }

    public void reload(){
        this.customHungerConfig.reloadConfig();
        this.isInDebugMode = customHungerConfig.isInDebugMode();
    }

    public boolean isInDebugMode() {
        return isInDebugMode;
    }

    public void validateFiles(String... fileNames) {
        for (String name : fileNames) {
            File file = new File(this.getDataFolder(), name);
            if (!file.exists()) {
                this.saveResource(name, false);
            }
        }
    }

    public CustomHungerData getWretchedData() {
        return wretchedData;
    }

    public CustomHungerData getSqualidData() {
        return squalidData;
    }

    public CustomHungerData getPoorData() {
        return poorData;
    }

    public CustomHungerData getModestData() {
        return modestData;
    }

    public CustomHungerData getComfyData() {
        return comfyData;
    }

    public CustomHungerData getWealthyData() {
        return wealthyData;
    }

    public CustomHungerData getAristocraticData() {
        return aristocraticData;
    }
}
