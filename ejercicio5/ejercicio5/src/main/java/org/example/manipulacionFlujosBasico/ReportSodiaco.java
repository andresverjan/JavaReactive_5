package org.example.manipulacionFlujosBasico;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ReportSodiaco {
    private String signo;
    private Integer cantidad;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
