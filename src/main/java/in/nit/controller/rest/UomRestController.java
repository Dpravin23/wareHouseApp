package in.nit.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nit.model.UomType;
import in.nit.service.IUomTypeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/uom")
public class UomRestController {
	@Autowired
	private IUomTypeService service;
//1.get all 
	@GetMapping("/all")
public ResponseEntity<List<UomType>> getAll()
{
	List<UomType> list=service.getALLUomTypes();
	return ResponseEntity.ok(list);

}
	
//2.get one record
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable Integer id)
	{
		Optional<UomType> opt=service.getOneUomType(id);
		ResponseEntity<?> resp =null;
		if(opt.isPresent())
		{
			resp=new ResponseEntity<>(opt.get(),HttpStatus.OK);
		}else {
			resp=new ResponseEntity<>("Data Not Found",HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
//3.delete one 
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable Integer id)
	{
		
		ResponseEntity<String> resp =null;
		if(service.isUomTypeExist(id))
		{  
			try {
			service.deleteUomType(id);
			resp=new ResponseEntity<String>("UOM DELETED",HttpStatus.OK);
		}catch(Exception e) {resp=new ResponseEntity<String>("Data Can Not be Deleted",HttpStatus.BAD_REQUEST);}
			
		}else {
			resp=new ResponseEntity<>("UOM NOT FOUND",HttpStatus.NOT_FOUND);
		}
		return resp;
	}

//4save one record 
	@PostMapping("/insert")
	public ResponseEntity<?> save(@Valid @RequestBody UomType uomtype,BindingResult errors)
	{
		
		ResponseEntity<?> resp =null;
		
		{  if(errors.hasErrors()) {
		  HashMap< String, String> errMap=new HashMap<>();
		  for(FieldError err:errors.getFieldErrors()){
			  errMap.put(err.getField(), err.getDefaultMessage());
		  }
		  resp=new ResponseEntity<HashMap< String, String>>(errMap,HttpStatus.BAD_REQUEST);
		}else {
			try {
			Integer id=service.saveUomType(uomtype);
			resp=new ResponseEntity<String>("UOM '"+id+"'SAVED",HttpStatus.CREATED);
		}catch(Exception e) {resp=new ResponseEntity<String>("Data Can Not inserted",HttpStatus.INTERNAL_SERVER_ERROR);}
			
		}
		}
		return resp;
	}

//5.update one record
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody UomType uomtype)
	{
		
		ResponseEntity<String> resp =null;
		 if(uomtype.getId()== null || !service.isUomTypeExist(uomtype.getId()))
		{  
			
			 resp=new ResponseEntity<String>("Data Can Not exist",HttpStatus.NOT_FOUND);
			
		}else 
		{
			service.updateUomType(uomtype);
			resp=new ResponseEntity<String>("UOM '"+uomtype.getId()+"'updated",HttpStatus.RESET_CONTENT);
		}
			
		
		return resp;
	}
}
