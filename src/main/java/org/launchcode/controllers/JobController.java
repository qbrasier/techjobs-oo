package org.launchcode.controllers;

import org.launchcode.models.Employer;
import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job temp = jobData.findById(id);
        model.addAttribute(temp);  // not sure if this is actually passing it on to the view
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if(errors.hasErrors()){
            return "new-job";
        }
        Job newJob = new Job();
        newJob.setName(jobForm.getName());
        newJob.setEmployer( jobForm.getEmployers().get(jobForm.getEmployerId()) );
        newJob.setLocation( jobForm.getLocations().get(jobForm.getLocationId()) );
        newJob.setCoreCompetency( jobForm.getCoreCompetencies().get(jobForm.getSkillId()) );
        newJob.setPositionType( jobForm.getPositionTypes().get(jobForm.getPositionId()) );

        jobData.add(newJob);

        model.addAttribute(newJob);
        //return "job-detail";
        int id = jobData.findAll().size();
        String url = "/job?id="+(id);
        return "redirect:"+url;
        //return new RedirectView(url);


    }
}
