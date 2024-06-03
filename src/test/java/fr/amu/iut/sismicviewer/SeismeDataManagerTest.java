package fr.amu.iut.sismicviewer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;

public class SeismeDataManagerTest {

    private static Seisme seisme1;
    private static Seisme seisme2;
    private static Seisme seisme3;
    private static SeismeDataManager seismeDataManager;

    @BeforeAll
    public static void setUp(){
        seismeDataManager = new SeismeDataManager();
        seisme1 = new Seisme();
        seisme2 = new Seisme();
        seisme3 = new Seisme();
    }

    @Test
    public void getAnneeFromToTest() {
        seisme1.setAnnee(2000);
        seisme2.setAnnee(1950);
        seisme3.setAnnee(1975);
        ArrayList<Seisme> data = new ArrayList<Seisme>(Arrays.asList(seisme1, seisme2, seisme3));
        ArrayList<Seisme> arrayTriee = seismeDataManager.getAnneeFromTo(data, 1955, 2000);
        assertEquals(2, arrayTriee.size());

        arrayTriee.clear();

        arrayTriee = seismeDataManager.getAnneeFromTo(data, 1940, 1960);
        assertEquals(1, arrayTriee.size());

        arrayTriee.clear();

        arrayTriee = seismeDataManager.getAnneeFromTo(data, 2020, 2090);
        assertEquals(0, arrayTriee.size());
    }

    @Test
    public void getSeismeMaxTest(){
        seisme1.setMagnitude(9);
        seisme2.setMagnitude(5);
        seisme3.setMagnitude(7);

        ArrayList listeSeisme = new ArrayList<Seisme>(Arrays.asList(seisme1, seisme2, seisme3));

        assertEquals(seisme1, seismeDataManager.getSeismeMax(listeSeisme));

        seisme1.setMagnitude(0);

        assertEquals(seisme3, seismeDataManager.getSeismeMax(listeSeisme));
    }

    @Test
    public void getSeismeMinTest(){
        seisme1.setMagnitude(6);
        seisme2.setMagnitude(5);
        seisme3.setMagnitude(4);

        ArrayList<Seisme> listeSeismes = new ArrayList<>(Arrays.asList(seisme1, seisme2, seisme3));
        assertEquals(seisme3, seismeDataManager.getSeismeMin(listeSeismes));

        seisme1.setMagnitude(2);
        seisme2.setMagnitude(-5);

        assertEquals(seisme1, seismeDataManager.getSeismeMin(listeSeismes));
    }

    @Test
    public void getNombreSeismeAvecMagnitudeConnueTest(){
        seisme1.setMagnitude(6);
        seisme2.setMagnitude(9);

        ArrayList<Seisme> listeSeisme = new ArrayList<>(Arrays.asList(seisme1, seisme2, seisme3));
        assertEquals(2, seismeDataManager.getNombreSeismeAvecMagnitudeConnue(listeSeisme));

        seisme3.setMagnitude(4);
        assertEquals(3, seismeDataManager.getNombreSeismeAvecMagnitudeConnue(listeSeisme));
    }

    @Test
    public void magnitudeMoyenneTest(){
        seisme1.setMagnitude(4);
        seisme2.setMagnitude(8);
        seisme3.setMagnitude(6);

        ArrayList<Seisme> listeSeisme = new ArrayList<>(Arrays.asList(seisme1, seisme2, seisme3));
        assertEquals(6, seismeDataManager.getMagnitudeMoyenne(listeSeisme));

        seisme1.setMagnitude(7);

        assertEquals(7, seismeDataManager.getMagnitudeMoyenne(listeSeisme));
    }





}
