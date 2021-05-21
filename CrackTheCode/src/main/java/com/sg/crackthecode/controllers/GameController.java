package com.sg.crackthecode.controllers;

import com.sg.crackthecode.data.GameDao;
import com.sg.crackthecode.models.Game;
import com.sg.crackthecode.models.Round;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class GameController {

    private static int numGames = 0;
    private final GameDao gDao;

    public GameController(GameDao gDao)
    {   this.gDao = gDao; }


   @PostMapping("/begin")
   public ResponseEntity<String> beginGame()
   {
       String result = getRandomFourDigitNum();
       Game game = new Game(numGames + 1, result);
       gDao.add(numGames + 1, game);
       numGames++;
       System.out.println("Solution for game with id: " + game.getId() + " is the following: " + game.getSolution());
       return new ResponseEntity<String>("" + game.getId(), HttpStatus.CREATED);
   }

    public String getRandomFourDigitNum()
    {
        HashSet<Integer> nums = new HashSet<Integer>();
        for(int i = 0; i <= 9; i++){    nums.add(i);}
        StringBuilder result = new StringBuilder();
        int firstNum = 0;
        while(firstNum == 0)    {
            Integer[] arrayNumbers = nums.toArray(new Integer[nums.size()]);
            Random rndm = new Random();
            firstNum = arrayNumbers[rndm.nextInt(nums.size())];
        }
        result.append(firstNum);
        nums.remove(firstNum);
        for(int i = 0; i < 3; i++)
        {
            Integer[] arrayNumbers = nums.toArray(new Integer[nums.size()]);
            Random rndm = new Random();
            int nextNum = arrayNumbers[rndm.nextInt(nums.size())];
            result.append(nextNum);
            nums.remove(nextNum);
        }
        return result.toString();
    }

    //Guess method to be implemented
    @PostMapping("/guess")
    public ResponseEntity<Round> userMakesAGuess(@RequestBody Game game)
    {
            //System.out.println("Received the json object, its id is: " + game.getId() + " & its value is " + game.getSolution());
        Game gameInPlay = gDao.findById(game.getId());
            //System.out.println("After searching for gameInPlay in hashtable: " + gameInPlay.getId());
        if(gameInPlay == null)  return null;
            //System.out.println("Game was found!");
        Round result = checkGuess(gameInPlay, game);
            //System.out.println("Returned from checkGuess method. Exact Match: " + result.getExactMatch() + " Partial Match: " + result.getPartialmatch());
        return new ResponseEntity<Round>(result, HttpStatus.OK);
    }
    public Round checkGuess(Game official, Game guess)
    {
        String offValue = official.getSolution();
        String guessValue = guess.getSolution();
        HashSet<Character> offValChars = new HashSet<>();
        Round round = new Round();

        for(int i = 0; i < offValue.length(); i++)
        {offValChars.add(offValue.charAt(i));}

        int exact = 0, partial = 0;
        for(int i = 0; i < offValue.length(); i++)
        {
            if(offValue.charAt(i) == guessValue.charAt(i))    exact++;
            else if(offValChars.contains(guessValue.charAt(i)))
            {
                partial++;
                offValChars.remove(guessValue.charAt(i));
            }
        }
        round.setExactMatch(exact);
        round.setPartialmatch(partial);
        if(exact == 4){
            round.setResult(guess.getSolution());
            official.setIsFinishedToTrue();
        }
        official.addRound(round);
        return round;
    }

    @GetMapping("/game")
    public List<Game> getGames()
    {
        Hashtable<Integer, Game> allGames = gDao.getAll();
        List<Game> result = new ArrayList<>();
        Game newGame;
        for(Integer i : allGames.keySet())
        {
            Game toAdd = allGames.get(i);
            if(toAdd.getIsFinished())
            {
                newGame = new Game(toAdd.getId(), toAdd.getSolution());
                newGame.setIsFinishedToTrue();
                result.add(newGame);
            }
            else
                result.add(new Game(toAdd.getId(), ""));
        }
        return result;
    }

    @GetMapping("/game/{gameId}")
    public Game getGame(@PathVariable int gameId)
    {
        Game searchFor = gDao.findById(gameId);
        if(searchFor == null)   return null;
        else if(searchFor.getIsFinished())  return searchFor;
        else return  new Game(gameId, "");
    }

    @GetMapping("/rounds/{gameId}")
    public List<Round> getGameRounds(@PathVariable int gameId)
    {
        Game game = gDao.findById(gameId);
        if(game == null)    return null;
        return game.getRounds();
    }

}