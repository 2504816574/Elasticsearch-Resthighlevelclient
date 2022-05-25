package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sdong
 * @date 2022/5/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private String sex;
    private Integer age;


}
