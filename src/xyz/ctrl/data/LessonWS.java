package xyz.ctrl.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.data.LessonSvc;

@Controller
@RequestMapping(value="/LessonWS")
public class LessonWS {

	@Autowired
	LessonSvc lessonSvc;
	
	@RequestMapping(value="queryLessonList")
	@ResponseBody
	public Map<String, Object> queryLessonList(
			int page,
			int rows){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return lessonSvc.queryLessonList(offset,pagesize);
	}
	
	@RequestMapping(value="addLesson")
	@ResponseBody
	public Map<String, Object> addLesson(
			String year,
			String type,
			String name,
			BigDecimal price,
			Date startDate,
			Date endDate,
			String teachType,
			int flagRefund,
			String remark){
		return lessonSvc.addLesson(year,type,name,price,startDate,endDate,teachType,flagRefund,remark);
	}
	
	@RequestMapping(value="editLesson")
	@ResponseBody
	public Map<String, Object> editLesson(
			String numberCode,
			String year,
			String type,
			String name,
			BigDecimal price,
			Date startDate,
			Date endDate,
			String teachType,
			int flagRefund,
			String remark){
		return lessonSvc.editLesson(numberCode,year,type,name,price,startDate,endDate,teachType,flagRefund,remark);
	}
	
	@RequestMapping(value="deleteLesson")
	@ResponseBody
	public Map<String, Object> deleteLesson(
			String numberCode){
		return lessonSvc.deleteLesson(numberCode);
	}
}
