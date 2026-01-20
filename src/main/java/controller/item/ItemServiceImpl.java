package controller.item;

import model.Item;

import java.util.List;

public class ItemServiceImpl implements ItemService {

    @Override
    public boolean addItem(Item item) {
        return false;
    }

    @Override
    public List<Item> getAll() {
        return List.of();
    }

    @Override
    public Item searchItemById(String id) {
        return null;
    }

    @Override
    public boolean updateItem(Item item) {
        return false;
    }

    @Override
    public boolean deleteItem(String id) {
        return false;
    }
}
