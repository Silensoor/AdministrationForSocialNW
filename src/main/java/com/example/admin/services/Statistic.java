package com.example.admin.services;

import com.example.admin.dto.response.StaticCountByIndex;

public interface Statistic {
    Long getCountPeople();
    Long getCountOnlinePeople();
    Long getCountMessages();
    Long getCountPost();
    StaticCountByIndex getStatic();

}
