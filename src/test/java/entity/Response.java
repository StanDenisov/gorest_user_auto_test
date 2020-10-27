package entity;

import lombok.Data;

@Data
public class Response {
    public Integer code;
    public String meta;
    public User data;

    public Response(){}
}
