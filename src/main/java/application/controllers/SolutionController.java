package application.controllers;

import application.core.exception.service.ExceptionService;
import application.core.processmeasureparticipant.service.ProcessMeasureParticipantService;
import application.core.solution.service.SolutionService;
import application.model.*;
import application.model.Exception;
import application.security.AppUser;
import application.util.DialogMessageUtil;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SolutionController {

    @Autowired
    SolutionService solutionService;

    @Autowired
    ExceptionService exceptionService;

    @Autowired
    ProcessMeasureParticipantService processMeasureParticipantService;

    @RequestMapping(value = "/solution/add-solution")
    public String submitSolution(@RequestParam("exceptionId") Integer exceptionId,
                                 @RequestParam("solutionId") String solutionId,
                                 @RequestParam("description") String description,
                                 RedirectAttributes redirectAttributes) {
        try {

            Exception exception = exceptionService.findById(exceptionId);

            if (exception == null) {
                DialogMessageUtil.addRedirectMessage(redirectAttributes,
                        "Invalidad Exception Id.",
                        "The exception id doesn't exists.",
                        "error");
                return "redirect:/index";
            }

            AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            SolutionId newSolutionId = new SolutionId();
            newSolutionId.setExceptionFk(exceptionId);
            newSolutionId.setId(solutionId);

            Solution solution = new Solution();
            solution.setSolutionId(newSolutionId);
            solution.setDescription(description);
            solution.setCount(1);
            solution.setRank(1);

            solutionService.insert(solution, appUser);
        } catch (HibernateException e) {
            DialogMessageUtil.addRedirectMessage(redirectAttributes,
                    "Error.",
                    e.getMessage(),
                    "error");
            return "redirect:/index";
        }
        DialogMessageUtil.addRedirectMessage(redirectAttributes,
                "Success!",
                "You solution was added.",
                "success");
        return "redirect:/exception/"+exceptionId;
    }


    @RequestMapping(value = "/solutions/get")
    public List<Solution> getSolutions() {
        return null;
    }

    @RequestMapping(value = "/solution/get/{id}")
    @ResponseBody
    public String getExceptionSolution(@PathVariable("id") long exceptiopnId) {
        Solution solution = solutionService.getRecommendedSolution(exceptiopnId);
        if(solution == null) return "No solutions yet! Propose one!";
        return solution.getDescription();
    }

    @RequestMapping(value= "/solution/vote")
    public String voteSolution(@RequestParam("exceptionId") long exceptionId,
                               RedirectAttributes redirectAttributes) {
        try {
            Exception exception = exceptionService.findById(exceptionId);

            Integer count = processMeasureParticipantService.countParticipantsByProcessMeasure(exception.getProcessMeasure().getProcessMeasureId());

            if(count > 1) {
                exception.setStatus("INDECISIVE");
            } else {
                exception.setStatus("CONFIRMED");
            }
            exceptionService.update(exception);

        } catch (java.lang.Exception e) {
            DialogMessageUtil.addRedirectMessage(redirectAttributes,
                    "Error.",
                    "Your vote was not cast.",
                    "error");
            return "redirect:/exception/"+exceptionId;
        }
        DialogMessageUtil.addRedirectMessage(redirectAttributes,
                "Success!",
                "Your vote was counted!",
                "success");
        return "redirect:/index";
    }
}

