package com.Evan_Roams.commands;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.model.InventoryPlayer;
import com.Evan_Roams.utils.MessageUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class TabletCommand implements CommandExecutor {

    private Economy economy;
    private Os_Druks_Rp_P plugin;

    public TabletCommand(Os_Druks_Rp_P plugin) {
        this.plugin = plugin;

        // Configura Vault Economy
        if (!setupEconomy()) {
            throw new IllegalStateException("Vault Economy no está disponible");
        }
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Os_Druks_Rp_P.getPlugin(Os_Druks_Rp_P.class).getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)){
            //Consola
            sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"Este comando no se usa desde consola"));
            return true;
        }

        //Jugador
        Player player = (Player) sender;

        // /roleplay args[0] args[1] args[2]
        if (args.length >= 1) {

            if(args[0].equalsIgnoreCase("help")){

                help(sender);

            } else {
                help(sender);
            }


        } else {
            // /miplugin sin argumento
            if (!sender.hasPermission("os_druks_rp_p.commands.tablet")){
                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"No tienes permisos para este comando"));
                return false;
            }
            plugin.getInventorysManager().openMainInventory(new InventoryPlayer(player));
        }

        return true;

    }

    public void help(CommandSender sender){
        sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f&l--------Comandos--------"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7- /tablet (abre tablet)"));
        sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&f&l--------Comandos--------"));
    }

    public void showBalance(Player player) {
        double balance = economy.getBalance(player);
        player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + "&fTu Dinero en banco es: " + ChatColor.GREEN + economy.format(balance)));
    }

}
