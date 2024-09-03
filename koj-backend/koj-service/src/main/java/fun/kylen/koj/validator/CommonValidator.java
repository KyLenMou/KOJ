package fun.kylen.koj.validator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommonValidator {
    /**
     * 校验字符串，长度在minLength和maxLength之间，minLength等于0时允许为空
     * @param content
     * @param item
     * @param minLength
     * @param maxLength
     */
    public void validateContent(String content, String item, int minLength, int maxLength) {
        if (StrUtil.isBlank(content) && minLength > 0) {
            throw new BusinessException(ResultEnum.FAIL, item + "的内容不能为空！");
        }
        if (content.length() < minLength) {
            throw new BusinessException(ResultEnum.FAIL, item + "的内容长度需大于" + minLength + "个字符！");
        }
        if (content.length() > maxLength) {
            throw new BusinessException(ResultEnum.FAIL, item + "的内容长度超过了" + maxLength + "个字符，请重新编辑！");
        }
    }
    public void validateNotNull(Object object, String item) {
        if (object == null || ObjUtil.isNull(object)) {
            throw new BusinessException(ResultEnum.FAIL, item + "不能为空");
        }
    }
    public void validateNull(Object object, String item) {
        if (object != null) {
            throw new BusinessException(ResultEnum.FAIL, item + "已存在");
        }
    }

    public void validateNumber(Integer number , String item, int min, int max) {
        if (number == null) {
            throw new BusinessException(ResultEnum.FAIL, item + "不能为空");
        }
        if (number < min) {
            throw new BusinessException(ResultEnum.FAIL, item + "不能小于" + min);
        }
        if (number > max) {
            throw new BusinessException(ResultEnum.FAIL, item + "不能大于" + max);
        }
    }

    // 校验是否为正整数
    public void validatePositive(Integer number, String item) {
        if (number == null) {
            throw new BusinessException(ResultEnum.FAIL, item + "不能为空");
        }
        if (number <= 0) {
            throw new BusinessException(ResultEnum.FAIL, item + "必须为正整数");
        }
    }

    public void validateStrings(String source, String item, String... strings) {
        for (String string : strings) {
            if (source.equals(string)) {
                return;
            }
        }
        throw new BusinessException(ResultEnum.FAIL, item + "不合法");
    }

    public void validateNotEmpty(Object value, String item) {
        if (ObjUtil.isEmpty(value)) {
            throw new BusinessException(ResultEnum.FAIL, item + "不能为空");
        }
    }
}
