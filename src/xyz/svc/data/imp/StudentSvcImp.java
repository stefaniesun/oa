package xyz.svc.data.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.data.Student;
import xyz.svc.data.StudentSvc;
import xyz.util.StringUtil;

@Service
public class StudentSvcImp implements StudentSvc{

	@Resource
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> queryStudentList(int offset, int pagesize) {

		String hql=" from Student where 1=1 ";
		/*if(nameCn!=null&&!"".equals(nameCn)){
			hql+=" and nameCn like '%"+nameCn+"%' ";
		}*/
		String countHql = "select count(numberCode) "+hql;
		Query countQuery = commonDao.getQuery(countHql);
		Number countTemp = (Number)countQuery.uniqueResult();
		int count = countTemp==null?0:countTemp.intValue();
		
		Query query = commonDao.getQuery(hql);
		query.setMaxResults(pagesize);
		query.setFirstResult(offset);
		@SuppressWarnings("unchecked")
		List<Student> students=query.list();
		
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", count);
		mapContent.put("rows",students);
		return ReturnUtil.returnMap(1, mapContent);
	
	}

	@Override
	public Map<String, Object> addStudent(String name, String phone,
			String idCard) {
		
		Student student=new Student();
		
		student.setNumberCode(StringUtil.get_new_numberCode());
		student.setName(name);
		student.setPhone(phone);
		student.setIdCard(idCard);
		student.setAddDate(new Date());
		
		commonDao.save(student);
		
		return ReturnUtil.returnMap(1, null);
	}

	@Override
	public Map<String, Object> editStudent(String numberCode, String name,
			String phone, String idCard) {
		
		Student student=(Student) commonDao.getObjectByUniqueCode("Student", "numberCode", numberCode);
		if(student==null){
			return ReturnUtil.returnMap(0, "学员不存在");
		}
		
		student.setName(name);
		student.setPhone(phone);
		student.setIdCard(idCard);
		
		commonDao.update(student);
		
		return ReturnUtil.returnMap(1, null);
	}

	@Override
	public Map<String, Object> deleteStudent(String numberCode) {
		
		Student student=(Student) commonDao.getObjectByUniqueCode("Student", "numberCode", numberCode);
		if(student==null){
			return ReturnUtil.returnMap(0, "学员不存在");
		}
		commonDao.delete(student);
		return ReturnUtil.returnMap(1, null);
	}

}
