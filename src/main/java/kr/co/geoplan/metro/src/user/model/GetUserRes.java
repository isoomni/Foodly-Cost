package kr.co.geoplan.metro.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int id;
    private String email_address;
    private String password;
    private String name;
}
