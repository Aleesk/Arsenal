package dev.aleesk.arsenal.listeners;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

@RequiredArgsConstructor
public class KitListener implements Listener {

    private final FileConfig configFile = Arsenal.get().getConfigFile();

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        ConfigurationSection panels = configFile.getConfiguration().getConfigurationSection("kit_gui.decorations.slot-items");

        if (panels != null && player.hasMetadata("arsenal-decorations-menu")) {
            for (int i = 0; i < player.getOpenInventory().getTopInventory().getSize(); i++) {
                ItemStack item = player.getOpenInventory().getTopInventory().getItem(i);
                if (item != null && item.getType() != Material.AIR) {
                    panels.set(i + ".material", item.getType().toString());
                    panels.set(i + ".data", item.getDurability());
                    panels.set(i + ".displayname", item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : " ");
                    if (item.getType().equals(XMaterial.PLAYER_HEAD.parseMaterial())) {
                        SkullMeta meta = (SkullMeta)item.getItemMeta();
                        panels.set(i + ".skull_owner", meta.getOwner());
                    }
                    panels.set(i + ".lore", item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : " ");
                } else {
                    panels.set(String.valueOf(i), null);
                }
            }
            configFile.save();
            player.removeMetadata("arsenal-decorations-menu", Arsenal.get());
            ChatUtil.sendMessage(player, "&aDecorations have been saved.");
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(player.hasMetadata("arsenal-decorations-menu")){
            player.removeMetadata("arsenal-decorations-menu", Arsenal.get());
        }
    }
}
