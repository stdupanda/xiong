package cn.mbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Run {
    private static final Logger log = LoggerFactory.getLogger(Run.class);

    public static void main(String[] args) throws Exception {
        log.info("begin");
        String fileName = "generatorConfig.xml";
        List<String> warnings = new ArrayList<>();
        InputStream resourceAsStream = Run.class.getClassLoader().getResourceAsStream(fileName);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(resourceAsStream);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        log.info("end.");
    }
}
