package io.github.NoOne.nMLEffects;

import io.github.NoOne.nMLPlayerStats.statSystem.StatChangeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class CustomEffectTracker {
    private static NMLEffects nmlEffects = NMLEffects.getInstance();
    private static HashMap<UUID, ArrayList<CustomEffect>> customEffects = new HashMap<>();

    public static void showEffectsTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (UUID uuid : customEffects.keySet()) {
                    if (Bukkit.getPlayer(uuid) != null) {
                        Player player = Bukkit.getPlayer(uuid);
                        Component header = Component
                                .text("Effects:", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD)
                                .appendNewline();

                        Component footer = Component.empty().append(header);
                        ArrayList<CustomEffect> effects = customEffects.get(uuid);

                        for (CustomEffect customEffect : effects) {
                            if (effects.getFirst().equals(customEffect)) { // if its the first custom effect then dont include a space behind it
                                footer = footer.append(customEffect.toComponent());
                            } else {
                                footer = footer.append(Component.text(" ")).append(customEffect.toComponent());
                            }
                        }

                        player.sendPlayerListFooter(footer);
                    }
                }
            }
        }.runTaskTimer(nmlEffects, 0, 20);
    }

    public static void tickDownEffectsTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Iterator<UUID> mapIterator = customEffects.keySet().iterator();

                while (mapIterator.hasNext()) {
                    UUID uuid = mapIterator.next();

                    if (Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline()) {
                        ArrayList<CustomEffect> effects = customEffects.get(uuid);

                        // tick down the seconds for every effect
                        effects.forEach(CustomEffect::tickDown);

                        for (CustomEffect customEffect : effects) {
                            if (customEffect.isMarkedForRemoval()) { // remove the stat change if the effect is marked for removal by tick down
                                Bukkit.getPluginManager().callEvent(new StatChangeEvent(Bukkit.getPlayer(uuid), customEffect.getPlayerStat(), -customEffect.getChange()));
                            }
                        }

                        effects.removeIf(CustomEffect::isMarkedForRemoval);

                        if (effects.isEmpty()) {
                            mapIterator.remove();
                        }
                    }

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

    public static void removeCustomEffect(Player player, CustomEffect customEffect) {
        ArrayList<CustomEffect> effects = customEffects.getOrDefault(player.getUniqueId(), new ArrayList<>());
        Iterator<CustomEffect> iterator = effects.iterator();

        while (iterator.hasNext()) {
            CustomEffect effect = iterator.next();

            if (effect.equals(customEffect)) {
                iterator.remove();
                Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, customEffect.getPlayerStat(), -customEffect.getChange()));
                break;
            }
        }
    }

    public static HashMap<UUID, ArrayList<CustomEffect>> getCustomEffects() {
        return customEffects;
    }
}
