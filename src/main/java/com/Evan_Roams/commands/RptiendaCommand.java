package com.Evan_Roams.commands;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.managers.InventorysManager;
import com.Evan_Roams.model.InventoryPlayer;
import com.Evan_Roams.utils.MessageUtils;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class RptiendaCommand implements CommandExecutor {

    private Os_Druks_Rp_P plugin;
    private LuckPerms luckPerms;
    private Economy economy;

    public RptiendaCommand(Os_Druks_Rp_P plugin) {
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
        Player player = null;

        // Si el comando se ejecuta por consola o NPC
        if (!(sender instanceof Player)) {
            if (args.length >= 2) {
                String target = args[1];

                // Detectar los identificadores especiales
                if (target.equalsIgnoreCase("@p")) {
                    // Obtener el jugador más cercano (en este caso, elegimos el primer jugador online)
                    player = Bukkit.getOnlinePlayers().stream().findFirst().orElse(null);
                } else {
                    // Tratar de obtener el jugador por nombre
                    player = Bukkit.getPlayer(target);
                }

                if (player == null) {
                    sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + " El jugador " + target + " no está en línea."));
                    return true;
                }
            } else {
                sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + " Debes especificar un jugador o identificador."));
                return true;
            }
        } else {
            // Si el comando lo ejecuta un jugador
            player = (Player) sender;
        }

        if (args.length >= 1) {
            // Comprobar permiso para comandos de administración
            if (!sender.hasPermission("os_druks_rp_p.admin") &&(sender instanceof Player) ) {
                sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + " No tienes permisos para este comando."));
                return false;
            }

            if (args[0].equalsIgnoreCase("dolares")) {
                if (player != null) {
                    InventorysManager.openTiendaDolaresInventory(new InventoryPlayer(player));
                    sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + " Se ha abierto la tienda de dólares para " + player.getName()));
                } else {
                    sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + " El jugador no está en línea."));
                }
            } else {
                sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + " No entiendo el comando."));
            }
        } else {
            // Comando sin argumentos
            if (!sender.hasPermission("os_druks_rp_p.commands.admin")) {
                sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + " No tienes permisos para este comando."));
                return false;
            }
        }

        return true;
    }
}
