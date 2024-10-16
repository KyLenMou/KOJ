package fun.kylen.koj.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName problem
 */
@Data
public class ProblemsetVO implements Serializable {
    private Long problemId;

    private String problemDisplayId;

    private String title;

    private String problemType;

    private String problemSource;

    private Boolean isRemote;

    private Integer difficulty;

    private List<TagVO> tags;
}