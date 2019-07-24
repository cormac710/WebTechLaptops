package com.cormacLaptops.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/image")
@Stateless
@LocalBean
public class FileUpload {

	private static final String SERVER_UPLOAD_LOCATION_FOLDER = "C:\\jboss\\jboss-as-7.1.1.Final\\standalone\\deployments\\cormacLaptops.war\\resources\\img\\stock\\";

	@POST
	@Consumes("multipart/form-data")
	public void uploadFile(final MultipartFormDataInput input) {

		String fileName = "";
		final Map<String, List<InputPart>> formParts = input.getFormDataMap();
		final List<InputPart> inPart = formParts.get("file");
		for (final InputPart inputPart : inPart) {
			try {
				System.out.println(inputPart.toString());
				final MultivaluedMap<String, String> headers = inputPart.getHeaders();
				fileName = parseFileName(headers);
				final InputStream istream = inputPart.getBody(InputStream.class, null);
				fileName = SERVER_UPLOAD_LOCATION_FOLDER + fileName;
				saveFile(istream, fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String parseFileName(final MultivaluedMap<String, String> headers) {
		final String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
		for (final String name : contentDispositionHeader) {
			if ((name.trim().startsWith("filename"))) {
				final String[] tmp = name.split("=");
				final String fileName = tmp[1].trim().replaceAll("\"", "");
				return fileName;
			}
		}
		return "randomName";
	}

	// save uploaded file to a defined location on the server
	private void saveFile(final InputStream uploadedInputStream, final String serverLocation) {
		try {
			OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			final byte[] bytes = new byte[1024];
			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {
		}
	}

}
