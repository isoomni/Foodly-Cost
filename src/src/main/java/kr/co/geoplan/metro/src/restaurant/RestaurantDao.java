package kr.co.geoplan.metro.src.restaurant;


import kr.co.geoplan.metro.src.restaurant.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class RestaurantDao {

    private JdbcTemplate jdbcTemplate;

    private GetOneResInfoRes getOneResInfoResList;
    private List<GetResMenuRes> getResMenuResList;


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * 식당 전체 조회 (유저)
     * [GET] /restaurants/:userIdx
     * @return BaseResponse<GetRestaurantRes>
     * Query: user 주소를 기준으로
     */
    public List<GetRestaurantRes> getRestaurant(int userIdx) {
        String getRestaurantQuery = "SELECT resName, resInfo, resAddress,resLat, resLon,\n" +
                "       round(SUM(rc.resScore)/COUNT(rc.id),1) as scoreAvg,\n" +
                "       concat(round(6371*acos(cos(radians(u.userLat))*cos(radians(r.resLat))*cos(radians(r.resLon)\n" +
                "           -radians(r.resLon))+sin(radians(u.userLat))*sin(radians(r.resLat))), 1), 'km') as realDistance\n" +
                "FROM tb_foodly_restaurant r\n" +
                "    LEFT JOIN tb_foodly_restaurant_comment rc ON r.id = rc.resId\n" +
                "   , tb_foodly_user u\n" +
                "WHERE u.userIdx = ?\n" +
                "GROUP BY rc.resId\n" +
                "HAVING realDistance <= 3\n" +
                "ORDER BY scoreAvg;";

        int getRestaurantParams = userIdx;

        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs, rowNum)-> new GetRestaurantRes(
                        rs.getString("resName"),
                        rs.getString("resInfo"),
                        rs.getString("resAddress"),
                        rs.getDouble("resLat"),
                        rs.getDouble("resLon"),
                        rs.getDouble("scoreAvg"),
                        rs.getString("realDistance")
                ),
                getRestaurantParams);
    }

//    concat(round(6371*acos(cos(radians(DA.userLatitude))*cos(radians(R.resLatitude))*cos(radians(R.resLongtitude)
//    -radians(R.resLongtitude))+sin(radians(DA.userLatitude))*sin(radians(R.resLatitude))), 1), 'km')


    // addressKeyword
    public List<GetRestaurantRes> getRestaurantByAddressKeyword(int userIdx, String addressKeyword) {
        String getRestaurantQuery = "SELECT resName, resInfo, resAddress\n" +
                "                , resLat, resLon,\n" +
                "                round(SUM(rc.resScore)/COUNT(rc.id),1) as scoreAvg,\n" +
                "       concat(round(6371*acos(cos(radians(u.userLat))*cos(radians(r.resLat))*cos(radians(r.resLon)\n" +
                "             -radians(r.resLon))+sin(radians(u.userLat))*sin(radians(r.resLat))), 1), 'km') as realDistance\n" +
                "                FROM tb_foodly_restaurant r\n" +
                "                LEFT JOIN tb_foodly_restaurant_comment rc\n" +
                "                ON r.id = rc.resId, tb_foodly_user u\n" +
                "                WHERE u.userIdx = ? AND r.status = 'Y' AND INSTR(resAddress, ?) > 0\n" +
                "                GROUP BY rc.resId\n" +
                "                HAVING realDistance <= 3\n" +
                "                ORDER BY scoreAvg;";

        int getRestaurantParams = userIdx;
        String getAddressParams = addressKeyword;

        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs, rowNum)-> new GetRestaurantRes(
                        rs.getString("resName"),
                        rs.getString("resInfo"),
                        rs.getString("resAddress"),
                        rs.getDouble("resLat"),
                        rs.getDouble("resLon"),
                        rs.getDouble("scoreAvg"),
                        rs.getString("realDistance")
                ),
                getRestaurantParams, getAddressParams);
    }

    /**
     *
     * 식당 개별 조회
     * [GET] /restaurants/:restaurantIdx/
     * @return BaseResponse<GetOneRestaurantRes>
     */
    public GetOneRestaurantRes getOneRestaurant(int userIdx, int id){
        // 식당 정보
        String getOneResInfoQuery = "SELECT resName, resInfo, COUNT(rc.id) as resCommentsCount, " +
                "resAddress, resLat, resLon,\n" +
                "round(SUM(rc.resScore)/COUNT(rc.resId),1) as scoreAvg\n" +
                "FROM tb_foodly_restaurant r\n" +
                "     LEFT JOIN tb_foodly_restaurant_comment rc\n" +
                "ON r.id = rc.resId\n" +
                "WHERE r.id = ?\n" +
                "GROUP BY rc.resId;";
        // 메뉴 List
        String getResMenuQuery = "SELECT menuName, " +
                "round(SUM(mc.menuScore)/COUNT(mc.menuId),1) as menuScoreAvg, " +
                "menuInfo, menuIngredients\n" +
                "FROM tb_foodly_menu m\n" +
                "LEFT JOIN tb_foodly_menu_comment mc\n" +
                "ON m.id = mc.menuId\n" +
                "WHERE restaurantId = ? AND m.status = 'Y'\n" +
                "GROUP BY mc.menuId;";


        int getOneResParams = id;

        return new GetOneRestaurantRes(
                // 식당 정보
                getOneResInfoResList = this.jdbcTemplate.queryForObject(getOneResInfoQuery,
                        (rs, rowNum) -> new GetOneResInfoRes(
                                rs.getString("resName"),
                                rs.getString("resInfo"),
                                rs.getDouble("resCommentsCount"),
                                rs.getString("resAddress"),
                                rs.getDouble("resLat"),
                                rs.getDouble("resLon"),
                                rs.getDouble("scoreAvg")),
                                getOneResParams),
                // 메뉴 List
                getResMenuResList = this.jdbcTemplate.query(getResMenuQuery,
                        (rs, rowNum) -> new GetResMenuRes(
                                rs.getString("menuName"),
                                rs.getDouble("menuScoreAvg"),
                                rs.getString("menuInfo"),
                                rs.getString("menuIngredients")),
                                getOneResParams)

                        );
    }



}















