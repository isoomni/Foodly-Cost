package kr.co.geoplan.metro.src.president.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@NotNull
@Data
@AllArgsConstructor
public class GetPresMenu {
    private String menuName;
    private String menuInfo;
    private String menuIngredients;
}
