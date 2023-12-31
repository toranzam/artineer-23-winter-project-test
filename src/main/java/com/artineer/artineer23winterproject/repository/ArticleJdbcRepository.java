package com.artineer.artineer23winterproject.repository;

import com.artineer.artineer23winterproject.entity.Article;
import com.artineer.artineer23winterproject.entity.ArticleJdbc;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.file.OpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<ArticleJdbc> findAll() {
        String sql = "select * from article_jdbc";
        return jdbcTemplate.query(sql, new RowMapper<ArticleJdbc>() {
            @Override
            public ArticleJdbc mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String author = rs.getString("author");
                LocalDateTime localDateTime = rs.getTimestamp("local_date_time").toLocalDateTime();
                return new ArticleJdbc(id, title, content, author, localDateTime);
            }
        });
    }


    public Optional<ArticleJdbc> findById(Long id) {
        String sql = "select * from article_jdbc where id = ?";
        try{
            ArticleJdbc articleJdbc = jdbcTemplate.queryForObject(sql, new RowMapper<ArticleJdbc>() {
                @Override
                public ArticleJdbc mapRow(ResultSet rs, int rowNum) throws SQLException {
                    long id = rs.getLong("id");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String author = rs.getString("author");
                    LocalDateTime localDateTime = rs.getTimestamp("local_date_time").toLocalDateTime();
                    return new ArticleJdbc(id, title, content, author, localDateTime);
                }
            }, id);
            return Optional.of(articleJdbc);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }


    public void save(ArticleJdbc articleJdbc) {
        String sql = "insert into article_jdbc (title, content, author, local_date_time) values (?, ?, ?, ?)";
                jdbcTemplate.update(sql, articleJdbc.getTitle(), articleJdbc.getContent(), articleJdbc.getAuthor(), articleJdbc.getLocalDateTime());
    }

    public void update(ArticleJdbc articleJdbc) {
        String sql = "update article_jdbc set title=?, content=?, author=?, local_date_tiem=? where id=?";
        jdbcTemplate.update(sql, articleJdbc.getTitle(), articleJdbc.getContent(), articleJdbc.getAuthor(), articleJdbc.getLocalDateTime());
    }

    public void delete(ArticleJdbc articleJdbc) {
        String sql = "delete from article_jdbc where id=?";
        jdbcTemplate.update(sql, articleJdbc.getId());

    }


}







