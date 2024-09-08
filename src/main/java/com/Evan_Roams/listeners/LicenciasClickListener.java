package com.Evan_Roams.listeners;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.model.InventoryPlayer;
import com.Evan_Roams.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LicenciasClickListener implements Listener {

    private Os_Druks_Rp_P plugin;

    public LicenciasClickListener(Os_Druks_Rp_P plugin) {
        this.plugin = plugin;
    }



    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();

            // Verifica si el ítem es una Licencia
            if (item.getType() == Material.PAPER) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.hasLore()) {
                    List<String> lore = meta.getLore();
                    String Lore = meta.getLore().get(0);

                    // Archivo YAML del jugador
                    File playerFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "dni/" + player.getName() + ".yml");
                    FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

                    if (Lore.equals("CONDUCCION")){
                        // Obtener el valor de la licencia desde ValorMultas.yml
                        boolean licenciaValue = playerConfig.getBoolean("Licencia_Conducir", false);

                        if (licenciaValue){
                            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Ya posees esta Licencia"));
                        } else {

                            playerConfig.set("Licencia_Conducir", true);
                            try {
                                playerConfig.save(playerFile);
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Adquiriste una nueva Licencia"));
                                plugin.getInventorysManager().openLegalidadInventory(new InventoryPlayer(player));

                            } catch (IOException e) {
                                e.printStackTrace();
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&c Error al guardar el la licencia."));
                            }
                        }
                    }

                    else if (Lore.equals("CONDUCCION AEREA")){
                        // Obtener el valor de la licencia desde ValorMultas.yml
                        boolean licenciaValue = playerConfig.getBoolean("Licencia_Aviación", false);

                        if (licenciaValue){
                            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Ya posees esta Licencia"));
                        } else {

                            playerConfig.set("Licencia_Aviación", true);
                            try {
                                playerConfig.save(playerFile);
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Adquiriste una nueva Licencia"));
                                plugin.getInventorysManager().openLegalidadInventory(new InventoryPlayer(player));

                            } catch (IOException e) {
                                e.printStackTrace();
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&c Error al guardar el la licencia."));
                            }
                        }
                    }

                    else if (Lore.equals("PISTOLA")){
                        // Obtener el valor de la licencia desde ValorMultas.yml
                        boolean licenciaValue = playerConfig.getBoolean("Licencia_Pistola", false);

                        if (licenciaValue){
                            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Ya posees esta Licencia"));
                        } else {

                            playerConfig.set("Licencia_Pistola", true);
                            try {
                                playerConfig.save(playerFile);
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Adquiriste una nueva Licencia"));
                                plugin.getInventorysManager().openLegalidadInventory(new InventoryPlayer(player));

                            } catch (IOException e) {
                                e.printStackTrace();
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&c Error al guardar el la licencia."));
                            }
                        }
                    }

                    else if (Lore.equals("ESCOPETA")){
                        // Obtener el valor de la licencia desde ValorMultas.yml
                        boolean licenciaValue = playerConfig.getBoolean("Licencia_Escopeta", false);

                        if (licenciaValue){
                            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Ya posees esta Licencia"));
                        } else {

                            playerConfig.set("Licencia_Escopeta", true);
                            try {
                                playerConfig.save(playerFile);
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Adquiriste una nueva Licencia"));
                                plugin.getInventorysManager().openLegalidadInventory(new InventoryPlayer(player));

                            } catch (IOException e) {
                                e.printStackTrace();
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&c Error al guardar el la licencia."));
                            }
                        }
                    }

                    else if (Lore.equals("SMG")){
                        // Obtener el valor de la licencia desde ValorMultas.yml
                        boolean licenciaValue = playerConfig.getBoolean("Licencia_SMG", false);

                        if (licenciaValue){
                            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Ya posees esta Licencia"));
                        } else {

                            playerConfig.set("Licencia_SMG", true);
                            try {
                                playerConfig.save(playerFile);
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Adquiriste una nueva Licencia"));
                                plugin.getInventorysManager().openLegalidadInventory(new InventoryPlayer(player));

                            } catch (IOException e) {
                                e.printStackTrace();
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&c Error al guardar el la licencia."));
                            }
                        }
                    }

                    else if (Lore.equals("AK")){
                        // Obtener el valor de la licencia desde ValorMultas.yml
                        boolean licenciaValue = playerConfig.getBoolean("Licencia_AK", false);

                        if (licenciaValue){
                            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Ya posees esta Licencia"));
                        } else {

                            playerConfig.set("Licencia_AK", true);
                            try {
                                playerConfig.save(playerFile);
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Adquiriste una nueva Licencia"));
                                plugin.getInventorysManager().openLegalidadInventory(new InventoryPlayer(player));

                            } catch (IOException e) {
                                e.printStackTrace();
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&c Error al guardar el la licencia."));
                            }
                        }
                    }

                    else if (Lore.equals("RIFLE")){
                        // Obtener el valor de la licencia desde ValorMultas.yml
                        boolean licenciaValue = playerConfig.getBoolean("Licencia_Rifle", false);

                        if (licenciaValue){
                            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Ya posees esta Licencia"));
                        } else {

                            playerConfig.set("Licencia_Rifle", true);
                            try {
                                playerConfig.save(playerFile);
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Adquiriste una nueva Licencia"));
                                plugin.getInventorysManager().openLegalidadInventory(new InventoryPlayer(player));

                            } catch (IOException e) {
                                e.printStackTrace();
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&c Error al guardar el la licencia."));
                            }
                        }
                    }

                    else if (Lore.equals("RIFLE DE ASALTO")){
                        // Obtener el valor de la licencia desde ValorMultas.yml
                        boolean licenciaValue = playerConfig.getBoolean("Licencia_Rifle_Asalto", false);

                        if (licenciaValue){
                            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Ya posees esta Licencia"));
                        } else {

                            playerConfig.set("Licencia_Rifle_Asalto", true);
                            try {
                                playerConfig.save(playerFile);
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f Adquiriste una nueva Licencia"));
                                plugin.getInventorysManager().openLegalidadInventory(new InventoryPlayer(player));

                            } catch (IOException e) {
                                e.printStackTrace();
                                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&c Error al guardar el la licencia."));
                            }
                        }
                    }
                }
            }
        }
    }
}
