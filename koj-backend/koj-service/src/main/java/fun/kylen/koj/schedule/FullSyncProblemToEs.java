package fun.kylen.koj.schedule;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import fun.kylen.koj.dao.JobEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.es.ProblemEsDTO;
import fun.kylen.koj.es.ProblemEsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: KyLen
 * @Date: 2024/10/31 10:54
 * @Description:
 */
@Component
@Slf4j
public class FullSyncProblemToEs implements CommandLineRunner {
    @Resource
    private ProblemEntityService problemEntityService;

    @Resource
    private ProblemEsDao problemEsDao;

    @Autowired
    private JobEntityService jobEntityService;

    private static final String fullSyncProblemToEsKey = "fullSyncProblemToEsKey";

    @Override
    public void run(String... args) {
        // 检查是否运行过
        List<Job> jobList = jobEntityService.list();
        Map<String,Integer> jobMap = new HashMap<>();
        jobList.forEach(job -> jobMap.put(job.getType(),job.getStatus()));

        if (jobMap.getOrDefault(fullSyncProblemToEsKey,1) > 0) return;

        // 全量获取题目（数据量不大的情况下使用）
        List<Problem> problemList = problemEntityService.list();
        if (CollUtil.isEmpty(problemList)) {
            return;
        }
        // 转为 ES 实体类
        List<ProblemEsDTO> problemEsDTOList = problemList.stream()
                .map(
                        problem -> {
                            ProblemEsDTO problemEsDTO = new ProblemEsDTO();
                            BeanUtil.copyProperties(problem, problemEsDTO);
                            return problemEsDTO;
                        }
                )
                .collect(Collectors.toList());
        // 分页批量插入到 ES
        final int pageSize = 500;
        int total = problemEsDTOList.size();
        log.info("FullSyncQuestionToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            // 注意同步的数据下标不能超过总数据量
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            problemEsDao.saveAll(problemEsDTOList.subList(i, end));
        }
        log.info("FullSyncQuestionToEs end, total {}", total);
    }
}
