package request.vip;

import $NAMASPACE$.model.request.form.PageForm;
import $NAMASPACE$.entity.table.Object;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: yaozhendong
 * @create: 2019-10-14 15:02
 **/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ListTempForm extends PageForm {


    public static Object toEntity(ListTempForm form) {
        return null;
    }
}