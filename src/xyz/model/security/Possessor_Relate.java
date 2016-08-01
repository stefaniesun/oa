/*******************************************************************************
** File Name   ：Possessor_Relate.java
** Create Date ：2016-7-18
** Author      ：xing xiaojie
** Version     ：1.0.0
** CopyRight   ：duanyi.com.cn
**
** Modified Date      Modified By      Why
** xxxx-xx-xx         xxx              xxxxx
** ... ...
********************************************************************************/

package xyz.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author xing xiaojie
 * @version 2016-7-18上午10:37:39
 */

/*
 *  机构 与 岗位的关系表
 * */
@Entity
@Table(name="possessor_relate",uniqueConstraints = {@UniqueConstraint(columnNames={"possessor","type","relate"})})
public class Possessor_Relate{
    
    @Id
    @Column(name="iidd",unique=true,nullable=false)
    @GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String iidd;//主键
    
    @Column(name="possessor")
    private String possessor;//机构
    
    @Column(name="type")
    private String type;  // channel\provider\tkview\position
    
    @Column(name="relate")
    private String relate; // 资源

    public String getIidd() {
        return iidd;
    }

    public void setIidd(String iidd) {
        this.iidd = iidd;
    }

    public String getPossessor() {
        return possessor;
    }

    public void setPossessor(String possessor) {
        this.possessor = possessor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelate() {
        return relate;
    }

    public void setRelate(String relate) {
        this.relate = relate;
    }
}
