package common;

import java.util.List;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;

public class Utils {
    public static void showItemList(List<Item> l){
        System.out.println("item size =" +  l.size());
        for (Item item : l) {
            System.out.println("---- Item Name: " + item.getName() + "----");
            for (Attribute attribute : item.getAttributes()) {
//                System.out.println("      Attribute");
                System.out.printf("Name: %12s      Value: %12s \n",attribute.getName(), attribute.getValue());
            }
        }
    }

}
