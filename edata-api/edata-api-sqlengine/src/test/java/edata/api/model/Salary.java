package edata.api.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import java.time.LocalDateTime;

/**
 * Salary
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/1
 */
@Table(name = "T_SALARY")
@Data
public class Salary {
    @AutoID
    private int id;

    private String name;
    private String dept;
    private Double salary;
    private int ed_level;
    private LocalDateTime hire_date;
}
