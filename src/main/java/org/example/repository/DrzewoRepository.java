
package org.example.repository;

import org.example.encje.Drzewo;
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
public class DrzewoRepository {

    private final JdbcTemplate jdbc;

    private final RowMapper<Drzewo> mapper = (rs, rowNum) ->
            new Drzewo(
                    rs.getLong("id"),
                    rs.getString("gatunek"),
                    rs.getDouble("wysokosc"),
                    rs.getDouble("srednica"),
                    rs.getBoolean("sciete"),
                    rs.getBoolean("aktywny")
            );

    public DrzewoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Drzewo save(Drzewo drzewo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO drzewa (gatunek, wysokosc, srednica, sciete, aktywny) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, drzewo.getGatunek());
            ps.setDouble(2, drzewo.getWysokosc());
            ps.setDouble(3, drzewo.getSrednica());
            ps.setBoolean(4, drzewo.isSciete());
            ps.setBoolean(5, drzewo.isAktywny());

            return ps;
        }, keyHolder);

        if (keyHolder.getKey() == null) {
            throw new IllegalStateException("Nie udalo sie pobrac ID drzewa");
        }

        drzewo.setId(keyHolder.getKey().longValue());
        return drzewo;
    }

    public List<Drzewo> findAll() {
        return jdbc.query("SELECT * FROM drzewa ORDER BY id", mapper);
    }

    public Optional<Drzewo> findById(Long id) {
        return jdbc.query(
                "SELECT * FROM drzewa WHERE id = ?",
                mapper,
                id
        ).stream().findFirst();
    }

    public int update(Long id, Drzewo drzewo) {
        return jdbc.update(
                "UPDATE drzewa SET gatunek = ?, wysokosc = ?, srednica = ?, sciete = ? WHERE id = ?",
                drzewo.getGatunek(),
                drzewo.getWysokosc(),
                drzewo.getSrednica(),
                drzewo.isSciete(),
                id
        );
    }

    public int makeInactive(Long id) {
        return jdbc.update(
                "UPDATE drzewa SET aktywny = FALSE WHERE id = ?",
                id
        );
    }

    public int delete(Long id) {
        return jdbc.update(
                "DELETE FROM drzewa WHERE id = ?",
                id
        );
    }
}
