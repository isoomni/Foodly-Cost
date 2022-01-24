package kr.co.geoplan.metro.src.president.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@NotNull
@Data
@AllArgsConstructor
public class GetPresResInfo {
    private String resName;
    private String resInfo;
    private String resAddress;
    private Double resLat;
    private Double resLon;
}
