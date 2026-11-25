package io.github.NoOne.nMLEffects;

import org.bukkit.plugin.java.JavaPlugin;

public final class NMLEffects extends JavaPlugin {
    private static NMLEffects instance;

    @Override
    public void onEnable() {
        instance = this;

        CustomEffectTracker.showEffectsTask();
    }

    public static NMLEffects getInstance() {
        return instance;
    }
}
