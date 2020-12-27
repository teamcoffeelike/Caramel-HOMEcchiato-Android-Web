package com.hanul.coffeelike.caramelweb.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.InputStream;

@Service
public class FileService{
	/**
	 * 파일을 저장하고 데이터베이스 내부에 저장 가능한 키 값을 반환합니다.
	 */
	public String save(ServletContext ctx, InputStream data){
		File file = storageLocation(ctx);
		System.out.println(file.getAbsolutePath());

		return "WIP";
	}

	/**
	 * 내부 스토리지에 저장된 파일 Stream을 생성하여 반환합니다. 파일이 존재하지 않을 경우 {@code null}을 반환합니다.
	 */
	@Nullable public InputStream load(ServletContext ctx, String attachment){

		return null;
	}

	private File storageLocation(ServletContext ctx){
		return new File("/attachments"); // .properties 사용?
	}
}
