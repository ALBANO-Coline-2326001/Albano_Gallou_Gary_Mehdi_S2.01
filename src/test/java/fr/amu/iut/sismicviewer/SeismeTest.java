package fr.amu.iut.sismicviewer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeismeTest {

    @Test
    public void cantHaveNegativeMagnitude(){
        Seisme seisme = new Seisme();
        seisme.setMagnitude(9);
        seisme.setMagnitude(-1);
        assertEquals(9, seisme.getMagnitude());
    }

    @Test
    public void cantHaveMagnitudeMoreThan10(){
        Seisme seisme = new Seisme();
        seisme.setMagnitude(6);
        seisme.setMagnitude(11);
        assertEquals(6, seisme.getMagnitude());
    }
}
