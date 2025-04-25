package com.andreidodu.aibrochure.service.impl;

import com.andreidodu.aibrochure.agent.BrochureMakerAgent;
import com.andreidodu.aibrochure.service.BrochureMakerService;
import com.andreidodu.aibrochure.util.UrlUtil;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.Tuple;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrochureMakerServiceImpl implements BrochureMakerService {
    private final OpenAiChatModel openAiChatModel;


    @Override
    public void makeBrochure(String website) {
        try {
            BrochureMakerAgent brochureMakerAgent = AiServices.create(BrochureMakerAgent.class, openAiChatModel);

            JSONArray relevantLinkList = retrieveRelevantLinkList(website, brochureMakerAgent);

            String allPageContents = mergePageContentOfEachLink(relevantLinkList);

            Tuple<String, String> homePageContent = UrlUtil.getPageContent(website);

            String brochure = brochureMakerAgent.makeBrochure(homePageContent._1(), allPageContents);

            log.info(brochure);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private static String mergePageContentOfEachLink(JSONArray relevantLinkList) {
        return relevantLinkList.toList()
                .parallelStream()
                .map(link -> UrlUtil.getPageContent((String) link))
                .reduce("", (partialString, element) -> partialString + "\n\n" + element._1() + "\n" + element._2(), (a, b) -> a + b);
    }

    private static JSONArray retrieveRelevantLinkList(String website, BrochureMakerAgent brochureMakerAgent) throws IOException {
        Set<String> linkList = UrlUtil.findLinks(website);
        var links = brochureMakerAgent.decideSuitableLinks(website, linkList.toString());
        JSONObject obj = new JSONObject(links);
        return obj.getJSONArray("links");
    }


}
