package com.mcatk.medalcabinet.papi;

import com.mcatk.medalcabinet.medal.Medal;
import com.mcatk.medalcabinet.sql.SQLManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class MedalPapi extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "medalcabinet";
    }

    @Override
    public String getAuthor() {
        return "soto";
    }

    @Override
    public String getVersion() {
        return "2.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.equals("show")) {
            Medal medal = SQLManager.getInstance().getMainMedal(player.getName());
            if (medal != null) {
                return medal.toString();
            } else {
                return "";
            }
        }
        return null;
    }


}
