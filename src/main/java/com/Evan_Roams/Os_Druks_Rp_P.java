package com.Evan_Roams;

import com.Evan_Roams.commands.DniCommand;
import com.Evan_Roams.commands.MainCommand;
import com.Evan_Roams.commands.PoliciaCommand;
import com.Evan_Roams.commands.TabletCommand;
import com.Evan_Roams.listeners.DniClickListener;
import com.Evan_Roams.listeners.InventoryListener;
import com.Evan_Roams.listeners.PlayerEventListener;
import com.Evan_Roams.managers.BankManager;
import com.Evan_Roams.managers.DataManager;
import com.Evan_Roams.managers.TabletInventoryManager;
import com.Evan_Roams.tasks.PaymentPoliceTask;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Os_Druks_Rp_P extends JavaPlugin {

    public static String prefix = "&8[&c&lOrion_Druks_Rp&8] ";
    private String version = getDescription().getVersion();
    private static Economy economy = null;
    private BankManager bankManager;
    private DataManager dataManager;
    private static Os_Druks_Rp_P instance; // Instancia estática

    private TabletInventoryManager tabletInventoryManager;



    @Override
    public void onEnable() {

        // Crear carpeta del plugin
        bankManager = new BankManager(this);
        dataManager = new DataManager(this);


        //Registra Comandos
        registerCommands();

        //RegistraEventos
        registerEvents();

        // Registrar el listener
        getServer().getPluginManager().registerEvents(new PlayerEventListener(dataManager), this);

        //Guardar instancia Plugin
        instance = this;

        //
        tabletInventoryManager = new TabletInventoryManager();

        // Programar la tarea de pago automático
        new PaymentPoliceTask(this).runTaskTimer(this, 0L, 20L * 60L /* * 30L */); // Ejecutar cada 30 minutos



        // Configura Vault Economy
        setupEconomy();

        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&eEnabled! &fVersion: " + version));
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&eGracias por usar un plugin de Evan_Roams"));

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&eDisabled! &fVersion: " + version));
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&eGracias por usar un plugin de Evan_Roams"));

        // Limpiar la instancia del plugin
        instance = null;

    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            economy = rsp.getProvider();
        }
    }

    public static Economy getEconomy() {
        return economy;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void registerCommands() {
        this.getCommand("roleplay").setExecutor(new MainCommand(this));
        this.getCommand("tablet").setExecutor(new TabletCommand(this));
        this.getCommand("dni").setExecutor(new DniCommand(this));
        this.getCommand("policia").setExecutor(new PoliciaCommand(this));

    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new DniClickListener(this), this);

    }

    public static Os_Druks_Rp_P getInstance() {
        return instance;
    }

    public TabletInventoryManager getTabletInventoryManager() {
        return tabletInventoryManager;
    }
}