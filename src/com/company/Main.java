package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Settings settings = new Settings();
        List<NodeCoord> nodeCoords = new ArrayList<>();

        settings.pop_size = 100;
        settings.gen = 100;  //ilosc generacji
        settings.Px = 0.75;  //prawdopodobienstwo krzyżowania
        settings.Pm = 0.05;  //prawdopodobieństwo mutacji
        settings.Tour = 2;  //ilosc osobników w turnieju

        //String source = System.getProperty("user.dir") +"/testdata/" + "test.TTP";
        String source =  System.getProperty("user.dir") +"/testdata/" + "trivial_0.TTP";
        //String source = System.getProperty("user.dir") +"/testdata/" + "easy_0.TTP";
        //String source = System.getProperty("user.dir") +"/testdata/" + "medium_0.TTP";
        //String source = System.getProperty("user.dir") +"/testdata/" + "hard_0.TTP";

        MyLoader.load (source, settings, nodeCoords);

        for (NodeCoord nodeCoord : nodeCoords)
        {
            nodeCoord.greedyMode(1);
        }

        Population population = new Population();
        population.newRand(settings, nodeCoords);
        Subject best = population.best(nodeCoords, settings);
        PrintWriter printWriter = new PrintWriter("logs.csv");


        for (int generation = 0; generation < settings.gen; generation++) {

            System.out.println("Generation " + (generation + 1));

            Population newPopulation = new Population();

            do {
                Subject subjectA = population.tournamnet(nodeCoords, settings);
                //Subject subjectA = population.roulette(nodeCoords, settings);
                System.out.println("Subject A " + subjectA.indexsOfNodeCoords);

                Subject subjectB = population.tournamnet(nodeCoords, settings);
                //Subject subjectB = population.roulette(nodeCoords, settings);
                System.out.println("Subject B " + subjectB.indexsOfNodeCoords);

                if (settings.Px > Math.random())
                {
                    System.out.println("Crossing YES        " + (newPopulation.size() + 1) + " / " + settings.gen);
                    Subject newSubject = new Subject();
                    newSubject.crossing(subjectA, subjectB);
                    newSubject.mut(settings);
                    newPopulation.add(newSubject);
                    System.out.println("kid " + newSubject.indexsOfNodeCoords);
                }

                else
                    System.out.println("Crossing NO");

            }
            while (settings.pop_size > newPopulation.size());

            Subject newBest = newPopulation.best(nodeCoords, settings);
            population = newPopulation;
            printWriter.println((generation + 1) + ", " + population.printLog(nodeCoords, settings));

            if (newBest.rating(nodeCoords, settings, settings) > best.rating(nodeCoords, settings, settings))
            {
                best = newBest;
            }
        }



        printWriter.close();
        System.out.println("Best:");
        System.out.println(best.indexsOfNodeCoords);
        System.out.println("Raiting " + best.rating(nodeCoords, settings, settings));
    }
}
