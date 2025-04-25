package com.andreidodu.aidemo.service.impl;

import com.andreidodu.aidemo.agent.BrochureMakerAgent;
import com.andreidodu.aidemo.service.BrochureMakerService;
import com.andreidodu.aidemo.util.UrlUtil;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.Tuple;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrochureMakerServiceImpl implements BrochureMakerService {
    private final OpenAiChatModel openAiChatModel;


    @Override
    public void makeBrochure(String website) {
        try {
            Set<String> linkList = UrlUtil.findLinks(website);
            BrochureMakerAgent brochureMakerAgent = AiServices.create(BrochureMakerAgent.class, openAiChatModel);
            var links = brochureMakerAgent.decideSuitableLinks(website, linkList.toString());
            JSONObject obj = new JSONObject(links);
            JSONArray relevantLinkList = obj.getJSONArray("links");
            String pageContentList = relevantLinkList.toList()
                    .parallelStream()
                    .map(link -> UrlUtil.getPageContent((String) link))
                    .reduce("", (partialString, element) -> partialString + "\n\n" + element._1() + "\n" + element._2(), (a, b) -> a + b);

            Tuple<String, String> homePageContent = UrlUtil.getPageContent(website);

            String brochure = brochureMakerAgent.makeBrochure(homePageContent._1(), pageContentList);
            log.info(brochure);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}
