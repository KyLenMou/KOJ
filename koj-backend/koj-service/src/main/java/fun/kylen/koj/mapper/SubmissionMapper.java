package fun.kylen.koj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.model.vo.SubmissionListVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author KyLen
 * @description 针对表【submission】的数据库操作Mapper
 * @createDate 2024-09-05 21:51:51
 * @Entity fun.kylen.koj.domain.Submission
 */
public interface SubmissionMapper extends BaseMapper<Submission> {

    Page<SubmissionListVO> listSubmissionByPage(Page<SubmissionListVO> page,
                                                @Param("problemId") Long problemId,
                                                @Param("problemDisplayId") String problemDisplayId,
                                                @Param("userId") String userId,
                                                @Param("username") String username,
                                                @Param("language") String language);

}




