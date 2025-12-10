@Override
public void onEnable() {
    getLogger().info("Starting Geyser Extension mover...");

    File pluginsFolder = getDataFolder().getParentFile();
    File updateFolder = new File(pluginsFolder, "update"); // folder to watch
    File geyserExtFolder = new File(pluginsFolder, "Geyser-Spigot/extensions");

    if (!geyserExtFolder.exists()) {
        geyserExtFolder.mkdirs();
    }

    // Schedule a repeating task every 5 seconds (100 ticks)
    getServer().getScheduler().runTaskTimer(this, () -> {
        File[] jars = updateFolder.listFiles((dir, name) ->
                name.contains("(GeyserExtension)") && name.endsWith(".jar"));

        if (jars == null || jars.length == 0) {
            return; // nothing to move
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
    }, 0L, 100L); // 0 tick delay, 100 tick repeat (5 seconds)
}
