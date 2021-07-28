package io.github.ayushchivate.customhunger;

import net.dohaw.corelib.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CustomHungerData extends Config {

    String type;

    public CustomHungerData(String fileName, String type) {
        super(fileName);
        this.type = type;
    }

    public void saveData(List<Inventory> pages) {

        for (int i = 0; i < pages.size(); i++) {
            ItemStack[] itemStacks = pages.get(i).getContents();
            List<ItemStack> itemStacks1 = new ArrayList<>();
            for (ItemStack item : itemStacks) {
                if (item != null && item.getType() != Material.BARRIER &&
                        item.getType() != Material.RED_STAINED_GLASS_PANE && item.getType() != Material.ARROW
                        && item.getType() != Material.PAPER) {
                    itemStacks1.add(item);
                }
            }
            config.set(i + "", itemStacks1);
        }

        saveConfig();
    }

    public List<Inventory> loadData() {

        List<Inventory> pages = new ArrayList<>();
        for (String i : config.getKeys(false)) {
            List<ItemStack> items = (List<ItemStack>) config.getList(i);
            Inventory inventory = Bukkit.createInventory(null, CustomHungerCommands.INVENTORY_SIZE,
                    type + " - Page " + (Integer.parseInt(i) + 1));
            for (ItemStack itemStack : items) {
                inventory.addItem(itemStack);
            }
            pages.add(inventory);
        }
        return pages;
    }
}
