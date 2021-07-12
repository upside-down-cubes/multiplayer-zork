package io.upsidedowncubes.multiplayerzork.gameLogic.item;

import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.stereotype.Component;

@Component
public class DummyConsumable implements Item, Consumable{

    @Override
    public void use() {

        MessageOutput.print("Used Dummy item on self");
    }

    @Override
    public String getName() {
        return "dummy_con";
    }

    @Override
    public int getItemID() {
        return -1;
    }
}
