package org.test;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.model.HiringProcessInput;
import org.model.TransactionFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@SpringBootApplication
public class ApplicationWithUpload extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AppConfig.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationWithUpload.class, args);
	}
}

@Controller
@RestController
@RequestMapping("/rpatransform")
@SessionAttributes( { "fileLink", "transactionFile", "linkFileAndURI", "linkFileAndURIArr"})
class GreetingUploadController{
	@Autowired
	RPAExcelWrite poiExcelWrite;
	@Autowired
	RPAExcelRead poiExcelRead;
	@Autowired
	RPAExcelUpload poiExcelUpload;
	@Autowired
	RPAExcelDownload poiExcelDownload;
	@Autowired
	RPACSVDownload poiCSVDownload;
    @Autowired
    RPACSVWrite poiCSVWrite;
    @Autowired
    RPAHiringDecision hiringDecision;
    @Autowired
    FileLink fileLink;
	
	@RequestMapping(method = RequestMethod.GET)	
    public ModelAndView helloNew(ModelMap model) {	
		TransactionFile transactionFile = new TransactionFile();
        model.addAttribute("transactionFile", transactionFile);		
        return new ModelAndView("RPAFileUpload");
    }

	@RequestMapping(method = RequestMethod.POST)	
    public @ResponseBody ModelAndView processTransaction(@RequestPart MultipartFile file, 
    		TransactionFile transactionFile, FileLink fileLink, ModelMap model) {

        ArrayList<WorkSheetData> sheetArr = null;
        byte[] output = null;
        ModelAndView mnv = null;
        ArrayList<LinkFileAndURI> linkFileAndURIArr = new ArrayList<LinkFileAndURI>();
        try {
	       sheetArr = poiExcelUpload.readXL(file.getInputStream());	
	       ByteArrayOutputStream[] baosArr = null;
	       if (transactionFile.getTransformType().equals("Transform from Excel to Excel")) {
	          baosArr = 
	          poiExcelDownload.checkWorkbook(sheetArr);  
	       }
	       else if (transactionFile.getTransformType().equals("Transform from Excel to CSV")) {
	    	   baosArr = poiCSVDownload.transformToCSV(sheetArr);	
	       }
	       FileItem[] fileItem = new FileItem[baosArr.length];
           for (int i=0; i<baosArr.length; i++) {
	        output = baosArr[i].toByteArray();	        
	        fileItem[i] = new FileItem();
	        fileItem[i].setArr(output);
	        fileItem[i].setId((long) (i+1));
	        fileLink.setFileItem(fileItem);
           } 
	        Link[] detail = new Link[fileItem.length];
	        LinkFileAndURI linkFileAndURI = null;
	        for (int i=0; i<fileItem.length; i++) {
	        	linkFileAndURI = new LinkFileAndURI();
	        	detail[i] = linkTo(GreetingUploadController.class).slash(fileItem[i].getId()).withSelfRel();
	        	linkFileAndURI.setDetail(detail[i]);
	        	if (transactionFile.getTransformType().equals("Transform from Excel to Excel")) {
	        	   linkFileAndURI.setFileName("XLSResult"+String.valueOf(i+1)+".xlsx");
	        	   fileItem[i].setName("XLSResult"+String.valueOf(i+1)+".xlsx");
	        	}
	        	else {
		       	   linkFileAndURI.setFileName(sheetArr.get(i).getDatasheetName()+".csv");
		       	   fileItem[i].setName(sheetArr.get(i).getDatasheetName()+".csv");	        		
	        	}
	        	linkFileAndURIArr.add(linkFileAndURI);
	        }
	        
	        int noOfFiles = fileItem.length;
	        model.addAttribute("fileLink", fileLink);
	        model.addAttribute("detail", detail);
	        model.addAttribute("linkFileAndURIArr", linkFileAndURIArr);
	        model.addAttribute("noOfFiles", noOfFiles);
	        mnv = new ModelAndView("RPAUploadSuccess");
	        mnv.addObject(model);
        } 
        catch (Exception e) {
	       // TODO Auto-generated catch block
	       e.printStackTrace();
        }
        return mnv;
    }
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<byte[]> downloadFile(@PathVariable Long id, 
    		FileLink fileLink, ModelMap model, BindingResult result
            ) {
    	
    	HttpHeaders responseHeaders = null;
        responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        responseHeaders.set("Content-disposition", "filename="+(fileLink.getFileItem())[id.intValue()-1].getName());
    	ResponseEntity<byte[]> r = new ResponseEntity<byte[]>((fileLink.getFileItem())[id.intValue()-1].getArr(), responseHeaders, HttpStatus.OK);
    	return r;
	}
    
	@RequestMapping(value = "/hiringcheck", method = RequestMethod.GET)	
    public ModelAndView hiring(ModelMap model) {
		
		HiringProcessInput hiringProcessInput = new HiringProcessInput();
        model.addAttribute("hiringProcessInput", hiringProcessInput);
        return new ModelAndView("getCandidatesToHire");
    }
	
	@RequestMapping(value = "/hiringcheck", method = RequestMethod.POST)	
    public ModelAndView processHiring(@RequestPart MultipartFile file, HiringProcessInput hiringProcessInput, 
    		BindingResult result, ModelMap model, FileLink fileLink) {
        ModelAndView mnv = null;
		ArrayList<WorkSheetData> sheetArr = null;
        System.out.println(hiringProcessInput.getHiringCriteria());
        if (hiringProcessInput.getHiringCriteria() == null) {
			model.addAttribute("error", "Criteria is not selected "
					+ " Please select criteria and re-submit.");
			mnv = new ModelAndView("errorpage");
			mnv.addObject(model);
			return mnv;
        }
        String [] allCriteria = hiringProcessInput.getHiringCriteria().split(",");
        Double salary = hiringProcessInput.getSalary();
        String union = hiringProcessInput.getUnion();
        String location = hiringProcessInput.getLocation();
        System.out.println(salary);

        for (String criteria:allCriteria) {
        	if (criteria.equals("Salary") && (salary==null || !(salary instanceof Double))) {
    			model.addAttribute("error", "Salary checkbox selected, but Salary is either null or not numeric."
    					+ " Please correct it and re-submit.");
    			mnv = new ModelAndView("errorpage");
    			mnv.addObject(model);
    			return mnv;
        	}
        	if (criteria.equals("Union") && union==null) {
    			model.addAttribute("error", "Union checkbox selected, but Union is either null or not numeric."
    					+ " Please correct it and re-submit.");
    			mnv = new ModelAndView("errorpage");
    			mnv.addObject(model);
    			return mnv;
        	}
        	if (criteria.equals("Location") && location==null) {
    			model.addAttribute("error", "Location checkbox selected, but Location is either null or not numeric."
    					+ " Please correct it and re-submit.");
    			mnv = new ModelAndView("errorpage");
    			mnv.addObject(model);
    			return mnv;
        	}        	
        }
        try {
			if (file == null || file.isEmpty() || file.getInputStream() == null || file.getInputStream().toString() == null) {
    			model.addAttribute("error", "Input File is not chosen yet. Please select a input file.");   					
    			mnv = new ModelAndView("errorpage");
    			mnv.addObject(model);
    			return mnv;
			}
			try {
				sheetArr = hiringDecision.processDecision(hiringProcessInput, file.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ByteArrayOutputStream[] baosArr = null;
        byte[] output = null;

        ArrayList<LinkFileAndURI> linkFileAndURIArr = new ArrayList<LinkFileAndURI>();
        baosArr = poiExcelDownload.checkWorkbook(sheetArr);
	       FileItem[] fileItem = new FileItem[baosArr.length];
           for (int i=0; i<baosArr.length; i++) {
	        output = baosArr[i].toByteArray();	        
	        fileItem[i] = new FileItem();
	        fileItem[i].setArr(output);
	        fileItem[i].setId((long) (i+1));
	        fileLink.setFileItem(fileItem);
           } 
	        Link[] detail = new Link[fileItem.length];
	        LinkFileAndURI linkFileAndURI = null;
	        for (int i=0; i<fileItem.length; i++) {
	        	linkFileAndURI = new LinkFileAndURI();
	        	detail[i] = linkTo(GreetingUploadController.class).slash(fileItem[i].getId()).withSelfRel();
	        	linkFileAndURI.setDetail(detail[i]);
	        	linkFileAndURI.setFileName("HiringDecisionResult"+".xlsx");
	        	fileItem[i].setName("HiringDecisionResult"+".xlsx");
	        	linkFileAndURIArr.add(linkFileAndURI);
	        }
	        
	        int noOfFiles = fileItem.length;
	        model.addAttribute("fileLink", fileLink);
	        model.addAttribute("detail", detail);
	        model.addAttribute("linkFileAndURIArr", linkFileAndURIArr);
	        model.addAttribute("noOfFiles", noOfFiles);
	        mnv = new ModelAndView("RPAUploadSuccess");
	        mnv.addObject(model);
	        return mnv;
	}
	
	@RequestMapping(value="/hiringCheck/{id}",method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<byte[]> downloadHiringCheckFile(@PathVariable Long id, 
    		FileLink fileLink, ModelMap model, BindingResult result
            ) {
    	
    	HttpHeaders responseHeaders = null;
        responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        responseHeaders.set("Content-disposition", "filename="+(fileLink.getFileItem())[id.intValue()-1].getName());
    	ResponseEntity<byte[]> r = new ResponseEntity<byte[]>((fileLink.getFileItem())[id.intValue()-1].getArr(), responseHeaders, HttpStatus.OK);
    	return r;
	}	
}
	

