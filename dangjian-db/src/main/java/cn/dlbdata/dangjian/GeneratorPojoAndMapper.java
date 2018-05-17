package cn.dlbdata.dangjian;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.cglib.beans.BeanMap.Generator;

public class GeneratorPojoAndMapper {

	public static void main(String[] args) {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config;
		try {
			config = cp.parseConfiguration(Generator.class.getResourceAsStream("/generatorConfig.xml"));
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
