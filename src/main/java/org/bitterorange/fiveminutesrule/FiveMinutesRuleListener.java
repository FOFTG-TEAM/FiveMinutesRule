package org.bitterorange.fiveminutesrule;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FiveMinutesRuleListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        FiveMinutesRule.IsActive.put(e.getPlayer().getName(), false);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        FiveMinutesRule.IsActive.remove(e.getPlayer().getName());
    }
}
