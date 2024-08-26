package com.Evan_Roams.managers;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.model.InventoryPlayer;
import com.Evan_Roams.model.InventorySection;
import com.Evan_Roams.utils.MessageUtils;
import com.Evan_Roams.utils.TabletInventoryClickManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.Evan_Roams.utils.StringUtils.getDisplayNameForKey;

public class TabletInventoryManager {

    private static ArrayList<InventoryPlayer> players;

    //Integrando el banco
    BankManager bankManager;

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

    public static void openMainInventory(InventoryPlayer inventoryPlayer) {

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

        //Legal
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&4LEGALIDAD"));
        List<String> lore = new ArrayList<>();
        lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&f'Menu Legalidad' -> &9Click Izquierdo "));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(10, item);

        //Finanzas
        item = new ItemStack(Material.KNOWLEDGE_BOOK);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&4FINANZAS"));
        lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&f'Menu Finanzas' -> &9Click Izquierdo "));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(11, item);



        player.openInventory(inv);
        players.add(inventoryPlayer);
    }

    public static void openLegalidadInventory(InventoryPlayer inventoryPlayer) {

        inventoryPlayer.setSection(InventorySection.MENU_LEGALIDAD);
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getColoredMessage("&4TABLET - LEGALIDAD"));
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

        //DNI - Identificacion
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&2DNI - IDETIFICACION"));
        lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&7Identificacion del usuario"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(19, item);

        /// Licencias Usuario

        String playerName = player.getName();
        File playerDniFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "dni/" + playerName + ".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerDniFile);

        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&2Licencias Usuario"));
        lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&7Licencia de Conducir: " + playerConfig.getBoolean("Licencia_Conducir", false)));
        lore.add(MessageUtils.getColoredMessage("&7Licencia de Aviación: " + playerConfig.getBoolean("Licencia_Aviación", false)));
        lore.add(MessageUtils.getColoredMessage("&7Licencia de Pistola: " + playerConfig.getBoolean("Licencia_Pistola", false)));
        lore.add(MessageUtils.getColoredMessage("&7Licencia de Escopeta: " + playerConfig.getBoolean("Licencia_Escopeta", false)));
        lore.add(MessageUtils.getColoredMessage("&7Licencia de SMG: " + playerConfig.getBoolean("Licencia_SMG", false)));
        lore.add(MessageUtils.getColoredMessage("&7Licencia de AK: " + playerConfig.getBoolean("Licencia_AK", false)));
        lore.add(MessageUtils.getColoredMessage("&7Licencia de Rifle: " + playerConfig.getBoolean("Licencia_Rifle", false)));
        lore.add(MessageUtils.getColoredMessage("&7Licencia de Rifle Asalto: " + playerConfig.getBoolean("Licencia_Rifle_Asalto", false)));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(20, item);

        //Multas
        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&4MULTAS"));
        lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&f'Mis Multas' -> &9Click Izquierdo "));
        lore.add(MessageUtils.getColoredMessage("&f    'Multar' -> &5Click Derecho"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(21, item);


        player.openInventory(inv);
        players.add(inventoryPlayer);
    }

    public static void openDniInventory(InventoryPlayer inventoryPlayer) {

        inventoryPlayer.setSection(InventorySection.MENU_DNI);
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getColoredMessage("&4TABLET - DNI"));
        Player player = inventoryPlayer.getPlayer();

        //relleno
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
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

        // Leer info del jugador
        String playerName = player.getName();
        File playerDniFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "dni/" + playerName + ".yml");
        FileConfiguration playerDniConfig = YamlConfiguration.loadConfiguration(playerDniFile);

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
        List<String> lore = new ArrayList<>();
        String enBusqueda;

        if (playerDniConfig.getBoolean("Se_Busca", false)){
            enBusqueda = "Si";
        } else {
            enBusqueda = "No";
        }
        lore.add(MessageUtils.getColoredMessage("&7¿En Busqueda y Captura?: " + enBusqueda));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(10, item);

        //Multas Usuario
        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&2Multas Usuario"));
        lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&7Multas pendientes: " + CantidadMultas));
        lore.add(MessageUtils.getColoredMessage("&7Valor En Multas Pendientes: $" + ValorMultas));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(11, item);

        // Guardar los cambios en los archivo YAML
        playerDniConfig.set("Multas_Por_Pagar", CantidadMultas);
        playerDniConfig.set("Valor_en_multas", ValorMultas);

        try {
            // Guardar los cambios en los archivo YAML
            playerDniConfig.save(playerDniFile);
        } catch (IOException e) {
            e.printStackTrace();
            player.sendMessage(MessageUtils.getColoredMessage("&cError al guardar el DNI."));
        }



        player.openInventory(inv);
        players.add(inventoryPlayer);
    }

    public static void openMisMultasInventory(InventoryPlayer inventoryPlayer) {

        inventoryPlayer.setSection(InventorySection.MENU_MISMULTAS);
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getColoredMessage("&4TABLET - MIS MULTAS"));
        Player player = inventoryPlayer.getPlayer();

        // Relleno
        ItemStack fillerItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta fillerMeta = fillerItem.getItemMeta();
        fillerMeta.setDisplayName("  ");
        fillerItem.setItemMeta(fillerMeta);

        for (int i = 0; i < 54; i++) {
            inv.setItem(i, fillerItem);
        }

        // Atras
        ItemStack backItem = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backItem.getItemMeta();
        backMeta.setDisplayName(MessageUtils.getColoredMessage("&7Atras"));
        backItem.setItemMeta(backMeta);

        inv.setItem(52, backItem);

        // Leer multas del jugador
        String playerName = player.getName();
        File playerFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "multas/" + playerName + ".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        // Crear ítems de multas manualmente

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <=9 ; j++) {
                String key = i + "_" + j;
                int multaAmmount = playerConfig.getInt(key, 0);

                if (multaAmmount > 0){
                    addFineItem(inv, key, multaAmmount,playerConfig);
                }

            }
        }

        player.openInventory(inv);
        players.add(inventoryPlayer);
    }

    private static void addFineItem(Inventory inv, String key, int multaAmmount, FileConfiguration playerConfig) {
        // Cargar el archivo ValorMultas.yml
        File valorMultasFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "multas/ValorMultas.yml");
        FileConfiguration valorMultasConfig = YamlConfiguration.loadConfiguration(valorMultasFile);
        int multaValue = valorMultasConfig.getInt(key, 0);

        ItemStack fineItem = new ItemStack(Material.PAPER);
        ItemMeta fineMeta = fineItem.getItemMeta();
        String displayName = MessageUtils.getColoredMessage("&4"+ getDisplayNameForKey(key));
        fineMeta.setDisplayName(MessageUtils.getColoredMessage(displayName));
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&4 Valor Multa: $+"+multaValue));
        lore.add(MessageUtils.getColoredMessage("&f Cantidad de Multas: x"+multaAmmount));
        lore.add(MessageUtils.getColoredMessage("&f Nada -> &9Click Izquierdo "));
        lore.add(MessageUtils.getColoredMessage("&f'Pagar Multas' -> &5Click Derecho"));
        fineMeta.setLore(lore);


        fineItem.setItemMeta(fineMeta);

        // Encontrar una ranura vacía para el ítem
        for (int k = 0; k < 52; k++) { // Solo desde 0 a 51
            if (inv.getItem(k) == null || inv.getItem(k).getType() == Material.STAINED_GLASS_PANE) {
                inv.setItem(k, fineItem);
                break;
            }
        }
    }

    public static void openMultarInventory(InventoryPlayer inventoryPlayer) {

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

    public static void openSeleccionarMulta(InventoryPlayer inventoryPlayer) {
        inventoryPlayer.setSection(InventorySection.MENU_SELECCIONAR_MULTA);
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getColoredMessage("&4TABLET - SELECCIONAR MULTA"));
        Player player = inventoryPlayer.getPlayer();

        //relleno
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("  ");
        item.setItemMeta(meta);

        for (int i = 0; i < 54; i++) {
            inv.setItem(i, item);
        }

        // Atras
        item = new ItemStack(Material.ARROW);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&7Atras"));
        item.setItemMeta(meta);
        inv.setItem(52, item);

        // Multas individuales
        ItemStack multa0_0 = new ItemStack(Material.PAPER);
        ItemMeta multa0_0Meta = multa0_0.getItemMeta();
        multa0_0Meta.setDisplayName(MessageUtils.getColoredMessage("&a0_0: Falta Licencia Vehiculo"));
        multa0_0.setItemMeta(multa0_0Meta);
        inv.setItem(0, multa0_0);

        ItemStack multa0_1 = new ItemStack(Material.PAPER);
        ItemMeta multa0_1Meta = multa0_1.getItemMeta();
        multa0_1Meta.setDisplayName(MessageUtils.getColoredMessage("&a0_1: Multa por exceso de velocidad"));
        multa0_1.setItemMeta(multa0_1Meta);
        inv.setItem(1, multa0_1);

        ItemStack multa0_2 = new ItemStack(Material.PAPER);
        ItemMeta multa0_2Meta = multa0_2.getItemMeta();
        multa0_2Meta.setDisplayName(MessageUtils.getColoredMessage("&a0_2: Multa por estacionamiento prohibido"));
        multa0_2.setItemMeta(multa0_2Meta);
        inv.setItem(2, multa0_2);

        player.openInventory(inv);
        players.add(inventoryPlayer);
    }

    public static void openFinanzasInventory(InventoryPlayer inventoryPlayer) {
        inventoryPlayer.setSection(InventorySection.MENU_FINANZAS);
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getColoredMessage("&4TABLET - FINANZAS"));
        Player player = inventoryPlayer.getPlayer();

        //relleno
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("  ");
        item.setItemMeta(meta);

        for (int i = 0; i < 54; i++) {
            inv.setItem(i, item);
        }

        // Atras
        item = new ItemStack(Material.ARROW);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&7Atras"));
        item.setItemMeta(meta);
        inv.setItem(19, item);

        // Dinero en Banco
        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&7Dinero en Banco"));
        item.setItemMeta(meta);
        inv.setItem(21, item);


        // Enviar DInero
        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&7Enviar Dinero"));
        item.setItemMeta(meta);
        inv.setItem(23, item);

        // Prestamos
        player.openInventory(inv);
        players.add(inventoryPlayer);
        item = new ItemStack(Material.BOOK);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&7Prestamos"));
        item.setItemMeta(meta);
        inv.setItem(25, item);
    }

    public static void openEnviarDineroInventory(InventoryPlayer inventoryPlayer) {
        inventoryPlayer.setSection(InventorySection.MENU_ENVIAR_DINERO);
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getColoredMessage("&4TABLET - ENVIAR DINERO"));
        Player player = inventoryPlayer.getPlayer();

        // Relleno
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("  ");
        item.setItemMeta(meta);

        for (int i = 0; i < 54; i++) {
            inv.setItem(i, item);
        }

        // Botones de cantidad -1000, -100, -10, -1
        ItemStack decrease1000 = new ItemStack(Material.REDSTONE);
        ItemMeta metaDecrease1000 = decrease1000.getItemMeta();
        metaDecrease1000.setDisplayName(MessageUtils.getColoredMessage("&c-1000"));
        decrease1000.setItemMeta(metaDecrease1000);
        inv.setItem(11, decrease1000);

        ItemStack decrease100 = new ItemStack(Material.REDSTONE);
        ItemMeta metaDecrease100 = decrease100.getItemMeta();
        metaDecrease100.setDisplayName(MessageUtils.getColoredMessage("&c-100"));
        decrease100.setItemMeta(metaDecrease100);
        inv.setItem(12, decrease100);

        ItemStack decrease10 = new ItemStack(Material.REDSTONE);
        ItemMeta metaDecrease10 = decrease10.getItemMeta();
        metaDecrease10.setDisplayName(MessageUtils.getColoredMessage("&c-10"));
        decrease10.setItemMeta(metaDecrease10);
        inv.setItem(13, decrease10);

        ItemStack decrease1 = new ItemStack(Material.REDSTONE);
        ItemMeta metaDecrease1 = decrease1.getItemMeta();
        metaDecrease1.setDisplayName(MessageUtils.getColoredMessage("&c-1"));
        decrease1.setItemMeta(metaDecrease1);
        inv.setItem(14, decrease1);

        // Botones de cantidad +1, +10, +100, +1000
        ItemStack increase1 = new ItemStack(Material.EMERALD);
        ItemMeta metaIncrease1 = increase1.getItemMeta();
        metaIncrease1.setDisplayName(MessageUtils.getColoredMessage("&a+1"));
        increase1.setItemMeta(metaIncrease1);
        inv.setItem(40, increase1);

        ItemStack increase10 = new ItemStack(Material.EMERALD);
        ItemMeta metaIncrease10 = increase10.getItemMeta();
        metaIncrease10.setDisplayName(MessageUtils.getColoredMessage("&a+10"));
        increase10.setItemMeta(metaIncrease10);
        inv.setItem(41, increase10);

        ItemStack increase100 = new ItemStack(Material.EMERALD);
        ItemMeta metaIncrease100 = increase100.getItemMeta();
        metaIncrease100.setDisplayName(MessageUtils.getColoredMessage("&a+100"));
        increase100.setItemMeta(metaIncrease100);
        inv.setItem(42, increase100);

        ItemStack increase1000 = new ItemStack(Material.EMERALD);
        ItemMeta metaIncrease1000 = increase1000.getItemMeta();
        metaIncrease1000.setDisplayName(MessageUtils.getColoredMessage("&a+1000"));
        increase1000.setItemMeta(metaIncrease1000);
        inv.setItem(43, increase1000);

        // Cantidad actual
        ItemStack currentAmount = new ItemStack(Material.PAPER);
        ItemMeta metaCurrentAmount = currentAmount.getItemMeta();
        metaCurrentAmount.setDisplayName(MessageUtils.getColoredMessage("&eCantidad: " + inventoryPlayer.getStoredAmount()));
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&f 'Enviar Dinero' -> &5Click Derecho"));
        meta.setLore(lore);
        currentAmount.setItemMeta(metaCurrentAmount);
        inv.setItem(22, currentAmount);

        // Atras
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta metaBack = back.getItemMeta();
        metaBack.setDisplayName(MessageUtils.getColoredMessage("&7Atras"));
        back.setItemMeta(metaBack);
        inv.setItem(19, back);

        player.openInventory(inv);
        players.add(inventoryPlayer);
    }

    public static void openEnviarDineroSeleccionarInventory(InventoryPlayer inventoryPlayer) {

        inventoryPlayer.setSection(InventorySection.MENU_ENVIAR_DINERO_SELECCIONAR);
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getColoredMessage("&4TABLET - ENVIAR DINERO"));
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
        int slot = 0;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            ItemStack playerItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            ItemMeta playerMeta = playerItem.getItemMeta();
            playerMeta.setDisplayName(MessageUtils.getColoredMessage("&a" + onlinePlayer.getName()));
            playerItem.setItemMeta(playerMeta);
            inv.setItem(slot, playerItem);
            slot++;

            // no sobrecarga
            if (slot > 51) break; 
        }


        player.openInventory(inv);
        players.add(inventoryPlayer);
    }

    public void inventoryClick(InventoryPlayer inventoryPlayer, int slot, ClickType clickType) {

        TabletInventoryClickManager.inventoryClick(inventoryPlayer, slot, clickType);
    }
}
