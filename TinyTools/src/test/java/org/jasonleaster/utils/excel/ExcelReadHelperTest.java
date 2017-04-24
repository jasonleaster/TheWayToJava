package org.jasonleaster.utils.excel;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Author: jasonleaster
 * Date  : 2017/4/24
 * Email : jasonleaster@gmail.com
 */
public class ExcelReadHelperTest {

    private String filePath = "excels/user.xlsx";

    @Test
    public void excelRead() throws Exception {
        String[] properties = new String[2];
        properties[0] = "name";
        properties[1] = "age";
        List<Object> users = ExcelReadHelper.excelRead(filePath, properties,User.class);
    }

    @Test
    public void excelRead1() throws Exception {

    }

}