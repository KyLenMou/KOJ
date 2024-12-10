package fun.kylen.koj.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/11/30 09:23
 * @Description:
 */
@Data
public class DebugVO implements Serializable {
    private String debugId;
    private String userId;
    private String language;
    private String code;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Integer stackLimit;
    private String judgeMode;
    private List<Integer> verdict;
    private List<Integer> runTime;
    private List<Integer> runMemory;
    private List<String> message;
    private List<String> userInputList;
    private List<String> userOutputList;
    private List<String> expectedOutputList;
    private static final long serialVersionUID = 8970706086615426441L;
}
