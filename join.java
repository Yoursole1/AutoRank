package me.yoursole.autorank.join;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.yoursole.autorank.config.config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class join implements Listener {
    static ArrayList<String> confirmGuild = new ArrayList<>();
    static boolean setup = false;
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws IOException {
        String name = e.getPlayer().getDisplayName();
        if(e.getPlayer().isOp()&& Objects.equals(config.get().getString
                ("GuildId"), "NONE (If you see this, an issue may have occurred" +
                "...please delete the config folder and restart)")){
            confirmGuild.add(e.getPlayer().getDisplayName());
            e.getPlayer().sendMessage(ChatColor.GOLD+"[AUTORANK]: Do you want to set up Guild Info");
            e.getPlayer().sendMessage(ChatColor.GOLD+"[AUTORANK]: Type \"yes\" or \"no\"");
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Autorank")), () -> {
                if(setup){
                    JsonElement guild;
                    String url = "https://api.hypixel.net/findGuild?key="+config.get().getString("API Key")+"&byUuid="+e.getPlayer().getUniqueId();
                    String UUIDJson = "";
                    boolean getguild = false;
                    try{
                        UUIDJson = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
                        getguild=true;
                    }catch(IOException exception){
                        Bukkit.getServer().getConsoleSender().sendMessage
                                (ChatColor.RED + "[AUTORANK]: Navigate to AutoRank Config and enter a valid API key");
                        return;
                    }
                    if(getguild){
                        JsonObject JsonFile = (JsonObject) new JsonParser().parse(UUIDJson);
                        guild = JsonFile.getAsJsonObject().get("guild");
                        String guildID = guild.toString();
                        if(guildID.equalsIgnoreCase("null")){
                            e.getPlayer().sendMessage(ChatColor.GOLD+"[AUTORANK]: You are not in a Guild, therefore the guild API info was not updated. " +
                                    "Please get a member of the correct guild to join as an Operator or manually input the guild ID");
                        }else{
                            config.get().set("GuildId", guildID.replace("\"","").replace("'",""));
                            config.save();
                            //MESSAGE
                            e.getPlayer().sendMessage(ChatColor.GOLD+"[AUTORANK]: Your guild id has been updated in the configuration-This will not " +
                                    "happen again without deleting and reloading the config");
                            //-------
                        }
                    }
                }else{
                    e.getPlayer().sendMessage(ChatColor.GOLD+"[AUTORANK]: Guild setup has been canceled");
                }
            }, 100L);
            return;
        }



        String bypassPlayersUNEDITED = config.get().getString("bypassPlayers");
        assert bypassPlayersUNEDITED != null;
        String bypassPlayers = bypassPlayersUNEDITED.replace(" ", "");
        String[] bypassPlayersList = bypassPlayers.split(",",-1);
        UUID playerUUID = e.getPlayer().getUniqueId();
        for (String s : bypassPlayersList) {
            if (s.equalsIgnoreCase(name)) {
                return;
            }
        }
        char[] rank = new char[0];
        String guildRank = parser.getRank(playerUUID);
        guildRank = guildRank.toLowerCase();
        if(guildRank.length()>1){
            rank= guildRank.toCharArray();
        }else{
            System.out.println(ChatColor.RED+"[AUTORANK]: Possible Issue, either this is a bug or someone that is not in the guild joined");
            return;
        }

        String[] configRanks = Objects.requireNonNull(config.get().getString("Guild Ranks (highest to lowest)"))
                .toLowerCase().replace(" ","").split(",");
        //"Guild Master"
        //"Officer"
        //"Junior Officer"
        //"Member"
        //"Trial Member"
        if(rank[1]== configRanks[0].charAt(0)){
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String command = Objects.requireNonNull(config.get().getString("Top Rank Command"))
                    .replace("NAME",e.getPlayer().getDisplayName());
            Bukkit.dispatchCommand(console, command);
        }
        else if(configRanks.length>1&&rank[1]==configRanks[1].charAt(0)){
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String command = Objects.requireNonNull(config.get().getString("Rank Two Command"))
                    .replace("NAME",e.getPlayer().getDisplayName());
            Bukkit.dispatchCommand(console, command);
        }
        else if(configRanks.length>2&&rank[1]==configRanks[2].charAt(0)){
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String command = Objects.requireNonNull(config.get().getString("Rank Three Command"))
                    .replace("NAME",e.getPlayer().getDisplayName());
            Bukkit.dispatchCommand(console, command);
        }
        else if(configRanks.length>3&&rank[1]==configRanks[3].charAt(0)){
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String command = Objects.requireNonNull(config.get().getString("Rank Four Command"))
                    .replace("NAME",e.getPlayer().getDisplayName());
            Bukkit.dispatchCommand(console, command);
        }
        else if(configRanks.length>4&&rank[1]==configRanks[4].charAt(0)){
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String command = Objects.requireNonNull(config.get().getString("Rank Five Command"))
                    .replace("NAME",e.getPlayer().getDisplayName());
            Bukkit.dispatchCommand(console, command);
        }
    }
    @EventHandler
    public static void onPlayerChat(AsyncPlayerChatEvent e){
        for (String s : confirmGuild) {
            if (e.getPlayer().getDisplayName().equalsIgnoreCase(s)) {
                if (e.getMessage().equalsIgnoreCase("yes")) {
                    setup = true;
                } else if (e.getMessage().equalsIgnoreCase("no")) {
                    setup = false;
                } else {
                    e.getPlayer().sendMessage(ChatColor.GOLD + "[AUTORANK]: Please respond yes or no");
                }
            }
        }
    }
}

