package dev.aleesk.arsenal.models.menus.editor;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.categories.CategoriesMenu;
import dev.aleesk.arsenal.models.menus.editor.kits.KitsMenu;
import dev.aleesk.arsenal.models.menus.editor.settings.SettingsMenu;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class KitAdminEditorMenu extends Menu {

    private final Arsenal plugin;
    @Override
    public String getTitle(Player player) {
        return "Kit Editor Menu";
    }

    public int getSize() {
        return 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(2, new CategoriesButton(plugin));
        buttons.put(4, new KitsButton(plugin));
        buttons.put(6, new SettingsButton(plugin));
        return buttons;
    }

    public boolean isUpdateAfterClick() {
        return true;
    }


    @RequiredArgsConstructor
    private static class CategoriesButton extends Button {
        private final Arsenal plugin;
        public ItemStack getButtonItem(Player player) {
            return (new ItemBuilder(XMaterial.CHEST_MINECART.parseMaterial()))
                    .setName("&aCategories")
                    .setLore("&7Here you can explore all available",
                            "&7categories and adjust their",
                            "&7features as you prefer.",
                            "",
                            "&7Available: &6" + plugin.getCategoryManager().getCategories().size(),
                            "",
                            "&eClick to manage!")
                    .build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            playSuccess(player);
            new CategoriesMenu(plugin, true).openMenu(player);
        }
    }

    @RequiredArgsConstructor
    private static class KitsButton extends Button {
        private final Arsenal plugin;

        public ItemStack getButtonItem(Player player) {
            return (new ItemBuilder(XMaterial.CHEST.parseMaterial()))
                    .setName("&aKits")
                    .setLore("&7Here you can explore all available",
                            "&7kits and adjust their features",
                            "&7as you prefer.",
                            "",
                            "&7Available: &6" + plugin.getKitManager().getKits().size(),
                            "",
                            "&eClick to manage!").build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            playSuccess(player);
            new KitsMenu(plugin, true).openMenu(player);
        }
    }

    @RequiredArgsConstructor
    private static class SettingsButton extends Button {
        private final Arsenal plugin;
        private final FileConfig configFile = Arsenal.get().getConfigFile();

        public ItemStack getButtonItem(Player player) {
            String path = "kit_gui.";
            String categoryName = configFile.getString("kit_gui.default_category");
            String categoryDisplayName = null;

            Map<String, Category> categories = plugin.getCategoryManager().getCategories();
            if (categories.containsKey(categoryName)) {
                Category category = categories.get(categoryName);
                categoryDisplayName = category.getDisplayName();
            }

            String kitName = configFile.getString(path + "kit");
            String kitDisplayName = null;

            Map<String, Kit> kits = plugin.getKitManager().getKits();
            if (kits.containsKey(kitName)) {
                Kit kit = kits.get(kitName);
                kitDisplayName = kit.getDisplayName();
            }
            return (new ItemBuilder(XMaterial.COMPARATOR.parseMaterial()))
                    .setName("&aSettings")
                    .setLore("&8▪ &7Title: &r" + this.configFile.getString(path + "title"),
                            "&8▪ &7Rows: &6" + this.configFile.getString(path + "rows"),
                            "&8▪ &7Default Category: &r" + (categoryDisplayName == null ? "&cNone" : categoryDisplayName),
                            "&8▪ &7Clear Inventory: " + this.getEnable(this.configFile.getBoolean("kit.clear_inventory")),
                            "&8▪ &7Decorations: ",
                            "  &e▸ &7Enable: " + this.getEnable(this.configFile.getBoolean(path + "decorations.enable")),
                            "  &e▸ &7Glow: " + this.getEnable(this.configFile.getBoolean(path + "decorations.glow")),
                            "  &e▸ &7Hide Enchant: " + this.getEnable(this.configFile.getBoolean(path + "decorations.glow")),
                            "&8▪ &7Starter Kit: ",
                            "  &e▸ &7Enable: " + this.getEnable(this.configFile.getBoolean("kit.starter.enable")),
                            "  &e▸ &7Kit: &r" + (kitDisplayName == null ? "&cNone" : kitDisplayName),
                            "  &e▸ &7Equip One Time: " + this.getEnable(this.configFile.getBoolean("kit.starter.one_time")),
                            "",
                            "&eClick to manage!").build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            playSuccess(player);
            new SettingsMenu(plugin).openMenu(player);
        }

        private String getEnable(boolean state) {
            return state ? "&aEnable" : "&cDisable";
        }
    }
}
