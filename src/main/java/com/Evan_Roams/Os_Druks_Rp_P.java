package com.Evan_Roams;

import com.Evan_Roams.commands.*;
import com.Evan_Roams.listeners.DniClickListener;
import com.Evan_Roams.listeners.InventoryListener;
import com.Evan_Roams.listeners.LicenciasClickListener;
import com.Evan_Roams.listeners.PlayerEventListener;
import com.Evan_Roams.managers.BankManager;
import com.Evan_Roams.managers.DataManager;
import com.Evan_Roams.managers.InventorysManager;
import com.Evan_Roams.tasks.PaymentPoliceTask;
import com.Evan_Roams.utils.StringUtils;
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

    StringUtils stringUtils;

    private InventorysManager inventorysManager;



    @Override
    public void onEnable() {

        // Crear carpeta del plugin
        createConfig();
        bankManager = new BankManager(this);
        dataManager = new DataManager(this);

        //Registra Comandos
        registerCommands();

        //RegistraEventos
        registerEvents();

        // Registrar el listener
        getServer().getPluginManager().registerEvents(new PlayerEventListener(dataManager, bankManager), this);

        //Guardar instancia Plugin
        instance = this;

        //Registrar Manager
        inventorysManager = new InventorysManager();

        // Programar la tarea de pago automático
        loadAndSchedulePaymentTasks();



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

    private void createConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveDefaultConfig(); // Esto guarda el config.yml con valores predeterminados desde resources
        }
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
        this.getCommand("radio").setExecutor(new RadioCommand(this));
        this.getCommand("rpadmin").setExecutor(new RpadminCommand(this));
        this.getCommand("rptienda").setExecutor(new RptiendaCommand(this));

    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new DniClickListener(this), this);
        getServer().getPluginManager().registerEvents(new LicenciasClickListener(this), this);

    }

    public static Os_Druks_Rp_P getInstance() {
        return instance;
    }

    public InventorysManager getInventorysManager() {
        return inventorysManager;
    }

    private void loadAndSchedulePaymentTasks() {

        long interval = getConfig().getLong("payments.interval", 1800L); // En segundos

        double paymentPoliceAmount = getConfig().getDouble("payments.police.amount", 100.0);

        // Programar la tarea de pago automático
        new PaymentPoliceTask(this, paymentPoliceAmount).runTaskTimer(this, 0L, interval * 20L); // Convertir a ticks
    }

    private void loteriaTask(){
        long interval = 3600L; //una hora en segundos

        

    }

}
