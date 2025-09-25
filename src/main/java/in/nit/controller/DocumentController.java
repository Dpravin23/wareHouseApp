package in.nit.controller;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import in.nit.model.Document;
import in.nit.service.IDocumentService;


@Controller
@RequestMapping("/documents")
public class DocumentController {
	@Autowired
    private IDocumentService service;
	
    @GetMapping("/all")
    public String ShowDocs(Model model){
    	model.addAttribute("list", service.findIdAndName());
    	
        return "Documents";
    }
    @PostMapping("/save")
    public String upload(@RequestParam Integer fileId,@RequestParam MultipartFile fileOb)
    {
        if (fileOb!=null && fileId!=null)
        {
        	Document doc= new Document();
             doc.setDocId(fileId);
             doc.setDocName(fileOb.getOriginalFilename());
            try{
                doc.setDocData(fileOb.getBytes());
            }catch(Exception e)
           {e.printStackTrace();}
               service.saveDocumnt(doc);
        }
        return "redirect:all";
    }
    
    //download doc based on ID
    @GetMapping("/download/{id}")
    public void downloadDoc(@PathVariable Integer id,HttpServletResponse resp) {
    	//get data from DB using ID
    	
    	Optional<Document> opt=service.findDocument(id);
    	if(opt.isPresent()) {
    		//read doc object
    		Document doc=opt.get();
    		//add header parameter(key-val) to response
    		resp.addHeader("Content-Disposition", "attachment;filename="+doc.getDocName());
    		try {
    			//copy byte[] data from model class object to servlet output stream
				FileCopyUtils.copy(doc.getDocData(),//from
						resp.getOutputStream());//to
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		
    	}
    }
    //ajax validation
	   @GetMapping("/validatefileId")
	   public @ResponseBody String  validateDocId(@RequestParam Integer docId)
	   {
		  String message="";
		  if(service.isDocIdExist(docId))
		  { 
			  System.out.println("checking docid");
			  message="File ID'"+docId+"' already exist";
			  System.out.println("doc if found");
	      }
		   return message;
	   }
}



