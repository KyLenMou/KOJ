package fun.kylen.koj.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/10/30 15:10
 * @Description:
 */
@Document(indexName = "problem")
@Data
public class ProblemEsDTO implements Serializable {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * ID
     */
    @Id
    private Long id;

    /**
     * 题目展示 ID
     */
    @Field(type = FieldType.Keyword)
    private String problemDisplayId;

    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    /**
     * 描述文本
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String descriptionText;

    /**
     * 备注文本
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String noteText;

    /**
     * 输入格式
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String input;

    /**
     * 输出格式
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String output;

    /**
     * 标签列表
     */
    @Field(type = FieldType.Keyword)
    private List<String> tags;

    /**
     * 答案
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String answer;

    /**
     * 作者用户 ID
     */
    @Field(type = FieldType.Keyword)
    private String authorUserId;

    /**
     * 作者用户名
     */
    @Field(type = FieldType.Keyword)
    private String authorUsername;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private Date createTime;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private Date updateTime;

    /**
     * 是否删除
     */
    @Field(type = FieldType.Keyword)
    private String isDelete;

    private static final long serialVersionUID = 1L;
}