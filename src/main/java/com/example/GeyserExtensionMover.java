package com.example;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class GeyserExtensionMover extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Moving GeyserExtensions jars...");

        File pluginsFolder = getDataFolder().getParentFile();
        File geyserExtFolder = new File(pluginsFolder, "Geyser-Spigot/extensions");

        if (!geyserExtFolder.exists()) {
            geyserExtFolder.mkdirs();
        }

        File[] jars = pluginsFolder.listFiles((dir, name) ->
                name.startsWith("(GeyserExtension)") && name.endsWith(".jar"));

        if (jars == null || jars.length == 0) {
            getLogger().info("No matching jars found.");
            return;
        }

        for (File jar : jars) {
            File target = new File(geyserExtFolder, jar.getName());
            boolean success = jar.renameTo(target);

            if (success) {
                getLogger().info("Moved: " + jar.getName());
            } else {
                getLogger().warning("Failed to move: " + jar.getName());
            }
        }

        getLogger().info("Done moving Geyser extension jars.");
    }
}


