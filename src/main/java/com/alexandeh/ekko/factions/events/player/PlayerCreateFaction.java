package com.alexandeh.ekko.factions.events.player;

import com.alexandeh.ekko.factions.events.Faction;
import com.alexandeh.ekko.factions.type.PlayerFaction;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * Copyright 2016 Alexander Maxwell
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Alexander Maxwell
 */
@Getter
public class PlayerCreateFaction extends Faction {

    private PlayerFaction faction;
    private Player player;

    public PlayerCreateFaction(Player player, PlayerFaction faction) {
        this.player = player;
        this.faction = faction;
    }

}
