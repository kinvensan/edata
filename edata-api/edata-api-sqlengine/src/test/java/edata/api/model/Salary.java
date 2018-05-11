package edata.api.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.beetl.sql.core.annotatoin.AutoID;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Salary
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/1
 */
@Table(name = "t_salary")
@Data
public class Salary {
    @AutoID
    @Column(name="id")
    private int id;
    @Column(name="name")
    @JoinColumn(name = "name")
    private String name;
    @Column(name = "dept")
    private String dept;
    @Column(name = "salary")
    private Double salary;
    @Column(name = "ed_level")
    private int ed_level;
    @Column(name = "hire_date")
    private LocalDateTime hire_date;
}
