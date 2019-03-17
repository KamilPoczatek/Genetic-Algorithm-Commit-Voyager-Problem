package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Subject
{
    List<Integer> indexsOfNodeCoords;

    Subject()
    {
        indexsOfNodeCoords = new ArrayList<>();
    }

    private void reset()
    {
        travelTime = 0;
        knapsackWeight = 0;
        knapsackValue = 0;
    }

    void random(List<NodeCoord> nodeCoords)
    {
        indexsOfNodeCoords.clear();

        for (int i = 0; i < nodeCoords.size(); i++)
        {
            indexsOfNodeCoords.add(i);
        }

        Collections.shuffle(indexsOfNodeCoords);
        reset();
    }

    private double travelTime = 0;
    private double knapsackWeight = 0;
    private double knapsackValue = 0;
    private double rating;

    double rating (List<NodeCoord> nodeCoords, Object y, Settings settings)
    {
        if (travelTime > 0)
        {
            return rating;
        }

        for (int i=1; i<indexsOfNodeCoords.size(); i++)
        {
            this.rating(nodeCoords, y, settings, indexsOfNodeCoords.get(i-1), indexsOfNodeCoords.get(i));
        }

        this.rating(nodeCoords, y, settings,
                indexsOfNodeCoords.get(indexsOfNodeCoords.size() - 1), indexsOfNodeCoords.get(0));

        rating = knapsackValue - travelTime;
        return rating;
    }

    private void rating(List<NodeCoord> nodeCoords, Object y, Settings settings, int indexA, int indexB)
    {
        selectItem(nodeCoords.get(indexA).items, settings.CAPACITY_OF_KNAPSACK - knapsackWeight);

        double difX = nodeCoords.get(indexA).x - nodeCoords.get(indexB).x;
        double dify = nodeCoords.get(indexA).y - nodeCoords.get(indexB).y;

        travelTime += (Math.sqrt((difX * difX) + (dify * dify)) / (settings.MAX_SPEED -
                (knapsackWeight * (settings.MAX_SPEED - settings.MIN_SPEED) / settings.CAPACITY_OF_KNAPSACK )));
    }

    private void selectItem(List<Item> items, double emptySpace)
    {
        for (Item item : items)
        {
            if (item.weight <= emptySpace)
            {
                knapsackWeight += item.weight;
                knapsackValue += item.profit;
                return;
            }
        }
    }

    void crossing (Subject a, Subject b)
    {
        int crossPoint = (int) (Math.random() * (a.indexsOfNodeCoords.size() + 1));

        for (int i = 0; i < crossPoint; i++)
        {
            this.indexsOfNodeCoords.add(a.indexsOfNodeCoords.get(i));
        }

        for (int i = crossPoint; i < b.indexsOfNodeCoords.size(); i++)
        {
            this.indexsOfNodeCoords.add(b.indexsOfNodeCoords.get(i));
        }

        repair();

        reset();
    }

    //private
    private void repair()
    {
        List<Integer> noContain = new ArrayList<>();
        List<Integer> multiContain = new ArrayList<>();

        for (int i = 0; i < indexsOfNodeCoords.size(); i++)
        {
            noContain.add(i);
        }

        for (Integer integer : indexsOfNodeCoords)
        {
            if (! noContain.remove(integer))
            {
                multiContain.add(integer);
            }
        }

        Collections.shuffle(noContain);

        for (int i=0; i<indexsOfNodeCoords.size(); i++)
        {
            if (multiContain.remove(indexsOfNodeCoords.get(i)))
            {
                indexsOfNodeCoords.set(i, noContain.get(0));
                noContain.remove(0);
            }
        }


    }

    void mut (Settings settings)
    {
        for (int i=0; i < indexsOfNodeCoords.size(); i++)
        {
            if (settings.Pm > Math.random())
            {
                int los = (int) (indexsOfNodeCoords.size() * Math.random());
                int buf = indexsOfNodeCoords.get(los);
                indexsOfNodeCoords.set(los, indexsOfNodeCoords.get(i));
                indexsOfNodeCoords.set(i, buf);
            }
        }
    }
}
