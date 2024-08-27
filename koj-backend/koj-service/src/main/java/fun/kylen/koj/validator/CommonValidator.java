package fun.kylen.koj.validator;

import cn.hutool.core.util.StrUtil;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import org.springframework.stereotype.Component;

@Component
public class CommonValidator {

    public void validateContent(String content, String item, int minLength, int maxLength) {
        if (StrUtil.isBlank(content) && minLength > 0) {
            throw new BusinessException(ResultEnum.FAIL, item + "的内容不能为空！");
        }
        if (content.length() < minLength) {
            throw new BusinessException(ResultEnum.FAIL, item + "的内容长度需大于" + minLength + "个字符！");
        }
        if (content.length() > maxLength) {
            throw new BusinessException(ResultEnum.FAIL, item + "的内容长度超过限制，请重新编辑！");
        }
    }

    public void validateNotEmpty(Object value, String item) {
        if (value == null) {
            throw new BusinessException(ResultEnum.FAIL, item + "不能为空");
        }
        if (value instanceof String){
            if (StrUtil.isBlank((String)value)){
                throw new BusinessException(ResultEnum.FAIL, item + "不能为空");
            }
        }
    }
}
