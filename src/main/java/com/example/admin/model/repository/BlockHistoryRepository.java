package com.example.admin.model.repository;

import com.example.admin.model.BlockHistory;
import com.example.admin.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlockHistoryRepository {
    private final JdbcTemplate jdbcTemplate;


    public List<BlockHistory> findBlocksByCommentId(Long commentId) {
        return jdbcTemplate.query("select * from block_history where comment_id = ?", blockHistoryRowMapper, commentId);
    }
    public List<BlockHistory> findBlocksByPersonId(Long personId) {
        return jdbcTemplate.query("select * from block_history where person_id = ?", blockHistoryRowMapper, personId);
    }
    public List<BlockHistory> findBlocksByPostId(Long postId) {
        return jdbcTemplate.query("select * from block_history where post_id = ?", blockHistoryRowMapper, postId);
    }

    private final RowMapper<BlockHistory> blockHistoryRowMapper = (rs, rowNum) -> {
        BlockHistory blockHistory = new BlockHistory();
        blockHistory.setId(rs.getLong("id"));
        blockHistory.setAction(rs.getString("action"));
        blockHistory.setTime(rs.getTimestamp("time"));
        blockHistory.setPostId(rs.getLong("post_id"));
        blockHistory.setPersonId(rs.getLong("person_id"));
        blockHistory.setCommentId(rs.getLong("comment_id"));
        return blockHistory;
    };

    public void deleteBlockHistoryById(Long id) {
        jdbcTemplate.update("delete from block_history where id =?", id);
    }
}
