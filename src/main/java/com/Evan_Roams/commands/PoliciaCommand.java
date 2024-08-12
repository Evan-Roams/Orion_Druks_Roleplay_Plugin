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

        //comprobar permiso
        if (!sender.hasPermission("os_druks_rp_p.commands.policia.servicio")){
            sender.sendMessage(MessageUtils.getColoredMessage(Os_Druks_Rp_P.prefix+"&fNo tienes permisos para este comando"));
            return false;
        }

        Player player = (Player) sender;
        UserManager userManager = luckPerms.getUserManager();

        if (args.length == 1 && args[0].equalsIgnoreCase("servicio")) {
            User user = userManager.getUser(player.getUniqueId());

            if (user != null) {
                boolean isInService = user.getCachedData().getPermissionData().checkPermission("policia.servicio").asBoolean();
                if (isInService) {
                    removePoliciaServicio(user);
                    player.sendMessage(MessageUtils.getColoredMessage("Has salido del servicio de policía."));
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (user != null) {
                            onlinePlayer.sendMessage(MessageUtils.getColoredMessage("&d"+player.getName()+"&4 Salió de servicio de policía"));
                        }
                    }
                } else {
                    addPoliciaServicio(user);
                    player.sendMessage(MessageUtils.getColoredMessage("Ahora estás en servicio de policía."));
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (user != null && user.getCachedData().getPermissionData().checkPermission("policia.servicio").asBoolean()) {
                            onlinePlayer.sendMessage(MessageUtils.getColoredMessage("&d"+player.getName()+"&2 Entró en servicio de policía"));
                        }
                    }
                }
            } else {
                player.sendMessage(MessageUtils.getColoredMessage("No se pudo encontrar tus datos de usuario."));
            }
        } else if (args.length == 1 && (args[0].equalsIgnoreCase("qrr"))) {
            sendEmergencyMessage(player);

        } else {
            // /miplugin sin argumento
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

    private void sendEmergencyMessage(Player sender) {
        UserManager userManager = luckPerms.getUserManager();
        String senderName = sender.getName();
        // Obtener las coordenadas del jugador y redondearlas a enteros
        int x = (int) Math.round(sender.getLocation().getX());
        int y = (int) Math.round(sender.getLocation().getY());
        int z = (int) Math.round(sender.getLocation().getZ());

        String message = String.format("&4 POLICIA &5(%s) &4EN RIESGO", senderName);
        String message2 = String.format("GPS: [name:\"%s QRR\", x:%d, y:%d, z:%d]",  senderName, x, y, z);

        // Enviar mensaje a todos los jugadores en servicio de policía
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            User user = userManager.getUser(onlinePlayer.getUniqueId());
            if (user != null && user.getCachedData().getPermissionData().checkPermission("policia.servicio").asBoolean()) {
                onlinePlayer.sendMessage(MessageUtils.getColoredMessage(message));
                onlinePlayer.sendMessage(message2);
            }
        }
    }
}
