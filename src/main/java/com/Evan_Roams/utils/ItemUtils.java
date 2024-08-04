package com.Evan_Roams.utils;

import com.Evan_Roams.Os_Druks_Rp_P;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    public static ItemStack generateDniItem(Player player){

        // Leer info del jugador
        String playerName = player.getName();
        File playerDniFile = new File(Os_Druks_Rp_P.getInstance().getDataFolder(), "dni/" + playerName + ".yml");
        FileConfiguration playerDniConfig = YamlConfiguration.loadConfiguration(playerDniFile);

        ItemStack item = new ItemStack(Material.KNOWLEDGE_BOOK);
        item.setAmount(1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&2DNI"));
        List<String> lore = new ArrayList<>();

        lore.add(MessageUtils.getColoredMessage(playerDniConfig.getString("Nombre", "No tiene nombre")));
        meta.setLore(lore);
        item.setItemMeta(meta);


        return item;
    }
}
