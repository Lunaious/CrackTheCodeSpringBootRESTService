package com.sg.crackthecode.models;

public class Round {
    private int exactMatch;
    private int partialmatch;
    private String result;

    public Round()
    {
        this.exactMatch = 0;
        this.partialmatch = 0;
        this.result = "XXXX";
    }
    public int getExactMatch()  {   return exactMatch;  }

    public int getPartialmatch()    {   return partialmatch;    }

    public void setExactMatch(int eMatch)   {this.exactMatch = eMatch;}

    public void setPartialmatch(int pMatch) {this.partialmatch = pMatch;}

    public void setResult(String result) {  this.result = result;   }
}
