package kr.co.geoplan.metro.src.president.model;

import kr.co.geoplan.metro.src.restaurant.model.GetOneResInfoRes;
import kr.co.geoplan.metro.src.restaurant.model.GetResMenuRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@NotNull
@Data
@AllArgsConstructor
public class GetPresidentRestaurantRes {
    private GetPresResInfo getPresResInfo;
    private List<GetPresMenu> getPresMenus;
}
