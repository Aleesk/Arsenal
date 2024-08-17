package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit.display;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.utilities.BukkitUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

@RequiredArgsConstructor
public class KitIconButton extends Button {
    private final Kit kit;

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(XMaterial.ITEM_FRAME.parseMaterial()))
                .setName("&aIcon")
                        .setLore("&7If you want to change the kit icon,",
                                "&7just drag and drop the new item here.",
                                "",
                                "&7Material: &b" + this.kit.getMaterial().toString(),
                                "&7Data: &6" + this.kit.getData(),
                                "",
                                "&eDrag and drop to set!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        player.getItemOnCursor().getItemMeta();

        ItemStack kitItem = (new ItemBuilder(this.kit.getMaterial())).setData(this.kit.getData()).build();
        if (player.getItemOnCursor().getType() == null || player
                .getItemOnCursor().getType().equals(Material.AIR) || player
                .getItemOnCursor().isSimilar(kitItem))
            return;
        this.kit.setMaterial(player.getItemOnCursor().getType());
        if (BukkitUtil.SERVER_VERSION_INT <= 12) {
            this.kit.setData(player.getItemOnCursor().getDurability());
        } else {
            this.kit.setData(player.getItemOnCursor().getData().getData());
        }

        if (player.getItemOnCursor().getType().equals(XMaterial.PLAYER_HEAD.parseMaterial())) {
            SkullMeta meta = (SkullMeta)player.getItemOnCursor().getItemMeta();
            this.kit.setSkullOwner(meta.getOwner());
        }
        this.kit.save();
        playSuccess(player);
    }
}
