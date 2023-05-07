package com.example.admin.model.repository;


import com.example.admin.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {
    private final JdbcTemplate jdbcTemplate;

    public void updateIsReadById(Integer notificationId) {
        jdbcTemplate.update("update notifications set is_read = 'true' where id = ?",
                notificationId);
    }

    public void updateIsReadAll(Long id) {
        jdbcTemplate.update("update notifications set is_read = 'true' where person_id = ?", id);
    }

    public List<Notification> getNotificationsById(Integer id) {
        return jdbcTemplate.query("select * from notifications where id = ?",
                notificationRowMapper, id);
    }
    public List<Notification> getNotificationsByEntityIdOrPersonId(Long id) {
        return jdbcTemplate.query("select * from notifications where entity_id = ? OR person_id = ?",
                notificationRowMapper, id,id);
    }
    public void deleteNotification(Notification notification){
        jdbcTemplate.update("delete from notifications where id = ?",notification.getId());
    }

    public List<Notification> getNotificationsByPersonId(Long id) {
        return jdbcTemplate.query("select * from notifications where person_id = ? " +
                "and is_read = false order by sent_time", notificationRowMapper, id);
    }

    public List<Notification> getNotifications(Long id, Integer itemPerPage, Integer offset) {
        return jdbcTemplate.query("select * from notifications where person_id = ? " +
                        "and is_read = false order by sent_time DESC  ",
                notificationRowMapper, id);
    }

    private final RowMapper<Notification> notificationRowMapper = (rs, rowNum) -> {
        Notification notification = new Notification();
        notification.setId(rs.getLong("id"));
        notification.setNotificationType(rs.getString("notification_type"));
        notification.setContact(rs.getString("contact"));
        notification.setIsRead(rs.getBoolean("is_read"));
        notification.setPersonId(rs.getLong("person_id"));
        notification.setEntityId(rs.getLong("entity_id"));
        notification.setSentTime(rs.getTimestamp("sent_time"));
        return notification;
    };

    public Long saveNotification(Notification notification) {
        jdbcTemplate.update("insert into notifications(contact,notification_type,entity_id,is_read,sent_time,person_id) values (?,?,?,?,?,?)",
                notification.getContact(), notification.getNotificationType(),
                notification.getEntityId(), notification.getIsRead(),
                notification.getSentTime(), notification.getPersonId());
        return jdbcTemplate.query("select * from notifications where id =(select max(id) from notifications)", (rs, rowNum) -> {
            return rs.getLong("id");
        }).stream().findAny().orElse(null);
    }


}
