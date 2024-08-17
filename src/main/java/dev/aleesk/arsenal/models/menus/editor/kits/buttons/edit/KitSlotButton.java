package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.kits.KitSlotMenu;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class KitSlotButton extends Button {
    private final Kit kit;

    private final Arsenal plugin;

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(XMaterial.PAINTING.parseMaterial()))
                .setName("&aSlot")
                        .setLore("&7You can change the GUI kit slot",
                                "&7to one that is available.",
                                "",
                                "&7Slot: &6"+ this.kit.getSlot(),
                                "",
                                "&eClick to edit!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (kit.getCategory() == null) {
            ChatUtil.sendMessage(player, "&cThere is no category assigned to the kit.");
            return;
        }
        new KitSlotMenu(this.kit, this.plugin).openMenu(player);
        playNeutral(player);
    }
}