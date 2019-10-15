import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: yaozhendong
 * @create: 2019-10-14 14:52
 **/

public class FileAndCodeTemplateTest {
    /**
     * 测试类
     * @throws Exception
     */
    @Test
    public void createTest() throws Exception {
        String className = "Vip";
        String codeTemplate="ListTempForm.java";
        String typeName="request";
        String newListTempFormName=String.format("List%sForm",className);
        createFileByCodeTemplate(className, codeTemplate, typeName, newListTempFormName);
    }

    private void createFileByCodeTemplate(String className, String codeTemplate, String typeName, String newListTempFormName) throws Exception {
        File readFile = new File(System.getProperty("user.dir") + String.format("/src/main/resources/%s/%s",typeName,codeTemplate));
        if (!readFile.exists()) {
            throw new NullPointerException("NOT FOUND FILE");
        }
        StringBuilder contentBuilder = new StringBuilder();
        List<String> lines = readFile(readFile);
        for (String string : lines) {
            System.out.println(string);
            contentBuilder.append(string + "\n");
        }
        String newContent=contentBuilder.toString().replace("ListTempForm",newListTempFormName);

        File writeFile = new File(System.getProperty("user.dir") + String.format("/src/main/java/com/yzd/%s/%s/%s.java",typeName,className.toLowerCase(),newListTempFormName));
        writeFile(newContent, writeFile);
    }

    /**
     * @param file
     * @return
     * @Description: Guava文件读取
     * @Title: FileGuava.java
     * @Copyright: Copyright (c) 2014
     * @author Comsys-LZP
     * @date 2014-6-26 下午01:20:50
     * @version V2.0
     */
    private static List<String> readFile(File file) throws Exception {
        if (!file.exists()) {
            return null;
        }
        return Files.readLines(file, Charsets.UTF_8);
    }

    /**
     * @param file
     * @return
     * @Description: 从文件中获取有规则的数据
     * @Title: FileGuava.java
     * @Copyright: Copyright (c) 2014
     * @author Comsys-LZP
     * @date 2014-6-26 下午01:56:42
     * @version V2.0
     */
    public List<String[]> readFileData(File file) throws Exception {
        List<String[]> list = new ArrayList<String[]>();
        for (String rLine : readFile(file)) {
            list.add(rLine.split("\\s+"));
        }
        return list;
    }

    /**
     * @param content
     * @param file
     * @Description: Guava写文件
     * @Title: FileGuava.java
     * @Copyright: Copyright (c) 2014
     * @author Comsys-LZP
     * @date 2014-6-26 下午01:32:06
     * @version V2.0
     */
    private static void writeFile(String content, File file) throws Exception {
        File pathFile = file.getParentFile();
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.write(content, file, Charsets.UTF_8);
    }
}
