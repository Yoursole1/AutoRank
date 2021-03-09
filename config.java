package me.yoursole.autorank.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class config {
    private static File configFile;
    private static FileConfiguration configuration;


    public static void setup(){
        configFile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager()
                .getPlugin("autorank")).getDataFolder(), "AutoRankConfig.yml");
        if(!configFile.exists()){
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configuration= YamlConfiguration.loadConfiguration(configFile);
    }
    public static FileConfiguration get(){
        return configuration;
    }
    public static void save(){
        try {
            configuration.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void reload(){
        configuration=YamlConfiguration.loadConfiguration(configFile);
    }


}
