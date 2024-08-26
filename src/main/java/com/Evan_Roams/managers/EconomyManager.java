package com.Evan_Roams.managers;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.utils.MessageUtils;
import com.Evan_Roams.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.Evan_Roams.model.InventoryPlayer;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;


import static com.Evan_Roams.managers.TabletInventoryManager.*;
import static com.Evan_Roams.utils.StringUtils.getDisplayNameForKey;


public class EconomyManager {

    private static InventoryPlayer inventoryPlayer;

    //Banco Integrado
    static BankManager bankManager;
    static DataManager dataManager;
    
    public static void multarJugador(Player player, Player selectedPlayer, int slot) {
        String selectedPlayerName = selectedPlayer.getName();

        // Cargar el archivo ValorMultas.yml
        File valorMultasFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "multas/ValorMultas.yml");
        FileConfiguration valorMultasConfig = YamlConfiguration.loadConfiguration(valorMultasFile);

        // Cargar YAML de multas para el jugador
        File playerFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "multas/" + selectedPlayerName + ".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        if (selectedPlayer != null) {
            // Leer el valor actual de la multa basada en el slot
            if (slot >= 0 && slot <= 9) {
                String multaKey = "0_" + slot;
                int currentFine = playerConfig.getInt(multaKey, 0);
                // Incrementar el valor de la multa tanto en el archivo de multas como en el registro dni
                playerConfig.set(multaKey, currentFine + 1);
                guardarMulta(multaKey, playerConfig, valorMultasConfig, playerFile, player, selectedPlayer, selectedPlayerName);

            } else if (slot >= 10 && slot <= 19) {
                String multaKey = "1_" + (slot - 10);
                int currentFine = playerConfig.getInt(multaKey, 0);
                // Incrementar el valor de la multa tanto en el archivo de multas como en el registro dni
                playerConfig.set(multaKey, currentFine + 1);
                guardarMulta(multaKey, playerConfig, valorMultasConfig, playerFile, player, selectedPlayer, selectedPlayerName);

            } else if (slot >= 20 && slot <= 29) {
                String multaKey = "2_" + (slot - 20);
                int currentFine = playerConfig.getInt(multaKey, 0);
                // Incrementar el valor de la multa tanto en el archivo de multas como en el registro dni
                playerConfig.set(multaKey, currentFine + 1);
                guardarMulta(multaKey, playerConfig, valorMultasConfig, playerFile, player, selectedPlayer, selectedPlayerName);

            } else if (slot >= 30 && slot <= 39) {
                String multaKey = "3_" + (slot - 30);
                int currentFine = playerConfig.getInt(multaKey, 0);
                // Incrementar el valor de la multa tanto en el archivo de multas como en el registro dni
                playerConfig.set(multaKey, currentFine + 1);
                guardarMulta(multaKey, playerConfig, valorMultasConfig, playerFile, player, selectedPlayer, selectedPlayerName);

            } else if (slot >= 40 && slot <= 49) {
                String multaKey = "4_" + (slot - 40);
                int currentFine = playerConfig.getInt(multaKey, 0);
                // Incrementar el valor de la multa tanto en el archivo de multas como en el registro dni
                playerConfig.set(multaKey, currentFine + 1);
                guardarMulta(multaKey, playerConfig, valorMultasConfig, playerFile, player, selectedPlayer, selectedPlayerName);

            } else {
                String multaKey = null;
            }
        } else {
            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&c No se ha seleccionado un jugador."));
        }
    }

    public static void guardarMulta(String multaKey, FileConfiguration playerConfig, FileConfiguration valorMultasConfig, File playerFile, Player player, Player selectedPlayer, String selectedPlayerName){
        try {
            // Guardar los cambios en los archivo YAML
            playerConfig.save(playerFile);
            player.sendMessage(MessageUtils.getColoredMessage("&FSe ha multado con &4" + getDisplayNameForKey(multaKey) + "&f a &d" + selectedPlayerName));
            selectedPlayer.sendMessage(MessageUtils.getColoredMessage("&2" + player.getName() + "&f Te ha multado con &4" + getDisplayNameForKey(multaKey)));

            int multaValue = valorMultasConfig.getInt(multaKey, 0);

            // Verificar y asegurar que exista el archivo de log
            File logsFolderMultas = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "logs/multas");
            File logsFolderTransacciones = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "logs/transacciones");
            DataManager.ensureLogFileExists(logsFolderMultas, "Multas");
            DataManager.ensureLogFileExists(logsFolderTransacciones, "Transacciones");

            //Enviar comision a policia
            double comisionPolicia = Os_Druks_Rp_P.getInstance().getConfig().getDouble("multas.comision_policia", 0.5);
            int comisionPoliciaMulta = (int)(multaValue*comisionPolicia);
            Os_Druks_Rp_P.getEconomy().depositPlayer(player, comisionPoliciaMulta);

            String razon = ("comisión de multaje de artículo |"+getDisplayNameForKey(multaKey)+"| ");
            dataManager.logTransacciones(player, "Banco[Multas ciudadanía]", player.getName(), razon, comisionPoliciaMulta);

            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&fHa recibido $"+comisionPoliciaMulta+" por comisión de multa aplicada"));
            dataManager.logMultas(player ,player.getName(), selectedPlayerName, getDisplayNameForKey(multaKey), multaValue, "multar");

        } catch (IOException e) {
            e.printStackTrace();
            player.sendMessage(MessageUtils.getColoredMessage("&cError al guardar la multa."));
        }
    }

    public static void pagarMulta(Player player, FileConfiguration valorMultasConfig, int slot){

        // Pago de multa
        ItemStack clickedItem = player.getOpenInventory().getTopInventory().getItem(slot);
        if (clickedItem != null && clickedItem.getType() == Material.PAPER) {
            String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
            String multaKey = itemName.substring(0,3).replace('.', '_');

            // Obtener el valor de la multa desde ValorMultas.yml
            int multaValue = valorMultasConfig.getInt(multaKey, 0);

            // Archivo YAML del jugador
            File playerFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "multas/" + player.getName() + ".yml");
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

            // Obtener la cantidad de multas del jugador
            int multaAmount = playerConfig.getInt(multaKey, 0);

            if (multaAmount > 0) {
                if (multaValue > 0) {
                    // Verificar si el jugador tiene suficiente dinero
                    double playerBalance = Os_Druks_Rp_P.getEconomy().getBalance(player);
                    if (playerBalance >= multaValue) {
                        // Deducir la multa del balance del jugador
                        Os_Druks_Rp_P.getEconomy().withdrawPlayer(player, multaValue);

                        // Actualizar el archivo YAML del jugador
                        playerConfig.set(multaKey, multaAmount - 1);
                        try {
                            playerConfig.save(playerFile);
                            player.sendMessage(MessageUtils.getColoredMessage("&aHas pagado la multa '" + multaKey + "' por $" + multaValue + "."));

                            BankManager.addToBalanceMultasCiudadania(multaValue, getDisplayNameForKey(multaKey), player); //se añade dinero al banco como si se hubeira desviado

                            openMisMultasInventory(inventoryPlayer); // Reabrir el inventario para reflejar los cambios
                        } catch (IOException e) {
                            e.printStackTrace();
                            player.sendMessage(MessageUtils.getColoredMessage("&cError al guardar el pago de la multa."));
                        }
                    } else {
                        player.sendMessage(MessageUtils.getColoredMessage("&cNo tienes "+ multaValue +"$ para pagar esta multa, añade dinero a tu cuenta bancaria"));
                    }
                } else {
                    player.sendMessage(MessageUtils.getColoredMessage("&cLa multa de este tipo tiene un valor de 0."));
                }
            } else {
                player.sendMessage(MessageUtils.getColoredMessage("&cNo tienes multas de este tipo para pagar."));
            }
        }
    }

    public static void loteria(String[] players){

    }
}
