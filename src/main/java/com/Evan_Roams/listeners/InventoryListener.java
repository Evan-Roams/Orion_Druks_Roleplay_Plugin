package com.Evan_Roams.listeners;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.model.InventoryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

    private Os_Druks_Rp_P plugin;

    public InventoryListener(Os_Druks_Rp_P plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        InventoryPlayer inventoryPlayer =  plugin.getTabletInventoryManager().getInventoryPlayer(player);
        if(inventoryPlayer != null){
            //significa que el jugador esta dentro de uno de lo inventarios
            event.setCancelled(true);
            //player.sendMessage("Cancelando click");
            if(event.getCurrentItem() != null && event.getClickedInventory().equals(player.getOpenInventory().getTopInventory())){
                plugin.getTabletInventoryManager().inventoryClick(inventoryPlayer, event.getSlot(), event.getClick());
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player)event.getPlayer();
        plugin.getTabletInventoryManager().removePLayer(player);
        //player.sendMessage("Saliendo Inventario");

    }
}
