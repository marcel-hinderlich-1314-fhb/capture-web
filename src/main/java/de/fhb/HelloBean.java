package de.fhb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.imageio.ImageIO;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
 
@ManagedBean
@SessionScoped
public class HelloBean implements Serializable {
	
	String url = "http://localhost:3000/?url=www.google.co";
	
	BufferedImage image;
	StreamedContent content;


	private static final long serialVersionUID = 1L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String capture() {
		System.out.println("capture");
		

		try {
			
			//get Image from Servlet			
			byte[] imageData = download(new URL(this.url));
			
			//get Images from ByteArray
			this.image = ImageIO.read(new ByteArrayInputStream(imageData));
			System.out.println("successful captured screenshot");
			System.out.println(this.image);
			
			InputStream is = new ByteArrayInputStream(imageData);
			content = new DefaultStreamedContent(is);
			
			
		} catch (Exception e) {
			System.out.println("error occured");

		}
		

		return "capture";
	}
	
	public byte[] download(URL url) throws IOException {
	    URLConnection uc = url.openConnection();
	    int len = uc.getContentLength();
	    InputStream is = new BufferedInputStream(uc.getInputStream());
	    try {
	        byte[] data = new byte[len];
	        int offset = 0;
	        while (offset < len) {
	            int read = is.read(data, offset, data.length - offset);
	            if (read < 0) {
	                break;
	            }
	          offset += read;
	        }
	        if (offset < len) {
	            throw new IOException(
	                String.format("Read %d bytes; expected %d", offset, len));
	        }
	        return data;
	    } finally {
	        is.close();
	    }
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public StreamedContent getContent() {
		return content;
	}

	public void setContent(StreamedContent content) {
		this.content = content;
	}

}