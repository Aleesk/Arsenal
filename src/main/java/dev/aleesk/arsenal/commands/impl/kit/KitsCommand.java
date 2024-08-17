package dev.aleesk.arsenal.commands.impl.kit;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.view.KitGUI;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.commands.BaseCommand;
import dev.aleesk.arsenal.commands.Command;
import dev.aleesk.arsenal.commands.CommandArgs;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitsCommand extends BaseCommand {
    private final Arsenal plugin;

    public KitsCommand(Arsenal plugin) {
        this.plugin = plugin;
    }

    @Command(name = "kit", aliases = {"kits", "gkit", "gkits"})
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();
        Player player = command.getPlayer();

        if (!command.isPlayer()) {
            ChatUtil.sendMessage(sender, "&cThis command in only executable in game.");

        } else if (args.length < 1){
            Category category = plugin.getCategoryManager().getCategory();
            if (category == null) {
                ChatUtil.sendMessage(player, "&cNo default category found. configure this in settings menu.");
            } else {
                new KitGUI(plugin, category).openMenu(player);
            }
        } else {
            Kit kit = this.plugin.getKitManager().getByName(args[0]);
            if (kit == null) {
                ChatUtil.sendMessage(player, "&c'" + args[0] + "' kit not found.");
                return;
            }
            kit.give(player, false);
        }
    }
}
