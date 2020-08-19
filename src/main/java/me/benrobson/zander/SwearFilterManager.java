package me.benrobson.zander;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SwearFilterManager {
    Map<String, String[]> words = new HashMap<>();
    int largestWordLength = 0;
    public boolean foundSwear;

    public void loadSwearFilterConfigs() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(ConfigurationManager.getConfig().getString("swearfilterwords")).openConnection().getInputStream()));
            String line = "";
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                counter++;
                String[] content = null;
                try {
                    content = line.split(",");
                    if(content.length == 0) {
                        continue;
                    }
                    String word = content[0];
                    String[] ignore_in_combination_with_words = new String[]{};
                    if (content.length > 1) {
                        ignore_in_combination_with_words = content[1].split("_");
                    }

                    if (word.length() > largestWordLength) {
                        largestWordLength = word.length();
                    }
                    words.put(word.replaceAll(" ", ""), ignore_in_combination_with_words);

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Loaded " + counter + " words to filter out");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> badWordsFound(String input) {
        if (input == null) {
            return new ArrayList<>();
        }

        input = input.replaceAll("1","i");
        input = input.replaceAll("!","i");
        input = input.replaceAll("3","e");
        input = input.replaceAll("4","a");
        input = input.replaceAll("@","a");
        input = input.replaceAll("5","s");
        input = input.replaceAll("7","t");
        input = input.replaceAll("0","o");
        input = input.replaceAll("9","g");


        ArrayList<String> badWords = new ArrayList<>();
        input = input.toLowerCase().replaceAll("[^a-zA-Z]", "");

        for (int start = 0; start < input.length(); start++) {
            for (int offset = 1; offset < (input.length()+1 - start) && offset < largestWordLength; offset++) {
                String wordToCheck = input.substring(start, start + offset);
                if (words.containsKey(wordToCheck)) {
                    String[] ignoreCheck = words.get(wordToCheck);
                    boolean ignore = false;
                    for (int s = 0; s < ignoreCheck.length; s++ ) {
                        if (input.contains(ignoreCheck[s])) {
                            ignore = true;
                            break;
                        }
                    }
                    if (!ignore) {
                        badWords.add(wordToCheck);
                    }
                }
            }
        }

        for (String string: badWords) {
            System.out.println(string + " qualified as a bad word.");
            foundSwear = true;
        }
        return badWords;
    }

    public String filterText(String input) {
        ArrayList<String> badWords = badWordsFound(input);
        if (badWords.size() > 0) {
            foundSwear = true;
            return "This message was blocked because a bad word was found.";
        }
        return input;
    }
}