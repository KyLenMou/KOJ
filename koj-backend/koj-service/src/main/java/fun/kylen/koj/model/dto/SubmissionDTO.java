package fun.kylen.koj.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class SubmissionDTO implements Serializable {
    /**
     * 题目id
     */
    @NotNull(message = "题目id不能为空")
    private Long problemId;

    /**
     * 代码语言
     */
    @NotBlank(message = "代码语言不能为空")
    private String language;

    /**
     * 代码
     */
    @NotBlank(message = "代码不能为空")
    private String code;
}