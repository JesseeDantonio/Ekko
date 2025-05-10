package com.alexandeh.ekko.factions.commands.officer;

import com.alexandeh.ekko.factions.commands.Faction;
import com.alexandeh.ekko.utils.command.Command;
import com.alexandeh.ekko.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Copyright 2016 Alexander Maxwell
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Alexander Maxwell
 */
public class FactionClaim extends Faction {
    @Command(name = "f.claim", aliases = {"faction.claim", "factions.claim", "factions.claimland", "f.claimland", "faction.claimland"}, inFactionOnly = true, isOfficerOnly = true)
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        if (!(player.getInventory().contains(com.alexandeh.ekko.factions.Faction.getWand(main)))) {
            player.getInventory().addItem(com.alexandeh.ekko.factions.Faction.getWand(main));
            player.sendMessage(langConfig.getString("FACTION_OTHER.RECEIVED_WAND"));
        }
    }
}
