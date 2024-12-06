package fun.kylen.koj.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @Author: Himit_ZH
 * @Date: 2021/11/24 19:16
 * @Description:
 */
public class JudgeUtil {

    private final static Pattern EOL_PATTERN = Pattern.compile("[^\\S\\n]+(?=\\n)");

    public static List<String> getArgs(String toProcess) {
        if (toProcess != null && !toProcess.isEmpty()) {
            int state = 0;
            StringTokenizer tok = new StringTokenizer(toProcess, "\"' ", true);
            List<String> result = new ArrayList<>();
            StringBuilder current = new StringBuilder();
            boolean lastTokenHasBeenQuoted = false;

            while (true) {
                while (tok.hasMoreTokens()) {
                    String nextTok = tok.nextToken();
                    switch (state) {
                        case 1:
                            if ("'".equals(nextTok)) {
                                lastTokenHasBeenQuoted = true;
                                state = 0;
                            } else {
                                current.append(nextTok);
                            }
                            continue;
                        case 2:
                            if ("\"".equals(nextTok)) {
                                lastTokenHasBeenQuoted = true;
                                state = 0;
                            } else {
                                current.append(nextTok);
                            }
                            continue;
                    }

                    if ("'".equals(nextTok)) {
                        state = 1;
                    } else if ("\"".equals(nextTok)) {
                        state = 2;
                    } else if (" ".equals(nextTok)) {
                        if (lastTokenHasBeenQuoted || current.length() > 0) {
                            result.add(current.toString());
                            current.setLength(0);
                        }
                    } else {
                        current.append(nextTok);
                    }

                    lastTokenHasBeenQuoted = false;
                }

                if (lastTokenHasBeenQuoted || current.length() > 0) {
                    result.add(current.toString());
                }

                if (state != 1 && state != 2) {
                    return result;
                }

                throw new RuntimeException("unbalanced quotes in " + toProcess);
            }
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 使用md5进行答案比对
     *
     * @param answer
     * @param output
     * @return
     */
    public static boolean check(String answer, String output) {
        String ans = DigestUtil.md5Hex(removeBlankAndEOL(answer));
        String out = DigestUtil.md5Hex(removeBlankAndEOL(output));
        return StrUtil.equals(ans, out);
    }

    /**
     * 去除value中的末尾空白符和换行符前的空格
     *
     * @param value
     * @return
     */
    public static String removeBlankAndEOL(String value) {
        if (value == null) return null;
        return EOL_PATTERN.matcher(StrUtil.trimEnd(value)).replaceAll("");
    }
}