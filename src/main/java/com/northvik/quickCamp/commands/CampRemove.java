package com.northvik.quickCamp.commands;

import com.northvik.quickCamp.QuickCamp;
import com.northvik.quickCamp.utils.ClaimHandler;
import com.northvik.quickCamp.utils.ConfigsInitialize;
import com.northvik.quickCamp.utils.LocatedCampPDC;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class CampRemove {
    Player player;
    Location camplocation;
    QuickCamp plugin;
    ConfigsInitialize ci;
    public CampRemove(Player player, Location camplocation, QuickCamp plugin, ConfigsInitialize ci){
        this.player = player;
        this.camplocation = camplocation;
        this.plugin = plugin;
        this.ci = ci;
    }
    public void removeCamp(){

        String path = player.getUniqueId().toString();
        LocatedCampPDC lc = new LocatedCampPDC(player,camplocation,plugin);
//        CampCustomBlockData campCBD = new CampCustomBlockData(plugin, player);
        ClaimHandler claimHandler = new ClaimHandler();
        List<Map<?,?>> locationList = ci.getCampLocationConfig().getMapList(path);

        if (locationList.isEmpty()){
            player.sendMessage(ChatColor.RED + "You don't have any camp to remove!");
        }else {

            for (Map<?, ?> map : locationList) {
                Location loc = Location.deserialize((Map<String, Object>) map);

//                for (Location location : campCBD.expandClaim(loc.getBlock())){
//                    campCBD.removeCustomBlockData(location.getBlock());
//                }

                loc.getBlock().setType(Material.AIR, false);
            }

            ci.getCampLocationConfig().set(path, null);
            ci.saveConfig(ci.getCampLocationConfig(), ci.getLocationFile());
            lc.removeCamping();
            claimHandler.removeClaim(player);

            player.sendMessage(ChatColor.GREEN + "Your camp been removed!");
        }
    }

}
