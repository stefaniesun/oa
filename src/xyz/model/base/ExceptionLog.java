package xyz.model.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import xyz.util.SysPropertyTool;

@Entity
@Table(name="exception_log")
public class ExceptionLog {
	
	
	public static final String TYPE_PAY_STOCK ="STOCK";//支付后出现库存不足的异常
	public static final String TYPE_ERP ="ERP";//ERP订单自动推送异常
	public static final String TYPE_PMS ="PMS";//PMS订单同步异常
	public static final String TYPE_TPS ="TPS";//TPS订单下行同步异常
	
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String iidd;//
	
	@Column(name="number_code",unique=true,nullable=false)
	private String numberCode;
	
	@Column(name="order_num")
	private String orderNum;
	
	@Column(name="add_date")
	private Date addDate;
	
	@Column(name="content")
	private String content;
	
	@Column(name="type")
	private String type;
	
	@Column(name="handle_flag")
	private int handleFlag;//处理标识 0未处理  1已处理
	
	@Transient
	private String productName;

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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getHandleFlag() {
		return handleFlag;
	}

	public void setHandleFlag(int handleFlag) {
		this.handleFlag = handleFlag;
	}

}
