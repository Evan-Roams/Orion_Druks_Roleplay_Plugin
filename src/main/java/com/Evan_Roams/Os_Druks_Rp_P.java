package com.Evan_Roams;

import com.Evan_Roams.commands.MainCommand;
import com.Evan_Roams.listeners.InventoryListener;
import com.Evan_Roams.listeners.PlayerListener;
import com.Evan_Roams.managers.TabletInventoryManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Os_Druks_Rp_P extends JavaPlugin {

    public static String prefix = "&8[&c&lOrion_Druks_Rp&8] ";
    private String version = getDescription().getVersion();
    private static Economy economy = null;

    private TabletInventoryManager tabletInventoryManager;



    @Override
    public void onEnable() {

        //Registra Comandos
        registerCommands();

        //RegistraEventos
        registerEvents();

        //
        tabletInventoryManager = new TabletInventoryManager();


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

    public void registerCommands() {
        this.getCommand("roleplay").setExecutor(new MainCommand(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
    }

    public TabletInventoryManager getTabletInventoryManager() {
        return tabletInventoryManager;
    }
}
