package main.model.notes;


import java.util.ArrayList;
import java.util.Collection;


public class Subject {

    private Collection<Observer> observers;


    public Subject() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(Object o) {
        for (var observer : observers) {
            observer.update(o);
        }
    }

}
