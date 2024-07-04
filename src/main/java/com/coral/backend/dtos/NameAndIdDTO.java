package com.coral.backend.dtos;

public class NameAndIdDTO {
    private String name;
    private long id;
    private String type;

    public NameAndIdDTO(String name, long id, String type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
