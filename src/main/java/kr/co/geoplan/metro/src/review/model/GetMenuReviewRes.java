package kr.co.geoplan.metro.src.review.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;


@NotNull
@Data
@AllArgsConstructor
public class GetMenuReviewRes {
    private String userName;
    private String menuComment;
    private String menuCommentDT;
}
