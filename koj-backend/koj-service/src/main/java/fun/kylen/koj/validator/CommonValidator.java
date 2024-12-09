package fun.kylen.koj.validator;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import org.springframework.stereotype.Component;

@Component
public class CommonValidator {
    /**
     * 校验字符串，长度在minLength和maxLength之间，minLength等于0时允许为空
     *
     * @param content
     * @param item
     * @param minLength
     * @param maxLength
     */
    public void between(String content, String item, int minLength, int maxLength) {
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

    /**
     * object不能为空
     * @param object
     * @param item
     */
    public void cannotBeNull(Object object, String item) {
        if (object == null || ObjUtil.isNull(object)) {
            throw new BusinessException(ResultEnum.FAIL, item + "不能为空");
        }
    }

    /**
     * object必须为空
     * @param object
     * @param item
     */
    public void mustBeNull(Object object, String item) {
        if (object != null) {
            throw new BusinessException(ResultEnum.FAIL, item + "已存在");
        }
    }

    /**
     * number必须在min和max之间
     * @param number
     * @param item
     * @param min
     * @param max
     */
    public void between(Integer number, String item, int min, int max) {
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

    /**
     * source必须在strings中
     * @param source
     * @param item
     * @param strings
     */
    public void mustIn(String source, String item, String... strings) {
        for (String string : strings) {
            if (source.equals(string)) {
                return;
            }
        }
        throw new BusinessException(ResultEnum.FAIL, item + "不合法");
    }

    /**
     * value不能为空
     * @param value
     * @param item
     */
    public void cannotBeEmpty(Object value, String item) {
        if (ObjUtil.isEmpty(value)) {
            throw new BusinessException(ResultEnum.FAIL, item + "不能为空");
        }
    }

    /**
     * i1和i2必须相等（数字）
     * @param i1
     * @param i2
     * @param item
     */
    public void mustBeSame(int i1, int i2, String item) {
        if (i1 != i2) {
            throw new BusinessException(ResultEnum.FAIL, item + "不一致");
        }
    }
}
