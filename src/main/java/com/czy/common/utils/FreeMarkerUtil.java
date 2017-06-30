package com.czy.common.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Comup on 2017-04-17.
 * FreeMarker工具
 */
public class FreeMarkerUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreeMarkerUtil.class);

    private static final Configuration CONFIGURATION = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    private static Map<String, Template> TEMPLATE_MAPS;

    private static final StringWriter STRING_WRITER = new StringWriter();

    private static final FreeMarkerUtil instance = new FreeMarkerUtil();

    private FreeMarkerUtil() {
        init();
    }

    public static FreeMarkerUtil getInstance() {
        return instance;
    }

    /**
     * 用数据填充模板
     *
     * @param airportCode  机场三字码
     * @param templateName 模板名
     * @param data         数据
     * @return 填充后的字符串
     */
    public synchronized String process(String airportCode, String templateName, Map<String, Object> data) throws IOException, TemplateException {
        if (TEMPLATE_MAPS.containsKey(airportCode.toUpperCase() + "|" + templateName)) {
            STRING_WRITER.getBuffer().delete(0, STRING_WRITER.getBuffer().length());
            TEMPLATE_MAPS.get(airportCode.toUpperCase() + "|" + templateName).process(data, STRING_WRITER);
            STRING_WRITER.flush();
            return STRING_WRITER.toString();
        } else {
            throw new IllegalArgumentException(templateName + " not found");
        }
    }

    private void init() {
        TEMPLATE_MAPS = Collections.synchronizedMap(new HashMap<>());
        try {
            //转码，以防止路径内有空格
            String path = URLDecoder.decode(this.getClass().getResource("/templates").getFile(), StandardCharsets.UTF_8.displayName());
            File file = new File(path);
            CONFIGURATION.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
            findFtl(file);
        } catch (Throwable t) {
            LOGGER.error("templates init error", t);
            throw new Error(t);
        }
    }

    private void findFtl(File file) {
        if (file == null) {
            throw new IllegalArgumentException("ftl directory error");
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File fileConsumer : files) {
                    if (fileConsumer.isDirectory()) {
                        findFtl(fileConsumer);
                    } else {
                        try {
                            CONFIGURATION.setDirectoryForTemplateLoading(fileConsumer.getParentFile());
                            TEMPLATE_MAPS.put(fileConsumer.getParentFile().getName().toUpperCase() + "|" + fileConsumer.getName().replaceAll(".ftl", ""), CONFIGURATION.getTemplate(fileConsumer.getName()));
                        } catch (Exception e) {
                            LOGGER.error("get template error the file is " + file.getAbsolutePath(), e);
                        }
                    }
                }
            }
        } else {
            try {
                CONFIGURATION.setDirectoryForTemplateLoading(file.getParentFile());
                TEMPLATE_MAPS.put(file.getName().replaceAll(".ftl", ""), CONFIGURATION.getTemplate(file.getName()));
            } catch (Exception e) {
                LOGGER.error("get template error the file is " + file.getAbsolutePath(), e);
            }
        }
    }
}
