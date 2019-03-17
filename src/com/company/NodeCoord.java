package com.company;

import java.util.*;

class NodeCoord
{
    int indexName;
    double x;
    double y;
    List<Item> items;

    NodeCoord()
    {
        items = new ArrayList<>();
    }

    void greedyMode(int mode)
    {
        System.out.print("Node coord " + indexName + " - ");

        switch (mode)
        {
            case 1:
                System.out.println("Zawsze bierz najdroższy dostępny przedmiot");
                items.sort(new ComparatorMode1());
                break;
            case 2:
                System.out.println("Zawsze bierz najlżejszy przedmiot");
                items.sort(new ComparatorMode2());
                break;
            case 3:
                System.out.println("Zawsze bierz przedmiot, który ma najlepszy stosunek wartość/waga.");
                items.sort(new ComparatorMode3());
                break;
            default:
                System.out.println("ERROR mode");
                break;
        }

        for (Item item : items)
        {
            System.out.print(item.indexName + ", ");
        }

        System.out.println();
    }


    private class ComparatorMode1 implements Comparator<Item>
    {
        @Override
        public int compare(Item o1, Item o2)
        {
            if (o1.profit < o2.profit)
                return 1;
            else return -1;
        }
    }

    private class ComparatorMode2 implements Comparator<Item>
    {
        @Override
        public int compare(Item o1, Item o2)
        {
            if (o1.weight > o2.weight)
                return 1;
            else return -1;
        }
    }

    private class ComparatorMode3 implements Comparator<Item>
    {
        @Override
        public int compare(Item o1, Item o2)
        {
            if ((o1.profit / o1.weight) < (o2.profit / o2.weight))
                return 1;
            else return -1;
        }
    }
}