package xyz.ctrl.data;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.data.StudentSvc;

@Controller
@RequestMapping(value="/StudentWS")
public class StudentWS {

	
	@Autowired
	StudentSvc studentSvc;
	
	@RequestMapping(value="queryStudentList")
	@ResponseBody
	public Map<String, Object> queryStudentList(
			int page,
			int rows){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return studentSvc.queryStudentList(offset,pagesize);
	}
	
	@RequestMapping(value="addStudent")
	@ResponseBody
	public Map<String, Object> addStudent(
			String name,
			String phone,
			String idCard){
		return studentSvc.addStudent(name,phone,idCard);
	}
	
	@RequestMapping(value="editStudent")
	@ResponseBody
	public Map<String, Object> editStudent(
			String numberCode,
			String name,
			String phone,
			String idCard){
		return studentSvc.editStudent(numberCode,name,phone,idCard);
	}
	
	@RequestMapping(value="deleteStudent")
	@ResponseBody
	public Map<String, Object> deleteStudent(
			String numberCode){
		return studentSvc.deleteStudent(numberCode);
	}
	
	
	
	
	
}
