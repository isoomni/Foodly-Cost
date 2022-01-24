package kr.co.geoplan.metro.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@NotNull
@Data
@AllArgsConstructor
public class GetMenuInfoRes {
    private String menuName;
    private String menuInfo;
    private String menuIngredients;
    private Integer menuCommentCount;
    private Double menuScoreAvg;
}
