package me.yoursole.autorank;

import me.yoursole.autorank.commands.commands;
import me.yoursole.autorank.config.config;
import me.yoursole.autorank.join.join;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Autorank extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new join(), this);
        Objects.requireNonNull(getCommand("ARbypasslist")).setExecutor(new commands());
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        config.setup();
        config.get().addDefault("bypassPlayers","Put A list of players to bypass all the join commands here");
        config.get().addDefault("GuildId", "NONE (If you see this, an issue may have occurred...please delete the config folder and restart)");
        config.get().addDefault("Guild Ranks (highest to lowest)", "Guild Master, ect");
        config.get().addDefault("Top Rank Command", "EXAMPLE: lp user NAME parent set myExampleRank");
        config.get().addDefault("Rank Two Command", "");
        config.get().addDefault("Rank Three Command", "");
        config.get().addDefault("Rank Four Command", "");
        config.get().addDefault("Rank Five Command", "");
        config.get().addDefault("API Key", "run /api on hypixel and input the key here");
        config.get().options().copyDefaults(true);
        config.save();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
