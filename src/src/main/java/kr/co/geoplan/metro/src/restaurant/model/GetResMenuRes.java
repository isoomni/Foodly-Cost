package kr.co.geoplan.metro.src.restaurant.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@NotNull
@Data
@AllArgsConstructor
public class GetResMenuRes {
    String menuName;
    Double menuScoreAvg;
    String menuInfo;
    String menuIngredients;
}
