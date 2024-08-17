package dev.aleesk.arsenal.commands.impl.kit.admin;

import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.commands.BaseCommand;
import dev.aleesk.arsenal.commands.Command;
import dev.aleesk.arsenal.commands.CommandArgs;
import org.bukkit.command.CommandSender;

public class KitAdminCommand extends BaseCommand {

    @Command(name = "kitadmin",permission = "arsenal.command.admin",aliases = {"kita"}, inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();
        String label = command.getLabel();

        if (args.length < 1) {
            sendHelp(sender, label);
        } else {
            if (!command.isPlayer()) {
                ChatUtil.sendMessage(sender, "&cThis command in only executable in game.");
            }
        }
    }

    private void sendHelp(CommandSender sender, String label) {
        ChatUtil.sendMessage(sender, "&a&lKit Commands");
        ChatUtil.sendMessage(sender, "");
        ChatUtil.sendMessage(sender, "  &e/" + label + " editor &7- &fManage All.");
        ChatUtil.sendMessage(sender, "  &e/" + label + " settings &7- &fKit GUI settings.");
        ChatUtil.sendMessage(sender, "  &e/" + label + " give <kit> <player> &7- &fGive a Kit to player.");
        ChatUtil.sendMessage(sender, "  &e/" + label + " cooldown <set|remove> <kit> <player> &7- &fCooldown Kit set/remove to player.");
        ChatUtil.sendMessage(sender, "  &e/" + label + " reset <player|all> &7- &fReset all players or a player data");
        ChatUtil.sendMessage(sender, "  &e/" + label + " list &7- &fList of kits.");
    }
}
