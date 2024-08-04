package com.Evan_Roams.commands;

import com.Evan_Roams.Os_Druks_Rp_P;
import com.Evan_Roams.utils.MessageUtils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PoliciaCommand implements CommandExecutor {

    private final Os_Druks_Rp_P plugin;
    private final LuckPerms luckPerms;

    public PoliciaCommand(Os_Druks_Rp_P plugin) {
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

        Player player = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("servicio")) {
            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            // /miplugin sin argumento
            if (!sender.hasPermission("os_druks_rp_p.commands.policia.servicio")){
                player.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&fNo tienes permisos para este comando"));
                return false;
            }

            if (user != null) {
                boolean isInService = user.getCachedData().getPermissionData().checkPermission("policia.servicio").asBoolean();
                if (isInService) {
                    removePoliciaServicio(user);
                    player.sendMessage(MessageUtils.getColoredMessage("Has salido del servicio de policía."));
                } else {
                    addPoliciaServicio(user);
                    player.sendMessage(MessageUtils.getColoredMessage("Ahora estás en servicio de policía."));
                }
            } else {
                player.sendMessage(MessageUtils.getColoredMessage("No se pudo encontrar tus datos de usuario."));
            }
        } else {
            player.sendMessage(MessageUtils.getColoredMessage("Uso: /policia servicio"));
        }
        return true;
    }

    private void addPoliciaServicio(User user) {
        Node node = Node.builder("policia.servicio").build();
        user.data().add(node);
        luckPerms.getUserManager().saveUser(user);
    }

    private void removePoliciaServicio(User user) {
        Node node = Node.builder("policia.servicio").build();
        user.data().remove(node);
        luckPerms.getUserManager().saveUser(user);
    }
}
