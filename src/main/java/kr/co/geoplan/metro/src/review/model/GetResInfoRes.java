package kr.co.geoplan.metro.src.review.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;


@NotNull
@Data
@AllArgsConstructor
public class GetResInfoRes {
    private String resName;
    private String resInfo;
    private Integer resCommentsCount;
    private Double resScoreAvg;
}
