package dev.aleesk.arsenal.utilities.prompt;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PromptListener implements Listener {

	private final PromptHandler promptHandler;
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onChat(AsyncPlayerChatEvent event) {
		if(promptHandler.getPromptByPlayer(event.getPlayer()) != null) {
			promptHandler.handleChat(event, promptHandler.getPromptByPlayer(event.getPlayer()).getPrompt());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if(promptHandler.getPromptByPlayer(event.getPlayer()) != null) {
			promptHandler.getPromptMap().remove(event.getPlayer().getUniqueId());
		}
	}
}
