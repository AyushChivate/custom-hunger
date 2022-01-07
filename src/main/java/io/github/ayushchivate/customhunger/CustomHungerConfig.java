package io.github.ayushchivate.customhunger;

import net.dohaw.corelib.Config;

public class CustomHungerConfig extends Config {


    public CustomHungerConfig() {
        super("config.yml");
    }

    public int getDefaultHunger() {
        return config.getInt("Default Hunger", -1);
    }

    public int[] getWretchedHunger() {
        return new int[]{config.getInt("Wretched Hunger.Minimum"),
                config.getInt("Wretched Hunger.Maximum")};
    }

    public int[] getSqualidHunger() {
        return new int[]{config.getInt("Squalid Hunger.Minimum"),
                config.getInt("Squalid Hunger.Maximum")};
    }

    public int[] getPoorHunger() {
        return new int[]{config.getInt("Poor Hunger.Minimum"),
                config.getInt("Poor Hunger.Maximum")};
    }

    public int[] getModestHunger() {
        return new int[]{config.getInt("Modest Hunger.Minimum"),
                config.getInt("Modest Hunger.Maximum")};
    }

    public int[] getComfyHunger() {
        return new int[]{config.getInt("Comfy Hunger.Minimum"),
                config.getInt("Comfy Hunger.Maximum")};
    }

    public int[] getWealthyHunger() {
        return new int[]{config.getInt("Wealthy Hunger.Minimum"),
                config.getInt("Wealthy Hunger.Maximum")};
    }

    public int[] getAristocraticHunger() {
        return new int[]{config.getInt("Aristocratic Hunger.Minimum"),
                config.getInt("Aristocratic Hunger.Maximum")};
    }

    public boolean isInDebugMode(){
        return config.getBoolean("Debug Mode");
    }

}
