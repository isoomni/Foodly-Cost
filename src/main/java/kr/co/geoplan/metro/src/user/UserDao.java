package kr.co.geoplan.metro.src.user;

import kr.co.geoplan.metro.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /** 회원가입 API */
    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into tb_foodly_user (emailAddress, password, name, type) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getEmailAddress(), postUserReq.getPassword(),postUserReq.getName(),postUserReq.getType()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkEmail(String emailAddress){
        String checkEmailQuery = "select exists(select emailAddress from tb_foodly_user where emailAddress = ?)";
        String checkEmailParams = emailAddress;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }
    /** 로그인 API */
    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, emailAddress,password,name from tb_foodly_user where emailAddress = ?";
        String getPwdParams = postLoginReq.getEmailAddress();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("emailAddress"),
                        rs.getString("password"),
                        rs.getString("name")
                ),
                getPwdParams
        );

    }

}
