package com.pjs.cjs.service;

import com.pjs.cjs.model.FileDetails;
import com.pjs.cjs.model.SimpleFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileParsingService {

    private int currentIndex = 0;
    public FileDetails parseFile(MultipartFile file) throws IOException {


        FileDetails fileDetails = new FileDetails();
        XWPFDocument docx = new XWPFDocument(file.getInputStream());
        XWPFWordExtractor we = new XWPFWordExtractor(docx);

        String[] tokenizedDocument = we.getText().toUpperCase().split("\n");

        fileDetails.setCourtName(tokenizedDocument[currentIndex+=2].trim());        // court name
        fileDetails.setAppealNumber(tokenizedDocument[currentIndex+=2].trim());     // Appeal no
        fileDetails.setAppellant(getAppellant(tokenizedDocument[currentIndex+=2].trim()));
        fileDetails.setRespondent(getRespondent(tokenizedDocument));
        fileDetails.setDateOfHearing(getDateOfHearing(tokenizedDocument));
        fileDetails.setAdvocatesForAppellant(getAdvForAppellant(tokenizedDocument));
        fileDetails.setAdvocatesForRespondent(getAdvocateForRespondent(tokenizedDocument));
        fileDetails.setJudgeName(getJudgeName(tokenizedDocument));
        fileDetails.setResult(getResult(tokenizedDocument));

        return fileDetails;
    }

    private String getResult(String[] tokenizedDocument) {
        currentIndex = currentIndex + (63 - currentIndex);
        return tokenizedDocument[currentIndex].split(",")[0];
    }

    private String getJudgeName(String[] tokenizedDocument) {
        while(tokenizedDocument[currentIndex+=1].isEmpty()){}
        String judgeName;
        judgeName = tokenizedDocument[currentIndex]
                .trim()
                .substring(0, tokenizedDocument[currentIndex].indexOf(","));

        return judgeName;
    }

    private String getAdvocateForRespondent(String[] tokenizedDocument) {
        while(!tokenizedDocument[currentIndex+=1].trim().contains("RESPONDENT")){}
        String advForRespondent;
        advForRespondent =  tokenizedDocument[currentIndex].trim().split("RESPONDENT")[1].trim();

        if(advForRespondent.contains("BY"))
        {
            advForRespondent = advForRespondent.split("BY")[1].trim();
        }
        if(advForRespondent.contains("&") || advForRespondent.contains("AND"))
        {
            while(tokenizedDocument[currentIndex+=1].isEmpty()){}
            advForRespondent = advForRespondent + " " + tokenizedDocument[currentIndex];

        }
        return advForRespondent;

    }

    private String getAdvForAppellant(String[] tokenizedDocument) {
        while(!tokenizedDocument[currentIndex+=1].trim().contains("APPELLANT-PETITIONER")){}
        String advForAppellant;
        advForAppellant =  tokenizedDocument[currentIndex].trim().split("APPELLANT-PETITIONER")[1].trim();

        if(advForAppellant.contains("BY"))
        {
            advForAppellant = advForAppellant.split("BY")[1].trim();
        }
        if(advForAppellant.endsWith("&") || advForAppellant.endsWith("AND"))
        {
            while(tokenizedDocument[currentIndex+=1].isEmpty()){}
            advForAppellant = advForAppellant + " " + tokenizedDocument[currentIndex];

        }
        return advForAppellant;
    }

    private String getDateOfHearing(String[] tokenizedDocument) {
        if(!tokenizedDocument[currentIndex+=1].trim().contains("DATE OF HEARING"))
        {
            getDateOfHearing(tokenizedDocument);
        }
        return tokenizedDocument[currentIndex].split("DATE OF HEARING")[1].trim();
    }

    private String getRespondent(String[] tokenizedDocument) {
        if(tokenizedDocument[currentIndex].split("VS").length > 1)
        {
            if(!tokenizedDocument[currentIndex].split("VS")[1].isEmpty())
            {
                return tokenizedDocument[currentIndex].split("VS")[1].trim();
            }
        }
        return tokenizedDocument[currentIndex+=1].trim();
    }

    private String getAppellant(String input) {
        return input.split("VS")[0].trim();
    }
}
