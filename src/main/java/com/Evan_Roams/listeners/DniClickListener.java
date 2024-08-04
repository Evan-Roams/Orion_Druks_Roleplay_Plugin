package com.Evan_Roams.listeners;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.utils.ItemUtils;
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

            // Verifica si el ítem es un DNI
            if (item.getType() == Material.KNOWLEDGE_BOOK) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.hasLore()) {
                    List<String> lore = meta.getLore();
                    if (!lore.isEmpty()) {
                        // Extrae el nombre del jugador desde el lore
                        String playerName = lore.get(0);
                        if (!playerName.isEmpty()) {
                            File playerDniFile = new File(plugin.getDataFolder(), "dni/" + playerName + ".yml");
                            FileConfiguration playerDniConfig = YamlConfiguration.loadConfiguration(playerDniFile);

                            // Crear y abrir el inventario
                            Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Información DNI - "+ playerName);

                            //relleno
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
                            FileConfiguration ValorMultasConfig = YamlConfiguration.loadConfiguration(valorMultasFile);

                            int CantidadMultas = 0;
                            int ValorMultas = 0;

                            int currentFines = playerDniConfig.getInt("Multas_Por_Pagar", 0);
                            for (int i = 0; i < 2; i++) {
                                for (int j = 0; j < 9; j++) {
                                    CantidadMultas = CantidadMultas + playerConfig.getInt(i+"_"+j, 0);
                                    ValorMultas = ValorMultas + (playerConfig.getInt(i+"_"+j, 0) * ValorMultasConfig.getInt(i+"_"+j, 0) ) ;

                                }
                            }



                            //Nombre Usuario
                            item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                            meta = item.getItemMeta();
                            String playerDniName = playerDniConfig.getString("Nombre", "Nombre no encontrado"); // Valor predeterminado si "Nombre" no existe
                            meta.setDisplayName(MessageUtils.getColoredMessage("&2Nombre: "+playerDniConfig.getString("Nombre", playerDniName)));
                            lore = new ArrayList<>();
                            String enBusqueda;

                            if (playerDniConfig.getBoolean("Se_Busca", false)){
                                enBusqueda = "Si";
                            } else {
                                enBusqueda = "No";
                            }
                            lore.add(MessageUtils.getColoredMessage("&7¿En Busqueda y Captura?: " + enBusqueda));
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            inventory.setItem(10, item);

                            //Multas Usuario
                            item = new ItemStack(Material.PAPER);
                            meta = item.getItemMeta();
                            meta.setDisplayName(MessageUtils.getColoredMessage("&2Multas Usuario"));
                            lore = new ArrayList<>();
                            lore.add(MessageUtils.getColoredMessage("&7Multas pendientes: " + CantidadMultas));
                            lore.add(MessageUtils.getColoredMessage("&7Valor En Multas Pendientes: $" + ValorMultas));
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            inventory.setItem(11, item);

                            playerDniConfig.set("Multas_Por_Pagar", CantidadMultas);
                            playerDniConfig.set("Valor_en_multas", ValorMultas);


                            // Guardar los cambios en los archivo YAML
                            try {
                                // Guardar los cambios en los archivo YAML
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

    private ItemStack createItem(Material material, String name, String value) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + name);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + value);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
