package com.company;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RandomRangeGenerator {


    protected ArrayList<RangeNode> generateNode(int amount) {
        ArrayList<RangeNode> list = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            int zip0 = ThreadLocalRandom.current().nextInt(0,100000);
            int zip1 = ThreadLocalRandom.current().nextInt(0,100000);
            RangeNode node;
            if (zip0 <= zip1) {
                node = new RangeNode(zip0, zip1);
            } else {
                node = new RangeNode(zip1, zip0);
            }
            list.add(node);
        }
        return list;
    }

}
