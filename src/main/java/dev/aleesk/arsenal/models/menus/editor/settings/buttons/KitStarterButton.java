package dev.aleesk.arsenal.models.menus.editor.settings.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.kits.KitsMenu;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class KitStarterButton extends Button {
    private final FileConfig configFile = Arsenal.get().getConfigFile();

    private final String path = "kit.starter.";

    private final Arsenal plugin;

    @Override
    public ItemStack getButtonItem(Player player) {
        String kitName = configFile.getString(path + "kit");
        String starterKit = "none";

        Map<String, Kit> kits = plugin.getKitManager().getKits();
        if (kits.containsKey(kitName)) {
            Kit kit = kits.get(kitName);
            starterKit = kit.getName();
        }

        return new ItemBuilder(configFile.getBoolean(path + "enable") ? XMaterial.DIAMOND_SWORD.parseMaterial() : XMaterial.WOODEN_SWORD.parseMaterial())
                .setName("&aStarter Kit")
                .setLore("&7It has the functionality that if a player",
                        "&7enters the game he will automatically",
                        "&7receive a kit.",
                        "",
                        "&7Enable: " + (configFile.getBoolean(path + "enable") ? "&aTrue" : "&cFalse"),
                        "&7Kit: &b" + (starterKit.equalsIgnoreCase("none") ? "&cNone" : starterKit),
                        "&7Equip One Time: " + (configFile.getBoolean(path + "one_time") ? "&aTrue" : "&cFalse"),
                        "",
                        "&bRight-Click to toggle equip one time.",
                        "&dMiddle-Click to change kit.",
                        "&eLeft-Click to toggle starter kit.").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        if (clickType.isLeftClick()) {
            configFile.getConfiguration().set(path + "enable", !configFile.getBoolean(path + "enable"));
            configFile.save();
        }
        if (clickType.isCreativeAction()) {
            new KitsMenu(plugin, false).openMenu(player);
        }
        if (clickType.isRightClick()){
            configFile.getConfiguration().set(path + "one_time", !configFile.getBoolean(path + "one_time"));
            configFile.save();
        }
    }
}
