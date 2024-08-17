package dev.aleesk.arsenal.models.menus.editor.settings.buttons;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@RequiredArgsConstructor
public class DecorationItemButton extends Button {

    private final int slot;
    private final FileConfig configFile;

    public DecorationItemButton(int slot) {
        this.slot = slot;
        this.configFile = Arsenal.get().getConfigFile();
    }

    public ItemStack getButtonItem(Player player) {
        String path = "kit_gui.decorations.slot-items." + slot;
        String material = configFile.getString(path + ".material");
        int data = configFile.getInt(path + ".data");
        String displayName = configFile.getString(path + ".displayname");
        String skullOwner = configFile.getString(path + ".skull_owner");
        List<String> lore = configFile.getStringList(path + ".lore");

        return new ItemBuilder(material)
                .setData(data)
                .setName(displayName)
                .setSkullOwner(skullOwner)
                .setLore(lore)
                .setEnchanted(configFile.getBoolean(path + "glow"), configFile.getBoolean(path + "enchant"))
                .build();
    }
}
