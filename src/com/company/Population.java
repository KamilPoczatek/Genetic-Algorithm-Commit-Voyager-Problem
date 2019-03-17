package com.company;

import java.util.*;

class Population
{
    private List<Subject> population;

    int size ()
    {
        return population.size();
    }

    Population ()
    {
        population = new ArrayList<>();
    }

    void newRand(Settings settings, List<NodeCoord> nodeCoords)
    {
        for (int i=0; i<settings.pop_size; i++)
        {
            Subject subject = new Subject();
            subject.random(nodeCoords);
            population.add(subject);
        }
    }

    void add (Subject subject)
    {
        population.add(subject);
    }

    Subject tournamnet (List<NodeCoord> nodeCoords, Settings settings)
    {
        Collections.shuffle(population);

        Subject best = population.get(0);

        for (int  i=1; i < settings.Tour; i++)
        {
            if (population.get(i).rating(nodeCoords, settings, settings) > best.rating(nodeCoords, settings, settings))
            {
                best = population.get(i);
            }
        }

        return best;
    }

    Subject roulette (List<NodeCoord> nodeCoords, Settings settings)
    {
        double worstRaitingAdd = this.worst(nodeCoords, settings).rating(nodeCoords, settings, settings);
        worstRaitingAdd = worstRaitingAdd > 0 ? 0 : worstRaitingAdd * -1 + 0.01;
        double random = Math.random();
        double totalPrevRaiting = 0;
        double totalRaiting = 0;

        for (Subject subject : population)
        {
            totalRaiting =+ subject.rating(nodeCoords, settings, settings) + worstRaitingAdd;
        }

        for (Subject subject : population)
        {
            totalPrevRaiting =+ subject.rating(nodeCoords, settings, settings) + worstRaitingAdd;

            if (totalPrevRaiting/totalRaiting > random)
            {
                return subject;
            }
        }

        return null;
    }

    Subject best (List<NodeCoord> nodeCoords, Settings settings)
    {
        Subject best = population.get(0);

        for (int  i=1; i < population.size(); i++)
        {
            if (population.get(i).rating(nodeCoords, settings, settings) > best.rating(nodeCoords, settings, settings))
            {
                best = population.get(i);
            }
        }

        return best;
    }

    Subject worst (List<NodeCoord> nodeCoords, Settings settings)
    {
        Subject worst = population.get(0);

        for (int  i=1; i < population.size(); i++)
        {
            if (population.get(i).rating(nodeCoords, settings, settings) < worst.rating(nodeCoords, settings, settings))
            {
                worst = population.get(i);
            }
        }

        return worst;
    }

    String printLog(List<NodeCoord> nodeCoords, Settings settings)
    {
        double min = population.get(0).rating(nodeCoords, settings, settings);
        double max = population.get(0).rating(nodeCoords, settings, settings);
        double sum = population.get(0).rating(nodeCoords, settings, settings);

        for (int  i=1; i < population.size(); i++)
        {
            if (population.get(i).rating(nodeCoords, settings, settings) > max)
            {
                max = population.get(i).rating(nodeCoords, settings, settings);
            }

            if (population.get(i).rating(nodeCoords, settings, settings) < min)
            {
                min = population.get(i).rating(nodeCoords, settings, settings);
            }

            sum += population.get(i).rating(nodeCoords, settings, settings);
        }

        return max + ", " + (sum/population.size()) + ", " + min; //najlepsza_ocena, średnia_ocen, najgorsza_ocena
    }
}
