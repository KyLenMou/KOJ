package fun.kylen.koj.utils;

import fun.kylen.koj.config.JudgeAsyncConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: KyLen
 * @Date: 2024/12/5 09:14
 * @Description:
 */
public class LogUtil {

    private static final Logger log = LoggerFactory.getLogger(LogUtil.class);
    public static void info(String message) {
        log.info(message);
    }

    public static void error(String message) {
        log.error(message);
    }
}
