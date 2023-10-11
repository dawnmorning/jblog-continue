package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.PostVo;

@Repository
public class PostRepository {

	@Autowired
	private SqlSession sqlSession;

	public boolean add(PostVo postVo) {
		int count = sqlSession.insert("post.add", postVo);
		return count == 1;
	}

	public boolean delete(Long postNo) {
		int count = sqlSession.delete("post.delete", postNo);
		return count == 1;
	}

	public List<PostVo> getPostsByCategory(Long categoryNo) {
		return sqlSession.selectList("post.getPostsByCategory", categoryNo);
	}

	public PostVo getPostByPostNo(Long postNo) {
		return sqlSession.selectOne("post.getPostByPostNo", postNo);
	}

}
