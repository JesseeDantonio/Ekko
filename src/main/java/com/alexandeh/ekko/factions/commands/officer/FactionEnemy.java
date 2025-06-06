package com.alexandeh.ekko.factions.commands.officer;

import com.alexandeh.ekko.factions.commands.Faction;
import com.alexandeh.ekko.factions.type.PlayerFaction;
import com.alexandeh.ekko.profiles.Profile;
import com.alexandeh.ekko.utils.command.Command;
import com.alexandeh.ekko.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Copyright 2016 Alexander Maxwell
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Alexander Maxwell
 */
public class FactionEnemy extends Faction {

    public FactionEnemy(){
        if (!(mainConfig.getBoolean("FACTION_GENERAL.ALLIES.ENABLED"))) {
            main.getFramework().unregisterCommands(this);
        }
    }

    @Command(name = "f.enemy", aliases = {"faction.enemy", "factions.enemy", "f.neutral", "factions.neutral", "faction.neutral"}, inFactionOnly = true, isOfficerOnly = true)
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.getArgs().length == 0) {
            player.sendMessage(langConfig.getString("TOO_FEW_ARGS.ENEMY"));
            return;
        }

        Profile profile = Profile.getByUuid(player.getUniqueId());
        PlayerFaction playerFaction = profile.getFaction();

        String factionName = command.getArgs(0);
        com.alexandeh.ekko.factions.Faction faction = com.alexandeh.ekko.factions.Faction.getByName(factionName);
        PlayerFaction enemyFaction = null;

        if (faction instanceof PlayerFaction) {
            enemyFaction = (PlayerFaction) faction;
        }

        if (faction == null || (!(faction instanceof PlayerFaction))) {
            enemyFaction = PlayerFaction.getByPlayerName(factionName);

            if (enemyFaction == null) {
                player.sendMessage(langConfig.getString("ERROR.NO_FACTIONS_FOUND").replace("%NAME%", factionName));
                return;
            }
        }

        if (playerFaction.getName().equals(enemyFaction.getName())) {
            player.sendMessage(langConfig.getString("ERROR.CANT_ENEMY_YOURSELF"));
            return;
        }

        if (!enemyFaction.getAllies().contains(playerFaction)) {
            player.sendMessage(langConfig.getString("ERROR.ALREADY_HAVE_RELATION").replace("%FACTION%", enemyFaction.getName()));
            return;
        }

        enemyFaction.getAllies().remove(playerFaction);
        playerFaction.getAllies().remove(enemyFaction);

        playerFaction.sendMessage(langConfig.getString("ANNOUNCEMENTS.FACTION_NOW_ENEMY").replace("%FACTION%", enemyFaction.getName()));
        enemyFaction.sendMessage(langConfig.getString("ANNOUNCEMENTS.FACTION_NOW_ENEMY").replace("%FACTION%", playerFaction.getName()));

        Bukkit.getPluginManager().callEvent(new com.alexandeh.ekko.factions.events.FactionEnemy(new PlayerFaction[]{playerFaction, enemyFaction}));
    }
}
