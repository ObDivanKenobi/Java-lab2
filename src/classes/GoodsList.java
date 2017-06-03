package classes;

import classes.DataModels.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Created by Илья on 21.05.2017.
 */
public class GoodsList {
    public static int StartCapacity = 20;
    private static Comparator<Goods> defaultComparator = (Goods o1, Goods o2) -> {
            if (o1.getID() > o2.getID())
                return 1;

            if (o1.getID() == o2.getID())
                return 0;

            return -1;
        };

    private ArrayList<Goods> goods;

    public GoodsList() {
        goods = new ArrayList<>(StartCapacity);
    }

    public GoodsList(int start_capacity) {
        goods = new ArrayList<>(start_capacity);
    }

    public GoodsList(GoodsList list) {
        goods = new ArrayList<>(list.goods);
    }

    public GoodsList(ArrayList<Goods> list) {
        goods = new ArrayList<>(list);
    }

    public int count() { return goods.size(); }

    public Goods getItem(int index) throws IndexOutOfBoundsException {
        return goods.get(index);
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

    public GoodsList sort(Comparator<Goods> comparator) {
        ArrayList<Goods> tmp = new ArrayList<>(goods);
        Collections.sort(tmp, comparator);
        return new GoodsList(tmp);
    }

    public GoodsList sort() {
        ArrayList<Goods> tmp = new ArrayList<>(goods);
        Collections.sort(tmp, defaultComparator);
        return new GoodsList(tmp);
    }

    public GoodsList filter(Predicate<Goods> condition) {
        ArrayList<Goods> tmp = new ArrayList<>(goods);
        tmp.removeIf(condition);
        return new GoodsList(tmp);
    }
}
