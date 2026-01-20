package controller.item;

import model.Item;

import java.util.List;

public interface ItemService {
    boolean addItem(Item item);
    List<Item> getAll();
    Item searchItemById(String id);
    boolean updateItem(Item item);
    boolean deleteItem(String id);
}
