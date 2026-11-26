package io.github.NoOne.nMLEffects;

import org.bukkit.plugin.java.JavaPlugin;

public final class NMLEffects extends JavaPlugin {
    private static NMLEffects instance;

    @Override
    public void onEnable() {
        instance = this;

        CustomEffectTracker.showEffectsTask();
        CustomEffectTracker.tickDownEffectsTask();

        getCommand("giveEffect").setExecutor(new GiveEffectCommand());
        getCommand("removeEffect").setExecutor(new RemoveEffectCommand());
    }

    @Override
    public void onDisable() {
        CustomEffectTracker.clearEffectMap();
    }

    public static NMLEffects getInstance() {
        return instance;
    }
}
