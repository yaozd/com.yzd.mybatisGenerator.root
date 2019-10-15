package request;

import $NAMASPACE$.entity.table.Object;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yaozhendong
 * @create: 2019-10-14 15:56
 **/
@Data
@NoArgsConstructor
public class UpdateTempForm {
    private Long id;

    public static Object toEntity(UpdateTempForm form) {
        return null;
    }
}