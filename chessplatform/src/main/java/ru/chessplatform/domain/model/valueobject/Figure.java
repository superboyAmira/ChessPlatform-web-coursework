package ru.chessplatform.domain.model.valueobject;

import javax.persistence.Embeddable;


@Embeddable
public class Figure {
    private String name;
    private int costPerPoints;

    public Figure() {}

    public Figure(String name, int costPerPoints) {
        this.name = name;
        this.costPerPoints = costPerPoints;
    }

    public String getName() {
        return name;
    }

    public int getCostPerPoints() {
        return costPerPoints;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCostPerPoints(int costPerPoints) {
        this.costPerPoints = costPerPoints;
    }

    // TODO: переопределяем для сравнения стоимости фигур -> каждая фигура стоит определеное кол-во пешек

    @Override
    public String toString() {
        return name + " (" + costPerPoints + " points)";
    }
}