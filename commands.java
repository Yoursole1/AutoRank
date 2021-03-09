package me.yoursole.autorank.commands;

import me.yoursole.autorank.config.config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED+"[AUTORANK]: Only players can do that");
            return true;
        }
        Player player = ((Player) sender).getPlayer();
        if(cmd.getName().equalsIgnoreCase("ARbypasslist")){
            if(sender.isOp()) {
                if(args.length>=2){
                    if(args[0].equalsIgnoreCase("add")){
                        //ADD
                        String current = config.get().getString("bypassPlayers");
                        assert current != null;
                        String bypassPlayers = current.replace(" ", "");
                        String[] bypassPlayersList = bypassPlayers.split(",",-1);
                        for (String s : bypassPlayersList) {
                            if (s.equalsIgnoreCase(args[1])) {
                                assert player != null;
                                player.sendMessage(ChatColor.GOLD+"[AUTORANK]: This player is already on the bypass list");
                                return true;
                            }
                        }
                        String setter = current+", "+args[1];
                        String setterA = setter.replace(" ","");
                        String setterB = setterA.replace("'","");
                        config.get().set("bypassPlayers", setterB);
                        config.save();
                        sender.sendMessage(ChatColor.GOLD+"[AUTORANK]: "+args[1]+" has been added to the bypass list");
                    }
                    else if(args[0].equalsIgnoreCase("remove")){
                        //REMOVE
                        String bypassPlayersUNEDITED = config.get().getString("bypassPlayers");
                        assert bypassPlayersUNEDITED != null;
                        String bypassPlayers = bypassPlayersUNEDITED.replace(" ", "");
                        String[] bypassPlayersList = bypassPlayers.split(",",-1);
                        int q = 0;
                        for (String s : bypassPlayersList) {
                            if (s.equalsIgnoreCase(args[1])) {
                                q++;
                            }
                        }
                        if(q>0){
                            String current = config.get().getString("bypassPlayers");
                            assert current != null;
                            String lowercurrent = current.toLowerCase();
                            String namelower = args[1].toLowerCase();
                            String setter = lowercurrent.replace(", "+namelower,"");
                            setter = setter.replace(namelower+" ,","");
                            setter = setter.replace(namelower+",","");
                            setter = setter.replace(","+namelower,"");
                            String setterA = setter.replace(" ","");
                            String setterB = setterA.replace("'","");
                            config.get().set("bypassPlayers", setterB);
                            config.save();
                            sender.sendMessage(ChatColor.GOLD+"[AUTORANK]: "+namelower+" has been removed from the bypass list");
                        }
                        else{
                            sender.sendMessage(ChatColor.GOLD+"[AUTORANK]: This player is not on the bypass list");
                        }
                    }
                    else{
                        assert player != null;
                        player.sendMessage(ChatColor.GOLD+"[AUTORANK]: Please specify add or remove (user)");
                    }
                }
                else{
                    assert player != null;
                    player.sendMessage(ChatColor.GOLD+"[AUTORANK]: Usage: /ARbypasslist (add/remove) (user)");
                }
            }
            else{
                assert player != null;
                player.sendMessage(ChatColor.GOLD+"[AUTORANK]: You must be a server Operator");
            }
        }
        return true;
    }
}


