package kr.co.geoplan.metro.src.review.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.DateFormat;


@NotNull
@Data
@AllArgsConstructor
public class GetResReviewRes {
    private String userName;
    private String resComment;
    private String resCommentDT;
}
