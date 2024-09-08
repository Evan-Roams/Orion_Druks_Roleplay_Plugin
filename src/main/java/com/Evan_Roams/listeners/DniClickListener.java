package com.Evan_Roams.listeners;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.model.InventoryPlayer;
import com.Evan_Roams.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DniClickListener implements Listener {

    private Os_Druks_Rp_P plugin;

    public DniClickListener(Os_Druks_Rp_P plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();

            String dniComprobacionNombre = MessageUtils.getColoredMessage("&2DNI");
            // Verifica si el ítem es un DNI
            if (item.getType() == Material.KNOWLEDGE_BOOK && item.getItemMeta().getDisplayName().equals(dniComprobacionNombre) ) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.hasLore()) {
                    List<String> lore = meta.getLore();
                    if (!lore.isEmpty()) {
                        // Extrae el nombre del jugador desde el lore
                        String playerName = lore.get(0);
                        if (!playerName.isEmpty()) {
                            player.sendMessage(item.getItemMeta().getDisplayName());
                            File playerDniFile = new File(plugin.getDataFolder(), "dni/" + playerName + ".yml");
                            FileConfiguration playerDniConfig = YamlConfiguration.loadConfiguration(playerDniFile);

                            // Crear y abrir el inventario
                            Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Información DNI - "+ playerName);

                            // Relleno
                            item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
                            meta = item.getItemMeta();
                            meta.setDisplayName("  ");
                            item.setItemMeta(meta);

                            for (int i = 0; i < 54; i++) {
                                inventory.setItem(i, item);
                            }

                            File playerFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "multas/" + playerName + ".yml");
                            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

                            File valorMultasFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "multas/ValorMultas.yml");
                            FileConfiguration valorMultasConfig = YamlConfiguration.loadConfiguration(valorMultasFile);

                            int cantidadMultas = 0;
                            int valorMultas = 0;

                            boolean licenciaAutomovilistica = playerConfig.getBoolean("Licencia_Conducir", false);
                            boolean licenciaAerea = playerConfig.getBoolean("Licencia_Aviación", false);
                            boolean licenciaPistola = playerConfig.getBoolean("Licencia_Pistola", false);
                            boolean licenciaEscopeta = playerConfig.getBoolean("Licencia_Escopeta", false);
                            boolean licenciaSMG = playerConfig.getBoolean("Licencia_SMG", false);
                            boolean licenciaAK = playerConfig.getBoolean("Licencia_AK", false);
                            boolean licenciaRifle = playerConfig.getBoolean("Licencia_Rifle", false);
                            boolean licenciaRifleAsalto = playerConfig.getBoolean("Licencia_Rifle_Asalto", false);


                            int currentFines = playerDniConfig.getInt("Multas_Por_Pagar", 0);
                            for (int i = 0; i < 2; i++) {
                                for (int j = 0; j < 9; j++) {
                                    cantidadMultas += playerConfig.getInt(i + "_" + j, 0);
                                    valorMultas += (playerConfig.getInt(i + "_" + j, 0) * valorMultasConfig.getInt(i + "_" + j, 0));
                                }
                            }

                            // Nombre Usuario
                            item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                            meta = item.getItemMeta();
                            String playerDniName = playerDniConfig.getString("Nombre", "Nombre no encontrado");
                            meta.setDisplayName(MessageUtils.getColoredMessage("&2Nombre: " + playerDniConfig.getString("Nombre", playerDniName)));
                            lore = new ArrayList<>();
                            String enBusqueda = playerDniConfig.getBoolean("Se_Busca", false) ? "Si" : "No";
                            lore.add(MessageUtils.getColoredMessage("&7¿En Busqueda y Captura?: " + enBusqueda));
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            inventory.setItem(10, item);

                            // Multas Usuario
                            item = new ItemStack(Material.PAPER);
                            meta = item.getItemMeta();
                            meta.setDisplayName(MessageUtils.getColoredMessage("&2Multas Usuario"));
                            lore = new ArrayList<>();
                            lore.add(MessageUtils.getColoredMessage("&7Multas pendientes: " + cantidadMultas));
                            lore.add(MessageUtils.getColoredMessage("&7Valor En Multas Pendientes: $" + valorMultas));
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            inventory.setItem(11, item);

                            // Licencias Usuario
                            item = new ItemStack(Material.PAPER);
                            meta = item.getItemMeta();
                            meta.setDisplayName(MessageUtils.getColoredMessage("&2Licencias Usuario"));
                            lore = new ArrayList<>();
                            lore.add(MessageUtils.getColoredMessage("&7Licencia de Conducir: " + licenciaAutomovilistica));
                            lore.add(MessageUtils.getColoredMessage("&7Licencia de Aviacion: " + licenciaAerea));
                            lore.add(MessageUtils.getColoredMessage("&7Licencia de Pistola: " + licenciaPistola));
                            lore.add(MessageUtils.getColoredMessage("&7Licencia de Escopeta: " + licenciaEscopeta));
                            lore.add(MessageUtils.getColoredMessage("&7Licencia de SMG: " + licenciaSMG));
                            lore.add(MessageUtils.getColoredMessage("&7Licencia de AK: " + licenciaAK));
                            lore.add(MessageUtils.getColoredMessage("&7Licencia de Rifle: " + licenciaRifle));
                            lore.add(MessageUtils.getColoredMessage("&7Licencia de Rifle Asalto: " + licenciaRifleAsalto));
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            inventory.setItem(12, item);

                            playerDniConfig.set("Multas_Por_Pagar", cantidadMultas);
                            playerDniConfig.set("Valor_en_multas", valorMultas);

                            // Guardar los cambios en los archivo YAML
                            try {
                                playerDniConfig.save(playerDniFile);
                            } catch (IOException e) {
                                e.printStackTrace();
                                player.sendMessage(MessageUtils.getColoredMessage("&cError al guardar el DNI."));
                            }

                            player.openInventory(inventory);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() != null && event.getClickedInventory().getTitle().startsWith(ChatColor.GREEN + "Información DNI")) {
            // Cancela todas las interacciones en el inventario del DNI
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        // Puedes realizar alguna acción adicional si es necesario
    }
}
