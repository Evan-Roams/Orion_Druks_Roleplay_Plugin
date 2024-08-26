package com.Evan_Roams.utils;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.managers.BankManager;
import com.Evan_Roams.managers.EconomyManager;
import com.Evan_Roams.model.InventoryPlayer;
import com.Evan_Roams.model.InventorySection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

import static com.Evan_Roams.managers.TabletInventoryManager.*;
public class TabletInventoryClickManager {


    static BankManager bankManager;
    static EconomyManager economyManager;

    public static void inventoryClick(InventoryPlayer inventoryPlayer, int slot, ClickType clickType) {

        Player player = inventoryPlayer.getPlayer();
        InventorySection section = inventoryPlayer.getSection();

        // Cargar el archivo ValorMultas.yml
        File valorMultasFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "multas/ValorMultas.yml");
        FileConfiguration valorMultasConfig = YamlConfiguration.loadConfiguration(valorMultasFile);

        if (section.equals(InventorySection.MENU_MAIN)) {
            if (slot == 10) {
                openLegalidadInventory(inventoryPlayer);
            } else if (slot == 11) {
                openFinanzasInventory(inventoryPlayer);
            }


        } else if (section.equals(InventorySection.MENU_LEGALIDAD)) {
            if (slot == 19) {
                openDniInventory(inventoryPlayer);
            } else if (slot == 21 && clickType == ClickType.LEFT) {
                openMisMultasInventory(inventoryPlayer);
            } else if (slot == 21 && clickType == ClickType.RIGHT) {
                if (!player.hasPermission("os_druks_rp_p.policia.multar")){
                    player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&fNo tienes permisos para este comando"));

                } else {
                    openMultarInventory(inventoryPlayer);
                }
            } else if (slot == 40) {
                openMainInventory(inventoryPlayer);
            }


        } else if (section.equals(InventorySection.MENU_DNI)) {
            if (slot == 52) {
                openMainInventory(inventoryPlayer);
            } else if (slot == 40){
                openLegalidadInventory(inventoryPlayer);
            }


        } else if (section.equals(InventorySection.MENU_MISMULTAS)) {
            if (slot == 52) {
                openMainInventory(inventoryPlayer);
            } else if (clickType == ClickType.RIGHT) {
                EconomyManager.pagarMulta(player, valorMultasConfig, slot);
            }


        } else if (section.equals(InventorySection.MENU_MULTAR)) {
            if (slot == 52) {
                openMainInventory(inventoryPlayer);
            } else {
                // Seleccionar jugador
                ItemStack clickedItem = player.getOpenInventory().getTopInventory().getItem(slot);
                if (clickedItem != null && clickedItem.getType() == Material.SKULL_ITEM) {
                    String playerName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
                    Player targetPlayer = Bukkit.getPlayer(playerName);

                    if (targetPlayer != null) {
                        inventoryPlayer.setSelectedPlayer(targetPlayer); // Guardar jugador seleccionado
                        openSeleccionarMulta(inventoryPlayer); // Abrir inventario de selección de multa
                    }
                }
            }


        } else if (section.equals(InventorySection.MENU_SELECCIONAR_MULTA)) {
            if (slot == 52) {
                openMultarInventory(inventoryPlayer);
            } else if (slot >= 0 && slot <= 9) {
                // Obtener el nombre del jugador seleccionado
                Player selectedPlayer = inventoryPlayer.getSelectedPlayer();
                EconomyManager.multarJugador(player, selectedPlayer, slot);
            }
        } else if (section.equals(InventorySection.MENU_FINANZAS)) {
            if (slot == 23) {
                openEnviarDineroSeleccionarInventory(inventoryPlayer);
            } else if (slot == 19) {
                openMainInventory(inventoryPlayer);
            }


        } else if (section.equals(InventorySection.MENU_ENVIAR_DINERO_SELECCIONAR)) {
            if (slot == 52) {
                openEnviarDineroInventory(inventoryPlayer);
            } else {
                // Seleccionar jugador
                ItemStack clickedItem = player.getOpenInventory().getTopInventory().getItem(slot);
                if (clickedItem != null && clickedItem.getType() == Material.SKULL_ITEM) {
                    String playerName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
                    Player targetPlayer = Bukkit.getPlayer(playerName);

                    if (targetPlayer != null) {
                        inventoryPlayer.setSelectedPlayer(targetPlayer); // Guardar jugador seleccionado
                        openEnviarDineroInventory(inventoryPlayer); // Abrir inventario de selección de dinero a enviar
                    }
                }
            }
        } else if (section.equals(InventorySection.MENU_ENVIAR_DINERO)) {
            double currentAmount = inventoryPlayer.getStoredAmount();
            if (clickType == ClickType.LEFT) {

                switch (slot) {
                    case 11: // -1000
                        inventoryPlayer.setStoredAmount(currentAmount - 1000);
                        break;
                    case 12: // -100
                        inventoryPlayer.setStoredAmount(currentAmount - 100);
                        break;
                    case 13: // -10
                        inventoryPlayer.setStoredAmount(currentAmount - 10);
                        break;
                    case 14: // -1
                        inventoryPlayer.setStoredAmount(currentAmount - 1);
                        break;
                    case 40: // +1
                        inventoryPlayer.setStoredAmount(currentAmount + 1);
                        break;
                    case 41: // +10
                        inventoryPlayer.setStoredAmount(currentAmount + 10);
                        break;
                    case 42: // +100
                        inventoryPlayer.setStoredAmount(currentAmount + 100);
                        break;
                    case 43: // +1000
                        inventoryPlayer.setStoredAmount(currentAmount + 1000);
                        break;
                    case 19: // Atras
                        openMainInventory(inventoryPlayer);
                        return;
                }

                // Actualizar la cantidad en el inventario
                openEnviarDineroInventory(inventoryPlayer);
            } else if (slot == 22 && clickType == ClickType.RIGHT && currentAmount > 0){
                double playerBalance = Os_Druks_Rp_P.getEconomy().getBalance(player);
                if (playerBalance >= currentAmount) {
                    Os_Druks_Rp_P.getEconomy().withdrawPlayer(player.getName(), currentAmount);
                    Player selectedPlayer = inventoryPlayer.getSelectedPlayer();
                    Os_Druks_Rp_P.getEconomy().depositPlayer(selectedPlayer, currentAmount);

                    player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&fEnviaste &4"+(int)currentAmount+"$ &fa&d"+selectedPlayer.getName()));
                    selectedPlayer.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+" &d "+player.getName()+"&f te envio &2"+(int)currentAmount+"$ " ));

                } else {
                    player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&4 No tienes dinero suficiente en el banco"));
                }
            }

        }
    }
}
