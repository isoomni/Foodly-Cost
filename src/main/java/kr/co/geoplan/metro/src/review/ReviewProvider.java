package kr.co.geoplan.metro.src.review;


import com.fasterxml.jackson.databind.ser.Serializers;
import kr.co.geoplan.metro.config.BaseException;
import kr.co.geoplan.metro.config.BaseResponseStatus;
import kr.co.geoplan.metro.src.review.model.GetMenuReviewPageRes;
import kr.co.geoplan.metro.src.review.model.GetMenuReviewRes;
import kr.co.geoplan.metro.src.review.model.GetResReviewPageRes;
import kr.co.geoplan.metro.src.review.model.GetResReviewRes;
import kr.co.geoplan.metro.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static kr.co.geoplan.metro.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ReviewProvider {
    private final ReviewDao reviewDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReviewProvider(ReviewDao reviewDao, JwtService jwtService) {
        this.reviewDao = reviewDao;

    }
    /**
     * 식당 리뷰 조회 (유저)
     * [GET] /reviews/:userIdx
     * @return BaseResponse<GetReviewRes>
     */
    public GetResReviewPageRes getResReview(int userIdx, int id) throws BaseException{
        try{
            GetResReviewPageRes getResReviewPageRes = reviewDao.getResReview(userIdx, id);
            return getResReviewPageRes;
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


    /**
     * 메뉴 리뷰 조회 (유저)(레스토랑)(메뉴)
     * [GET] /reviews/:userIdx/:restaurantIdx/:menuIdx
     * @return BaseResponse<GetMenuReviewRes>
     */
    public GetMenuReviewPageRes getMenuReview(int userIdx, int restaurantIdx, int menuIdx) throws BaseException{
        try{
            GetMenuReviewPageRes getMenuReviewPageRes = reviewDao.getMenuReview(userIdx, restaurantIdx, menuIdx);
            return getMenuReviewPageRes;
        } catch (Exception exception){
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
