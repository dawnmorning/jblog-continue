package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;

	public BlogVo getBlog(String blogId) {
		return blogRepository.getBlogById(blogId);
	}

	public List<CategoryVo> getCategoriesById(String blogId) {
		return blogRepository.getCategoriesById(blogId);
	}

	public List<PostVo> getPostsByCategory(Long no) {
		return blogRepository.getPostByCategory(no);
	}

	public void add(BlogVo blogVo) {
		blogRepository.add(blogVo);
	}

	public void update(BlogVo blogVo) {
		blogRepository.update(blogVo);
	}

}
