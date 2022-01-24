package kr.co.geoplan.metro.src.restaurant;

import kr.co.geoplan.metro.config.BaseException;
import kr.co.geoplan.metro.src.restaurant.model.GetOneRestaurantRes;
import kr.co.geoplan.metro.src.restaurant.model.GetRestaurantRes;
import kr.co.geoplan.metro.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static kr.co.geoplan.metro.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class RestaurantProvider {
    private final RestaurantDao restaurantDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RestaurantProvider(RestaurantDao restaurantDao, JwtService jwtService) {
        this.restaurantDao = restaurantDao;

    }

    /**
     * 식당 전체 조회 (유저)
     * [GET] /restaurants/:userIdx
     * @return BaseResponse<GetRestaurantRes>
     */
    public List<GetRestaurantRes> getRestaurant(int userIdx) throws BaseException {
        try{
            List<GetRestaurantRes> getRestaurantRes = restaurantDao.getRestaurant(userIdx);
            return getRestaurantRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // addressKeyword
    public List<GetRestaurantRes> getRestaurantByAddressKeyword(int userIdx, String addressKeyword) throws BaseException {
        try{
            List<GetRestaurantRes> getRestaurantRes = restaurantDao.getRestaurantByAddressKeyword(userIdx, addressKeyword);
            return getRestaurantRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }






    /**
     * 식당 개별 조회
     * [GET] /restaurants/:restaurantIdx/
     * @return BaseResponse<GetOneRestaurantRes>
     */
    public GetOneRestaurantRes getOneRestaurant(int userIdx, int id) throws BaseException {
        try {
            GetOneRestaurantRes getOneRestaurant = restaurantDao.getOneRestaurant(userIdx, id);
            return getOneRestaurant;
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
