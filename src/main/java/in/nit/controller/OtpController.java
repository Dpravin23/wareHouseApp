package in.nit.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nit.model.OtpRequest;
import in.nit.model.User;
import in.nit.service.EmailService;
import in.nit.service.IUserService;
import in.nit.service.OtpService;
import jakarta.servlet.http.HttpSession;

@Controller

public class OtpController {
	@Autowired
    private IUserService userService;
    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendOtpVerifcation(@ModelAttribute User user,Model model,HttpSession session)
    {
    	String generatedOtp=otpService.generateOtp(user.getEmail());
		emailService.sendOtpEmail(user.getEmail(), generatedOtp);
		session.setAttribute("otprequested", true);
		session.setAttribute("email", user.getEmail());
		session.setAttribute("generatedOtp", generatedOtp);
		model.addAttribute("user", user);
		return "redirect:/verify";
    }
	/*
	 * public ResponseEntity<String> sendOtp(@RequestBody User request) { String otp
	 * = otpService.generateOtp(request.getEmail());
	 * emailService.sendOtpEmail(request.getEmail(), otp);
	 * System.out.println("otp sent success"+otp); return
	 * ResponseEntity.ok("OTP sent to " + request.getEmail()); }
	 */
    @GetMapping("/verify")
    public String getVerified(Model model,User user,HttpSession session)
    
	{   
    	 Boolean isVerified = (Boolean) session.getAttribute("isVerified");
    	    if (Boolean.TRUE.equals(isVerified)) {
    	        session.removeAttribute("generatedOtp");
    	        session.removeAttribute("email");
    	        session.removeAttribute("isVerified");
    	    }
			/*
			 * if(session.isNew()) { session.removeAttribute("otprequested"); }
			 */
    	return "Verify";
    }
    @PostMapping("/verify-otp")
    public String verifyOtp(@ModelAttribute User user, RedirectAttributes redirectAttribute,HttpSession session) {
        String email = (String) session.getAttribute("email");
        String submittedOtp = user.getOtp();

        if (email != null) {
            Optional<User> optionalUser = userService.findByEmail(email);
            if (optionalUser.isPresent()) {
                /*User dbUser = optionalUser.get();
                if (dbUser.getOtp().equals(submittedOtp))*/
            	 String vefotpwith=(String)session.getAttribute("generatedOtp");
            	 if(vefotpwith.equalsIgnoreCase(submittedOtp)){
                    System.out.println("verified");
                    user.setVerified(true);
                    //userService.updateUser(user);
                    userService.updateUserStatus(true,optionalUser.get().getId());
                    session.setAttribute("isVerfied", true);
                    redirectAttribute.addFlashAttribute("message", "You have been verified. Please log in now!");
                } else {
                    System.out.println("nonverified");
                }
            	 redirectAttribute.addFlashAttribute("user", user);
            } else {
            	redirectAttribute.addFlashAttribute("error", "User not found");
            }
        } else {
        	redirectAttribute.addFlashAttribute("error", "Email is missing");
        }

        return "redirect:/verify";
    }

   /* public ResponseEntity<String> verifyOtp(@RequestBody User request) {
        boolean isValid = otpService.verifyOtp(request.getEmail(), request.getOtp());
        return isValid ? ResponseEntity.ok("OTP verified successfully")
                       : ResponseEntity.badRequest().body("Invalid or expired OTP");
    }*/
}
