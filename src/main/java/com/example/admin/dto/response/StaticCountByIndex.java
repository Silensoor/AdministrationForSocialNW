package com.example.admin.dto.response;

import lombok.Data;

@Data
public class StaticCountByIndex {

    private Long countPeople;
    private Long countOnlinePeople;
    private Long countPosts;
    private Long countMessages;
}
