package com.Evan_Roams.managers;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.configuration.file.YamlConfiguration;

public class DataManager {
    private final JavaPlugin plugin;
    private final File dataFolder;
    private final File multasFolder;
    private final File valorMultasFile;
    private final File dniFolder;
    private final File valorLicenciasFile;
    private final File logsFolder;
    private final File multasLogFolder;
    private final File transaccionesLogFolder;

    public DataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.dataFolder = plugin.getDataFolder();
        this.multasFolder = new File(dataFolder, "multas");
        this.dniFolder = new File(dataFolder, "dni");

        if (!multasFolder.exists()) {
            multasFolder.mkdirs(); // Crear la carpeta de multas si no existe
        }

        if (!dniFolder.exists()) {
            dniFolder.mkdirs(); // Crear la carpeta de DNI si no existe
        }

        // Crear carpetas de logs
        this.logsFolder = new File(dataFolder, "logs");
        this.multasLogFolder = new File(logsFolder, "multas");
        this.transaccionesLogFolder = new File(logsFolder, "transacciones");

        if (!logsFolder.exists()) {
            logsFolder.mkdirs(); // Crear la carpeta de logs si no existe
        }

        if (!multasLogFolder.exists()) {
            multasLogFolder.mkdirs(); // Crear la carpeta de logs de multas si no existe
        }

        if (!transaccionesLogFolder.exists()) {
            transaccionesLogFolder.mkdirs(); // Crear la carpeta de logs de transacciones si no existe
        }



        this.valorMultasFile = new File(multasFolder, "ValorMultas.yml");
        if (!valorMultasFile.exists()) {
            createDefaultValorMultasFile();
        }

        this.valorLicenciasFile = new File(dniFolder, "ValorLicencias.yml");
        if (!valorLicenciasFile.exists()) {
            createDefaultValorLicenciasFile();
        }

        // Comprobar y crear archivos de log si no existen
        ensureLogFileExists(multasLogFolder, "Multas");
        ensureLogFileExists(transaccionesLogFolder, "Transacciones");

    }

    public File getPlayerFile(String playerName) {
        return new File(multasFolder, playerName + ".yml");
    }

    public YamlConfiguration getPlayerConfig(String playerName) {
        File playerFile = getPlayerFile(playerName);
        if (!playerFile.exists()) {
            createDefaultConfig(playerFile);
        }
        return YamlConfiguration.loadConfiguration(playerFile);
    }

    private void createDefaultConfig(File playerFile) {
        try {
            if (playerFile.createNewFile()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
                // Inicializar con valores por defecto usando el formato 0_0, 0_1, ..., 0_9, 1_0, ..., 1_9, etc.
                for (int i = 0; i <= 2; i++) { // Ajusta el límite superior según sea necesario
                    for (int j = 0; j <= 9; j++) {
                        config.set(i + "_" + j, 0);
                    }
                }
                config.save(playerFile);
                plugin.getLogger().info("Created new config file for player: " + playerFile.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefaultValorMultasFile() {
        try {
            if (valorMultasFile.createNewFile()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(valorMultasFile);
                // Inicializar con valores predeterminados
                config.set("0_0", 100);
                for (int i = 0; i <= 4; i++) { // Ajusta el límite superior según sea necesario
                    for (int j = 0; j <= 9; j++) {
                        if (i == 0 && j == 0) continue; // Ya se ha establecido 0_0
                        config.set(i + "_" + j, 0);
                    }
                }
                config.save(valorMultasFile);
                plugin.getLogger().info("Created default ValorMultas.yml with default values.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getValorMultasConfig() {
        return YamlConfiguration.loadConfiguration(valorMultasFile);
    }

    public void savePlayerConfig(String playerName, YamlConfiguration config) {
        try {
            config.save(getPlayerFile(playerName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getDniFile(String playerName) {
        return new File(dniFolder, playerName + ".yml");
    }

    public YamlConfiguration getDniConfig(String playerName) {
        File dniFile = getDniFile(playerName);
        if (!dniFile.exists()) {
             String playerNameTag = playerName.toString();
            createDefaultDniConfig(dniFile, playerNameTag);
        }
        return YamlConfiguration.loadConfiguration(dniFile);
    }

    private void createDefaultDniConfig(File dniFile, String playerName) {
        try {
            if (dniFile.createNewFile()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(dniFile);
                String playerDniName = playerName;

                // Inicializar con valores por defecto
                config.set("Nombre", playerDniName);
                config.set("Se_Busca", false);
                config.set("Cuenta_Bancaria_Lvl_1", true);
                config.set("Cuenta_Bancaria_Lvl_2", false);
                config.set("Cuenta_Bancaria_Lvl_3", false);
                config.set("Dinero_Banco", 0);
                config.set("Multas_Por_Pagar", 0);
                config.set("Valor_en_multas", 0);
                config.set("Prestamos_por_Pagar", 0);
                config.set("Valor_en_Prestamos", 0);
                config.set("Licencia_Conducir", false);
                config.set("Licencia_Aviación", false);
                config.set("Licencia_Pistola", false);
                config.set("Licencia_Escopeta", false);
                config.set("Licencia_SMG", false);
                config.set("Licencia_AK", false);
                config.set("Licencia_Rifle", false);
                config.set("Licencia_Rifle_Asalto", false);

                config.save(dniFile);
                plugin.getLogger().info("Created default DNI config file for player: " + dniFile.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDniConfig(String playerName, YamlConfiguration config) {
        try {
            config.save(getDniFile(playerName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefaultValorLicenciasFile() {
        try {
            if (valorMultasFile.createNewFile()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(valorMultasFile);
                // Inicializar con valores predeterminados
                config.set("Licencia_Conducir", 0);
                config.set("Licencia_Aviación", 0);
                config.set("Licencia_Pistola", 0);
                config.set("Licencia_Pistola", 0);
                config.set("Licencia_Escopeta", 0);
                config.set("Licencia_SMG", 0);
                config.set("Licencia_AK", 0);
                config.set("Licencia_Rifle", 0);
                config.set("Licencia_Rifle_Asalto", 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para asegurarse de que exista un archivo de log para el día actual
    public static void ensureLogFileExists(File folder, String logType) {
        File logFile = getLogFile(folder, logType);
        if (!logFile.exists()) {
            writeToFile(logFile, "Log creado: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
    }

    // Método para obtener el archivo de log con la fecha actual
    static File getLogFile(File folder, String logType) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return new File(folder, logType + "_" + date + ".log");
    }

    // Método para escribir mensajes en un archivo
    private static void writeToFile(File file, String message) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logMultas(Player sender, String emisor, String receptor, String articulo, int valorMulta, String multar_pagar) {
        String hora = new SimpleDateFormat("HH-mm-ss").format(new Date());

        // Obtener la carpeta de logs de multas
        File logsFolderMultas = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "logs/multas");

        // Asegurarse de que el archivo de log existe
        DataManager.ensureLogFileExists(logsFolderMultas, "Multas");

        // Obtener el archivo de log para el día actual
        File logFile = DataManager.getLogFile(logsFolderMultas, "Multas");

        // Crear el mensaje de log
        String logMessage;
        if (multar_pagar.equalsIgnoreCase("multar")){
            logMessage = ("|"+hora+"|: "+emisor+" multó a "+receptor+" con el Artículo |"+articulo+"| por un valor de $"+valorMulta);

        } else if (multar_pagar.equalsIgnoreCase("pagar")) {
            logMessage = ("|"+hora+"|: "+emisor+" pagó su multa del Artículo |"+articulo+"| por un valor de $"+valorMulta);

        } else {
            logMessage = ("|"+hora+"|: "+emisor+" valor inconcluso (preguntar) "+receptor+" con el Artículo |"+articulo+"| por un valor de $"+valorMulta);
        }

        // Escribir el mensaje en el archivo de log
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(logMessage + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&fERROR AL AÑADIR MULTA"));
        }
    }

    public static void logTransacciones(Player sender, String emisor, String receptor, String razon, int valor) {
        String hora = new SimpleDateFormat("HH-mm-ss").format(new Date());

        // Obtener la carpeta de logs de multas
        File logsFolderTransacciones = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "logs/transacciones");

        // Asegurarse de que el archivo de log existe
        DataManager.ensureLogFileExists(logsFolderTransacciones, "Transacciones");

        // Obtener el archivo de log para el día actual
        File logFile = DataManager.getLogFile(logsFolderTransacciones, "Transacciones");

        // Crear el mensaje de log
        String logMessage = ("|"+hora+"|: "+emisor+" envió $"+valor+" a "+receptor+" por "+razon);


        // Escribir el mensaje en el archivo de log
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(logMessage + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&fERROR AL AÑADIR LOG"));
        }
    }



}
