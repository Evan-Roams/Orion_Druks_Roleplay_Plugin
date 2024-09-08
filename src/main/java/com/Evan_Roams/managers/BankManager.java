package com.Evan_Roams.managers;


import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.utils.MessageUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BankManager {
    private final File bankMultasCiudadania;
    private final File bankCuentaLoterias;
    private final File loteriaParticipantesFile;

    private final JavaPlugin plugin;
    static DataManager dataManager;

    public BankManager(JavaPlugin plugin) {
        this.plugin = plugin;

        // Crear la carpeta "banco" si no existe
        File bankFolder = new File(plugin.getDataFolder(), "banco");
        if (!bankFolder.exists()) {
            bankFolder.mkdirs();
        }

        // Crea "Multas_Ciudadania.yml si no existe
        this.bankMultasCiudadania = new File(bankFolder, "Multas_Ciudadania.yml");
        if (!bankMultasCiudadania.exists()) {
            createDefaultbankMultasCiudadania();
        }

        // Crea "Cuenta_Loterias.yml" si no existe
        this.bankCuentaLoterias = new File(bankFolder, "Cuentas_Loterias.yml");
        if (!bankCuentaLoterias.exists()) {
            createDefaultbankCuentaLoterias();
        }

        // Crear el archivo "Loteria_Participantes.yml" si no existe
        this.loteriaParticipantesFile = new File(bankFolder, "Loteria_Participantes.yml");
        if (!loteriaParticipantesFile.exists()) {
            createDefaultLoteriaParticipantesFile();
        }

    }

    private void createDefaultbankMultasCiudadania() {
        try {
            if (bankMultasCiudadania.createNewFile()) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(bankMultasCiudadania);
                // Inicializar el balance con 0
                config.set("Balance", 0);
                config.save(bankMultasCiudadania);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDefaultCuentasConfig(File playerFile) {
        try {
            if (playerFile.createNewFile()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
                        config.set("Cuenta_lvl1", 0);
                        config.set("Cuenta_lvl2", 0);
                        config.set("Cuenta_lvl3", 0);

                config.save(playerFile);
                plugin.getLogger().info("Created new config file for player: " + playerFile.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDefaultbankCuentaLoterias(){
        try {
            if (bankCuentaLoterias.createNewFile()) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(bankCuentaLoterias);
                // Inicializar el balance con 0
                config.set("Loteria_Base_Balance", 0);
                config.save(bankCuentaLoterias);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefaultLoteriaParticipantesFile() {
        try {
            if (loteriaParticipantesFile.createNewFile()) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(loteriaParticipantesFile);
                config.createSection("Participants");
                config.save(loteriaParticipantesFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addToBalanceMultasCiudadania(int multaValue, String articulo, Player player) {
        // Ruta multas ciudadania
        File multasCiudadaniaFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "banco/Multas_Ciudadania.yml");

        // carga config
        FileConfiguration multasCiudadaniaConfig = YamlConfiguration.loadConfiguration(multasCiudadaniaFile);

        // saca balance actual
        int currentBalance = multasCiudadaniaConfig.getInt("Balance", 0);

        // crendo nuevo balance
        int newBalance = currentBalance + multaValue;

        // Gguardando nuevo balance
        multasCiudadaniaConfig.set("Balance", newBalance);

        try {
            // guarda cambios
            multasCiudadaniaConfig.save(multasCiudadaniaFile);
            DataManager.logTransacciones(player, player.getName(), "Banco[Multas ciudadanía]",("pago de multa Artículo: |"+articulo+"|"), multaValue);
        } catch (IOException e) {
            e.printStackTrace();
            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + "&cError al actualizar el balance de Multas Ciudadanía."));
        }
    }

    public static void loteriaBaseBalanceAumentar (Player player){
        int playerBalance = (int)Os_Druks_Rp_P.getEconomy().getBalance(player);

        // Rutas loteria
        File cuentaLoteriasFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "banco/Cuentas_Loterias.yml");

        // carga config
        FileConfiguration cuentaLoteriasConfig = YamlConfiguration.loadConfiguration(cuentaLoteriasFile);

        // saca balance actual
        int currentBalance = cuentaLoteriasConfig.getInt("Loteria_Base_Balance", 0);

        //saca valor de la loteria del config
        int valorLoteriaBase = Os_Druks_Rp_P.getInstance().getConfig().getInt("loeria.loteria_base_precio", 0);

        if (playerBalance >= valorLoteriaBase){
            // crendo nuevo balance
            int newBalance = currentBalance + valorLoteriaBase;

            // Gguardando nuevo balance
            cuentaLoteriasConfig.set("Loteria_Base_Balance", newBalance);

            try {
                // guarda cambios

                cuentaLoteriasConfig.save(cuentaLoteriasFile);
                DataManager.logTransacciones(player, player.getName(), "Banco[Loteria Base]",("poner en la loteria"), valorLoteriaBase);
            } catch (IOException e) {
                e.printStackTrace();
                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + "&cError al entrar a la loteria."));
            }
        } else {
            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&4 No tienes suficiente dinero, necesitas para entrar a loteria minimo $"+valorLoteriaBase));
        }
    }

    public static void loteriaBaseDarGanador (Player player){

        // Rutas loteria
        File cuentaLoteriasFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "banco/Cuentas_Loterias.yml");

        // carga config
        FileConfiguration cuentaLoteriasConfig = YamlConfiguration.loadConfiguration(cuentaLoteriasFile);

        // saca balance actual
        int currentBalance = cuentaLoteriasConfig.getInt("Loteria_Base_Balance", 0);

        // vaciando balance
        int newBalance = 0;

        // Gguardando nuevo balance
        cuentaLoteriasConfig.set("Loteria_Base_Balance", newBalance);

        try {
            // guarda cambios
            cuentaLoteriasConfig.save(cuentaLoteriasFile);

            Os_Druks_Rp_P.getEconomy().depositPlayer(player, (double)currentBalance);
            DataManager.logTransacciones(player, "Banco[Loteria Base]", player.getName(),("ganar en la loteria"), currentBalance);
        } catch (IOException e) {
            e.printStackTrace();
            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + "&cError al completar loteria."));
        }
    }

    public void enlistarJugadorLoteria(Player player) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(loteriaParticipantesFile);

        // Verifica si el jugador ya está enlistado
        if (config.getStringList("Participants").contains(player.getUniqueId().toString())) {
            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + "&cYa estás inscrito en la lotería."));
            return;
        }

        // Agregar al jugador a la lista
        List<String> participantes = config.getStringList("Participants");
        participantes.add(player.getUniqueId().toString());
        config.set("Participants", participantes);

        try {
            config.save(loteriaParticipantesFile);
            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + "&aTe has inscrito exitosamente en la lotería."));
        } catch (IOException e) {
            e.printStackTrace();
            player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + "&cError al inscribirte en la lotería."));
        }
    }

    public void seleccionarGanadorLoteria() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(loteriaParticipantesFile);
        List<String> participants = config.getStringList("Participants");

        if (participants.isEmpty()) {
            plugin.getServer().broadcastMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + "&cNo hay participantes en la lotería."));
            return;
        }

        Random rand = new Random();
        String ganadorUUID = participants.get(rand.nextInt(participants.size()));

        Player ganador = plugin.getServer().getPlayer(UUID.fromString(ganadorUUID));
        if (ganador != null && ganador.isOnline()) {
            loteriaBaseDarGanador(ganador);
            plugin.getServer().broadcastMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + "&aEl ganador de la lotería es " + ganador.getName() + "!"));
        } else {
            plugin.getServer().broadcastMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + "&cEl ganador no está en línea."));
        }

        // Limpiar la lista de participantes después de seleccionar al ganador
        config.set("Participants", new ArrayList<>());
        try {
            config.save(loteriaParticipantesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
