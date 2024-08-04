package com.Evan_Roams.listeners;

import com.Evan_Roams.managers.DataManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEventListener implements Listener {

    private final DataManager dataManager;

    public PlayerEventListener(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        YamlConfiguration playerConfig = dataManager.getPlayerConfig(playerName);
        YamlConfiguration playerDni = dataManager.getDniConfig(playerName);


        // Aquí podrías realizar otras acciones si es necesario, como inicializar valores o cargar datos
        dataManager.savePlayerConfig(playerName, playerConfig);
        dataManager.saveDniConfig(playerName, playerDni);
    }



}
