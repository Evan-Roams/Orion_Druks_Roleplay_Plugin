package com.Evan_Roams.managers;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;

public class DataManager {
    private final JavaPlugin plugin;
    private final File dataFolder;
    private final File multasFolder;
    private final File valorMultasFile;
    private final File dniFolder;

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



        this.valorMultasFile = new File(multasFolder, "ValorMultas.yml");
        if (!valorMultasFile.exists()) {
            createDefaultValorMultasFile();
        }

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
                for (int i = 0; i <= 2; i++) { // Ajusta el límite superior según sea necesario
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
            createDefaultDniConfig(dniFile, playerName);
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
                config.set("Multas_Por_Pagar", 0);
                config.set("Valor_en_multas", 0);
                config.set("Prestamos_por_Pagar", 0);
                config.set("Valor_en_Prestamos", 0);
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
}
