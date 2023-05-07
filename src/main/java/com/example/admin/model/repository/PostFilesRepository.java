package com.example.admin.model.repository;

import com.example.admin.model.BlockHistory;
import com.example.admin.model.PostFiles;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostFilesRepository {
    private final JdbcTemplate jdbcTemplate;


    public List<PostFiles> findPostFilesByPostId(Long postId) {
        return jdbcTemplate.query("select * from post_files where post_id = ?", postFilesRowMapper, postId);
    }


    public void deletePostFileById(Long id) {
        jdbcTemplate.update("delete from post_files where id = ?", id);
    }

    private final RowMapper<PostFiles> postFilesRowMapper = (rs, rowNum) -> {
        PostFiles postFiles = new PostFiles();
        postFiles.setId(rs.getLong("id"));
        postFiles.setPost_id(rs.getLong("post_id"));
        postFiles.setName(rs.getString("name"));
        postFiles.setPath(rs.getString("path"));
        return postFiles;
    };
}
