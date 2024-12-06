package fun.kylen.koj.manager.oj;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.ProblemTagEntityService;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.ProblemTag;
import fun.kylen.koj.es.ProblemEsDTO;
import fun.kylen.koj.model.oj.vo.ProblemDetailVO;
import fun.kylen.koj.model.oj.vo.ProblemInfoCardVO;
import fun.kylen.koj.model.oj.vo.ProblemsetVO;
import fun.kylen.koj.validator.ProblemValidator;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: KyLen
 * @Date: 2024/8/27 23:12
 * @Description:
 */
@Component
public class ProblemManager {
    @Autowired
    private ProblemEntityService problemEntityService;
    @Autowired
    private ProblemCaseEntityService problemCaseEntityService;
    @Autowired
    private ProblemTagEntityService problemTagEntityService;
    @Autowired
    private ProblemValidator problemValidator;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public Page<ProblemsetVO> listProblemsetVOByPage(Integer current, Integer pageSize, String searchText) {
        if (pageSize >= 100) {
            throw new BusinessException(ResultEnum.FAIL, "请不要爬取数据哦 :) ");
        }
        Page<Problem> problemPage = problemEntityService.lambdaQuery()
                .like(StrUtil.isNotBlank(searchText),Problem::getTitle, searchText)
                .like(StrUtil.isNotBlank(searchText),Problem::getDescription, searchText)
                .like(StrUtil.isNotBlank(searchText),Problem::getNote, searchText)
                .like(StrUtil.isNotBlank(searchText),Problem::getDisplayId, searchText)
                .like(StrUtil.isNotBlank(searchText),Problem::getId, searchText)
                .orderByDesc(Problem::getCreateTime)
                .page(new Page<>(current, pageSize));
        // 封装成ProblemsetVO
        Page<ProblemsetVO> problemsetVOPage = new Page<>();
        problemsetVOPage.setTotal(problemPage.getTotal());
        problemsetVOPage.setRecords(problemPage.getRecords().stream().map(problem -> {
            ProblemsetVO problemsetVO = new ProblemsetVO();
            BeanUtil.copyProperties(problem, problemsetVO);
            return problemsetVO;
        }).collect(Collectors.toList()));
        return problemsetVOPage;
    }

    public ProblemDetailVO getProblemDetailVO(String problemDisplayId) {
        ProblemDetailVO problem = problemEntityService.getProblemDetailVO(problemDisplayId);
        if (problem == null) {
            throw new BusinessException(ResultEnum.NOT_FOUND);
        }
        return problem;
    }

    public Page<ProblemsetVO> listProblemsetVOFromEs(Integer current, Integer pageSize, String searchText) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.matchQuery("title", searchText));
        boolQueryBuilder.should(QueryBuilders.matchQuery("descriptionText", searchText));
        boolQueryBuilder.should(QueryBuilders.matchQuery("noteText", searchText));
        boolQueryBuilder.should(QueryBuilders.matchQuery("problemDisplayId", searchText));
        boolQueryBuilder.should(QueryBuilders.matchQuery("input", searchText));
        boolQueryBuilder.should(QueryBuilders.matchQuery("output", searchText));
        boolQueryBuilder.should(QueryBuilders.matchQuery("tags", searchText));
        boolQueryBuilder.should(QueryBuilders.matchQuery("answer", searchText));
        boolQueryBuilder.should(QueryBuilders.matchQuery("authorUsername", searchText));

        // 分页
        PageRequest pageRequest = PageRequest.of(current, pageSize);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageRequest)
                .build();
        SearchHits<ProblemEsDTO> searchHits = elasticsearchRestTemplate.search(searchQuery, ProblemEsDTO.class);
        Page<ProblemsetVO> page = new Page<>();
        page.setTotal(searchHits.getTotalHits());
        List<ProblemsetVO> resourceList = new ArrayList<>();
        if (searchHits.hasSearchHits()) {
            List<SearchHit<ProblemEsDTO>> searchHitList = searchHits.getSearchHits();
            for (SearchHit<ProblemEsDTO> problemEsDTOSearchHit : searchHitList) {
                ProblemEsDTO problemEsDTO = problemEsDTOSearchHit.getContent();
                ProblemsetVO problemsetVO = new ProblemsetVO();
                problemsetVO.setId(problemEsDTO.getId());
                problemsetVO.setDisplayId(problemEsDTO.getProblemDisplayId());
                problemsetVO.setTitle(problemEsDTO.getTitle());
                resourceList.add(problemsetVO);
            }
        }
        page.setRecords(resourceList);
        return page;
    }

    public ProblemInfoCardVO getProblemInfoCard(String problemId) {
        ProblemInfoCardVO problemInfoCardVO = new ProblemInfoCardVO();
        Problem problem = problemEntityService.getById(problemId);
        if (problem == null) {
            throw new BusinessException(ResultEnum.NOT_FOUND, "题目不存在，请检查题目ID");
        }
        String abstractDescription = StrUtil.sub(problem.getDescription(), 0, 200);
        abstractDescription += problem.getDescription().length() > 200 ? "..." : "";

        List<Long> tagIds = problemTagEntityService.lambdaQuery()
                .eq(ProblemTag::getProblemId, problemId)
                .list()
                .stream()
                .map(ProblemTag::getTagId)
                .collect(Collectors.toList());

        problemInfoCardVO.setTitle(problem.getTitle());
        problemInfoCardVO.setDisplayId(problem.getDisplayId());
        problemInfoCardVO.setId(problem.getId());
        problemInfoCardVO.setJudgeMode(problem.getJudgeMode());
        problemInfoCardVO.setDifficulty(problem.getDifficulty());
        problemInfoCardVO.setDescription(abstractDescription);
        problemInfoCardVO.setTags(tagIds);
        Integer acceptCount = RandomUtil.randomInt(0,1000);
        Integer submitCount = RandomUtil.randomInt(acceptCount,10000);
        problemInfoCardVO.setAcceptCount(acceptCount);
        problemInfoCardVO.setSubmitCount(submitCount);
        // 保留两位小数，百分比
        if (submitCount > 0) problemInfoCardVO.setAcceptRate(String.format("%.2f", (double) acceptCount / submitCount * 100) + "%");
        else problemInfoCardVO.setAcceptRate("0%");

        return problemInfoCardVO;
    }

}

















