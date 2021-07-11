package upsidedowncubes.multiplayerzork.gameLogic.item;

public class DummyConsumable implements Item, Consumable{

    @Override
    public void use() {

        System.out.println("Used Dummy item on self");
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
