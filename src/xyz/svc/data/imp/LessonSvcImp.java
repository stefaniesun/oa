package xyz.svc.data.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.data.Lesson;
import xyz.model.data.Student;
import xyz.svc.data.LessonSvc;

@Service
public class LessonSvcImp implements LessonSvc {

	@Resource
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> queryLessonList(int offset, int pagesize) {

		String hql=" from Lesson where 1=1 ";
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
		List<Lesson> lessons=query.list();
		
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", count);
		mapContent.put("rows",lessons);
		return ReturnUtil.returnMap(1, mapContent);
	
	}

	@Override
	public Map<String, Object> addLesson(String name, String phone,
			String idCard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> editLesson(String numberCode, String name,
			String phone, String idCard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteLesson(String numberCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
