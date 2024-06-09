package fr.amu.iut.chess;


import fr.amu.iut.chess.donnée.GameStats;
import fr.amu.iut.chess.donnée.PlayerData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PlayerDataTest {



    private GameStats gamePlayed;
    private PlayerData playerData;


    @BeforeEach
    public void init(){

        playerData = new PlayerData();

        gamePlayed = new GameStats("Opponent1", "05:00", "Opponent2", "06:00", "0-1");
    }

    @Test
    public void testScore() {
        playerData.setScore(7);
        assertEquals(7,playerData.getScore());
    }

    @Test
    public void testGamesPlayes(){

        playerData.setGames(gamePlayed);
        assertEquals(true,playerData.getGames().contains(gamePlayed));
    }

    @Test
    public void testEcriture() throws IOException {

        playerData.setGames(gamePlayed);
        playerData.setGamesPlayed(1);
        playerData.setScore(10);

        playerData.writeDataToFile("testPlayerFile.json");

        String content = new String(Files.readAllBytes(Paths.get("testPlayerFile.json")));
        assertTrue(content.contains("Games Played ,1"));
        assertTrue(content.contains("Score10"));
        assertTrue(content.contains("05:00 06:00 Opponent1 Opponent2  0-1"));

    }

    @Test
    public void testReadDataFromFileException() {
        Exception exception = assertThrows(IOException.class, () -> {
            playerData.readDataFromFile("UnNomDeFichierInexistant.json"); // Verification que l'Exception est bien lancé
        });

        assertEquals("UnNomDeFichierInexistant.json (Le fichier spécifié est introuvable)", exception.getMessage());
    }


}
