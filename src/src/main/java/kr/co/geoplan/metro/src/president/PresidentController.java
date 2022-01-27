package kr.co.geoplan.metro.src.president;

import kr.co.geoplan.metro.config.BaseException;
import kr.co.geoplan.metro.config.BaseResponse;
import kr.co.geoplan.metro.src.president.model.GetPresidentRestaurantRes;
import kr.co.geoplan.metro.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static kr.co.geoplan.metro.config.BaseResponseStatus.INVALID_USER_JWT;
import static kr.co.geoplan.metro.config.BaseResponseStatus.POST_PRESIDENTS_USERIDX_IS_NOT_P;

@RestController
@RequestMapping("/presidents")
public class PresidentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PresidentProvider presidentProvider;
    @Autowired
    private final PresidentService presidentService;
    @Autowired
    private final JwtService jwtService;

    public PresidentController(PresidentProvider presidentProvider, PresidentService presidentService, JwtService jwtService){
        this.presidentProvider = presidentProvider;
        this.presidentService = presidentService;
        this.jwtService = jwtService;
    }

    /**
     * 내 식당 관리 페이지 조회 (사장)
     * [GET] /presidents/:userIdx/:restaurantIdx
     *  @return BaseResponse<GetPresidentRes>
     * */
    @ResponseBody
    @GetMapping("{userIdx}/{restaurantIdx}")
    public BaseResponse<GetPresidentRestaurantRes> getPresidentPage(@PathVariable("userIdx") int userIdx,@PathVariable("restaurantIdx") int restaurantIdx){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            // CHECK USER IS PRESIDENT OR NOT
            int userIdxIsP = presidentProvider.getUserIdxIsP(userIdx);
            if (userIdxIsP != 1){  // president가 아니면 에러 메시지
                return new BaseResponse<>(POST_PRESIDENTS_USERIDX_IS_NOT_P);
            }// president 이면 return Res
            GetPresidentRestaurantRes getPresidentRestaurantRes = presidentProvider.getPresidentRestaurant(userIdx, restaurantIdx);
            return new BaseResponse<>(getPresidentRestaurantRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
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
