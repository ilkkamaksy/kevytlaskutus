package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kartinSaldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void latausKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(10);
        
        assertEquals(20, kortti.saldo());
    }
    
    @Test
    public void saldoVaheneeJosRahaaTarpeeksi() {
        kortti.otaRahaa(10);
        assertEquals(0, kortti.saldo());
    }
    
    @Test
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(20);
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosSaldoRiittaa() {
        assertTrue(kortti.otaRahaa(10));
    }
    
    @Test
    public void otaRahaaPalauttaaFalseJosSaldoEiRiita() {
        assertFalse(kortti.otaRahaa(20));
    }
    
    @Test
    public void saldoTulosteOnOikein() {
        kortti.lataaRahaa(10);
        
        assertEquals("saldo: 0.20", kortti.toString());
    }
}
