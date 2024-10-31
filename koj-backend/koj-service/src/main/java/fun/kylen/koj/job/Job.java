package fun.kylen.koj.job;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName job
 */
@TableName(value ="job")
@Data
public class Job implements Serializable {
    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}