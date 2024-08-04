package com.Evan_Roams.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class BankManager {
    private final File bankFile;
    private FileConfiguration bankConfig;

    public BankManager(JavaPlugin plugin) {
        // Crear la carpeta "banco" si no existe
        File bankFolder = new File(plugin.getDataFolder(), "banco");
        if (!bankFolder.exists()) {
            bankFolder.mkdirs();
        }

        // Crear el archivo "Multas_Ciudadania.yml" si no existe
        this.bankFile = new File(bankFolder, "Multas_Ciudadania.yml");
        if (!bankFile.exists()) {
            createDefaultBankFile();
        }
        this.bankConfig = YamlConfiguration.loadConfiguration(bankFile);
    }

    private void createDefaultBankFile() {
        try {
            if (bankFile.createNewFile()) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(bankFile);
                // Inicializar el balance con 0
                config.set("Balance", 0);
                config.save(bankFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getBalance() {
        return bankConfig.getDouble("Balance", 0);
    }

    public void setBalance(double amount) {
        bankConfig.set("Balance", amount);
        save();
    }

    public void addToBalance(double amount) {
        double currentBalance = getBalance();
        setBalance(currentBalance + amount);
    }

    public void subtractFromBalance(double amount) {
        double currentBalance = getBalance();
        setBalance(currentBalance - amount);
    }

    private void save() {
        try {
            bankConfig.save(bankFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
