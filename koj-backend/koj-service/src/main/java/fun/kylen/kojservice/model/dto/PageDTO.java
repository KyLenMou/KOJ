package fun.kylen.kojservice.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: KyLen
 * @Date: 2024/7/19 下午9:20
 * @Description:
 */
@Data
public class PageDTO {
    /**
     * 当前页号
     */
    @NotNull(message = "页号不能为空")
    private Integer current = 1;

    /**
     * 页面大小
     */
    @NotNull(message = "页面大小不能为空")
    private Integer pageSize = 10;

}
