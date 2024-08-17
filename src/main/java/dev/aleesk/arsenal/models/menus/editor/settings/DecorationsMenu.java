package dev.aleesk.arsenal.models.menus.editor.settings;

import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class DecorationsMenu extends Menu {

    private final Arsenal plugin;
    private final FileConfig configFile = Arsenal.get().getConfigFile();

    @Override
    public String getTitle(Player player) {
        return "Kit GUI Decorations";
    }

    public int getSize() {
        return this.configFile.getInt("kit_gui.rows") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        ConfigurationSection panels = plugin.getConfigFile().getConfiguration().getConfigurationSection("kit_gui.decorations.slot-items");

        if (panels != null) {
            for (String key : panels.getKeys(false)) {
                int slot = Integer.parseInt(key);
                if (slot < getSize()) {
                    buttons.put(slot, new FillItemButton(slot));
                }
            }
        }
        return buttons;
    }

    public boolean isCancelPlayerInventory() {
        return false;
    }

    private static class FillItemButton extends Button {
        private final int slot;
        private final FileConfig configFile;

        public FillItemButton(int slot) {
            this.slot = slot;
            this.configFile = Arsenal.get().getConfigFile();
        }

        public ItemStack getButtonItem(Player player) {
            String path = "kit_gui.decorations.";
            String material = configFile.getString(path + "slot-items." + slot + ".material");
            int data = configFile.getInt(path + "slot-items." + slot + ".data");
            String displayname = configFile.getString(path + "slot-items." + slot + ".displayname");
            List<String> lore = configFile.getStringList(path + "slot-items." + slot + ".lore");

            return new ItemBuilder(material)
                    .setData(data)
                    .setName(displayname)
                    .setLore(lore)
                    .build();
        }

        public boolean shouldCancel(Player player, int slot, ClickType clickType) {
            return false;
        }
    }
}
