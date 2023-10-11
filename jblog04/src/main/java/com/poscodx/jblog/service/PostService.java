package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.PostVo;
@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	public List<PostVo> getPostsByCategory(Long categoryNo) {
		return postRepository.getPostsByCategory(categoryNo);
	}

	public PostVo getPostByNo(Long postNo) {
		return postRepository.getPostByPostNo(postNo);
	}

	public boolean add(PostVo postVo) {
		return postRepository.add(postVo);
		
	}

	public boolean delete(Long no) {
		return postRepository.delete(no);
		
	}

}
