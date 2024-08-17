package dev.aleesk.arsenal.utilities.prompt.defaults;

import dev.aleesk.arsenal.utilities.prompt.Prompt;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class StringPrompt extends Prompt<String> {

	@Override
	public void handleFailed(Player player, String input) {
		player.sendMessage(ChatColor.RED + "The input '" + ChatColor.WHITE + input + ChatColor.RED + "' cannot be null.");
	}

	@Override
	public String handleInput(Player player, String input) {
		if (input == null || input.isEmpty()) {
			return null;
		}
		
		return input;
	}
}
