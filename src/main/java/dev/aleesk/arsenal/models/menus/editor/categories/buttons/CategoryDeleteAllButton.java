package dev.aleesk.arsenal.models.menus.editor.categories.buttons;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.menus.ConfirmMenu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@RequiredArgsConstructor
public class CategoryDeleteAllButton extends Button {
    private final Arsenal plugin;

    @Override
    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(com.cryptomorin.xseries.XMaterial.BLAZE_POWDER.parseMaterial()))
                .setName("&aDelete Categories")
                .setLore(Arrays.asList(
                        "&4Warning: &cIf you confirm to delete all",
                        "&cthe categories, you will not be able to",
                        "&crecover from the kits again!",
                        "",
                        "&eClick to delete categories!"))
                .build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);
        ConfirmMenu confirmMenu = new ConfirmMenu(ChatUtil.translate("&cDelete All Categories"), confirmed -> {
            if (confirmed) {
                plugin.getCategoryManager().deleteAll();
                playSuccess(player);
                ChatUtil.sendMessage(player, ChatUtil.translate("&aAll categories have been deleted."));
            } else {
                ChatUtil.sendMessage(player, ChatUtil.translate("&cDeletion of categories canceled."));
                playFail(player);
            }
        });
        confirmMenu.openMenu(player);
    }
}
