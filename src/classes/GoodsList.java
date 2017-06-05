package classes;

import classes.DataModels.Goods;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

/**
 * Created by Илья on 21.05.2017.
 */
public class GoodsList {
    public static int StartCapacity = 20;

    private ArrayList<Goods> goods;

    public GoodsList() {
        goods = new ArrayList<>(StartCapacity);
    }

    public GoodsList(int start_capacity) {
        goods = new ArrayList<>(start_capacity);
    }

    public GoodsList(GoodsList list) {
        goods = list.goods;
    }

    private GoodsList(ArrayList<Goods> list) {
        goods = new ArrayList<>(list);
    }

    public int count() { return goods.size(); }

    public Goods getItem(int index) throws IndexOutOfBoundsException {
        return goods.get(index);
    }

    public int getMaxID() {
        return goods.stream().mapToInt(g -> g.getID()).max().orElse(0);
    }

    public void setItem(int index, Goods item) throws IndexOutOfBoundsException {
        goods.set(index, item);
    }

    public void removeItem(int index) throws IndexOutOfBoundsException {
        goods.remove(index);
    }

    public void removeItem(Goods item) {
        goods.remove(item);
    }

    public void addItem(Goods item) {goods.add(item); }

    public int indexOf(Goods g) {
        return goods.indexOf(g);
    }

    public boolean existsId(int id) {
        return goods.stream().anyMatch(item -> item.getID() == id);
    }

    public GoodsList sort(Comparator<Goods> comparator) {
        ArrayList<Goods> tmp = new ArrayList<>(goods);
        Collections.sort(tmp, comparator);
        return new GoodsList(tmp);
    }

    public GoodsList filter(Predicate<Goods> condition) {
        List<Goods> tmp = goods.stream()
                               .filter(condition)
                               .collect(Collectors.toList());
        ArrayList<Goods> filtered = (ArrayList<Goods>) tmp;
        return new GoodsList(filtered);
    }
}
