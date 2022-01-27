package kr.co.geoplan.metro.src.president;

import kr.co.geoplan.metro.config.BaseException;
import kr.co.geoplan.metro.src.president.model.GetPresidentRestaurantRes;
import kr.co.geoplan.metro.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static kr.co.geoplan.metro.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PresidentProvider {
    private final PresidentDao presidentDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PresidentProvider(PresidentDao presidentDao, JwtService jwtService) {
        this.presidentDao = presidentDao;
    }

    /**
     * 사장 계정 체크
     * getUserIdxInP
     */
    public int getUserIdxIsP(int userIdx) throws BaseException {
        try {
            return presidentDao.getUserIdxIsP(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 내 식당 관리 페이지 조회 (사장)
     * [GET] /presidents/:id
     *  @return BaseResponse<GetPresidentRes>
     */
    public GetPresidentRestaurantRes getPresidentRestaurant(int userIdx, int restaurantIdx) throws BaseException{
        try {
            GetPresidentRestaurantRes getPresidentRestaurantRes = presidentDao.getPresidentRestaurant(userIdx, restaurantIdx);
            return getPresidentRestaurantRes;
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
