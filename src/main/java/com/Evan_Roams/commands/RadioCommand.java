package com.Evan_Roams.commands;

import com.Evan_Roams.Os_Druks_Rp_P;
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
import org.bukkit.plugin.RegisteredServiceProvider;

public class RadioCommand implements CommandExecutor {

    private final Os_Druks_Rp_P plugin;
    private final LuckPerms luckPerms;

    public RadioCommand(Os_Druks_Rp_P plugin) {
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

        // Comprobar permiso
        if (!sender.hasPermission("os_druks_rp_p.commands.policia.servicio")) {
            sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix + "&fNo tienes permisos para este comando"));
            return false;
        }

        Player player = (Player) sender;
        UserManager userManager = luckPerms.getUserManager();
        User user = userManager.getUser(player.getUniqueId());

        if (user == null) {
            player.sendMessage(MessageUtils.getColoredMessage("No se pudo encontrar tus datos de usuario."));
            return true;
        }

        if (args.length > 0) {
            // Crear el mensaje de radio con los argumentos dados
            String mensaje = String.join(" ", args);
            String mensajeFormateado = MessageUtils.getColoredMessage("&a |" + player.getName() + "| &6Radio Policial: &f" + mensaje);

            // Enviar el mensaje a todos los policías en servicio
            for (Player p : Bukkit.getOnlinePlayers()) {
                User pUser = userManager.getUser(p.getUniqueId());
                if (pUser != null && pUser.getCachedData().getPermissionData().checkPermission("policia.servicio").asBoolean()) {
                    p.sendMessage(mensajeFormateado);
                }
            }
        } else {
            player.sendMessage(MessageUtils.getColoredMessage("Uso: /radio [mensaje]"));
        }
        return true;
    }
}
