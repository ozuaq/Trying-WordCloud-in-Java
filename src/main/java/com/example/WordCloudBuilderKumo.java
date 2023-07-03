package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.*;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.palette.ColorPalette;

public class WordCloudBuilderKumo {

    private TextAnalyzerSudachi textAnalyzerSudachi;

    public WordCloudBuilderKumo() {
        textAnalyzerSudachi = new TextAnalyzerSudachi();
    }

    public static void main(String[] args) {
        WordCloudBuilderKumo wordCloudBuilderKumo = new WordCloudBuilderKumo();
        wordCloudBuilderKumo.createWordCloudImage("./aozora_dummy_text.txt");
    }

    public void createWordCloudImage(String TextFilePath) {
        List<WordFrequency> wordFrequencies = new ArrayList<>();

        Map<String, Integer> wordFrequencyMap = textAnalyzerSudachi.wordFrequencyMap(TextFilePath);

        for (String key : wordFrequencyMap.keySet()) {
            wordFrequencies.add(new WordFrequency(key, wordFrequencyMap.get(key)));
        }

        Dimension dimension = new Dimension(600, 600);
        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(new ColorPalette(new Color(0xFF0000), new Color(0xFF007F), new Color(0xFF3399),
                new Color(0xFF00FF), new Color(0xFF33FF), new Color(0xFF66FF)));
        wordCloud.setFontScalar(new SqrtFontScalar(20, 50));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("./output/wordcloud_rectangle.png");
    }
}
