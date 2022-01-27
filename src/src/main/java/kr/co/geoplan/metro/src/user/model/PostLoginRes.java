package kr.co.geoplan.metro.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@NotNull
@Data
@AllArgsConstructor
public class PostLoginRes {
    private int userIdx;
}
