package com.example.demo.form;

import com.example.demo.annotation.HalfWidthAlpha;
import com.example.demo.form.ValidationGroups.HalfWidthAlphaGroup;
import com.example.demo.form.ValidationGroups.NotBlankGroup;
import com.example.demo.form.ValidationGroups.SizeCheckGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentForm {
	
	private Long id;
	
	@NotBlank(message = "部署名を入力してください。" , groups = NotBlankGroup.class)
	@Size(max = 255, min = 1, message = "部署名は1文字以上、255文字以内で入力してください。" , groups = SizeCheckGroup.class)
	public String nameJp;
	
	@NotBlank(message = "部署名（英語）を入力してください。" , groups = NotBlankGroup.class)
	@Size(max = 255, min = 1, message = "部署名（英語）は1文字以上、255文字以内で入力してください。" , groups = SizeCheckGroup.class)
	@HalfWidthAlpha(message = "半角英数字で入力してください。",groups = HalfWidthAlphaGroup.class)
	public String nameEn;
}
