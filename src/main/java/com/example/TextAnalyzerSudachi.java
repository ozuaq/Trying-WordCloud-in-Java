package com.example;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.worksap.nlp.sudachi.Dictionary;
import com.worksap.nlp.sudachi.DictionaryFactory;
import com.worksap.nlp.sudachi.Morpheme;
import com.worksap.nlp.sudachi.Tokenizer;


public class TextAnalyzerSudachi {
	public static void main(String[] args) {
		TextAnalyzerSudachi textAnalyzerSudachi = new TextAnalyzerSudachi();
		textAnalyzerSudachi.showWordFrequencyMap(textAnalyzerSudachi.wordFrequencyMap("./aozora_dummy_text.txt"));
	}

	public Map<String, Integer> wordFrequencyMap(String TextFilePath) {
		Map<String, Integer> resultWordFrequencyMap = new HashMap<>();

		// 形態素解析器の用意
		Dictionary dictionary = null;
		try {
			// 設定ファイル sudachi.json を用意した場合にはそれを読み込む
			// dictionary = new
			// DictionaryFactory().create(Files.readString(Paths.get("sudachi.json")));
			dictionary = new DictionaryFactory().create();
		} catch (Exception e) {
			System.err.println("辞書が読み込めません: " + e);
			System.exit(-1);
		}
		Tokenizer tokenizer = dictionary.create();

		List<String> strLines = null;
		try {
			strLines = Files.readAllLines(Paths.get(TextFilePath), Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String strLine : strLines) {
			// 形態素解析
			for (List<Morpheme> list : tokenizer.tokenizeSentences(Tokenizer.SplitMode.C, strLine)) {
				for (Morpheme morpheme : list) {
					List<String> partOfSpeechList = morpheme.partOfSpeech();
					if (partOfSpeechList.get(2).equals("人名")) {
						String properNoun = morpheme.surface();
						// System.out.println(properNoun+" : "+String.join(" - ", partOfSpeechList));
						// System.out.println(properNoun+" : "+partOfSpeechList.get(2));
						if (resultWordFrequencyMap.containsKey(properNoun)) {
							int currentFrequency = resultWordFrequencyMap.get(properNoun);
							resultWordFrequencyMap.replace(properNoun, currentFrequency + 1);
						} else {
							resultWordFrequencyMap.put(properNoun, 1);
						}
					}
				}
			}
		}

		return resultWordFrequencyMap;
	}

	public void showWordFrequencyMap(Map<String, Integer> targetMap) {
		for (String key : targetMap.keySet()) {
			System.out.println(key + " : " + targetMap.get(key));
		}
	}

}