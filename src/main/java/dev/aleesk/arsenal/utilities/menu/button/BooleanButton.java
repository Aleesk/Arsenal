package dev.aleesk.arsenal.utilities.menu.button;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.utilities.BukkitUtil;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.callback.Callback;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

public class BooleanButton extends Button {

    private final boolean confirm;
    private final Callback<Boolean> callback;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        if (this.confirm){
            lore.add(ChatUtil.translate("&aClick here to confirm"));
            lore.add(ChatUtil.translate("&athe procedure action."));
        } else {
            lore.add(ChatUtil.translate("&cClick here to cancel"));
            lore.add(ChatUtil.translate("&cthe procedure action."));
        }
        if (BukkitUtil.SERVER_VERSION_INT <= 12) return new ItemBuilder(com.cryptomorin.xseries.XMaterial.WHITE_WOOL.parseMaterial()).setName(this.confirm ? ChatUtil.translate("&a&lConfirm") : ChatUtil.translate("&c&lCancel")).setLore(lore).setData(this.confirm ? (byte) 5 : 14).build();
        else return new ItemBuilder(this.confirm ? XMaterial.LIME_WOOL.parseMaterial() : XMaterial.RED_WOOL.parseMaterial()).setName(this.confirm ? ChatUtil.translate("&a&lConfirm") : ChatUtil.translate("&c&lCancel")).setLore(lore).build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (this.confirm) {
            playSuccess(player);
        } else {
            playFail(player);
        }
        player.closeInventory();
        this.callback.callback(this.confirm);
    }

    @ConstructorProperties(value={"confirm", "callback"})
    public BooleanButton(boolean confirm, Callback<Boolean> callback) {
        this.confirm = confirm;
        this.callback = callback;
    }
}

