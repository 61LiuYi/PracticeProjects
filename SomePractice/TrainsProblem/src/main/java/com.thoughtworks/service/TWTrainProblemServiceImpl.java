package com.thoughtworks.service;

import com.thoughtworks.util.*;
import com.thoughtworks.question.*;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 实现服务类接口
 */
public class TWTrainProblemServiceImpl implements TWTrainProblemService {
    private TWTownMapImpl townMap; // 地图类对象
    private List<TWQuestion> questions; // 问题列表

    public TWTrainProblemServiceImpl() {
        townMap = null;
        questions = new LinkedList<>();
    }

    @Override
    public void initWithFile(String filePath) throws FileNotFoundException {
        townMap = new TWTownMapImpl();
        townMap.initWithFile(filePath);
    }

    @Override
    public void inputQuestionWithFile(String filePath) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream(filePath));
        TWQuestionFactoryImpl factory = new TWQuestionFactoryImpl();

        while (in.hasNext()) {
            String input = in.next();
            TWQuestion question = factory.product(input);
            questions.add(question);
        }
    }

    @Override
    public String outputAnswer() {
        StringBuilder finalOutput = new StringBuilder();
        for (TWQuestion question : questions) {
            finalOutput.append(question.toString()).append(" -> Answer is:");
            finalOutput.append(question.solveWithTownMap(townMap));
            finalOutput.append("\n");
        }
        writeAnswerToLocalFile("src/resource/answer.txt",finalOutput.toString());
        return finalOutput.toString();
    }

    /**
     * 写入本地文件
     * @param filePath 文件路径
     * @param content 需要写入的内容
     */
    private void writeAnswerToLocalFile(String filePath, String content) {
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                throw new IOException();
            }
            BufferedWriter writer  = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
