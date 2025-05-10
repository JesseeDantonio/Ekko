package com.alexandeh.ekko.factions.commands.officer;

import com.alexandeh.ekko.Ekko;
import com.alexandeh.ekko.factions.commands.FactionCommand;
import com.alexandeh.ekko.factions.type.PlayerFaction;
import com.alexandeh.ekko.profiles.Profile;
import com.alexandeh.ekko.utils.command.Command;
import com.alexandeh.ekko.utils.command.CommandArgs;
import com.alexandeh.ekko.utils.player.SimpleOfflinePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

import static net.kyori.adventure.text.Component.text;

/**
 * Copyright 2016 Alexander Maxwell
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Alexander Maxwell
 */
public class FactionInviteCommand extends FactionCommand {

    private final Ekko ekko;
    public FactionInviteCommand(Ekko ekko) {
        this.ekko = ekko;
    }

    @Command(name = "f.invite", aliases = {"faction.invite", "factions.invite", "f.inv", "factions.inv", "faction.inv"}, inFactionOnly = true, isOfficerOnly = true)
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.getArgs().length == 0) {
            player.sendMessage(langConfig.getString("TOO_FEW_ARGS.INVITE"));
            return;
        }

        Profile profile = Profile.getByUuid(player.getUniqueId());
        PlayerFaction playerFaction = profile.getFaction();

        if (command.getArgs(0).equalsIgnoreCase(player.getName())) {
            player.sendMessage(langConfig.getString("ERROR.INVITE_YOURSELF"));
            return;
        }


        UUID uuid;
        String name;
        Player toInvite = Bukkit.getPlayer(command.getArgs(0));

        if (toInvite == null) {
            SimpleOfflinePlayer offlinePlayer = SimpleOfflinePlayer.getByName(command.getArgs(0));
            if (offlinePlayer != null) {
                uuid = offlinePlayer.getUuid();
                name = offlinePlayer.getName();
            } else {
                player.sendMessage(langConfig.getString("ERROR.NOT_ONLINE").replace("%PLAYER%", command.getArgs(0)));
                return;
            }
        } else {
            uuid = toInvite.getUniqueId();
            name = toInvite.getName();
        }

        if (playerFaction.getAllPlayerUuids().contains(uuid)) {
            player.sendMessage(langConfig.getString("ERROR.INVITE_MEMBER").replace("%PLAYER%", name));
            return;
        }


        if (playerFaction.getInvitedPlayers().containsKey(uuid)) {
            player.sendMessage(langConfig.getString("ERROR.ALREADY_INVITED").replace("%PLAYER%", name));
            return;
        }

        if (toInvite != null) {
            Component fancyMessage = text(langConfig.getString("FACTION_OTHER.INVITED_TO_JOIN").replace("%FACTION%", playerFaction.getName()))
                    .clickEvent(ClickEvent.runCommand("/f join " + playerFaction.getName().toLowerCase()));

            ekko.adventure().player(toInvite).sendMessage(fancyMessage);
        }

        playerFaction.getInvitedPlayers().put(uuid, player.getUniqueId());
        playerFaction.sendMessage(langConfig.getString("ANNOUNCEMENTS.FACTION.PLAYER_INVITED").replace("%PLAYER%", player.getName()).replace("%INVITED_PLAYER%", name));
    }
}
