package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;

	public BlogVo getBlogById(String blogId) {
		return sqlSession.selectOne("blog.getBlogById", blogId);
	}

	public List<CategoryVo> getCategoriesById(String blogId) {
		return sqlSession.selectList("blog.getCategoriesById", blogId);
	}

	public List<PostVo> getPostByCategory(Long no) {
		return sqlSession.selectList("blog.getPostByCategory", no);
	}

	public void add(BlogVo blogVo) {
		sqlSession.insert("blog.add", blogVo);
	}

	public void update(BlogVo blogVo) {
		sqlSession.update("blog.update", blogVo);

	}

}
