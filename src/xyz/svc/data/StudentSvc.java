package xyz.svc.data;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface StudentSvc {

	public Map<String, Object> queryStudentList(int offset, int pagesize);

	public Map<String, Object> addStudent(String name, String phone,
			String idCard);

	public Map<String, Object> editStudent(String numberCode, String name,
			String phone, String idCard);

	public Map<String, Object> deleteStudent(String numberCode);

}
