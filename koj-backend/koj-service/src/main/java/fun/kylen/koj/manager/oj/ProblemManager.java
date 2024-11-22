package fun.kylen.koj.manager.oj;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.ProblemTagEntityService;
import fun.kylen.koj.es.ProblemEsDTO;
import fun.kylen.koj.model.oj.vo.ProblemDetailVO;
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
    public Page<ProblemsetVO> listProblemsetVOByPage(Integer current, Integer pageSize) {
        if (pageSize >= 100) {
            throw new BusinessException(ResultEnum.FAIL, "请不要爬取数据哦 :) ");
        }
        return problemEntityService.listProblemsetVOByPage(new Page<>(current, pageSize));
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
                problemsetVO.setProblemId(problemEsDTO.getId());
                problemsetVO.setDisplayId(problemEsDTO.getProblemDisplayId());
                problemsetVO.setTitle(problemEsDTO.getTitle());
                resourceList.add(problemsetVO);
            }
        }
        page.setRecords(resourceList);
        return page;
    }
}

















