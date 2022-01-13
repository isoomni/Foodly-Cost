package kr.co.geoplan.metro.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String UserName;
    private String id;
    private String email_address;
    private String password;
}
