package io.github.smartsteves.vaultTest;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Created by JUN on 2016-02-07.
 */
public class VaultTest extends JavaPlugin {
    public static Economy economy;

    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().log(Level.WARNING, "Vault플러그인이 필요합니다");
            getServer().getPluginManager().disablePlugin(this);
        }
        economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("economyTest") && sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("You have " + economy.getBalance(player) + "money");
            economy.withdrawPlayer(player, 100D); //플레이어한테서 100원 뻇기
            economy.depositPlayer(player, 1000D); //플레이어에게 1000원 주기
        }
        return true;
    }
}
