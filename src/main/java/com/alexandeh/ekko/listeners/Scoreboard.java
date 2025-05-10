package com.alexandeh.ekko.listeners;

import com.alexandeh.ekko.factions.events.FactionAlly;
import com.alexandeh.ekko.factions.events.FactionEnemy;
import com.alexandeh.ekko.factions.events.player.PlayerDisbandFaction;
import com.alexandeh.ekko.factions.events.player.PlayerJoinFaction;
import com.alexandeh.ekko.factions.events.player.PlayerLeaveFaction;
import com.alexandeh.ekko.factions.type.PlayerFaction;
import com.alexandeh.ekko.profiles.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright 2016 Alexander Maxwell
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Alexander Maxwell
 */
public class Scoreboard implements Listener {

    @EventHandler
    public void onJoinFaction(PlayerJoinFaction event) {
        for (Player player : event.getFaction().getOnlinePlayers()) {
            Profile profile = Profile.getByUuid(player.getUniqueId());
            profile.updateTab();
        }
        for (PlayerFaction ally : event.getFaction().getAllies()) {
            for (Player allyPlayer : ally.getOnlinePlayers()) {
                Profile profile = Profile.getByUuid(allyPlayer.getUniqueId());
                profile.updateTab();
            }
        }
    }

    @EventHandler
    public void onLeaveFaction(PlayerLeaveFaction event) {
        Set<Player> toLoop = new HashSet<>(event.getFaction().getOnlinePlayers());
        toLoop.add(event.getPlayer());
        for (Player player : toLoop) {
            Profile profile = Profile.getByUuid(player.getUniqueId());
            profile.updateTab();
        }
        for (PlayerFaction ally : event.getFaction().getAllies()) {
            for (Player allyPlayer : ally.getOnlinePlayers()) {
                Profile profile = Profile.getByUuid(allyPlayer.getUniqueId());
                profile.updateTab();
            }
        }
    }

    @EventHandler
    public void onDisbandFaction(PlayerDisbandFaction event) {
        for (Player player : event.getFaction().getOnlinePlayers()) {
            Profile profile = Profile.getByUuid(player.getUniqueId());
            profile.updateTab();
        }
        for (PlayerFaction ally : event.getFaction().getAllies()) {
            for (Player allyPlayer : ally.getOnlinePlayers()) {
                Profile profile = Profile.getByUuid(allyPlayer.getUniqueId());
                profile.updateTab();
            }
        }
    }


    @EventHandler
    public void onAllyFaction(FactionAlly event) {
        for (PlayerFaction faction : event.getFactions()) {
            for (Player player : faction.getOnlinePlayers()) {
                Profile profile = Profile.getByUuid(player.getUniqueId());
                profile.updateTab();
            }
        }
    }

    @EventHandler
    public void onEnemyFaction(FactionEnemy event) {
        for (PlayerFaction faction : event.getFactions()) {
            for (Player player : faction.getOnlinePlayers()) {
                Profile profile = Profile.getByUuid(player.getUniqueId());
                profile.updateTab();
            }
        }
    }

}
