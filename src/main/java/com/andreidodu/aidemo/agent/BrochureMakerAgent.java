package com.andreidodu.aidemo.agent;

import dev.langchain4j.service.*;

public interface BrochureMakerAgent {

    @SystemMessage("You are a professional IT company brochure maker that analyzes the contents of several relevant pages from a company website " +
            "and creates a short brochure about the company for prospective customers, investors and recruits. Respond in markdown." +
            "Include details of company culture, customers and careers/jobs if you have the information. You are friendly, polite and concise.")
    @UserMessage("You are looking at a company called: {{companyName}}, Here are the contents of its landing page and other relevant pages {{text}};\n\n" +
            "use this information to build a short brochure of the company in markdown")
    String makeBrochure(@V("companyName") String companyName, @V("text") String text);

    @SystemMessage("Here is the list of links on the website of {{websiteUrl}}. Please decide which of these are relevant web links for a " +
            "brochure about the company, respond with the full https URL in JSON format (avoid markdown, root property key should be \"links\"). " +
            "Do not include Terms of Service, Privacy, email links.")
    @UserMessage("Links (some might be relative links): {{links}}")
    String decideSuitableLinks(@V("websiteUrl") String websiteUrl, @V("links") String links);

}
