package kr.co.geoplan.metro.src.restaurant;


import kr.co.geoplan.metro.config.BaseException;
import kr.co.geoplan.metro.config.BaseResponse;
import kr.co.geoplan.metro.src.restaurant.model.GetOneRestaurantRes;
import kr.co.geoplan.metro.src.restaurant.model.GetRestaurantRes;
import kr.co.geoplan.metro.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kr.co.geoplan.metro.config.BaseResponseStatus.INVALID_USER_JWT;
import static kr.co.geoplan.metro.config.BaseResponseStatus.POST_ADDRESS_PARAM_CONTAINS_SPACE;


@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final RestaurantProvider restaurantProvider;
    @Autowired
    private final RestaurantService restaurantService;
    @Autowired
    private final JwtService jwtService;


    public RestaurantController(RestaurantProvider restaurantProvider, RestaurantService restaurantService, JwtService jwtService) {
        this.restaurantProvider = restaurantProvider;
        this.restaurantService = restaurantService;
        this.jwtService = jwtService;
    }

    /**
     * 식당 전체 조회 (유저)
     * ( 주소 검색에 따른 식당 조회 )
     * [GET] /restaurants/:userIdx
     * [GET] /restaurants/:userIdx?addressKeyword=
     * @return BaseResponse<GetRestaurantRes>
     */
    @ResponseBody
    @GetMapping("/{userIdx}") // (GET) 127.0.0.1:8080/restaurants/:id
    public BaseResponse<List<GetRestaurantRes>> getRestaurant(@PathVariable("userIdx") int userIdx, @RequestParam(required = false) String addressKeyword){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 필요한 api 에서 사용
            // 여기는 필터
            if (addressKeyword.contains(" ")){
                return new BaseResponse<>(POST_ADDRESS_PARAM_CONTAINS_SPACE);
            }
            if (addressKeyword != null) {  // 만약 addressKeyword가 존재하면
                List<GetRestaurantRes> getRestaurantRes = restaurantProvider.getRestaurantByAddressKeyword(userIdx, addressKeyword);
                return new BaseResponse<>(getRestaurantRes);
            } List<GetRestaurantRes> getRestaurantRes = restaurantProvider.getRestaurant(userIdx);
            return new BaseResponse<>(getRestaurantRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 식당 개별 조회 (유저)
     * [GET] /restaurants/:userIdx/:restaurantIdx
     * @return BaseResponse<GetOneRestaurantRes>
     */
    @ResponseBody
    @GetMapping("/{userIdx}/{restaurantIdx}") // (GET) 127.0.0.1:8080/restaurants/:userIdx/:restaurantIdx
    public BaseResponse<GetOneRestaurantRes> getOneRestaurant(@PathVariable("userIdx") int userIdx, @PathVariable("restaurantIdx") int id){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            GetOneRestaurantRes getOneRestaurantRes = restaurantProvider.getOneRestaurant(userIdx, id);
            return new BaseResponse<>(getOneRestaurantRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
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

