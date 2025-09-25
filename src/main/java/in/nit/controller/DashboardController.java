package in.nit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.nit.model.DashboardSummary;
import in.nit.service.impl.DashboardService;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/wareHouseDashboard")
    public String showDashboard(Model model) {
        DashboardSummary summary = dashboardService.getSummary();
        model.addAttribute("summary", summary);
        return "Dashboard"; // dashboard.html in templates folder
    }
}
