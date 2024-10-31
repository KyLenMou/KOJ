package fun.kylen.koj.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author: KyLen
 * @Date: 2024/10/30 15:14
 * @Description:
 */
public interface ProblemEsDao extends ElasticsearchRepository<ProblemEsDTO, Long> {

}
