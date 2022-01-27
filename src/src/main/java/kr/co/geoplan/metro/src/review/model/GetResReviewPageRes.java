package kr.co.geoplan.metro.src.review.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;


@NotNull
@Data
@AllArgsConstructor
public class GetResReviewPageRes {
    private GetResInfoRes getResInfoRes;
    private List<GetResReviewRes> getResReviewResList;
}
