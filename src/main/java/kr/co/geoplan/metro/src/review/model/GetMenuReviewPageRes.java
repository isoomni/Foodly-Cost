package kr.co.geoplan.metro.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@NotNull
@Data
@AllArgsConstructor
public class GetMenuReviewPageRes {
    private GetResInfoRes getResInfoRes;
    private GetMenuInfoRes getMenuInfoRes;
    private List<GetMenuReviewRes> getMenuReviewResList;
}
