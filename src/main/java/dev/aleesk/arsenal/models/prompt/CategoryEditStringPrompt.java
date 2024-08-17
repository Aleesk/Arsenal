package dev.aleesk.arsenal.models.prompt;

import com.cryptomorin.xseries.XSound;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.menus.editor.categories.CategoryEditMenu;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.menu.callback.Callback;
import dev.aleesk.arsenal.utilities.prompt.Prompt;
import dev.aleesk.arsenal.utilities.prompt.defaults.StringPrompt;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class CategoryEditStringPrompt extends StringPrompt {

    private final Category category;
    private final Arsenal plugin;
    private final Callback<String> callback;

    @Override
    public void handleBegin(Player player) {
        player.closeInventory();
    }

    @Override
    public void handleCancel(Player player) {
        ChatUtil.sendMessage(player, "&cYou have cancelled edit '&f" + this.category.getName() + "&c' category process.");
        Bukkit.getScheduler().runTask(Arsenal.get(), () -> {
            new CategoryEditMenu(category, plugin).openMenu(player);
        });
        XSound.ENTITY_VILLAGER_NO.play(player);
    }

    @Override
    public Prompt<?> acceptInput(Player player, String value) {
        callback.callback(value.trim());

        Bukkit.getScheduler().runTask(Arsenal.get(), () -> {
            new CategoryEditMenu(category, plugin).openMenu(player);
        });

        return null;
    }
}
