package com.gls.job.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * file tool
 *
 * @author xuxueli 2017-12-29 17:56:48
 */
@Slf4j
public class FileUtil {

    public static void deleteFile(String fileName) {
        // file
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    public static List<String> findFiles(String filePath) {
        log.info("filePath:{}", filePath);
        File[] files = new File(filePath).listFiles();
        if (files != null) {

            return Arrays.stream(files).map(file -> {
                try {
                    return file.getCanonicalPath();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
                return null;
            }).filter(StringUtils::hasText).collect(Collectors.toList());
        }
        return Collections.emptyList();

    }

    public static byte[] readFile(String fileName) {
        File file = new File(fileName);
        byte[] data = new byte[Math.toIntExact(file.length())];
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            inputStream.read(data);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return data;
    }

}
