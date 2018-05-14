package com.github.edata.api.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * Sex
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/10
 */
@Data
@Table(name = "t_sex")
public class Sex {
    @Column(name = "name")
    @JoinColumn(name = "name")
    private String name;
    @Column(name = "sex")
    private int sex;
}
