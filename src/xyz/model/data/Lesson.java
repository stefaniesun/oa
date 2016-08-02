package xyz.model.data;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="lesson")
public class Lesson {

	public static final String TEACH_TYPE_DAY="DAY";//走读
	public static final String TEACH_TYPE_NIGHT="NIGHT";//住宿包住
	public static final String TEACH_TYPE_EAT_NIGHT="EAT_NIGHT";//住宿包吃包住
	
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "identity")
	private int iidd;//主键
	
	@Column(name="number_code",unique=true)
	private String numberCode;
	
	@Column(name="year")
	private String year;//年份
	
	@Column(name="type")
	private String type;//类型  0笔试/1面试
	
	@Column(name="name")
	private String name;//名称
	
	@Column(name="price")
	private BigDecimal price;//价格
	
	@Column(name="date_info")
	private Date dateInfo;//上课时间
	
	@Column(name="teach_type")
	private String teachType;//授课类型  
	
	@Column(name="flag_refund")
	private int flagRefund;//是否退费 
	
	@Column(name="remark")
	private String remark;//说明
	
	@Column(name="add_date")
	private Date addDate;//添加时间

	public int getIidd() {
		return iidd;
	}

	public void setIidd(int iidd) {
		this.iidd = iidd;
	}

	public String getNumberCode() {
		return numberCode;
	}

	public void setNumberCode(String numberCode) {
		this.numberCode = numberCode;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getDateInfo() {
		return dateInfo;
	}

	public void setDateInfo(Date dateInfo) {
		this.dateInfo = dateInfo;
	}

	public String getTeachType() {
		return teachType;
	}

	public void setTeachType(String teachType) {
		this.teachType = teachType;
	}

	public int getFlagRefund() {
		return flagRefund;
	}

	public void setFlagRefund(int flagRefund) {
		this.flagRefund = flagRefund;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
