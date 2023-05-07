package com.example.admin.model;

import lombok.Data;

@Data
public class PostFiles {
    private Long id;
    private String name;
    private String path;
    private Long post_id;
}
