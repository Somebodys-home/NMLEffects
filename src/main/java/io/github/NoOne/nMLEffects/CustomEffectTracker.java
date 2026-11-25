package io.github.NoOne.nMLEffects;

import io.github.NoOne.nMLPlayerStats.statSystem.StatChangeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CustomEffectTracker {
    private static NMLEffects nmlEffects = NMLEffects.getInstance();
    private static HashMap<UUID, ArrayList<CustomEffect>> customEffects = new HashMap<>();

    public static void showEffectsTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Component footer = Component.text("It is: today!", NamedTextColor.LIGHT_PURPLE);
                    player.sendPlayerListFooter(footer);
                }
            }
        }.runTaskTimer(nmlEffects, 0, 20);
    }

    public static void addCustomEffect(Player player, CustomEffect customEffect) {
        ArrayList<CustomEffect> effects = customEffects.getOrDefault(player.getUniqueId(), new ArrayList<>());

        effects.add(customEffect);
        customEffects.put(player.getUniqueId(), effects);
        Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, customEffect.getPlayerStat(), customEffect.getChange()));
    }
}
