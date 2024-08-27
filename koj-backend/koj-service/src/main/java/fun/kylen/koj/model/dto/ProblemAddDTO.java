package fun.kylen.koj.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.ProblemCase;
import fun.kylen.koj.domain.Tag;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ProblemAddDTO {

    @Valid
    private Problem problem;

    @NotNull(message = "必须选择是否上传文件")
    private Boolean isFileUpload;

    private List<ProblemCase> testCases;

    private String uploadTestcaseDir;

    private List<Tag> tags;
}