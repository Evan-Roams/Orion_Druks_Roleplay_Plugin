package com.Evan_Roams.managers;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.model.InventoryPlayer;
import com.Evan_Roams.model.InventorySection;
import com.Evan_Roams.utils.MessageUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TabletInventoryManager {

    private ArrayList<InventoryPlayer> players;

    public TabletInventoryManager() {
        this.players = new ArrayList<>();
    }

    public InventoryPlayer getInventoryPlayer(Player player) {
        for (InventoryPlayer inventoryPlayer : players) {
            if (inventoryPlayer.getPlayer().equals(player)) {
                return inventoryPlayer;
            }
        }
        return null;
    }

    public void removePLayer(Player player) {
        players.removeIf(inventoryPlayer -> inventoryPlayer.getPlayer().equals(player));
    }

    public void openMainInventory(InventoryPlayer inventoryPlayer) {

        inventoryPlayer.setSection(InventorySection.MENU_MAIN);
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getColoredMessage("&4TABLET"));
        Player player = inventoryPlayer.getPlayer();

        //relleno
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("  ");
        item.setItemMeta(meta);

        for (int i = 0; i < 54; i++) {
            inv.setItem(i, item);
        }

        //Informacion usuario
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&2INFORMACION USUARIO"));
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&7Nombre: " + player.getName()));
        lore.add(MessageUtils.getColoredMessage("&7Dinero: " + Os_Druks_Rp_P.getEconomy().getBalance(player)));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(4, item);

        //Licencias
        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&4Licencias"));
        lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&7Licencias del Usuario"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(19, item);

        //Multas
        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&4MULTAS"));
        lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&f'Mis Multas' -> &9Click Izquierdo "));
        lore.add(MessageUtils.getColoredMessage("&f    'Multar' -> &5Click Derecho"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(22, item);


        player.openInventory(inv);
        players.add(inventoryPlayer);
    }

    public void openLicenciasInventory(InventoryPlayer inventoryPlayer) {

        inventoryPlayer.setSection(InventorySection.MENU_LICENCIAS);
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getColoredMessage("&4TABLET - LICENCIAS"));
        Player player = inventoryPlayer.getPlayer();

        //relleno
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("  ");
        item.setItemMeta(meta);

        for (int i = 0; i < 54; i++) {
            inv.setItem(i, item);
        }


        //Atras
        item = new ItemStack(Material.ARROW);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&7Atras"));
        item.setItemMeta(meta);

        inv.setItem(40, item);


        player.openInventory(inv);
        players.add(inventoryPlayer);
    }

    public void openMisMultasInventory(InventoryPlayer inventoryPlayer) {

        inventoryPlayer.setSection(InventorySection.MENU_MISMULTAS);
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getColoredMessage("&4TABLET - MIS MULTAS"));
        Player player = inventoryPlayer.getPlayer();

        //relleno
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("  ");
        item.setItemMeta(meta);

        for (int i = 0; i < 54; i++) {
            inv.setItem(i, item);
        }


        //Atras
        item = new ItemStack(Material.ARROW);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&7Atras"));
        item.setItemMeta(meta);

        inv.setItem(40, item);


        player.openInventory(inv);
        players.add(inventoryPlayer);
    }

    public void openMultarInventory(InventoryPlayer inventoryPlayer) {

        inventoryPlayer.setSection(InventorySection.MENU_MULTAR);
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getColoredMessage("&4TABLET - MULTAR"));
        Player player = inventoryPlayer.getPlayer();

        //relleno
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("  ");
        item.setItemMeta(meta);

        for (int i = 0; i < 54; i++) {
            inv.setItem(i, item);
        }


        //Atras
        item = new ItemStack(Material.ARROW);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&7Atras"));
        item.setItemMeta(meta);

        inv.setItem(52, item);

        // Lista de jugadores en línea
        int slot = 0; // Empezar en el slot 10 y avanzar desde ahí
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            ItemStack playerItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            ItemMeta playerMeta = playerItem.getItemMeta();
            playerMeta.setDisplayName(MessageUtils.getColoredMessage("&a" + onlinePlayer.getName()));
            playerItem.setItemMeta(playerMeta);
            inv.setItem(slot, playerItem);
            slot++;

            // Evitar sobrecargar el inventario
            if (slot > 51) break; // Limitar el número de jugadores mostrados en el inventario
        }


        player.openInventory(inv);
        players.add(inventoryPlayer);
    }

    public void inventoryClick(InventoryPlayer inventoryPlayer, int slot, ClickType clickType) {
        Player player = inventoryPlayer.getPlayer();
        InventorySection section = inventoryPlayer.getSection();
        if (section.equals(InventorySection.MENU_MAIN)) {
            if (slot == 19) {

                openLicenciasInventory(inventoryPlayer);
            } else if (slot == 22 && clickType == ClickType.LEFT) {
                openMisMultasInventory(inventoryPlayer);
            } else if (slot == 22 && clickType == ClickType.RIGHT) {
                openMultarInventory(inventoryPlayer);
            }


        } else if (section.equals(InventorySection.MENU_LICENCIAS)) {
            if (slot == 40) {
                openMainInventory(inventoryPlayer);
            }
        } else if (section.equals(InventorySection.MENU_MISMULTAS)) {
            if (slot == 40) {
                openMainInventory(inventoryPlayer);
            }
        } else if (section.equals(InventorySection.MENU_MULTAR)) {
            if (slot == 52) {
                openMainInventory(inventoryPlayer);
            } else {
                // Multar al jugador
                ItemStack clickedItem = player.getOpenInventory().getTopInventory().getItem(slot);
                if (clickedItem != null && clickedItem.getType() == Material.SKULL_ITEM) {
                    String playerName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
                    Player targetPlayer = Bukkit.getPlayer(playerName);

                    if (targetPlayer != null) {
                        // Lógica para multar al jugador
                        player.sendMessage(MessageUtils.getColoredMessage("&aHas multado a " + playerName));
                    }
                }
            }

        }
    }
}
