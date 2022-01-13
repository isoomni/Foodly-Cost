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

    public List<GetUserRes> getUser(int id){
        String getUserQuery = "select * from tb_foodly_user where id = ?";
        int getUserParams = id;
        return this.jdbcTemplate.query(getUserQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("id"),
                        rs.getString("email_address"),
                        rs.getString("password"),
                        rs.getString("name")),
                getUserParams
                );
    }


    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into tb_foodly_user (id, email_address, password, name) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getId(),postUserReq.getEmail_address(), postUserReq.getPassword(),postUserReq.getUserName()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkEmail(String emailAddress){
        String checkEmailQuery = "select exists(select emailAddress from tb_foodly_user where email_address = ?)";
        String checkEmailParams = emailAddress;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, password,emailAddress,userName from RC_coupang_eats_d_Riley.User where emailAddress = ?";
        String getPwdParams = postLoginReq.getEmail_address();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("emailAddress")
                ),
                getPwdParams
        );

    }

}
