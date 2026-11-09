
package org.example.repository;

import org.example.encje.Pracownik;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class PracownikRepository {

    private final JdbcTemplate jdbc;

    private final RowMapper<Pracownik> mapper = (rs, rowNum) ->
            new Pracownik(
                    rs.getLong("id"),
                    rs.getString("imie"),
                    rs.getString("stanowisko"),
                    rs.getDouble("pensja"),
                    rs.getBoolean("aktywny")
            );

    public PracownikRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Pracownik save(Pracownik pracownik) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO pracownicy (imie, stanowisko, pensja, aktywny) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, pracownik.getImie());
            ps.setString(2, pracownik.getStanowisko());
            ps.setDouble(3, pracownik.getPensja());
            ps.setBoolean(4, pracownik.isAktywny());

            return ps;
        }, keyHolder);

        if (keyHolder.getKey() == null) {
            throw new IllegalStateException("Nie udalo sie pobrac ID pracownika");
        }

        pracownik.setId(keyHolder.getKey().longValue());
        return pracownik;
    }

    public List<Pracownik> findAll() {
        return jdbc.query(
                "SELECT * FROM pracownicy ORDER BY id",
                mapper
        );
    }

    public Optional<Pracownik> findById(Long id) {
        return jdbc.query(
                "SELECT * FROM pracownicy WHERE id = ?",
                mapper,
                id
        ).stream().findFirst();
    }

    public int update(Long id, Pracownik pracownik) {
        return jdbc.update(
                "UPDATE pracownicy SET imie = ?, stanowisko = ?, pensja = ? WHERE id = ?",
                pracownik.getImie(),
                pracownik.getStanowisko(),
                pracownik.getPensja(),
                id
        );
    }

    public int makeInactive(Long id) {
        return jdbc.update(
                "UPDATE pracownicy SET aktywny = FALSE WHERE id = ?",
                id
        );
    }

    public int delete(Long id) {
        return jdbc.update(
                "DELETE FROM pracownicy WHERE id = ?",
                id
        );
    }
}
