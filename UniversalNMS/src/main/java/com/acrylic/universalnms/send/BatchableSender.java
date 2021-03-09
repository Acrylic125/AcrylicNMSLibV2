package com.acrylic.universalnms.send;

import org.bukkit.entity.Player;

public interface BatchableSender extends Sender {

    void onInitialBatchCall();

    void sendToPlayerOnBatch(Player player);

}
