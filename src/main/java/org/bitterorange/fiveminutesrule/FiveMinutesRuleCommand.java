package org.bitterorange.fiveminutesrule;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FiveMinutesRuleCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, final String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("只有玩家能够执行此命令！");
            return true;
        }
        if (args.length > 0)
        {
            return false;
        }
        Player player = (Player) sender;
        if (FiveMinutesRule.IsActive.get(player.getName()))
        {
            FiveMinutesRule.IsActive.replace(player.getName(), false);
            player.sendMessage("FMR模式已关闭。");
        }
        else
        {
            FiveMinutesRule.IsActive.replace(player.getName(), true);
            player.sendMessage("FMR模式已开启。");
        }
        return true;
    }
}
