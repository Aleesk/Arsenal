package dev.aleesk.arsenal.utilities.menu.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.utilities.BukkitUtil;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.pagination.PaginatedMenu;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PageButton extends Button {

    private final int mod;
    private final PaginatedMenu menu;

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemBuilder itemBuilder = new ItemBuilder(XMaterial.WHITE_CARPET.parseMaterial());

        String buttonText;
        int data = 1;
        int nextPage = menu.getPage() + mod;

        if (mod > 0) {
            buttonText = hasNext(player) ? "&aNext Page" : "&7Next Page";
            if(BukkitUtil.SERVER_VERSION_INT <= 12) data = hasNext(player) ? 13 : 7;
            else itemBuilder = new ItemBuilder(hasNext(player) ? XMaterial.GREEN_CARPET.parseMaterial() : XMaterial.GRAY_CARPET.parseMaterial());

        } else {
            buttonText = menu.getPage() > 1 ? "&cPrevious Page" : "&7Previous Page";
            if(BukkitUtil.SERVER_VERSION_INT <= 12) data = menu.getPage() > 1 ? 14 : 7;
            else itemBuilder = new ItemBuilder(menu.getPage() > 1  ? XMaterial.RED_CARPET.parseMaterial() : XMaterial.GRAY_CARPET.parseMaterial());
        }

        itemBuilder.setName(ChatUtil.translate(buttonText));
        itemBuilder.setData(data);

        if (mod > 0 && hasNext(player)) {
            itemBuilder.setLore(ChatUtil.translate("&ePage: " + nextPage));
        } else if (mod < 0 && menu.getPage() > 1) {
            itemBuilder.setLore(ChatUtil.translate("&ePage: " + (menu.getPage() - 1)));
        }

        return itemBuilder.build();
    }

    @Override
    public void clicked(Player player, int i, ClickType clickType, int hb) {
        if (hasNext(player)) {
            menu.modPage(player, mod);
            playNeutral(player);
        }
        else {
            playFail(player);
        }
    }

    private boolean hasNext(Player player) {
        int pg = menu.getPage() + mod;
        return pg > 0 && menu.getPages(player) >= pg;
    }
}
