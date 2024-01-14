package hu.nye.szakdolgozat.web.controller;

import org.springframework.stereotype.Component;

@Component
public class Move {
    private int from;
    private int to;

    public Move(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public Move() {
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
