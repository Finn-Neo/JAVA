package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @program: sky-take-out
 * @author: Qiaolezi
 * @create: 2024-07-11 15:46
 * @description: 通用接口
 **/
@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {
	@Autowired
	private AliOssUtil aliOssUtil;

	//file 名称与前端提交的名称保持一致
	@PostMapping("/upload")
	@ApiOperation("文件上传")
	public Result<String> upload(MultipartFile file) {
		log.info("文件上传:{}", file);

		try {
			String originalFilename = file.getOriginalFilename();//原始文件名
			//获取文件的扩展名
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			//构建新文件名
			String objectName = UUID.randomUUID().toString() + extension;

			//文件的请求路径
			String filePath = aliOssUtil.upload(file.getBytes(), objectName);
			return Result.success(filePath);
		} catch (IOException e) {
			log.error("文件上传失败:{}", e);
		}
		return Result.error(MessageConstant.UPLOAD_FAILED);
	}
}