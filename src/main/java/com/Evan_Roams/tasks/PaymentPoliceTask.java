package com.Evan_Roams.tasks;

import com.Evan_Roams.Os_Druks_Rp_P;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PaymentPoliceTask extends BukkitRunnable {

    private final Os_Druks_Rp_P plugin;
    private final double paymentAmount = 100.0; // Monto a pagar a cada jugador en servicio

    public PaymentPoliceTask(Os_Druks_Rp_P plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        RegisteredServiceProvider<LuckPerms> rsp = plugin.getServer().getServicesManager().getRegistration(LuckPerms.class);
        LuckPerms luckPerms = (rsp != null) ? rsp.getProvider() : null;
        if (luckPerms == null) {
            plugin.getLogger().warning("LuckPerms no está registrado.");
            return;
        }

        UserManager userManager = luckPerms.getUserManager();

        // Iterar sobre todos los jugadores en línea
        for (org.bukkit.entity.Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            User user = userManager.getUser(onlinePlayer.getUniqueId());
            if (user != null && user.getCachedData().getPermissionData().checkPermission("policia.servicio").asBoolean()) {
                // Pagamos al jugador
                Bukkit.getScheduler().runTask(plugin, () -> {
                    onlinePlayer.sendMessage("¡Has recibido " + paymentAmount + " por estar en servicio!");
                    if (plugin.getEconomy() != null) {
                        plugin.getEconomy().depositPlayer(onlinePlayer, paymentAmount);
                    } else {
                        plugin.getLogger().warning("Vault no está configurado.");
                    }
                });
            }
        }
    }
}
