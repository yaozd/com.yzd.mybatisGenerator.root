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

public class FileAndCodeTemplateALLTest {
    private static final String NAMESPACE="com.yzd";
    /**
     * 生成Controller所需要的所有对象
     * @throws Exception
     */
    @Test
    public void createTest() throws Exception {
        String className = "Vip";
        String tableClassName = "TbVip";
        createFileByCodeTemplate(className, tableClassName, "ListTempForm", "request");
        createFileByCodeTemplate(className, tableClassName, "SaveTempForm", "request");
        createFileByCodeTemplate(className, tableClassName, "UpdateTempForm", "request");
        createFileByCodeTemplate(className, tableClassName, "GetTempVO", "response");
        createFileByCodeTemplate(className, tableClassName, "ListVipVO", "response");
    }

    private void createFileByCodeTemplate(String className, String tableClassName, String codeTemplate, String typeName) throws Exception {
        String newClassName = codeTemplate.replace("Temp", className);
        File readFile = new File(System.getProperty("user.dir") + String.format("/src/main/resources/%s/%s.tpl", typeName, codeTemplate));
        if (!readFile.exists()) {
            throw new NullPointerException("NOT FOUND FILE");
        }
        StringBuilder contentBuilder = new StringBuilder();
        List<String> lines = readFile(readFile);
        for (String string : lines) {
            System.out.println(string);
            contentBuilder.append(string + "\n");
        }
        String newContent = contentBuilder.toString()
                .replace("$NAMASPACE$",NAMESPACE).replace(codeTemplate, newClassName).replace("Object",tableClassName);

        File writeFile = new File(System.getProperty("user.dir") + String.format("/src/main/java/com/yzd/%s/%s/%s.java", typeName, className.toLowerCase(), newClassName));
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
