package io.github.NoOne.nMLEffects.saveEffects;

import io.github.NoOne.nMLEffects.NMLEffects;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

// this listener is here just because removing the stats from effects when the server shuts down is painfully inconsistent
// so instead we save the effects they had when the server started and clear them the first time they rejoin
public class SaveEffectsListener implements Listener {
    private SavedEffectsManager savedEffectsManager;

    public SaveEffectsListener(NMLEffects nmlEffects) {
        savedEffectsManager = nmlEffects.getSavedEffectsManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (savedEffectsManager.hasUUID(player)) {
            savedEffectsManager.removeEffectsFromConfig(player);
            player.sendMessage("had saved effects to remove");
            player.sendMessage("did it clear? " + !savedEffectsManager.hasUUID(player));
        } else {
            player.sendMessage("did not have saved effects to remove");
        }
    }
}
