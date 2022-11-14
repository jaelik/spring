package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vo.DeptVO;

@Repository("dept_dao")  //setter 나 생성자 인젝션을 사용할수 없기에 별칭을 달아준다.
//servlet 클래스에서도 관리가 가능하게 하는 어노테이션을 달아주면  servlet에서 객체생성을 해준다 
public class DeptDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	public DeptDAO() {
		System.out.println("----DeptDAO의 생성자----");
	}
	
	//부서 목록 조회 
	public List<DeptVO> selectList(){
		List<DeptVO> list = sqlSession.selectList("d.dept_list");
		return list;
	}

}
