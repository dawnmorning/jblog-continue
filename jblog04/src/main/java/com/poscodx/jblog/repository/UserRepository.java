package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.UserVo;

@Repository
public class UserRepository {
	// Sql session이 필요하다. 그러므로 pom.xml에 마이바티스 의존성을 추가하고, applicationContext.xml에도 관련해서 추가해주어야 한다. 
	@Autowired
	private SqlSession sqlSession;
	public boolean insert(UserVo userVo) {
		int count = sqlSession.insert("user.insert", userVo);
		return count == 1;
	}
	public UserVo findByIdAndPassword(String id, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("password", password);
		
		return sqlSession.selectOne("user.findByIdAndPassword", map);
	}
	public int findById(String id) {
	    return sqlSession.selectOne("user.findById", id);
	}

}
