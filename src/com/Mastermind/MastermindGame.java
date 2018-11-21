package com.Mastermind;

public class MastermindGame extends GameMode {


    public MastermindGame() {
        super(0, 0);
    }

    @Override
    public GameMode challenger() {
        System.out.println("challenger Mastermind");
        return null;
    }

    @Override
    public GameMode defender() {
        System.out.println("défenseur Mastermind");
        return null;
    }

    @Override
    public GameMode duel() {
        System.out.println("duel Mastermind");
        return null;
    }
}
