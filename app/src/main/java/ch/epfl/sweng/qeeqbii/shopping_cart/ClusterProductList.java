package ch.epfl.sweng.qeeqbii.shopping_cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.qeeqbii.open_food.ClusterType;

/**
 * Created by gmollard on 01.12.17.
 *
 */
public class ClusterProductList {

    private Boolean do_mock_barcode_checking = Boolean.FALSE;

    private Map<ClusterType, Boolean> is_checked_item = new HashMap<>();

    private List<ClusterType> m_items = new ArrayList<>();

    public ClusterProductList(){
        //To avoid an empty list
        //addItemToCart(new Product("Please Click on + to add an item", "0 mg", "Stuff", "cool Nutrients"));
    }

    public ClusterProductList(List<ClusterType> items) {

        m_items = items;
        for (ClusterType cluster : m_items)
        {
            is_checked_item.put(cluster,false);
        }
    }

    public void addItemToList(ClusterType cluster) {
        m_items.add(cluster);
        is_checked_item.put(cluster,false);
    }

    public void addSpecificItemInList(int index, ClusterType cluster) {
        m_items.add(index, cluster);
    }

    public List<ClusterType> getItems() {
        return m_items;
    }

    public ClusterType getSpecificItemInList(int index) {
        return m_items.get(index);
    }

    public Boolean isCheckedItem(ClusterType cluster)
    {
        return is_checked_item.get(cluster);
    }

    public void deleteSingleItem() {
        Iterator<ClusterType> element = m_items.iterator();
        //for (Product element : m_items) {
        while (element.hasNext()) {
            ClusterType cluster = element.next();
            if (is_checked_item.get(cluster))
            {
                element.remove();
            }
        }
    }

    public void clear()
    {
        m_items.clear();
        is_checked_item.clear();
    }

    public void checkItem(ClusterType cluster)
    {
        is_checked_item.put(cluster,true);
    }

    public int getClusterPosition(ClusterType cluster)
    {
        return m_items.indexOf(cluster);
    }

    public void checkItemFromBarcode(String barcode) {
        if (do_mock_barcode_checking)
            checkItemFromBarcodeMock(barcode);
        else {
            // TODO: write a real method
            // instead of this mocked version
            // use OpenFood for map (barcode -> product)
            // and Clustering for for map (product -> category)
            checkItemFromBarcodeMock(barcode);
        }
    }

    // mocked version of checking items from barcode
    // includes a constant mapping barcode -> category
    // instead of using OpenFood (barcode -> product)
    // and Clustering (product -> category)
    // see checkItemFromBarcode()
    private void checkItemFromBarcodeMock(String barcode) {
        /*for (Product prod : m_items) {
            if ((prod.getName().equals("Pizza") && barcode.equals("4001724819905")) ||
<<<<<<< HEAD:app/src/main/java/ch/epfl/sweng/qeeqbii/shopping_cart/ClusterProductList.java
                (prod.getName().equals("Wine") && barcode.equals("8437002948153")) ||
                (prod.getName().equals("Cheese") && barcode.equals("2108726006400")))
            prod.setChecked(Boolean.TRUE);
        }*/
    }

    public void enableMockBarcodeChecking() {
        do_mock_barcode_checking = Boolean.TRUE;
    }
}