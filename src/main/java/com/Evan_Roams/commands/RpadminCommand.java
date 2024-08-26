package com.Evan_Roams.commands;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.utils.ItemUtils;
import com.Evan_Roams.utils.MessageUtils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

public class RpadminCommand implements CommandExecutor {

    private final Os_Druks_Rp_P plugin;
    private final LuckPerms luckPerms;

    public RpadminCommand(Os_Druks_Rp_P plugin) {
        this.plugin = plugin;
        this.luckPerms = setupLuckPerms();
    }

    private LuckPerms setupLuckPerms() {
        RegisteredServiceProvider<LuckPerms> rsp = plugin.getServer().getServicesManager().getRegistration(LuckPerms.class);
        return (rsp != null) ? rsp.getProvider() : null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtils.getColoredMessage("Este comando solo puede ser usado por jugadores."));
            return true;
        }

        //comprobar permiso
        if (!sender.hasPermission("os_druks_rp_p.admin")){
            sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&fNo tienes permisos para este comando"));
            return false;
        }

        Player player = (Player) sender;
        UserManager userManager = luckPerms.getUserManager();

        if (args.length == 1 && args[0].equalsIgnoreCase("items")) {
            User user = userManager.getUser(player.getUniqueId());

            if (user != null) {
                ItemStack item1 = ItemUtils.generateLicenciaConducirItem();
                ItemStack item2 = ItemUtils.generateLicenciaAviacionItem();
                ItemStack item3 = ItemUtils.generateLicenciaPistolaItem();
                ItemStack item4 = ItemUtils.generateLicenciaEscopetaItem();
                ItemStack item5 = ItemUtils.generateLicenciaSMGItem();
                ItemStack item6 = ItemUtils.generateLicenciaAKItem();
                ItemStack item7 = ItemUtils.generateLicenciaRifleItem();
                ItemStack item8 = ItemUtils.generateLicenciaRifleAsaltoItem();

                player.getInventory().addItem(item1, item2, item3, item4, item5, item6, item7, item8);
                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&a ITEMS DE ADMIN RECIBIDOS"));

            } else {
                player.sendMessage(MessageUtils.getColoredMessage("No se pudo encontrar tus datos de usuario."));
            }
        } else {
            // /miplugin sin argumento
            player.sendMessage(MessageUtils.getColoredMessage("Uso: /rpadmin [comando]"));
        }
        return true;
    }
}
