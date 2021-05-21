package com.sg.crackthecode.data;

import com.sg.crackthecode.models.Game;
import java.util.Hashtable;

public interface GameDao {
    Game add(Integer id, Game game);    //  Adding game to inMemoryDao

    Hashtable<Integer, Game> getAll();   // Retrieve all games

    Game findById(int id);  // Retrieve a specific game

}
