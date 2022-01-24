package kr.co.geoplan.metro.src.review;

import kr.co.geoplan.metro.config.BaseResponse;
import kr.co.geoplan.metro.src.review.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.util.List;

import static kr.co.geoplan.metro.config.BaseResponseStatus.INVALID_USER_JWT;

@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;



    private GetResInfoRes getResInfoRes;
    private List<GetResReviewRes> getResReviewResList;

    private List<GetMenuReviewRes> getMenuReviewRes;
    private GetMenuInfoRes getMenuInfoRes;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * 식당 리뷰 조회 (유저)
     * [GET] /reviews/:userIdx/restaurants/:restaurantIdx
     * @return BaseResponse<GetResReviewPageRes>
     */
    public GetResReviewPageRes getResReview(int userIdx, int id){
        // 식당 정보 조회
        String getResInfoQuery = "SELECT resName, resInfo, COUNT(rc.id) as resCommentsCount, " +
                "round(SUM(rc.resScore)/COUNT(rc.resId),1) as resScoreAvg\n" +
                "FROM tb_foodly_restaurant r\n" +
                "LEFT JOIN tb_foodly_restaurant_comment rc\n" +
                "ON r.id = rc.resId\n" +
                "WHERE rc.resId = ?\n" +
                "GROUP BY rc.resId;";
        // 식당 리뷰 조회
        String getResReviewQuery = "SELECT u.name as userName, rc.resComment, " +
                "DATE_FORMAT(rc.reg_dt, '%y년%m월%d일') as resCommentDT\n" +
                "FROM tb_foodly_user u\n" +
                "LEFT JOIN tb_foodly_restaurant_comment rc\n" +
                "ON u.userIdx = rc.userId\n" +
                "WHERE rc.resId = ? and rc.status = 'Y';";


        int resParams = id;

        return new GetResReviewPageRes(
                getResInfoRes = this.jdbcTemplate.queryForObject(getResInfoQuery,
                        (rs, rowNum) -> new GetResInfoRes(
                                rs.getString("resName"),
                                rs.getString("resInfo"),
                                rs.getInt("resCommentsCount"),
                                rs.getDouble("resScoreAvg")),
                        resParams),

                getResReviewResList = this.jdbcTemplate.query(getResReviewQuery,
                        (rs, rowNum) -> new GetResReviewRes(
                                rs.getString("userName"),
                                rs.getString("resComment"),
                                rs.getString("resCommentDT")),
                        resParams)
                        );

    }


    /**
     * 메뉴 리뷰 조회 (유저)
     * [GET] /reviews/:userIdx/menus/:restaurantIdx/:menuIdx
     * @return BaseResponse<GetMenuReviewPageRes>
     */
    public GetMenuReviewPageRes getMenuReview(int userIdx, int restaurantIdx, int menuIdx){
        // 식당 정보
        String getResInfoQuery = "SELECT resName, resInfo, COUNT(rc.id) as resCommentsCount, " +
                "round(SUM(rc.resScore)/COUNT(rc.resId),1) as resScoreAvg\n" +
                "FROM tb_foodly_restaurant r\n" +
                "LEFT JOIN tb_foodly_restaurant_comment rc\n" +
                "ON r.id = rc.resId\n" +
                "WHERE rc.resId = ?\n" +
                "GROUP BY rc.resId;";

        // 메뉴 정보
        String getMenuInfoQuery = "SELECT menuName, menuInfo, menuIngredients, " +
                "COUNT(mc.id) as menuCommentCount, " +
                "round(SUM(mc.menuScore)/COUNT(mc.menuId),1) as menuScoreAvg\n" +
                "FROM tb_foodly_menu m\n" +
                "LEFT JOIN tb_foodly_menu_comment mc\n" +
                "ON m.id = mc.menuId\n" +
                "WHERE m.id = ? and m.status = 'Y'\n" +
                "GROUP BY mc.menuId;\n";

        // 메뉴 리뷰 리스트
        String getMenuReviewQuery = "SELECT name as userName, menuComment, " +
                "DATE_FORMAT(mc.reg_dt, '%y년%m월%d일') as menuCommentDT\n" +
                "FROM tb_foodly_menu_comment mc\n" +
                "LEFT JOIN tb_foodly_user u\n" +
                "ON mc.userId = u.userIdx\n" +
                "LEFT JOIN tb_foodly_menu m\n" +
                "ON m.id = mc.menuId\n" +
                "WHERE m.id = ? and mc.status = 'Y';";

        int resParams = restaurantIdx;
        int menuParams = menuIdx;

        return new GetMenuReviewPageRes(
                getResInfoRes = this.jdbcTemplate.queryForObject(getResInfoQuery,
                        (rs, rowNum) -> new GetResInfoRes(
                                rs.getString("resName"),
                                rs.getString("resInfo"),
                                rs.getInt("resCommentsCount"),
                                rs.getDouble("resScoreAvg")),
                        resParams),
                getMenuInfoRes = this.jdbcTemplate.queryForObject(getMenuInfoQuery,
                        (rs, rowNum) -> new GetMenuInfoRes(
                                rs.getString("menuName"),
                                rs.getString("menuInfo"),
                                rs.getString("menuIngredients"),
                                rs.getInt("menuCommentCount"),
                                rs.getDouble("menuScoreAvg")),
                        menuParams),
                getMenuReviewRes = this.jdbcTemplate.query(getMenuReviewQuery,
                        (rs, rowNum) -> new GetMenuReviewRes(
                                rs.getString("userName"),
                                rs.getString("menuComment"),
                                rs.getString("menuCommentDT")),
                        menuParams)

                );

    }


    /**
     * 식당 리뷰 등록 (유저)
     * [POST] /reviews/:userIdx/restaurants/:restaurantIdx
     * @return BaseResponse<PostResReviewRes>
     */
    public int createResReview(int userIdx, int resId,PostResReviewReq postResReviewReq){
        String Query1 = "INSERT INTO tb_foodly_restaurant_comment(resId, userId, resComment, resScore) values (?,?,?,?);";
        Object[] createResReviewParams = new Object[]{resId, userIdx, postResReviewReq.getResComment(), postResReviewReq.getResScore()};
        this.jdbcTemplate.update(Query1, createResReviewParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    /**
     * 식당 리뷰 수정 (유저)
     * [PATCH] /reviews/:userIdx/restaurants/:reviewIdx
     * @return BaseResponse<String>
     */
    public int modifyResReview(PatchResReviewReq patchResReviewReq){
        String modifyOrderQuery = "update tb_foodly_restaurant_comment set resComment = ?, resScore = ? where id = ?";
        Object[] modifyOrderParams = new Object[]{patchResReviewReq.getResComment(), patchResReviewReq.getResScore(), patchResReviewReq.getReviewIdx()};

        return this.jdbcTemplate.update(modifyOrderQuery,modifyOrderParams);
    }


    /**
     * 식당 리뷰 삭제 (유저)
     * [PATCH] /reviews/:userIdx/restaurants/:reviewIdx/status
     * @return BaseResponse<GetReviewRes>
     */
    public int deleteResReview(PatchResReviewStatusReq patchResReviewStatusReq){
        String modifyOrderQuery = "update tb_foodly_restaurant_comment set status = ? where id = ?";
        Object[] modifyOrderParams = new Object[]{patchResReviewStatusReq.getStatus(), patchResReviewStatusReq.getReviewIdx()};

        return this.jdbcTemplate.update(modifyOrderQuery,modifyOrderParams);
    }




    /**
     * 메뉴 리뷰 등록 (유저)
     * [POST] /reviews/:userIdx/menus/:menuIdx
     * @return BaseResponse<String>
     */
    public int createMenuReview(int userIdx, int menuId, PostMenuReviewReq postMenuReviewReq){
        String Query1 = "INSERT INTO tb_foodly_menu_comment(menuId, userId, menuComment, menuScore) values (?,?,?,?);";
        Object[] createResReviewParams = new Object[]{menuId, userIdx, postMenuReviewReq.getMenuComment(), postMenuReviewReq.getMenuScore()};
        this.jdbcTemplate.update(Query1, createResReviewParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }



    /**
     * 메뉴 리뷰 수정 (유저)
     * [PATCH] /reviews/:userIdx/menus/:reviewIdx
     * @return BaseResponse<String>
     */
    public int modifyMenuReview(PatchMenuReviewReq patchMenuReviewReq){
        String modifyOrderQuery = "update tb_foodly_menu_comment set menuComment = ?, menuScore = ? where id = ?";
        Object[] modifyOrderParams = new Object[]{patchMenuReviewReq.getMenuComment(), patchMenuReviewReq.getMenuScore(), patchMenuReviewReq.getReviewIdx()};

        return this.jdbcTemplate.update(modifyOrderQuery,modifyOrderParams);
    }




    /**
     * 메뉴 리뷰 삭제 (유저)
     * [PATCH] /reviews/:userIdx/menus/:reviewIdx/status
     * @return BaseResponse<String>
     */
    public int deleteMenuReview(PatchMenuReviewStatusReq patchMenuReviewStatusReq){
        String modifyOrderQuery = "update tb_foodly_menu_comment set status = ? where id = ?";
        Object[] modifyOrderParams = new Object[]{patchMenuReviewStatusReq.getStatus(), patchMenuReviewStatusReq.getReviewIdx()};

        return this.jdbcTemplate.update(modifyOrderQuery,modifyOrderParams);
    }




}
