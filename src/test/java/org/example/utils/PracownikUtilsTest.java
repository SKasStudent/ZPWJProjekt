package org.example.utils;

import org.example.encje.Pracownik;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testy PracownikUtils")
@Tag("lab9")
class PracownikUtilsTest {

    private PracownikUtils utils;
    private Pracownik jan;
    private Pracownik anna;
    private Pracownik piotr;

    @BeforeEach
    void setUp() {
        utils = new PracownikUtils();

        jan = new Pracownik(
                1L, "Jan", "Programista", 5000, true
        );
        anna = new Pracownik(
                2L, "Anna", "Tester", 7000, false
        );
        piotr = new Pracownik(
                3L, "Piotr", "Programista", 3000, true
        );
    }

    @Nested
    @DisplayName("Aktywnosc i pensja")
    class AktywnoscIPensjaTests {

        @Test
        void czyAktywnyDlaAktywnegoPracownika() {
            assertTrue(utils.czyAktywny(jan));
        }

        @Test
        void czyAktywnyDlaNieaktywnegoPracownika() {
            assertFalse(utils.czyAktywny(anna));
        }

        @Test
        void czyAktywnyDlaNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyAktywny(null)
            );
        }

        @Test
        void czyWysokaPensjaPowyzejProgu() {
            assertTrue(utils.czyWysokaPensja(jan, 4999));
        }

        @Test
        void czyWysokaPensjaNaGranicy() {
            assertFalse(utils.czyWysokaPensja(jan, 5000));
        }

        @Test
        void czyWysokaPensjaPonizejProgu() {
            assertFalse(utils.czyWysokaPensja(jan, 5001));
        }

        @Test
        void czyWysokaPensjaDlaNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyWysokaPensja(null, 1000)
            );
        }

        @ParameterizedTest
        @ValueSource(doubles = {
                -1.0,
                -0.01,
                Double.NaN,
                Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY
        })
        void czyWysokaPensjaOdrzucaBlednyProg(double prog) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyWysokaPensja(jan, prog)
            );
        }

        @Test
        void czyPensjaCoNajmniejNaGranicy() {
            assertTrue(utils.czyPensjaCoNajmniej(jan, 5000));
        }

        @Test
        void czyPensjaCoNajmniejPonizejMinimum() {
            assertFalse(utils.czyPensjaCoNajmniej(jan, 5001));
        }

        @Test
        void czyPensjaCoNajmniejPowyzejMinimum() {
            assertTrue(utils.czyPensjaCoNajmniej(jan, 4999));
        }

        @Test
        void czyPensjaCoNajmniejDlaZera() {
            assertTrue(utils.czyPensjaCoNajmniej(jan, 0));
        }

        @Test
        void czyPensjaCoNajmniejDlaNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyPensjaCoNajmniej(null, 1000)
            );
        }

        @ParameterizedTest
        @ValueSource(doubles = {
                -1.0,
                Double.NaN,
                Double.POSITIVE_INFINITY
        })
        void czyPensjaCoNajmniejOdrzucaBledneMinimum(
                double minimum
        ) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyPensjaCoNajmniej(jan, minimum)
            );
        }
    }

    @Nested
    @DisplayName("Stanowisko")
    class StanowiskoTests {

        @Test
        void czyStanowiskoDlaPoprawnejNazwy() {
            assertTrue(utils.czyStanowisko(jan, "Programista"));
        }

        @Test
        void czyStanowiskoIgnorujeWielkoscLiterISpacje() {
            assertTrue(utils.czyStanowisko(
                    jan, "  pRoGrAmIsTa  "
            ));
        }

        @Test
        void czyStanowiskoDlaInnejNazwy() {
            assertFalse(utils.czyStanowisko(jan, "Tester"));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   ", "\t"})
        void czyStanowiskoOdrzucaPusteDane(String stanowisko) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyStanowisko(jan, stanowisko)
            );
        }

        @Test
        void czyStanowiskoDlaNullPracownika() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.czyStanowisko(null, "Programista")
            );
        }
    }

    @Nested
    @DisplayName("Porownywanie pensji i podwyzki")
    class ObliczeniaTests {

        @Test
        void porownajPensjePierwszyZarabiaWiecej() {
            assertTrue(utils.porownajPensje(anna, jan) > 0);
        }

        @Test
        void porownajPensjePierwszyZarabiaMniej() {
            assertTrue(utils.porownajPensje(jan, anna) < 0);
        }

        @Test
        void porownajPensjeRowne() {
            piotr.setPensja(5000);

            assertEquals(0, utils.porownajPensje(jan, piotr));
        }

        @Test
        void porownajPensjeDlaNull() {
            assertAll(
                    () -> assertThrows(
                            IllegalArgumentException.class,
                            () -> utils.porownajPensje(null, jan)
                    ),
                    () -> assertThrows(
                            IllegalArgumentException.class,
                            () -> utils.porownajPensje(jan, null)
                    )
            );
        }

        @Test
        void roznicaPensjiDlaRoznychPensji() {
            assertEquals(2000, utils.roznicaPensji(jan, anna));
        }

        @Test
        void roznicaPensjiNieZalezyOdKolejnosci() {
            assertEquals(
                    utils.roznicaPensji(jan, anna),
                    utils.roznicaPensji(anna, jan)
            );
        }

        @Test
        void roznicaPensjiDlaTejSamejOsoby() {
            assertEquals(0, utils.roznicaPensji(jan, jan));
        }

        @Test
        void roznicaPensjiDlaNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.roznicaPensji(jan, null)
            );
        }

        @Test
        void pensjaPoPodwyzceDziesiecProcent() {
            assertEquals(
                    5500,
                    utils.pensjaPoPodwyzce(jan, 10),
                    0.000001
            );
        }

        @Test
        void pensjaPoPodwyzceZeroProcent() {
            assertEquals(
                    5000,
                    utils.pensjaPoPodwyzce(jan, 0)
            );
        }

        @Test
        void pensjaPoPodwyzceStoProcent() {
            assertEquals(
                    10000,
                    utils.pensjaPoPodwyzce(jan, 100)
            );
        }

        @Test
        void pensjaPoPodwyzceNieZmieniaPracownika() {
            utils.pensjaPoPodwyzce(jan, 20);

            assertEquals(5000, jan.getPensja());
        }

        @Test
        void pensjaPoPodwyzceDlaNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.pensjaPoPodwyzce(null, 10)
            );
        }

        @ParameterizedTest
        @ValueSource(doubles = {
                -1.0,
                -10.0,
                Double.NaN,
                Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY
        })
        void pensjaPoPodwyzceOdrzucaBlednyProcent(
                double procent
        ) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.pensjaPoPodwyzce(jan, procent)
            );
        }
    }

    @Nested
    @DisplayName("Operacje na listach pracownikow")
    class ListaPracownikowTests {

        @Test
        void policzAktywnychDlaMieszanejListy() {
            assertEquals(
                    2L,
                    utils.policzAktywnych(List.of(jan, anna, piotr))
            );
        }

        @Test
        void policzAktywnychDlaPustejListy() {
            assertEquals(0L, utils.policzAktywnych(List.of()));
        }

        @Test
        void policzAktywnychDlaNieaktywnych() {
            assertEquals(
                    0L,
                    utils.policzAktywnych(List.of(anna))
            );
        }

        @Test
        void policzAktywnychDlaNullListy() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.policzAktywnych(null)
            );
        }

        @Test
        void policzAktywnychDlaListyZNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.policzAktywnych(
                            Arrays.asList(jan, null)
                    )
            );
        }

        @Test
        void sredniaPensjaDlaKilkuPracownikow() {
            assertEquals(
                    5000,
                    utils.sredniaPensja(List.of(jan, anna, piotr))
            );
        }

        @Test
        void sredniaPensjaDlaJednegoPracownika() {
            assertEquals(
                    7000,
                    utils.sredniaPensja(List.of(anna))
            );
        }

        @Test
        void sredniaPensjaDlaPustejListy() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.sredniaPensja(List.of())
            );
        }

        @Test
        void sredniaPensjaDlaNullListy() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.sredniaPensja(null)
            );
        }

        @Test
        void sredniaPensjaDlaListyZNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.sredniaPensja(
                            Arrays.asList(jan, null)
                    )
            );
        }

        @Test
        void najlepiejZarabiajacyDlaKilkuPracownikow() {
            assertSame(
                    anna,
                    utils.najlepiejZarabiajacy(
                            List.of(jan, anna, piotr)
                    )
            );
        }

        @Test
        void najlepiejZarabiajacyDlaJednegoPracownika() {
            assertSame(
                    jan,
                    utils.najlepiejZarabiajacy(List.of(jan))
            );
        }

        @Test
        void najlepiejZarabiajacyDlaPustejListy() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.najlepiejZarabiajacy(List.of())
            );
        }

        @Test
        void najlepiejZarabiajacyDlaNullListy() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.najlepiejZarabiajacy(null)
            );
        }

        @Test
        void najlepiejZarabiajacyDlaListyZNull() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> utils.najlepiejZarabiajacy(
                            Arrays.asList(jan, null)
                    )
            );
        }

        @Test
        void najlepiejZarabiajacyDlaRemisu() {
            piotr.setPensja(7000);

            Pracownik wynik = utils.najlepiejZarabiajacy(
                    List.of(jan, anna, piotr)
            );

            assertNotNull(wynik);
            assertEquals(7000, wynik.getPensja());
        }
    }

    @TestFactory
    @DisplayName("Dynamiczne testy podwyzek")
    Stream<DynamicTest> dynamiczneTestyPodwyzek() {
        double[] procenty = {0, 10, 20, 100};
        double[] wyniki = {5000, 5500, 6000, 10000};

        return java.util.stream.IntStream.range(
                0, procenty.length
        ).mapToObj(i -> DynamicTest.dynamicTest(
                "Podwyzka: " + procenty[i] + "%",
                () -> assertEquals(
                        wyniki[i],
                        utils.pensjaPoPodwyzce(jan, procenty[i]),
                        0.000001
                )
        ));
    }
}
