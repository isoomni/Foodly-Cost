package kr.co.geoplan.metro.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@NotNull
@Data
@AllArgsConstructor
public class PatchResReviewReq {
    private Integer ReviewIdx;
    private String resComment;
    private Double resScore;

}
