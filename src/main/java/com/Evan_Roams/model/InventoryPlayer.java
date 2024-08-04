package com.Evan_Roams.model;

import org.bukkit.entity.Player;

public class InventoryPlayer {

    private Player player;
    private InventorySection section;
    private Player selectedPlayer; // Agrega este campo para almacenar al jugador seleccionado para multar


    public InventoryPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public InventorySection getSection() {
        return section;
    }

    public void setSection(InventorySection section) {
        this.section = section;
    }

    public Player getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(Player selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }
}