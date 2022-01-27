package kr.co.geoplan.metro.src.review;

import kr.co.geoplan.metro.config.BaseException;
import kr.co.geoplan.metro.config.BaseResponseStatus;
import kr.co.geoplan.metro.src.restaurant.RestaurantDao;
import kr.co.geoplan.metro.src.restaurant.RestaurantProvider;
import kr.co.geoplan.metro.src.review.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static kr.co.geoplan.metro.config.BaseResponseStatus.*;


@Service
public class ReviewService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewProvider reviewProvider;
    private final ReviewDao reviewDao;

    @Autowired
    public ReviewService(ReviewProvider reviewProvider, ReviewDao reviewDao){
        this.reviewProvider = reviewProvider;
        this.reviewDao = reviewDao;

    }

    /**
     * 식당 리뷰 등록 (유저)
     * [POST] /reviews/:userIdx/restaurants/:restaurantIdx
     * @return BaseResponse<PostResReviewRes>
     */
    public PostResReviewRes createResReview(int userIdx, int resId,PostResReviewReq postResReviewReq) throws BaseException {
        // 주인이 리뷰 등록할 수 없게
        try {
            int commentIdx = reviewDao.createResReview(userIdx, resId, postResReviewReq);
            return new PostResReviewRes(commentIdx);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 식당 리뷰 수정 (유저)
     * [PATCH] /reviews/:userIdx/restaurants/:reviewIdx
     * @return BaseResponse<String>
     */
    public void modifyResReview(PatchResReviewReq patchResReviewReq) throws BaseException {
        try{
            int result = reviewDao.modifyResReview(patchResReviewReq);
            if (result == 0){
                throw new BaseException(REVIEW_MODIFY_FAIL);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


    /**
     * 식당 리뷰 삭제 (유저)
     * [PATCH] /reviews/:userIdx/restaurants/:reviewIdx/status
     * @return BaseResponse<GetReviewRes>
     */
    public void deleteResReview(PatchResReviewStatusReq patchResReviewStatusReq) throws BaseException {
        try{
            int result = reviewDao.deleteResReview(patchResReviewStatusReq);
            if (result == 0){
                throw new BaseException(REVIEW_DELETE_FAIL);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }



    /**
     * 메뉴 리뷰 등록 (유저)
     * [POST] /reviews/:userIdx/menus/:menuIdx
     * @return BaseResponse<String>
     */
    public PostMenuReviewRes createMenuReview(int userIdx, int menuId, PostMenuReviewReq postMenuReviewReq) throws BaseException {
        // 주인이 리뷰 등록할 수 없게
        try {
            int commentIdx = reviewDao.createMenuReview(userIdx, menuId, postMenuReviewReq);
            return new PostMenuReviewRes(commentIdx);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


    /**
     * 메뉴 리뷰 수정 (유저)
     * [PATCH] /reviews/:userIdx/menus/:reviewIdx
     * @return BaseResponse<String>
     */
    public void modifyMenuReview(PatchMenuReviewReq patchMenuReviewReq) throws BaseException {
        try{
            int result = reviewDao.modifyMenuReview(patchMenuReviewReq);
            if (result == 0){
                throw new BaseException(REVIEW_MODIFY_FAIL);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 메뉴 리뷰 삭제 (유저)
     * [PATCH] /reviews/:userIdx/menus/:reviewIdx/status
     * @return BaseResponse<String>
     */
    public void deleteMenuReview(PatchMenuReviewStatusReq patchMenuReviewStatusReq) throws BaseException {
        try{
            int result = reviewDao.deleteMenuReview(patchMenuReviewStatusReq);
            if (result == 0){
                throw new BaseException(REVIEW_DELETE_FAIL);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }




    /**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */
    @ResponseBody
    @GetMapping("/log")
    public String getAll() {
        System.out.println("테스트");
//        trace, debug 레벨은 Console X, 파일 로깅 X
//        logger.trace("TRACE Level 테스트");
//        logger.debug("DEBUG Level 테스트");

//        info 레벨은 Console 로깅 O, 파일 로깅 X
        logger.info("INFO Level 테스트");
//        warn 레벨은 Console 로깅 O, 파일 로깅 O
        logger.warn("Warn Level 테스트");
//        error 레벨은 Console 로깅 O, 파일 로깅 O (app.log 뿐만 아니라 error.log 에도 로깅 됨)
//        app.log 와 error.log 는 날짜가 바뀌면 자동으로 *.gz 으로 압축 백업됨
        logger.error("ERROR Level 테스트");

        return "Success Test";
    }
}
