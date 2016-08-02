package xyz.svc.data;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface LessonSvc {

	public Map<String, Object> queryLessonList(int offset, int pagesize);

	public Map<String, Object> addLesson(String name, String phone,
			String idCard);

	public Map<String, Object> editLesson(String numberCode, String name,
			String phone, String idCard);

	public Map<String, Object> deleteLesson(String numberCode);

}
