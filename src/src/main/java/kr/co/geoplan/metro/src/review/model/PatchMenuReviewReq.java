package kr.co.geoplan.metro.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@NotNull
@Data
@AllArgsConstructor
public class PatchMenuReviewReq {
    private Integer ReviewIdx;
    private String menuComment;
    private Double menuScore;
}
