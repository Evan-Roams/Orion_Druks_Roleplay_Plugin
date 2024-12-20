package com.Evan_Roams.listeners;

import com.Evan_Roams.managers.BankManager;
import com.Evan_Roams.managers.DataManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class PlayerEventListener implements Listener {

    private final DataManager dataManager;
    private final BankManager bankManager;

    public PlayerEventListener(DataManager dataManager, BankManager bankManager) {
        this.dataManager = dataManager;
        this.bankManager = bankManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        YamlConfiguration playerConfig = dataManager.getPlayerConfig(playerName);
        YamlConfiguration playerDni = dataManager.getDniConfig(playerName);

        // inicializar valores o cargar datos
        dataManager.savePlayerConfig(playerName, playerConfig);
        dataManager.saveDniConfig(playerName, playerDni);


    }



}
