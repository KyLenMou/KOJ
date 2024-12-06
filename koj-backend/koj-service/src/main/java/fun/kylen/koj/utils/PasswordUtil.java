package fun.kylen.koj.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;

/**
 * @Author: KyLen
 * @Date: 2024/7/19 下午10:26
 * @Description:
 */
public class PasswordUtil {
    /**
     * 密码加盐加密
     * @param originPassword 原密码
     * @return
     * @description 生成32位uuid作为盐，和原始密码拼接后进行md5加密，返回32位盐+加密后的密码
     */
    public static String encrypt(String originPassword) {
        // 32位盐
        String salt = IdUtil.simpleUUID();
        // 拼接
        originPassword = salt + originPassword;
        // md5加密
        String encryptedPassword = DigestUtil.md5Hex(originPassword);
        // 返回32位盐+加密后的密码
        return salt + encryptedPassword;
    }

    /**
     * 校验密码是否正确
     * @param originPassword 原密码
     * @param encryptedPassword 加密后的密码
     * @return
     */
    public static boolean check(String originPassword, String encryptedPassword) {
        // 取出盐
        String salt = StrUtil.sub(encryptedPassword,0,32);
        // 真密码
        encryptedPassword = StrUtil.sub(encryptedPassword,32, encryptedPassword.length());
        // 拼接并加密
        originPassword = DigestUtil.md5Hex(salt + originPassword);
        // 判断md5加密后的拼接密码是否与真密码相同
        return originPassword.equals(encryptedPassword);
    }
}
