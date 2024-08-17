package dev.aleesk.arsenal.models.menus.editor.kits.buttons;

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
public class KitDeleteAllButton extends Button {
    private final Arsenal plugin;

    @Override
    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(com.cryptomorin.xseries.XMaterial.BLAZE_POWDER.parseMaterial()))
                .setName("&aDelete Kits")
                .setLore(Arrays.asList(
                        "&4Warning: &cIf you confirm to delete all",
                        "&cthe kits, you will not be able to",
                        "&crecover from the kits again!",
                        "",
                        "&eClick to delete kits!"))
                .build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);
        ConfirmMenu confirmMenu = new ConfirmMenu(ChatUtil.translate("&cDelete All Kits"), confirmed -> {
            if (confirmed) {
                plugin.getKitManager().deleteAll();
                playSuccess(player);
                ChatUtil.sendMessage(player, ChatUtil.translate("&aAll kits have been deleted."));
            } else {
                ChatUtil.sendMessage(player, ChatUtil.translate("&cDeletion of kits canceled."));
                playFail(player);
            }
        });
        confirmMenu.openMenu(player);
    }
}
