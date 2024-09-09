package fun.kylen.koj.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SubmissionDTO implements Serializable {
    /**
     * 题目id
     */
    private Long problemId;

    /**
     * 题目展示id
     */
    private String problemDisplayId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 代码语言
     */
    private String language;

    /**
     * 代码
     */
    private String code;
}