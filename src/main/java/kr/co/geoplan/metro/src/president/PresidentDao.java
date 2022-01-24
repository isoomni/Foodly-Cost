package kr.co.geoplan.metro.src.president;

import kr.co.geoplan.metro.src.president.model.GetPresMenu;
import kr.co.geoplan.metro.src.president.model.GetPresResInfo;
import kr.co.geoplan.metro.src.president.model.GetPresidentRestaurantRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PresidentDao {

    private JdbcTemplate jdbcTemplate;
    private GetPresResInfo getPresResInfo;
    private List<GetPresMenu> getPresMenus;


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * 사장 계정 체크
     * getUserIdxInP
     */
    public int getUserIdxIsP (int userIdx){
        String Query = "SELECT IF(u.type != 'c', 1, 0) as typeP From tb_foodly_user u WHERE userIdx = ?;";

        int Params = userIdx;

        return this.jdbcTemplate.queryForObject(Query, int.class, Params);
    }

    /**
     * 내 식당 관리 페이지 조회 (사장)
     * [GET] /presidents/:userIdx
     *  @return BaseResponse<GetPresidentRes>
     */
    public GetPresidentRestaurantRes getPresidentRestaurant(int userIdx, int restaurantIdx){
        String Query1 = "SELECT resName, resInfo, resAddress, resLat, resLon\n" +
                "FROM tb_foodly_restaurant r\n" +
                "WHERE id = ?;";
        String Query2 = "SELECT menuName, menuInfo,menuIngredients\n" +
                "FROM tb_foodly_menu\n" +
                "WHERE restaurantId = ?;";
        int Params = userIdx;
        int Params2 = restaurantIdx;

        return new GetPresidentRestaurantRes (
                getPresResInfo = this.jdbcTemplate.queryForObject(Query1,
                (rs, rowNum) -> new GetPresResInfo(
                        rs.getString("resName"),
                        rs.getString("resInfo"),
                        rs.getString("resAddress"),
                        rs.getDouble("resLat"),
                        rs.getDouble("resLon")),
                        Params2),
                getPresMenus = this.jdbcTemplate.query(Query2,
                        (rs, rowNum) -> new GetPresMenu(
                                rs.getString("menuName"),
                                rs.getString("menuInfo"),
                                rs.getString("menuIngredients")),
                        Params2)
        );
    }
}
