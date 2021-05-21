package com.sg.crackthecode.data;

import com.sg.crackthecode.models.Game;
import org.springframework.stereotype.Repository;
import java.util.Hashtable;

@Repository
public class GameInMemoryDao implements GameDao {
    private static final Hashtable<Integer,Game> games = new Hashtable<>();

    @Override
    public Game add(Integer id, Game game){
        games.put(id, game);
        return game;
    }

    @Override
    public Hashtable<Integer, Game> getAll() {   // Return to implement
        return games;
    }

    @Override
    public Game findById(int id) {  // Return to implement
        if(games.containsKey(id))   return games.get(id);
        else    return null;
    }

}
