/*******************************************************************************
** File Name   ：Possessor.java
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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author xing xiaojie
 * @version 2016-7-18上午10:31:33
 */

@Entity
@Table(name="possessor")
public class Possessor{
    @Id
    @Column(name="iidd",unique=true,nullable=false)
    @GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String iidd;//主键
    
    @Column(name="number_code",unique=true,nullable=false)
    private String numberCode;//编号
    
    @Column(name="name_cn",unique=true,nullable=false)
    private String nameCn;//名称
    
    @Column(name="add_date")
    private Date addDate;//添加日期
    
    @Column(name="alter_date")
    private Date alterDate;//修改时间
    
    @Column(name="remark",length=5000)
    private String remark;//备注

    public String getIidd() {
        return iidd;
    }

    public void setIidd(String iidd) {
        this.iidd = iidd;
    }

    public String getNumberCode() {
        return numberCode;
    }

    public void setNumberCode(String numberCode) {
        this.numberCode = numberCode;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getAlterDate() {
        return alterDate;
    }

    public void setAlterDate(Date alterDate) {
        this.alterDate = alterDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
