package com.sg.crackthecode.models;

import java.util.ArrayList;

public class Game {
    private int gameId;
    private ArrayList<Round> rounds;
    private String solution;
    private boolean isFinished = false;

    public Game(int gameId, String solution)
    {
        this.gameId = gameId;
        this.solution = new String(solution);
        this.rounds = new ArrayList<Round>();
    }

    public int getId()   {   return gameId;  }
    public void setId(int id) { this.gameId = id;   }

    public String getSolution() {return this.solution;}

    public boolean getIsFinished()    {return this.isFinished;}

    public void setIsFinishedToTrue() {this.isFinished = true;}

    public void addRound(Round round)   {   rounds.add(round);  }

    public ArrayList<Round> getRounds() {   return rounds;  }
}
