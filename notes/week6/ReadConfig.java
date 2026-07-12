import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class ReadConfig {

    private static final Logger LOG = Logger.getLogger(ReadConfig.class);

    public static void main(String[] args) {
        // 初始化 Log4j 基础配置（输出到控制台）
        BasicConfigurator.configure();

        String path = "C:/Users/wangyu/adf-sample-agent-java-master/config/module.cfg";
        LOG.info("开始读取配置文件: " + path);

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            LOG.info("文件打开成功，开始逐行解析...");

            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                // 跳过空行和注释行（## 开头）
                if (line.isBlank() || line.startsWith("##")) {
                    continue;
                }

                // 按 ":" 分割，左右分别取配置项和值
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    System.out.println(key + " → " + value);
                    count++;
                }
            }

            LOG.info("解析完成，共输出 " + count + " 条配置");

        } catch (FileNotFoundException e) {
            LOG.error("配置文件不存在: " + path, e);
        } catch (IOException e) {
            LOG.error("读取配置文件时发生 I/O 错误", e);
        }
    }
}