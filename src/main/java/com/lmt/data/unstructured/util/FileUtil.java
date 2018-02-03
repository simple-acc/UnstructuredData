package com.lmt.data.unstructured.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

/**
 * @author MT-Lin
 * @date 2018/1/10 17:49
 */
@Component
@SuppressWarnings("resource")
public class FileUtil {

	private Logger logger = LoggerFactory.getLogger(FileUtil.class);

	@Autowired
	private Environment environment;

	public String getFileContent(String resourceFileName) {
		if (!this.existResourceFile(resourceFileName)) {
			logger.error("资源文件 [resourceFileName={}] 不存在，获取资源内容失败", resourceFileName);
			return null;
		}
		String extention = resourceFileName.substring(resourceFileName.lastIndexOf(UdConstant.FILE_EXTENSION_SPLIT) + 1)
				.toLowerCase();
		String filePath = this.getFullFilePath(resourceFileName);
		String content = null;
		switch (extention) {
		case UdConstant.FILE_TYPE_PDF:
			content = this.getPdfFileContent(filePath);
			break;
		case UdConstant.FILE_TYPE_DOC:
			content = this.getDocFileContent(filePath);
			break;
		case UdConstant.FILE_TYPE_DOCX:
			content = this.getDocxFileContent(filePath);
			break;
		case UdConstant.FILE_TYPE_XLS:
		case UdConstant.FILE_TYPE_XLSX:
			content = this.getExcelFileContent(filePath);
			break;
		case UdConstant.FILE_TYPE_TXT:
			content = this.getTxtFileContent(filePath);
			break;
		case UdConstant.FILE_TYPE_PPT:
			content = this.getPptFileContent(filePath);
			break;
		case UdConstant.FILE_TYPE_PPTX:
			content = this.getPptxFileContent(filePath);
			break;
		default:
			break;
		}
		return content;
	}

	private String getPptxFileContent(String filePath) {
		// TODO 所有的Office文档，包括2007及以下版本的文档都可以使用该方法读取内容，坑爹啊(* ￣︿￣)
		File file = new File(filePath);
		POITextExtractor extractor = null;
		String content = null;
		try {
			extractor = ExtractorFactory.createExtractor(file);
			content = extractor.getText();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != extractor) {
				try {
					extractor.close();
				} catch (IOException e) {
					logger.error("读取pptx文件之后关闭POITextExtractor出现IO异常");
					e.printStackTrace();
				}
			}
		}
		return content;
	}

	private String getPptFileContent(String filePath) {
		PowerPointExtractor extractor = null;
		String content = null;
		try {
			extractor = new PowerPointExtractor(filePath);
			content = extractor.getText(true, true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != extractor) {
				try {
					extractor.close();
				} catch (IOException e) {
					logger.error("读取ppt文件之后关闭 PowerPointExtractor 出现IO异常");
					e.printStackTrace();
				}
			}
		}
		return content;
	}

	private String getTxtFileContent(String filePath) {
		File file = new File(filePath);
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, EncodingDetect.getJavaEncode(filePath));
			br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("读取txt文件之后关闭BufferReader出现IO异常");
					e.printStackTrace();
				}
			}
			if (null != isr) {
				try {
					isr.close();
				} catch (IOException e) {
					logger.error("读取txt文件之后关闭InputStreamReader出现IO异常");
					e.printStackTrace();
				}
			}
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("读取txt文件之后关闭FileInputStream出现IO异常");
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("deprecation")
	private String getExcelFileContent(String filePath) {
		File file = new File(filePath);
		Workbook workbook = null;
		StringBuilder content = new StringBuilder();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			if (filePath.endsWith(UdConstant.FILE_TYPE_XLS)) {
				workbook = new HSSFWorkbook(fis);
			} else {
				workbook = new XSSFWorkbook(file);
			}
			for (Sheet rows : workbook) {
				for (Row row : rows) {
					for (Cell cell : row) {
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_FORMULA:
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							content.append(cell.getNumericCellValue()).append('\t');
							break;
						case HSSFCell.CELL_TYPE_STRING:
							content.append(cell.getStringCellValue()).append('\t');
							break;
						default:
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("获取Excel资源文件内容之后关闭文件输入流出现异常");
					e.printStackTrace();
				}
			}
			if (null != workbook) {
				try {
					workbook.close();
				} catch (IOException e) {
					logger.error("获取Excel资源文件内容之后关闭workbook出现异常");
					e.printStackTrace();
				}
			}
		}
		return content.toString();
	}

	private String getDocxFileContent(String filePath) {
		File file = new File(filePath);
		String content = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			XWPFDocument document = new XWPFDocument(fis);
			XWPFWordExtractor extractor = new XWPFWordExtractor(document);
			content = extractor.getText();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("获取docx资源文件内容之后关闭文件输入流出现异常");
					e.printStackTrace();
				}
			}
		}
		return content;
	}

	private String getDocFileContent(String filePath) {
		File file = new File(filePath);
		String content = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			WordExtractor wordExtractor = new WordExtractor(fis);
			content = wordExtractor.getText();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("获取doc资源文件内容之后关闭文件输入流出现异常");
					e.printStackTrace();
				}
			}
		}
		return content;
	}

	private String getPdfFileContent(String filePath) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			PdfReader pdfReader = new PdfReader(filePath);
			PdfReaderContentParser parser = new PdfReaderContentParser(pdfReader);
			TextExtractionStrategy strategy;
			for (int i = 1; i < pdfReader.getNumberOfPages(); i++) {
				strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
				stringBuilder.append(strategy.getResultantText());
			}
			pdfReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	public String getFullFilePath(String fileName) {
		String filePath = environment.getProperty(UdConstant.RESOURCE_TEMP);
		return filePath + fileName;
	}

	public boolean existResourceFile(String resourceFileName) {
		String filePath = environment.getProperty(UdConstant.RESOURCE_TEMP);
		File resourceFile = new File(filePath + resourceFileName);
		return resourceFile.exists();
	}

	public boolean renameFile(MultipartFile multipartFile, String resourceId) {
		String oldFileName = multipartFile.getOriginalFilename();
		String newFileName = resourceId + oldFileName.substring(oldFileName.lastIndexOf("."));
		String filePath = environment.getProperty(UdConstant.RESOURCE_TEMP);
		File renameFile = new File(filePath + newFileName);
		File file = new File(filePath + oldFileName);
		return file.renameTo(renameFile);
	}

	public String saveFile(MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			return null;
		}
		String fileName = multipartFile.getOriginalFilename();
		String filePath = environment.getProperty(UdConstant.RESOURCE_TEMP);
		File folder = new File(filePath);
		if (!folder.exists()) {
			// noinspection ResultOfMethodCallIgnored
			folder.mkdirs();
		}
		InputStream inputStream = null;
		OutputStream outputStream = null;
		File file = new File(filePath + fileName);
		try {
			inputStream = multipartFile.getInputStream();
			outputStream = new FileOutputStream(file);
			int bytesReader;
			byte[] buffer = new byte[UdConstant.FILE_READ_BUFFER_SIZE];
			while ((bytesReader = inputStream.read(buffer, 0, buffer.length)) != -1) {
				outputStream.write(buffer, 0, bytesReader);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("文件输入流关闭异常");
					e.printStackTrace();
				}
			}
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("文件输出流关闭异常");
					e.printStackTrace();
				}
			}
		}
		return Md5Util.getFileMD5(file);
	}
}
