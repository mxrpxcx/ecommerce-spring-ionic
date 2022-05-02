package com.gxdxy.curso.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gxdxy.curso.service.exception.FileException;

@Service
public class ImageService {

	public BufferedImage jpgFromFile(MultipartFile multipartFile) {
		String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		
		if(!"png".equals(ext) && !"jpg".equals(ext)) {
			throw new FileException("Somente imagens .png/jpg são aceitas");
		}
		
		try {
			BufferedImage img = ImageIO.read(multipartFile.getInputStream());
			if("png".equals(ext)) {
				img = pngToJpg(img);
			}
			return img;
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
		
	}

	public BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), 
				BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img,0,0,Color.WHITE,null);
		return jpgImage;
	}
	
	public InputStream getInputStream(BufferedImage img, String ext) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, ext, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo!");
		}
	}
	
	public BufferedImage enquadrarImagem(BufferedImage src) {
		int min = (src.getHeight() <= src.getWidth()) ? src.getHeight() : src.getWidth();
		return Scalr.crop(src, 
				(src.getWidth()/2)-(min/2),
				(src.getHeight()/2)-(min/2), min, min);
	}
	
	public BufferedImage redimensionarImagem(BufferedImage src, int tamanho) {
		return Scalr.resize(src, Scalr.Method.ULTRA_QUALITY, tamanho);
		
	}
	
}
