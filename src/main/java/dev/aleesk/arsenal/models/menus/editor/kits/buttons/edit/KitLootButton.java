package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.kits.KitLootMenu;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.OffHandUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@RequiredArgsConstructor
public class KitLootButton extends Button {
    private final Kit kit;
    private final Arsenal plugin;

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(XMaterial.CHEST.parseMaterial()))
                .setName("&aLoot")
                        .setLore("&7You can quickly save loot by simply",
                                "&7clicking on it and your entire inventory",
                                "&7will be saved. You can also edit it with",
                                "&7a menu,just by right clicking on it.",
                                "",
                                "&bRight-Click to save your inventory!",
                                "&eLeft-Click to edit!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (clickType.isLeftClick()) {
            new KitLootMenu(kit, plugin).openMenu(player);
            playSuccess(player);
        }
        if (clickType.isRightClick()) {
            ItemStack[] armor = new ItemStack[4];
            ItemStack[] contents = new ItemStack[36];
            int i;
            for (i = 0; i < 4; i++) {
                if (player.getInventory().getArmorContents()[i] != null)
                    armor[i] = player.getInventory().getArmorContents()[i].clone();
            }
            for (i = 0; i < 36; i++) {
                if (player.getInventory().getContents()[i] != null)
                    contents[i] = player.getInventory().getContents()[i].clone();
            }
            this.kit.setArmor(armor);
            this.kit.setContents(contents);
            if(this.kit.getOffHand() != null) this.kit.setOffHand(Objects.requireNonNull(OffHandUtil.getOffHandItem(player)));
            this.kit.save();
            playSave(player);
            ChatUtil.sendMessage(player, "&a'&f" + this.kit.getName() + "&a' kit Loot has been updated.");
        }
    }
}