package com.Evan_Roams.managers;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.model.InventoryPlayer;
import com.Evan_Roams.model.InventorySection;
import com.Evan_Roams.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

public class TabletInventoryManager {

    private ArrayList<InventoryPlayer> players;

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


        player.openInventory(inv);
        players.add(inventoryPlayer);
    }

    public void openLegalidadInventory(InventoryPlayer inventoryPlayer) {

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

        //Licencias
        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&4LICENCIAS"));
        lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&7Licencias del Usuario"));
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
        inv.setItem(22, item);


        player.openInventory(inv);
        players.add(inventoryPlayer);
    }

    public void openDniInventory(InventoryPlayer inventoryPlayer) {

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

    private void addFineItem(Inventory inv, String key, int multaAmmount, FileConfiguration playerConfig) {
        // Cargar el archivo ValorMultas.yml
        File valorMultasFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "multas/ValorMultas.yml");
        FileConfiguration valorMultasConfig = YamlConfiguration.loadConfiguration(valorMultasFile);
        int multaValue = valorMultasConfig.getInt(key, 0);

        ItemStack fineItem = new ItemStack(Material.PAPER);
        ItemMeta fineMeta = fineItem.getItemMeta();
        String displayName = MessageUtils.getColoredMessage("&4"+getDisplayNameForKey(key));
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

    private String getDisplayNameForKey(String key) {
        switch (key) {
            case "0_0":
                return "0.0 - Falta Licencia de Auto";
            case "0_1":
                return "0.1 - Exceso de Velocidad";
            case "0_2":
                return "0.2 - Mal Estacionado";
            case "0_3":
                return "0.3 - Conducción Temerararia";
            case "0_4":
                return "0.4 - Luz Roja";
            case "0_5":
                return "0.5 - Sin Seguro";
            case "0_6":
                return "0.6 - Sin Placa";
            case "0_7":
                return "0.7 - Ruidos Excesivos";
            case "0_8":
                return "0.8 - Uso del Teléfono";
            case "0_9":
                return "0.9 - Otros";
            case "1_0":
                return "1.0 - Falta Licencia de Conducir";
            case "1_1":
                return "1.1 - Exceso de Alcohol";
            case "1_2":
                return "1.2 - Pasarse el Semáforo en Rojo";
            case "1_3":
                return "1.3 - Exceso de Carga";
            case "1_4":
                return "1.4 - Falta de Equipamiento";
            case "1_5":
                return "1.5 - Inspección Técnica";
            case "1_6":
                return "1.6 - Conducir sin Licencia";
            case "1_7":
                return "1.7 - Manipulación del Vehículo";
            case "1_8":
                return "1.8 - Sin Documentación";
            case "1_9":
                return "1.9 - Circulación en Área Restringida";
            case "2_0":
                return "2.0 - Conducir sin Seguro";
            default:
                return key;
        }
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

    public void openSeleccionarMulta(InventoryPlayer inventoryPlayer) {
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

    public void openFinanzasInventory(InventoryPlayer inventoryPlayer) {
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

    public void inventoryClick(InventoryPlayer inventoryPlayer, int slot, ClickType clickType) {

        Player player = inventoryPlayer.getPlayer();
        InventorySection section = inventoryPlayer.getSection();

        // Cargar el archivo ValorMultas.yml
        File valorMultasFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "multas/ValorMultas.yml");
        FileConfiguration valorMultasConfig = YamlConfiguration.loadConfiguration(valorMultasFile);

        if (section.equals(InventorySection.MENU_MAIN)) {
            if (slot == 10) {
                openLegalidadInventory(inventoryPlayer);
            }
        } else if (section.equals(InventorySection.MENU_LEGALIDAD)) {
            if (slot == 19) {
                openDniInventory(inventoryPlayer);
            } else if(slot == 20) {
                openLicenciasInventory(inventoryPlayer);
            } else if (slot == 22 && clickType == ClickType.LEFT) {
                openMisMultasInventory(inventoryPlayer);
            } else if (slot == 22 && clickType == ClickType.RIGHT) {
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
        } else if (section.equals(InventorySection.MENU_LICENCIAS)) {
            if (slot == 40) {
                openMainInventory(inventoryPlayer);
            }
        } else if (section.equals(InventorySection.MENU_MISMULTAS)) {
            if (slot == 52) {
                openMainInventory(inventoryPlayer);
            } else if (clickType == ClickType.RIGHT) {
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
                                    bankManager.addToBalance(multaValue); //se añade dinero al bancocomo si se hubeira desviado
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
                if (selectedPlayer != null) {
                    String selectedPlayerName = selectedPlayer.getName();

                    // Archivo YAML de multas para el jugador
                    File playerFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "multas/" + selectedPlayerName + ".yml");
                    FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

                    // Leer el valor actual de la multa basada en el slot
                    String multaKey = "0_" + slot;
                    int currentFine = playerConfig.getInt(multaKey, 0);

                    // Incrementar el valor de la multa tanto en el archivo de multas como en el registro dni
                    playerConfig.set(multaKey, currentFine + 1);




                    try {
                        // Guardar los cambios en los archivo YAML
                        playerConfig.save(playerFile);
                        player.sendMessage(MessageUtils.getColoredMessage("&F Se ha multado con &4"+getDisplayNameForKey(multaKey)+" &f a &d "+selectedPlayerName));
                        selectedPlayer.sendMessage(MessageUtils.getColoredMessage("&2"+player.getName()+"&fTe ha multado con &4"+getDisplayNameForKey(multaKey)));

                    } catch (IOException e) {
                        e.printStackTrace();
                        player.sendMessage(MessageUtils.getColoredMessage("&cError al guardar la multa."));
                    }
                } else {
                    player.sendMessage(MessageUtils.getColoredMessage("&cNo se ha seleccionado un jugador."));
                }
            }
        }
    }
}
