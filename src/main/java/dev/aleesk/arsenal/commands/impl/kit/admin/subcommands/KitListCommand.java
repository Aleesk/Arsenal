package dev.aleesk.arsenal.commands.impl.kit.admin.subcommands;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.commands.BaseCommand;
import dev.aleesk.arsenal.commands.Command;
import dev.aleesk.arsenal.commands.CommandArgs;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class KitListCommand extends BaseCommand {
    private final Arsenal plugin;

    public KitListCommand(Arsenal plugin) {
        this.plugin = plugin;
    }

    @Command(name = "kitadmin.list", permission = "arsenal.command.admin.list", aliases = {"kita.list"}, inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String label = command.getLabel().replace(".list", "");
        String[] args = command.getArgs();

        if (args.length < 1) {
            ChatUtil.sendMessage(sender, "&cUsage: /" + label + " list <kit|category>");
            return;
        }
        if (args[0].equalsIgnoreCase("kit") || args[0].equalsIgnoreCase("category")) {
            ChatUtil.sendMessage(sender, ChatUtil.NORMAL_LINE);
            ChatUtil.sendMessage(sender, " &6&l" + args[0] + " List");
            ChatUtil.sendMessage(sender, "");
            if (args[0].equalsIgnoreCase("kit")){
                Map<String, Kit> kits = this.plugin.getKitManager().getKits();
                if (kits.isEmpty()) {
                    ChatUtil.sendMessage(sender, " &cThere is no kit create");
                } else {
                    int index = 1;
                    for (Kit kit : kits.values()) {
                        ChatUtil.sendMessage(sender, " &6" + index + ". ▸ &r" + kit.getName() + " &eKit");
                        index++;
                    }
                }
            }else if(args[0].equalsIgnoreCase("category")){
                Map<String, Category> kits = this.plugin.getCategoryManager().getCategories();
                if (kits.isEmpty()) {
                    ChatUtil.sendMessage(sender, " &cThere is no categories create");
                } else {
                    int index = 1;
                    for (Category kit : kits.values()) {
                        ChatUtil.sendMessage(sender, " &6" + index + ". ▸ &r" + kit.getName() + " &eCategory");
                        index++;
                    }
                }
            }
            ChatUtil.sendMessage(sender, ChatUtil.NORMAL_LINE);
        }
    }
}
