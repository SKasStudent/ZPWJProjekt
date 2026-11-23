package org.example.utils;

import org.example.encje.Drzewo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testy DrzewoUtils")
@Tag("lab9")
class DrzewoUtilsTest {

    private DrzewoUtils utils;
    private Drzewo dab;
    private Drzewo sosna;
    private Drzewo brzoza;

    @BeforeEach
    void setUp() {
        utils = new DrzewoUtils();

        dab = new Drzewo(1L, "Dab", 20, 40, false, true);
        sosna = new Drzewo(2L, "Sosna", 15, 30, false, false);
        brzoza = new Drzewo(3L, "Brzoza", 10, 20, true, true);
    }

    @Nested
    @DisplayName("Sprawdzanie stanu drzewa")
    class StanDrzewaTests {

        @Test
        void czyAktywneDlaAktywnegoDrzewa() {
            assertTrue(utils.czyAktywne(dab));
        }

        @Test
        void czyAktywneDlaNieaktywnegoDrzewa() {
            assertFalse(utils.czyAktywne(sosna));
        }

        @Test
        void czyAktywneDlaNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyAktywne(null)
            );
        }

        @Test
        void czyScieteDlaScietegoDrzewa() {
            assertTrue(utils.czySciete(brzoza));
        }

        @Test
        void czyScieteDlaNiescietegoDrzewa() {
            assertFalse(utils.czySciete(dab));
        }

        @Test
        void czyScieteDlaNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czySciete(null)
            );
        }
    }

    @Nested
    @DisplayName("Porownywanie wymiarow")
    class WymiaryTests {

        @Test
        void czyWyzszeNizDlaWiekszejWysokosci() {
            assertTrue(utils.czyWyzszeNiz(dab, 19));
        }

        @Test
        void czyWyzszeNizDlaRownejWysokosci() {
            assertFalse(utils.czyWyzszeNiz(dab, 20));
        }

        @Test
        void czyWyzszeNizDlaWiekszegoProgu() {
            assertFalse(utils.czyWyzszeNiz(dab, 21));
        }

        @Test
        void czyWyzszeNizDlaNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyWyzszeNiz(null, 10)
            );
        }

        @ParameterizedTest
        @ValueSource(doubles = {
                -1.0,
                -0.001,
                Double.NaN,
                Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY
        })
        void czyWyzszeNizOdrzucaNiepoprawnyProg(double prog) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyWyzszeNiz(dab, prog)
            );
        }

        @Test
        void czyGrubszeNizDlaWiekszejSrednicy() {
            assertTrue(utils.czyGrubszeNiz(dab, 39));
        }

        @Test
        void czyGrubszeNizDlaRownejSrednicy() {
            assertFalse(utils.czyGrubszeNiz(dab, 40));
        }

        @Test
        void czyGrubszeNizDlaMniejszejSrednicy() {
            assertFalse(utils.czyGrubszeNiz(dab, 41));
        }

        @Test
        void czyGrubszeNizDlaNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyGrubszeNiz(null, 20)
            );
        }

        @ParameterizedTest
        @ValueSource(doubles = {
                -1.0,
                Double.NaN,
                Double.POSITIVE_INFINITY
        })
        void czyGrubszeNizOdrzucaNiepoprawnyProg(double prog) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyGrubszeNiz(dab, prog)
            );
        }

        @Test
        void porownajWysokoscPierwszeWyzsze() {
            assertTrue(utils.porownajWysokosc(dab, sosna) > 0);
        }

        @Test
        void porownajWysokoscPierwszeNizsze() {
            assertTrue(utils.porownajWysokosc(sosna, dab) < 0);
        }

        @Test
        void porownajWysokoscRowne() {
            dab.setWysokosc(15);

            assertEquals(0, utils.porownajWysokosc(dab, sosna));
        }

        @Test
        void porownajWysokoscDlaNull() {
            assertAll(
                    () -> assertThrows(
                            IllegalArgumentException.class,
                            () -> utils.porownajWysokosc(null, dab)
                    ),
                    () -> assertThrows(
                            IllegalArgumentException.class,
                            () -> utils.porownajWysokosc(dab, null)
                    )
            );
        }

        @Test
        void roznicaWysokosciDlaRoznychDrzew() {
            assertEquals(5.0, utils.roznicaWysokosci(dab, sosna));
        }

        @Test
        void roznicaWysokosciNieZalezyOdKolejnosci() {
            assertEquals(
                    utils.roznicaWysokosci(dab, sosna),
                    utils.roznicaWysokosci(sosna, dab)
            );
        }

        @Test
        void roznicaWysokosciDlaTegoSamegoDrzewa() {
            assertEquals(0.0, utils.roznicaWysokosci(dab, dab));
        }

        @Test
        void roznicaWysokosciDlaNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.roznicaWysokosci(null, dab)
            );
        }
    }

    @Nested
    @DisplayName("Sprawdzanie gatunku")
    class GatunekTests {

        @Test
        void czyGatunekDlaPoprawnegoGatunku() {
            assertTrue(utils.czyGatunek(dab, "Dab"));
        }

        @Test
        void czyGatunekIgnorujeWielkoscLiterISpacje() {
            assertTrue(utils.czyGatunek(dab, "  dAb  "));
        }

        @Test
        void czyGatunekDlaInnegoGatunku() {
            assertFalse(utils.czyGatunek(dab, "Sosna"));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   ", "\t"})
        void czyGatunekOdrzucaPustyTekst(String gatunek) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyGatunek(dab, gatunek)
            );
        }

        @Test
        void czyGatunekDlaNullDrzewa() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyGatunek(null, "Dab")
            );
        }
    }

    @Nested
    @DisplayName("Operacje na listach drzew")
    class ListaDrzewTests {

        @Test
        void policzAktywneDlaMieszanejListy() {
            assertEquals(
                    2L,
                    utils.policzAktywne(List.of(dab, sosna, brzoza))
            );
        }

        @Test
        void policzAktywneDlaPustejListy() {
            assertEquals(0L, utils.policzAktywne(List.of()));
        }

        @Test
        void policzAktywneDlaWszystkichNieaktywnych() {
            assertEquals(0L, utils.policzAktywne(List.of(sosna)));
        }

        @Test
        void policzAktywneDlaNullListy() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.policzAktywne(null)
            );
        }

        @Test
        void policzAktywneDlaListyZNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.policzAktywne(
                            java.util.Arrays.asList(dab, null)
                    )
            );
        }

        @Test
        void sredniaWysokoscDlaKilkuDrzew() {
            assertEquals(
                    15.0,
                    utils.sredniaWysokosc(List.of(dab, sosna, brzoza))
            );
        }

        @Test
        void sredniaWysokoscDlaJednegoDrzewa() {
            assertEquals(
                    20.0,
                    utils.sredniaWysokosc(List.of(dab))
            );
        }

        @Test
        void sredniaWysokoscDlaPustejListy() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.sredniaWysokosc(List.of())
            );
        }

        @Test
        void sredniaWysokoscDlaNullListy() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.sredniaWysokosc(null)
            );
        }

        @Test
        void najwyzszeDrzewoDlaKilkuDrzew() {
            assertSame(
                    dab,
                    utils.najwyzszeDrzewo(List.of(dab, sosna, brzoza))
            );
        }

        @Test
        void najwyzszeDrzewoDlaJednegoDrzewa() {
            assertSame(
                    sosna,
                    utils.najwyzszeDrzewo(List.of(sosna))
            );
        }

        @Test
        void najwyzszeDrzewoDlaPustejListy() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.najwyzszeDrzewo(List.of())
            );
        }

        @Test
        void najwyzszeDrzewoDlaNullListy() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.najwyzszeDrzewo(null)
            );
        }

        @Test
        void najwyzszeDrzewoDlaListyZNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.najwyzszeDrzewo(
                            java.util.Arrays.asList(dab, null)
                    )
            );
        }
    }

    @TestFactory
    @DisplayName("Dynamiczne testy granicznych wysokosci")
    Stream<DynamicTest> dynamiczneTestyWysokosci() {
        double[] progi = {0, 10, 20, 30};
        boolean[] oczekiwane = {true, true, false, false};

        return java.util.stream.IntStream.range(0, progi.length)
                .mapToObj(i -> DynamicTest.dynamicTest(
                        "Prog wysokosci: " + progi[i],
                        () -> assertEquals(
                                oczekiwane[i],
                                utils.czyWyzszeNiz(dab, progi[i])
                        )
                ));
    }
}
