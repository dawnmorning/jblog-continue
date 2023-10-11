package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.service.fileUploadService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Controller
@RequestMapping(value = "/{blogId:^(?!assets).*$}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private fileUploadService fileUploadService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostService postService;

	@RequestMapping({ "", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(@PathVariable("blogId") String blogId, @PathVariable Optional<Long> categoryNo,
			@PathVariable Optional<Long> postNo, Model model) {

		BlogVo blogVo = blogService.getBlog(blogId);
		if (blogVo == null) {
			return "error/404";
		}

		List<CategoryVo> categoryList = categoryService.getCategoriesById(blogId);
		if (categoryList == null || categoryList.isEmpty()) {
			return "error/404";
		}

		Long resolvedCategoryNo = categoryNo.orElse(-1L);
		boolean isValidCategory = categoryList.stream()
				.anyMatch(cat -> cat.getNo() != null && cat.getNo().equals(resolvedCategoryNo));

		if (!isValidCategory && categoryNo.isPresent()) {
			return "error/404";
		}

		List<PostVo> postVo = null;
		if (categoryNo.isPresent()) {
			postVo = postService.getPostsByCategory(categoryNo.get());
			if (postVo == null || postVo.isEmpty()) {
				return "error/404";
			}
		} else {
			categoryNo = Optional.of(categoryList.get(0).getNo());
			postVo = postService.getPostsByCategory(categoryNo.get());
		}

		if (postNo.isPresent()) {
			if (postVo.stream().noneMatch(post -> post.getNo().equals(postNo.get()))) {
				return "error/404";
			}
			PostVo post = postService.getPostByNo(postNo.get());
			model.addAttribute("post", post);
		} else if (postVo != null && !postVo.isEmpty()) {
			model.addAttribute("post", postVo.get(0));
		}
		System.out.println(categoryList);
		model.addAttribute("categoryNo", categoryNo.orElse(null));
		model.addAttribute("postVo", postVo);
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);

		return "blog/main";
	}

	@RequestMapping(value = "/admin/basic", method = RequestMethod.GET)
	public String adminBasic(@PathVariable("blogId") String blogId, Model model) {

		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);

		return "blog/admin-basic";
	}

	@RequestMapping(value = "/admin/basic", method = RequestMethod.POST)
	public String adminBasic(@PathVariable("blogId") String blogId, BlogVo blogVo, MultipartFile file) {

		blogVo.setBlogId(blogId);
		String image = fileUploadService.restore(file);
		if (image != null) {
			blogVo.setImage(image);
		}
		blogService.update(blogVo);

		return "redirect:/" + blogId;
	}

	@RequestMapping(value = "/admin/category", method = RequestMethod.GET)
	public String adminCategory(@PathVariable("blogId") String blogId, Model model) {
		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);

		List<CategoryVo> categoryVo = categoryService.getCategoriesById(blogId);
		System.out.println(categoryVo);
		model.addAttribute("categoryVo", categoryVo);
		return "blog/admin-category";
	}

	@RequestMapping(value = "/admin/category", method = RequestMethod.POST)
	public String adminCategory(@PathVariable("blogId") String blogId, CategoryVo categoryVo) {

		categoryVo.setBlogId(blogId);
		categoryService.add(categoryVo);

		return "redirect:/" + blogId + "/admin/category";
	}

	@RequestMapping(value = "/admin/write", method = RequestMethod.GET)
	public String adminWrite(@PathVariable("blogId") String blogId, Model model) {
		List<CategoryVo> categoryVo = categoryService.getCategoriesById(blogId);
		model.addAttribute("categoryVo", categoryVo);

		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);

		return "blog/admin-write";
	}

	@RequestMapping(value = "/admin/write", method = RequestMethod.POST)
	public String adminWrite(@PathVariable("blogId") String blogId, PostVo postVo) {
		postService.add(postVo);
		return "redirect:/" + blogId;
	}

	@RequestMapping(value = "/admin/basic/update", method = RequestMethod.POST)
	public String adminUpdate(@PathVariable("blogId") String blogId, @RequestParam("logo-file") MultipartFile file,
			BlogVo blogVo) {
		String url = fileUploadService.restore(file);
		blogVo.setImage(url);
		blogVo.setBlogId(blogId);
		blogService.update(blogVo);

		return "redirect:/" + blogId + "/admin/basic";
	}

	@RequestMapping(value = "/admin/delete/{no}")
	public String adminDelete(@PathVariable("blogId") String blogId, @PathVariable Long no) {
		postService.delete(no);
		categoryService.delete(no);
		return "redirect:/" + blogId + "/admin/category";
	}

}
