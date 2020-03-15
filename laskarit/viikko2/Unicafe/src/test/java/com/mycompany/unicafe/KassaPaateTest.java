package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ilkka
 */
public class KassaPaateTest {
    
    Kassapaate kassapaate;
    Maksukortti maksukortti;
    
    private static int kassassaAluksiRahaa;
    private final static int edullisenLounaanHinta = 240;
    private final static int maukkaanLounaanHinta = 400;
    
    public KassaPaateTest() {
    }
    
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        kassassaAluksiRahaa = kassapaate.kassassaRahaa();
        maksukortti = new Maksukortti(1000);
    }
    
    @Test
    public void konstruktoriAsettaaKassanVaratOikein() {
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void konstruktoriAsettaaLounaidenMyyntiMaaratOikein() {
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void lounaanKateisOstossaKassaKasvaaLounaanHinnalla() {
        int kateinen = 500;
        int kassassa = kassapaate.kassassaRahaa() + edullisenLounaanHinta;
        kassapaate.syoEdullisesti(kateinen);
        assertEquals(kassassa, kassapaate.kassassaRahaa());
        
        kassapaate.syoMaukkaasti(kateinen);
        kassassa += maukkaanLounaanHinta;
        assertEquals(kassassa, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void lounaanKateisOstossaVaihtoRahatOvatOikein() {
        int kateinen = 500;
        kassapaate.syoEdullisesti(kateinen);
        assertEquals(kateinen - edullisenLounaanHinta, kassapaate.syoEdullisesti(kateinen));
        assertEquals(kateinen - maukkaanLounaanHinta, kassapaate.syoMaukkaasti(kateinen));
    }
    
    @Test
    public void lounaanKateisOstonEpaonnistuessaKassanRahamaaraEiKasva() {
        int kassassa = kassapaate.kassassaRahaa();
        kassapaate.syoEdullisesti(200);
        assertEquals(kassassa, kassapaate.kassassaRahaa());
        kassapaate.syoMaukkaasti(300);
        assertEquals(kassassa, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void lounaanKateisOstonEpaonnistuessaPalautetaanKaikkiRahat() {
        assertEquals(200, kassapaate.syoEdullisesti(200));
        assertEquals(300, kassapaate.syoMaukkaasti(300));
    }
    
    @Test
    public void lounaidenMaaraKassassaKasvaaOnnistuneenKateisOstonJalkeen() {
        kassapaate.syoEdullisesti(240);
        kassapaate.syoMaukkaasti(400);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void lounaidenMaaraKateisOstonEpaonnistuessaEiMuutu() {
        kassapaate.syoEdullisesti(200);
        kassapaate.syoMaukkaasti(300);
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanLounaanKateisOstossaKassaKasvaaEdullisenLounaanHinnalla() {
        int kateinen = 500;
        int kassassa = 100000 + edullisenLounaanHinta;
        kassapaate.syoEdullisesti(500);
        assertEquals(kassassa, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void kassapaatePalauttaaMaksunJalkeenTrueKunKortinSaldoRiittaa() {
        assertTrue(kassapaate.syoEdullisesti(maksukortti));
        assertTrue(kassapaate.syoMaukkaasti(maksukortti));
    }

    @Test
    public void kassapaatePalauttaaMaksunJalkeenFalseKunKortinSaldoEiRiita() {
        maksukortti.otaRahaa(800);
        assertFalse(kassapaate.syoEdullisesti(maksukortti));
        assertFalse(kassapaate.syoMaukkaasti(maksukortti));
    }
    
    @Test
    public void kassapaateVeloittaaKortiltaOikeanSummanKunKortinSaldoRiittaa() {
        int kortillaRahaa = maksukortti.saldo();
        kassapaate.syoEdullisesti(maksukortti);
        kortillaRahaa -= edullisenLounaanHinta;
                
        assertEquals(kortillaRahaa, maksukortti.saldo());
    
        kassapaate.syoMaukkaasti(maksukortti);
        kortillaRahaa -= maukkaanLounaanHinta;
        assertEquals(kortillaRahaa, maksukortti.saldo());
    }
    
    @Test
    public void kassapaateEiTeeVeloitustaKunKortinSaldoEiRiita() {
        maksukortti.otaRahaa(800);
        int kortillaRahaa = maksukortti.saldo();
        
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(kortillaRahaa, maksukortti.saldo());
    
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(kortillaRahaa, maksukortti.saldo());
    }
    
    @Test
    public void onnistuneenKorttimaksunJalkeenMyytyjenLounaidenMaaraKasvaaKassassa() {
        kassapaate.syoEdullisesti(maksukortti);
        kassapaate.syoMaukkaasti(maksukortti);
        
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void epaonnistuneenKorttimaksunJalkeenMyytyjenLounaidenMaaraEiMuutu() {
        maksukortti.otaRahaa(800);
        kassapaate.syoEdullisesti(maksukortti);
        kassapaate.syoMaukkaasti(maksukortti);
        
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassanRahaMaaraEiKasvaOnnistuneenKorttiostonJalkeen() {
        kassapaate.syoEdullisesti(maksukortti);
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(kassassaAluksiRahaa, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void kortinSaldoKasvaaLadatunSummanVerran() {
        int kortillaRahaa = maksukortti.saldo();
        kassapaate.lataaRahaaKortille(maksukortti, 1000);
        assertEquals(kortillaRahaa+1000, maksukortti.saldo());
    }
    
    @Test
    public void kassanRahamaaraKasvaaKorttilatauksenSummanVerran() {
        kassapaate.lataaRahaaKortille(maksukortti, 1000);
        assertEquals(kassassaAluksiRahaa + 1000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void negatiivisenSummanLatausKortilleEiMuutaKortinSaldoa() {
        int kortillaRahaa = maksukortti.saldo();
        kassapaate.lataaRahaaKortille(maksukortti, -1000);
        assertEquals(kortillaRahaa, maksukortti.saldo());
    }
    
    @Test 
    public void negatiivisenSummanLatausKortilleEiMuutaKassanRahaMaaraa() {
        kassapaate.lataaRahaaKortille(maksukortti, -1000);
        assertEquals(kassassaAluksiRahaa, kassapaate.kassassaRahaa());
    }
}
