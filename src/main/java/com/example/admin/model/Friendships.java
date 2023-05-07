package com.example.admin.model;

import com.example.admin.model.enums.FriendshipStatusTypes;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Friendships {
    private Long id;

    private Timestamp sentTime;

    private Long dstPersonId;

    private Long srcPersonId;

    private FriendshipStatusTypes statusName;
}
