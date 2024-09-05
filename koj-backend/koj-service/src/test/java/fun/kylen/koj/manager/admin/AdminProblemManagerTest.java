package fun.kylen.koj.manager.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.dao.ProblemEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: KyLen
 * @Date: 2024/9/5 00:45
 * @Description:
 */
@SpringBootTest
class AdminProblemManagerTest {
    @Autowired
    private ProblemEntityService problemEntityService;

    @Test
    void listProblemsetVOByPage() {
        System.out.println(problemEntityService.page(new Page<>(1, 10)));
    }
}