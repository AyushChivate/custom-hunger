package io.github.ayushchivate.customhunger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CustomHungerCommands implements CommandExecutor, Listener {

    private final List<String> VALID_INVENTORY_NAMES = Arrays.asList(
            "Wretched",
            "Squalid",
            "Poor",
            "Modest",
            "Comfy",
            "Wealthy",
            "Aristocratic"
    );

    /* menu button locations */
    public static final int INVENTORY_SIZE = 54;
    private static final int BACK_ARROW_INDEX = 45;
    private static final int NEW_PAGE_INDEX = 49;
    private static final int FORWARD_ARROW_INDEX = 53;
    private static final List<Material> AIR_MATERIALS = Arrays.asList(Material.AIR, Material.CAVE_AIR,
            Material.VOID_AIR);

    private Player player;
    private CustomHungerConfig customHungerConfig;
    private CustomHungerPlugin customHungerPlugin;

    /* create the first page of all inventories aka default inventories */
    private Inventory wretchedInventory = Bukkit.createInventory(null, INVENTORY_SIZE, "Wretched - Page 1");
    private Inventory squalidInventory = Bukkit.createInventory(null, INVENTORY_SIZE, "Squalid - Page 1");
    private Inventory poorInventory = Bukkit.createInventory(null, INVENTORY_SIZE, "Poor - Page 1");
    private Inventory modestInventory = Bukkit.createInventory(null, INVENTORY_SIZE, "Modest - Page 1");
    private Inventory comfyInventory = Bukkit.createInventory(null, INVENTORY_SIZE, "Comfy - Page 1");
    private Inventory wealthyInventory = Bukkit.createInventory(null, INVENTORY_SIZE, "Wealthy - Page 1");
    private Inventory aristocraticInventory = Bukkit.createInventory(null, INVENTORY_SIZE, "Aristocratic " +
            "- Page 1");

    /* create empty page lists for each inventory */
    private List<Inventory> wretchedPages;
    private List<Inventory> squalidPages;
    private List<Inventory> poorPages;
    private List<Inventory> modestPages;
    private List<Inventory> comfyPages;
    private List<Inventory> wealthyPages;
    private List<Inventory> aristocraticPages;

    public CustomHungerCommands(CustomHungerPlugin customHungerPlugin,
                                CustomHungerConfig customHungerConfig) {

        /* set the instances of the plugin and config */
        this.customHungerConfig = customHungerConfig;
        this.customHungerPlugin = customHungerPlugin;

        /* add menu buttons to each default inventory */
        setInventoryMenu(this.wretchedInventory, true, false, true);
        setInventoryMenu(this.squalidInventory, true, false, true);
        setInventoryMenu(this.poorInventory, true, false, true);
        setInventoryMenu(this.modestInventory, true, false, true);
        setInventoryMenu(this.comfyInventory, true, false, true);
        setInventoryMenu(this.wealthyInventory, true, false, true);
        setInventoryMenu(this.aristocraticInventory, true, false, true);

        /* sets the saved data to the pages if it's not empty, otherwise, it adds the default inventory */

        this.wretchedPages = this.customHungerPlugin.getWretchedData().loadData();
        if (this.wretchedPages.isEmpty()) {
            this.wretchedPages.add(this.wretchedInventory);
        }
        for (int i = 0; i < this.wretchedPages.size(); i++) {
            setInventoryMenu(this.wretchedPages.get(i), i == 0, this.wretchedPages.size() > 1, i == this.wretchedPages.size()-1);
        }

        this.squalidPages = this.customHungerPlugin.getSqualidData().loadData();
        if (this.squalidPages.isEmpty()) {
            this.squalidPages.add(this.squalidInventory);
        }
        for (int i = 0; i < this.squalidPages.size(); i++) {
            setInventoryMenu(this.squalidPages.get(i), i == 0, this.squalidPages.size() > 1, i == this.squalidPages.size()-1);
        }

        this.poorPages = this.customHungerPlugin.getPoorData().loadData();
        if (this.poorPages.isEmpty()) {
            this.poorPages.add(this.poorInventory);
        }
        for (int i = 0; i < this.poorPages.size(); i++) {
            setInventoryMenu(this.poorPages.get(i), i == 0, this.poorPages.size() > 1, i == this.poorPages.size()-1);
        }

        this.modestPages = this.customHungerPlugin.getModestData().loadData();
        if (this.modestPages.isEmpty()) {
            this.modestPages.add(this.modestInventory);
        }
        for (int i = 0; i < this.modestPages.size(); i++) {
            setInventoryMenu(this.modestPages.get(i), i == 0, this.modestPages.size() > 1, i == this.modestPages.size()-1);
        }

        this.comfyPages = this.customHungerPlugin.getComfyData().loadData();
        if (this.comfyPages.isEmpty()) {
            this.comfyPages.add(this.comfyInventory);
        }
        for (int i = 0; i < this.comfyPages.size(); i++) {
            setInventoryMenu(this.comfyPages.get(i), i == 0, this.comfyPages.size() > 1, i == this.comfyPages.size()-1);
        }

        this.wealthyPages = this.customHungerPlugin.getWealthyData().loadData();
        if (this.wealthyPages.isEmpty()) {
            this.wealthyPages.add(this.wealthyInventory);
        }
        for (int i = 0; i < this.wealthyPages.size(); i++) {
            setInventoryMenu(this.wealthyPages.get(i), i == 0, this.wealthyPages.size() > 1, i == this.wealthyPages.size()-1);
        }

        this.aristocraticPages = this.customHungerPlugin.getAristocraticData().loadData();
        if (this.aristocraticPages.isEmpty()) {
            this.aristocraticPages.add(this.aristocraticInventory);
        }
        for (int i = 0; i < this.aristocraticPages.size(); i++) {
            setInventoryMenu(this.aristocraticPages.get(i), i == 0, this.aristocraticPages.size() > 1, i == this.aristocraticPages.size()-1);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        /* permission and error checking of commands */

        if (!(sender instanceof Player)) {
            return false;
        }

        this.player = (Player) sender;

        if (!player.hasPermission("customhunger.commands")) {
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "[CustomHunger] Invalid usage. Use </customhunger help> for " +
                    "a list of commands and usages.");
            return false;
        }

        String playerCommand = args[0];

        /* find and execute command */
        switch (playerCommand) {
            case "wretched":
            case "wr":
                wretched();
                break;
            case "squalid":
            case "s":
                squalid();
                break;
            case "poor":
            case "p":
                poor();
                break;
            case "modest":
            case "m":
                modest();
                break;
            case "comfy":
            case "c":
                comfy();
                break;
            case "wealthy":
            case "wl":
                wealthy();
                break;
            case "aristocratic":
            case "a":
                aristocratic();
                break;
            case "help":
                help();
                break;
            case "reload":
                reload();
                break;
            case "lowerhunger":
                lowerHunger();
                break;
            default:
                player.sendMessage(ChatColor.RED + "[CustomHunger] Invalid command. Use </customhunger " +
                        "help> for a list of commands and usages.");
        }

        return true;
    }

    private void reload() {
        customHungerPlugin.reload();
        this.player.sendMessage(ChatColor.GREEN + "[CustomHunger] Plugin has been reloaded successfully!");
    }

    /* open the inventory that the player specified in the command */

    private void wretched() {
        this.player.openInventory(this.wretchedPages.get(0));
    }

    private void squalid() {
        this.player.openInventory(this.squalidPages.get(0));
    }

    private void poor() {
        this.player.openInventory(this.poorPages.get(0));
    }

    private void modest() {
        this.player.openInventory(this.modestPages.get(0));
    }

    private void comfy() {
        this.player.openInventory(this.comfyPages.get(0));
    }

    private void wealthy() {
        this.player.openInventory(this.wealthyPages.get(0));
    }

    private void aristocratic() {
        this.player.openInventory(this.aristocraticPages.get(0));
    }

    private void help() {

        player.sendMessage(ChatColor.YELLOW + "=============================================");
        player.sendMessage("");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Custom Hunger Commands:");
        player.sendMessage("");

        player.sendMessage("Note: To access the different inventories in this plugin, you must use " +
                ChatColor.YELLOW + "</customhunger [inventory name]>" +
                ChatColor.WHITE + ". You can also use" + ChatColor.YELLOW + "</ch [inventory name]>" +
                ChatColor.WHITE + " which does the same thing.");
        player.sendMessage("");

        player.sendMessage("The following commands all open up the inventory that is specified in the command. Any " +
                "food placed inside that inventory will have a random hunger value between the minimum and " +
                "maximum values, which you can specify in the config.yml file.");
        player.sendMessage("");

        player.sendMessage(ChatColor.LIGHT_PURPLE + "  ○ " + ChatColor.YELLOW + "/ch [wretched | wr]");
        player.sendMessage("");

        player.sendMessage(ChatColor.LIGHT_PURPLE + "  ○ " + ChatColor.YELLOW + "/ch [squalid | s]");
        player.sendMessage("");

        player.sendMessage(ChatColor.LIGHT_PURPLE + "  ○ " + ChatColor.YELLOW + "/ch [poor | p]");
        player.sendMessage("");

        player.sendMessage(ChatColor.LIGHT_PURPLE + "  ○ " + ChatColor.YELLOW + "/ch [modest | m]");
        player.sendMessage("");

        player.sendMessage(ChatColor.LIGHT_PURPLE + "  ○ " + ChatColor.YELLOW + "/ch [comfy | c]");
        player.sendMessage("");

        player.sendMessage(ChatColor.LIGHT_PURPLE + "  ○ " + ChatColor.YELLOW + "/ch [wealthy | wl]");
        player.sendMessage("");

        player.sendMessage(ChatColor.LIGHT_PURPLE + "  ○ " + ChatColor.YELLOW + "/ch [aristocratic | a]");
        player.sendMessage("");

        player.sendMessage(ChatColor.LIGHT_PURPLE + "  ○ " + ChatColor.YELLOW + "/ch reload");
        player.sendMessage(ChatColor.WHITE + "        Reloads the config.yml file. Must be executed after");
        player.sendMessage(ChatColor.WHITE + "        making changes to the config.yml file.");


        player.sendMessage("");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "  ○ " + ChatColor.YELLOW + "/ch help");
        player.sendMessage(ChatColor.WHITE + "        Lists all the commands and their usages in this plugin.");

        player.sendMessage("");
        player.sendMessage(ChatColor.YELLOW + "=============================================");
    }

    public List<Inventory> getWretchedPages() {

        return this.wretchedPages;
    }

    public List<Inventory> getSqualidPages() {
        return this.squalidPages;
    }

    public List<Inventory> getPoorPages() {
        return this.poorPages;
    }

    public List<Inventory> getModestPages() {
        return this.modestPages;
    }

    public List<Inventory> getComfyPages() {
        return this.comfyPages;
    }

    public List<Inventory> getWealthyPages() {
        return this.wealthyPages;
    }

    public List<Inventory> getAristocraticPages() {
        return this.aristocraticPages;
    }

    /* inventory naming convention: "Wretched - Page 1" */

    @EventHandler
    public void onPlayerInventoryClick(InventoryClickEvent event) {

        /* get the inventory name, page number, and clicked item */
        Player player = (Player) event.getWhoClicked();
        String clickedInventoryName = event.getView().getTitle().split(" - ")[0];

        int clickedInventoryPageNumber;
        try {
            clickedInventoryPageNumber = Integer.parseInt(event.getView().getTitle().split(" - ")[1]
                    .split(" ")[1]);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            clickedInventoryPageNumber = -1;
        }

        ItemStack clickedItem = event.getCurrentItem();

        /* make sure the player has clicked in a custom hunger inventory */
        if(!VALID_INVENTORY_NAMES.contains(clickedInventoryName)){
            return;
        }

        if (clickedItem == null) {
            return;
        }

        /* prevent a player from dragging the menu buttons */
        if (event.getSlot() == FORWARD_ARROW_INDEX || event.getSlot() == NEW_PAGE_INDEX ||
                event.getSlot() == BACK_ARROW_INDEX || event.getSlot() == 46 || event.getSlot() == 47 ||
                event.getSlot() == 48 || event.getSlot() == 50 || event.getSlot() == 51 || event.getSlot() == 52) {
            event.setCancelled(true);
        }

        /* get the pages list of the clicked inventory */

        List<Inventory> pages;

        switch (clickedInventoryName) {
            case "Wretched":
                pages = this.wretchedPages;
                break;
            case "Squalid":
                pages = this.squalidPages;
                break;
            case "Poor":
                pages = this.poorPages;
                break;
            case "Modest":
                pages = this.modestPages;
                break;
            case "Comfy":
                pages = this.comfyPages;
                break;
            case "Wealthy":
                pages = this.wealthyPages;
                break;
            case "Aristocratic":
                pages = this.aristocraticPages;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + clickedInventoryName);
        }

        /* open the next or previous page if the arrow is clicked, otherwise, create a new page */
        if (event.getSlot() == FORWARD_ARROW_INDEX && clickedItem.getType() == Material.ARROW) {
            player.openInventory(pages.get(clickedInventoryPageNumber));
        } else if (event.getSlot() == BACK_ARROW_INDEX && clickedItem.getType() == Material.ARROW) {
            player.openInventory(pages.get(clickedInventoryPageNumber - 2));
        } else if (event.getSlot() == NEW_PAGE_INDEX) {
            addPage(clickedInventoryName);
        }
    }

    /*
     * 0 1 2 3 4 5 6 7 8
     * -----------------
     * 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0
     * 1 0 0 0 1 0 0 0 1
     *
     * 45, 49, 53
     */

    public void addPage(String type) {

        String title;
        Inventory newInventory;
        int previousPage;
        List<Inventory> pages;

        /* find which inventory to add a page to */
        switch (type) {
            case "Wretched":
                title = "Wretched - Page " + (this.wretchedPages.size() + 1);
                pages = this.wretchedPages;
                break;
            case "Squalid":
                title = "Squalid - Page " + (this.squalidPages.size() + 1);
                pages = this.squalidPages;
                break;
            case "Poor":
                title = "Poor - Page " + (this.poorPages.size() + 1);
                pages = this.poorPages;
                break;
            case "Modest":
                title = "Modest - Page " + (this.modestPages.size() + 1);
                pages = this.modestPages;
                break;
            case "Comfy":
                title = "Comfy - Page " + (this.comfyPages.size() + 1);
                pages = this.comfyPages;
                break;
            case "Wealthy":
                title = "Wealthy - Page " + (this.wealthyPages.size() + 1);
                pages = this.wealthyPages;
                break;
            case "Aristocratic":
                title = "Aristocratic - Page " + (this.aristocraticPages.size() + 1);
                pages = this.aristocraticPages;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        /* create a new page */
        newInventory = Bukkit.createInventory(null, INVENTORY_SIZE, title);
        setInventoryMenu(newInventory, false, false, true);
        /* set the previous page's forward button to an arrow */
        pages.get(pages.size() - 1).setItem(FORWARD_ARROW_INDEX, new ItemStack(Material.ARROW));
        /* add the new inventory to the pages list */
        pages.add(newInventory);
    }

    private void setInventoryMenu(Inventory inventory, boolean isFirst, boolean isSecond, boolean isLastPage) {

        /* set the back arrow slot to barrier if this is the first page inventory, otherwise make it an arrow */
        if (isFirst) {
            inventory.setItem(BACK_ARROW_INDEX, new ItemStack(Material.BARRIER));
        } else {
            inventory.setItem(BACK_ARROW_INDEX, new ItemStack(Material.ARROW));
        }
        /* set the new page's new page and forward button slot */
        inventory.setItem(NEW_PAGE_INDEX, new ItemStack(Material.PAPER));

        if (isSecond && !isLastPage) {
            inventory.setItem(FORWARD_ARROW_INDEX, new ItemStack(Material.ARROW));
        } else {
            inventory.setItem(FORWARD_ARROW_INDEX, new ItemStack(Material.BARRIER));
        }

        /* set the red panes */
        for (int i = 46; i < 53; i++) {
            if (i != 49) {
                inventory.setItem(i, new ItemStack(Material.RED_STAINED_GLASS_PANE));
            }
        }
    }

    @EventHandler
    public void onPlayerEat(PlayerItemConsumeEvent e) {

        ItemStack food = e.getItem();
        Player player = e.getPlayer();

        Map<String, List<Inventory>> allPages = new HashMap<String, List<Inventory>>() {{
            put("wretched", wretchedPages);
            put("squalid", squalidPages);
            put("poor", poorPages);
            put("modest", modestPages);
            put("comfy", comfyPages);
            put("wealthy", wealthyPages);
            put("aristocratic", aristocraticPages);
        }};

        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (Map.Entry<String, List<Inventory>> pages : allPages.entrySet()) {
            for (Inventory inventory : pages.getValue()) {
                for (ItemStack inventoryFood : inventory.getContents()) {
                    if (isSimilar(inventoryFood, food)) {
                        switch (pages.getKey()) {
                            case "wretched":
                                e.setCancelled(true);
                                /*player.setHunger(player.getHunger() + r.nextInt(customHungerConfig.getWretchedHunger()[0],
                                        customHungerConfig.getWretchedHunger()[1] + 1));*/
                                player.setFoodLevel(player.getFoodLevel() + r.nextInt(customHungerConfig.getWretchedHunger()[0],
                                        customHungerConfig.getWretchedHunger()[1] + 1));
                                return;
                            case "squalid":
                                e.setCancelled(true);
                                /*player.setHunger(player.getHunger() + r.nextInt(customHungerConfig.getSqualidHunger()[0],
                                        customHungerConfig.getSqualidHunger()[1] + 1));*/
                                player.setFoodLevel(player.getFoodLevel() + r.nextInt(customHungerConfig.getSqualidHunger()[0],
                                        customHungerConfig.getSqualidHunger()[1] + 1));
                                return;
                            case "poor":
                                e.setCancelled(true);
                                /* player.setHunger(player.getHunger() + r.nextInt(customHungerConfig.getPoorHunger()[0],
                                        customHungerConfig.getPoorHunger()[1] + 1)); */
                                player.setFoodLevel(player.getFoodLevel() + r.nextInt(customHungerConfig.getPoorHunger()[0],
                                        customHungerConfig.getPoorHunger()[1] + 1));
                                return;
                            case "modest":
                                e.setCancelled(true);
                                /* player.setHunger(player.getHunger() + r.nextInt(customHungerConfig.getModestHunger()[0],
                                        customHungerConfig.getModestHunger()[1] + 1)); */
                                player.setFoodLevel(player.getFoodLevel() + r.nextInt(customHungerConfig.getModestHunger()[0],
                                        customHungerConfig.getModestHunger()[1] + 1));
                                return;
                            case "comfy":
                                e.setCancelled(true);
                                /* player.setHunger(player.getHunger() + r.nextInt(customHungerConfig.getComfyHunger()[0],
                                        customHungerConfig.getComfyHunger()[1] + 1)); */
                                player.setFoodLevel(player.getFoodLevel() + r.nextInt(customHungerConfig.getComfyHunger()[0],
                                        customHungerConfig.getComfyHunger()[1] + 1));
                                return;
                            case "wealthy":
                                e.setCancelled(true);
                                /* player.setHunger(player.getHunger() + r.nextInt(customHungerConfig.getWealthyHunger()[0],
                                        customHungerConfig.getWealthyHunger()[1] + 1)); */
                                player.setFoodLevel(player.getFoodLevel() + r.nextInt(customHungerConfig.getWealthyHunger()[0],
                                        customHungerConfig.getWealthyHunger()[1] + 1));
                                return;
                            case "aristocratic":
                                e.setCancelled(true);
                                /* player.setHunger(player.getHunger() + r.nextInt(customHungerConfig.getAristocraticHunger()[0],
                                        customHungerConfig.getAristocraticHunger()[1] + 1)); */
                                player.setFoodLevel(player.getFoodLevel() + r.nextInt(customHungerConfig.getAristocraticHunger()[0],
                                        customHungerConfig.getAristocraticHunger()[1] + 1));
                                return;
                        }
                    }
                }
            }
        }

        int defaultHunger = customHungerConfig.getDefaultHunger();
        if (defaultHunger != -1) {
            /*player.setHunger(player.getHunger() + defaultHunger);*/
            e.setCancelled(true);
            player.setFoodLevel(player.getFoodLevel() + defaultHunger);
        }

    }

    /**
     * Compares 2 item stacks via display name, lore, enchantments
     */
    public static boolean isSimilar(ItemStack stack1, ItemStack stack2) {

        if (stack1 == null || stack2 == null) return false;

        Material stack1Type = stack1.getType();
        Material stack2Type = stack2.getType();
        if (stack1Type != stack2Type) return false;
        // Just so that I won't have to deal with any null item metas (Only air returns a null item meta to my knowledge)
        if (AIR_MATERIALS.contains(stack1Type)) return true;

//        if(stack1.getAmount() != stack2.getAmount()) return false;

        ItemMeta stack1Meta = stack1.getItemMeta();
        ItemMeta stack2Meta = stack2.getItemMeta();

        String stack1DisplayName = stack1Meta.hasDisplayName() ? stack1Meta.getDisplayName() : "";
        String stack2DisplayName = stack2Meta.hasDisplayName() ? stack2Meta.getDisplayName() : "";
        if (!stack1DisplayName.equals(stack2DisplayName)) return false;

        List<String> stack1Lore = stack1Meta.hasLore() ? stack1Meta.getLore() : new ArrayList<>();
        List<String> stack2Lore = stack2Meta.hasLore() ? stack2Meta.getLore() : new ArrayList<>();
        if (!stack1Lore.equals(stack2Lore)) return false;

        Map<Enchantment, Integer> stack1Enchants = stack1Meta.hasEnchants() ? stack1Meta.getEnchants() : new HashMap<>();
        Map<Enchantment, Integer> stack2Enchants = stack2Meta.hasEnchants() ? stack2Meta.getEnchants() : new HashMap<>();
        if (stack1Enchants.size() != stack2Enchants.size()) return false;

        return hasSameEnchants(stack1Meta, stack2Meta);
    }

    private static boolean hasSameEnchants(ItemMeta meta1, ItemMeta meta2) {
        for (Map.Entry<Enchantment, Integer> entry : meta1.getEnchants().entrySet()) {
            if (!meta2.hasEnchant(entry.getKey())) {
                return false;
            }
        }
        return true;
    }

    private void lowerHunger() {
        /*player.setHunger(1);*/
        player.setFoodLevel(1);
    }

}
