package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

class MyLoader
{
    private static Scanner scanner;
    private static int numbOfNodes;
    private static int numbOfItems;

    static void load(String source, Settings settings, List<NodeCoord> nodeCoords)
    {
        try
        {
            scanner = new Scanner(new File(source));
            loadSettings(settings);
            loadNodes(nodeCoords);
            loadItems(nodeCoords);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Load ERROR");
            e.printStackTrace();
        }
    }

    private static void loadSettings(Settings settings)
    {
        settings.PROBLEM_NAME = (scanner.nextLine().split("\t"))[1];
        settings.KNAPSACK_DATA_TYPE = (scanner.nextLine().split(" "))[1];
        settings.DIMENSION = Integer.parseInt((scanner.nextLine().split("\t"))[1]);
        settings.NUMBER_OF_ITEMS = Integer.parseInt((scanner.nextLine().split("\t"))[1]);
        settings.CAPACITY_OF_KNAPSACK = Integer.parseInt((scanner.nextLine().split("\t"))[1]);
        settings.MIN_SPEED = Double.parseDouble((scanner.nextLine().split("\t"))[1]);
        settings.MAX_SPEED = Double.parseDouble((scanner.nextLine().split("\t"))[1]);
        settings.RENTING_RATIO = Double.parseDouble((scanner.nextLine().split("\t"))[1]);
        settings.EDGE_WEIGHT_TYPE = (scanner.nextLine().split("\t"))[1];

        numbOfNodes = settings.DIMENSION;
        numbOfItems = settings.NUMBER_OF_ITEMS;
        System.out.println("Load setting OK");
    }

    private static void loadNodes(List<NodeCoord> nodeCoords)
    {
        scanner.nextLine();

        for (int i = 0; i < numbOfNodes; i++)
        {
            NodeCoord nodeCoord = new NodeCoord();

            String[] row = scanner.nextLine().split("\t");
            nodeCoord.indexName = Integer.parseInt(row[0]);
            nodeCoord.x = Double.parseDouble(row[1]);
            nodeCoord.y = Double.parseDouble(row[2]);

            nodeCoords.add(nodeCoord);
        }

        System.out.println("Load nodes OK");
    }

    private static void loadItems(List<NodeCoord> nodeCoords)
    {
        scanner.nextLine();

        for (int i = 0; i < numbOfItems; i++)
        {
            Item item = new Item();

            String[] row = scanner.nextLine().split("\t");
            item.indexName = Integer.parseInt(row[0]);
            item.profit = Integer.parseInt((row[1]));
            item.weight = Integer.parseInt((row[2]));
            int indexNameOfNode = Integer.parseInt(row[3]);

            if (nodeCoords.get(indexNameOfNode - 1).indexName == indexNameOfNode)
            {
                nodeCoords.get(indexNameOfNode - 1).items.add(item);
            }

            else
            {
                System.out.println("Load items ERROR");
                System.out.println("No rule!");
            }
        }

        System.out.println("Load items OK");
    }
}