package io.github.NoOne.nMLEffects.saveEffects;

import io.github.NoOne.nMLConfig.ConfigManager;
import io.github.NoOne.nMLConfig.ConfigTemplate;
import io.github.NoOne.nMLEffects.CustomEffect;
import io.github.NoOne.nMLPlayerStats.statSystem.PlayerStat;
import io.github.NoOne.nMLPlayerStats.statSystem.StatChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SavedEffectsManager extends ConfigManager {
    public SavedEffectsManager(ConfigTemplate configTemplate) {
        super(configTemplate);
    }

    public void saveEffectsToConfig(HashMap<UUID, ArrayList<CustomEffect>> customEffects) {
        for (UUID uuid : customEffects.keySet()) {
            HashMap<PlayerStat, Double> totalStats = new HashMap<>(){{
                for (CustomEffect customEffect : customEffects.get(uuid)) {
                    PlayerStat playerStat = customEffect.getPlayerStat();
                    double change = customEffect.getChange();

                    put(playerStat, getOrDefault(playerStat, 0.0) + change);
                }
            }};

            String string = playerStatsToString(totalStats);

            config.set(uuid.toString(), string);
        }
    }

    public void removeEffectsFromConfig(Player player) {
        String uuid = player.getUniqueId().toString();
        HashMap<PlayerStat, Double> totalStats = playerStatsFromString(config.getString(uuid));

        for (Map.Entry<PlayerStat, Double> entry : totalStats.entrySet()) {
            Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, entry.getKey(), -entry.getValue()));
        }

        config.set(uuid, null);
    }

    public boolean hasUUID(Player player) {
        String uuid = player.getUniqueId().toString();
        player.sendMessage("Joining UUID: " + uuid);
        player.sendMessage("Keys in config: " + config.getKeys(false));
        player.sendMessage("contains: " + config.contains(uuid));
        return config.contains(uuid);
    }

    private String playerStatsToString(HashMap<PlayerStat, Double> stats) {
        String string = "";

        for (Map.Entry<PlayerStat, Double> entry : stats.entrySet()) {
            string += entry.getKey().toString() + ":" + entry.getValue() + "/";
        }

        return string;
    }

    private HashMap<PlayerStat, Double> playerStatsFromString(String stats) {
        return new HashMap<>(){{
            for (String string : stats.split("/")) { // for every unique stat
                String[] split = string.split(":"); // spits into stat and value

                put(PlayerStat.fromString(split[0]), Double.parseDouble(split[1]));
            }
        }};
    }
}
