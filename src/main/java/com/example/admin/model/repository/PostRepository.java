package com.example.admin.model.repository;


import com.example.admin.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public Long getCountAllPost() {
        return jdbcTemplate.queryForObject("select COUNT(*) from posts", Long.class);
    }

    public List<Post> findAll() {
        return jdbcTemplate.query("SELECT * FROM posts", postRowMapper);
    }

    public List<Post> findAll(int offset, int perPage) {
        try {
            return jdbcTemplate.query("SELECT * FROM posts WHERE is_deleted = false ORDER BY time DESC OFFSET ? ROWS LIMIT ?", postRowMapper, offset, perPage);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public int save(Post post) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("posts").usingGeneratedKeyColumns("id");
        Map<String, Object> values = new HashMap<>();
        values.put("post_text", post.getPostText());
        values.put("time", post.getTime());
        values.put("title", post.getTitle());
        values.put("author_id", post.getAuthorId());
        values.put("is_blocked", post.getIsBlocked());
        values.put("is_deleted", post.getIsDeleted());

        Number id = simpleJdbcInsert.executeAndReturnKey(values);
        return id.intValue();
    }

    public Post findById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM posts WHERE id = ?",
                    postRowMapper,
                    id
            );
        } catch (EmptyResultDataAccessException ignored) {
            return null;
        }
    }

    public void updateById(int id, Post post) {
        String update = "UPDATE posts SET post_text = ?, title = ? WHERE id = ?";
        jdbcTemplate.update(update, post.getPostText(), post.getTitle(), id);
    }

    public void markAsDeleteById(int id) {
        String update = "UPDATE posts SET is_deleted = true, time_delete = now() WHERE id = ?";
        jdbcTemplate.update(update, id);
    }


    public void deleteById(Long id) {
        jdbcTemplate.update("delete from posts where id = ?", id);
    }

    public List<Post> findPostsByUserId(Long userId, Integer offset, Integer perPage) {
        try {
            return jdbcTemplate.query(
                    "select * from posts where author_id = ? order by time desc offset ? rows limit ?",
                    postRowMapper,
                    userId,
                    offset,
                    perPage
            );
        } catch (EmptyResultDataAccessException ignored) {
            return null;
        }
    }

    public List<Post> findPostsByUserId(Long userId) {
        try {
            return jdbcTemplate.query(
                    "select * from posts where author_id = ?",
                    postRowMapper,
                    userId
            );
        } catch (EmptyResultDataAccessException ignored) {
            return null;
        }
    }

    public List<Post> findDeletedPosts() {
        String select = "SELECT * FROM posts WHERE is_deleted = true";
        try {
            return jdbcTemplate.query(select, postRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }


    private Timestamp parseDate(Long str) {
        Date date = new Date(str);
        return new Timestamp(date.getTime());
    }

    private final RowMapper<Post> postRowMapper = (resultSet, rowNum) -> {
        Post post = new Post();
        post.setId(resultSet.getLong("id"));
        post.setIsBlocked(resultSet.getBoolean("is_blocked"));
        post.setIsDeleted(resultSet.getBoolean("is_deleted"));
        post.setPostText(resultSet.getString("post_text"));
        post.setTime(resultSet.getTimestamp("time"));
        post.setTimeDelete(resultSet.getTimestamp("time_delete"));
        post.setTitle(resultSet.getString("title"));
        post.setAuthorId(resultSet.getLong("author_id"));
        return post;
    };
}
