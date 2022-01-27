package kr.co.geoplan.metro.src.restaurant.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;


@NotNull
@Data
@AllArgsConstructor
public class GetOneResInfoRes {
    String resName;
    String resInfo;
    Double resCommentsCount;
    String resAddress;
    Double resLat;
    Double resLon;
    Double scoreAvg;
}
