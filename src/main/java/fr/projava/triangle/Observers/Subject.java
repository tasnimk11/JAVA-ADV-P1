package fr.projava.triangle.Observers;

import java.util.ArrayList;

public class Subject {
    private static ArrayList<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    public static void notifyObservers(String message){
        for(Observer o : observers){
            o.update(message);
        }
    }


}
