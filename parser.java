package me.yoursole.autorank.join;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.yoursole.autorank.config.config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

public class parser {
    public static String getRank(UUID uuid) throws IOException {
        JsonObject guild;
        String url = "https://api.hypixel.net/guild?key="+config.get().getString("API Key").
                replace(" ","")+"&id="
                + config.get().getString("GuildId");
        String UUIDJson="";
        boolean worked = false;
        try{
            UUIDJson = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
            worked=true;
        }catch (java.io.IOException exception){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED+"[AUTORANK]: Please make sure you have a valid API key and Guild ID in the config");
        }
        String rank = "";
        if(worked){
            JsonObject JsonFile = (JsonObject) new JsonParser().parse(UUIDJson);
            guild = (JsonObject) JsonFile.getAsJsonObject().get("guild");
            JsonArray members = guild.get("members").getAsJsonArray();
            for(int i = 0; i < members.size(); i++){
                JsonObject user = (JsonObject) members.get(i);
                if(user.getAsJsonObject().get("uuid").toString().equals("\"" + uuid.toString().replace("-", "") + "\"")){
                    rank = user.getAsJsonObject().get("rank").toString();
                }
            }
        }
        return rank;
    }
}
