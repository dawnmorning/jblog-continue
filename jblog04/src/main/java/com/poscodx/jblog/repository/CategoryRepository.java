package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	@Autowired
	private SqlSession sqlSession;

	public List<CategoryVo> getCategoriesById(String blogId) {
		return sqlSession.selectList("category.getCategoriesById", blogId);
	}

	public long add(CategoryVo categoryVo) {
	    sqlSession.insert("category.add", categoryVo);
	    return categoryVo.getNo();
	}

	public boolean delete(Long categoryNo) {
		int count = sqlSession.delete("category.delete", categoryNo);
		return count == 1;
	}


}
